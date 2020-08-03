package com.synuwxy.dango.aggreate.task.docker.container;

import com.synuwxy.dango.aggreate.task.Task;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wxy
 */
@Slf4j
@Builder
public class StopContainerTask implements Task {
    @Override
    public void run() throws Exception {

    }
}
