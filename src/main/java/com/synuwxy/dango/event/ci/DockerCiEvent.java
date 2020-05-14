package com.synuwxy.dango.event.ci;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author wxy
 */
@Getter
public class DockerCiEvent extends ApplicationEvent {

    public DockerCiEvent(Object source) {
        super(source);
    }
}
