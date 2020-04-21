package com.synuwxy.dango.api.git;

import com.synuwxy.dango.api.git.model.GitCloneParam;
import com.synuwxy.dango.common.utils.FileUtil;
import com.synuwxy.dango.common.utils.GitUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.regex.Pattern;

/**
 * @author wxy
 */
@Slf4j
@Service
public class GitServiceImpl implements GitService {

    private final int SUCCESS = 0;

    @Override
    public boolean clone(GitCloneParam gitCloneParam, String path) throws IOException, InterruptedException {
        if (notGitUrl(gitCloneParam.getRepository())) {
            throw new RuntimeException("非法Git仓库路径");
        }

        if (GitUtil.repoExists(path, gitCloneParam.getRepository())) {
            log.info("仓库已存在");
            return true;
        }

        String branch = gitCloneParam.getBranch();
        if (null == branch) {
            branch = "master";
        }

        String finalUrl = formatGitUrl(gitCloneParam);

        String command = "git clone -b " + branch + " " + finalUrl + " " + path + "/" + GitUtil.getRepositoryName(finalUrl);

        return Runtime.getRuntime().exec(command).waitFor() == SUCCESS;
    }

    private String formatGitUrl(GitCloneParam gitCloneParam) {
        String username = gitCloneParam.getUsername();
        String password = gitCloneParam.getPassword();
        String repository = gitCloneParam.getRepository();
        if (null == username || null == password) {
            return repository;
        }

        username = username.replace("@", "%40");

        String pattern = "(http://|https://|git@|ssh://git@)";
        String prefix = getProtocol(repository);
        return prefix + username + ":" + password + "@" + repository.replaceAll(pattern,"");
    }

    private String getProtocol(String gitUrl) {
        String httpPrefix = "http://";
        String httpsPrefix = "https://";

        if (gitUrl.contains(httpsPrefix)) {
            return httpsPrefix;
        }
        return httpPrefix;
    }

    private boolean notGitUrl(String gitUrl) {
        String pattern = "^(http://|https://|git@|ssh://git@).*\\.git$";
        return !Pattern.matches(pattern, gitUrl);
    }

    @Override
    public boolean registryGitAccount(String username, String email) throws Exception {
        if (!registryName(username)) {
            log.error("配置 git name 失败");
            return false;
        }
        return registryEmail(email);
    }

    @Override
    public boolean registryName(String username) throws Exception {
        String command = "git config --global user.name " + username;
        log.info("command: {}", command);
        return Runtime.getRuntime().exec(command).waitFor() == SUCCESS;
    }

    @Override
    public boolean registryEmail(String email) throws Exception {
        String command = "git config --global user.email " + email;
        log.info("command: {}", command);
        return Runtime.getRuntime().exec(command).waitFor() == SUCCESS;
    }

    @Override
    public boolean setKey(String publicKey, String privateKey, String home) {
        if (!setPublicKey(publicKey, home)) {
            log.error("设置公钥失败");
            return false;
        }
        return setPrivateKey(privateKey, home);
    }

    @Override
    public boolean setPublicKey(String publicKey, String home) {
        String path = home + "/.ssh/id_rsa.pub";
        log.info("setPublicKey");
        return FileUtil.writeFile(path, publicKey);
    }

    @Override
    public boolean setPrivateKey(String privateKey, String home) {
        String path = home + "/.ssh/id_rsa";
        log.info("setPrivateKey");
        return FileUtil.writeFile(path, privateKey);
    }

    @Override
    public boolean setKnownHosts(String gitUrl, String home) throws IOException {
        if (notGitUrl(gitUrl)) {
            return false;
        }
        String path = home + "/.ssh/known_hosts";

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
            return process.waitFor() == SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private String formatKnownHostsCommand(String gitUrl) {
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

    private String getBaseUrl(String gitUrl) {
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
}
