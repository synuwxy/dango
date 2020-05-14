package com.synuwxy.dango.event.ci;

import com.synuwxy.dango.api.ci.model.DockerCiCommand;
import com.synuwxy.dango.event.code.CodeBuildEvent;
import com.synuwxy.dango.event.docker.DockerBuildEvent;
import com.synuwxy.dango.event.docker.GeneratorDockerfileEvent;
import com.synuwxy.dango.common.config.CommonConfig;
import com.synuwxy.dango.common.utils.FileUtil;
import com.synuwxy.dango.common.utils.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author wxy
 */
@Component
@Slf4j
public class DockerCiListener {

    private final String DOCKER_WORKSPACE;
    private final String CODE_WORKSPACE;
    private final String DOCKERFILE_WORKSPACE;
    private final ApplicationEventPublisher publisher;

    public DockerCiListener(CommonConfig commonConfig, ApplicationEventPublisher publisher) {
        this.CODE_WORKSPACE = commonConfig.getWorkspacePrefix() + "/code/workspace";
        this.DOCKER_WORKSPACE = commonConfig.getDockerfileWorkspace() + "/docker/workspace";
        this.DOCKERFILE_WORKSPACE = commonConfig.getDockerfileWorkspace();
        this.publisher = publisher;
    }

    @Async
    @EventListener(condition = "#dockerCiEvent.source instanceof T(com.synuwxy.dango.api.ci.model.DockerCiCommand)")
    public void handle(DockerCiEvent dockerCiEvent) throws IOException {
        DockerCiCommand dockerCiCommand = (DockerCiCommand) dockerCiEvent.getSource();
        String codeWorkspace = CODE_WORKSPACE + "/" + UUIDUtil.generatorId();
        String dockerWorkspace = DOCKER_WORKSPACE + "/" + UUIDUtil.generatorId();
        //创建工作文件夹
        FileUtil.mkdir(codeWorkspace);
        FileUtil.mkdir(dockerWorkspace);

        try {
            log.info("发送 CodeBuildEvent");
            publisher.publishEvent(new CodeBuildEvent(dockerCiCommand, codeWorkspace, dockerWorkspace));
            log.info("发送 GeneratorDockerfileEvent");
            publisher.publishEvent(new GeneratorDockerfileEvent(dockerCiCommand, DOCKERFILE_WORKSPACE, dockerWorkspace));
            log.info("发送 DockerBuildEvent");
            publisher.publishEvent(new DockerBuildEvent(dockerCiCommand, dockerWorkspace));
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            FileUtil.delete(codeWorkspace);
        }
    }

}
