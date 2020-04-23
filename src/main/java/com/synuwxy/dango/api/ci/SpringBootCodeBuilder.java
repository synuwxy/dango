package com.synuwxy.dango.api.ci;

import com.alibaba.fastjson.JSONObject;
import com.synuwxy.dango.api.git.GitService;
import com.synuwxy.dango.api.git.model.GitCloneParam;
import com.synuwxy.dango.api.script.ScriptBuilder;
import com.synuwxy.dango.common.config.CommonConfig;
import com.synuwxy.dango.common.utils.FileUtil;
import com.synuwxy.dango.common.utils.GitUtil;
import com.synuwxy.dango.common.utils.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;

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
    public File build(GitCloneParam gitCloneParam, String workspace) throws IOException, InterruptedException {
        FileUtil.mkdir(workspace);
        workspace = cloneSource(gitCloneParam, workspace);
        return scriptBuilder.build(workspace);
    }

    private String cloneSource(GitCloneParam gitCloneParam, String workspace) throws IOException, InterruptedException {
        log.info("clone 代码 gitCloneParam: {}", JSONObject.toJSONString(gitCloneParam));
        boolean status = gitService.clone(gitCloneParam, workspace);
        if (!status) {
            throw new RuntimeException("clone 失败");
        }
        workspace += "/" + GitUtil.getRepositoryName(gitCloneParam.getRepository());
        return workspace;
    }

    @Override
    public void cleanBuild(GitCloneParam gitCloneParam, String target) throws IOException, InterruptedException {
        String workspace = this.CODE_BUILD_WORKSPACE + "/" + UUIDUtil.generatorId();
        File product = build(gitCloneParam, workspace);
        FileCopyUtils.copy(product, new File(target + "/" + product.getName()));
        FileUtil.delete(workspace);
    }

    @Override
    public File customBuild(GitCloneParam gitCloneParam, String command, String productName, String productPath, String workspace) throws IOException, InterruptedException {
        FileUtil.mkdir(workspace);
        workspace = cloneSource(gitCloneParam, workspace);
        return scriptBuilder.customBuild(command, productName, productPath, workspace);
    }

    @Override
    public void customCleanBuild(GitCloneParam gitCloneParam, String command, String productName, String productPath, String target) throws IOException, InterruptedException {
        String workspace = this.CODE_BUILD_WORKSPACE + "/" + UUIDUtil.generatorId();
        File product = customBuild(gitCloneParam, command, productName, productPath, workspace);
        FileCopyUtils.copy(product, new File(target + "/" + product.getName()));
        FileUtil.delete(workspace);
    }
}
