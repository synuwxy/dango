package com.synuwxy.dango.api.script;

import com.synuwxy.dango.common.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;

/**
 * @author wxy
 */
@Slf4j
@Service
public class ScriptHandlerImpl implements ScriptHandler {

    @Override
    public boolean run(String workspace, String command) {
        log.info("执行命令 workspace: {}, cmd: {}", workspace, command);
        // 命令执行目录
        File dir = new File(workspace);
        Process process;
        try {
            // 组装命令执行脚本
            String shellPath = createShell(workspace, command);
            String cmd = "sh " + shellPath;
            // 执行命令
            process = Runtime.getRuntime().exec(cmd, null, dir);
        } catch (Exception e) {
            log.error("[ERROR] message: {}", e.getMessage());
            return false;
        }

        // 读取命令行输出
        assert process != null;
        try (InputStream inputStream = process.getInputStream();
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

            String result;
            while ((result = bufferedReader.readLine()) != null) {
                // 打印日志
                log.info("[log] {}", result);
            }
            return process.waitFor() == 0;
        } catch (Exception e) {
            log.error("[ERROR] message: {}", e.getMessage());
            return false;
        } finally {
            process.destroy();
        }
    }

    /**
     * 创建脚本文件
     * @param command shell命令
     * @param path 执行路径
     * @return 脚本文件绝对路径
     * @throws IOException IO异常
     */
    private String createShell(String path, String command) throws IOException {
        log.info("创建脚本文件");
        // 创建脚本
        String shellName = "run.sh";
        FileUtil.mkdir(path);
        String shellPath = path + "/" + shellName;
        File sh = new File(shellPath);
        if (!sh.exists()) {
            if (!sh.createNewFile()) {
                throw new RuntimeException("创建脚本文件失败");
            }

        }
        // 写入脚本命令
        try (FileOutputStream fos = new FileOutputStream(sh)) {
            fos.write(command.getBytes());
            fos.flush();
        }
        return shellPath;
    }
}
