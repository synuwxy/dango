package com.synuwxy.dango.ddd.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author wxy
 */
public class DeployEvent extends ApplicationEvent {
    public DeployEvent(Object source) {
        super(source);
    }
}
