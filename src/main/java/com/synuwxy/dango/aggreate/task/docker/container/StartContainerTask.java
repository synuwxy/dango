package com.synuwxy.dango.aggreate.task.docker.container;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.model.*;
import com.synuwxy.dango.aggreate.task.Task;
import com.synuwxy.dango.service.docker.model.ContainerEnv;
import com.synuwxy.dango.service.docker.model.ContainerPort;
import com.synuwxy.dango.service.docker.model.ContainerVolume;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wxy
 */
@Slf4j
@Builder
public class StartContainerTask implements Task {
    private final DockerClient dockerClient;
    private final String containerName;
    private final String imageName;
    private String networkMode;
    private final List<ContainerPort> containerPorts;
    private final List<ContainerVolume> containerVolumes;
    private final List<ContainerEnv> containerEnvs;

    @Override
    public void run() {
        startContainer();
    }

    private void startContainer() {

        CreateContainerCmd createContainerCmd = dockerClient.createContainerCmd(imageName);
        createContainerCmd.withName(containerName);

        // 使用默认的network类型
        if (null == networkMode) {
            networkMode = "none";
        }
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

    private List<PortBinding> getPortBindings() {
        List<PortBinding> list = new ArrayList<>();
        if (null == containerPorts) {
            return list;
        }
        containerPorts.forEach(containerPort -> {
            PortBinding portBinding = new PortBinding(Ports.Binding.bindPort(containerPort.getOutsidePort()),
                    ExposedPort.tcp(containerPort.getInsidePort()));
            list.add(portBinding);
        });
        return list;
    }

    private List<ExposedPort> getExposedPorts() {
        List<ExposedPort> list = new ArrayList<>();
        containerPorts.forEach(containerPort -> list.add(ExposedPort.tcp(containerPort.getInsidePort())));
        return list;
    }

    private List<Volume> getVolumes() {
        List<Volume> list = new ArrayList<>();
        if (null == containerVolumes) {
            return list;
        }
        containerVolumes.forEach(containerVolume -> list.add(new Volume(containerVolume.generator())));
        return list;
    }

    private List<String> getEnvs() {
        List<String> list = new ArrayList<>();
        if (null == containerEnvs) {
            return list;
        }
        containerEnvs.forEach(containerEnv -> list.add(containerEnv.generator()));
        return list;
    }
}
