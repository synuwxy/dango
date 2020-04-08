package com.synuwxy.dango.api.docker.model;

import lombok.Data;

/**
 * @author wxy
 */
@Data
public class ContainerVolume {
    private String insideVolume;
    private String outsideVolume;

    public ContainerVolume(String insideVolume, String outsideVolume) {
        this.insideVolume = insideVolume;
        this.outsideVolume = outsideVolume;
    }

    public String generator() {
        return this.outsideVolume + ":" + this.insideVolume;
    }
}
