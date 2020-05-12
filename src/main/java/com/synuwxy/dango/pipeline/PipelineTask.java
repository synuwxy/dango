package com.synuwxy.dango.pipeline;

/**
 * @author wxy
 */
public interface PipelineTask {

    /**
     * pipeline执行任务接口
     * @throws Exception 可能抛出的任意异常都要被监测，后续回滚用
     */
    void process() throws Exception;

    /**
     * 是否执行下一个步骤的判断，默认执行
     * @return true 执行，false pipeline停止
     */
    default boolean isForward() {
        return true;
    }

    /**
     * 执行任务之前运行的接口
     */
    default void before() {}

    /**
     * 执行任务之后运行的接口
     */
    default void after() {}

    /**
     * 补偿
     */
    default void compensate() {}
}
