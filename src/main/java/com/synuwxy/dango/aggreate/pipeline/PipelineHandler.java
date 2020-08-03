package com.synuwxy.dango.aggreate.pipeline;

import com.synuwxy.dango.aggreate.task.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * 流水线实现
 * @author wxy
 */
public class PipelineHandler implements Pipeline {

    private final List<Task> tasks = new ArrayList<>();

    @Override
    public void addTask(Task task) {
        tasks.add(task);
    }

    @Override
    public void run() throws Exception {
        for (Task task : tasks) {
            task.run();
        }
    }
}
