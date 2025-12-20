package model;

import java.util.Scanner;

public interface CrudEntity {
    void create();
    void read();
    void update(Scanner sc);
    void delete();
}
