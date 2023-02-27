package com.sm360.backend.controller;


import com.sm360.backend.model.Dealer;
import com.sm360.backend.service.DealerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;



@ExtendWith(MockitoExtension.class)
public class DealerControllerTests {

    @Mock
    private DealerService dealerService;

    @InjectMocks
    private DealerController dealerController;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void getDealerById_ReturnsDealer_WhenIdExists() {
        long id = 1;
        Dealer dealer = new Dealer("Test Dealer", 5);
        when(dealerService.getDealerById(id)).thenReturn(dealer);

        ResponseEntity<Dealer> response = dealerController.getDealerById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dealer, response.getBody());
    }

    @Test
    public void getDealerById_ReturnsNotFound_WhenIdDoesNotExist() {
        long id = 1;
        when(dealerService.getDealerById(id)).thenReturn(null);

        ResponseEntity<Dealer> response = dealerController.getDealerById(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }
}

