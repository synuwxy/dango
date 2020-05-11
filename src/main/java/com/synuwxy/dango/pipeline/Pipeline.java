package com.synuwxy.dango.pipeline;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @author wxy
 */
@Slf4j
public class Pipeline {

    private final Queue<PipelineTask> taskQueue = new LinkedList<>();

    public Pipeline addTask(PipelineTask pipelineTask) {
        this.taskQueue.add(pipelineTask);
        return this;
    }

    public void run() {
        Stack<PipelineTask> stack = new Stack<>();
        while (!taskQueue.isEmpty()) {
            PipelineTask task = taskQueue.poll();
            try {
                task.before();
                task.process();
                task.after();
                if (!task.isForward()) {
                    break;
                }
                stack.push(task);
            } catch (Exception e) {
                log.error(e.getMessage());
                while (!stack.empty()) {
                    stack.pop().compensate();
                }
            }
        }
    }

    public static Pipeline create() {
        return new Pipeline();
    }
}
