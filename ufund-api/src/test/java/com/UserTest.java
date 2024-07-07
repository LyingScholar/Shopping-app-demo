package com;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.ufund.api.ufundapi.User;


class UserTest {
    /**
     * Testing basic constructor of User class
     */
    @Test
    void testUserConstructor() {
        User user = new User(1, "John Doe", true);
        assertEquals(1, user.getId());
        assertEquals("John Doe", user.getName());
        assertTrue(user.isAdmin());
    }

    /**
     * testing Name Setter
     */
    @Test
    void testGetName() {
        User user = new User(1, "Jane Doe", false);
        assertEquals("Jane Doe", user.getName());
    }


    /**
     * testing to Check Admin
     */
    @Test
    void testIsAdmin() {
        User adminUser = new User(1, "Admin", true);
        User regularUser = new User(2, "Regular", false);
        
        assertTrue(adminUser.isAdmin());
        assertFalse(regularUser.isAdmin());
    }


    /**
     * testing GetId
     */
    @Test
    void testGetId() {
        User user = new User(42, "Test User", false);
        assertEquals(42, user.getId());
    }

    /**
     * testing toString
     */
    @Test
    void testToString() {
        User user = new User(1, "John Doe", true);
        String expected = "User [id=1, name=John Doe, admin=true]";
        System.out.println(user.toString());
        System.out.println(expected);
        assertEquals(expected, user.toString());
    }

}
