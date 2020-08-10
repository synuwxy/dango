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
public class TagImageTask implements Task {

    private final DockerClient dockerClient;
    private final String sourceName;
    private final String targetName;
    private final String targetTag;

    @Override
    public void run() {
        log.info("更新镜像tag 原镜像全称: {}, 新镜像名: {}, 新镜像tag: {}", sourceName, targetName, targetTag);
        dockerClient.tagImageCmd(sourceName, targetName, targetTag).exec();
    }
}
