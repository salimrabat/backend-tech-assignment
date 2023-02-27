package com.sm360.backend.exception;


public class ListingNotFoundException extends RuntimeException {
    public ListingNotFoundException(Long listingId) {
        super(String.format("Listing with id %s not found", listingId.toString()));
    }
}

