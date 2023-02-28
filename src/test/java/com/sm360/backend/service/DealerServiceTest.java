package com.sm360.backend.service;

import com.sm360.backend.exception.DealerNotFoundException;
import com.sm360.backend.model.Dealer;
import com.sm360.backend.repository.DealerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class DealerServiceTest {

    @Mock
    private DealerRepository dealerRepository;

    @InjectMocks
    private DealerServiceImpl dealerService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createDealer_ValidInput_ReturnsDealer() {
        String dealerName = "Test Dealer";
        int tierLimit = 10;
        Dealer dealer = new Dealer(dealerName, tierLimit);

        when(dealerRepository.save(any(Dealer.class))).thenReturn(dealer);

        Dealer createdDealer = dealerService.createDealer(dealerName, tierLimit);

        assertEquals(dealer, createdDealer);
        verify(dealerRepository, times(1)).save(any(Dealer.class));
    }

    @Test
    public void getDealerById_ValidId_ReturnsDealer() throws DealerNotFoundException {
        Long dealerId = 1L;
        String dealerName = "Test Dealer";
        int tierLimit = 10;
        Dealer dealer = new Dealer(dealerName, tierLimit);
        dealer.setId(dealerId);

        when(dealerRepository.findById(dealerId)).thenReturn(Optional.of(dealer));

        Dealer foundDealer = dealerService.getDealerById(dealerId);

        assertEquals(dealer, foundDealer);
        verify(dealerRepository, times(1)).findById(dealerId);
    }

    @Test
    public void getDealerById_InvalidId_ThrowsException() {
        Long dealerId = 1L;
        when(dealerRepository.findById(dealerId)).thenReturn(Optional.empty());

        assertThrows(DealerNotFoundException.class, () -> dealerService.getDealerById(dealerId));
        verify(dealerRepository, times(1)).findById(dealerId);
    }

    @Test
    public void getAllDealers_ReturnsListOfDealers() {
        List<Dealer> dealers = new ArrayList<>();
        dealers.add(new Dealer("Dealer 1", 5));
        dealers.add(new Dealer("Dealer 2", 10));

        when(dealerRepository.findAll()).thenReturn(dealers);

        List<Dealer> foundDealers = dealerService.getAllDealers();

        assertEquals(dealers, foundDealers);
        verify(dealerRepository, times(1)).findAll();
    }

    @Test
    public void updateDealer_ValidInput_ReturnsUpdatedDealer() throws DealerNotFoundException {
        Long dealerId = 1L;
        String oldDealerName = "Test Dealer";
        int oldTierLimit = 10;
        Dealer oldDealer = new Dealer(oldDealerName, oldTierLimit);
        oldDealer.setId(dealerId);
        String newDealerName = "New Dealer Name";
        int newTierLimit = 5;

        Dealer updatedDealer = new Dealer(newDealerName, newTierLimit);
        updatedDealer.setId(dealerId);

        when(dealerRepository.findById(dealerId)).thenReturn(Optional.of(oldDealer));
        when(dealerRepository.save(any(Dealer.class))).thenReturn(updatedDealer);

        Dealer result = dealerService.updateDealer(dealerId, updatedDealer);

        assertEquals(dealerId, result.getId());
        assertEquals(newDealerName, result.getName());
        verify(dealerRepository, times(1)).findById(dealerId);
        verify(dealerRepository, times(1)).save(oldDealer);
    }
    @Test
    public void updateDealer_NonexistentDealer_ThrowsDealerNotFoundException() throws DealerNotFoundException {
        Long dealerId = 1L;
        String newDealerName = "New Dealer Name";
        Dealer newDealer = new Dealer();
        newDealer.setName(newDealerName);
        when(dealerRepository.findById(dealerId)).thenReturn(Optional.empty());

        assertThrows(DealerNotFoundException.class, () -> dealerService.updateDealer(dealerId, newDealer));

        verify(dealerRepository, times(1)).findById(dealerId);
        verify(dealerRepository, never()).save(any(Dealer.class));
    }

}
