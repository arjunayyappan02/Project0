package org.example.controller;

import org.example.repository.entities.TaskEntity;
import org.example.service.TaskService;
import org.example.service.model.Task;
import org.example.util.InputHandler;

import java.util.ArrayList;
import java.util.Scanner;

public class TaskController {
    private final TaskService taskService = new TaskService();

    public void handleInput() {
        boolean session = true;
        while (session) {
            displayMenu();
            int option = InputHandler.getIntInput("Choose from the following options: ");
            switch (option) {
                case 1:
                    addTask();
                case 2:
                    closeTask();
                case 3:
                    viewTasks();
                case 0:
                    session = false;
                    System.out.println("You have successfully logged out");
                default:
                    System.out.println("Invalid input. Please try again");
            }
        }
    }

    public Task addTask() {
        String taskName = InputHandler.getStringInput("Enter task name");
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setName(taskName);
        Integer newTaskId = taskService.createEntity(taskEntity);

        if (newTaskId == -1) {
            System.out.println("Task name is invalid");
        }else {
            System.out.println("New Task Created: " + newTaskId);
        }
        /*
        String name;
        String desc;
        LocalDate dueDate;
        int priority;
        Task task = new Task();

        System.out.println("Enter task name: ");
        name = scanner.nextLine();

        System.out.println("Enter task description (Press enter to skip): ");
        desc = scanner.nextLine();

        System.out.println("Enter task due date (mm/dd/yy): ");

        //Read dueDate
        try (Scanner scanner = new Scanner(input)) {
            String dateString = scanner.next();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            dueDate = LocalDate.parse(dateString, formatter);

            System.out.println("Enter task priority (0 - Low, 1 - Medium, 2 - High): ");
            priority = scanner.nextInt();

            return task;
        }
        scanner.close();
         */
    }

    private void closeTask() {
        int option = InputHandler.getIntInput("Enter ID of task you'd like to close: ");
        int close = scanner.nextInt();
        System.out.println("Confirm deletion? (y/n)");
        if (scanner.nextLine().equals("y")) {
            tasks.remove(close);
            System.out.println("Item " + close + " successfully deleted");
        }
        scanner.close();
    }

    //Prints a list of current tasks
    private void viewTasks() {
        ArrayList<Task> tasks = taskService.getAllModels();
        System.out.println("Current Tasks: ");
        if (tasks.isEmpty())
            System.out.print("None");
        else {
            //Standard for loop
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(i + ": ");
                System.out.print(tasks.get(i));
            }
        }
    }

    /*
        Move this somewhere else
        private boolean validateTaskName (String name) {
            //Task name cannot be more than thirty chars
        }
    */

    private void displayMenu() {
        System.out.println("Select an option");
        System.out.println("1 - Create new task");
        System.out.println("2 - Close task");
        System.out.println("3 - Show current tasks");
        if (isAdmin) {
            System.out.println("4 - Assign new task");
        }
        System.out.println("0 - Log out");
    }
}
