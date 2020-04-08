package com.synuwxy.dango.api.git.github;

import com.alibaba.fastjson.JSONObject;
import com.synuwxy.dango.api.ci.CodeBuilder;
import com.synuwxy.dango.api.docker.DockerService;
import com.synuwxy.dango.common.config.CommonConfig;
import com.synuwxy.dango.common.utils.FileUtil;
import com.synuwxy.dango.common.utils.UUIDUtil;
import com.synuwxy.dango.api.git.github.model.hook.GithubHookParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GithubServiceImpl implements GithubService {

    private final DockerService dockerService;

    private final String GITHUB_WORKSPACE;

    private final CodeBuilder codeBuilder;


    public GithubServiceImpl(DockerService dockerService, CodeBuilder codeBuilder, CommonConfig commonConfig) {
        this.dockerService = dockerService;
        this.codeBuilder = codeBuilder;
        this.GITHUB_WORKSPACE = commonConfig.getWorkspacePrefix() + "/github/workspace";
    }

    @Async
    @Override
    public void hookBuild(GithubHookParam gitHubHookParam, String type) {
        log.info("GitHub Hook 构建 gitHubHookParam: {}, type: {}", JSONObject.toJSONString(gitHubHookParam), type);
        String name = gitHubHookParam.getRepository().getName();
        String repo = gitHubHookParam.getRepository().getCloneUrl();
        String branch = gitHubHookParam.getRepository().getDefaultBranch();
        String baseTag = "synuwxy";
        String workspace = GITHUB_WORKSPACE + "/" + UUIDUtil.generatorId();
        FileUtil.mkdir(workspace);
        try {
            log.info("编译代码");
            codeBuilder.cleanBuild(repo, branch, type, workspace);
            log.info("GitHub Hook 构建");
            dockerService.build(workspace, type, baseTag + "/" + name);
        } catch (Exception e) {
            log.error("GitHub Hook 构建失败 message: {}", e.getMessage());
        }
    }

}
