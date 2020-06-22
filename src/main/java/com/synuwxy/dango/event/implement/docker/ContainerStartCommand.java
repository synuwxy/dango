package com.synuwxy.dango.event.implement.docker;

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
public class ContainerStartCommand {
    private final String containerName;
    private final String imageName;
    private final List<DockerContainerPort> containerPorts;
    private final List<DockerContainerVolume> containerVolumes;
    private final List<DockerContainerEnv> containerEnvs;
    private final DockerClient dockerClient;

    public static ContainerStartCommand create(
            String containerName,
            String imageName,
            List<DockerContainerPort> containerPorts,
            List<DockerContainerVolume> containerVolumes,
            List<DockerContainerEnv> containerEnvs,
            DockerClient dockerClient) {
        return new ContainerStartCommand(
                containerName,
                imageName,
                containerPorts,
                containerVolumes,
                containerEnvs,
                dockerClient);
    }
}
