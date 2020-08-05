package com.synuwxy.dango.service.docker.model;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wxy
 */
@Data
public class ContainerPort {
    private int insidePort;
    private int outsidePort;
}
