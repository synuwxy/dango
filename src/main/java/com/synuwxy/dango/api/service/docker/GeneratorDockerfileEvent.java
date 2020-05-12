package com.synuwxy.dango.api.service.docker;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author wxy
 */
@Getter
public class GeneratorDockerfileEvent extends ApplicationEvent {

    private final String name;
    private final String workspace;
    private final String targetPath;

    public GeneratorDockerfileEvent(Object source, String name, String workspace, String targetPath) {
        super(source);
        this.name = name;
        this.workspace = workspace;
        this.targetPath = targetPath;
    }
}
