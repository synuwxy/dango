package com.synuwxy.dango.api.docker.model;

import lombok.Data;

import java.util.List;

/**
 * @author wxy
 */
@Data
public class SearchContainerParam {
    private String containerId;
    private String containerName;
    private List<String> labels;
    private List<String> status;
}
