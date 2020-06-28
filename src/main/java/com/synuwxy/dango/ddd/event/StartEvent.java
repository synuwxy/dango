package com.synuwxy.dango.ddd.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author wxy
 */
public class StartEvent extends ApplicationEvent {
    public StartEvent(Object source) {
        super(source);
    }
}
