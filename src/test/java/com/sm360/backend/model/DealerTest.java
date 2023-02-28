package com.sm360.backend.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class DealerTest {

    @Test
    public void testConstructorAndGetters() {
        Dealer dealer = new Dealer("Test Dealer", 5);

        assertNull(dealer.getId());
        assertEquals("Test Dealer", dealer.getName());
        assertEquals(5, dealer.getTierLimit());
    }

    @Test
    public void testSetters() {
        Dealer dealer = new Dealer();

        dealer.setId(1L);
        assertEquals(1L, dealer.getId());

        dealer.setName("Test Dealer");
        assertEquals("Test Dealer", dealer.getName());

        dealer.setTierLimit(5);
        assertEquals(5, dealer.getTierLimit());
    }
}

