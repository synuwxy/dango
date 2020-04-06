package com.synuwxy.dango.api.git.github.model.hook;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class HookInfoDto {
    List<HookInfoModel> hooks;
}
