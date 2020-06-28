package com.synuwxy.dango.ddd.event.implement.cd;

import com.github.dockerjava.api.DockerClient;
import com.synuwxy.dango.ddd.aggreate.docker.container.DockerContainerEnv;
import com.synuwxy.dango.ddd.aggreate.docker.container.DockerContainerPort;
import com.synuwxy.dango.ddd.aggreate.docker.container.DockerContainerVolume;
import lombok.Data;

import java.util.List;

/**
 * @author wxy
 */
@Data
public class DockerCdCommand {
    private String containerName;
    private String imageName;
    private String networkMode;
    private List<DockerContainerPort> containerPorts;
    private List<DockerContainerVolume> containerVolumes;
    private List<DockerContainerEnv> containerEnvs;
    private DockerClient dockerClient;
}
