package com.synuwxy.dango.api.service.docker;

import com.synuwxy.dango.aggreate.docker.dockerfile.Dockerfile;
import com.synuwxy.dango.aggreate.docker.image.DockerImage;
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

    @EventListener
    public void handle(GeneratorDockerfileEvent generatorDockerfileEvent) throws IOException {
        Dockerfile dockerfile = Dockerfile.builder().name(generatorDockerfileEvent.getName()).build();
        dockerfile.generatorDockerfile(generatorDockerfileEvent.getWorkspace(), generatorDockerfileEvent.getTargetPath());
    }
}
