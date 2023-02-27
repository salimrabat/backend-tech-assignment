package com.sm360.backend.controller;

import java.util.List;

import com.sm360.backend.exception.ErrorResponse;
import com.sm360.backend.model.Dealer;
import com.sm360.backend.model.Listing;
import com.sm360.backend.model.ListingState;
import com.sm360.backend.service.DealerService;
import com.sm360.backend.service.ListingService;
import com.sm360.backend.service.PublishOptions;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/listings")
@Tag(name = "Listings Endpoints")
public class ListingController {

    @Autowired
    private ListingService listingService;
    @Autowired
    private DealerService dealerService;

    @GetMapping("/{id}")
    @Operation(summary = "getListingById", description = "Get a listing by its identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Listing.class)) }),
            @ApiResponse(responseCode = "404", description = "Listing not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    public ResponseEntity<Listing> getListingById(@PathVariable Long id) {
        Listing listing = listingService.getListingById(id);
        if (listing != null) {
            return new ResponseEntity<>(listing, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/dealer/{dealerId}")
    @Operation(summary = "createListing", description = "Create a new listing")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Listing.class)) }),
            @ApiResponse(responseCode = "404", description = "Dealer not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    public ResponseEntity<Listing> createListing(@PathVariable Long dealerId, @RequestBody Listing listing) {
        Dealer dealer = dealerService.getDealerById(dealerId);
        listing.setDealer(dealer);
        Listing createdListing = listingService.createListing(listing);
        return new ResponseEntity<>(createdListing, HttpStatus.CREATED);
    }

    @GetMapping("")
    @Operation(summary = "getAllListings", description = "Get all listings")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = { @Content(mediaType = "application/json",
                            array =  @ArraySchema(schema = @Schema(implementation = Listing.class))) }),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    public ResponseEntity<List<Listing>> getAllListings() {
        List<Listing> listings = listingService.getAllListings();
        return new ResponseEntity<>(listings, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "updateListing", description = "Update listing")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Listing.class)) }),
            @ApiResponse(responseCode = "404", description = "Listing not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    public ResponseEntity<Listing> updateListing(@PathVariable Long id, @RequestBody Listing listing) {
        Listing updatedListing = listingService.updateListing(id, listing.getVehicle(), listing.getPrice());
        if (updatedListing != null) {
            return new ResponseEntity<>(updatedListing, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "deleteListing", description = "Delete listing")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Listing.class)) }),
            @ApiResponse(responseCode = "404", description = "Listing not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    public ResponseEntity<Listing> deleteListing(@PathVariable Long id) {
        listingService.deleteListingById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{id}/publish")
    @Operation(summary = "publishListing", description = "Publish listing")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Listing.class)) }),
            @ApiResponse(responseCode = "403", description = "Tier limit exceeded",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Listing not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    public ResponseEntity<Listing> publishListing(@PathVariable Long id, @RequestParam(defaultValue = "false") boolean conformToTierLimit) {
        Listing publishedListing = listingService.publishListing(id, new PublishOptions(conformToTierLimit));
        if (publishedListing != null) {
            return new ResponseEntity<>(publishedListing, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{id}/unpublish")
    @Operation(summary = "unpublishListing", description = "Unpublish listing")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Listing.class)) }),
            @ApiResponse(responseCode = "404", description = "Listing not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    public ResponseEntity<Listing> unpublishListing(@PathVariable Long id) {
        Listing unpublishedListing = listingService.unpublishListing(id);
        return new ResponseEntity<>(unpublishedListing, HttpStatus.OK);
    }

    @GetMapping("/dealer/{dealerId}")
    @Operation(summary = "getListingsByDealerIdAndState", description = "Get All listings by listed by a dealer and could be filtered by state")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = { @Content(mediaType = "application/json",
                            array =  @ArraySchema(schema = @Schema(implementation = Listing.class))) }),
            @ApiResponse(responseCode = "404", description = "Dealer not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    public ResponseEntity<List<Listing>> getListingsByDealerIdAndState(@PathVariable Long dealerId, @RequestParam(required = false) ListingState state) {
        List<Listing> listings = listingService.getListingsByDealerIdAndState(dealerId, state);
        if (listings != null) {
            return new ResponseEntity<>(listings, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}

