package com.synuwxy.dango.api.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.BuildImageCmd;
import com.github.dockerjava.api.command.BuildImageResultCallback;
import com.github.dockerjava.api.model.BuildResponseItem;
import com.synuwxy.dango.common.utils.DockerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * @author wxy
 */
@Slf4j
@Service
public class DockerServiceImpl implements DockerService {

    private final DockerClient dockerClient;

    public DockerServiceImpl(DockerClient dockerClient) {
        this.dockerClient = dockerClient;
    }

    @Override
    public void build(String workspace, String type, String tag) {
        log.info("生成Dockerfile workspace: {}, type: {}, tag: {}", workspace, type, tag);
        DockerUtil.generatorDockerfile(workspace, type);
        File dir = new File(workspace);
        BuildImageResultCallback callback = new BuildImageResultCallback() {
            @Override
            public void onNext(BuildResponseItem item) {
                log.info("[docker log] {}", item);
                super.onNext(item);
            }
        };
        Set<String> tags = new HashSet<>();
        tags.add(tag);
        log.info("构建镜像");
        BuildImageCmd buildImageCmd = dockerClient.buildImageCmd(dir);
        buildImageCmd.withTags(tags).exec(callback).awaitImageId();
    }

    @Override
    public void run() {

    }

    @Override
    public void push() {

    }

    @Override
    public void update() {

    }
}
