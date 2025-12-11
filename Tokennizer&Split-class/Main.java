import java.util.Scanner;
public class Main 
{
  public static void main(String[] args )
 {
	   Scanner input = new Scanner(System.in);
	   
	   System.out.print("Enter sentence: ");
	   String sentence = input.nextLine();
   
   System.out.print("Enter word:");
   String word = input.nextLine();
     
     
     int count = 0; 
     int index = 0;
     
  while ((index = sentence.indexOf(word, index)) != -1)
   {
        count++;
	index += word.length();   
   }
    System.out.println("Occurences = " + count );
 }
}  
