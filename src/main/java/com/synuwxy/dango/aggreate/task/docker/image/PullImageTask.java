package com.synuwxy.dango.aggreate.task.docker.image;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.PullImageResultCallback;
import com.github.dockerjava.api.model.PullResponseItem;
import com.synuwxy.dango.aggreate.task.Task;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wxy
 */
@Slf4j
@Builder
public class PullImageTask implements Task {

    private final DockerClient dockerClient;
    private final String name;

    @Override
    public void run() {
        log.info("拉取镜像: {}", name);
        PullImageResultCallback pullImageResultCallback = new PullImageResultCallback() {
            @Override
            public void onNext(PullResponseItem item) {
                log.info("[docker log] {}", item.getStream());
                super.onNext(item);
            }
        };
        dockerClient.pullImageCmd(name).exec(pullImageResultCallback);
    }
}
