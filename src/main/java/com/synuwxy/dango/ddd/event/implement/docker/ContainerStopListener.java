package com.synuwxy.dango.ddd.event.implement.docker;

import com.synuwxy.dango.ddd.aggreate.docker.container.DockerContainer;
import com.synuwxy.dango.ddd.event.StopEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author wxy
 */
@Component
@Slf4j
public class ContainerStopListener {
    @EventListener(condition = "#stopEvent.source instanceof T(com.synuwxy.dango.ddd.event.implement.docker.ContainerStopCommand)")
    public void handle(StopEvent stopEvent) {
        log.info("[StopEvent] ContainerStop start");
        ContainerStopCommand containerStopCommand = (ContainerStopCommand)stopEvent.getSource();
        DockerContainer dockerContainer = DockerContainer.builder()
                .id(containerStopCommand.getId())
                .dockerClient(containerStopCommand.getDockerClient())
                .build();
        dockerContainer.stopContainer();
        log.info("[StopEvent] ContainerStop end");
    }
}
