package org.example;

import org.example.controller.TaskController;
import org.example.controller.UserController;
import org.example.repository.entities.UserEntity;
import org.example.service.UserService;
import org.example.util.InputHandler;

import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        UserController userController = new UserController();
        TaskController taskController = new TaskController();
        UserService userService = new UserService();
        boolean session = true;

        UserEntity consoleUser = new UserEntity();
        int choice;

        while (session) {
            // Getting userID from user input
            Integer consoleUserID = InputHandler.getIntInput("Enter user ID: ");
            Optional<UserEntity> optConsoleUser = userService.getEntityByID(consoleUserID);
            if (optConsoleUser.isPresent()) {
                //Converting Optional<UserEntity> to UserEntity
                consoleUser = optConsoleUser.get();

                System.out.println("Welcome " + consoleUser.getFirstName() + " " + consoleUser.getLastName() + "!");

                boolean isUserAdmin = isAdmin(consoleUser);

                if (isUserAdmin) {
                    System.out.println("");
                    System.out.println("Select an option");
                    displayMainMenu();
                    choice = InputHandler.getIntInput("");
                    switch (choice) {
                        case 1:
                            userController.handleInput();
                            break;
                        case 2:
                            int userID = consoleUser.getID();
                            taskController.handleInput(userID);
                            break;
                        case 0:
                            System.out.println("Exiting Application");
                            System.out.println("");
                            session = false;
                            break;
                        default:
                            System.out.println("Invalid choice");
                            System.out.println("");
                    }
                } else {
                    int userID = consoleUser.getID();
                    taskController.handleInput(userID);
                    taskController.handleInput(userID);
                }
            } else {
                System.out.println("User not found, try again!");
                //break;
            }
        }
    }

    public static boolean isAdmin (UserEntity standardOrAdmin) {
        boolean adminCheck;
        if (standardOrAdmin.getRole() == 1) {
            System.out.println("Access Level: Admin");
            adminCheck = true;
        } else {
            System.out.println("Access Level: Standard");
            adminCheck = false;
        }
        return adminCheck;
    }

    private static void displayMainMenu () {
        System.out.println("1 - User Services");
        System.out.println("2 - Task Services");
        System.out.println("0 - Exit Application");
    }
}
