package org.example.service.model;

//import org.example.repository.entities.UserTasks;

import java.util.Objects;

public class UserTasks {
    public int id;
    public int userID;
    public int taskID;

    public UserTasks(int id, int userID, int taskID) {
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
        return "UserTasks{" +
                "ID=" + id +
                ", userID=" + userID +
                ", taskID=" + taskID +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserTasks that = (UserTasks) o;
        return id == that.id && userID == that.userID && taskID == that.taskID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userID, taskID);
    }
    public UserTasks() {
    }
}
