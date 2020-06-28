package com.synuwxy.dango.ddd.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author wxy
 */
public class CleanEvent extends ApplicationEvent {
    public CleanEvent(Object source) {
        super(source);
    }
}
