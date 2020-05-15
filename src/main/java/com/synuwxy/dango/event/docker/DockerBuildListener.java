package com.synuwxy.dango.event.docker;

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

    @EventListener(condition = "#dockerBuildEvent.source instanceof T(com.synuwxy.dango.event.docker.DockerBuildCommand)")
    public void defaultHandle(DockerBuildEvent dockerBuildEvent) {
        log.info("[DockerBuildEvent] defaultHandle start");
        DockerBuildCommand dockerBuildCommand = (DockerBuildCommand)dockerBuildEvent.getSource();
        DockerImage dockerImage = DockerImage.builder()
                .dockerClient(dockerBuildCommand.getDockerClient())
                .imageFullName(dockerBuildCommand.getImageFullName())
                .build();
        log.info("[DockerBuildEvent] defaultHandle build");
        dockerImage.build(dockerBuildCommand.getWorkspace());
        log.info("[DockerBuildEvent] defaultHandle end");
    }
}
