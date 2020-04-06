package com.synuwxy.dango.api.git.github;

import com.synuwxy.dango.api.docker.DockerService;
import com.synuwxy.dango.common.utils.DockerUtil;
import com.synuwxy.dango.common.utils.UUIDUtil;
import org.springframework.stereotype.Service;

@Service
public class GithubServiceImpl implements GithubService {

    private final DockerService dockerService;

    private final String GITHUB_WORKSPACE = "/home/github/workspace";

    public GithubServiceImpl(DockerService dockerService) {
        this.dockerService = dockerService;
    }

    @Override
    public void hookBuild() {
        String tag = "test:v1";
        String workspace = GITHUB_WORKSPACE + "/" + UUIDUtil.generatorId();
        dockerService.build(workspace, DockerUtil.TYPE_JAVA, tag);
    }

}
