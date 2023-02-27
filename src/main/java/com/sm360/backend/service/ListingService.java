package com.sm360.backend.service;

import com.sm360.backend.exception.DealerNotFoundException;
import com.sm360.backend.exception.ListingNotFoundException;
import com.sm360.backend.exception.TierLimitExceededException;
import com.sm360.backend.model.Listing;
import com.sm360.backend.model.ListingState;

import java.util.List;

public interface ListingService {
    /**
     * Creates a new listing.
     *
     * @param listing  The listing object that we want to create.
     * @return The created listing.
     */
    Listing createListing(Listing listing);

    /**
     * Updates the given Listing with the new vehicle and price.
     *
     * @param id   The ID of the dealer to update.
     * @param vehicle The new name of the vehicle in the listing.
     * @param price The new price of the vehicle in the listing.
     * @return The updated listing.
     * @throws ListingNotFoundException If no listing exists with the given ID.
     */
    Listing updateListing(Long id, String vehicle, double price) throws ListingNotFoundException;

    /**
     * Finds and returns the listing with the given ID.
     *
     * @param id The ID of the listing to find.
     * @return The listing with the given ID.
     * @throws ListingNotFoundException If no listing exists with the given ID.
     */
    Listing getListingById(Long id) throws ListingNotFoundException;

    /**
     * Finds and returns all listings.
     *
     * @return A list of all listings.
     */
    List<Listing> getAllListings();

    /**
     * Deletes the listing with the given ID.
     *
     * @param id The ID of the listing to delete.
     * @throws ListingNotFoundException If no listing exists with the given ID.
     */
    void deleteListingById(Long id) throws ListingNotFoundException;

    /**
     * Finds and returns all listings for the dealer with the given ID and state.
     *
     * @param dealerId The ID of the dealer to find listings for.
     * @param state The state of the listings to find.
     * @return A list of all listings for the dealer with the given ID and state.
     * @throws DealerNotFoundException If no dealer exists with the given ID.
     */
    List<Listing> getListingsByDealerIdAndState(Long dealerId, ListingState state) throws DealerNotFoundException;

    /**
     * Publishes the listing with the given ID for the dealer with the given ID.
     *
     * @param id The ID of the listing to publish.
     * @param options The option for handling a tier limit exceedance.
     * @return The published listing.
     * @throws ListingNotFoundException If no listing exists with the given ID.
     * @throws TierLimitExceededException If publishing the listing would exceed the dealer's tier limit and the publish option is set to ERROR.
     */
    Listing publishListing(Long id, PublishOptions options) throws ListingNotFoundException, TierLimitExceededException;

    /**
     * Unpublishes the listing with the given ID for the dealer with the given ID.
     *
     * @param id The ID of the listing to unpublish.
     * @return The unpublished listing.
     * @throws ListingNotFoundException If no listing exists with the given ID.
     */
    Listing unpublishListing(Long id) throws ListingNotFoundException;

}



