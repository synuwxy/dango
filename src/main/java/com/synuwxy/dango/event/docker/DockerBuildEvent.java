package com.synuwxy.dango.event.docker;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author wxy
 */
@Getter
public class DockerBuildEvent extends ApplicationEvent {
    public DockerBuildEvent(Object source) {
        super(source);
    }
}
