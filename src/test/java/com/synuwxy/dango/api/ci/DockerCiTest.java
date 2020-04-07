package com.synuwxy.dango.api.ci;

import com.synuwxy.dango.common.utils.DockerUtil;
import com.synuwxy.dango.controller.ci.DockerBuildParam;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DockerCiTest {

    @Autowired
    private DockerCiService dockerCiService;

    @Test
    public void buildTest() {
        DockerBuildParam dockerBuildParam = new DockerBuildParam();
        dockerBuildParam.setRepository("https://gitee.com/synuwxy/event-bus.git");
        dockerBuildParam.setBranch("master");
        dockerBuildParam.setType(DockerUtil.TYPE_MAVEN);
        dockerBuildParam.setDockerTag("test:v1");
        dockerCiService.build(dockerBuildParam);
    }
}
