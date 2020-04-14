package com.synuwxy.dango.api.cd;

import com.github.dockerjava.api.model.Container;
import com.synuwxy.dango.api.cd.model.DockerDeployParam;
import com.synuwxy.dango.api.docker.DockerService;
import com.synuwxy.dango.api.docker.model.ContainerEnv;
import com.synuwxy.dango.api.docker.model.ContainerModel;
import com.synuwxy.dango.api.docker.model.ContainerPort;
import com.synuwxy.dango.api.docker.model.ContainerVolume;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author wxy
 */
@Slf4j
@Service
public class DockerCdServiceImpl implements DockerCdService {

    private final DockerService dockerService;

    public DockerCdServiceImpl(DockerService dockerService) {
        this.dockerService = dockerService;
    }

    @Override
    public void deploy(DockerDeployParam dockerDeployParam) {
        List<ContainerPort> containerPorts = dockerDeployParam.getContainerPorts();
        List<ContainerVolume> containerVolumes = dockerDeployParam.getContainerVolumes();
        List<ContainerEnv> containerEnvs = dockerDeployParam.getContainerEnvs();

        ContainerModel containerModel = new ContainerModel(dockerDeployParam.getContainerName(), dockerDeployParam.getImageName());

        if (verifyNetworkMode(dockerDeployParam.getNetworkMode())) {
            containerModel.setNetWorkMode(dockerDeployParam.getNetworkMode());
        }

        if (null != containerPorts) {
            containerModel.setContainerPorts(containerPorts);
        }
        if (null != containerVolumes) {
            containerModel.setContainerVolumes(containerVolumes);
        }
        if (null != containerEnvs) {
            containerModel.setContainerEnvs(containerEnvs);
        }

        dockerService.run(containerModel);
    }

    @Override
    public void slideDeploy(DockerDeployParam dockerDeployParam) {
        Container container = dockerService.searchContainer(dockerDeployParam.getContainerName());
        if (null != container) {
            dockerService.removeContainer(container.getId());
        }
        deploy(dockerDeployParam);
    }

    private boolean verifyNetworkMode(String networkMode) {
        List<String> networks = Arrays.asList("bridge", "host", "none");
        for (String mode:networks) {
            if (mode.equals(networkMode)) {
                return true;
            }
        }
        return false;
    }
}
