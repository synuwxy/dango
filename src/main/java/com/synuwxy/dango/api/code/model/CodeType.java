package com.synuwxy.dango.api.code.model;

import lombok.Getter;

/**
 * @author wxy
 */
@Getter
public class CodeType {

    private final String name;

    private final String productParentPath;

    private final String productName;

    private final String productExtensionName;

    public CodeType(String name, String productParentPath, String productName, String productExtensionName) {
        this.name = name;
        this.productParentPath = productParentPath;
        this.productName = productName;
        this.productExtensionName = productExtensionName;
    }
}
