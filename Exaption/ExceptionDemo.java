package Exaption;
import java.util.Scanner;

public class ExceptionDemo {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        ExaptionGenerator gen = new ExaptionGenerator();
      
        
        try {
                System.out.print("Enter age: ");
            int age = input.nextInt();
             gen.checkAge(age);   

        } catch (MyCustomException ex) {
            System.out.println("Age Error: " + ex.getMessage());
        } finally {
            System.out.println("Age check finished.\n");
        }

        try {
            System.out.print("Enter numerator (a): ");
            int a = input.nextInt();

            System.out.print("Enter denominator (b): ");
            int b = input.nextInt();

            double result = gen.safeDivide(a, b);
            System.out.println("Result = " + result);
        } catch (MyCustomException ex) {
            System.out.println("Divide Error: " + ex.getMessage());
        } finally {
            System.out.println("Division check finished.\n");
        }

        input.nextLine(); 
        
        try {
            System.out.print("Enter your name: ");
            String name = input.nextLine();
            gen.checkName(name);
        } catch (MyCustomException ex) {
            System.out.println("Name Error: " + ex.getMessage());
        } finally {
            System.out.println("Name check finished.\n");
        }

        input.close();
    }
}
