package com.synuwxy.dango.ddd.aggreate.docker.container;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wxy
 */
@Getter
@AllArgsConstructor
public class DockerContainerVolume {
    private final String insidePath;
    private final String outsidePath;
    public String generator() {
        return this.outsidePath + ":" + this.insidePath;
    }
}
