package com.synuwxy.dango.aggreate.docker.image;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.BuildImageCmd;
import com.github.dockerjava.api.command.BuildImageResultCallback;
import com.github.dockerjava.api.command.PullImageResultCallback;
import com.github.dockerjava.api.model.BuildResponseItem;
import com.github.dockerjava.api.model.PullResponseItem;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * @author wxy
 */
@Builder
@Data
@Slf4j
public class DockerImage {

    private final DockerClient dockerClient;

    private String id;
    private String name;
    private String tag;
    private String imageFullName;

    public void build(String workspace) {
        File dir = new File(workspace);
        BuildImageResultCallback callback = new BuildImageResultCallback() {
            @Override
            public void onNext(BuildResponseItem item) {
                log.info("[docker log] {}", item.getStream());
                super.onNext(item);
            }
        };
        Set<String> tags = new HashSet<>();
        tags.add(imageFullName);
        log.info("构建镜像");
        BuildImageCmd buildImageCmd = dockerClient.buildImageCmd(dir);
        buildImageCmd.withTags(tags).exec(callback).awaitImageId();
        log.info("构建完成");
    }

    public void push() {
        log.info("推送镜像 tag: {}", imageFullName);
        dockerClient.pushImageCmd(imageFullName).start();
    }

    public void pull() {
        log.info("拉取镜像 tag: {}", imageFullName);
        PullImageResultCallback pullImageResultCallback = new PullImageResultCallback() {
            @Override
            public void onNext(PullResponseItem item) {
                log.info("[docker log] {}", item.getStream());
                super.onNext(item);
            }
        };
        dockerClient.pullImageCmd(imageFullName).exec(pullImageResultCallback);
    }

    public void removeImage() {
        log.info("删除镜像 imageId: {}", id);
        dockerClient.removeImageCmd(id).exec();
    }

    public void tagImage(String imageName, String imageTag) {
        log.info("更新镜像tag 原镜像全称: {}, 新镜像名: {}, 新镜像tag: {}", imageFullName, imageName, imageTag);
        dockerClient.tagImageCmd(imageFullName, imageName, imageTag).exec();
    }

}
