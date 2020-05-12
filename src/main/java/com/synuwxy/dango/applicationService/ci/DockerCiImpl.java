package com.synuwxy.dango.applicationService.ci;

import com.github.dockerjava.api.DockerClient;
import com.synuwxy.dango.api.ci.DockerCiService;
import com.synuwxy.dango.api.ci.model.DockerBuildParam;
import com.synuwxy.dango.api.ci.model.DockerCustomBuildParam;
import com.synuwxy.dango.api.code.CodeTypeFinder;
import com.synuwxy.dango.api.garbage.RecycleBin;
import com.synuwxy.dango.api.git.model.GitCloneParam;
import com.synuwxy.dango.common.config.CommonConfig;
import com.synuwxy.dango.common.utils.GitUtil;
import com.synuwxy.dango.common.utils.UUIDUtil;
import com.synuwxy.dango.pipeline.Pipeline;
import com.synuwxy.dango.pipeline.task.code.DefaultBuildProductTask;
import com.synuwxy.dango.pipeline.task.common.CustomProductCopyTask;
import com.synuwxy.dango.pipeline.task.docker.DockerImageBuildTask;
import com.synuwxy.dango.pipeline.task.docker.DockerfileGeneratorTask;
import com.synuwxy.dango.pipeline.task.git.GitCloneTask;
import com.synuwxy.dango.pipeline.task.script.ScriptExecutionTask;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @author wxy
 */
@Service
public class DockerCiImpl implements DockerCiService {
    private final String CODE_WORKSPACE;
    private final String DOCKER_WORKSPACE;
    private final String DOCKERFILE_WORKSPACE;
    private final CodeTypeFinder codeTypeFinder;
    private final DockerClient dockerClient;

    public DockerCiImpl(CommonConfig commonConfig, CodeTypeFinder codeTypeFinder, DockerClient dockerClient) {
        this.CODE_WORKSPACE = commonConfig.getWorkspacePrefix() + "/code/workspace";
        this.DOCKER_WORKSPACE = commonConfig.getWorkspacePrefix() + "/docker/workspace";
        this.DOCKERFILE_WORKSPACE = commonConfig.getDockerfileWorkspace();
        this.codeTypeFinder = codeTypeFinder;
        this.dockerClient = dockerClient;
    }

    @Override
    public void build(DockerBuildParam dockerBuildParam) throws IOException {
        String codeWorkspace = this.CODE_WORKSPACE + "/" + UUIDUtil.generatorId();
        String dockerWorkspace = this.DOCKER_WORKSPACE + "/" + UUIDUtil.generatorId();
        GitCloneParam gitCloneParam = dockerBuildParam.getGitCloneParam();
        String imageFullName = dockerBuildParam.getDockerTag();
        String dockerfileName = dockerBuildParam.getType();
        String sourceName = GitUtil.getRepositoryName(gitCloneParam.getRepository());
        String codeRootPath = codeWorkspace + "/" + sourceName;

        RecycleBin recycleBin = RecycleBin.create();
        recycleBin.addPathToDirectoriesBin(codeWorkspace);
        recycleBin.addPathToDirectoriesBin(dockerWorkspace);

        Pipeline.create()
                .addTask(GitCloneTask.create(codeWorkspace, gitCloneParam))
                .addTask(DefaultBuildProductTask.create(codeTypeFinder, codeRootPath, dockerWorkspace))
                .addTask(DockerfileGeneratorTask.create(dockerfileName, DOCKERFILE_WORKSPACE, dockerWorkspace))
                .addTask(DockerImageBuildTask.create(dockerClient, imageFullName, dockerWorkspace))
                .run();

        recycleBin.cleanDirectoriesBin();
    }

    @Override
    public void customBuild(DockerCustomBuildParam dockerCustomBuildParam) throws IOException {
        String codeWorkspace = this.CODE_WORKSPACE + "/" + UUIDUtil.generatorId();
        String dockerWorkspace = this.DOCKER_WORKSPACE + "/" + UUIDUtil.generatorId();
        GitCloneParam gitCloneParam = dockerCustomBuildParam.getGitCloneParam();
        String imageFullName = dockerCustomBuildParam.getDockerTag();
        String dockerfileName = dockerCustomBuildParam.getType();
        String command = dockerCustomBuildParam.getCommand();
        String productPath = dockerCustomBuildParam.getProductPath();
        List<String> extraPaths = dockerCustomBuildParam.getExtraPaths();
        String sourceName = GitUtil.getRepositoryName(gitCloneParam.getRepository());
        String codeRootPath = codeWorkspace + "/" + sourceName;

        RecycleBin recycleBin = RecycleBin.create();
        recycleBin.addPathToDirectoriesBin(codeWorkspace);
        recycleBin.addPathToDirectoriesBin(dockerWorkspace);

        Pipeline.create()
                .addTask(GitCloneTask.create(codeWorkspace, gitCloneParam))
                .addTask(ScriptExecutionTask.create(command, codeRootPath))
                .addTask(CustomProductCopyTask.create(sourceName, codeRootPath, productPath, extraPaths, dockerWorkspace))
                .addTask(DockerfileGeneratorTask.create(dockerfileName, DOCKERFILE_WORKSPACE, dockerWorkspace))
                .addTask(DockerImageBuildTask.create(dockerClient, imageFullName, dockerWorkspace))
                .run();

        recycleBin.cleanDirectoriesBin();
    }
}
