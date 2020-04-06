package com.synuwxy.dango.common.utils;

import java.util.UUID;

/**
 * @author wxy
 */
public class UUIDUtil {

    public static String generatorId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
