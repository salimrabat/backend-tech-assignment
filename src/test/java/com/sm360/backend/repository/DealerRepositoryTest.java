package com.sm360.backend.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.sm360.backend.model.Dealer;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
public class DealerRepositoryTest {

    @Autowired
    private DealerRepository dealerRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testFindAll() {
        Dealer dealer1 = new Dealer();
        dealer1.setName("Dealer 1");
        entityManager.persist(dealer1);

        Dealer dealer2 = new Dealer();
        dealer2.setName("Dealer 2");
        entityManager.persist(dealer2);

        List<Dealer> dealers = dealerRepository.findAll();

        assertThat(dealers).containsExactly(dealer1, dealer2);
        assertEquals(2, dealers.size());
    }

    @Test
    public void testFindById() {
        Dealer dealer = new Dealer();
        dealer.setName("Test Dealer");
        entityManager.persist(dealer);

        Optional<Dealer> optionalDealer = dealerRepository.findById(dealer.getId());

        assertThat(optionalDealer).isPresent();
        assertThat(optionalDealer.get()).isEqualTo(dealer);
    }
}
