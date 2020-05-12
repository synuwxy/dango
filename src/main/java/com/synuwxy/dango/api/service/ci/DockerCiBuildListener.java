package com.synuwxy.dango.api.service.ci;

import com.github.dockerjava.api.DockerClient;
import com.synuwxy.dango.api.ci.model.DockerBuildParam;
import com.synuwxy.dango.api.service.code.CodeBuildEvent;
import com.synuwxy.dango.api.service.docker.DockerBuildEvent;
import com.synuwxy.dango.api.service.docker.GeneratorDockerfileEvent;
import com.synuwxy.dango.common.config.CommonConfig;
import com.synuwxy.dango.common.utils.FileUtil;
import com.synuwxy.dango.common.utils.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author wxy
 */
@Component
@Slf4j
public class DockerCiBuildListener {

    private final String DOCKER_WORKSPACE;
    private final String CODE_WORKSPACE;
    private final String DOCKERFILE_WORKSPACE;
    private final ApplicationEventPublisher publisher;

    public DockerCiBuildListener(CommonConfig commonConfig, ApplicationEventPublisher publisher) {
        this.CODE_WORKSPACE = commonConfig.getWorkspacePrefix() + "/code/workspace";
        this.DOCKER_WORKSPACE = commonConfig.getDockerfileWorkspace() + "/docker/workspace";
        this.DOCKERFILE_WORKSPACE = commonConfig.getDockerfileWorkspace();
        this.publisher = publisher;
    }

    @EventListener
    public void handle(DockerCiBuildEvent dockerCiBuildEvent) throws IOException {
        String codeWorkspace = CODE_WORKSPACE + "/" + UUIDUtil.generatorId();
        String dockerWorkspace = DOCKER_WORKSPACE + "/" + UUIDUtil.generatorId();
        DockerBuildParam dockerBuildParam = dockerCiBuildEvent.getDockerBuildParam();
        String type = dockerBuildParam.getType();
        String imageFullName = dockerBuildParam.getDockerTag();
        DockerClient dockerClient = dockerCiBuildEvent.getDockerClient();

        FileUtil.mkdir(codeWorkspace);
        FileUtil.mkdir(dockerWorkspace);

        try {
            publisher.publishEvent(new CodeBuildEvent(this, dockerBuildParam.getGitCloneParam(), codeWorkspace, dockerWorkspace));
            publisher.publishEvent(new GeneratorDockerfileEvent(this, type, DOCKERFILE_WORKSPACE, dockerWorkspace));
            publisher.publishEvent(new DockerBuildEvent(this, dockerClient, imageFullName, dockerWorkspace));
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            FileUtil.delete(codeWorkspace);
        }
    }

}
