package com.synuwxy.dango.event.implement.docker;

import com.synuwxy.dango.aggreate.docker.container.DockerContainer;
import com.synuwxy.dango.event.RemoveEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author wxy
 */
@Component
@Slf4j
public class ContainerRemoveListener {

    @EventListener(condition = "#removeEvent.source instanceof T(com.synuwxy.dango.event.implement.docker.ContainerRemoveCommand)")
    public void handle(RemoveEvent removeEvent) {
        log.info("[RemoveEvent] ContainerRemove start");
        ContainerRemoveCommand containerRemoveCommand = (ContainerRemoveCommand) removeEvent.getSource();
        DockerContainer dockerContainer = DockerContainer.builder()
                .id(containerRemoveCommand.getId())
                .dockerClient(containerRemoveCommand.getDockerClient())
                .build();
        dockerContainer.removeContainer();
        log.info("[RemoveEvent] ContainerRemove end");
    }
}
