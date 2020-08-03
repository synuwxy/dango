package com.synuwxy.dango.aggreate.task.git;

import com.synuwxy.dango.aggreate.task.Task;
import com.synuwxy.dango.common.utils.GitUtil;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.regex.Pattern;

/**
 * git clone 代码
 * @author wxy
 */
@Slf4j
@Builder
public class CloneTask implements Task {

    private final int SUCCESS = 0;

    private final String repository;

    private String branch;

    private final String username;

    private final String password;

    private final String workspace;

    @Override
    public void run() throws Exception {
        gitClone();
    }

    public void gitClone() throws IOException, InterruptedException {
        if (notGitUrl(repository)) {
            throw new RuntimeException("非法Git仓库路径");
        }

        if (GitUtil.repoExists(workspace, repository)) {
            log.info("仓库已存在");
            return;
        }

        if (null == branch) {
            branch = "master";
        }

        String finalUrl = formatGitUrl();

        String command = "git clone -b " + branch + " " + finalUrl + " " + workspace + "/" + GitUtil.getRepositoryName(finalUrl);
        log.info("clone:{}", command);
        assert Runtime.getRuntime().exec(command).waitFor() == SUCCESS;
    }

    /**
     * 格式化url，如果有用户名密码的话使用用户名密码拉取
     * @return 格式化后的url
     */
    private String formatGitUrl() {
        if (null == username || null == password) {
            return repository;
        }

        String formatUsername = username.replace("@", "%40");

        String pattern = "(http://|https://|git@|ssh://git@)";
        String prefix = getProtocol(repository);
        return prefix + formatUsername + ":" + password + "@" + repository.replaceAll(pattern,"");
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
}
