package com.synuwxy.dango.event.docker;

import com.synuwxy.dango.aggreate.docker.dockerfile.Dockerfile;
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

    @EventListener(condition = "#generatorDockerfileEvent.source instanceof T(com.synuwxy.dango.event.docker.GeneratorDockerfileCommand)")
    public void defaultHandle(GeneratorDockerfileEvent generatorDockerfileEvent) throws IOException {
        log.info("[GeneratorDockerfileEvent] defaultHandle start");
        GeneratorDockerfileCommand generatorDockerfileCommand = (GeneratorDockerfileCommand)generatorDockerfileEvent.getSource();
        Dockerfile dockerfile = Dockerfile.builder().type(generatorDockerfileCommand.getType()).build();
        dockerfile.generatorDockerfile(generatorDockerfileCommand.getWorkspace(), generatorDockerfileCommand.getTargetPath());
        log.info("[GeneratorDockerfileEvent] defaultHandle end");
    }
}
