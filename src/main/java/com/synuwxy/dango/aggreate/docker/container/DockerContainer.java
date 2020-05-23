package com.synuwxy.dango.aggreate.docker.container;

import com.alibaba.fastjson.JSONObject;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.command.ListContainersCmd;
import com.github.dockerjava.api.model.*;
import com.synuwxy.dango.service.docker.model.SearchContainerParam;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author wxy
 */
@Slf4j
@Builder
public class DockerContainer {
    private final DockerClient dockerClient;
    private final String id;
    private final String containerName;
    private final String imageName;
    private final String networkMode = "none";
    private final List<DockerContainerPort> dockerContainerPorts;
    private final List<DockerContainerVolume> dockerContainerVolumes;
    private final List<DockerContainerEnv> dockerContainerEnvs;

    private List<PortBinding> getPortBindings() {
        List<PortBinding> list = new ArrayList<>();
        dockerContainerPorts.forEach(dockerContainerPort -> {
            PortBinding portBinding = new PortBinding(Ports.Binding.bindPort(dockerContainerPort.getOutsidePort()),
                    ExposedPort.tcp(dockerContainerPort.getInsidePort()));
            list.add(portBinding);
        });
        return list;
    }

    private List<ExposedPort> getExposedPorts() {
        List<ExposedPort> list = new ArrayList<>();
        dockerContainerPorts.forEach(dockerContainerPort -> list.add(ExposedPort.tcp(dockerContainerPort.getInsidePort())));
        return list;
    }

    private List<Volume> getVolumes() {
        List<Volume> list = new ArrayList<>();
        dockerContainerVolumes.forEach(dockerContainerVolume -> list.add(new Volume(dockerContainerVolume.generator())));
        return list;
    }

    private List<String> getEnvs() {
        List<String> list = new ArrayList<>();
        dockerContainerEnvs.forEach(dockerContainerEnv -> list.add(dockerContainerEnv.generator()));
        return list;
    }

    public void startContainer() {

        CreateContainerCmd createContainerCmd = dockerClient.createContainerCmd(imageName);
        createContainerCmd.withName(containerName);

        // 使用默认的network类型
        createContainerCmd.withHostConfig(HostConfig.newHostConfig().withNetworkMode(networkMode));

        if (!getPortBindings().isEmpty()) {
            createContainerCmd.withExposedPorts(getExposedPorts());
            createContainerCmd.withHostConfig(HostConfig.newHostConfig().withPortBindings(getPortBindings()));
        }

        if (!getVolumes().isEmpty()) {
            createContainerCmd.withVolumes(getVolumes());
        }

        if (!getEnvs().isEmpty()) {
            createContainerCmd.withEnv(getEnvs());
        }

        String containerId = createContainerCmd.exec().getId();
        dockerClient.startContainerCmd(containerId).exec();
    }

    public void stopContainer() {
        log.info("停止容器 containerId: {}", id);
        dockerClient.stopContainerCmd(id).exec();
    }

    public void removeContainer() {
        log.info("删除容器 containerId: {}", id);
        dockerClient.removeContainerCmd(id).exec();
    }

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
}
