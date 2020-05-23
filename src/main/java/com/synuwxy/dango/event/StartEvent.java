package com.synuwxy.dango.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author wxy
 */
public class StartEvent extends ApplicationEvent {
    public StartEvent(Object source) {
        super(source);
    }
}
