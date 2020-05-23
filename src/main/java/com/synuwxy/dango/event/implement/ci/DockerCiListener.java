package com.synuwxy.dango.event.implement.ci;

import com.synuwxy.dango.common.config.CommonConfig;
import com.synuwxy.dango.common.utils.FileUtil;
import com.synuwxy.dango.common.utils.UUIDUtil;
import com.synuwxy.dango.event.BuildEvent;
import com.synuwxy.dango.event.FileOperationsEvent;
import com.synuwxy.dango.event.implement.code.CodeCustomBuildCommand;
import com.synuwxy.dango.event.implement.code.CodeDefaultBuildCommand;
import com.synuwxy.dango.event.implement.docker.DockerBuildCommand;
import com.synuwxy.dango.event.implement.docker.GeneratorDockerfileCommand;
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
    @EventListener(condition = "#buildEvent.source instanceof T(com.synuwxy.dango.event.implement.ci.DockerCiCommand)")
    public void defaultHandle(BuildEvent buildEvent) throws IOException {
        log.info("[DockerCiEvent] start");
        String codeWorkspace = CODE_WORKSPACE + "/" + UUIDUtil.generatorId();
        String dockerWorkspace = DOCKER_WORKSPACE + "/" + UUIDUtil.generatorId();
        DockerCiCommand dockerCiCommand = (DockerCiCommand) buildEvent.getSource();
        CodeDefaultBuildCommand codeDefaultBuildCommand = CodeDefaultBuildCommand.create(
                dockerCiCommand.getGitCloneParam(),
                codeWorkspace,
                dockerWorkspace);
        GeneratorDockerfileCommand generatorDockerfileCommand = GeneratorDockerfileCommand.create(
                dockerCiCommand.getType(),
                DOCKERFILE_WORKSPACE,
                dockerWorkspace);
        DockerBuildCommand dockerBuildCommand = DockerBuildCommand.create(
                dockerCiCommand.getDockerClient(),
                dockerCiCommand.getImageName(),
                dockerWorkspace);
        //创建工作文件夹
        FileUtil.mkdirs(codeWorkspace, dockerWorkspace);
        try {
            log.info("[DockerCiEvent] 发送 codeDefaultBuildCommand");
            publisher.publishEvent(new BuildEvent(codeDefaultBuildCommand));
            log.info("[DockerCiEvent] 发送 generatorDockerfileCommand");
            publisher.publishEvent(new FileOperationsEvent(generatorDockerfileCommand));
            log.info("[DockerCiEvent] 发送 dockerBuildCommand");
            publisher.publishEvent(new BuildEvent(dockerBuildCommand));
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            FileUtil.delete(codeWorkspace);
        }
        log.info("[DockerCiEvent] end");
    }

    @Async
    @EventListener(condition = "#buildEvent.source instanceof T(com.synuwxy.dango.event.implement.ci.DockerCustomCiCommand)")
    public void customHandle(BuildEvent buildEvent) throws IOException {
        log.info("[DockerCiEvent] start");
        DockerCustomCiCommand dockerCustomCiCommand = (DockerCustomCiCommand) buildEvent.getSource();
        String codeWorkspace = CODE_WORKSPACE + "/" + UUIDUtil.generatorId();
        String dockerWorkspace = DOCKER_WORKSPACE + "/" + UUIDUtil.generatorId();
        CodeCustomBuildCommand codeCustomBuildCommand = CodeCustomBuildCommand.create(
                dockerCustomCiCommand.getGitCloneParam(),
                dockerCustomCiCommand.getCommand(),
                dockerCustomCiCommand.getProductPath(),
                dockerCustomCiCommand.getExtraPaths(),
                codeWorkspace,
                dockerWorkspace);
        DockerBuildCommand dockerBuildCommand = DockerBuildCommand.create(
                dockerCustomCiCommand.getDockerClient(),
                dockerCustomCiCommand.getImageName(),
                dockerWorkspace);
        GeneratorDockerfileCommand generatorDockerfileCommand = GeneratorDockerfileCommand.create(
                dockerCustomCiCommand.getType(),
                DOCKERFILE_WORKSPACE,
                dockerWorkspace);
        //创建工作文件夹
        FileUtil.mkdirs(codeWorkspace, dockerWorkspace);
        try {
            log.info("[DockerCiEvent] 发送 CodeBuildEvent");
            publisher.publishEvent(new BuildEvent(codeCustomBuildCommand));
            log.info("[DockerCiEvent] 发送 GeneratorDockerfileEvent");
            publisher.publishEvent(new FileOperationsEvent(generatorDockerfileCommand));
            log.info("[DockerCiEvent] 发送 DockerBuildEvent");
            publisher.publishEvent(new BuildEvent(dockerBuildCommand));
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            FileUtil.delete(codeWorkspace);
        }
        log.info("[DockerCiEvent] end");
    }

}
