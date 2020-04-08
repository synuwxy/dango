package com.synuwxy.dango.api.git.github;

import com.synuwxy.dango.api.git.github.model.hook.GithubHookParam;

/**
 * @author wxy
 */
public interface GithubService {

    /**
     * 通过hook调度进行构建
     * @param githubHookParam github hook 参数
     * @param type 类型
     */
    void hookBuild(GithubHookParam githubHookParam, String type);
}
