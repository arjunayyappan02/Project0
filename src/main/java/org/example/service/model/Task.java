package org.example.service.model;

import java.util.Date;
import java.util.Objects;

public class Task {
    private int id;      //Task identifier
    private String name; //Name of task
    private int status;      //Task description
    private Date dueDate;    //Due date
    private int priority;    //Set task priority (0 - Low, 1 - Medium, 2 - High)

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && priority == task.priority && Objects.equals(name, task.name) && Objects.equals(status, task.status) && Objects.equals(dueDate, task.dueDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, status, dueDate, priority);
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskID=" + id +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", dueDate=" + dueDate +
                ", priority=" + priority +
                '}';
    }

    public int getID() { return id; }

    public void setID(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getStatus() { return status; }

    public void setStatus(int status) { this.status = status; }

    public Date getDueDate() { return dueDate; }

    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }

    public int getPriority() { return priority; }

    public void setPriority(int priority) { this.priority = priority; }

    public Task(int id, String name, int status, Date dueDate, int priority) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.dueDate = dueDate;
        this.priority = priority;
    }

    public Task() {
    }
}