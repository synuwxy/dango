package com.synuwxy.dango.service.script;

import com.synuwxy.dango.service.script.model.ScriptExecuteParam;
import com.synuwxy.dango.common.config.CommonConfig;
import com.synuwxy.dango.common.utils.FileUtil;
import com.synuwxy.dango.common.utils.UUIDUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author wxy
 */
@Service
public class ScriptServiceImpl implements ScriptService {
    private final String SCRIPT_WORKSPACE;

    private final ScriptHandler scriptHandler;

    public ScriptServiceImpl(CommonConfig commonConfig, ScriptHandler scriptHandler) {
        SCRIPT_WORKSPACE = commonConfig.getWorkspacePrefix() + "/script/workspace";
        this.scriptHandler = scriptHandler;
    }

    @Override
    public boolean exec(ScriptExecuteParam scriptExecuteParam) throws IOException {
        String workspace = SCRIPT_WORKSPACE + "/" + UUIDUtil.generatorId();
        FileUtil.mkdir(workspace);
        boolean status = scriptHandler.run(workspace, scriptExecuteParam.getCommand());
        FileUtil.delete(workspace);
        return status;
    }
}
