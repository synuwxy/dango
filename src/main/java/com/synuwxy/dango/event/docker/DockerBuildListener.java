package com.synuwxy.dango.event.docker;

import com.synuwxy.dango.aggreate.docker.image.DockerImage;
import com.synuwxy.dango.api.ci.model.DockerCiCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author wxy
 */
@Component
@Slf4j
public class DockerBuildListener {

    @EventListener(condition = "#dockerBuildEvent.source instanceof T(com.synuwxy.dango.api.ci.model.DockerCiCommand)")
    public void handle(DockerBuildEvent dockerBuildEvent) {
        DockerCiCommand dockerCiCommand = (DockerCiCommand)dockerBuildEvent.getSource();
        DockerImage dockerImage = DockerImage.builder()
                .dockerClient(dockerCiCommand.getDockerClient())
                .imageFullName(dockerCiCommand.getImageFullName())
                .build();
        dockerImage.build(dockerBuildEvent.getWorkspace());
    }
}
