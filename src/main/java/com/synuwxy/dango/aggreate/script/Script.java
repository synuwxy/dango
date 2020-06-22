package com.synuwxy.dango.aggreate.script;

import com.synuwxy.dango.common.utils.FileUtil;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * @author wxy
 */
@Data
@Builder
@Slf4j
public class Script {

    private String command;

    public boolean run(String workspace) {
        log.info("执行命令 workspace: {}, cmd: {}", workspace, command);
        // 命令执行目录
        File dir = new File(workspace);
        Process process;
        try {
            // 组装命令执行脚本
            String shellPath = createShell(workspace);
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
            deleteShell(workspace);
        }
    }

    private String createShell(String workspace) throws IOException {
        log.info("创建脚本文件");
        // 创建脚本
        String shellName = "synuwxy-run.sh";
        FileUtil.mkdir(workspace);
        String shellPath = workspace + "/" + shellName;
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

    private void deleteShell(String workspace) {
        log.info("删除脚本文件");
        String shellName = "synuwxy-run.sh";
        String shellPath = workspace + "/" + shellName;
        File sh = new File(shellPath);
        sh.deleteOnExit();
    }
}
