package com.twuc.wf.twspring.core.schedule.task;

import java.lang.reflect.Method;

public class ScheduleIntervalTask extends ScheduleTimeoutTask {
    private Long interval;

    public ScheduleIntervalTask(Object object, Class<?> clz, Method method, Object[] args, Long interval) {
        super(TaskType.TASK_TYPE_SCHEDULE_INTERVAL, object, clz, method, args, interval);
        this.interval = interval;
    }

    public Long getInterval() {
        return interval;
    }
}
