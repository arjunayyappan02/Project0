import org.example.repository.DAO.TaskDAO;
import org.example.repository.entities.TaskEntity;
import org.example.repository.entities.UserEntity;
import org.example.repository.entities.UserTasksEntity;
import org.example.service.TaskService;
import org.example.service.UserService;
import org.example.service.UserTasksService;
import org.example.service.model.Task;
import org.example.service.model.User;
import org.example.service.model.UserTasks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskDAO taskDAO;

    @InjectMocks
    private TaskService taskService;

    @Test
    void createEntity_success() throws SQLException {
        TaskEntity entity = new TaskEntity();
        when(taskDAO.create(entity)).thenReturn(1);

        Integer result = taskService.createEntity(entity);

        assertEquals(1, result);
        verify(taskDAO).create(entity);
    }

    @Test
    void getEntityByID_success() throws SQLException {
        TaskEntity entity = new TaskEntity();
        entity.setID(1);

        when(taskDAO.findByID(1)).thenReturn(Optional.of(entity));

        Optional<TaskEntity> result = taskService.getEntityByID(1);

        assertTrue(result.isPresent());
        assertEquals(1, result.get().getID());
    }

    @Test
    void convertEntityToModel_success() {
        TaskEntity entity = new TaskEntity();
        entity.setID(1);
        entity.setName("Test");
        entity.setStatus(0);
        entity.setPriority(1);

        Optional<Task> result = taskService.convertEntityToModel(entity);

        assertTrue(result.isPresent());
        assertEquals("Test", result.get().getName());
    }

    @Test
    void getModelByID_success() throws SQLException {
        TaskEntity entity = new TaskEntity();
        entity.setID(1);
        entity.setName("Task");

        when(taskDAO.findByID(1)).thenReturn(Optional.of(entity));

        Optional<Task> result = taskService.getModelByID(1);

        assertTrue(result.isPresent());
        assertEquals("Task", result.get().getName());
    }

    @Test
    void createEntity_sqlException_returnsMinusOne() throws SQLException {
        TaskEntity entity = new TaskEntity();
        when(taskDAO.create(entity)).thenThrow(SQLException.class);

        Integer result = taskService.createEntity(entity);

        assertEquals(-1, result);
    }

    @Test
    void getEntityByID_notFound_returnsEmpty() throws SQLException {
        when(taskDAO.findByID(99)).thenReturn(Optional.empty());

        Optional<TaskEntity> result = taskService.getEntityByID(99);

        assertTrue(result.isEmpty());
    }

    @Test
    void getModelByID_entityNotFound_returnsEmpty() throws SQLException {
        when(taskDAO.findByID(99)).thenReturn(Optional.empty());

        Optional<Task> result = taskService.getModelByID(99);

        assertTrue(result.isEmpty());
    }

    @Test
    void getAllEntities_sqlException_returnsNull() throws SQLException {
        when(taskDAO.findAll()).thenThrow(SQLException.class);

        List<TaskEntity> result = taskService.getAllEntities();

        assertNull(result);
    }

    @Test
    void getModelByTaskName_success() throws SQLException {
        TaskEntity entity = new TaskEntity();
        entity.setName("MyTask");

        when(taskDAO.findByTaskName("MyTask")).thenReturn(Optional.of(entity));

        Optional<Task> result = taskService.getModelByTaskName("MyTask");

        assertTrue(result.isPresent());
        assertEquals("MyTask", result.get().getName());
    }

    @Test
    void getAllModels_convertsAllEntities() throws SQLException {
        TaskEntity t1 = new TaskEntity();
        t1.setName("Task1");

        TaskEntity t2 = new TaskEntity();
        t2.setName("Task2");

        when(taskDAO.findAll()).thenReturn(List.of(t1, t2));

        List<Task> result = taskService.getAllModels();

        assertEquals(2, result.size());
        assertEquals("Task1", result.get(0).getName());
    }
}

