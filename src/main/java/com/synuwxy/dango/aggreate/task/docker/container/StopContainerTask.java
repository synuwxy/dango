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
public class StopContainerTask implements Task {
    private final DockerClient dockerClient;
    private final String id;

    @Override
    public void run() {
        log.info("停止容器 containerId: {}", id);
        dockerClient.stopContainerCmd(id).exec();
    }
}
