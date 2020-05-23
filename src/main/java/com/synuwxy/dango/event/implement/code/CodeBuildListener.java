package com.synuwxy.dango.event.implement.code;

import com.alibaba.fastjson.JSONObject;
import com.synuwxy.dango.aggreate.git.GitRepo;
import com.synuwxy.dango.aggreate.script.Script;
import com.synuwxy.dango.common.utils.GitUtil;
import com.synuwxy.dango.event.BuildEvent;
import com.synuwxy.dango.service.code.CodeTypeFinder;
import com.synuwxy.dango.service.code.model.CodeType;
import com.synuwxy.dango.service.git.model.GitCloneParam;
import com.synuwxy.dango.service.script.ScriptHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author wxy
 */
@Component
@Slf4j
public class CodeBuildListener {

    private final CodeTypeFinder codeTypeFinder;
    private final ScriptHandler scriptHandler;

    public CodeBuildListener(CodeTypeFinder codeTypeFinder, ScriptHandler scriptHandler) {
        this.codeTypeFinder = codeTypeFinder;
        this.scriptHandler = scriptHandler;
    }

    @EventListener(condition = "#buildEvent.source instanceof T(com.synuwxy.dango.event.implement.code.CodeDefaultBuildCommand)")
    public void defaultHandle(BuildEvent buildEvent) throws IOException, InterruptedException {
        log.info("[CodeBuildEvent] defaultHandle start");
        CodeDefaultBuildCommand codeDefaultBuildCommand = (CodeDefaultBuildCommand)buildEvent.getSource();
        GitCloneParam gitCloneParam = codeDefaultBuildCommand.getGitCloneParam();

        String workspace = cloneSource(gitCloneParam, codeDefaultBuildCommand.getWorkspace());
        File product = build(workspace);
        FileCopyUtils.copy(product, new File(codeDefaultBuildCommand.getTargetPath() + "/" + product.getName()));
        log.info("[CodeBuildEvent] end");
    }

    private String cloneSource(GitCloneParam gitCloneParam, String workspace) throws IOException, InterruptedException {
        log.info("[CodeBuildEvent] clone 代码 gitCloneParam: {}", JSONObject.toJSONString(gitCloneParam));
        GitRepo gitRepo = GitRepo.builder()
                .repository(gitCloneParam.getRepository())
                .branch(gitCloneParam.getBranch())
                .username(gitCloneParam.getUsername())
                .password(gitCloneParam.getPassword())
                .build();
        boolean status = gitRepo.clone(workspace);
        if (!status) {
            throw new RuntimeException("[CodeBuildEvent] clone 失败");
        }
        workspace += "/" + GitUtil.getRepositoryName(gitCloneParam.getRepository());
        return workspace;
    }

    private File build(String workspace) {
        log.info("[CodeBuildEvent] 开始构建");
        CodeType codeType = codeTypeFinder.findCodeType(workspace);
        String buildCommand = codeType.getBuildCommand();
        Script script = Script.builder().command(buildCommand).build();
        if (!script.run(workspace)) {
            throw new RuntimeException("[CodeBuildEvent] 构建失败");
        }
        log.info("[CodeBuildEvent] 构建完成");
        String productParentPath = codeType.getProductParentPath();
        String productName = codeType.getProductName();
        String productPath = productParentPath + "/" + productName;
        log.info("[CodeBuildEvent] 搜索输出文件 {}", productPath);
        File product = new File(productPath);
        if (!product.exists()) {
            throw new RuntimeException("[CodeBuildEvent] 编译产出物不存在");
        }
        return product;
    }

    @EventListener(condition = "#buildEvent.source instanceof T(com.synuwxy.dango.event.implement.code.CodeCustomBuildCommand)")
    public void customHandle(BuildEvent buildEvent) throws IOException, InterruptedException {
        log.info("[CodeBuildEvent] customHandle start");
        CodeCustomBuildCommand codeCustomBuildCommand = (CodeCustomBuildCommand)buildEvent.getSource();
        GitCloneParam gitCloneParam = codeCustomBuildCommand.getGitCloneParam();
        String command = codeCustomBuildCommand.getCommand();
        String productPath = codeCustomBuildCommand.getProductPath();
        List<String> extraPaths = codeCustomBuildCommand.getExtraPaths();
        String targetPath = codeCustomBuildCommand.getTargetPath();

        String workspace = cloneSource(gitCloneParam, codeCustomBuildCommand.getWorkspace());
        log.info("[CodeBuildEvent] 自定义构建");
        File product = customBuild(workspace, command, productPath);
        String target = targetPath + "/" + product.getName();
        log.info("[CodeBuildEvent] 复制编译产出物 target: {}", targetPath);
        FileCopyUtils.copy(product, new File(target));
        log.info("[CodeBuildEvent] 复制额外文件 extraPaths: {}", JSONObject.toJSONString(extraPaths));
        moveExtraFiles(extraPaths, workspace, targetPath);
        log.info("[CodeBuildEvent] end");
    }

    private File customBuild(String workspace, String command, String productPath) {
        if (!scriptHandler.run(workspace, command)) {
            throw new RuntimeException("[CodeBuildEvent] 构建命令执行失败");
        }
        String absolutePath = workspace + "/" + productPath;
        File product = new File(absolutePath);
        if (!product.exists()) {
            throw new RuntimeException("[CodeBuildEvent] 构建物不存在，绝对路径: " + absolutePath);
        }
        return product;
    }

    private void moveExtraFiles(List<String> extraPaths, String root, String target) throws IOException {
        for (String extraPath : extraPaths) {
            String name = extraPath.substring(extraPath.lastIndexOf("/") == -1 ? 0:extraPath.lastIndexOf("/"));
            File src = new File(root + "/" + extraPath);
            if (!src.exists()) {
                continue;
            }
            log.info("[CodeBuildEvent] 复制额外文件 {} to {}", src.getPath(), target);
            if (src.isDirectory()) {
                FileUtils.copyDirectoryToDirectory(src, new File(target));
            } else {
                FileUtils.copyFile(src, new File(target + "/" + name));
            }
        }
    }
}
