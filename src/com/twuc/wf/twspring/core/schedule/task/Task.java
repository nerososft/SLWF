package com.twuc.wf.twspring.core.schedule.task;

public class Task{
    private TaskType taskType;
    private TaskStatus taskStatus;

    public Task(TaskType taskType, TaskStatus taskStatus) {
        this.taskType = taskType;
        this.taskStatus = taskStatus;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }
}
