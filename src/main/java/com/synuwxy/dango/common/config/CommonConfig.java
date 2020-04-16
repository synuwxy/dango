package com.synuwxy.dango.common.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author wxy
 */
@Data
@Component
public class CommonConfig {

    @Value("${workspace.prefix}")
    private String workspacePrefix;

    @Value("${docker.dockerfile.workspace}")
    private String dockerfileWorkspace;
}
