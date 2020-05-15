package com.synuwxy.dango.event.docker;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author wxy
 */
@Getter
public class GeneratorDockerfileEvent extends ApplicationEvent {
    public GeneratorDockerfileEvent(Object source) {
        super(source);
    }
}
