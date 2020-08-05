package com.synuwxy.dango.aggreate.task.docker.container;

import com.github.dockerjava.api.DockerClient;
import com.synuwxy.dango.aggreate.task.Task;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wxy
 */
@Slf4j
@Builder
public class RemoveContainerTask implements Task {

    private final String id;
    private final DockerClient dockerClient;

    @Override
    public void run() {
        log.info("删除容器 containerId: {}", id);
        dockerClient.removeContainerCmd(id).exec();
    }
}