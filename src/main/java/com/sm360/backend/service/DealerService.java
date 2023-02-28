package com.sm360.backend.service;

import com.sm360.backend.exception.DealerNotFoundException;
import com.sm360.backend.model.Dealer;

import java.util.List;

public interface DealerService {

    /**
     * Creates a new dealer with the given name.
     *
     * @param name      The name of the dealer to create.
     * @param tierLimit the limit on the number of published listings at the same time.
     * @return The created dealer.
     */
    Dealer createDealer(String name, int tierLimit);

    /**
     * Finds and returns the dealer with the given ID.
     *
     * @param id The ID of the dealer to find.
     * @return The dealer with the given ID.
     * @throws DealerNotFoundException If no dealer exists with the given ID.
     */
    Dealer getDealerById(Long id) throws DealerNotFoundException;

    /**
     * Finds and returns all dealers.
     *
     * @return A list of all dealers.
     */
    List<Dealer> getAllDealers();

    /**
     * Updates the given dealer with the new name.
     *
     * @param id   The ID of the dealer to update.
     * @param dealer The new dealer information for the dealer.
     * @return The updated dealer.
     * @throws DealerNotFoundException If no dealer exists with the given ID.
     */
    Dealer updateDealer(Long id, Dealer dealer) throws DealerNotFoundException;

    /**
     * Deletes the dealer with the given ID.
     *
     * @param id The ID of the dealer to delete.
     * @throws DealerNotFoundException If no dealer exists with the given ID.
     */
    void deleteDealer(Long id) throws DealerNotFoundException;

}

