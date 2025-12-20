package service;

import exception.ItemNotFoundException;
import model.LibraryItem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Library {
    private List<LibraryItem> items = new ArrayList<>();

    public <T extends LibraryItem> void addItem(T item) {
        items.add(item);
    }

    public LibraryItem getItemById(int id) throws ItemNotFoundException {
        return items.stream()
                .filter(i -> i.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ItemNotFoundException("Library item not found."));
    }

    public <T extends LibraryItem> T getItemById(int id, Class<T> type) throws ItemNotFoundException {
        return items.stream()
                .filter(i -> i.getId() == id && type.isInstance(i))
                .map(type::cast)
                .findFirst()
                .orElseThrow(() -> new ItemNotFoundException("Library item not found for this type."));
    }

    public void displayAllItems() {
        if (items.isEmpty()) {
            System.out.println("No items.");
            return;
        }
        items.forEach(i -> System.out.println(i.getItemDetails()));
    }

    public void displayAnyItems(List<? extends LibraryItem> list) {
        if (list == null || list.isEmpty()) {
            System.out.println("No items to display.");
            return;
        }
        list.forEach(i -> System.out.println(i.getItemDetails()));
    }

    public List<LibraryItem> getAvailableItems() {
        return items.stream()
                .filter(LibraryItem::isAvailable)
                .collect(Collectors.toList());
    }

    public void deleteItem(int id) throws ItemNotFoundException {
        LibraryItem item = getItemById(id);
        items.remove(item);
    }

    public List<LibraryItem> getItems() {
        return items;
    }
}
