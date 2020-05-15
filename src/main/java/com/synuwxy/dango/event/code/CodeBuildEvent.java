package com.synuwxy.dango.event.code;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author wxy
 */
@Getter
public class CodeBuildEvent extends ApplicationEvent {

    public CodeBuildEvent(Object source) {
        super(source);
    }
}
