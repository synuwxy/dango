package com.synuwxy.dango.api.service.ci;

import com.github.dockerjava.api.DockerClient;
import com.synuwxy.dango.api.ci.model.DockerBuildParam;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author wxy
 */
@Getter
public class DockerCiBuildEvent extends ApplicationEvent {

    private final DockerBuildParam dockerBuildParam;
    private final DockerClient dockerClient;

    public DockerCiBuildEvent(Object source, DockerBuildParam dockerBuildParam, DockerClient dockerClient) {
        super(source);
        this.dockerBuildParam = dockerBuildParam;
        this.dockerClient = dockerClient;
    }
}
