package com.synuwxy.dango.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author wxy
 */
@Getter
public class BuildEvent extends ApplicationEvent {
    public BuildEvent(Object source) {
        super(source);
    }
}
