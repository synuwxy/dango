package com.synuwxy.dango.api.service.docker;

import com.github.dockerjava.api.DockerClient;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author wxy
 */
@Getter
public class DockerBuildEvent extends ApplicationEvent {

    private final DockerClient dockerClient;
    private final String imageFullName;
    private final String workspace;


    public DockerBuildEvent(Object source, DockerClient dockerClient, String imageFullName, String workspace) {
        super(source);
        this.dockerClient = dockerClient;
        this.imageFullName = imageFullName;
        this.workspace = workspace;
    }
}
