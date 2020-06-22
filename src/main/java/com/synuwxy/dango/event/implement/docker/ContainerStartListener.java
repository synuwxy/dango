package com.synuwxy.dango.event.implement.docker;

import com.synuwxy.dango.aggreate.docker.container.DockerContainer;
import com.synuwxy.dango.event.DeployEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author wxy
 */
@Component
@Slf4j
public class ContainerStartListener {
    @EventListener(condition = "#deployEvent.source instanceof T(com.synuwxy.dango.event.implement.docker.ContainerStartCommand)")
    public void handle(DeployEvent deployEvent) {
        log.info("[deployEvent] ContainerStart start");
        ContainerStartCommand containerStartCommand = (ContainerStartCommand)deployEvent.getSource();
        DockerContainer dockerContainer = DockerContainer.builder()
                .containerName(containerStartCommand.getContainerName())
                .imageName(containerStartCommand.getImageName())
                .containerEnvs(containerStartCommand.getContainerEnvs())
                .containerPorts(containerStartCommand.getContainerPorts())
                .containerVolumes(containerStartCommand.getContainerVolumes())
                .dockerClient(containerStartCommand.getDockerClient())
                .build();

        dockerContainer.startContainer();
        log.info("[deployEvent] ContainerStart end");
    }
}
