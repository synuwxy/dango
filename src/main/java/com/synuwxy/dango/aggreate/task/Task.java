package com.synuwxy.dango.aggreate.task;

/**
 * 声明一个任务，此任务具备一个执行的方法，一个任务代表着一次完整的事件
 * 任务会作为pipeline的一个执行单元
 *
 * 实现task需要自行构建好入参，以一个void方法进行调度，在run方法内应当处理好此次事件的所有逻辑
 * @author wxy
 */
public interface Task {
    /**
     * 执行
     * @throws Exception 直接抛出Exception交给上层处理
     */
    void run() throws Exception;
}
