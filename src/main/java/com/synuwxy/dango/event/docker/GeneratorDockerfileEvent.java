package com.synuwxy.dango.event.docker;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author wxy
 */
@Getter
public class GeneratorDockerfileEvent extends ApplicationEvent {

    private final String workspace;
    private final String targetPath;

    public GeneratorDockerfileEvent(Object source, String workspace, String targetPath) {
        super(source);
        this.workspace = workspace;
        this.targetPath = targetPath;
    }
}
