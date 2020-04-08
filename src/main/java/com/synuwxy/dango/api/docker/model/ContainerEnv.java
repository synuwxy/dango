package com.synuwxy.dango.api.docker.model;

import lombok.Data;

/**
 * @author wxy
 */
@Data
public class ContainerEnv {
    private String key;
    private String value;

    public ContainerEnv(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String generator() {
        return this.key + "=" + this.value;
    }
}
