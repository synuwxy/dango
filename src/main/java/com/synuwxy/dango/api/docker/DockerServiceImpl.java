package com.synuwxy.dango.api.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.BuildImageCmd;
import com.github.dockerjava.api.command.BuildImageResultCallback;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.command.PullImageResultCallback;
import com.github.dockerjava.api.model.*;
import com.synuwxy.dango.api.docker.model.ContainerModel;
import com.synuwxy.dango.common.utils.DockerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
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
    public void run(ContainerModel containerModel) {
        CreateContainerCmd createContainerCmd = dockerClient.createContainerCmd(containerModel.getImageName());
        createContainerCmd.withName(containerModel.getContainerName());

        // 使用默认的network类型
        createContainerCmd.withHostConfig(HostConfig.newHostConfig().withNetworkMode(containerModel.getNetWorkMode()));

        if (!containerModel.getPortBindings().isEmpty()) {
            createContainerCmd.withExposedPorts(containerModel.getExposedPorts());
            createContainerCmd.withHostConfig(HostConfig.newHostConfig().withPortBindings(containerModel.getPortBindings()));
        }

        if (!containerModel.getVolumes().isEmpty()) {
            createContainerCmd.withVolumes(containerModel.getVolumes());
        }

        if (!containerModel.getEnvs().isEmpty()) {
            createContainerCmd.withEnv(containerModel.getEnvs());
        }

        String containerId = createContainerCmd.exec().getId();
        dockerClient.startContainerCmd(containerId).exec();
    }

    @Override
    public void push(String tag) {
        dockerClient.pushImageCmd(tag).start();
    }

    @Override
    public void pull(String tag) {
        PullImageResultCallback pullImageResultCallback = new PullImageResultCallback() {
            @Override
            public void onNext(PullResponseItem item) {
                log.info("[docker log] {}", item);
                super.onNext(item);
            }
        };
        dockerClient.pullImageCmd(tag).exec(pullImageResultCallback);
    }

    @Override
    public void removeContainer(String containerId) {
        dockerClient.removeContainerCmd(containerId).exec();
    }

    @Override
    public void removeImage(String imageId) {
        dockerClient.removeImageCmd(imageId).exec();
    }

    @Override
    public Image searchImage(String tag) {
        String str = ":";
        if (!tag.contains(str)) {
            tag += ":latest";
        }
        // 筛选服务器上的镜像
        List<Image> images = dockerClient.listImagesCmd().withImageNameFilter(tag).exec();
        if (images.isEmpty()) {
            return null;
        }
        return images.get(0);
    }

    @Override
    public List<Image> searchImages(String tag) {
        return dockerClient.listImagesCmd().withImageNameFilter(tag).exec();
    }

    @Override
    public Container searchContainer(String name) {
        List<Container> containers = dockerClient.listContainersCmd().withNameFilter(Collections.singleton(name)).exec();
        if (containers.isEmpty()) {
            return null;
        }
        return containers.get(0);
    }

    @Override
    public List<Container> searchContainers(String name) {
        return dockerClient.listContainersCmd().withNameFilter(Collections.singleton(name)).exec();
    }

    @Override
    public void tagImage(String imageName, String imageNameWithRepository, String tag) {
        dockerClient.tagImageCmd(imageName, imageNameWithRepository, tag).exec();
    }
}
