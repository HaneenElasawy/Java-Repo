public class CheckString{

    interface StringCheck {
        boolean isBetter(String s1, String s2);
    }

    public static String betterString(String s1, String s2, StringCheck check) {
        if (check.isBetter(s1, s2)) {
            return s1;
        } else {
            return s2;
        }
    }
    public static void main(String[] args) {

        String string1 = "Haneen";
        String string2 = "ITI";

        String longer = betterString(string1, string2,
                (s1, s2) -> s1.length() > s2.length());

        System.out.println("Longer String = " + longer);

       
    }
}
