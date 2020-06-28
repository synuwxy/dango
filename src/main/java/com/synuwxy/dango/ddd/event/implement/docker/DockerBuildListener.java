package com.synuwxy.dango.ddd.event.implement.docker;

import com.synuwxy.dango.ddd.aggreate.docker.image.DockerImage;
import com.synuwxy.dango.ddd.event.BuildEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author wxy
 */
@Component
@Slf4j
public class DockerBuildListener {

    @EventListener(condition = "#buildEvent.source instanceof T(com.synuwxy.dango.ddd.event.implement.docker.DockerBuildCommand)")
    public void handle(BuildEvent buildEvent) {
        log.info("[BuildEvent] DockerBuild start");
        DockerBuildCommand dockerBuildCommand = (DockerBuildCommand) buildEvent.getSource();
        DockerImage dockerImage = DockerImage.builder()
                .dockerClient(dockerBuildCommand.getDockerClient())
                .imageFullName(dockerBuildCommand.getImageFullName())
                .build();
        log.info("[BuildEvent] build");
        dockerImage.build(dockerBuildCommand.getWorkspace());
        log.info("[BuildEvent] DockerBuild end");
    }
}
