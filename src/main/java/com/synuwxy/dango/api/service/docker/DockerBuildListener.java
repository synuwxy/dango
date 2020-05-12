package com.synuwxy.dango.api.service.docker;

import com.synuwxy.dango.aggreate.docker.image.DockerImage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author wxy
 */
@Component
@Slf4j
public class DockerBuildListener {

    @EventListener
    public void handle(DockerBuildEvent dockerBuildEvent) {
        DockerImage dockerImage = DockerImage.builder()
                .dockerClient(dockerBuildEvent.getDockerClient())
                .imageFullName(dockerBuildEvent.getImageFullName())
                .build();
        dockerImage.build(dockerBuildEvent.getWorkspace());
    }
}
