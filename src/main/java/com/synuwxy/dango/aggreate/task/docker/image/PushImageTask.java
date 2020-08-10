package com.synuwxy.dango.aggreate.task.docker.image;

import com.github.dockerjava.api.DockerClient;
import com.synuwxy.dango.aggreate.task.Task;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wxy
 */
@Slf4j
@Builder
public class PushImageTask implements Task {

    private final DockerClient dockerClient;
    private final String name;

    @Override
    public void run() {
        log.info("推送镜像: {}", name);
        dockerClient.pushImageCmd(name).start();
    }
}
