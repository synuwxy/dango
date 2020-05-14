package com.synuwxy.dango.event.docker;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author wxy
 */
@Getter
public class DockerBuildEvent extends ApplicationEvent {

    private final String workspace;

    public DockerBuildEvent(Object source, String workspace) {
        super(source);
        this.workspace = workspace;
    }
}
