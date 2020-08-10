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
public class RemoveImageTask implements Task {

    private final DockerClient dockerClient;
    private final String id;

    @Override
    public void run() {
        log.info("删除镜像 imageId: {}", id);
        dockerClient.removeImageCmd(id).exec();
    }
}
