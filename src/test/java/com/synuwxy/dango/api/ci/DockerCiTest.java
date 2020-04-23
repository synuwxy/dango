package com.synuwxy.dango.api.ci;

import com.synuwxy.dango.api.git.model.GitCloneParam;
import com.synuwxy.dango.common.utils.DockerUtil;
import com.synuwxy.dango.api.ci.model.DockerBuildParam;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class DockerCiTest {

    @Autowired
    private DockerCiService dockerCiService;

    @Test
    public void buildTest() throws IOException, InterruptedException {
        DockerBuildParam dockerBuildParam = new DockerBuildParam();
        dockerBuildParam.setType(DockerUtil.TYPE_MAVEN);
        dockerBuildParam.setDockerTag("test:v1");

        GitCloneParam gitCloneParam = new GitCloneParam();
        gitCloneParam.setRepository("https://gitee.com/synuwxy/event-bus.git");
        gitCloneParam.setBranch("master");

        dockerBuildParam.setGitCloneParam(gitCloneParam);
        dockerCiService.build(dockerBuildParam);
    }
}
