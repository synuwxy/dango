package com.synuwxy.dango.ddd.event.implement.cd;

import com.synuwxy.dango.ddd.event.CleanEvent;
import com.synuwxy.dango.ddd.event.DeployEvent;
import com.synuwxy.dango.ddd.event.implement.docker.ContainerCleanCommand;
import com.synuwxy.dango.ddd.event.implement.docker.ContainerStartCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author wxy
 */
@Component
@Slf4j
public class DockerCdListener {

    private final ApplicationEventPublisher publisher;

    public DockerCdListener(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @EventListener(condition = "#deployEvent.source instanceof T(com.synuwxy.dango.ddd.event.implement.cd.DockerCdCommand)")
    public void handle(DeployEvent deployEvent) {
        log.info("[DeployEvent] DockerCd start");
        DockerCdCommand dockerCdCommand = (DockerCdCommand)deployEvent.getSource();
        ContainerCleanCommand containerCleanCommand = ContainerCleanCommand.create(
                dockerCdCommand.getContainerName(), dockerCdCommand.getDockerClient());
        ContainerStartCommand containerStartCommand = ContainerStartCommand.create(
                dockerCdCommand.getContainerName(),
                dockerCdCommand.getImageName(),
                dockerCdCommand.getContainerPorts(),
                dockerCdCommand.getContainerVolumes(),
                dockerCdCommand.getContainerEnvs(),
                dockerCdCommand.getDockerClient());

        publisher.publishEvent(new CleanEvent(containerCleanCommand));
        publisher.publishEvent(new DeployEvent(containerStartCommand));
        log.info("[DeployEvent] DockerCd end");
    }
}
