package org.example;

import org.example.controller.TaskController;
import org.example.controller.UserController;
import org.example.controller.UserTasksController;
import org.example.repository.DAO.TaskDAO;
import org.example.repository.entities.TaskEntity;
import org.example.service.UserService;
import org.example.util.ConnectionHandler;
import org.example.util.InputHandler;

import java.sql.SQLException;

import org.example.service.model.Task;

import java.util.Objects;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        TaskController taskController = new TaskController();
        UserController userController = new UserController();
        //UserTasksController userTasksController = new UserTasksController();

        // Getting username from user input
        System.out.print("Enter user ID: ");
        //System.out.println("Welcome " + userFirstName + userLastName "!");

        if (Objects.equals(userID, adminID)) {
            System.out.println("Access Level: Admin");
        } else
            System.out.println("Access Level: Standard");

        boolean session = true;
        while (session) {
            displayMenu();
            int option = InputHandler.getIntInput("Select one of the options displayed: ");
            switch (option) {
                case 1:
                    taskController.handleInput();
                case 2:
                    userController.handleInput();
                case 3:
                    viewTasks();
                case 0:
                    session = false;
                    System.out.println("You have successfully logged out");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("Select an option");
        System.out.println("1 - Create new task");
        System.out.println("2 - Close task");
        System.out.println("3 - Show current tasks");
        //Add pseudocode to validate admin
        System.out.println("4 - Assign new task");
        System.out.println("0 - Log out");
    }
}
