package com.synuwxy.dango.api.git.github.model.hook;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author wxy
 */
@AllArgsConstructor
@Data
public class HookInfoDto {
    List<HookInfoModel> hooks;
}
