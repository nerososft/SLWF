package com.twuc.wf.twspring.core.schedule.task;

import java.lang.reflect.Method;

public class ScheduleTimeoutTask extends ScheduleTask {
    private Long timeout;

    public ScheduleTimeoutTask(TaskType taskType, Object object, Class<?> clz, Method method, Object[] args, Long timeout) {
        super(taskType, object, clz, method, args);
        this.timeout = timeout;
    }


    public ScheduleTimeoutTask(Object object, Class<?> clz, Method method, Object[] args, Long timeout) {
        super(TaskType.TASK_TYPE_SCHEDULE_TIMEOUT, object, clz, method, args);
        this.timeout = timeout;
    }

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }
}
