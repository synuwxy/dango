package com.synuwxy.dango.event.docker;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author wxy
 */
@Data
@AllArgsConstructor
public class GeneratorDockerfileCommand {

    private String type;
    private String workspace;
    private String targetPath;

    public static GeneratorDockerfileCommand create(String type, String workspace, String targetPath) {
        return new GeneratorDockerfileCommand(type, workspace, targetPath);
    }
}
