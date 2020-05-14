package com.synuwxy.dango.service.git.github;

import com.synuwxy.dango.service.git.github.model.hook.GithubHookParam;

import java.io.IOException;

/**
 * @author wxy
 */
public interface GithubService {

    /**
     * 通过hook调度进行构建
     * @param githubHookParam github hook 参数
     * @param type 类型
     * @throws IOException IO
     * @throws InterruptedException Interrupted
     */
    void hookBuild(GithubHookParam githubHookParam, String type) throws IOException, InterruptedException;

    /**
     * 通过hook调度进行部署
     * @param githubHookParam github hook 参数
     * @param type 类型
     * @throws IOException IO
     * @throws InterruptedException Interrupted
     */
    void hookDeploy(GithubHookParam githubHookParam, String type) throws IOException, InterruptedException;
}
