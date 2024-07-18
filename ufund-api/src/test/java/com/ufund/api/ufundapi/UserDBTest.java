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
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.fasterxml.jackson.databind.ObjectMapper;

class UserDBTest {

    @Mock
    private ObjectMapper mockObjectMapper;

    private UserDB userDB;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        
        // Mocking the  empty user and helper arrays

        when(mockObjectMapper.readValue(any(File.class), eq(User[].class))).thenReturn(new User[]{});
        when(mockObjectMapper.readValue(any(File.class), eq(Helper[].class))).thenReturn(new Helper[]{});
        
        userDB = new UserDB("users.json", "helpers.json", mockObjectMapper);
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

    @Test
void testLogout() throws IOException {
    User user = new User(1, "testUser", false);
    userDB.createUser(user);
    userDB.login("testUser");
    
    // Test success
    int result = userDB.logout("testUser");
    assertEquals(3, result);
    
    // Test for not logged in
    result = userDB.logout("testUser");
    assertEquals(2, result);
    
    
    // Test  non-existent user
    result = userDB.logout("nonexistentUser");
    assertEquals(1, result);
}


// @Test
@Test
void testSave() throws IOException {
    User user = new User(1, "testUser", false);
    userDB.createUser(user);

    //null values only worked this way, weird implemetation, do not touch
    doNothing().when(mockObjectMapper).writeValue(any(File.class), any(User[].class));
    doNothing().when(mockObjectMapper).writeValue(any(File.class), any(Helper[].class));

    User newUser = new User(0, "newUser", false);
    User createdUser = userDB.createUser(newUser);

    assertNotNull(createdUser);
    
    // Verify that writeValue was called at least once for each type
    verify(mockObjectMapper, atLeastOnce()).writeValue(any(File.class), any(User[].class));
    verify(mockObjectMapper, atLeastOnce()).writeValue(any(File.class), any(Helper[].class));
    
    // Checking total number of invocations if needed
    // int totalWriteValueCalls = mockingDetails(mockObjectMapper).getInvocations().size();
    // System.out.println("Total writeValue calls: " + totalWriteValueCalls);
}


@Test
void testLoad() throws IOException {
    User[] mockUsers = {new User(1, "user1", false), new User(2, "user2", true)};
    Helper[] mockHelpers = {new Helper(3, "helper1", true)};
    
    when(mockObjectMapper.readValue(any(File.class), eq(User[].class))).thenReturn(mockUsers);
    when(mockObjectMapper.readValue(any(File.class), eq(Helper[].class))).thenReturn(mockHelpers);
    UserDB newUserDB = new UserDB("users.json", "helpers.json", mockObjectMapper);
    
    User[] users = newUserDB.getUsers();
    assertEquals(3, users.length);
    
    User user = newUserDB.getUser(1);
    assertNotNull(user);
    assertEquals("user1", user.getName());
    

    Helper helper = newUserDB.getHelper(3);
    assertNotNull(helper);
    assertEquals("helper1", helper.getName());
}


@Test
void testGetHelper() throws IOException {
    Helper helper = new Helper(1, "testHelper", true);
    userDB.createHelper(helper);
    
    Helper foundHelper = userDB.getHelper(1);
    assertNotNull(foundHelper);
    assertEquals("testHelper", foundHelper.getName());
    
    Helper nonexistentHelper = userDB.getHelper(2);
    assertNull(nonexistentHelper);
}


@Test
void testCreateHelper() throws IOException {
    Helper newHelper = new Helper(0, "newHelper", true);
    Helper createdHelper = userDB.createHelper(newHelper);
    
    assertNotNull(createdHelper);
    assertEquals("newHelper", createdHelper.getName());
    assertNotEquals(0, createdHelper.getId());
    
    // duplicate helper case
    Helper duplicateHelper = userDB.createHelper(newHelper);
    assertNull(duplicateHelper);
}

}