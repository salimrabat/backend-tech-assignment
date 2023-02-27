package com.sm360.backend.model;

import javax.persistence.*;

import java.util.List;

@Entity
@Table(name = "dealers")
public class Dealer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int tierLimit;

    @OneToMany(mappedBy = "dealer")
    private List<Listing> listings;

    public Dealer() {}

    public Dealer(String name, int tierLimit) {
        this.name = name;
        this.tierLimit = tierLimit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTierLimit() {
        return tierLimit;
    }

    public void setTierLimit(int tierLimit) {
        this.tierLimit = tierLimit;
    }
}

