package com.synuwxy.dango.event.implement.code;

import com.synuwxy.dango.service.git.model.GitCloneParam;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author wxy
 */
@Data
@AllArgsConstructor
public class CodeDefaultBuildCommand {

    private GitCloneParam gitCloneParam;
    private String workspace;
    private String targetPath;

    public static CodeDefaultBuildCommand create(GitCloneParam gitCloneParam, String workspace, String targetPath) {
        return new CodeDefaultBuildCommand(gitCloneParam, workspace, targetPath);
    }
}
