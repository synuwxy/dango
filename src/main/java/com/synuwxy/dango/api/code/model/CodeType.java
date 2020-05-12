package com.synuwxy.dango.api.code.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wxy
 */
@Getter
@AllArgsConstructor
public class CodeType {

    private final String name;

    private final String productParentPath;

    private final String productName;

    private final String productExtensionName;

    private final String buildCommand;

}
