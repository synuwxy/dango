package com.synuwxy.dango.ddd.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author wxy
 */
public class StopEvent extends ApplicationEvent {
    public StopEvent(Object source) {
        super(source);
    }
}
