package com.synuwxy.dango.api.git.github;

import com.alibaba.fastjson.JSONObject;
import com.synuwxy.dango.api.cd.DockerCdService;
import com.synuwxy.dango.api.cd.model.DockerDeployParam;
import com.synuwxy.dango.api.ci.DockerCiService;
import com.synuwxy.dango.api.ci.model.DockerBuildParam;
import com.synuwxy.dango.api.git.model.GitCloneParam;
import com.synuwxy.dango.api.git.github.model.hook.GithubHookParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GithubServiceImpl implements GithubService {

    private final DockerCiService dockerCiService;

    private final DockerCdService dockerCdService;

    public GithubServiceImpl(DockerCiService dockerCiService, DockerCdService dockerCdService) {
        this.dockerCiService = dockerCiService;
        this.dockerCdService = dockerCdService;
    }

    @Async
    @Override
    public void hookBuild(GithubHookParam githubHookParam, String type) {
        log.info("GitHub Hook 构建 gitHubHookParam: {}, type: {}", JSONObject.toJSONString(githubHookParam), type);
        String name = githubHookParam.getRepository().getName();
        String repository = githubHookParam.getRepository().getCloneUrl();
        String branch = githubHookParam.getRepository().getDefaultBranch();
        String dockerTag = "synuwxy/" + name;
        build(repository, dockerTag, type, branch);
    }

    private void build(String repository, String dockerTag, String type, String branch) {
        DockerBuildParam dockerBuildParam = new DockerBuildParam();
        dockerBuildParam.setDockerTag(dockerTag);
        dockerBuildParam.setType(type);

        GitCloneParam gitCloneParam = new GitCloneParam();
        gitCloneParam.setRepository(repository);
        gitCloneParam.setBranch(branch);
        dockerBuildParam.setGitCloneParam(gitCloneParam);
        dockerCiService.build(dockerBuildParam);
    }

    @Async
    @Override
    public void hookDeploy(GithubHookParam githubHookParam, String type) {
        String name = githubHookParam.getRepository().getName();
        String repository = githubHookParam.getRepository().getCloneUrl();
        String branch = githubHookParam.getRepository().getDefaultBranch();
        String dockerTag = "synuwxy/" + name;
        String networkMode = "host";

        build(repository, dockerTag, type, branch);
        deploy(dockerTag, name, networkMode);
    }

    private void deploy(String imageName, String containerName, String networkMode) {
        DockerDeployParam dockerDeployParam = new DockerDeployParam();
        dockerDeployParam.setImageName(imageName);
        dockerDeployParam.setContainerName(containerName);
        dockerDeployParam.setNetworkMode(networkMode);
        dockerCdService.slideDeploy(dockerDeployParam);
    }
}
