package com.sm360.backend.model;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListingTest {

    @Test
    public void testSetAndGetId() {
        Listing listing = new Listing();
        Long id = 1L;
        listing.setId(id);
        assertEquals(id, listing.getId());
    }

    @Test
    public void testSetAndGetDealer() {
        Listing listing = new Listing();
        Dealer dealer = new Dealer();
        dealer.setName("Test Dealer");
        listing.setDealer(dealer);
        assertEquals(dealer, listing.getDealer());
    }

    @Test
    public void testSetAndGetVehicle() {
        Listing listing = new Listing();
        String vehicle = "Test Vehicle";
        listing.setVehicle(vehicle);
        assertEquals(vehicle, listing.getVehicle());
    }

    @Test
    public void testSetAndGetPrice() {
        Listing listing = new Listing();
        double price = 10000.0;
        listing.setPrice(price);
        assertEquals(price, listing.getPrice());
    }

    @Test
    public void testSetAndGetCreatedAt() {
        Listing listing = new Listing();
        Date createdAt = new Date();
        listing.setCreatedAt(createdAt);
        assertEquals(createdAt, listing.getCreatedAt());
    }

    @Test
    public void testSetAndGetState() {
        Listing listing = new Listing();
        ListingState state = ListingState.PUBLISHED;
        listing.setState(state);
        assertEquals(state, listing.getState());
    }

    @Test
    public void testConstructorWithDealer() {
        Dealer dealer = new Dealer();
        dealer.setName("Test Dealer");
        Listing listing = new Listing("Test Vehicle", 10000.0, new Date(), ListingState.PUBLISHED, dealer);
        assertEquals(dealer, listing.getDealer());
    }
}
