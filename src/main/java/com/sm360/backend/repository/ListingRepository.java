package com.sm360.backend.repository;

import com.sm360.backend.model.Listing;
import com.sm360.backend.model.ListingState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {
    List<Listing> findByDealerId(Long dealerId);

    List<Listing> findByDealerIdAndState(Long dealerId, ListingState state);

    Listing findFirstByDealerIdAndStateOrderByCreatedAtAsc(Long dealerId, ListingState state);

    int countByDealerIdAndState(Long dealerId, ListingState state);
}

