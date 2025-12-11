import java.util.Scanner;
import java.util.StringTokenizer;
public class TokenizerDemo
{
  public static void main(String[] args )
 {
   String sentence = "ITI develops people and ITI house of developers and ITI fo people";
   
   String modified = sentence.replace("ITI" , "#");
   
   StringTokenizer tokenizer = new StringTokenizer(modified, "#");
   
   while (tokenizer.hasMoreTokens()) 
   {
     System.out.println(tokenizer.nextToken().trim());
   }
 }
 
 }
