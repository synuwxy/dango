package com.synuwxy.dango.event.code;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author wxy
 */
@Getter
public class CodeBuildEvent extends ApplicationEvent {

    private final String workspace;
    private final String targetPath;

    public CodeBuildEvent(Object source, String workspace, String targetPath) {
        super(source);
        this.workspace = workspace;
        this.targetPath = targetPath;
    }
}
