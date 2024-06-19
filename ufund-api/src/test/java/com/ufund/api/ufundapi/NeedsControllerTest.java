package com.ufund.api.ufundapi;

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

    @Mock
    private Cupboard cupboard;
    @InjectMocks
    private NeedController needController;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testgetNeed_shouldReturnTheNeedIfNeedExists() {
        int id = 1;
        Need need = new Need(id, "Test Need", 10, 100, "Food");
        when(cupboard.getNeed(id)).thenReturn(need);

        ResponseEntity<Need> response = needController.getNeed(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(need, response.getBody());
    }



    @Test
    void testgetNeed_NeedDoesNotExist() {
        int id = 1;
        when(cupboard.getNeed(id)).thenReturn(null);

        ResponseEntity<Need> response = needController.getNeed(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


}