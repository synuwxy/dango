package com.synuwxy.dango.ddd.aggreate.docker.container;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * @author wxy
 */
@Getter
@AllArgsConstructor
public class DockerContainerPort {
    private final int insidePort;
    private final int outsidePort;
}
