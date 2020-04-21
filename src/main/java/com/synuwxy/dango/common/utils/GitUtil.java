package com.synuwxy.dango.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.regex.Pattern;

/**
 * @author wxy
 */
@Slf4j
public class GitUtil {


    private static boolean notGitUrl(String gitUrl) {
        String pattern = "^(http://|https://|git@|ssh://git@).*\\.git$";
        return !Pattern.matches(pattern, gitUrl);
    }

    public static String getRepositoryName(String gitUrl) {
        int begin = gitUrl.lastIndexOf("/") + 1;
        int end = gitUrl.indexOf(".git");
        return gitUrl.substring(begin, end);
    }

    public static boolean repoExists(String userWorkspace, String gitUrl) {
        String repoName = GitUtil.getRepositoryName(gitUrl);
        String repoPath = userWorkspace + "/" + repoName;
        File repo = new File(repoPath);
        return repo.exists();
    }
}
