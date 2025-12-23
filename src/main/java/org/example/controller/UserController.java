package org.example.controller;

import org.example.repository.entities.TaskEntity;
import org.example.repository.entities.UserEntity;
import org.example.service.TaskService;
import org.example.service.UserService;
//import org.example.service.UserTasksService;
import org.example.service.model.Task;
import org.example.service.model.User;
import org.example.util.InputHandler;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class UserController {
    private final UserService userService = new UserService();
    //private final UserTasksService userTasksService = new UserTasksService();
    //private final TaskService taskService = new TaskService();
    TaskController taskController = new TaskController();


    public void handleInput() {
        boolean running = true;
        UserEntity consoleUser = new UserEntity();
        int choice;

        while (running) {
            System.out.println("");
            System.out.println("Select an option");

            displayUserMenu();
            choice = InputHandler.getIntInput("");
            switch (choice) {
                case 1:
                    addUser(); //Add user
                    break;
                case 2:
                    getAllUsers(); //List all users
                    break;
                case 3:
                    deleteUser();
                    break;
                case 0:
                    System.out.println("Logging Out...");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice");
                    System.out.println("");
            }
        }
    }

    public boolean isAdmin(UserEntity standardOrAdmin) {
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

    //Admin Option 1
    private void addUser() {
        UserEntity userEntity = new UserEntity();
        String firstName = InputHandler.getStringInput("Enter user's first name");
        String lastName = InputHandler.getStringInput("Enter user's last name");
        Integer role = InputHandler.getIntInput("Enter user role (0 - Standard, 1 - Admin)");

        userEntity.setFirstName(firstName);
        userEntity.setLastName(lastName);
        userEntity.setRole(role);

        Integer newUserID = userService.createEntity(userEntity);

        if (newUserID == -1) {
            System.out.println("Unable to create user");
        } else {
            System.out.println("User " + + newUserID + " (" + userEntity.getFirstName() + " " + userEntity.getLastName() + ") has been successfully created");
        }
    }

    //Admin Option 2
    private void getAllUsers() {
        //List<User> users = userService.getAllModels();
        List<UserEntity> users = userService.getAllEntities();
        for (UserEntity user : users) {
            System.out.println(user);
        }
    }

    //Admin Option 3
    private void deleteUser() {
        Integer assignUserID = InputHandler.getIntInput("Enter ID of user you'd like to delete: ");
        Optional<UserEntity> optAssignUser = userService.getEntityByID(assignUserID);
        if (optAssignUser.isPresent()) {
            if ((InputHandler.getStringInput("Confirm deletion of User " + assignUserID + " (Press y for yes, n for no)")).equals("y")) {
                userService.deleteEntity(assignUserID);
                System.out.println("User " + assignUserID + " successfully deleted!");
                //taskService.deleteEntity(userEntTest.getID()); //Add logic for deleting task from DAO??
            }
        } else {
            System.out.println("User not found, try again!");
        }
    }

    private UserEntity validateUser() {
        UserEntity validUser = new UserEntity();
        // Getting userID from user input
        Integer consoleUserID = InputHandler.getIntInput("Enter user ID: ");
        Optional<UserEntity> optConsoleUser = userService.getEntityByID(consoleUserID);
        if (optConsoleUser.isPresent()) {
            //Converting Optional<UserEntity> to UserEntity
            validUser = optConsoleUser.get();
        } else {
            System.out.println("User not found, try again!");
        }
        return validUser;
    }

    private void displayUserMenu() {
        System.out.println("1 - Add User");
        System.out.println("2 - List All Users");
        System.out.println("3 - Delete User By ID");
        System.out.println("0 - Log Out");
    }
}