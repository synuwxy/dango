package com.synuwxy.dango.api.ci;

import com.synuwxy.dango.api.docker.DockerService;
import com.synuwxy.dango.common.config.CommonConfig;
import com.synuwxy.dango.common.utils.FileUtil;
import com.synuwxy.dango.common.utils.UUIDUtil;
import com.synuwxy.dango.controller.ci.DockerBuildParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author wxy
 */
@Service
@Slf4j
public class DockerCiServiceImpl implements DockerCiService {

    private final String DOCKER_CI_WORKSPACE;

    private final CodeBuilder codeBuilder;

    private final DockerService dockerService;

    public DockerCiServiceImpl(CodeBuilder codeBuilder, DockerService dockerService, CommonConfig commonConfig) {
        this.codeBuilder = codeBuilder;
        this.dockerService = dockerService;
        this.DOCKER_CI_WORKSPACE = commonConfig.getWorkspacePrefix() + "/docker/workspace";
    }


    @Async
    @Override
    public void build(DockerBuildParam dockerBuildParam) {
        String workspace = DOCKER_CI_WORKSPACE + "/" + UUIDUtil.generatorId();
        FileUtil.mkdir(workspace);
        try {
            log.info("编译代码");
            codeBuilder.cleanBuild(dockerBuildParam.getRepository(), dockerBuildParam.getBranch(), dockerBuildParam.getType(), workspace);
            log.info("docker 构建");
            dockerService.build(workspace, dockerBuildParam.getType(), dockerBuildParam.getDockerTag());
        } catch (Exception e) {
            log.error("DockerCI 构建失败 message: {}", e.getMessage());
        }
    }
}