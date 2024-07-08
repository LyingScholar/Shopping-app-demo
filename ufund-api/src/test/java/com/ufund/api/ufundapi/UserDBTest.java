package com.ufund.api.ufundapi;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.fasterxml.jackson.databind.ObjectMapper;

class UserDBTest {

    @Mock
    private ObjectMapper objectMapper;

    private UserDB userDB;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        when(objectMapper.readValue(any(File.class), eq(User[].class))).thenReturn(new User[]{});
        userDB = new UserDB("test.json", objectMapper);
    }

    @Test
    void testLogin_UserDoesNotExist() {
        int result = userDB.login("nonexistantUser");
        assertEquals(1, result);
    }


    @Test
    void testLogin_UserAlreadyLoggedIn() throws IOException {
        User user = new User(1, "testUser", false);
        userDB.createUser(user);
        userDB.login("testUser");
        int result = userDB.login("testUser");
        assertEquals(2, result);
    }


    @Test
    void testLogin_SuccessfulLogin() throws IOException {
        User user = new User(1, "testUser", false);
        userDB.createUser(user);
        int result = userDB.login("testUser");
        assertEquals(3, result);
    }

    @Test
    void testGetUsers() throws IOException {
        User user1 = new User(1, "user1", false);
        User user2 = new User(2, "user2", true);
        userDB.createUser(user1);
        userDB.createUser(user2);

        User[] users = userDB.getUsers();
        assertEquals(2, users.length);
        assertEquals("user1", users[0].getName());
        assertEquals("user2", users[1].getName());
    }

    @Test
    void testFindUsers() throws IOException {
        User user1 = new User(1, "user1", false);
        User user2 = new User(2, "user2", true);
        User user3 = new User(3, "admin", true);
        userDB.createUser(user1);
        userDB.createUser(user2);
        userDB.createUser(user3);

        User[] foundUsers = userDB.findUsers("user");
        
        
        assertEquals(2, foundUsers.length);
        assertEquals("user1", foundUsers[0].getName());
        assertEquals("user2", foundUsers[1].getName());
    }

    @Test
    void testGetUser() throws IOException {
        User user = new User(1, "testUser", false);
        userDB.createUser(user);

        User foundUser = userDB.getUser(1);
        assertNotNull(foundUser);
        assertEquals("testUser", foundUser.getName());


        User nonexistantUser = userDB.getUser(2);
        assertNull(nonexistantUser);
    }

    @Test
    void testGetUserByName() throws IOException {
        User user = new User(1, "testUser", false);
        userDB.createUser(user);

        User foundUser = userDB.getUserByName("testUser");
        assertNotNull(foundUser);
        assertEquals(1, foundUser.getId());

        User nonexistantUser = userDB.getUserByName("nonexisentUser");
        assertNull(nonexistantUser);
    }

    @Test
    void testCheckForAUser() throws IOException {
        User user = new User(1, "testUser", false);
        userDB.createUser(user);

        assertTrue(userDB.checkForAUser("testUser"));
        assertFalse(userDB.checkForAUser("nonexisentUser"));
    }

    @Test
    void testCheckForALoggedInUser() throws IOException {
        User user = new User(1, "testUser", false);
        userDB.createUser(user);
        userDB.login("testUser");

        assertTrue(userDB.checkForALoggedInUser("testUser"));
        assertFalse(userDB.checkForALoggedInUser("nonexisentUser"));
    }

    @Test
    void testCreateUser() throws IOException {
        User newUser = new User(0, "newUser", false);
        User createdUser = userDB.createUser(newUser);

        assertNotNull(createdUser);
        assertEquals("newUser", createdUser.getName());
        assertNotEquals(0, createdUser.getId());

        User duplicateUser = userDB.createUser(newUser);
        assertNull(duplicateUser);
    }


    @Test
    void testUpdateUser() throws IOException {
        User user = new User(1, "testUser", false);
        userDB.createUser(user);

        User updatedUser = new User(1, "updatedUser", true);
        User result = userDB.updateUser(updatedUser);

        assertNotNull(result);
        assertEquals("updatedUser", result.getName());
        assertTrue(result.isAdmin());

        User nonexistantUser = new User(2, "nonexistant", false);
        User failedUpdate = userDB.updateUser(nonexistantUser);
        assertNull(failedUpdate);
    }



    @Test
    void testDeleteUser() throws IOException {
        User user = new User(1, "testUser", false);
        userDB.createUser(user);

        boolean deleted = userDB.deleteUser(1);
        assertTrue(deleted);

        boolean failedDelete = userDB.deleteUser(2);
        assertFalse(failedDelete);
    }
}