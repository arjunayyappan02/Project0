package org.example.repository.entities;

import org.example.service.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserEntity {
    public int id;
    public String firstName;
    public String lastName;
    public int role;
    public List<Task> tasks = new ArrayList<>();

    public UserEntity(int ID, String firstName, String lastName, int role, List<Task> tasks) {
        this.id = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.tasks = tasks;
    }

    public int getID() {
        return id;
    }

    public void setID(int ID) {
        this.id = ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void updateTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void setTask(Task task) {
        tasks.add(task);
    }

    public void removeTask(Task task) {
        this.tasks.remove(task);
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "ID =" + id +
                ", firstName ='" + firstName + '\'' +
                ", lastName ='" + lastName + '\'' +
                ", role =" + role +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return id == that.id && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, role);
    }

    public UserEntity() {
    }
}
