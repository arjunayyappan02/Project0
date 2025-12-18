package org.example.service;

import org.example.repository.DAO.TaskDAO;
import org.example.repository.entities.TaskEntity;
import org.example.service.interfaces.ServiceInterface;
import org.example.service.model.Task;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskService implements ServiceInterface<TaskEntity, Task> {

    private TaskDAO taskDAO = new TaskDAO();

    @Override
    public Integer createEntity(TaskEntity entity) {
        try{
            Integer newID = taskDAO.create(entity);
            return newID;
        } catch(SQLException e){
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public Optional<TaskEntity> getEntityByID(Integer ID) {
        try{
            Optional<TaskEntity> TaskEntity = taskDAO.findByID(ID);
            if(TaskEntity.isEmpty()){
                throw new RuntimeException("Task not found");
            }

            return TaskEntity;
        } catch(SQLException | RuntimeException e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<TaskEntity> getAllEntities() {
        try{
            List<TaskEntity> taskEntities = taskDAO.findAll();
            return taskEntities;
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public TaskEntity updateEntity(Integer ID, TaskEntity newEntity) {
        return null;
    }

    @Override
    public boolean deleteEntity(Integer ID) {
        return false;
    }

    @Override
    public Optional<Task> convertEntityToModel(TaskEntity entity) {

        Task task = new Task();
        task.setID(entity.getID());
        task.setName(entity.getName());
        task.setStatus(entity.getStatus());
        task.setDueDate(entity.getDueDate());
        task.setPriority(entity.getPriority());

        return Optional.of(task);
    }

    @Override
    public Optional<Task> getModelByID(Integer ID) {
        Optional<TaskEntity> taskEntity = getEntityByID(ID);
        try{
            if (taskEntity.isPresent()) {
                Optional<Task> Task = convertEntityToModel(taskEntity.get());
                if (Task.isPresent()) {
                    return Task;
                } else{
                    throw new RuntimeException("TaskEntity conversion failed");
                }
            } else {
                throw new RuntimeException("TaskEntity not found");
            }

        } catch(RuntimeException e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<Task> getModelByTaskName(String taskName) {
        Optional<TaskEntity> taskEntity = getEntityByTaskName(taskName);
        try{
            if (taskEntity.isPresent()){
                Optional<Task> task = convertEntityToModel(taskEntity.get());
                if (task.isPresent()){
                    return task;
                } else{
                    throw new RuntimeException("TaskEntity conversion failed");
                }
            } else{
                throw new RuntimeException("TaskEntity not found");
            }
        } catch(RuntimeException e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private Optional<TaskEntity> getEntityByTaskName(String taskName) {
        try{
            Optional<TaskEntity> taskEntity = taskDAO.findByTaskName(taskName);
            return taskEntity;
        }catch (SQLException e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public List<Task> getAllModels() {
        List<TaskEntity> taskEntities = getAllEntities();
        List<Task> tasks = new ArrayList<>();
        for(TaskEntity taskEntity : taskEntities){
            Optional<Task> task = convertEntityToModel(taskEntity);
            if(task.isPresent()){
                tasks.add(task.get());
            }
        }
        return tasks;
    }
}