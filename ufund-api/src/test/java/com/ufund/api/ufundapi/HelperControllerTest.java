package com.ufund.api.ufundapi;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class HelperControllerTest {
    @Mock
    private Cupboard cupboard;
    
    @Mock
    private UserDB userDB;
    
    @InjectMocks
    private HelperController helperController;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
void testViewNeeds_Success() {
    Need[] mockNeeds = {new Need(0, "Need1", 0, 0, null), new Need(0, "Need2", 0, 0, null)};
    when(cupboard.getNeeds()).thenReturn(mockNeeds);

    ResponseEntity<Need[]> response = helperController.viewNeeds();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertArrayEquals(mockNeeds, response.getBody());
}

@Test
void testViewNeeds_EmptyCupboard() {
    when(cupboard.getNeeds()).thenReturn(null);

    ResponseEntity<Need[]> response = helperController.viewNeeds();

    assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
}

@Test
void testViewNeeds_ThrowsError() {
    when(cupboard.getNeeds()).thenThrow(new RuntimeException("Test exception"));

    ResponseEntity<Need[]> response = helperController.viewNeeds();

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
}


@Test
void testSearchNeeds_Success() {
    String searchName = "Test";
    Need[] mockNeeds = {new Need(0, "Need1", 0, 0, null), new Need(0, "Need2", 0, 0, null)};
    when(cupboard.findNeeds(searchName)).thenReturn(mockNeeds);

    ResponseEntity<Need[]> response = helperController.searchNeeds(searchName);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertArrayEquals(mockNeeds, response.getBody());
}

@Test
void testSearchNeeds_NotFound() {
    String searchName = "NonExistent";
    when(cupboard.findNeeds(searchName)).thenReturn(null);

    ResponseEntity<Need[]> response = helperController.searchNeeds(searchName);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
}

@Test
void testSearchNeeds_ThrowsError() {
    String searchName = "Test";
    when(cupboard.findNeeds(searchName)).thenThrow(new RuntimeException("Test exception"));

    ResponseEntity<Need[]> response = helperController.searchNeeds(searchName);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
}



@Test
void testCreateHelper_Success() throws IOException{
    Helper mockHelper = new Helper(0, "TestHelper", false);
    when(userDB.createHelper(any(Helper.class))).thenReturn(mockHelper);

    ResponseEntity<User> response = helperController.createHelper(mockHelper);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(mockHelper, response.getBody());
}

@Test
void testCreateHelper_Conflict() throws IOException {
    Helper mockHelper = new Helper(0, "ExistingHelper", false);
    when(userDB.createHelper(mockHelper)).thenReturn(null);

    ResponseEntity<User> response = helperController.createHelper(mockHelper);

    assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
}

@Test
void testCreateHelper_ThrowsError() throws IOException {
    Helper mockHelper = new Helper(0, "TestHelper", false);
    when(userDB.createHelper(mockHelper)).thenThrow(new IOException("Test exception"));

    ResponseEntity<User> response = helperController.createHelper(mockHelper);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
}


@Test
void testViewFundingBasket_Success() {
    int userId = 1;
    Helper mockHelper = new Helper(userId, "TestHelper", false);
    ArrayList<Need> mockBasket = new ArrayList<>();
    mockBasket.add(new Need(userId, "TestNeed", userId, userId, null));
    mockHelper.addNeed(mockBasket.get(0));
    when(userDB.getHelper(userId)).thenReturn(mockHelper);

    ResponseEntity<ArrayList<Need>> response = helperController.viewFundingBasket(userId);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(mockBasket, response.getBody());
}


@Test
void testViewFundingBasket_UserNotFound() {
    int userId = 999;
    when(userDB.getHelper(userId)).thenReturn(null);

    ResponseEntity<ArrayList<Need>> response = helperController.viewFundingBasket(userId);

    assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
}

// @Test
// void testViewFundingBasket_NotHelper() throws IOException {
//     int userId = 2;
//     User mockUser = new User(userId, "NotHelper", false);
//     when(userDB.getHelper(userId)).thenReturn(mockUser);

//     ResponseEntity<ArrayList<Need>> response = helperController.viewFundingBasket(userId);

//     assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
// }

@Test
void testAddNeed_Success() {
    int userId = 1;
    int needId = 101;
    Helper mockHelper = new Helper(needId, "TestHelper", false);
    Need mockNeed = new Need(needId, "TestNeed", needId, needId, null);
    when(userDB.getUser(userId)).thenReturn(mockHelper);
    when(cupboard.getNeed(needId)).thenReturn(mockNeed);

    ResponseEntity<Need> response = helperController.addNeed(userId, needId);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(mockNeed, response.getBody());
}

@Test
void testAddNeed_UserNotFound() {
    int userId = 999;
    int needId = 101;
    when(userDB.getUser(userId)).thenReturn(null);

    ResponseEntity<Need> response = helperController.addNeed(userId, needId);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
}

@Test
void testAddNeed_NotHelper() {
    int userId = 2;
    int needId = 101;
    User mockUser = new User(needId, "NotHelper", false);
    when(userDB.getUser(userId)).thenReturn(mockUser);

    ResponseEntity<Need> response = helperController.addNeed(userId, needId);

    assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
}

@Test
void testRemoveNeed_Success() {
    int userId = 1;
    int needId = 101;
    Helper mockHelper = new Helper(needId, "TestHelper", false);
    Need mockNeed = new Need(needId, "TestNeed", needId, needId, null);
    mockHelper.addNeed(mockNeed);
    when(userDB.getUser(userId)).thenReturn(mockHelper);

    ResponseEntity<Need> response = helperController.removeNeed(userId, needId);

    assertEquals(HttpStatus.OK, response.getStatusCode());
}

@Test
void testRemoveNeed_UserNotFound() {
    int userId = 999;
    int needId = 101;
    when(userDB.getUser(userId)).thenReturn(null);

    ResponseEntity<Need> response = helperController.removeNeed(userId, needId);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
}

@Test
void testRemoveNeed_NotHelper() {
    int userId = 2;
    int needId = 101;
    User mockUser = new User(needId, "NotHelper", false);
    when(userDB.getUser(userId)).thenReturn(mockUser);

    ResponseEntity<Need> response = helperController.removeNeed(userId, needId);

    assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
}


@Test
void testCheckout_Success() {
    int userId = 1;
    Helper mockHelper = new Helper(userId, "TestHelper", false);
    when(userDB.getUser(userId)).thenReturn(mockHelper);

    ResponseEntity<Need> response = helperController.checkout(userId);

    assertEquals(HttpStatus.OK, response.getStatusCode());
}

@Test
void testCheckout_UserNotFound() {

    int userId = 999;
    when(userDB.getUser(userId)).thenReturn(null);

    ResponseEntity<Need> response = helperController.checkout(userId);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
}

@Test
void testCheckoutNotHelper() {

    int userId = 2;
    User mockUser = new User(userId, "NotHelper", false);
    when(userDB.getUser(userId)).thenReturn(mockUser);

    ResponseEntity<Need> response = helperController.checkout(userId);

    assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
}
}
