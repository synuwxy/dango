package com.synuwxy.dango.api.git.github;

import com.synuwxy.dango.controller.GitHubHookParam;

public interface GithubService {

    void hookBuild(GitHubHookParam payload, String type);
}
