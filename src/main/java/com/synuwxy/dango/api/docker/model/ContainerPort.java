package com.synuwxy.dango.api.docker.model;

import lombok.Data;

/**
 * @author wxy
 */
@Data
public class ContainerPort {
    private int insidePort;
    private int outsidePort;
}
