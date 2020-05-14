package com.synuwxy.dango.service.docker.model;

import lombok.Data;

/**
 * @author wxy
 */
@Data
public class ContainerPort {
    private int insidePort;
    private int outsidePort;
}
