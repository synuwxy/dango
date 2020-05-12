package com.synuwxy.dango.aggreate.docker.container;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.model.*;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;
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

    public void run() {

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
}
