package com.synuwxy.dango.event.docker;

import com.synuwxy.dango.aggreate.docker.dockerfile.Dockerfile;
import com.synuwxy.dango.api.ci.model.DockerCiCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author wxy
 */
@Component
@Slf4j
public class GeneratorDockerfileListener {

    @EventListener(condition = "#generatorDockerfileEvent.source instanceof T(com.synuwxy.dango.api.ci.model.DockerCiCommand)")
    public void handle(GeneratorDockerfileEvent generatorDockerfileEvent) throws IOException {
        DockerCiCommand dockerCiCommand = (DockerCiCommand)generatorDockerfileEvent.getSource();
        Dockerfile dockerfile = Dockerfile.builder().type(dockerCiCommand.getDockerfileType()).build();
        dockerfile.generatorDockerfile(generatorDockerfileEvent.getWorkspace(), generatorDockerfileEvent.getTargetPath());
    }
}
