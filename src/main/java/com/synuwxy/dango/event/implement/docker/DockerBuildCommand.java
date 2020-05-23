package com.synuwxy.dango.event.implement.docker;

import com.github.dockerjava.api.DockerClient;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author wxy
 */
@Data
@AllArgsConstructor
public class DockerBuildCommand {

    private final DockerClient dockerClient;

    private final String imageFullName;

    private final String workspace;

    public static DockerBuildCommand create(DockerClient dockerClient, String imageFullName, String workspace) {
        return new DockerBuildCommand(dockerClient, imageFullName, workspace);
    }
}
