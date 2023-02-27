package com.sm360.backend.model;

import javax.persistence.*;

import java.util.Date;

@Entity
@Table(name = "listings")
public class Listing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Dealer dealer;

    private String vehicle;

    private double price;

    private Date createdAt;

    @Enumerated(EnumType.STRING)
    private ListingState state;

    public Listing() {}

    public Listing(String vehicle, double price, Date createdAt, ListingState state) {
        this.vehicle = vehicle;
        this.price = price;
        this.createdAt = createdAt;
        this.state = state;
    }

    public Listing(String vehicle, double price, Date createdAt, ListingState state, Dealer dealer) {
        this.vehicle = vehicle;
        this.price = price;
        this.createdAt = createdAt;
        this.state = state;
        this.dealer = dealer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Dealer getDealer() {
        return dealer;
    }

    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public ListingState getState() {
        return state;
    }

    public void setState(ListingState state) {
        this.state = state;
    }
}

