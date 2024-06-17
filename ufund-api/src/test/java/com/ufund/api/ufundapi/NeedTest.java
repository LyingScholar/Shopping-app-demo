package com.ufund.api.ufundapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class NeedTest {
    /**
     * Testing basic constructor of Need class
     */
    @Test
    public void testConstructor() {
        Need need = new Need(1, "Food", 10, 100, "Grocery");
        assertEquals(1, need.getId());
        assertEquals("Food", need.getName());
        assertEquals(10, need.getQuantity());
        assertEquals(100, need.getCost());
        assertEquals("Grocery", need.getType());
    }

    /**
     * testing Name Setter
     */
    @Test
    public void testSetName() {
        Need need = new Need(1, "Food", 10, 100, "Grocery");
        need.setName("Water");
        assertEquals("Water", need.getName());
    }


    /**
     * testing toString
     */
    @Test
    public void testToString() {
        Need need = new Need(1, "Food", 10, 100, "Grocery");
        String expected = "need [id=1, name=Food]";
        assertEquals(expected, need.toString());
    }

    /**
     * test for other basic getters
     */
    @Test
    public void testGetQuantityCostAndType() {
        Need need = new Need(1, "Food", 10, 100, "grocery");
        // assertEquals(20, need.getQuantity());
        assertEquals(10, need.getQuantity());
        assertEquals(100, need.getCost());
        assertEquals("grocery", need.getType());

    }

}