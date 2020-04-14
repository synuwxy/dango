package com.synuwxy.dango.api.docker;

import com.alibaba.fastjson.JSONObject;
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
        log.info("编译镜像");
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
        log.info("启动容器 containerModel: {}", JSONObject.toJSONString(containerModel));
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
        log.info("推送镜像 tag: {}", tag);
        dockerClient.pushImageCmd(tag).start();
    }

    @Override
    public void pull(String tag) {
        log.info("拉取镜像 tag: {}", tag);
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
    public void stopContainer(String containerId) {
        log.info("停止容器 containerId: {}", containerId);
        dockerClient.stopContainerCmd(containerId).exec();
    }

    @Override
    public void removeContainer(String containerId) {
        log.info("删除容器 containerId: {}", containerId);
        dockerClient.removeContainerCmd(containerId).exec();
    }

    @Override
    public void removeImage(String imageId) {
        log.info("删除镜像 imageId: {}", imageId);
        dockerClient.removeImageCmd(imageId).exec();
    }

    @Override
    public Image searchImage(String tag) {
        log.info("查询镜像 tag: {}", tag);
        String str = ":";
        if (!tag.contains(str)) {
            log.info("填充镜像全称 tag: {}", tag);
            tag += ":latest";
        }
        // 筛选服务器上的镜像
        List<Image> images = dockerClient.listImagesCmd().withImageNameFilter(tag).exec();
        if (images.isEmpty()) {
            log.info("查询结果为空");
            return null;
        }
        return images.get(0);
    }

    @Override
    public List<Image> searchImages(String tag) {
        log.info("查询镜像列表 tag: {}", tag);
        return dockerClient.listImagesCmd().withImageNameFilter(tag).exec();
    }

    @Override
    public Container searchContainer(String name) {
        log.info("查询容器 name: {}", name);
        List<Container> containers = dockerClient.listContainersCmd()
                .withNameFilter(Collections.singleton(name))
                .exec();
        if (containers.isEmpty()) {
            log.info("查询结果为空");
            return null;
        }
        return containers.get(0);
    }

    @Override
    public Container searchContainer(String name, String status) {
        log.info("查询容器 name: {}, status: {}", name, status);
        List<Container> containers = dockerClient.listContainersCmd()
                .withNameFilter(Collections.singleton(name))
                .withStatusFilter(Collections.singleton(status))
                .exec();

        if (containers.isEmpty()) {
            log.info("查询结果为空");
            return null;
        }
        return containers.get(0);
    }

    @Override
    public List<Container> searchContainers(String name) {
        log.info("查询容器列表 name: {}", name);
        return dockerClient.listContainersCmd().withNameFilter(Collections.singleton(name)).exec();
    }

    @Override
    public void tagImage(String imageName, String imageNameWithRepository, String tag) {
        log.info("更新镜像tag 原镜像全称: {}, 新镜像名: {}, 新镜像tag: {}", imageName, imageNameWithRepository, tag);
        dockerClient.tagImageCmd(imageName, imageNameWithRepository, tag).exec();
    }
}
