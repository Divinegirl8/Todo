package com.todo.services;

import com.todo.data.models.Date;
import com.todo.data.models.DueDate;
import com.todo.data.models.Task;
import com.todo.data.models.Time;
import com.todo.data.repositories.TaskRepository;
import com.todo.data.repositories.UserRepository;
import com.todo.dtos.request.*;
import com.todo.exceptions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TodoServiceImplTest {
    @Autowired
    TodoServices todoServices;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TaskRepository taskRepository;

    @AfterEach
    void cleanUp(){
        userRepository.deleteAll();
        taskRepository.deleteAll();
    }

    @Test void  register_User_Count_Is_One(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");

        todoServices.register(registerRequest);
        assertEquals(1,userRepository.count());
    }

    @Test void registerUser_registerUser_With_Same_Username_Throws_Exception(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");

        todoServices.register(registerRequest);
        assertThrows(UserExistException.class,()->todoServices.register(registerRequest));
    }

    @Test void registerUser_Login(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        todoServices.register(registerRequest);
        boolean isLogin = todoServices.findAccountBelongingTo("username").isLogin();
        assertFalse(isLogin);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");

        todoServices.login(loginRequest);
        isLogin = todoServices.findAccountBelongingTo("username").isLogin();
        assertTrue(isLogin);

    }
    @Test void register_Login_With_Invalid_Username_Throws_Exception(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        todoServices.register(registerRequest);
        boolean isLogin = todoServices.findAccountBelongingTo("username").isLogin();
        assertFalse(isLogin);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("name");
        loginRequest.setPassword("password");

        assertThrows(InvalidDetailsException.class,()->todoServices.login(loginRequest));
    }

    @Test void register_Login_With_Invalid_Password_Throws_Exception(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        todoServices.register(registerRequest);
        boolean isLogin = todoServices.findAccountBelongingTo("username").isLogin();
        assertFalse(isLogin);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("word");

        assertThrows(InvalidDetailsException.class,()->todoServices.login(loginRequest));
    }

    @Test void registerUser_Login_Logout(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        todoServices.register(registerRequest);
        boolean isLogin = todoServices.findAccountBelongingTo("username").isLogin();
        assertFalse(isLogin);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");

        todoServices.login(loginRequest);
        isLogin = todoServices.findAccountBelongingTo("username").isLogin();
        assertTrue(isLogin);

        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("username");
        logoutRequest.setPassword("password");

        todoServices.logout(logoutRequest);
        isLogin = todoServices.findAccountBelongingTo("username").isLogin();
        assertFalse(isLogin);

    }
    @Test void registerUser_Login_Logout_With_Wrong_Username_Throws_Exception(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        todoServices.register(registerRequest);
        boolean isLogin = todoServices.findAccountBelongingTo("username").isLogin();
        assertFalse(isLogin);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");

        todoServices.login(loginRequest);
        isLogin = todoServices.findAccountBelongingTo("username").isLogin();
        assertTrue(isLogin);

        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("name");
        logoutRequest.setPassword("password");

        assertThrows(NoUserExist.class,()->todoServices.logout(logoutRequest));

    }


    @Test void registerUser_Login_Logout_With_Wrong_Password_Throws_Exception(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        todoServices.register(registerRequest);
        boolean isLogin = todoServices.findAccountBelongingTo("username").isLogin();
        assertFalse(isLogin);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");

        todoServices.login(loginRequest);
        isLogin = todoServices.findAccountBelongingTo("username").isLogin();
        assertTrue(isLogin);

        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("username");
        logoutRequest.setPassword("word");

        assertThrows(InvalidDetailsException.class,()->todoServices.logout(logoutRequest));

    }


    @Test void registerUser_Logout_Without_Logout_Throws_Exception(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        todoServices.register(registerRequest);
        boolean isLogin = todoServices.findAccountBelongingTo("username").isLogin();
        assertFalse(isLogin);


        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("username");
        logoutRequest.setPassword("password");

        assertThrows(NotLoginException.class,()->todoServices.logout(logoutRequest));

    }

    @Test void registerUser_Login_addTask_Count_Is_One(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        todoServices.register(registerRequest);

        boolean isLogin = todoServices.findAccountBelongingTo("username").isLogin();
        assertFalse(isLogin);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");

        todoServices.login(loginRequest);
        isLogin = todoServices.findAccountBelongingTo("username").isLogin();
        assertTrue(isLogin);

        Date date = new Date();
        date.setMonth("October");
        date.setDay("9");
        date.setYear("2023");

        Time time = new Time();
        time.setHours("9");
        time.setMinutes("15");
        time.setSeconds("24");

        DueDate dueDate = new DueDate();
        dueDate.setDate(date);
        dueDate.setTime(time);

        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setUserId("UID1");
        taskRequest.setMessage("read");
        taskRequest.setDueDate(dueDate);

        todoServices.addTask(taskRequest);
        assertEquals(1,taskRepository.count());

    }


    @Test void registerUser_Login_addTask_WithWrong_UserId_Throws_Exception(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        todoServices.register(registerRequest);
        boolean isLogin = todoServices.findAccountBelongingTo("username").isLogin();
        assertFalse(isLogin);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");

        todoServices.login(loginRequest);
        isLogin = todoServices.findAccountBelongingTo("username").isLogin();
        assertTrue(isLogin);

        Date date = new Date();
        date.setMonth("October");
        date.setDay("9");
        date.setYear("2023");

        Time time = new Time();
        time.setHours("9");
        time.setMinutes("15");
        time.setSeconds("24");

        DueDate dueDate = new DueDate();
        dueDate.setDate(date);
        dueDate.setTime(time);

        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setUserId("UID");
        taskRequest.setMessage("read");
        taskRequest.setDueDate(dueDate);

       assertThrows(NoUserExist.class,()->todoServices.addTask(taskRequest));

    }


    @Test void registerUser_addTask_Without_Login_Throws_Exception(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        todoServices.register(registerRequest);
        boolean isLogin = todoServices.findAccountBelongingTo("username").isLogin();
        assertFalse(isLogin);


        Date date = new Date();
        date.setMonth("October");
        date.setDay("9");
        date.setYear("2023");

        Time time = new Time();
        time.setHours("9");
        time.setMinutes("15");
        time.setSeconds("24");

        DueDate dueDate = new DueDate();
        dueDate.setDate(date);
        dueDate.setTime(time);

        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setUserId("UID1");
        taskRequest.setMessage("read");
        taskRequest.setDueDate(dueDate);

        assertThrows(NotLoginException.class,()->todoServices.addTask(taskRequest));

    }

    @Test void registerUser_Login_addTask_addTaskAgain_CompleteOneTask(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        todoServices.register(registerRequest);
        boolean isLogin = todoServices.findAccountBelongingTo("username").isLogin();
        assertFalse(isLogin);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");

        todoServices.login(loginRequest);
        isLogin = todoServices.findAccountBelongingTo("username").isLogin();
        assertTrue(isLogin);

        Date date = new Date();
        date.setMonth("October");
        date.setDay("9");
        date.setYear("2023");

        Time time = new Time();
        time.setHours("9");
        time.setMinutes("15");
        time.setSeconds("24");

        DueDate dueDate = new DueDate();
        dueDate.setDate(date);
        dueDate.setTime(time);

        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setUserId("UID1");
        taskRequest.setMessage("read");
        taskRequest.setDueDate(dueDate);


        todoServices.addTask(taskRequest);


        TaskRequest taskRequest2 = new TaskRequest();
        taskRequest2.setUserId("UID1");
        taskRequest2.setMessage("read");
        taskRequest2.setDueDate(dueDate);

        todoServices.addTask(taskRequest2);

        CompleteTaskRequest completeTaskRequest = new CompleteTaskRequest();
        completeTaskRequest.setUserId("UID1");
        completeTaskRequest.setTaskId("TID1");


        todoServices.completeTask(completeTaskRequest);




        boolean status1 = taskRepository.findByTaskId("TID1").isStatus();
        boolean status2 = taskRepository.findByTaskId("TID2").isStatus();


        assertTrue(status1);
        assertFalse(status2);
    }


    @Test void registerUser_Login_addTask_addTaskAgain_CompleteTwoTask(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        todoServices.register(registerRequest);
        boolean isLogin = todoServices.findAccountBelongingTo("username").isLogin();
        assertFalse(isLogin);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");

        todoServices.login(loginRequest);
        isLogin = todoServices.findAccountBelongingTo("username").isLogin();
        assertTrue(isLogin);

        Date date = new Date();
        date.setMonth("October");
        date.setDay("9");
        date.setYear("2023");

        Time time = new Time();
        time.setHours("9");
        time.setMinutes("15");
        time.setSeconds("24");

        DueDate dueDate = new DueDate();
        dueDate.setDate(date);
        dueDate.setTime(time);

        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setUserId("UID1");
        taskRequest.setMessage("read");
        taskRequest.setDueDate(dueDate);


        todoServices.addTask(taskRequest);


        TaskRequest taskRequest2 = new TaskRequest();
        taskRequest2.setUserId("UID1");
        taskRequest2.setMessage("read");
        taskRequest2.setDueDate(dueDate);

        todoServices.addTask(taskRequest2);


        CompleteTaskRequest completeTaskRequest = new CompleteTaskRequest();
        completeTaskRequest.setUserId("UID1");
        completeTaskRequest.setTaskId("TID1");

        todoServices.completeTask(completeTaskRequest);


        CompleteTaskRequest completeTaskRequest2 = new CompleteTaskRequest();
        completeTaskRequest2.setUserId("UID1");
        completeTaskRequest2.setTaskId("TID2");


        todoServices.completeTask(completeTaskRequest2);





        boolean status1 = taskRepository.findByTaskId("TID1").isStatus();
        boolean status2 = taskRepository.findByTaskId("TID2").isStatus();


        assertTrue(status1);
        assertTrue(status2);
    }


    @Test void registerUser_Login_addTask_CompleteTask_With_Wrong_UserId_Throws_Exception(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        todoServices.register(registerRequest);
        boolean isLogin = todoServices.findAccountBelongingTo("username").isLogin();
        assertFalse(isLogin);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");

        todoServices.login(loginRequest);
        isLogin = todoServices.findAccountBelongingTo("username").isLogin();
        assertTrue(isLogin);

        Date date = new Date();
        date.setMonth("October");
        date.setDay("9");
        date.setYear("2023");

        Time time = new Time();
        time.setHours("9");
        time.setMinutes("15");
        time.setSeconds("24");

        DueDate dueDate = new DueDate();
        dueDate.setDate(date);
        dueDate.setTime(time);

        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setUserId("UID1");
        taskRequest.setMessage("read");
        taskRequest.setDueDate(dueDate);


        todoServices.addTask(taskRequest);

        CompleteTaskRequest completeTaskRequest = new CompleteTaskRequest();
        completeTaskRequest.setUserId("UID");
        completeTaskRequest.setTaskId("TID1");





        assertThrows(NoUserExist.class,()->todoServices.completeTask(completeTaskRequest));

    }



    @Test void registerUser_Login_addTask_CompleteTask_With_Wrong_TaskId_Throws_Exception(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        todoServices.register(registerRequest);
        boolean isLogin = todoServices.findAccountBelongingTo("username").isLogin();
        assertFalse(isLogin);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");

        todoServices.login(loginRequest);
        isLogin = todoServices.findAccountBelongingTo("username").isLogin();
        assertTrue(isLogin);

        Date date = new Date();
        date.setMonth("October");
        date.setDay("9");
        date.setYear("2023");

        Time time = new Time();
        time.setHours("9");
        time.setMinutes("15");
        time.setSeconds("24");

        DueDate dueDate = new DueDate();
        dueDate.setDate(date);
        dueDate.setTime(time);

        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setUserId("UID1");
        taskRequest.setMessage("read");
        taskRequest.setDueDate(dueDate);


        todoServices.addTask(taskRequest);

        CompleteTaskRequest completeTaskRequest = new CompleteTaskRequest();
        completeTaskRequest.setUserId("UID1");
        completeTaskRequest.setTaskId("TID2");


        assertThrows(TaskIdNotFoundException.class,()->todoServices.completeTask(completeTaskRequest));

    }



    @Test void registerUserTwice_LoginTwice_AddTaskTwice_For_The_Two_Users(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        todoServices.register(registerRequest);




        RegisterRequest registerRequest2 = new RegisterRequest();
        registerRequest2.setUsername("name");
        registerRequest2.setPassword("password");
        todoServices.register(registerRequest2);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");

        todoServices.login(loginRequest);

        LoginRequest loginRequest2 = new LoginRequest();
        loginRequest2.setUsername("name");
        loginRequest2.setPassword("password");
        todoServices.login(loginRequest2);


        Date date = new Date();
        date.setMonth("October");
        date.setDay("9");
        date.setYear("2023");

        Time time = new Time();
        time.setHours("9");
        time.setMinutes("15");
        time.setSeconds("24");

        DueDate dueDate = new DueDate();
        dueDate.setDate(date);
        dueDate.setTime(time);

        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setUserId("UID1");
        taskRequest.setMessage("read");
        taskRequest.setDueDate(dueDate);


        TaskRequest taskRequest2 = new TaskRequest();
        taskRequest2.setUserId("UID2");
        taskRequest2.setMessage("read");
        taskRequest2.setDueDate(dueDate);


        System.out.println(todoServices.addTask(taskRequest));
        System.out.println(todoServices.addTask(taskRequest));

        System.out.println(todoServices.addTask(taskRequest2));
        System.out.println(todoServices.addTask(taskRequest2));


    }


    @Test void registerUser_Login_addTask_addTaskAgain_CompleteTwoTask_DeleteOneTask(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        todoServices.register(registerRequest);
        boolean isLogin = todoServices.findAccountBelongingTo("username").isLogin();
        assertFalse(isLogin);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");

        todoServices.login(loginRequest);
        isLogin = todoServices.findAccountBelongingTo("username").isLogin();
        assertTrue(isLogin);

        Date date = new Date();
        date.setMonth("October");
        date.setDay("9");
        date.setYear("2023");

        Time time = new Time();
        time.setHours("9");
        time.setMinutes("15");
        time.setSeconds("24");

        DueDate dueDate = new DueDate();
        dueDate.setDate(date);
        dueDate.setTime(time);

        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setUserId("UID1");
        taskRequest.setMessage("read");
        taskRequest.setDueDate(dueDate);


        todoServices.addTask(taskRequest);


        TaskRequest taskRequest2 = new TaskRequest();
        taskRequest2.setUserId("UID1");
        taskRequest2.setMessage("read");
        taskRequest2.setDueDate(dueDate);

        todoServices.addTask(taskRequest2);


        CompleteTaskRequest completeTaskRequest = new CompleteTaskRequest();
        completeTaskRequest.setUserId("UID1");
        completeTaskRequest.setTaskId("TID1");

        todoServices.completeTask(completeTaskRequest);


        CompleteTaskRequest completeTaskRequest2 = new CompleteTaskRequest();
        completeTaskRequest2.setUserId("UID1");
        completeTaskRequest2.setTaskId("TID2");


        todoServices.completeTask(completeTaskRequest2);


        boolean status1 = taskRepository.findByTaskId("TID1").isStatus();
        boolean status2 = taskRepository.findByTaskId("TID2").isStatus();

        assertEquals(2,taskRepository.count());
        assertTrue(status1);
        assertTrue(status2);

        DeleteTaskRequest deleteTaskRequest = new DeleteTaskRequest();
        deleteTaskRequest.setTaskId("TID1");
        deleteTaskRequest.setUserId("UID1");
        deleteTaskRequest.setPassword("password");


        todoServices.deleteTask(deleteTaskRequest);
        assertEquals(1,taskRepository.count());
        assertThrows(TaskIdNotFoundException.class,()->todoServices.findTaskById("TID1"));

    }




    @Test void registerUser_Login_addTask_addTaskAgain_CompleteTwoTask_DeleteOneTask_With_Wrong_Password(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        todoServices.register(registerRequest);
        boolean isLogin = todoServices.findAccountBelongingTo("username").isLogin();
        assertFalse(isLogin);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");

        todoServices.login(loginRequest);
        isLogin = todoServices.findAccountBelongingTo("username").isLogin();
        assertTrue(isLogin);

        Date date = new Date();
        date.setMonth("October");
        date.setDay("9");
        date.setYear("2023");

        Time time = new Time();
        time.setHours("9");
        time.setMinutes("15");
        time.setSeconds("24");

        DueDate dueDate = new DueDate();
        dueDate.setDate(date);
        dueDate.setTime(time);

        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setUserId("UID1");
        taskRequest.setMessage("read");
        taskRequest.setDueDate(dueDate);


        todoServices.addTask(taskRequest);


        TaskRequest taskRequest2 = new TaskRequest();
        taskRequest2.setUserId("UID1");
        taskRequest2.setMessage("read");
        taskRequest2.setDueDate(dueDate);

        todoServices.addTask(taskRequest2);


        CompleteTaskRequest completeTaskRequest = new CompleteTaskRequest();
        completeTaskRequest.setUserId("UID1");
        completeTaskRequest.setTaskId("TID1");

        todoServices.completeTask(completeTaskRequest);


        CompleteTaskRequest completeTaskRequest2 = new CompleteTaskRequest();
        completeTaskRequest2.setUserId("UID1");
        completeTaskRequest2.setTaskId("TID2");


        todoServices.completeTask(completeTaskRequest2);





        boolean status1 = taskRepository.findByTaskId("TID1").isStatus();
        boolean status2 = taskRepository.findByTaskId("TID2").isStatus();

        assertEquals(2,taskRepository.count());
        assertTrue(status1);
        assertTrue(status2);

        DeleteTaskRequest deleteTaskRequest = new DeleteTaskRequest();
        deleteTaskRequest.setTaskId("TID1");
        deleteTaskRequest.setUserId("UID1");
        deleteTaskRequest.setPassword("word");


        assertThrows(InvalidDetailsException.class,()->todoServices.deleteTask(deleteTaskRequest));


    }



    @Test void registerUser_Login_addTask_addTaskAgain_CompleteTwoTask_DeleteOneTask_With_Wrong_UserId(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        todoServices.register(registerRequest);
        boolean isLogin = todoServices.findAccountBelongingTo("username").isLogin();
        assertFalse(isLogin);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");

        todoServices.login(loginRequest);
        isLogin = todoServices.findAccountBelongingTo("username").isLogin();
        assertTrue(isLogin);

        Date date = new Date();
        date.setMonth("October");
        date.setDay("9");
        date.setYear("2023");

        Time time = new Time();
        time.setHours("9");
        time.setMinutes("15");
        time.setSeconds("24");

        DueDate dueDate = new DueDate();
        dueDate.setDate(date);
        dueDate.setTime(time);

        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setUserId("UID1");
        taskRequest.setMessage("read");
        taskRequest.setDueDate(dueDate);


        todoServices.addTask(taskRequest);


        TaskRequest taskRequest2 = new TaskRequest();
        taskRequest2.setUserId("UID1");
        taskRequest2.setMessage("read");
        taskRequest2.setDueDate(dueDate);

        todoServices.addTask(taskRequest2);


        CompleteTaskRequest completeTaskRequest = new CompleteTaskRequest();
        completeTaskRequest.setUserId("UID1");
        completeTaskRequest.setTaskId("TID1");

        todoServices.completeTask(completeTaskRequest);


        CompleteTaskRequest completeTaskRequest2 = new CompleteTaskRequest();
        completeTaskRequest2.setUserId("UID1");
        completeTaskRequest2.setTaskId("TID2");


        todoServices.completeTask(completeTaskRequest2);


        boolean status1 = taskRepository.findByTaskId("TID1").isStatus();
        boolean status2 = taskRepository.findByTaskId("TID2").isStatus();

        assertEquals(2,taskRepository.count());
        assertTrue(status1);
        assertTrue(status2);

        DeleteTaskRequest deleteTaskRequest = new DeleteTaskRequest();
        deleteTaskRequest.setTaskId("TID1");
        deleteTaskRequest.setUserId("UID");
        deleteTaskRequest.setPassword("password");


        assertThrows(NoUserExist.class,()->todoServices.deleteTask(deleteTaskRequest));


    }


    @Test void registerUser_Login_addTask_addTaskAgain_CompleteTwoTask_DeleteOneTask_With_Wrong_TaskId() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        todoServices.register(registerRequest);
        boolean isLogin = todoServices.findAccountBelongingTo("username").isLogin();
        assertFalse(isLogin);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");

        todoServices.login(loginRequest);
        isLogin = todoServices.findAccountBelongingTo("username").isLogin();
        assertTrue(isLogin);

        Date date = new Date();
        date.setMonth("October");
        date.setDay("9");
        date.setYear("2023");

        Time time = new Time();
        time.setHours("9");
        time.setMinutes("15");
        time.setSeconds("24");

        DueDate dueDate = new DueDate();
        dueDate.setDate(date);
        dueDate.setTime(time);

        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setUserId("UID1");
        taskRequest.setMessage("read");
        taskRequest.setDueDate(dueDate);


        todoServices.addTask(taskRequest);


        TaskRequest taskRequest2 = new TaskRequest();
        taskRequest2.setUserId("UID1");
        taskRequest2.setMessage("read");
        taskRequest2.setDueDate(dueDate);

        todoServices.addTask(taskRequest2);


        CompleteTaskRequest completeTaskRequest = new CompleteTaskRequest();
        completeTaskRequest.setUserId("UID1");
        completeTaskRequest.setTaskId("TID1");

        todoServices.completeTask(completeTaskRequest);


        CompleteTaskRequest completeTaskRequest2 = new CompleteTaskRequest();
        completeTaskRequest2.setUserId("UID1");
        completeTaskRequest2.setTaskId("TID2");


        todoServices.completeTask(completeTaskRequest2);


        boolean status1 = taskRepository.findByTaskId("TID1").isStatus();
        boolean status2 = taskRepository.findByTaskId("TID2").isStatus();

        assertEquals(2, taskRepository.count());
        assertTrue(status1);
        assertTrue(status2);

        DeleteTaskRequest deleteTaskRequest = new DeleteTaskRequest();
        deleteTaskRequest.setTaskId("TID");
        deleteTaskRequest.setUserId("UID1");
        deleteTaskRequest.setPassword("password");


        assertThrows(TaskIdNotFoundException.class, () -> todoServices.deleteTask(deleteTaskRequest));

    }



    @Test void registerUser_Login_addTask_addTaskAgain_CompleteTwoTask_FindTask(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        todoServices.register(registerRequest);
        boolean isLogin = todoServices.findAccountBelongingTo("username").isLogin();
        assertFalse(isLogin);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");

        todoServices.login(loginRequest);
        isLogin = todoServices.findAccountBelongingTo("username").isLogin();
        assertTrue(isLogin);

        Date date = new Date();
        date.setMonth("October");
        date.setDay("9");
        date.setYear("2023");

        Time time = new Time();
        time.setHours("9");
        time.setMinutes("15");
        time.setSeconds("24");

        DueDate dueDate = new DueDate();
        dueDate.setDate(date);
        dueDate.setTime(time);

        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setUserId("UID1");
        taskRequest.setMessage("read");
        taskRequest.setDueDate(dueDate);


        todoServices.addTask(taskRequest);


        TaskRequest taskRequest2 = new TaskRequest();
        taskRequest2.setUserId("UID1");
        taskRequest2.setMessage("read");
        taskRequest2.setDueDate(dueDate);

        todoServices.addTask(taskRequest2);


        CompleteTaskRequest completeTaskRequest = new CompleteTaskRequest();
        completeTaskRequest.setUserId("UID1");
        completeTaskRequest.setTaskId("TID1");

        todoServices.completeTask(completeTaskRequest);


        CompleteTaskRequest completeTaskRequest2 = new CompleteTaskRequest();
        completeTaskRequest2.setUserId("UID1");
        completeTaskRequest2.setTaskId("TID2");


        todoServices.completeTask(completeTaskRequest2);
        Task task = todoServices.findTaskBy("UID1", "TID1");
        assertNotNull(task);
    }


    @Test void registerUser_Login_addTask_addTaskAgain_CompleteOneTask_FindAllTask(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        todoServices.register(registerRequest);
        boolean isLogin = todoServices.findAccountBelongingTo("username").isLogin();
        assertFalse(isLogin);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");

        todoServices.login(loginRequest);
        isLogin = todoServices.findAccountBelongingTo("username").isLogin();
        assertTrue(isLogin);

        Date date = new Date();
        date.setMonth("October");
        date.setDay("9");
        date.setYear("2023");

        Time time = new Time();
        time.setHours("9");
        time.setMinutes("15");
        time.setSeconds("24");

        DueDate dueDate = new DueDate();
        dueDate.setDate(date);
        dueDate.setTime(time);

        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setUserId("UID1");
        taskRequest.setMessage("read");
        taskRequest.setDueDate(dueDate);


        todoServices.addTask(taskRequest);


        TaskRequest taskRequest2 = new TaskRequest();
        taskRequest2.setUserId("UID1");
        taskRequest2.setMessage("read");
        taskRequest2.setDueDate(dueDate);

        todoServices.addTask(taskRequest2);


        CompleteTaskRequest completeTaskRequest = new CompleteTaskRequest();
        completeTaskRequest.setUserId("UID1");
        completeTaskRequest.setTaskId("TID1");

        todoServices.completeTask(completeTaskRequest);

        List<Task> task = todoServices.findAllTask("UID1");
        assertNotNull(task);
    }

}