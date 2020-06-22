package com.synuwxy.dango.api.cd.model;

import com.synuwxy.dango.aggreate.DockerClientMachine;
import com.synuwxy.dango.aggreate.docker.container.DockerContainerEnv;
import com.synuwxy.dango.aggreate.docker.container.DockerContainerPort;
import com.synuwxy.dango.aggreate.docker.container.DockerContainerVolume;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
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
    private List<DockerContainerPort> containerPorts;
    private List<DockerContainerVolume> containerVolumes;
    private List<DockerContainerEnv> containerEnvs;
    private List<DockerClientMachine> machines = new ArrayList<>();
}
