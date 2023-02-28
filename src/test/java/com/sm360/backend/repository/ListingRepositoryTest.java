package com.sm360.backend.repository;

import com.sm360.backend.model.Dealer;
import com.sm360.backend.model.Listing;
import com.sm360.backend.model.ListingState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class ListingRepositoryTest {

    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testFindByDealerId() {
        Dealer dealer = new Dealer();
        dealer.setName("Test Dealer");
        entityManager.persist(dealer);

        Listing listing1 = new Listing();
        listing1.setDealer(dealer);
        listing1.setState(ListingState.DRAFT);
        listing1.setVehicle("Test Vehicle 1");
        listing1.setPrice(10000.0);
        listing1.setCreatedAt(new Date());
        entityManager.persist(listing1);

        Listing listing2 = new Listing();
        listing2.setDealer(dealer);
        listing2.setState(ListingState.DRAFT);
        listing2.setVehicle("Test Vehicle 2");
        listing2.setPrice(20000.0);
        listing2.setCreatedAt(new Date());
        entityManager.persist(listing2);

        List<Listing> listings = listingRepository.findByDealerId(dealer.getId());

        assertThat(listings).isNotEmpty();
        assertThat(listings.size()).isEqualTo(2);
    }

    @Test
    public void testFindByDealerIdAndState() {
        Dealer dealer = new Dealer();
        dealer.setName("Test Dealer");
        entityManager.persist(dealer);

        Listing listing1 = new Listing();
        listing1.setDealer(dealer);
        listing1.setState(ListingState.DRAFT);
        listing1.setVehicle("Test Vehicle 1");
        listing1.setPrice(10000.0);
        listing1.setCreatedAt(new Date());
        entityManager.persist(listing1);

        Listing listing2 = new Listing();
        listing2.setDealer(dealer);
        listing2.setState(ListingState.PUBLISHED);
        listing2.setVehicle("Test Vehicle 2");
        listing2.setPrice(20000.0);
        listing2.setCreatedAt(new Date());
        entityManager.persist(listing2);

        List<Listing> listings = listingRepository.findByDealerIdAndState(dealer.getId(), ListingState.PUBLISHED);

        assertThat(listings).isNotEmpty();
        assertThat(listings.size()).isEqualTo(1);
        assertThat(listings.get(0).getVehicle()).isEqualTo("Test Vehicle 2");
    }

    @Test
    public void testFindFirstByDealerIdAndStateOrderByCreatedAtAsc() {
        Dealer dealer = new Dealer();
        dealer.setName("Test Dealer");
        entityManager.persist(dealer);

        Listing listing1 = new Listing();
        listing1.setDealer(dealer);
        listing1.setState(ListingState.PUBLISHED);
        listing1.setVehicle("Test Vehicle 1");
        listing1.setPrice(10000.0);
        listing1.setCreatedAt(new Date(1645898400000L)); // 2022-02-27 00:00:00 UTC
        entityManager.persist(listing1);

        Listing listing2 = new Listing();
        listing2.setDealer(dealer);
        listing2.setState(ListingState.PUBLISHED);
        listing2.setVehicle("Test Vehicle 2");
        listing2.setPrice(20000.0);
        listing2.setCreatedAt(new Date(1645808400000L)); // 2022-02-26 00:00:00 UTC

        entityManager.persist(listing2);

        Listing listing3 = new Listing();
        listing3.setDealer(dealer);
        listing3.setState(ListingState.DRAFT);
        listing3.setVehicle("Test Vehicle 3");
        listing3.setPrice(30000.0);
        listing3.setCreatedAt(new Date(1645718400000L)); // 2022-02-25 00:00:00 UTC
        entityManager.persist(listing3);

        entityManager.flush();

        Listing oldestPublished = listingRepository.findFirstByDealerIdAndStateOrderByCreatedAtAsc(dealer.getId(), ListingState.PUBLISHED);

        assertNotNull(oldestPublished);
        assertEquals(listing2.getId(), oldestPublished.getId());
        assertEquals("Test Vehicle 2", oldestPublished.getVehicle());
        assertEquals(20000.0, oldestPublished.getPrice(), 0.001);
        assertEquals(new Date(1645808400000L), oldestPublished.getCreatedAt());
    }

    @Test
    public void testCountByDealerIdAndState() {
        Dealer dealer = new Dealer();
        dealer.setName("Test Dealer");
        entityManager.persist(dealer);

        Listing listing1 = new Listing();
        listing1.setDealer(dealer);
        listing1.setState(ListingState.PUBLISHED);
        listing1.setVehicle("Test Vehicle 1");
        listing1.setPrice(10000.0);
        entityManager.persist(listing1);

        Listing listing2 = new Listing();
        listing2.setDealer(dealer);
        listing2.setState(ListingState.DRAFT);
        listing2.setVehicle("Test Vehicle 2");
        listing2.setPrice(20000.0);
        entityManager.persist(listing2);

        Listing listing3 = new Listing();
        listing3.setDealer(dealer);
        listing3.setState(ListingState.PUBLISHED);
        listing3.setVehicle("Test Vehicle 3");
        listing3.setPrice(30000.0);
        entityManager.persist(listing3);

        int countPublishedListings = listingRepository.countByDealerIdAndState(dealer.getId(), ListingState.PUBLISHED);
        assertEquals(2, countPublishedListings);

        int countDraftListings = listingRepository.countByDealerIdAndState(dealer.getId(), ListingState.DRAFT);
        assertEquals(1, countDraftListings);
    }
}
