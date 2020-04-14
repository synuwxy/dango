package com.synuwxy.dango.api.docker;

import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Image;
import com.synuwxy.dango.api.docker.model.ContainerModel;
import com.synuwxy.dango.api.docker.model.ContainerState;

import java.util.List;

/**
 * docker服务接口
 * @author wxy
 */
public interface DockerService {

    /**
     * 构建，产出一个 docker 镜像
     *
     * @param workspace 构建工作目录
     * @param type      类型
     * @param tag       镜像名称 (name:tag)
     */
    void build(String workspace, String type, String tag);

    /**
     * 启动，启动一个 docker 容器
     *
     * @param containerModel 容器对象
     */
    void run(ContainerModel containerModel);

    /**
     * push 镜像
     *
     * @param tag 镜像名称 (name:tag)
     */
    void push(String tag);

    /**
     * pull 镜像
     *
     * @param tag 镜像名称 (name:tag)
     */
    void pull(String tag);

    /**
     * stop 容器
     * @param containerId 容器id
     */
    void stopContainer(String containerId);

    /**
     * 删除容器
     *
     * @param containerId 容器id
     */
    void removeContainer(String containerId);

    /**
     * 删除镜像
     *
     * @param imageId 镜像id
     */
    void removeImage(String imageId);

    /**
     * 搜索镜像，单个
     *
     * @param tag 镜像名称 (name:tag)
     * @return 镜像对象，可能为null
     */
    Image searchImage(String tag);

    /**
     * 搜索镜像列表
     *
     * @param tag 镜像名称 (name:tag)
     * @return 镜像列表，可能为 empty
     */
    List<Image> searchImages(String tag);

    /**
     * 搜索容器列表
     *
     * @param name 容器名称
     * @return 容器列表，可能为 empty
     */
    List<Container> searchContainers(String name);

    /**
     * 搜索容器，单个
     *
     * @param name 容器名称 (name:tag)
     * @return 容器对象，可能为null
     */
    Container searchContainer(String name);

    /**
     * 搜索容器，单个
     *
     * @param name 容器名称 (name:tag)
     * @param status 容器状态 {@link ContainerState}
     * @return 容器对象，可能为null
     */
    Container searchContainer(String name, String status);

    /**
     * 容器打上标签
     *
     * @param imageName 容器名称 或者 id
     * @param targetName 新的名称
     * @param targetTag 新的tag
     */
    void tagImage(String imageName, String targetName, String targetTag);
}