package com.synuwxy.dango.api.docker.model;

import lombok.Data;

/**
 * @author wxy
 */
@Data
public class ContainerPort {
    private int insidePort;
    private int outsidePort;

    public ContainerPort(int insidePort, int outsidePort) {
        this.insidePort = insidePort;
        this.outsidePort = outsidePort;
    }

    public String generator() {
        return this.outsidePort + ":" + this.insidePort;
    }
}
