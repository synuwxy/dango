package com.synuwxy.dango.api.service.code;

import com.synuwxy.dango.api.git.model.GitCloneParam;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author wxy
 */
@Getter
public class CodeBuildEvent extends ApplicationEvent {

    private final GitCloneParam gitCloneParam;
    private final String workspace;
    private final String targetPath;

    public CodeBuildEvent(Object source, GitCloneParam gitCloneParam, String workspace, String targetPath) {
        super(source);
        this.gitCloneParam = gitCloneParam;
        this.workspace = workspace;
        this.targetPath = targetPath;
    }
}
