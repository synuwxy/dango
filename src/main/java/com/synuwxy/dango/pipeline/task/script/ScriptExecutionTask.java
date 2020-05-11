package com.synuwxy.dango.pipeline.task.script;

import com.alibaba.fastjson.JSONObject;
import com.synuwxy.dango.aggreate.script.Script;
import com.synuwxy.dango.common.utils.FileUtil;
import com.synuwxy.dango.pipeline.PipelineTask;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wxy
 */
@Slf4j
@AllArgsConstructor
public class ScriptExecutionTask implements PipelineTask {

    private final Script script;
    private final String workspace;

    @Override
    public void process() {
        script.run(workspace);
    }

    @Override
    public void before() {
        if (null == script.getCommand()) {
            throw new RuntimeException("[ScriptExecutionTask 参数校验] command: null");
        }
        if (null == workspace) {
            throw new RuntimeException("[ScriptExecutionTask 参数校验] workspace: null");
        }

        FileUtil.mkdir(workspace);
        log.info("[ScriptExecutionTask] 开始执行");
    }

    public static ScriptExecutionTask create(String command, String workspace) {
        Script script = Script.builder().command(command).build();
        return new ScriptExecutionTask(script, workspace);
    }

}
