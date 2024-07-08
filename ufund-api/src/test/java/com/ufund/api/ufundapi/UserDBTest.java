package com.ufund.api.ufundapi;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UserDBTest {

    /**
     * @throws IOException
     * created a test file to write sample users and act as UserDB
     * testing each value of each user to verify read, write capabilities
     * also tested all users get retrieved successfully
     */
    @Test
    public void testGetAllUsers() throws IOException {
        File testFile  = File.createTempFile("test", ".json");
        ObjectMapper objectMapper = new ObjectMapper();
        User[] initialUsers = {
            new User(1, "User 1", false),
            new User(2, "User 2", true),
             new User(3, "User 3", false)
        };
        
        objectMapper.writeValue(testFile, initialUsers);
        
        UserDB userDB = new UserDB(testFile.getAbsolutePath(), objectMapper);
        
        User[] retrievedUsers = userDB.getUsers();
        
        assertEquals(3, retrievedUsers.length);
        
        assertEquals(1, retrievedUsers[0].getId());
        assertEquals("User 1", retrievedUsers[0].getName());
        assertFalse(retrievedUsers[0].isAdmin());
        
        assertEquals(2, retrievedUsers[1].getId());
        assertEquals("User 2", retrievedUsers[1].getName());
        assertTrue(retrievedUsers[1].isAdmin());
        
        assertEquals(3, retrievedUsers[2].getId());
        assertEquals("User 3", retrievedUsers[2].getName());
        assertFalse(retrievedUsers[2].isAdmin());
    }
    
    /**
     * @throws IOException
     * created a test file to write sample users and act as UserDB
     * testing each value of each user to verify find capabilities
     * also tested all users get found successfully
     */
    @Test
    public void testFindUsers() throws IOException {
        File testFile  = File.createTempFile("test", ".json");
        
        ObjectMapper objectMapper = new ObjectMapper();
        
        User[] testUsers = {
            new User(1, "User 1A", false),
            new User(2, "User 2" , true),
            new User(3, "User 1B" , false)
        };
        
        objectMapper.writeValue(testFile, testUsers);
        
        UserDB userDB = new UserDB(testFile.getAbsolutePath(), objectMapper);
        
        User[] foundUsers = userDB.findUsers("User 1");
        
        assertEquals(2, foundUsers.length);

        assertEquals(1, foundUsers[0].getId());
        assertEquals("User 1A", foundUsers[0].getName());
        assertFalse(foundUsers[0].isAdmin());
        
        assertEquals(3, foundUsers[1].getId());
        assertEquals("User 1B", foundUsers[1].getName());
        assertFalse(foundUsers[1].isAdmin());
    }
    
    @Test
    public void testCheckForAUser() throws IOException {
        File testFile  = File.createTempFile("test", ".json");
        ObjectMapper objectMapper = new ObjectMapper();
        User[] initialUsers = {
            new User(1, "User 1", false),
            new User(2, "User 2" , true)
        };
        objectMapper.writeValue(testFile, initialUsers);
        
        UserDB userDB = new UserDB(testFile.getAbsolutePath(), objectMapper);
        
        assertTrue(userDB.checkForAUser("User 1"));
        assertFalse(userDB.checkForAUser("User 3"));
    }

    /**
     * @throws IOException
     * created a test file to write sample users and act as UserDB
     * testing each value of the required user
     */
    @Test
    public void testGetUser() throws IOException {
        
        File testFile  = File.createTempFile("test", ".json");
        
        ObjectMapper objectMapper = new ObjectMapper();
        
        User[] initialUsers = {
            new User(1, "User 1", false),
            new User(2, "User 2" , true),
            new User(3, "User 3", false)
        };
        
        objectMapper.writeValue(testFile, initialUsers);
        UserDB userDB = new UserDB(testFile.getAbsolutePath(), objectMapper);
        User retrievedUser = userDB.getUser(2);
        
        assertNotNull(retrievedUser);
        
        assertEquals(2, retrievedUser.getId());
        assertEquals("User 2", retrievedUser.getName());
        assertTrue(retrievedUser.isAdmin());
    }

    /**
     * @throws IOException
     * created a test file to write sample users and act as UserDB
     * testing the function, forcing it to search for non-existent user
     */
    @Test
    public void testGetUserNonExistent() throws IOException {
        File testFile  = File.createTempFile("test", ".json");
        ObjectMapper objectMapper = new  ObjectMapper();
        User[] initialUsers = {
            new User(1, "User 1", false)
        };


        objectMapper.writeValue(testFile, initialUsers);
    
        UserDB userDB = new UserDB(testFile.getAbsolutePath(), objectMapper);
    
        User retrievedUser = userDB.getUser(99);
        assertNull(retrievedUser);
    }



    @Test
    public void testCreateUser() throws IOException {
        File testFile  = File.createTempFile("test", ".json");
        ObjectMapper objectMapper = new ObjectMapper();
        User[] initialUsers = {};
        objectMapper.writeValue(testFile, initialUsers);
    
        UserDB userDB = new UserDB(testFile.getAbsolutePath(), objectMapper);
        User newUser = new User(0, "New User", true);
        User createdUser = userDB.createUser(newUser);
    
        assertNotNull(createdUser);
        assertEquals("New User", createdUser.getName());
        assertTrue(createdUser.getId() > 0);
        assertTrue(createdUser.isAdmin());
    
        // Try creating a duplicate user (should fail)
        User duplicateUser = userDB.createUser(newUser);
        assertNull(duplicateUser);
    }




    /**
     * @throws IOException
     * created a test file to write sample users and act as UserDB
     * testing deletion of a specific user
     * also testing deletion of non existent user
     */
    @Test
    public void testDeleteUser() throws IOException {
        File testFile  = File.createTempFile("test", ".json");
        ObjectMapper objectMapper = new ObjectMapper();

        User[] initialUsers = {
            new User(1, "User 1", false),
            new User(2, "User 2", true),
            new User(3, "User 3", false)
        };
        objectMapper.writeValue(testFile, initialUsers);
        UserDB userDB = new UserDB(testFile.getAbsolutePath(), objectMapper);

        boolean deleted = userDB.deleteUser(initialUsers[1].getId());
        assertTrue(deleted);
        assertNull(userDB.getUser((initialUsers[1]).getId()));
       
        deleted = userDB.deleteUser(initialUsers[1].getId());
        assertFalse(deleted);
    }




    /**
     * @throws IOException
     * created a test file to write sample users and act as UserDB
     * testing addition of updating a user in the UserDB
     * also testing updating non existent user
     */
    @Test
    public void testUpdateUser() throws IOException {
        File testFile = File.createTempFile("test", ".json");
        ObjectMapper  objectMapper = new ObjectMapper();
        User[] initialUsers = {
            new User(1, "User 1", false)
        };
        objectMapper.writeValue(testFile, initialUsers);
    
        UserDB userDB = new UserDB(testFile.getAbsolutePath(), objectMapper);
    
        User updatedUser  = new User(1, "Updated User", true);
        User result = userDB.updateUser(updatedUser);
    
        assertNotNull(result);
        assertEquals("Updated User", result.getName());
        assertTrue(result.isAdmin());
    


        User nonExistentUser = new User(99, "Non-existent", false);
        result = userDB.updateUser(nonExistentUser);
        assertNull(result);
    }
}