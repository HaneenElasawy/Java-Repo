package app;

import exception.ItemNotFoundException;
import model.*;
import service.*;
import util.InputUtil;

import java.util.List;
import java.util.Scanner;

public class LibraryApp {

    private static final Scanner sc = new Scanner(System.in);
    private static final Library library = new Library();
    private static final ClientManager clientManager = new ClientManager();

    public static void main(String[] args) {
        library.addItem(new Book(1, "Clean Code", "Robert Martin", 464, 2));
        library.addItem(new Magazine(2, "Java Magazine", 101, "2024", 1));
        clientManager.addClient(new Client(10, "Nino", "nino@mail.com"));

        int choice;
        do {
            System.out.println("\n===== MAIN MENU =====");
            System.out.println("1) Library Items (CRUD)");
            System.out.println("2) Clients (CRUD)");
            System.out.println("3) Borrow Item");
            System.out.println("4) Return Item");
            System.out.println("5) Show Available Items");
            System.out.println("0) Exit");
            System.out.print("Choice: ");

            choice = InputUtil.readIntSafe(sc);

            switch (choice) {
                case 1: itemsMenu(); break;
                case 2: clientsMenu(); break;
                case 3: borrowItemMenu(); break;
                case 4: returnItemMenu(); break;
                case 5: showAvailableItems(); break;
            }
        } while (choice != 0);

        System.out.println("Bye.");
    }

    private static void itemsMenu() {
        System.out.println("\n--- ITEMS MENU ---");
        System.out.println("1) Add Book");
        System.out.println("2) Add Magazine");
        System.out.println("3) Read Item Details");
        System.out.println("4) Update Item Title");
        System.out.println("5) Delete Item");
        System.out.println("6) Display All Items");
        System.out.print("Choice: ");

        int c = InputUtil.readIntSafe(sc);

        try {
            switch (c) {
                case 1: addBook(); break;
                case 2: addMagazine(); break;
                case 3: readItem(); break;
                case 4: updateItem(); break;
                case 5: deleteItem(); break;
                case 6: library.displayAllItems(); break;
            }
        } catch (ItemNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void clientsMenu() {
        System.out.println("\n--- CLIENTS MENU ---");
        System.out.println("1) Add Client");
        System.out.println("2) Read Client Details");
        System.out.println("3) Update Client");
        System.out.println("4) Delete Client");
        System.out.println("5) Display All Clients");
        System.out.print("Choice: ");

        int c = InputUtil.readIntSafe(sc);

        try {
            switch (c) {
                case 1: addClient(); break;
                case 2: readClient(); break;
                case 3: updateClient(); break;
                case 4: deleteClient(); break;
                case 5: clientManager.displayAllClients(); break;
            }
        } catch (ItemNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void borrowItemMenu() {
        System.out.print("Client ID: ");
        int userId = InputUtil.readIntSafe(sc);

        System.out.print("Item ID: ");
        int itemId = InputUtil.readIntSafe(sc);

        try {
            Client client = clientManager.getClientById(userId);
            LibraryItem item = library.getItemById(itemId);

            if (!item.isAvailable()) {
                System.out.println("Item is not available right now.");
                return;
            }

            boolean ok = item.borrowCopy();
            if (!ok) {
                System.out.println("Borrow failed.");
                return;
            }

            client.borrowItem(item);
            System.out.println("Borrowed successfully by " + client.getName());

        } catch (ItemNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void returnItemMenu() {
        System.out.print("Client ID: ");
        int userId = InputUtil.readIntSafe(sc);

        System.out.print("Item ID: ");
        int itemId = InputUtil.readIntSafe(sc);

        try {
            Client client = clientManager.getClientById(userId);
            LibraryItem item = library.getItemById(itemId);

            if (!client.hasBorrowedItem(itemId)) {
                System.out.println("This client did not borrow this item.");
                return;
            }

            boolean removed = client.returnItem(itemId);
            if (removed) {
                item.returnCopy();
                System.out.println("Returned successfully.");
            } else {
                System.out.println("Return failed.");
            }

        } catch (ItemNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void showAvailableItems() {
        List<LibraryItem> available = library.getAvailableItems();
        if (available.isEmpty()) {
            System.out.println("No available items.");
        } else {
            System.out.println("Available items:");
            library.displayAnyItems(available);
        }
    }

    private static void addBook() {
        System.out.print("Book ID: ");
        int id = InputUtil.readUniqueId(sc, library, clientManager);

        System.out.print("Title: ");
        String title = sc.nextLine();

        System.out.print("Author: ");
        String author = sc.nextLine();

        System.out.print("Pages: ");
        int pages = InputUtil.readIntSafe(sc);

        System.out.print("Total copies: ");
        int copies = InputUtil.readIntSafe(sc);

        Book b = new Book(id, title, author, pages, copies);
        library.addItem(b);
        b.create();
    }

    private static void addMagazine() {
        System.out.print("Magazine ID: ");
        int id = InputUtil.readUniqueId(sc, library, clientManager);

        System.out.print("Title: ");
        String title = sc.nextLine();

        System.out.print("Issue number: ");
        int issue = InputUtil.readIntSafe(sc);

        System.out.print("Publication date: ");
        String date = sc.nextLine();

        System.out.print("Total copies: ");
        int copies = InputUtil.readIntSafe(sc);

        Magazine m = new Magazine(id, title, issue, date, copies);
        library.addItem(m);
        m.create();
    }

    private static void readItem() throws ItemNotFoundException {
        System.out.print("Item ID: ");
        int id = InputUtil.readIntSafe(sc);
        LibraryItem item = library.getItemById(id);
        item.read();
    }

    private static void updateItem() throws ItemNotFoundException {
        System.out.print("Item ID: ");
        int id = InputUtil.readIntSafe(sc);
        LibraryItem item = library.getItemById(id);
        item.update(sc);
    }

    private static void deleteItem() throws ItemNotFoundException {
        System.out.print("Item ID: ");
        int id = InputUtil.readIntSafe(sc);
        LibraryItem item = library.getItemById(id);
        item.delete();
        library.deleteItem(id);
        System.out.println("Removed from library.");
    }

    private static void addClient() {
        System.out.print("Client ID: ");
        int id = InputUtil.readUniqueId(sc, library, clientManager);

        System.out.print("Name: ");
        String name = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        Client c = new Client(id, name, email);
        clientManager.addClient(c);
        c.create();
    }

    private static void readClient() throws ItemNotFoundException {
        System.out.print("Client ID: ");
        int id = InputUtil.readIntSafe(sc);
        Client c = clientManager.getClientById(id);
        c.read();
    }

    private static void updateClient() throws ItemNotFoundException {
        System.out.print("Client ID: ");
        int id = InputUtil.readIntSafe(sc);
        Client c = clientManager.getClientById(id);
        c.update(sc);
    }

    private static void deleteClient() throws ItemNotFoundException {
        System.out.print("Client ID: ");
        int id = InputUtil.readIntSafe(sc);
        Client c = clientManager.getClientById(id);
        c.delete();
        clientManager.deleteClient(id);
        System.out.println("Removed client.");
    }
}
