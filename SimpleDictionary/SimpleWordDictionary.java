import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Iterator;
import java.util.Scanner;

public class SimpleWordDictionary {

    private Map<Character, TreeSet<String>> dictionary;

    // constructor
    public SimpleWordDictionary() {
        dictionary = new TreeMap<>();

        for (char c = 'a'; c <= 'z'; c++) {
            dictionary.put(c, new TreeSet<String>());
        }
    }

    // adding words
    public void addWord(String word) {
        if (word == null || word.isEmpty()) {
            return;
        }

        word = word.trim().toLowerCase();

        if (word.isEmpty()) {
            return;
        }

        char firstLetter = word.charAt(0);

        if (firstLetter < 'a' || firstLetter > 'z') {
            return;
        }

        TreeSet<String> wordsForLetter = dictionary.get(firstLetter);
        wordsForLetter.add(word);
    }

    public void printAll() {
        // Iterator 
        Iterator<Map.Entry<Character, TreeSet<String>>> it =
                dictionary.entrySet().iterator();

        boolean allEmpty = true;

        while (it.hasNext()) {
            Map.Entry<Character, TreeSet<String>> entry = it.next();
            char letter = entry.getKey();
            TreeSet<String> words = entry.getValue();

            if (words.isEmpty()) {
                continue;
            }

            allEmpty = false;
            System.out.println("Letter: " + letter);
            System.out.println("Words: " + words);
            System.out.println("---------------------");
        }

        if (allEmpty) {
            System.out.println("Dictionary is empty (no words added yet).");
        }
    }

    public void printByLetter(char letter) {
        letter = Character.toLowerCase(letter);

        TreeSet<String> words = dictionary.get(letter);
        if (words == null || words.isEmpty()) {
            System.out.println("No words found for letter: " + letter);
        } else {
            System.out.println("Words for letter '" + letter + "': " + words);
        }
    }

    public static void main(String[] args) {
        SimpleWordDictionary dict = new SimpleWordDictionary();
        Scanner sc = new Scanner(System.in);
     
        dict.addWord("apple");
        dict.addWord("ant");
        dict.addWord("angel");

        dict.addWord("banana");
        dict.addWord("ball");
        dict.addWord("bird");

        dict.addWord("cat");
        dict.addWord("car");
        dict.addWord("city");

        dict.addWord("dog");
        dict.addWord("door");
        dict.addWord("desk");

        dict.addWord("egg");
        dict.addWord("ear");
        dict.addWord("earth");

        dict.addWord("fish");
        dict.addWord("frog");
        dict.addWord("flower");

        dict.addWord("goat");
        dict.addWord("glass");
        dict.addWord("green");

        dict.addWord("hat");
        dict.addWord("horse");
        dict.addWord("house");

        dict.addWord("ice");
        dict.addWord("island");

        dict.addWord("juice");
        dict.addWord("jacket");

        dict.addWord("kite");
        dict.addWord("king");

        dict.addWord("lion");
        dict.addWord("lamp");

        dict.addWord("monkey");
        dict.addWord("moon");

        dict.addWord("nose");
        dict.addWord("nurse");

        dict.addWord("orange");
        dict.addWord("owl");

        dict.addWord("pizza");
        dict.addWord("panda");

        dict.addWord("queen");
        dict.addWord("quick");

        dict.addWord("rabbit");
        dict.addWord("road");

        dict.addWord("sun");
        dict.addWord("sand");
        dict.addWord("star");

        dict.addWord("tree");
        dict.addWord("table");
        dict.addWord("train");

        dict.addWord("umbrella");
        dict.addWord("universe");

        dict.addWord("violin");
        dict.addWord("village");

        dict.addWord("water");
        dict.addWord("window");

        dict.addWord("xray");
        dict.addWord("xenon");

        dict.addWord("yellow");
        dict.addWord("yard");

        dict.addWord("zebra");
        dict.addWord("zero");

        //menue
        while (true) {
            System.out.println("===== Simple Word Dictionary =====");
            System.out.println("1) Add word");
            System.out.println("2) Print all letters and words");
            System.out.println("3) Print words of a given letter");
            System.out.println("4) Exit");
            System.out.print("Choose: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice, try again.\n");
                continue;
            }

            if (choice == 1) {
                System.out.print("Enter word: ");
                String word = sc.nextLine();
                dict.addWord(word);
                System.out.println("Word added.\n");

            } else if (choice == 2) {
                dict.printAll();
                System.out.println();

            } else if (choice == 3) {
                System.out.print("Enter letter: ");
                String input = sc.nextLine();
                if (input.isEmpty()) {
                    System.out.println("No letter entered.\n");
                    continue;
                }
                char letter = input.charAt(0);
                dict.printByLetter(letter);
                System.out.println();

            } else if (choice == 4) {
                System.out.println("Bye!");
                break;

            } else {
                System.out.println("Invalid choice, try again.\n");
            }
        }

        sc.close();
    }
}
