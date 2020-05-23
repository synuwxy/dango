package com.synuwxy.dango.event.implement.docker;

import com.synuwxy.dango.aggreate.docker.dockerfile.Dockerfile;
import com.synuwxy.dango.event.FileOperationsEvent;
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

    @EventListener(condition = "#fileOperationsEvent.source instanceof T(com.synuwxy.dango.event.implement.docker.GeneratorDockerfileCommand)")
    public void defaultHandle(FileOperationsEvent fileOperationsEvent) throws IOException {
        log.info("[FileOperationsEvent] GeneratorDockerfile start");
        GeneratorDockerfileCommand generatorDockerfileCommand = (GeneratorDockerfileCommand) fileOperationsEvent.getSource();
        Dockerfile dockerfile = Dockerfile.builder().type(generatorDockerfileCommand.getType()).build();
        dockerfile.generatorDockerfile(generatorDockerfileCommand.getWorkspace(), generatorDockerfileCommand.getTargetPath());
        log.info("[FileOperationsEvent] GeneratorDockerfile end");
    }
}
