package org.example.service.model;

import java.util.ArrayList;
import java.util.Objects;

public class User {
    public int ID;
    public String firstName;
    public String lastName;
    public int role;
    public ArrayList<Task> tasks;

    public User(int ID, String firstName, String lastName, int role, ArrayList<Task> tasks) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.tasks = tasks;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
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

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return ID == user.ID && role == user.role && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(tasks, user.tasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, firstName, lastName, role, tasks);
    }

    @Override
    public String toString() {
        return "User{" +
                "ID=" + ID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role=" + role +
                ", tasks=" + tasks +
                '}';
    }

    public User() {
    }
}
