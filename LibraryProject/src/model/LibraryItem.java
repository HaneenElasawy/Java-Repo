package model;

import java.util.Scanner;

public abstract class LibraryItem implements CrudEntity {
    private int id;
    private String title;

    private int totalCopies;
    private int availableCopies;

    public LibraryItem(int id, String title, int totalCopies) {
        this.id = id;
        this.title = title;

        if (totalCopies <= 0) totalCopies = 1;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getTotalCopies() { return totalCopies; }
    public int getAvailableCopies() { return availableCopies; }

    public boolean isAvailable() { return availableCopies > 0; }

    public boolean borrowCopy() {
        if (availableCopies > 0) {
            availableCopies--;
            return true;
        }
        return false;
    }

    public void returnCopy() {
        if (availableCopies < totalCopies) {
            availableCopies++;
        }
    }

    public abstract String getItemDetails();

    @Override
    public void create() {
        System.out.println("Created: " + getItemDetails());
    }

    @Override
    public void read() {
        System.out.println(getItemDetails());
    }

    @Override
    public void update(Scanner sc) {
        System.out.print("New title: ");
        setTitle(sc.nextLine());
        System.out.println("Item updated.");
    }

    @Override
    public void delete() {
        System.out.println("Deleted: " + getItemDetails());
    }
}
