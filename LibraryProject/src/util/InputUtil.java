package util;

import service.ClientManager;
import service.Library;

import java.util.Scanner;

public class InputUtil {

    public static int readIntSafe(Scanner sc) {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Enter a valid number: ");
            }
        }
    }

    public static boolean isIdUsed(int id, Library library, ClientManager clientManager) {
        boolean inItems = library.getItems().stream().anyMatch(i -> i.getId() == id);
        boolean inClients = clientManager.getClients().stream().anyMatch(c -> c.getId() == id);
        return inItems || inClients;
    }

    public static int readUniqueId(Scanner sc, Library library, ClientManager clientManager) {
        while (true) {
            int id = readIntSafe(sc);
            if (isIdUsed(id, library, clientManager)) {
                System.out.print("ID already exists. Enter another ID: ");
            } else {
                return id;
            }
        }
    }
}
