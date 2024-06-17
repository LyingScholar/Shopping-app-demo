package com.ufund.api.ufundapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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

    /**
     * edge case of negative ID, IsPossible
     */
    @Test
    public void testConstructorWithNegatve() {
        Need need = new Need(-1, "Food", 10, 100, "Grocery");
        assertEquals(-1, need.getId());
    }
    /**
     * edge case of zero items, IsPossible
     */
    @Test
    public void testConstructorWithZeroQuant() {
        Need need = new Need(1, "Food", 0, 100, "Grocery");
        assertEquals(0, need.getQuantity());
    }

    /**
     * edge case of zero cost, IsPossible
     */
    @Test
    public void testConstructorWithZeroCost() {
        Need need = new Need(1, "Food", 10, 0, "Grocery");
        assertEquals(0, need.getCost());
    }


    /**
     * edge case of null name, IsPossible
     */
    @Test
    public void testSetNameWithNull() {
        Need need = new Need(1, "Food", 10, 100, "Grocery");
        need.setName(null);
        assertNull(need.getName());
    }

    /**
     * edge case of empty String name, IsPossible
     */
    @Test
    public void testSetNameWithEmptyString() {
        Need need = new Need(1, "Food", 10, 100, "Grocery");
        need.setName("");
        assertEquals("", need.getName());
    }

}