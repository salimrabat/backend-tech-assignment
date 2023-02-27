package com.sm360.backend.repository;

import com.sm360.backend.model.Dealer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DealerRepository extends JpaRepository<Dealer, Long> {
    List<Dealer> findAll();

    Optional<Dealer> findById(Long id);
}

