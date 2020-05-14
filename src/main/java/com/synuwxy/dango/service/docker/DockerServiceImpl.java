package com.synuwxy.dango.service.docker;

import com.alibaba.fastjson.JSONObject;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.*;
import com.github.dockerjava.api.model.*;
import com.synuwxy.dango.service.docker.model.ContainerModel;
import com.synuwxy.dango.service.docker.model.SearchContainerParam;
import com.synuwxy.dango.service.docker.model.SearchImageParam;
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
    public void build(String workspace, String tag) {
        log.info("编译镜像");
        File dir = new File(workspace);
        BuildImageResultCallback callback = new BuildImageResultCallback() {
            @Override
            public void onNext(BuildResponseItem item) {
                log.info("[docker log] {}", item.getStream());
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
                log.info("[docker log] {}", item.getStream());
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
            tag += ":latest";
            log.info("填充镜像全称 tag: {}", tag);
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
    public List<Image> searchImages(SearchImageParam searchImageParam) {
        log.info("查询镜像列表 searchImageParam: {}", JSONObject.toJSONString(searchImageParam));
        ListImagesCmd listImagesCmd = dockerClient.listImagesCmd();
        if (null != searchImageParam.getImageName()) {
            listImagesCmd.withImageNameFilter(searchImageParam.getImageName());
        }
        List<String> labels = searchImageParam.getLabels();
        if (null != labels && labels.size() > 0) {
            labels.forEach(listImagesCmd::withLabelFilter);
        }
        return listImagesCmd.exec();
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
    public List<Container> searchContainers(SearchContainerParam searchContainerParam) {
        log.info("查询容器列表 searchContainerParam: {}", JSONObject.toJSONString(searchContainerParam));
        ListContainersCmd listContainersCmd = dockerClient.listContainersCmd();

        if (null != searchContainerParam.getContainerId()) {
            listContainersCmd.withIdFilter(Collections.singleton(searchContainerParam.getContainerId()));
        }
        if (null != searchContainerParam.getContainerName()) {
            listContainersCmd.withNameFilter(Collections.singleton(searchContainerParam.getContainerName()));
        }
        if (null != searchContainerParam.getLabels() && searchContainerParam.getLabels().size() > 0) {
            listContainersCmd.withLabelFilter(searchContainerParam.getLabels());
        }
        if (null != searchContainerParam.getStatus() && searchContainerParam.getStatus().size() > 0) {
            listContainersCmd.withStatusFilter(searchContainerParam.getStatus());
        }

        return listContainersCmd.exec();
    }

    @Override
    public void tagImage(String imageName, String imageNameWithRepository, String tag) {
        log.info("更新镜像tag 原镜像全称: {}, 新镜像名: {}, 新镜像tag: {}", imageName, imageNameWithRepository, tag);
        dockerClient.tagImageCmd(imageName, imageNameWithRepository, tag).exec();
    }
}
