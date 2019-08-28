package com.twuc.wf.twspring.core.schedule.task;

import java.lang.reflect.Method;

public class ScheduleTask extends FunctionTask {

    public ScheduleTask(TaskType taskType,Object object, Class<?> clz, Method method, Object[] args) {
        super(taskType, TaskStatus.TASK_READY, object, clz, method, args);
    }
}
