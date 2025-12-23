package org.example.service;

import org.example.repository.DAO.UserDAO;
import org.example.repository.DAO.UserTasksDAO;
import org.example.repository.entities.TaskEntity;
import org.example.repository.entities.UserEntity;
import org.example.service.interfaces.ServiceInterface;
import org.example.service.model.Task;
import org.example.service.model.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserService implements ServiceInterface<UserEntity, User> {
    private UserDAO userDAO = new UserDAO();
    private final UserTasksDAO userTasksDAO = new UserTasksDAO();

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public UserService() {
    }

    @Override
    public Integer createEntity(UserEntity entity) {
        try{
            Integer newID = userDAO.create(entity);
            return newID;
        }catch(SQLException e){
            //e.printStackTrace();
            return -1;
        }
    }

    @Override
    public Optional<UserEntity> getEntityByID(Integer id) {
        try{
            Optional<UserEntity> user = userDAO.findByID(id);
            if(user.isEmpty()){
                throw new RuntimeException("User not found");
            }
            return user;
        }catch(SQLException | RuntimeException e){
            //e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<UserEntity> getAllEntities() {
        try{
            List<UserEntity> userEntities = userDAO.findAll();
            return userEntities;
        }catch (SQLException e){
            //e.printStackTrace();
            return null;
        }
    }

    //@Override
    public String returnFullName(Integer id) {
        try{
            String fullName = userDAO.returnFullName(id);
            return fullName;
        }catch (SQLException e){
            //e.printStackTrace();
            return null;
        }
    }

    @Override
    public UserEntity updateEntity(Integer id, UserEntity newEntity) {
        return null;
    }

    @Override
    public void deleteEntity(Integer id) {
        try{
            userDAO.deleteByID(id);
        }catch (SQLException e){
            //e.printStackTrace();
            System.out.println("SQL Exception Error Thrown!");
        }
    }

    @Override
    public Optional<User> convertEntityToModel(UserEntity entity) {
        try{
            User user = new User();
            user.setID(entity.getID());
            user.setFirstName(entity.getFirstName());
            user.setLastName(entity.getLastName());
            user.setRole(entity.getRole());
            //user.setTasks(entity.getTasks());

            return Optional.of(user);

        }catch(RuntimeException e){
            //e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> getModelByID(Integer ID) {
        try{
            Optional<User> user = convertEntityToModel(getEntityByID(ID).get());
            return user;
        }catch(RuntimeException e){
            //e.printStackTrace();
            return Optional.empty();
        }
    }

    public List<User> getAllModels() {
        List<UserEntity> userEntities = getAllEntities();
        List<User> users = new ArrayList<>();
        for(UserEntity userEntity: userEntities){
            Optional<User> user = convertEntityToModel(userEntity);
            if(user.isPresent()){
                users.add(user.get());
            }
        }
        return users;
    }

    public List<User> getAllModelsByTask(Task task) {
        List<UserEntity> userEntities = getAllEntitiesByTaskID(task.getID());
        List<User> users = new ArrayList<>();
        for(UserEntity entity : userEntities){
            Optional<User> user = convertEntityToModel(entity);
            if(user.isPresent()){
                users.add(user.get());
            }
        }
        return users;
    }

    private List<UserEntity> getAllEntitiesByTaskID(Integer taskID) {
        try{
            List<UserEntity> userEntities = userDAO.findAllByTaskID(taskID);
            return userEntities;
        }catch(SQLException e){
            //e.printStackTrace();
            return null;
        }
    }

    public boolean assignTaskToUser(int userID, int taskID) {
        System.out.println("Assigning task " + taskID + " to user " + userID);
        try {
            userTasksDAO.assignTaskToUser(userID, taskID);
            return true;
        } catch (SQLException e) {
            e.printStackTrace(); // TEMP: enable this
            return false;
        }
    }

    public List<TaskEntity> getTasksForUser(int userID) {
        try {
            return userTasksDAO.findTasksByUserID(userID);
        } catch (SQLException e) {
            return List.of(); // NEVER return null
        }
    }

}