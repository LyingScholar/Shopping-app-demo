package com.ufund.api.ufundapi;

import java.io.IOException;
import java.util.Arrays;

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

public class NeedsControllerTest {

    //we set up a mock environment for the cupboard before every test
    //test names are self explanatory so specific jdocs are omitted
    //response entity and mockito were googled A LOT while writing these tests

    @Mock
    private Cupboard cupboard;
    @InjectMocks
    private NeedController needController;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetNeed_shouldReturnTheNeedIfNeedExists() {
        int id = 1;
        Need need = new Need(id, "Test Need", 10, 100, "Food");
        when(cupboard.getNeed(id)).thenReturn(need);

        ResponseEntity<Need> response = needController.getNeed(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(need, response.getBody());
    }


    @Test
    void testGetNeed_NeedDoesNotExist() {
        int id = 1;
        when(cupboard.getNeed(id)).thenReturn(null);

        ResponseEntity<Need> response = needController.getNeed(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetNeeds() {
        Need[] needs = {new Need(1, "Need 1", 10, 100, "Food"),
                        new Need(2, "Need 4", 20, 200, "Non-Fod")};
        when(cupboard.getNeeds()).thenReturn(needs);

        ResponseEntity<Need[]> response = needController.getNeeds();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Arrays.asList(needs), Arrays.asList(response.getBody()));
    }



    @Test
    void testSearchNeeds() {
        Need[] needs = {new Need(1, "Test Need", 10, 100, "Food"),
                        new Need(2, "Taste Need", 20, 269, "Non-Fod")};
        when(cupboard.findNeeds("Test")).thenReturn(needs);

        ResponseEntity<Need[]> response = needController.searchNeeds("Test");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Arrays.asList(needs), Arrays.asList(response.getBody()));
    }

    @Test
    void testcreateNewNeed() throws IOException {
         Need newNeed = new Need(1, "New Need", 30, 300, "Food");
        when(cupboard.createNeed(newNeed)).thenReturn(newNeed);

        ResponseEntity<Need> response = needController.createNeed(newNeed);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        assertEquals(newNeed, response.getBody());
    }

    @Test
    void testcreateNewNeed_NeedAlreadyExists() throws IOException {
         Need need1 = new Need(1, "New Need", 30, 300, "Food");
         Need need2 = new Need(2, "New Need", 30, 300, "Food");
        when(cupboard.createNeed(need1)).thenReturn(need1);
        when(cupboard.createNeed(need2)).thenReturn(null);

        ResponseEntity<Need> firstResponse = needController.createNeed(need1);
        assertEquals(HttpStatus.CREATED, firstResponse.getStatusCode());
        assertEquals(need1, firstResponse.getBody());

        ResponseEntity<Need> secondResponse = needController.createNeed(need2);
        assertEquals(HttpStatus.CONFLICT, secondResponse.getStatusCode());
        assertNull(secondResponse.getBody());
    }

    @Test
    void testupdateNeed_NeedExists() throws IOException{
         Need updatedNeed = new Need(1, "Updatee Need", 43, 400, "Non-Fod");
        when(cupboard.updateNeed(updatedNeed)).thenReturn(updatedNeed);

        ResponseEntity<Need> response = needController.updateNeed(updatedNeed);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedNeed, response.getBody());
    }

    @Test
    void testupdateNeed_NeedDosntExist() throws IOException {
         Need updatedNeed = new Need(1, "Updatee Need", 43, 400, "Non-Fod");
        when(cupboard.updateNeed(updatedNeed)).thenReturn(null);

        ResponseEntity<Need> response = needController.updateNeed(updatedNeed);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    
// 01001110 01100101 01110110 01100101 01110010 00100000 01100111 01101111 01101110 01101110 01100001
    @Test
    void testDeleteNeedExists() throws IOException{
        int id = 1;
        when(cupboard.deleteNeed(id)).thenReturn(true);
        ResponseEntity<Need> response = needController.deleteNeed(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDeleteNeedDosNotExist() throws IOException{
        int id = 1;
        when(cupboard.deleteNeed(id)).thenReturn(false);
        ResponseEntity<Need> response = needController.deleteNeed(id);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}