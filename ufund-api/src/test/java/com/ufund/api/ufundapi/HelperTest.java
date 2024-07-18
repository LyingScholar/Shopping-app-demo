package com.ufund.api.ufundapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HelperTest {

    private Helper helper;
    private Need need1;
    private Need need2;

    @BeforeEach
    void setUp() {
        helper = new Helper(1, "Tst Helper", false);
        need1 = new Need(1, "Need 1", 10, 100, "Food");
        need2 = new Need(2, "Need 2", 5, 50, "Clothin");
    }


    @Test
    void testConstructor() {
        assertEquals(1, helper.getId());
        assertEquals("Tst Helper", helper.getName());
        
        assertFalse(helper.isAdmin());
        assertTrue(helper.getFundingBasket().isEmpty());
    }

    @Test
    void testGetFundingBasket() {
        assertTrue(helper.getFundingBasket().isEmpty());
        helper.addNeed(need1);
        assertEquals(1, helper.getFundingBasket().size());
        assertTrue(helper.getFundingBasket().contains(need1));
    }

    @Test
    void testAddNeed() {
        helper.addNeed(need1);
        assertEquals(1,  helper.getFundingBasket().size());
        assertTrue(helper.getFundingBasket().contains(need1));

        helper.addNeed(need2);
        assertEquals(2, helper.getFundingBasket().size());
        assertTrue(helper.getFundingBasket().contains(need2));
    }

    @Test
    void testCheckout() {
        helper.addNeed(need1);
        helper.addNeed(need2);
        assertEquals(2, helper.getFundingBasket().size());

         helper.checkout();
        assertTrue(helper.getFundingBasket().isEmpty());
    }

    @Test
    void testRemoveNeed() {
        helper.addNeed(need1);
        helper.addNeed(need2);
        assertEquals(2, helper.getFundingBasket().size());

        helper.removeNeed(need1);


        assertEquals(1, helper.getFundingBasket().size());
        assertFalse(helper.getFundingBasket().contains(need1));
        assertTrue(helper.getFundingBasket().contains(need2));
    }

    @Test
    void testGetNeed() {
        helper.addNeed(need1);
        helper.addNeed(need2);

        Need retrievedNeed = helper.getNeed(1);
          assertEquals(need1, retrievedNeed);
        retrievedNeed = helper.getNeed(2);
        assertEquals(need2, retrievedNeed);

        retrievedNeed = helper.getNeed(3);
        assertNull(retrievedNeed);
    }

    @Test
    void testToString() {
        assertEquals("Helper", helper.toString());
    }
}