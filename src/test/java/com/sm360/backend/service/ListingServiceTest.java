package com.sm360.backend.service;

import com.sm360.backend.exception.DealerNotFoundException;
import com.sm360.backend.exception.ListingNotFoundException;
import com.sm360.backend.model.Dealer;
import com.sm360.backend.model.Listing;
import com.sm360.backend.model.ListingState;
import com.sm360.backend.repository.DealerRepository;
import com.sm360.backend.repository.ListingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ListingServiceTest {

    @Mock
    private ListingRepository listingRepository;

    @Mock
    private DealerRepository dealerRepository;

    @Mock
    private DealerService dealerService;

    @InjectMocks
    private ListingServiceImpl listingService;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createListing_ValidInput_ReturnsCreatedListing() {
        Listing listing = new Listing();
        listing.setVehicle("Test Vehicle");
        listing.setPrice(10000.0);
        when(listingRepository.save(any(Listing.class))).thenReturn(listing);

        Listing createdListing = listingService.createListing(listing);

        assertEquals(listing, createdListing);
    }

    @Test
    public void updateListing_ListingNotFound_ThrowsListingNotFoundException() throws ListingNotFoundException {
        Long listingId = 1L;
        String newVehicle = "New Vehicle";
        double newPrice = 20000.0;
        when(listingRepository.findById(listingId)).thenReturn(Optional.empty());

        assertThrows(ListingNotFoundException.class, () -> listingService.updateListing(listingId, newVehicle, newPrice));
    }

    @Test
    public void updateListing_ValidInput_ReturnsUpdatedListing() {
        Long listingId = 1L;
        String oldVehicle = "Test Vehicle";
        double oldPrice = 10000.0;
        Listing oldListing = new Listing();
        oldListing.setVehicle(oldVehicle);
        oldListing.setPrice(oldPrice);
        oldListing.setId(listingId);

        String newVehicle = "New Vehicle";
        double newPrice = 20000.0;

        when(listingRepository.findById(listingId)).thenReturn(Optional.of(oldListing));
        when(listingRepository.save(any(Listing.class))).thenReturn(oldListing);

        Listing updatedListing = listingService.updateListing(listingId, newVehicle, newPrice);

        assertEquals(newVehicle, updatedListing.getVehicle());
        assertEquals(newPrice, updatedListing.getPrice(), 0.001);
    }

    @Test
    public void getListingById_ListingNotFound_ThrowsListingNotFoundException() throws ListingNotFoundException {
        long listingId = 1;
        when(listingRepository.findById(listingId)).thenReturn(Optional.empty());

        assertThrows(ListingNotFoundException.class, () -> listingService.getListingById(listingId));
    }

    @Test
    public void getListingById_ListingFound_ReturnsListing() throws ListingNotFoundException {
        long listingId = 1;
        String vehicle = "Test Vehicle";
        double price = 10000.0;
        Listing listing = new Listing();
        listing.setVehicle(vehicle);
        listing.setPrice(price);
        listing.setId(listingId);

        when(listingRepository.findById(listingId)).thenReturn(Optional.of(listing));

        Listing foundListing = listingService.getListingById(listingId);

        assertEquals(listing, foundListing);
    }

    @Test
    public void getAllListings_ReturnsAllListings() {
        Listing listing1 = new Listing();
        listing1.setVehicle("Vehicle 1");
        listing1.setPrice(10000.0);
        Listing listing2 = new Listing();
        listing2.setVehicle("Vehicle 2");
        listing2.setPrice(20000.0);
        Listing listing3 = new Listing();
        listing3.setVehicle("Vehicle 3");
        listing3.setPrice(30000.0);
        List<Listing> listings = Arrays.asList(listing1, listing2, listing3);
        when(listingRepository.findAll()).thenReturn(listings);

        List<Listing> allListings = listingService.getAllListings();

        assertEquals(listings, allListings);
    }

    @Test
    public void deleteListingById_ListingNotFound_ThrowsListingNotFoundException() throws ListingNotFoundException {
        long listingId = 1;
        when(listingRepository.findById(listingId)).thenReturn(Optional.empty());


        assertThrows(ListingNotFoundException.class, () -> listingService.deleteListingById(listingId));
    }

    @Test
    public void deleteListingById_ListingFound_DeletesListing() {
        Long listingId = 1L;
        Listing listing = new Listing();
        listing.setVehicle("Test Vehicle");
        listing.setPrice(10000.0);
        listing.setId(listingId);
        when(listingRepository.findById(listingId)).thenReturn(Optional.of(listing));

        listingService.deleteListingById(listingId);

        verify(listingRepository, times(1)).delete(listing);
    }

    @Test
    public void getListingsByDealerIdAndState_DealerNotFound_ThrowsDealerNotFoundException() throws DealerNotFoundException {
        Long dealerId = 1L;
        ListingState state = ListingState.PUBLISHED;
        when(dealerService.getDealerById(dealerId)).thenThrow(DealerNotFoundException.class);

        assertThrows(DealerNotFoundException.class, () -> listingService.getListingsByDealerIdAndState(dealerId, state));

    }

    @Test
    public void getListingsByDealerIdAndState_DealerFound_ReturnsListingsWithState() throws DealerNotFoundException {
        Long dealerId = 1L;
        ListingState state = ListingState.PUBLISHED;
        Dealer dealer = new Dealer("Test Dealer", 10);
        dealer.setId(dealerId);

        Listing listing1 = new Listing();
        listing1.setVehicle("Test Vehicle 1");
        listing1.setPrice(10000.0);
        Listing listing2 = new Listing();
        listing2.setVehicle("Test Vehicle 2");
        listing2.setPrice(20000.0);
        Listing listing3 = new Listing();
        listing3.setVehicle("Test Vehicle 3");
        listing3.setPrice(30000.0);

        listing1.setDealer(dealer);
        listing1.setState(ListingState.PUBLISHED);

        listing2.setDealer(dealer);
        listing2.setState(ListingState.PUBLISHED);

        listing3.setDealer(dealer);
        listing3.setState(ListingState.DRAFT);

        when(dealerRepository.findById(dealerId)).thenReturn(Optional.of(dealer));
        when(listingRepository.findByDealerIdAndState(dealerId, state)).thenReturn(Arrays.asList(listing1, listing2));

        List<Listing> result = listingService.getListingsByDealerIdAndState(dealerId, state);

        assertEquals(2, result.size());
        assertTrue(result.contains(listing1));
        assertTrue(result.contains(listing2));
        assertFalse(result.contains(listing3));
    }

}
