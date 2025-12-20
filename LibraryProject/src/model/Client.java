package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client implements CrudEntity {
    private int id;
    private String name;
    private String email;

    private List<LibraryItem> borrowedItems = new ArrayList<>();

    public Client(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId() { return id; }
    public String getName() { return name; }

    public String getClientDetails() {
        return "Client [id=" + id + ", name=" + name + ", email=" + email +
                ", borrowed=" + borrowedItems.size() + "]";
    }

    public void borrowItem(LibraryItem item) {
        borrowedItems.add(item);
    }

    public boolean hasBorrowedItem(int itemId) {
        return borrowedItems.stream().anyMatch(i -> i.getId() == itemId);
    }

    public boolean returnItem(int itemId) {
        LibraryItem found = borrowedItems.stream()
                .filter(i -> i.getId() == itemId)
                .findFirst()
                .orElse(null);

        if (found != null) {
            borrowedItems.remove(found);
            return true;
        }
        return false;
    }

    @Override
    public void create() {
        System.out.println("Created: " + getClientDetails());
    }

    @Override
    public void read() {
        System.out.println(getClientDetails());
        if (borrowedItems.isEmpty()) {
            System.out.println("Borrowed items: none");
        } else {
            System.out.println("Borrowed items:");
            borrowedItems.forEach(i -> System.out.println(" - " + i.getItemDetails()));
        }
    }

    @Override
    public void update(Scanner sc) {
        System.out.print("New name: ");
        name = sc.nextLine();
        System.out.print("New email: ");
        email = sc.nextLine();
        System.out.println("Client updated.");
    }

    @Override
    public void delete() {
        System.out.println("Deleted: " + getClientDetails());
    }
}
