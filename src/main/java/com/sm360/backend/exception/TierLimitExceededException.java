package com.sm360.backend.exception;


public class TierLimitExceededException extends RuntimeException {
    public TierLimitExceededException(Long dealerId) {
        super(String.format("Dealer with id %s has exceeded their tier limit", dealerId.toString()));
    }
}

