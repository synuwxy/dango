package com.synuwxy.dango.aggreate.pipeline;

import com.synuwxy.dango.aggreate.task.Task;

/**
 * 流水线，执行任务的集合
 * @author wxy
 */
public interface Pipeline {

    /**
     * 添加任务
     * @param task 任务
     */
    void addTask(Task task);

    /**
     * 运行流水线
     * 顺序执行
     * @throws Exception 直接抛出Exception交给上层处理
     */
    void run() throws Exception;
}
