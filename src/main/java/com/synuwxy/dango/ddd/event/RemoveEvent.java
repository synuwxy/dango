package com.synuwxy.dango.ddd.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author wxy
 */
public class RemoveEvent extends ApplicationEvent {
    public RemoveEvent(Object source) {
        super(source);
    }
}
