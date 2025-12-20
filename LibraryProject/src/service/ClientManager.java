package service;

import exception.ItemNotFoundException;
import model.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientManager {
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
