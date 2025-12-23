package org.example.service.model;

import java.util.List;
import java.util.Objects;

public class User {
    public int id;
    public String firstName;
    public String lastName;
    public int role;
    public List<Task> tasks;

    public User(int id, String firstName, String lastName, int role, List<Task> tasks) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.tasks = tasks;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
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
        this.tasks.add(task);
    }

    public void removeTask(Task task) {
        this.tasks.remove(task);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && role == user.role && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(tasks, user.tasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, role, tasks);
    }

    @Override
    public String toString() {
        return "User{" +
                "ID =" + id +
                ", firstName ='" + firstName + '\'' +
                ", lastName ='" + lastName + '\'' +
                ", role =" + role +
                ", tasks =" + tasks +
                '}';
    }

    public User() {
    }
}
