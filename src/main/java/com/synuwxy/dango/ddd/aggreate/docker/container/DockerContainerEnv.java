package com.synuwxy.dango.ddd.aggreate.docker.container;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wxy
 */
@Getter
@AllArgsConstructor
public class DockerContainerEnv {

    private final String key;
    private final String value;
    public String generator() {
        return this.key + "=" + this.value;
    }
}
