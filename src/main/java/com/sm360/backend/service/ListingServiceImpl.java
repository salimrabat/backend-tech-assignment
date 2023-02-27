package com.sm360.backend.service;

import com.sm360.backend.exception.ListingNotFoundException;
import com.sm360.backend.exception.TierLimitExceededException;
import com.sm360.backend.model.Dealer;
import com.sm360.backend.model.Listing;
import com.sm360.backend.model.ListingState;
import com.sm360.backend.repository.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ListingServiceImpl implements ListingService {

    private final ListingRepository listingRepository;
    private final DealerService dealerService;

    @Autowired
    public ListingServiceImpl(ListingRepository listingRepository, DealerService dealerService) {
        this.listingRepository = listingRepository;
        this.dealerService = dealerService;
    }

    @Override
    public Listing createListing(Listing listing) {
        listing.setState(ListingState.DRAFT);
        listing.setCreatedAt(new Date());
        return listingRepository.save(listing);
    }

    @Override
    public Listing updateListing(Long listingId, String vehicle, double price) {
        Listing listing = getListingById(listingId);
        listing.setVehicle(vehicle);
        listing.setPrice(price);
        return listingRepository.save(listing);
    }

    @Override
    public void deleteListingById(Long id) {
        Listing listing = getListingById(id);
        listingRepository.delete(listing);
    }

    @Override
    public List<Listing> getAllListings() {
        return listingRepository.findAll();
    }

    @Override
    public List<Listing> getListingsByDealerIdAndState(Long dealerId, ListingState state) {
        dealerService.getDealerById(dealerId); // Make sure dealer exists
        if (state != null) {
            return listingRepository.findByDealerIdAndState(dealerId, state);
        } else {
            return listingRepository.findByDealerId(dealerId);
        }

    }

    @Override
    public Listing publishListing(Long listingId, PublishOptions options) {
        Listing listing = getListingById(listingId);
        Dealer dealer = listing.getDealer();
        int publishedCount = countPublishedListings(dealer.getId());
        int tierLimit = dealer.getTierLimit();

        if (publishedCount >= tierLimit) {
            if (options.isConformToTierLimit()) {
                throw new TierLimitExceededException(dealer.getId());
            } else {
                // Find the oldest published listing and unpublish it
                Listing oldestPublished = listingRepository.findFirstByDealerIdAndStateOrderByCreatedAtAsc(dealer.getId(), ListingState.PUBLISHED);
                oldestPublished.setState(ListingState.DRAFT);
                listingRepository.save(oldestPublished);
            }
        }

        listing.setState(ListingState.PUBLISHED);
        return listingRepository.save(listing);
    }

    @Override
    public Listing unpublishListing(Long listingId) {
        Listing listing = getListingById(listingId);
        listing.setState(ListingState.DRAFT);
        return listingRepository.save(listing);
    }

    public Listing getListingById(Long listingId) {
        Optional<Listing> optionalListing = listingRepository.findById(listingId);
        if (optionalListing.isEmpty()) {
            throw new ListingNotFoundException(listingId);
        }
        return optionalListing.get();
    }

    private int countPublishedListings(Long dealerId) {
        return listingRepository.countByDealerIdAndState(dealerId, ListingState.PUBLISHED);
    }

}

