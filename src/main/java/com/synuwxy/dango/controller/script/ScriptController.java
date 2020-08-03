package com.synuwxy.dango.controller.script;

import com.synuwxy.dango.service.script.ScriptService;
import com.synuwxy.dango.service.script.model.ScriptExecuteParam;
import com.synuwxy.dango.common.ResultObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


/**
 * @author wxy
 */
@RestController
@RequestMapping("/script")
public class ScriptController {

    private final ScriptService scriptService;

    public ScriptController(ScriptService scriptService) {
        this.scriptService = scriptService;
    }

    @PostMapping("/exec")
    public ResultObject exec(ScriptExecuteParam scriptExecuteParam) throws IOException {
        return scriptService.exec(scriptExecuteParam)?ResultObject.success("执行成功"):ResultObject.error("执行失败");
    }
}
