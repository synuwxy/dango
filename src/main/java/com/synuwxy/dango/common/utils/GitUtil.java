package com.synuwxy.dango.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.regex.Pattern;

/**
 * @author wxy
 */
@Slf4j
public class GitUtil {

    private final static int SUCCESS = 0;

    public static boolean clone(String gitUrl, String branch, String path) throws IOException, InterruptedException {
        if (notGitUrl(gitUrl)) {
            throw new RuntimeException("非法Git仓库路径");
        }

        if (repoExists(path, gitUrl)) {
            log.info("仓库已存在");
            return true;
        }
        if (null == branch) {
            branch = "master";
        }

        String command = "git clone -b " + branch + " " + gitUrl + " " + path + "/" + getRepositoryName(gitUrl);

        return Runtime.getRuntime().exec(command).waitFor() == GitUtil.SUCCESS;
    }

    private static boolean notGitUrl(String gitUrl) {
        String pattern = "^(http://|https://|git@|ssh://git@).*\\.git$";
        return !Pattern.matches(pattern, gitUrl);
    }

    public static String getRepositoryName(String gitUrl) {
        int begin = gitUrl.lastIndexOf("/") + 1;
        int end = gitUrl.indexOf(".git");
        return gitUrl.substring(begin, end);
    }

    public static boolean registryGitAccount(String username, String email) throws Exception {
        if (!registryName(username)) {
            log.error("配置 git name 失败");
            return false;
        }
        return registryEmail(email);
    }

    public static boolean registryName(String name) throws Exception {
        String command = "git config --global user.name " + name;
        log.info("command: {}", command);
        return Runtime.getRuntime().exec(command).waitFor() == GitUtil.SUCCESS;
    }

    public static boolean registryEmail(String email) throws Exception {
        String command = "git config --global user.email " + email;
        log.info("command: {}", command);
        return Runtime.getRuntime().exec(command).waitFor() == GitUtil.SUCCESS;
    }

    public static boolean setKey(String publicKey, String privateKey) {
        if (!setPublicKey(publicKey)) {
            log.error("设置公钥失败");
            return false;
        }
        return setPrivateKey(privateKey);
    }

    public static boolean setPublicKey(String publicKey) {
        String path = "/root/.ssh/id_rsa.pub";
        log.info("setPublicKey");
        return writeFile(path, publicKey);
    }

    public static boolean setPrivateKey(String privateKey) {
        String path = "/root/.ssh/id_rsa";
        log.info("setPrivateKey");
        return writeFile(path, privateKey);
    }

    private static boolean writeFile(String filePath, String context) {
        File file = new File(filePath);
        log.info("写入文件 [path: {}, context: {}]", filePath, context);
        if (!FileUtil.assertionExists(filePath)) {
            log.error("文件校验失败 file name {}", file.getName());
            return false;
        }

        try (PrintWriter printWriter = new PrintWriter(file)){
            if (null == context) {
                context = "";
            }
            printWriter.print(context);
            printWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean setKnownHosts(String gitUrl) throws IOException {
        if (notGitUrl(gitUrl)) {
            return false;
        }
        String path = "/root/.ssh/known_hosts";

        File file = new File(path);

        if (!FileUtil.assertionExists(path)) {
            log.error("文件校验失败 file name {}", file.getName());
            return false;
        }

        String command = formatKnownHostsCommand(gitUrl);
        log.info("command: {}", command);
        Process process = Runtime.getRuntime().exec(command);

        try (PrintWriter printWriter = new PrintWriter(file);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()))){
            String result;
            while ((result = bufferedReader.readLine()) != null) {
                log.info("result {}", result);
                printWriter.println(result);
            }
            printWriter.flush();
            return process.waitFor() == GitUtil.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String formatKnownHostsCommand(String gitUrl) {
        String baseUrl = getBaseUrl(gitUrl);
        String command = "ssh-keyscan";
        String identification = ":";
        if (baseUrl.contains(identification)) {
            int index = baseUrl.indexOf(":");
            String port = baseUrl.substring(index + 1);
            String pattern = "^[1-9]\\d*$";
            if (Pattern.matches(pattern, port)) {
                command += " -p " + port;
            }
            baseUrl = baseUrl.substring(0, index);
        }
        command += " -t rsa " + baseUrl;
        return command;
    }

    private static String getBaseUrl(String gitUrl) {
        String httpPrefix = "http://";
        String httpsPrefix = "https://";
        String sshGitPrefix = "ssh://git@";
        String gitPrefix = "git@";


        if (gitUrl.contains(httpPrefix)) {
            gitUrl = gitUrl.substring(httpPrefix.length());
        }
        else if (gitUrl.contains(httpsPrefix)) {
            gitUrl = gitUrl.substring(httpsPrefix.length());
        }
        else if (gitUrl.contains(sshGitPrefix)) {
            // 因为gitPrefix是sshGitPrefix的子串，所以这个判断需要在 gitPrefix
            gitUrl = gitUrl.substring(sshGitPrefix.length());
        }
        else if (gitUrl.contains(gitPrefix)) {
            gitUrl = gitUrl.substring(gitPrefix.length());
        }

        return gitUrl.substring(0, gitUrl.indexOf("/"));
    }

    public static boolean repoExists(String userWorkspace, String gitUrl) {
        String repoName = GitUtil.getRepositoryName(gitUrl);
        String repoPath = userWorkspace + "/" + repoName;
        File repo = new File(repoPath);
        return repo.exists();
    }
}
