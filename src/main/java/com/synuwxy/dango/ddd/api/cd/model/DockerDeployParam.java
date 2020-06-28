package com.synuwxy.dango.ddd.api.cd.model;

import com.synuwxy.dango.ddd.aggreate.DockerClientMachine;
import com.synuwxy.dango.ddd.aggreate.docker.container.DockerContainerEnv;
import com.synuwxy.dango.ddd.aggreate.docker.container.DockerContainerPort;
import com.synuwxy.dango.ddd.aggreate.docker.container.DockerContainerVolume;
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
