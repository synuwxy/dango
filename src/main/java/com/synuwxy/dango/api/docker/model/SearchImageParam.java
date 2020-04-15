package com.synuwxy.dango.api.docker.model;

import lombok.Data;

import java.util.List;

/**
 * @author wxy
 */
@Data
public class SearchImageParam {
    private String imageName;
    private List<String> labels;
}
