package com.synuwxy.dango.api.ci;

import com.alibaba.fastjson.JSONObject;
import com.synuwxy.dango.api.git.model.GitCloneParam;
import com.synuwxy.dango.api.git.GitService;
import com.synuwxy.dango.api.script.ScriptBuilder;
import com.synuwxy.dango.common.config.CommonConfig;
import com.synuwxy.dango.common.utils.FileUtil;
import com.synuwxy.dango.common.utils.GitUtil;
import com.synuwxy.dango.common.utils.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.File;

/**
 * @author wxy
 */
@Slf4j
@Service
public class SpringBootCodeBuilder implements CodeBuilder {

    private final String CODE_BUILD_WORKSPACE;

    private final ScriptBuilder scriptBuilder;

    private final GitService gitService;

    public SpringBootCodeBuilder(ScriptBuilder scriptBuilder, CommonConfig commonConfig, GitService gitService) {
        this.scriptBuilder = scriptBuilder;
        this.CODE_BUILD_WORKSPACE = commonConfig.getWorkspacePrefix() + "/code/workspace";
        this.gitService = gitService;
    }

    @Override
    public File build(GitCloneParam gitCloneParam, String workspace) throws Exception {
        FileUtil.mkdir(workspace);
        log.info("clone 代码 gitCloneParam: {}", JSONObject.toJSONString(gitCloneParam));
        boolean status = gitService.clone(gitCloneParam, workspace);
        if (!status) {
            throw new RuntimeException("clone 失败");
        }
        workspace += "/" + GitUtil.getRepositoryName(gitCloneParam.getRepository());
        return scriptBuilder.build(workspace);
    }

    @Override
    public void cleanBuild(GitCloneParam gitCloneParam, String type, String target) throws Exception {
        String workspace = this.CODE_BUILD_WORKSPACE + "/" + UUIDUtil.generatorId();
        File jar = build(gitCloneParam, workspace);
        FileCopyUtils.copy(jar, new File(target + "/" + jar.getName()));
        FileUtil.delete(workspace);
    }
}
