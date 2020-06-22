package com.synuwxy.dango.api.cd;

import com.synuwxy.dango.api.cd.model.DockerDeployParam;
import com.synuwxy.dango.common.utils.DockerUtil;
import com.synuwxy.dango.event.implement.cd.DockerCdCommand;
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
