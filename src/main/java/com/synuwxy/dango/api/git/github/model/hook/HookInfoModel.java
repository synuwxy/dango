package com.synuwxy.dango.api.git.github.model.hook;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author wxy
 */
@AllArgsConstructor
@Data
public class HookInfoModel {
    private String url;
    private String desc;
}
