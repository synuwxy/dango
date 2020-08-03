package com.synuwxy.dango.aggreate.task.shell;

import com.synuwxy.dango.common.utils.FileUtil;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * @author wxy
 */
@Builder
@Slf4j
public class ShellTask {

    private final String command;
    private final String workspace;

    public void run() throws Exception {
        log.info("执行命令 workspace: {}, cmd: {}", workspace, command);
        // 命令执行目录
        File dir = new File(workspace);
        Process process;
        // 组装命令执行脚本
        String shellPath = createShell(workspace);
        String cmd = "sh " + shellPath;
        // 执行命令
        process = Runtime.getRuntime().exec(cmd, null, dir);

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
            assert process.waitFor() == 0;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
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
