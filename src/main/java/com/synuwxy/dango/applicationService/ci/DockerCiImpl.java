package com.synuwxy.dango.applicationService.ci;

import com.github.dockerjava.api.DockerClient;
import com.synuwxy.dango.api.ci.DockerCiService;
import com.synuwxy.dango.api.ci.model.DockerBuildParam;
import com.synuwxy.dango.api.ci.model.DockerCustomBuildParam;
import com.synuwxy.dango.api.code.CodeTypeFinder;
import com.synuwxy.dango.api.git.model.GitCloneParam;
import com.synuwxy.dango.common.config.CommonConfig;
import com.synuwxy.dango.common.utils.UUIDUtil;
import com.synuwxy.dango.pipeline.Pipeline;
import com.synuwxy.dango.pipeline.task.code.MoveProductTask;
import com.synuwxy.dango.pipeline.task.docker.DockerImageBuildTask;
import com.synuwxy.dango.pipeline.task.docker.DockerfileGeneratorTask;
import com.synuwxy.dango.pipeline.task.git.GitCloneTask;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author wxy
 */
@Service
public class DockerCiImpl implements DockerCiService {
    private String codeWorkspace;
    private String dockerWorkspace;
    private final String dockerfileWorkspace;
    private final CodeTypeFinder codeTypeFinder;
    private final DockerClient dockerClient;

    public DockerCiImpl(CommonConfig commonConfig, CodeTypeFinder codeTypeFinder, DockerClient dockerClient) {
        this.codeWorkspace = commonConfig.getWorkspacePrefix() + "/code/workspace";
        this.dockerWorkspace = commonConfig.getWorkspacePrefix() + "/docker/workspace";
        this.dockerfileWorkspace = commonConfig.getDockerfileWorkspace();
        this.codeTypeFinder = codeTypeFinder;
        this.dockerClient = dockerClient;
    }

    @Override
    public void build(DockerBuildParam dockerBuildParam) {
        this.codeWorkspace += "/" + UUIDUtil.generatorId();
        this.dockerWorkspace += "/" + UUIDUtil.generatorId();
        GitCloneParam gitCloneParam = dockerBuildParam.getGitCloneParam();
        String imageFullName = dockerBuildParam.getDockerTag();
        String dockerfileName = dockerBuildParam.getType();

        Pipeline.create()
                .addTask(GitCloneTask.create(codeWorkspace, gitCloneParam))
                .addTask(MoveProductTask.create(codeTypeFinder, codeWorkspace, dockerWorkspace))
                .addTask(DockerfileGeneratorTask.create(dockerfileName, dockerfileWorkspace, dockerWorkspace))
                .addTask(DockerImageBuildTask.create(dockerClient, imageFullName, dockerWorkspace))
                .run();
    }

    @Override
    public void customBuild(DockerCustomBuildParam dockerCustomBuildParam) throws IOException, InterruptedException {

    }
}
