package com.synuwxy.dango.aggreate.code;

import lombok.Getter;

/**
 * @author wxy
 */
@Getter
public enum Language {
    /**
     * java
     */
    JAVA(1),
    /**
     * go
     */
    GO(2),
    /**
     * js
     */
    JAVASCRIPT(3),
    /**
     * 其他
     */
    OTHER(0);
    private final int value;
    Language(int value) {
        this.value = value;
    }
}
