import java.util.Scanner;

public class AlphabetValidator {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a string: ");
        String input = sc.nextLine();
    //regular expretion
    //boolean isAlphabetOnly = input.matches("[a-zA-Z]+");
    //boolean isAlphabetOnly = input.replaceAll("[a-zA-Z]", "").isEmpty();

        boolean isAlphabetOnly = true;

        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);

            if (!Character.isLetter(ch)) {
                isAlphabetOnly = false;
                break;
            }
        }

        if (isAlphabetOnly)
            System.out.println("The string contains only alphabets.");
        else
            System.out.println("The string does NOT contain only alphabets.");
    }
}
