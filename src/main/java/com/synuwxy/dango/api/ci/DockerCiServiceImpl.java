package com.synuwxy.dango.api.ci;

import com.synuwxy.dango.api.ci.model.DockerCustomBuildParam;
import com.synuwxy.dango.api.ci.model.DockerBuildParam;
import com.synuwxy.dango.api.docker.DockerService;
import com.synuwxy.dango.api.docker.DockerfileService;
import com.synuwxy.dango.common.config.CommonConfig;
import com.synuwxy.dango.common.utils.FileUtil;
import com.synuwxy.dango.common.utils.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author wxy
 */
@Service
@Slf4j
public class DockerCiServiceImpl implements DockerCiService {

    private final String DOCKER_CI_WORKSPACE;

    private final CodeBuilder codeBuilder;

    private final DockerfileService dockerfileService;

    private final DockerService dockerService;

    public DockerCiServiceImpl(CodeBuilder codeBuilder, DockerService dockerService, CommonConfig commonConfig, DockerfileService dockerfileService) {
        this.codeBuilder = codeBuilder;
        this.dockerService = dockerService;
        this.DOCKER_CI_WORKSPACE = commonConfig.getWorkspacePrefix() + "/docker/workspace";
        this.dockerfileService = dockerfileService;
    }

    @Override
    public void build(DockerBuildParam dockerBuildParam) throws IOException, InterruptedException {
        String workspace = DOCKER_CI_WORKSPACE + "/" + UUIDUtil.generatorId();
        FileUtil.mkdir(workspace);
        log.info("编译代码");
        codeBuilder.cleanBuild(dockerBuildParam.getGitCloneParam(), workspace);
        log.info("生成dockerfile");
        dockerfileService.generatorDockerfile(workspace, dockerBuildParam.getType());
        log.info("docker 构建");
        dockerService.build(workspace, dockerBuildParam.getType(), dockerBuildParam.getDockerTag());
        FileUtil.delete(workspace);
    }

    @Override
    public void customBuild(DockerCustomBuildParam dockerCustomBuildParam) throws IOException, InterruptedException {
        String workspace = DOCKER_CI_WORKSPACE + "/" + UUIDUtil.generatorId();
        FileUtil.mkdir(workspace);
        log.info("自定义编译代码");
        codeBuilder.customCleanBuild(
                dockerCustomBuildParam.getGitCloneParam(),
                dockerCustomBuildParam.getCommand(),
                dockerCustomBuildParam.getProductName(),
                dockerCustomBuildParam.getProductPath(),
                workspace);
        log.info("生成dockerfile");
        dockerfileService.generatorDockerfile(workspace, dockerCustomBuildParam.getType());
        log.info("docker 构建");
        dockerService.build(workspace, dockerCustomBuildParam.getType(), dockerCustomBuildParam.getDockerTag());
        FileUtil.delete(workspace);
    }
}
