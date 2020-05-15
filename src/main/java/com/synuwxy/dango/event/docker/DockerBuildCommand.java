package com.synuwxy.dango.event.docker;

import com.github.dockerjava.api.DockerClient;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author wxy
 */
@Data
@AllArgsConstructor
public class DockerBuildCommand {

    private DockerClient dockerClient;

    private String imageFullName;

    private String workspace;

    public static DockerBuildCommand create(DockerClient dockerClient, String imageFullName, String workspace) {
        return new DockerBuildCommand(dockerClient, imageFullName, workspace);
    }
}
