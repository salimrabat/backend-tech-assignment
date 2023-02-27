package com.sm360.backend.service;

public class PublishOptions {

    private boolean conformToTierLimit;

    public PublishOptions(boolean conformToTierLimit) {
        this.conformToTierLimit = conformToTierLimit;
    }

    public boolean isConformToTierLimit() {
        return conformToTierLimit;
    }


}
