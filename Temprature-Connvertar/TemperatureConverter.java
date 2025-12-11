import java.util.Scanner;
import java.util.function.Function;

public class TemperatureConverter
{ 

public static void main(String[] args) 
 {

Scanner input = new Scanner(System.in);

  Function<Double , Double> cToF = c -> c * 9 / 5 + 32;
  
 System.out.print("Enter temperature in Celsius : " );
 
  	double celsius = input.nextDouble();
  	double fahrenheit = cToF.apply(celsius);
  
  System.out.println("Temperature in Fahrenhiet = " + fahrenheit);
  
  }
  
}
 
