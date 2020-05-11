package com.synuwxy.dango.pipeline.task.git;

import com.synuwxy.dango.aggreate.git.GitRepo;
import com.synuwxy.dango.api.git.model.GitCloneParam;
import com.synuwxy.dango.common.utils.FileUtil;
import com.synuwxy.dango.common.utils.GitUtil;
import com.synuwxy.dango.pipeline.PipelineParameterVerification;
import com.synuwxy.dango.pipeline.PipelineTask;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author wxy
 */
@Slf4j
@AllArgsConstructor
public class GitCloneTask implements PipelineTask {

    private final GitRepo gitRepo;
    private final String workspace;

    @Override
    public void process() throws Exception {
        gitRepo.clone(workspace);
    }

    @Override
    public void before() {
        PipelineParameterVerification pipelineParameterVerification = PipelineParameterVerification.create();
        if (null == gitRepo.getRepository()) {
            pipelineParameterVerification.add("[GitCloneTask 参数校验] repository: null");
        }
        if (null == workspace) {
            pipelineParameterVerification.add("[GitCloneTask 参数校验] workspace:null");
        }
        pipelineParameterVerification.verify();
        FileUtil.mkdir(workspace);
        log.info("[GitCloneTask] 开始执行");
    }

    @Override
    public void compensate() {
        if (GitUtil.repoExists(workspace, gitRepo.getRepository())) {
            String repoName = GitUtil.getRepositoryName(gitRepo.getRepository());
            try {
                FileUtil.delete(workspace + "/" + repoName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static GitCloneTask create(String workspace, GitCloneParam gitCloneParam) {
        GitRepo gitRepo = GitRepo.builder()
                .repository(gitCloneParam.getRepository())
                .branch(gitCloneParam.getBranch())
                .username(gitCloneParam.getUsername())
                .password(gitCloneParam.getPassword())
                .build();
        return new GitCloneTask(gitRepo, workspace);
    }
}
