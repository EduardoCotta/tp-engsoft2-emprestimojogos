package br.ufmg.engsoft2.gameloan.domain;

import java.util.UUID;

public class Game {
    private final UUID id;
    private final String name;
    private final String description;
    private final double price;
    private final String ownerEmail;

    public Game(String name, String description, double price, String ownerEmail) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.price = price;
        this.ownerEmail = ownerEmail;
    }

    public Game(UUID id, String name, String description, double price, String ownerEmail) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.ownerEmail = ownerEmail;
    }

    public Game(UUID id) {
        this.id = id;
        this.name = "";
        this.description = "";
        this.price = 0;
        this.ownerEmail = "";
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("| %-15s | %-15s | %-15s |%n", name, description, price);
    }
}
