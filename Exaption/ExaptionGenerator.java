
package Exaption;

public class ExaptionGenerator {
    public void checkAge(int age)throws MyCustomException {
        if(age <= 0 || age > 100)
{
     throw new MyCustomException("Invalid age : " + age);
}
       System.out.println("Age is Valid : " + age );
    }
        public double safeDivide(int a, int b) throws MyCustomException {
        if (b == 0) {
            throw new MyCustomException("Cannot divide by zero");
        }
        return (double) a / b;
    }


    public void checkName(String name) throws MyCustomException {
        if (name == null || name.isBlank()) {
            throw new MyCustomException("Name cannot be empty");
        }
        System.out.println("Hello, " + name);
    }
} 


   class MyCustomException extends Exception{
    
    public MyCustomException (String message){
        super(message);
    }
}
