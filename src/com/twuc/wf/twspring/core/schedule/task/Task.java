package com.twuc.wf.twspring.core.schedule.task;

public class Task{
    private TaskType taskType;

    public Task(TaskType taskType) {
        this.taskType = taskType;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }
}
