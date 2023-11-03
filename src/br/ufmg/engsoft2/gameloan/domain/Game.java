package br.ufmg.engsoft2.gameloan.domain;

public class Game {
    private final String name;
    private final String description;
    private final double price;
    private final String ownerEmail;

    public Game(String name, String description, double price, String ownerEmail) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.ownerEmail = ownerEmail;
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

    @Override
    public String toString() {
        return String.format("| %-15s | %-15s | %-15s |%n", name, description, price);
    }
}
