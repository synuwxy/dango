package com.synuwxy.dango.api.script.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author wxy
 */
@Data
public class ScriptExecuteParam {

    @NotNull
    private String command;
}
