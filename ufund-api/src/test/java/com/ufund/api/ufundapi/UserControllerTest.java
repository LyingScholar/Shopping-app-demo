package com.ufund.api.ufundapi;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class UserControllerTest {

    @Mock
    private UserDB userDB;
    @InjectMocks
    private UserController userController;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUser_ReturnsTheUserIfUserExists() {
        User testUser = new User(1, "Test User", false);
        when(userDB.getUser(1)).thenReturn(testUser);

        ResponseEntity<User> response = userController.getUser(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testUser, response.getBody());
    }

    @Test
    void testGetUser_UserDoesNotExist() {
        when(userDB.getUser(2)).thenReturn(null);

        ResponseEntity<User> response = userController.getUser(2);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    
    @Test
    void testGetUser_ExceptionThrown() {
        when(userDB.getUser(3)).thenThrow(new RuntimeException("Test exception"));


    ResponseEntity<User> response = userController.getUser(3);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertNull(response.getBody());
}

    @Test
    void testGetUsers_UsersFound() {
        User testUser = new User(1, "Test User", false);
        User[] users = {testUser};
        when(userDB.getUsers()).thenReturn(users);

        ResponseEntity<User[]> response = userController.getUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertArrayEquals(users, response.getBody());
    }

    @Test
    void testGetUsers_UsersNotFound() {
        when(userDB.getUsers()).thenReturn(null);

        ResponseEntity<User[]> response = userController.getUsers();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetUsers_ExceptionThrown() {
        Exception mockException = new RuntimeException("Test exception");
        when(userDB.getUsers()).thenThrow(mockException);

        ResponseEntity<User[]> response = userController.getUsers();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }


    @Test
    void testSearchUsers_UsersFound() {
        User testUser = new User(1, "Test User", false);
        User[] users = {testUser};
        when(userDB.findUsers("Test")).thenReturn(users);

        ResponseEntity<User[]> response = userController.searchUsers("Test");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertArrayEquals(users, response.getBody());
    }

    @Test
    void testSearchUsers_UsersNotFound() {
        
        when(userDB.findUsers("Test")).thenReturn(null);

        ResponseEntity<User[]> response = userController.searchUsers("Test");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testSearchUsers_ExceptionThrown() {
        String searchUser = "TestUser";
        when(userDB.findUsers(searchUser)).thenThrow(new RuntimeException("Test exception"));


        ResponseEntity<User[]> response = userController.searchUsers(searchUser);
 
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }


    @Test
    void testCreateUser_Success() throws IOException {
        User testUser = new User(1, "Test User", false);
        when(userDB.createUser(testUser)).thenReturn(testUser);

        ResponseEntity<User> response = userController.createUser(testUser);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(testUser, response.getBody());
    }

    @Test
    void testCreateUser_Conflict() throws IOException {
        User testUser1 = new User(1, "Test User", false);
        User testUser2 = new User(2, "Test User", false);
        when(userDB.createUser(testUser1)).thenReturn(testUser1);
        when(userDB.createUser(testUser2)).thenReturn(null);

        ResponseEntity<User> firstResponse = userController.createUser(testUser1);
        assertEquals(HttpStatus.CREATED, firstResponse.getStatusCode());
        assertEquals(testUser1, firstResponse.getBody());


        ResponseEntity<User> secondResponse = userController.createUser(testUser2);
        assertEquals(HttpStatus.CONFLICT, secondResponse.getStatusCode());
        assertNull(secondResponse.getBody());
    }

    @Test
    void testCreateUser_ExceptionThrown() throws IOException {
        User testUser = new User(1, "Test User", false);
        when(userDB.createUser(testUser)).thenThrow(new IOException("Test IO exception"));

        ResponseEntity<User> response = userController.createUser(testUser);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }


    @Test
    void testUpdateUser_Success() throws IOException {
        User testUser = new User(1, "Test User", false);
        when(userDB.updateUser(testUser)).thenReturn(testUser);

        ResponseEntity<User> response = userController.updateUser(testUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testUser, response.getBody());
    }

    @Test
    void testUpdateUser_UserDoesntExist() throws IOException {
        User testUser = new User(1, "Test User", false);
        when(userDB.updateUser(testUser)).thenReturn(null);

        ResponseEntity<User> response = userController.updateUser(testUser);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testupdateUser_ThrowsError() throws IOException{
        User testUser = new User(1,  "Test User", false);
        when(userDB.updateUser(testUser)).thenThrow(new IOException("Test IO exception"));

        ResponseEntity<User> response = userController.updateUser(testUser);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }


    @Test
    void testDeleteUser_Success() throws IOException {
        when(userDB.deleteUser(1)).thenReturn(true);
        ResponseEntity<User> response = userController.deleteUser(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    @Test
    void testDeleteUser_UserDoesNotExist() throws IOException {
        when(userDB.deleteUser(2)).thenReturn(false);
        ResponseEntity<User> response = userController.deleteUser(2);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteUser_ThrowsError() throws IOException {
        when(userDB.deleteUser(3)).thenThrow(new RuntimeException("Test exception"));
        ResponseEntity<User> response = userController.deleteUser(3);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}