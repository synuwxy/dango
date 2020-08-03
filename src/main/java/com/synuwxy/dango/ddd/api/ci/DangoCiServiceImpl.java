package com.synuwxy.dango.ddd.api.ci;

import com.github.dockerjava.api.DockerClient;
import com.synuwxy.dango.ddd.aggreate.DockerClientMachine;
import com.synuwxy.dango.ddd.api.ci.model.DockerCiParam;
import com.synuwxy.dango.ddd.api.ci.model.DockerCustomCiParam;
import com.synuwxy.dango.ddd.event.BuildEvent;
import com.synuwxy.dango.ddd.event.implement.ci.DockerCiCommand;
import com.synuwxy.dango.ddd.event.implement.ci.DockerCustomCiCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wxy
 */
@Service
@Slf4j
public class DangoCiServiceImpl implements DangoCiService {

    private final DockerClient dockerClient;

    private final ApplicationEventPublisher publisher;

    public DangoCiServiceImpl(DockerClient dockerClient, ApplicationEventPublisher publisher) {
        this.dockerClient = dockerClient;
        this.publisher = publisher;
    }

    @Override
    public void build(DockerCiParam dockerCiParam) {
        List<DockerClientMachine> machines = dockerCiParam.getMachines();
        if (machines.isEmpty()) {
            DockerCiCommand dockerCiCommand = new DockerCiCommand();
            BeanUtils.copyProperties(dockerCiParam, dockerCiCommand);
            dockerCiCommand.setDockerClient(dockerClient);
            publisher.publishEvent(new BuildEvent(dockerCiCommand));
        } else {
            for (DockerClientMachine machine:machines) {
                DockerCiCommand dockerCiCommand = new DockerCiCommand();
                BeanUtils.copyProperties(dockerCiParam, dockerCiCommand);
                dockerCiCommand.setDockerClient(machine.createTcpClient());
                publisher.publishEvent(new BuildEvent(dockerCiCommand));
            }
        }

    }

    @Override
    public void customBuild(DockerCustomCiParam dockerCustomCiParam) {
        List<DockerClientMachine> machines = dockerCustomCiParam.getMachines();
        if (machines.isEmpty()) {
            DockerCustomCiCommand dockerCustomCiCommand = new DockerCustomCiCommand();
            BeanUtils.copyProperties(dockerCustomCiParam, dockerCustomCiCommand);
            dockerCustomCiCommand.setDockerClient(dockerClient);
            publisher.publishEvent(new BuildEvent(dockerCustomCiCommand));
        } else {
            for (DockerClientMachine machine:machines) {
                DockerCustomCiCommand dockerCustomCiCommand = new DockerCustomCiCommand();
                BeanUtils.copyProperties(dockerCustomCiParam, dockerCustomCiCommand);
                dockerCustomCiCommand.setDockerClient(machine.createTcpClient());
                publisher.publishEvent(new BuildEvent(dockerCustomCiCommand));
            }
        }
    }

}