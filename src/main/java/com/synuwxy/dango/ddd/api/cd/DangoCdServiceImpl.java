package com.synuwxy.dango.ddd.api.cd;

import com.synuwxy.dango.ddd.api.cd.model.DockerDeployParam;
import com.synuwxy.dango.common.utils.DockerUtil;
import com.synuwxy.dango.ddd.event.implement.cd.DockerCdCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @author wxy
 */
@Service
@Slf4j
public class DangoCdServiceImpl implements DangoCdService {
    @Override
    public void deploy(DockerDeployParam dockerDeployParam) {
        DockerCdCommand dockerCdCommand = new DockerCdCommand();
        BeanUtils.copyProperties(dockerDeployParam, dockerCdCommand);
        DockerUtil.extractClients(dockerDeployParam.getMachines());

    }
}
