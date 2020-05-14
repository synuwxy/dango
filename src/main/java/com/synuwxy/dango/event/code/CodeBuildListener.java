package com.synuwxy.dango.event.code;

import com.alibaba.fastjson.JSONObject;
import com.synuwxy.dango.aggreate.git.GitRepo;
import com.synuwxy.dango.aggreate.script.Script;
import com.synuwxy.dango.service.code.CodeTypeFinder;
import com.synuwxy.dango.service.code.model.CodeType;
import com.synuwxy.dango.service.git.model.GitCloneParam;
import com.synuwxy.dango.api.ci.model.DockerCiCommand;
import com.synuwxy.dango.common.utils.GitUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;

/**
 * @author wxy
 */
@Component
@Slf4j
public class CodeBuildListener {

    private final CodeTypeFinder codeTypeFinder;

    public CodeBuildListener(CodeTypeFinder codeTypeFinder) {
        this.codeTypeFinder = codeTypeFinder;
    }

    @EventListener(condition = "#codeBuildEvent.source instanceof T(com.synuwxy.dango.api.ci.model.DockerCiCommand)")
    public void handle(CodeBuildEvent codeBuildEvent) throws IOException, InterruptedException {
        DockerCiCommand dockerCiCommand = (DockerCiCommand)codeBuildEvent.getSource();
        GitCloneParam gitCloneParam = dockerCiCommand.getGitCloneParam();
        String workspace = cloneSource(gitCloneParam, codeBuildEvent.getWorkspace());
        File product = build(workspace);
        FileCopyUtils.copy(product, new File(codeBuildEvent.getTargetPath() + "/" + product.getName()));
    }

    private String cloneSource(GitCloneParam gitCloneParam, String workspace) throws IOException, InterruptedException {
        log.info("clone 代码 gitCloneParam: {}", JSONObject.toJSONString(gitCloneParam));
        GitRepo gitRepo = GitRepo.builder()
                .repository(gitCloneParam.getRepository())
                .branch(gitCloneParam.getBranch())
                .username(gitCloneParam.getUsername())
                .password(gitCloneParam.getPassword())
                .build();
        boolean status = gitRepo.clone(workspace);
        if (!status) {
            throw new RuntimeException("clone 失败");
        }
        workspace += "/" + GitUtil.getRepositoryName(gitCloneParam.getRepository());
        return workspace;
    }

    private File build(String workspace) {
        log.info("开始构建");
        CodeType codeType = codeTypeFinder.findCodeType(workspace);
        String buildCommand = codeType.getBuildCommand();
        Script script = Script.builder().command(buildCommand).build();
        if (!script.run(workspace)) {
            throw new RuntimeException("构建失败 type: SpringBoot");
        }

        log.info("构建完成");
        log.info("搜索输出文件");
        String productParentPath = codeType.getProductParentPath();
        String productName = codeType.getProductName();
        File product = new File(productParentPath + "/" + productName);
        if (!product.exists()) {
            throw new RuntimeException("编译产出物不存在");
        }
        return product;
    }
}
