package com.synuwxy.dango.ddd.event.implement.docker;

import com.alibaba.fastjson.JSONObject;
import com.github.dockerjava.api.model.Container;
import com.synuwxy.dango.ddd.aggreate.docker.container.DockerContainer;
import com.synuwxy.dango.ddd.event.CleanEvent;
import com.synuwxy.dango.ddd.event.RemoveEvent;
import com.synuwxy.dango.ddd.event.StopEvent;
import com.synuwxy.dango.service.docker.model.ContainerState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author wxy
 */
@Component
@Slf4j
public class ContainerCleanListener {

    private final ApplicationEventPublisher publisher;

    public ContainerCleanListener(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @EventListener(condition = "#cleanEvent.source instanceof T(com.synuwxy.dango.ddd.event.implement.docker.ContainerCleanCommand)")
    public void handle(CleanEvent cleanEvent) {
        log.info("[CleanEvent] start");
        ContainerCleanCommand containerCleanCommand = (ContainerCleanCommand) cleanEvent.getSource();
        String containerName = containerCleanCommand.getName();
        DockerContainer dockerContainer = DockerContainer.builder()
                .dockerClient(containerCleanCommand.getDockerClient())
                .build();
        Container container = dockerContainer.searchContainer(containerName);
        if (null != container) {
            log.info("[CleanEvent] 容器已存在 container: {}", JSONObject.toJSONString(container));
            // 如果是已经在运行的容器，需要先stop才能remove
            if (ContainerState.RUNNING.equals(container.getState())) {
                log.info("[CleanEvent] stop container name: {}", containerName);
                ContainerStopCommand containerStopCommand = ContainerStopCommand.create(
                        container.getId(), containerCleanCommand.getDockerClient());
                publisher.publishEvent(new StopEvent(containerStopCommand));
            }
            log.info("[CleanEvent] stop container name: {}", containerName);
            ContainerRemoveCommand containerRemoveCommand = ContainerRemoveCommand.create(
                    container.getId(), containerCleanCommand.getDockerClient());
            publisher.publishEvent(new RemoveEvent(containerRemoveCommand));
        }
        log.info("[CleanEvent] end");
    }
}
