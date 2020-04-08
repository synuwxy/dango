package com.synuwxy.dango.api.docker.model;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import com.github.dockerjava.api.model.Volume;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wxy
 */
public class ContainerModel {
    private String containerName;
    private String imageName;
    private List<ContainerPort> containerPorts = new ArrayList<>();
    private List<ContainerVolume> containerVolumes = new ArrayList<>();
    private List<ContainerEnv> containerEnvs = new ArrayList<>();

    public ContainerModel(String containerName, String imageName) {
        this.containerName = containerName;
        this.imageName = imageName;
    }

    public List<PortBinding> getPortBindings() {
        List<PortBinding> list = new ArrayList<>();
        containerPorts.forEach(containerPort -> list.add(PortBinding.parse(containerPort.generator())));
        return list;
    }

    public List<ExposedPort> getExposedPorts() {
        List<ExposedPort> list = new ArrayList<>();
        containerPorts.forEach(containerPort -> list.add(ExposedPort.tcp(containerPort.getOutsidePort())));
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
}
