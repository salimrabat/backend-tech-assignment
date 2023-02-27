package com.sm360.backend.exception;


public class DealerNotFoundException extends RuntimeException {
    public DealerNotFoundException(Long dealerId) {
        super(String.format("Dealer with id %s not found", dealerId.toString()));
    }
}

