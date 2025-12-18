package org.example.repository.entities;

import java.util.Objects;

public class UserTasksEntity {
    public int id;
    public int userID;
    public int taskID;

    public UserTasksEntity(int id, int userID, int taskID) {
        this.id = id;
        this.userID = userID;
        this.taskID = taskID;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    @Override
    public String toString() {
        return "UserTasksEntity{" +
                "ID=" + id +
                ", userID=" + userID +
                ", taskID=" + taskID +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserTasksEntity that = (UserTasksEntity) o;
        return id == that.id && userID == that.userID && taskID == that.taskID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userID, taskID);
    }
}
