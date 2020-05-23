package com.synuwxy.dango.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author wxy
 */
public class DeployEvent extends ApplicationEvent {
    public DeployEvent(Object source) {
        super(source);
    }
}
