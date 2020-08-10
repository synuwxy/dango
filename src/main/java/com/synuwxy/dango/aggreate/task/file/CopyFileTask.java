package com.synuwxy.dango.aggreate.task.file;

import com.synuwxy.dango.aggreate.task.Task;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.FileCopyUtils;

import java.io.File;

/**
 * @author wxy
 */
@Slf4j
@Builder
public class CopyFileTask implements Task {

    private final String source;
    private final String target;

    @Override
    public void run() throws Exception {
        FileCopyUtils.copy(new File(source), new File(target));
    }
}
