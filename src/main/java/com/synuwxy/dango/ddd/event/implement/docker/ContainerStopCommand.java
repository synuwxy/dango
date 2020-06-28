package com.synuwxy.dango.ddd.event.implement.docker;

import com.github.dockerjava.api.DockerClient;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author wxy
 */
@Data
@AllArgsConstructor
public class ContainerStopCommand {
    private final String id;
    private final DockerClient dockerClient;

    public static ContainerStopCommand create(String id, DockerClient dockerClient) {
        return new ContainerStopCommand(id, dockerClient);
    }
}
