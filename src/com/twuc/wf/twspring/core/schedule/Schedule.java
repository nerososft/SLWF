package com.twuc.wf.twspring.core.schedule;

import com.twuc.wf.twspring.annotations.Scheduled;
import com.twuc.wf.twspring.core.AnnotationApplicationContext;
import com.twuc.wf.twspring.core.schedule.task.ScheduleIntervalTask;
import com.twuc.wf.twspring.core.schedule.task.ScheduleTask;
import com.twuc.wf.twspring.core.schedule.task.TaskType;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.twuc.wf.twspring.constant.CONSTANT.pInfo;

public class Schedule {

    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors() * 2);

    public void start() {
        for (ScheduleTask task : AnnotationApplicationContext.scheduleTaskSet) {
            if (task.getTaskType() == TaskType.TASK_TYPE_SCHEDULE_INTERVAL) {
                Scheduled scheduled = task.getMethod().getDeclaredAnnotation(Scheduled.class);
                pInfo("[ " + Thread.currentThread().getName() + " ] Schedule Task(" + task.getTaskType() + "): '" + task.getClz().getName() + "." + task.getMethod().getName() + "' interval:'" + scheduled.fixedRate() + "' initializeDelay:'" + scheduled.initialDelay() + "'");
                scheduledExecutorService.scheduleAtFixedRate(task, scheduled.initialDelay(), scheduled.fixedRate(), TimeUnit.MILLISECONDS);
            } else if (task.getTaskType() == TaskType.TASK_TYPE_SCHEDULE_TIMEOUT) {
                Scheduled scheduled = task.getMethod().getDeclaredAnnotation(Scheduled.class);
                pInfo("[ " + Thread.currentThread().getName() + " ] Schedule Task(" + task.getTaskType() + "): '" + task.getClz().getName() + "." + task.getMethod().getName() + "' interval:'" + scheduled.fixedRate() + "' initializeDelay:'" + scheduled.initialDelay() + "'");
                scheduledExecutorService.schedule(task, scheduled.initialDelay(), TimeUnit.MILLISECONDS);
            }
        }
    }
}
