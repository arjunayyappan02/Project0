package org.example.repository.entities;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

public class TaskEntity {
    public int id; //Task identifier
    public String name; //Name of task
    public int status; //Task status
    public LocalDate dueDate; //Due date
    public int priority; //Set task priority (0 - Low, 1 - Medium, 2 - High)
    //Scanner scanner = new Scanner(System.in);

    public TaskEntity(int id, String name, int status, LocalDate dueDate, int priority) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.dueDate = dueDate;
        this.priority = priority;
    }

    public int getID() { return id; }

    public void setID(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getStatus() { return status; }

    public void setStatus(int status) { this.status = status; }

    public LocalDate getDueDate() { return dueDate; }

    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public int getPriority() { return priority; }

    public void setPriority(int priority) { this.priority = priority; }

    @Override
    public String toString() {
        return "TaskEntity {" +
                "ID = " + id +
                ", name = '" + name + '\'' +
                ", status = '" + status + '\'' +
                ", dueDate = " + dueDate +
                ", priority = " + priority +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskEntity that = (TaskEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(status, that.status) && Objects.equals(dueDate, that.dueDate) && Objects.equals(priority, that.priority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, status, dueDate, priority);
    }

    public TaskEntity() {
    }
}