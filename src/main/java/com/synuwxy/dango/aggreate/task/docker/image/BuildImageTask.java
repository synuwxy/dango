package com.synuwxy.dango.aggreate.task.docker.image;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.BuildImageCmd;
import com.github.dockerjava.api.command.BuildImageResultCallback;
import com.github.dockerjava.api.model.BuildResponseItem;
import com.synuwxy.dango.aggreate.task.Task;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * @author wxy
 */
@Slf4j
@Builder
public class BuildImageTask implements Task {

    private final DockerClient dockerClient;
    private final String workspace;
    private final String name;

    @Override
    public void run() {
        File dir = new File(workspace);
        BuildImageResultCallback callback = new BuildImageResultCallback() {
            @Override
            public void onNext(BuildResponseItem item) {
                log.info("[docker log] {}", item.getStream());
                super.onNext(item);
            }
        };
        Set<String> tags = new HashSet<>();
        tags.add(name);
        log.info("构建镜像");
        BuildImageCmd buildImageCmd = dockerClient.buildImageCmd(dir);
        buildImageCmd.withTags(tags).exec(callback).awaitImageId();
        log.info("构建完成");
    }
}
