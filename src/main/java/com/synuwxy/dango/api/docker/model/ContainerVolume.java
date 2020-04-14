package com.synuwxy.dango.api.docker.model;

import lombok.Data;

/**
 * @author wxy
 */
@Data
public class ContainerVolume {
    private String insidePath;
    private String outsidePath;

    public ContainerVolume(String insideVolume, String outsideVolume) {
        this.insidePath = insideVolume;
        this.outsidePath = outsideVolume;
    }

    public String generator() {
        return this.outsidePath + ":" + this.insidePath;
    }
}
