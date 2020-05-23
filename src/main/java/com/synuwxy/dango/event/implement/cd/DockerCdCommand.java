package com.synuwxy.dango.event.implement.cd;

import com.github.dockerjava.api.DockerClient;
import com.synuwxy.dango.aggreate.docker.container.DockerContainerEnv;
import com.synuwxy.dango.aggreate.docker.container.DockerContainerPort;
import com.synuwxy.dango.aggreate.docker.container.DockerContainerVolume;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author wxy
 */
@Data
@AllArgsConstructor
public class DockerCdCommand {
    private String containerName;
    private String imageName;
    private String networkMode;
    private final List<DockerContainerPort> dockerContainerPorts;
    private final List<DockerContainerVolume> dockerContainerVolumes;
    private final List<DockerContainerEnv> dockerContainerEnvs;
    private DockerClient dockerClient;
}
