package com.synuwxy.dango.service.docker.model;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import com.github.dockerjava.api.model.Volume;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wxy
 */
public class ContainerModel {
    private final String containerName;
    private final String imageName;
    private String networkMode = "none";
    private List<ContainerPort> containerPorts = new ArrayList<>();
    private List<ContainerVolume> containerVolumes = new ArrayList<>();
    private List<ContainerEnv> containerEnvs = new ArrayList<>();

    public ContainerModel(String containerName, String imageName) {
        this.containerName = containerName;
        this.imageName = imageName;
    }

    public List<PortBinding> getPortBindings() {
        List<PortBinding> list = new ArrayList<>();
        containerPorts.forEach(containerPort -> {
            PortBinding portBinding = new PortBinding(Ports.Binding.bindPort(containerPort.getOutsidePort()), ExposedPort.tcp(containerPort.getInsidePort()));
            list.add(portBinding);
        });
        return list;
    }

    public List<ExposedPort> getExposedPorts() {
        List<ExposedPort> list = new ArrayList<>();
        containerPorts.forEach(containerPort -> list.add(ExposedPort.tcp(containerPort.getInsidePort())));
        return list;
    }

    public List<Volume> getVolumes() {
        List<Volume> list = new ArrayList<>();
        containerVolumes.forEach(containerVolume -> list.add(new Volume(containerVolume.generator())));
        return list;
    }

    public List<String> getEnvs() {
        List<String> list = new ArrayList<>();
        containerEnvs.forEach(containerEnv -> list.add(containerEnv.generator()));
        return list;
    }

    public String getContainerName() {
        return containerName;
    }

    public String getImageName() {
        return imageName;
    }

    public List<ContainerPort> getContainerPorts() {
        return containerPorts;
    }

    public void setContainerPorts(List<ContainerPort> containerPorts) {
        this.containerPorts = containerPorts;
    }

    public List<ContainerVolume> getContainerVolumes() {
        return containerVolumes;
    }

    public void setContainerVolumes(List<ContainerVolume> containerVolumes) {
        this.containerVolumes = containerVolumes;
    }

    public List<ContainerEnv> getContainerEnvs() {
        return containerEnvs;
    }

    public void setContainerEnvs(List<ContainerEnv> containerEnvs) {
        this.containerEnvs = containerEnvs;
    }

    public String getNetWorkMode() {
        return networkMode;
    }

    public void setNetWorkMode(String networkMode) {
        this.networkMode = networkMode;
    }
}
