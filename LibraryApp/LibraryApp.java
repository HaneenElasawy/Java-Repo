import java.util.*;
import java.util.stream.Collectors;

/* ===== BONUS: CRUD interface implemented by BOTH LibraryItem and Client ===== */
interface CrudEntity {
    void create();
    void read();
    void update(Scanner sc);
    void delete();
}

/* ===== Custom Exception ===== */
class ItemNotFoundException extends Exception {
    public ItemNotFoundException(String msg) {
        super(msg);
    }
}

/* ===== Library Items (OOP + Abstract + Inheritance) ===== */
abstract class LibraryItem implements CrudEntity {
    private int id;
    private String title;

    // Stock (multiple copies)
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

    // CRUD interface (simple student style)
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

class Book extends LibraryItem {
    private String author;
    private int pages;

    public Book(int id, String title, String author, int pages, int copies) {
        super(id, title, copies);
        this.author = author;
        this.pages = pages;
    }

    @Override
    public String getItemDetails() {
        return "Book [id=" + getId() +
                ", title=" + getTitle() +
                ", author=" + author +
                ", pages=" + pages +
                ", available=" + getAvailableCopies() + "/" + getTotalCopies() + "]";
    }
}

class Magazine extends LibraryItem {
    private int issueNumber;
    private String publicationDate;

    public Magazine(int id, String title, int issueNumber, String publicationDate, int copies) {
        super(id, title, copies);
        this.issueNumber = issueNumber;
        this.publicationDate = publicationDate;
    }

    @Override
    public String getItemDetails() {
        return "Magazine [id=" + getId() +
                ", title=" + getTitle() +
                ", issue=" + issueNumber +
                ", date=" + publicationDate +
                ", available=" + getAvailableCopies() + "/" + getTotalCopies() + "]";
    }
}

/* ===== Library (Generics + Wildcards + Streams) ===== */
class Library {
    private List<LibraryItem> items = new ArrayList<>();

    // Generics: allow adding any subclass
    public <T extends LibraryItem> void addItem(T item) {
        items.add(item);
    }

    // Streams: find by id
    public LibraryItem getItemById(int id) throws ItemNotFoundException {
        return items.stream()
                .filter(i -> i.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ItemNotFoundException("Library item not found."));
    }

    // Generics retrieval (shows Class<T> usage)
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

    // Wildcards: display any list of subclasses
    public void displayAnyItems(List<? extends LibraryItem> list) {
        if (list == null || list.isEmpty()) {
            System.out.println("No items to display.");
            return;
        }
        list.forEach(i -> System.out.println(i.getItemDetails()));
    }

    // Streams: filter available items
    public List<LibraryItem> getAvailableItems() {
        return items.stream()
                .filter(LibraryItem::isAvailable)
                .collect(Collectors.toList());
    }

    public void updateItemTitle(int id, String newTitle) throws ItemNotFoundException {
        LibraryItem item = getItemById(id);
        item.setTitle(newTitle);
    }

    public void deleteItem(int id) throws ItemNotFoundException {
        LibraryItem item = getItemById(id);
        items.remove(item);
    }

    public List<LibraryItem> getItems() {
        return items;
    }
}

/* ===== Clients (relation: client has borrowed items) ===== */
class Client implements CrudEntity {
    private int id;
    private String name;
    private String email;

    // Each client has a collection of borrowed library items
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

    // Streams: check borrowed
    public boolean hasBorrowedItem(int itemId) {
        return borrowedItems.stream().anyMatch(i -> i.getId() == itemId);
    }

    // Return one copy (remove one occurrence)
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

/* ===== Client Manager (Streams + CRUD helpers) ===== */
class ClientManager {
    private List<Client> clients = new ArrayList<>();

    public void addClient(Client c) {
        clients.add(c);
    }

    public Client getClientById(int id) throws ItemNotFoundException {
        return clients.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ItemNotFoundException("Client not found."));
    }

    public void deleteClient(int id) throws ItemNotFoundException {
        Client c = getClientById(id);
        clients.remove(c);
    }

    public void displayAllClients() {
        if (clients.isEmpty()) {
            System.out.println("No clients.");
            return;
        }
        clients.forEach(c -> System.out.println(c.getClientDetails()));
    }

    public List<Client> getClients() {
        return clients;
    }
}

/* ===== Main + Menu (CRUD + Borrow/Return + Validations) ===== */
public class LibraryApp {

    private static final Scanner sc = new Scanner(System.in);
    private static final Library library = new Library();
    private static final ClientManager clientManager = new ClientManager();

    public static void main(String[] args) {
        // sample data (you can remove)
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

            choice = readIntSafe();

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

        int c = readIntSafe();

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

        int c = readIntSafe();

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
        int userId = readIntSafe();
        System.out.print("Item ID: ");
        int itemId = readIntSafe();

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
        int userId = readIntSafe();
        System.out.print("Item ID: ");
        int itemId = readIntSafe();

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
            library.displayAnyItems(available); // wildcard usage
        }
    }

    private static void addBook() {
        System.out.print("Book ID: ");
        int id = readUniqueId();

        System.out.print("Title: ");
        String title = sc.nextLine();

        System.out.print("Author: ");
        String author = sc.nextLine();

        System.out.print("Pages: ");
        int pages = readIntSafe();

        System.out.print("Total copies: ");
        int copies = readIntSafe();

        Book b = new Book(id, title, author, pages, copies);
        library.addItem(b);
        b.create();
    }

    private static void addMagazine() {
        System.out.print("Magazine ID: ");
        int id = readUniqueId();

        System.out.print("Title: ");
        String title = sc.nextLine();

        System.out.print("Issue number: ");
        int issue = readIntSafe();

        System.out.print("Publication date: ");
        String date = sc.nextLine();

        System.out.print("Total copies: ");
        int copies = readIntSafe();

        Magazine m = new Magazine(id, title, issue, date, copies);
        library.addItem(m);
        m.create();
    }

    private static void readItem() throws ItemNotFoundException {
        System.out.print("Item ID: ");
        int id = readIntSafe();
        LibraryItem item = library.getItemById(id);
        item.read();
    }

    private static void updateItem() throws ItemNotFoundException {
        System.out.print("Item ID: ");
        int id = readIntSafe();
        LibraryItem item = library.getItemById(id);
        item.update(sc);
    }

    private static void deleteItem() throws ItemNotFoundException {
        System.out.print("Item ID: ");
        int id = readIntSafe();
        LibraryItem item = library.getItemById(id);
        item.delete();
        library.deleteItem(id);
        System.out.println("Removed from library.");
    }

    private static void addClient() {
        System.out.print("Client ID: ");
        int id = readUniqueId();

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
        int id = readIntSafe();
        Client c = clientManager.getClientById(id);
        c.read();
    }

    private static void updateClient() throws ItemNotFoundException {
        System.out.print("Client ID: ");
        int id = readIntSafe();
        Client c = clientManager.getClientById(id);
        c.update(sc);
    }

    private static void deleteClient() throws ItemNotFoundException {
        System.out.print("Client ID: ");
        int id = readIntSafe();
        Client c = clientManager.getClientById(id);
        c.delete();
        clientManager.deleteClient(id);
        System.out.println("Removed client.");
    }

    // Validations: unique IDs across BOTH items & clients (Streams)
    private static boolean isIdUsed(int id) {
        boolean inItems = library.getItems().stream().anyMatch(i -> i.getId() == id);
        boolean inClients = clientManager.getClients().stream().anyMatch(c -> c.getId() == id);
        return inItems || inClients;
    }

    private static int readUniqueId() {
        while (true) {
            int id = readIntSafe();
            if (isIdUsed(id)) {
                System.out.print("ID already exists. Enter another ID: ");
            } else {
                return id;
            }
        }
    }

    private static int readIntSafe() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Enter a valid number: ");
            }
        }
    }
}
