package com.ufund.api.ufundapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class NeedTest {
    @Test
    public void testConstructor() {
        Need need = new Need(1, "Food", 10, 100, "Grocery");
        assertEquals(1, need.getId());
        assertEquals("Food", need.getName());
        assertEquals(10, need.getQuantity());
        assertEquals(100, need.getCost());
        assertEquals("Grocery", need.getType());
    }

    @Test
    public void testSetName() {
        Need need = new Need(1, "Food", 10, 100, "Grocery");
        need.setName("Water");
        assertEquals("Water", need.getName());
    }


    @Test
    public void testToString() {
        Need need = new Need(1, "Food", 10, 100, "Grocery");
        String expected = "need [id=1, name=Food]";
        assertEquals(expected, need.toString());
    }

}