package com.synuwxy.dango.api.cd;

import com.synuwxy.dango.api.cd.model.DockerDeployParam;
import com.synuwxy.dango.api.docker.DockerService;
import com.synuwxy.dango.api.docker.model.ContainerEnv;
import com.synuwxy.dango.api.docker.model.ContainerModel;
import com.synuwxy.dango.api.docker.model.ContainerPort;
import com.synuwxy.dango.api.docker.model.ContainerVolume;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
