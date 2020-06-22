package com.synuwxy.dango.service.cd.model;

import com.synuwxy.dango.aggreate.DockerClientMachine;
import com.synuwxy.dango.service.docker.model.ContainerEnv;
import com.synuwxy.dango.service.docker.model.ContainerPort;
import com.synuwxy.dango.service.docker.model.ContainerVolume;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wxy
 */
@Data
public class DockerDeployServiceParam {
    @NotBlank(message = "容器名称不能为空")
    private String containerName;
    @NotBlank(message = "镜像名称不能为空")
    private String imageName;
    private String networkMode;
    private List<ContainerPort> containerPorts;
    private List<ContainerVolume> containerVolumes;
    private List<ContainerEnv> containerEnvs;
}
