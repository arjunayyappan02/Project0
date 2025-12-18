package org.example.controller;

import org.example.repository.entities.UserEntity;
import org.example.service.TaskService;
import org.example.service.UserService;
import org.example.service.UserTasksService;
import org.example.service.model.Task;
import org.example.service.model.User;
import org.example.util.InputHandler;

import java.util.List;
import java.util.Optional;

public class UserController {
    private final UserService userService = new UserService();
    private final UserTasksService userTasksService = new UserTasksService();
    private final TaskService TaskService = new TaskService();

    public void handleInput(){
        boolean running = true;
        while(running){
            printMenu();
            int choice = InputHandler.getIntInput("Make a choice!");
            switch(choice){
                case 1 -> addUser();
//                case 2 -> getUserByID();
//                case 3 -> getUserByName();
                case 4 -> getAllUsers();
                case 5 -> getAllUsersByTask();
//                case 6 -> getAllUsersByUserTasks();
                case 0 -> {
                    System.out.println("Exiting User Services");
                    running = false; }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    public void handleAdminInput(){
        boolean running = true;
        while(running) {
            printMenu();
            int choice = InputHandler.getIntInput("Make a choice!");
            switch(choice){
                case 1 -> addUser();
//                case 2 -> getUserById();
//                case 3 -> getUserByName();
                case 4 -> getAllUsers();
                case 5 -> getAllUsersByTask();
//                case 6 -> getAllUsersByUserTasks();
                case 0 -> {
                    System.out.println("Leaving User Services");
                    running = false; }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private void getAllUsersByTask() {
        String taskName = InputHandler.getStringInput("Enter task name");
        Optional<Task> task = TaskService.getModelByTaskName(taskName);
        if(task.isPresent()){
            List<User> users = userService.getAllModelsByTask(task.get());
            for(User user: users){
                System.out.println(user);
            }
        }else{
            System.out.println("Invalid Task Name");
        }
    }

    private void getAllUsers() {
        List<User> Users = userService.getAllModels();
        for(User User: Users){
            System.out.println(User);
        }
    }

    private void addUser() {
        // What do we expect from the user?
        // User full name
        String firstName = InputHandler.getStringInput("Enter user's first name");
        String lastName = InputHandler.getStringInput("Enter user's lull name");
        // Task Name
        String taskName = InputHandler.getStringInput("Enter task name");

        Optional<Task> Task = TaskService.getModelByTaskName(taskName);
        if (Task.isPresent()){
            // UserTasks Name
            String UserTasksName = InputHandler.getStringInput("What is the UserTasks name");

            Optional<UserTasks> UserTasks = userTasksService.getModelByUserTasksName(UserTasksName);

            if(UserTasks.isPresent()){
                UserEntity UserEntity = new UserEntity();
                UserEntity.setFirstName(firstName);
                UserEntity.setLastName(lastName);
                UserEntity.setID(Task.get().getID());
                UserEntity.setUserTasksId(UserTasks.get().getID());

                Integer newUserId = userService.createEntity(UserEntity);

                if(newUserId == -1){
                    System.out.println("Unable to create User");
                }else{
                    System.out.println("User has been created: " + newUserId);
                }
            }else{
                System.out.println("UserTasks name is invalid");
            }

        }else{
            System.out.println("Task name is invalid");
        }


    }

    private void printMenu(){
        System.out.println("=== Admin Services ===");
        System.out.println("1 - Add User");
        System.out.println("2 - List All Users");
        System.out.println("2 - List All Users");
        System.out.println("3 - List All Tasks");
        System.out.println("4 - List All Users by Task");
        System.out.println("0 - Exit");

    }
}

