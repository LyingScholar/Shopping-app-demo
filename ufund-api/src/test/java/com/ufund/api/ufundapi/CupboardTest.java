package com.ufund.api.ufundapi;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CupboardTest {
    /**
     * @throws IOException
     * created a test file to write sample needs and act as cupboard
     * testing each value of each need to verify read, write capabilities
     * also tested all needs get retrieved successfully
     */
    @Test
    public void testGetNeeds() throws IOException {
            File testFile = File.createTempFile("test", ".json");
            ObjectMapper objectMapper = new ObjectMapper();
            Need[] initialNeeds = {
                new Need(1, "Need 1", 10, 100, "Type A"),
                new Need(2, "Need 2", 5, 50, "Type B"),
                new Need(3, "Need 3", 15, 75, "Type A")
            };

                objectMapper.writeValue(testFile, initialNeeds);
                Cupboard cupboard = new Cupboard(testFile.getAbsolutePath(), objectMapper);
                Need[] retrievedNeeds = cupboard.getNeeds();

        //Verifying all needs get retrieved

        assertEquals(3, retrievedNeeds.length);
        
        //Verify the contents of retrieved needs

        assertEquals(1, retrievedNeeds[0].getId());
        assertEquals("Need 1", retrievedNeeds[0].getName());
        assertEquals(10, retrievedNeeds[0].getQuantity());
        assertEquals(100, retrievedNeeds[0].getCost());
        assertEquals("Type A", retrievedNeeds[0].getType());
        

        assertEquals(2, retrievedNeeds[1].getId());
        assertEquals("Need 2", retrievedNeeds[1].getName());
        assertEquals(5, retrievedNeeds[1].getQuantity());
        assertEquals(50, retrievedNeeds[1].getCost());
        assertEquals("Type B", retrievedNeeds[1].getType());
        

        assertEquals(3, retrievedNeeds[2].getId());
        assertEquals("Need 3", retrievedNeeds[2].getName());
        assertEquals(15, retrievedNeeds[2].getQuantity());
        assertEquals(75, retrievedNeeds[2].getCost());
        assertEquals("Type A", retrievedNeeds[2].getType());

    }
    
    /**
     * @throws IOException
     * created a test file to write sample needs and act as cupboard
     * testing each value of each need to verify find capabilities
     * also tested all needs get found successfully
     */
    @Test
    public void testFindNeeds() throws IOException {
        //similar setup to testGetNeeds

                File testFile = File.createTempFile("test", ".json");
                ObjectMapper objectMapper = new ObjectMapper();
                Need[] testNeeds = {
                    new Need(1, "Need 1A", 10, 100, "Type A"),
                    new Need(2, "Need 2", 5, 50, "Type B"),
                    new Need(3, "Need 1B", 15, 75, "Type A")
                };
                objectMapper.writeValue(testFile, testNeeds);
                Cupboard cupboard = new Cupboard(testFile.getAbsolutePath(), objectMapper);
                Need[] foundNeeds = cupboard.findNeeds("Need 1");
                assertEquals(2, foundNeeds.length);
            
        
        // Assertions to verify the contents of the found needs
        assertEquals(1, foundNeeds[0].getId());
        assertEquals("Need 1A", foundNeeds[0].getName());
        assertEquals(10, foundNeeds[0].getQuantity());
        assertEquals(100, foundNeeds[0].getCost());
        assertEquals("Type A", foundNeeds[0].getType());
        

        assertEquals(3, foundNeeds[1].getId());
        assertEquals("Need 1B", foundNeeds[1].getName());
        assertEquals(15, foundNeeds[1].getQuantity());
        assertEquals(75, foundNeeds[1].getCost());
        assertEquals("Type A", foundNeeds[1].getType());

    }
    

    /**
     * @throws IOException
     * created a test file to write sample needs and act as cupboard
     * testing each value of the required need
     */
    @Test
    public void testGetNeed() throws IOException {
                File testFile = File.createTempFile("test", ".json");
                ObjectMapper objectMapper = new ObjectMapper();
                Need[] initialNeeds = {
                    new Need(1, "Need 1", 10, 100, "Type A"),
                    new Need(2, "Need 2", 5, 50, "Type B"),
                    new Need(3, "Need 3", 15, 75, "Type A")
                };
                objectMapper.writeValue(testFile, initialNeeds);
                Cupboard cupboard = new Cupboard(testFile.getAbsolutePath(), objectMapper);
                Need retrievedNeed = cupboard.getNeed(2);
                
        assertNotNull(retrievedNeed);
        

        // Assertions to verify the need details
        assertEquals(2, retrievedNeed.getId());
        assertEquals("Need 2", retrievedNeed.getName());
        assertEquals(5, retrievedNeed.getQuantity());
        assertEquals(50, retrievedNeed.getCost());
        assertEquals("Type B", retrievedNeed.getType());

    }

}