package org.example.controller;

import org.example.repository.DAO.UserTasksDAO;
import org.example.repository.entities.TaskEntity;
import org.example.repository.entities.UserEntity;
import org.example.service.TaskService;
import org.example.service.UserService;
import org.example.service.model.Task;
import org.example.service.model.User;
import org.example.util.InputHandler;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskController {
    private final TaskService taskService = new TaskService();
    private final UserService userService = new UserService();

    public void handleInput (int userID) {
        Optional<UserEntity> userOpt = userService.getEntityByID(userID);
        if (userOpt.isEmpty()) return;

        UserEntity consoleUser = userOpt.get();

        boolean running = true;
        int choice;

        boolean isUserAdmin = isAdmin(consoleUser);

        while (running) {
            System.out.println("");
            System.out.println("Select an option");
            if (isUserAdmin) {
                displayAdminTaskMenu();
                choice = InputHandler.getIntInput("");
                switch (choice) {

                    case 1:
                        assignTask(); //Assigns tasks to user(s)
                        break;
                    case 2:
                        getAllUsersByTask();
                        break;
                    case 0:
                        System.out.println("Logging Out...");
                        System.out.println("");
                        //running = false;
                        //break;
                        return;
                    default:
                        System.out.println("Invalid choice");
                }
            } else {
                displayStandardTaskMenu();
                choice = InputHandler.getIntInput("");
                switch (choice) {
                    case 1:
                        Optional<UserEntity> freshUser = userService.getEntityByID(consoleUser.getID());
                        if (freshUser.isEmpty()) {
                            System.out.println("User session invalid");
                            return;
                        }
                        getCurrentTasks(freshUser.get());
                        break;
                    case 2:
                        closeTask(consoleUser); //Closes task
                        break;
                    case 0:
                        System.out.println("Logging Out...");
                        System.out.println("");
                        //running = false;
                        //break;
                        return;
                    default:
                        System.out.println("Invalid choice");
                }
            }
        }
    }

    public void addTask() {
        TaskEntity taskEntity = new TaskEntity(); //Creates new task entity
        String taskName = InputHandler.getStringInput("Enter task name"); //Reads in task name
        taskEntity.setName(taskName); //Sets taskEntity's name to taskName
        Integer newTaskID = taskService.createEntity(taskEntity); //Creates new task ID and assigns it
        taskEntity.setStatus(0); //Automatically sets task as incomplete
        LocalDate newTaskDate = InputHandler.getDateInput("Enter task due date (mm/dd/yy)");
        taskEntity.setDueDate(newTaskDate);
        Integer taskPriority = InputHandler.getIntInput("Enter task priority (0 - Low Priority, 1 - Medium Priority, 2 - High Priority)");
        taskEntity.setPriority(taskPriority);

        if (newTaskID == -1) {
            System.out.println("Task name is invalid");
        }else {
            System.out.println("New Task Created: " + newTaskID);
        }
    }

    //Admin Case 3
    private void getAllTasks() {
        List<User> users = userService.getAllModels();
        for (User user : users) {
            System.out.println(user);
        }
    }

    private void getAllUsersByTask() {
        // Debug: show available tasks
        System.out.println("Available Tasks:");
        for (Task t : taskService.getAllModels()) {
            System.out.println("- " + t.getName());
        }

        String taskName = InputHandler.getStringInput("Enter task name");

        Optional<Task> taskOpt = taskService.getModelByTaskName(taskName);
        if (taskOpt.isEmpty()) {
            System.out.println("Task not found");
            return;
        }

        Task task = taskOpt.get();
        List<User> users = userService.getAllModelsByTask(task);

        if (users.isEmpty()) {
            System.out.println("No users assigned to this task");
            return;
        }

        System.out.println("Users assigned to \"" + task.getName() + "\":");
        for (User user : users) {
            System.out.println(user);
        }
    }

    //Admin Case 5
    private void assignTask() {
        TaskEntity taskEntity = new TaskEntity();
        UserEntity userEntity = new UserEntity();
        Task task = new Task();
        boolean running = true;

        String taskName = InputHandler.getStringInput("Enter task name"); //Reads in task name
        LocalDate taskDate = InputHandler.getDateInput("Enter task due date (mm/dd/yy)");
        Integer taskPriority = InputHandler.getIntInput("Enter task priority (0 - Low Priority, 1 - Medium Priority, 2 - High Priority)");

        taskEntity.setName(taskName);
        taskEntity.setStatus(0);
        taskEntity.setDueDate(taskDate);
        taskEntity.setPriority(taskPriority);

        Integer newTaskID = taskService.createEntity(taskEntity);

        if (newTaskID == -1) {
            System.out.println("Unable to assign new task");
        } else {
            taskEntity.setID(newTaskID);
            System.out.println(taskEntity.toString() + " successfully created!");
            System.out.println("");
        }

        taskEntity.setID(newTaskID);
        Optional<Task> optAssignTask = taskService.getModelByID(newTaskID);
        if (optAssignTask.isPresent()) {
            //Converting Optional<UserEntity> to UserEntity
            task = optAssignTask.get();
        } else {
            System.out.println("Task not found, try again!");
        }

        System.out.println("Select an option");
        System.out.println("1 - Assign " + task.getName() + " to single user");
        System.out.println("2 - Assign  " + task.getName() + "  to all users");

        int choice = InputHandler.getIntInput("");
        switch (choice) {
            case 1:
                Integer assignUserID = InputHandler.getIntInput("Enter ID of user you'd like to assign task to: ");
                Optional<UserEntity> optAssignUser = userService.getEntityByID(assignUserID);
                if (optAssignUser.isPresent()) {
                    //Converting Optional<UserEntity> to UserEntity
                    userEntity = optAssignUser.get();
                } if (optAssignUser.isEmpty()) {
                System.out.println("User not found, try again!");
                return;
            }

                boolean success = userService.assignTaskToUser(
                        userEntity.getID(),
                        task.getID()
                );

                if (success) {
                    System.out.println(task.getName() + " has been successfully assigned to " + userEntity.getFirstName() + " " + userEntity.getLastName() + "!");
                } else {
                    System.out.println("Failed to assign task");
                }
                break;
            case 2:
                List<UserEntity> userEntities = userService.getAllEntities();
                for (UserEntity u : userEntities) {
                    //System.out.println(u);
                    u.setTask(task);
                }
                System.out.println(task.getName() + " has been successfully assigned to all users!");
                break;
            default:
                System.out.println("Invalid choice");
        }
    }


    private void getCurrentTasks(UserEntity userEntity) {
        if (userEntity == null) {
            System.out.println("Invalid user");
            return;
        }

        //Fetch TaskEntities assigned to this user via UserTasksDAO
        UserTasksDAO userTasksDAO = new UserTasksDAO();
        List<TaskEntity> taskEntities;
        try {
            taskEntities = userTasksDAO.findTasksByUserID(userEntity.getID());
        } catch (SQLException e) {
            //e.printStackTrace();
            System.out.println("Error fetching tasks for user");
            return;
        }

        //Check if user has any tasks
        if (taskEntities == null || taskEntities.isEmpty()) {
            System.out.println("You're all caught up! No outstanding tasks");
            return;
        }

        //Convert TaskEntity → Task (service/model object) and filter incomplete tasks
        TaskService taskService = new TaskService();
        List<Task> tasks = new ArrayList<>();
        for (TaskEntity entity : taskEntities) {
            Optional<Task> taskOpt = taskService.convertEntityToModel(entity);
            taskOpt.ifPresent(task -> {
                if (task.getStatus() == 0) { // Only incomplete tasks
                    tasks.add(task);
                }
            });
        }

        //Print tasks or "All caught up" if none are incomplete
        if (tasks.isEmpty()) {
            System.out.println("You're all caught up! No outstanding tasks");
        } else {
            System.out.println("Current Tasks:");
            for (Task task : tasks) {
                System.out.println(task);
            }
        }
    }

    private void closeTask(UserEntity userEntity) {
        if (userEntity == null) return;

        // 1️⃣ Fetch current tasks from the junction table
        UserTasksDAO userTasksDAO = new UserTasksDAO();
        List<TaskEntity> taskEntities;
        try {
            taskEntities = userTasksDAO.findTasksByUserID(userEntity.getID());
        } catch (SQLException e) {
            //e.printStackTrace();
            System.out.println("Error fetching tasks for user");
            return;
        }

        if (taskEntities == null || taskEntities.isEmpty()) {
            System.out.println("You're all caught up! No tasks to close");
            return;
        }

        // 2️⃣ Convert TaskEntity → Task (service/model)
        TaskService taskService = new TaskService();
        List<Task> tasks = new ArrayList<>();
        for (TaskEntity entity : taskEntities) {
            taskService.convertEntityToModel(entity).ifPresent(tasks::add);
        }

        // 3️⃣ Prompt user to select task to close
        System.out.println("Your Current Tasks:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + " - " + tasks.get(i).getName());
        }

        String closedTaskName = InputHandler.getStringInput("Enter name of task you'd like to close: ");

        Optional<Task> optCloseTask = tasks.stream()
                .filter(t -> t.getName().equalsIgnoreCase(closedTaskName))
                .findFirst();

        if (optCloseTask.isEmpty()) {
            System.out.println("Task not found or not assigned to you");
            return;
        }

        Task closeTask = optCloseTask.get();

        // 4️⃣ Confirm closure
        String confirm = InputHandler.getStringInput(
                "Confirm closure of Task " + closeTask.getID() + " - " + closeTask.getName() + " (y/n): "
        );
        if (!confirm.equalsIgnoreCase("y")) {
            System.out.println("Task not closed");
            return;
        }

        // 5️⃣ Remove from junction table
        try {
            boolean success = userTasksDAO.removeTaskFromUser(userEntity.getID(), closeTask.getID());
            if (success) {
                System.out.println("Task " + closeTask.getName() + " successfully closed!");
            } else {
                System.out.println("Failed to close task");
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            System.out.println("Error closing task");
        }
    }


    //Prints a list of current tasks
    private void viewTasks() {
        List<Task> tasks = taskService.getAllModels();
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

    public boolean isAdmin(UserEntity standardOrAdmin) {
        //Figure out the difference between getModelByID and getEntityByID
        boolean adminCheck;
        if (standardOrAdmin.getRole() == 1) {
            adminCheck = true;
        } else {
            adminCheck = false;
        }
        return adminCheck;
    }

    private void displayStandardTaskMenu() {
        System.out.println("1 - View Tasks");
        System.out.println("2 - Close Task");
        System.out.println("0 - Log Out");
    }

    private void displayAdminTaskMenu() {

        System.out.println("1 - Assign Task");
        System.out.println("2 - List All Users By Task");
        System.out.println("3 - Find Incomplete Tasks");
        System.out.println("4 - List All Tasks By Status");
        System.out.println("0 - Log Out");
    }
}
