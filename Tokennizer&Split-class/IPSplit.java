import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Scanner;

public class IPSplit {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        System.out.print("Enter IP address: ");
        String ip = input.nextLine();

        
        String part = "(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)";
        String ipRegex = "^" + part + "\\." + part + "\\." + part + "\\." + part + "$";

        Pattern pattern = Pattern.compile(ipRegex);
        Matcher matcher = pattern.matcher(ip);

        
        if (!matcher.matches()) {
            System.out.println(" Invalid IP Address!");
            input.close();
            return;
        }

        System.out.println("✔ Valid IP Address!");
    
        System.out.println("IP Parts:");
        System.out.println("Part 1 = " + matcher.group(1));
        System.out.println("Part 2 = " + matcher.group(2));
        System.out.println("Part 3 = " + matcher.group(3));
        System.out.println("Part 4 = " + matcher.group(4));

        input.close();
    }
}

