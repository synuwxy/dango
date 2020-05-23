package com.synuwxy.dango.event.implement.docker;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author wxy
 */
@Data
@AllArgsConstructor
public class GeneratorDockerfileCommand {

    private final String type;
    private final String workspace;
    private final String targetPath;

    public static GeneratorDockerfileCommand create(String type, String workspace, String targetPath) {
        return new GeneratorDockerfileCommand(type, workspace, targetPath);
    }
}
