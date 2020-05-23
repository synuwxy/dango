package com.synuwxy.dango.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author wxy
 */
@Getter
public class FileOperationsEvent extends ApplicationEvent {
    public FileOperationsEvent(Object source) {
        super(source);
    }
}