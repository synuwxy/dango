package com.synuwxy.dango.event.implement.docker;

import com.synuwxy.dango.aggreate.docker.image.DockerImage;
import com.synuwxy.dango.event.BuildEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author wxy
 */
@Component
@Slf4j
public class DockerBuildListener {

    @EventListener(condition = "#buildEvent.source instanceof T(com.synuwxy.dango.event.implement.docker.DockerBuildCommand)")
    public void defaultHandle(BuildEvent buildEvent) {
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
