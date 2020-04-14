package com.synuwxy.dango.api.cd.model;

import com.synuwxy.dango.api.docker.model.ContainerEnv;
import com.synuwxy.dango.api.docker.model.ContainerPort;
import com.synuwxy.dango.api.docker.model.ContainerVolume;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author wxy
 */
@Data
public class DockerDeployParam {
    @NotBlank(message = "容器名称不能为空")
    private String containerName;
    @NotBlank(message = "镜像名称不能为空")
    private String imageName;
    private String networkMode;
    private List<ContainerPort> containerPorts;
    private List<ContainerVolume> containerVolumes;
    private List<ContainerEnv> containerEnvs;
}
