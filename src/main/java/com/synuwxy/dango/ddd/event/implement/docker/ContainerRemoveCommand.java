package com.synuwxy.dango.ddd.event.implement.docker;

import com.github.dockerjava.api.DockerClient;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author wxy
 */
@Data
@AllArgsConstructor
public class ContainerRemoveCommand {
    private final String id;
    private final DockerClient dockerClient;

    public static ContainerRemoveCommand create(String id, DockerClient dockerClient) {
        return new ContainerRemoveCommand(id, dockerClient);
    }
}
