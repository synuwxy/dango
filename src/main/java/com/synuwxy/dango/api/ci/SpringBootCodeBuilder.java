package com.synuwxy.dango.api.ci;

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

    public SpringBootCodeBuilder(ScriptBuilder scriptBuilder, CommonConfig commonConfig) {
        this.scriptBuilder = scriptBuilder;
        this.CODE_BUILD_WORKSPACE = commonConfig.getWorkspacePrefix() + "/code/workspace";
    }

    @Override
    public File build(String repo, String branch, String type, String workspace) throws Exception {
        FileUtil.mkdir(workspace);
        log.info("clone 代码 repo: {}, branch: {}, type: {}", repo, branch, type);
        boolean status = GitUtil.clone(repo, branch, workspace);
        if (!status) {
            throw new RuntimeException("clone 失败");
        }
        workspace += "/" + GitUtil.getRepositoryName(repo);
        return scriptBuilder.build(workspace);
    }

    @Override
    public void cleanBuild(String repo, String branch, String type, String target) throws Exception {
        String workspace = this.CODE_BUILD_WORKSPACE + "/" + UUIDUtil.generatorId();
        File jar = build(repo, branch, type, workspace);
        FileCopyUtils.copy(jar, new File(target + "/" + jar.getName()));
        FileUtil.delete(workspace);
    }
}
