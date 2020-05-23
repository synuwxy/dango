package com.synuwxy.dango.event.implement.docker;

import com.github.dockerjava.api.DockerClient;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author wxy
 */
@Data
@AllArgsConstructor
public class ContainerCleanCommand {
    private final String name;
    private final DockerClient dockerClient;
    public static ContainerCleanCommand create(String name, DockerClient dockerClient) {
        return new ContainerCleanCommand(name, dockerClient);
    }
}
