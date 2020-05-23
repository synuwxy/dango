package com.synuwxy.dango.event.implement.code;

import com.synuwxy.dango.service.git.model.GitCloneParam;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author wxy
 */
@Data
@AllArgsConstructor
public class CodeCustomBuildCommand {

    private GitCloneParam gitCloneParam;
    private String command;
    private String productPath;
    private List<String> extraPaths;
    private String workspace;
    private String targetPath;

    public static CodeCustomBuildCommand create(
            GitCloneParam gitCloneParam,
            String command,
            String productPath,
            List<String> extraPaths,
            String workspace,
            String targetPath) {
        return new CodeCustomBuildCommand(gitCloneParam, command, productPath, extraPaths, workspace, targetPath);
    }
}
