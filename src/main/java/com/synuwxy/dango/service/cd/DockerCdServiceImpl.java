package com.synuwxy.dango.service.cd;

import com.alibaba.fastjson.JSONObject;
import com.github.dockerjava.api.model.Container;
import com.synuwxy.dango.service.cd.model.DockerDeployServiceParam;
import com.synuwxy.dango.service.docker.DockerService;
import com.synuwxy.dango.service.docker.model.*;
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
    public void deploy(DockerDeployServiceParam dockerDeployServiceParam) {
        List<ContainerPort> containerPorts = dockerDeployServiceParam.getContainerPorts();
        List<ContainerVolume> containerVolumes = dockerDeployServiceParam.getContainerVolumes();
        List<ContainerEnv> containerEnvs = dockerDeployServiceParam.getContainerEnvs();

        ContainerModel containerModel = new ContainerModel(dockerDeployServiceParam.getContainerName(), dockerDeployServiceParam.getImageName());

        if (verifyNetworkMode(dockerDeployServiceParam.getNetworkMode())) {
            containerModel.setNetWorkMode(dockerDeployServiceParam.getNetworkMode());
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
    public void slideDeploy(DockerDeployServiceParam dockerDeployServiceParam) {
        cleanContainer(dockerDeployServiceParam.getContainerName());
        deploy(dockerDeployServiceParam);
    }

    /**
     * 校验network类型
     * @param networkMode network类型
     * @return 校验是否成功
     */
    private boolean verifyNetworkMode(String networkMode) {
        List<String> networks = Arrays.asList("bridge", "host", "none");
        for (String mode:networks) {
            if (mode.equals(networkMode)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 清除掉现有的容器
     * @param containerName 容器名称
     */
    private void cleanContainer(String containerName) {
        log.info("清除容器 name: {}", containerName);
        Container container = dockerService.searchContainer(containerName);
        if (null != container) {
            log.info("容器已存在 container: {}", JSONObject.toJSONString(container));
            // 如果是已经在运行的容器，需要先stop才能remove
            if (ContainerState.RUNNING.equals(container.getState())) {
                dockerService.stopContainer(container.getId());
            }
            dockerService.removeContainer(container.getId());
        }
    }
}
