package com.ufund.api.ufundapi;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    //response entity and mockito were googled a lot while writing these tests

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
    void testGetNeds() {
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

}