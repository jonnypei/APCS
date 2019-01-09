/**
 * Exercise 5
 */

public class Abecedarian {

    // A word is said to be “abecedarian” if the letters in the word appear in alphabetical order
    public static boolean isAbecedarian(String s) {
        if (s.length() == 1)
            return true;

        if (s.charAt(0) < s.charAt(1))
            return isAbecedarian(s.substring(1));

        return false;
    }

    public static void main(String[] args) {
        String s = "abdest";

        System.out.println("Is '" + s + "' an abecedarian word? " + (isAbecedarian(s) ? "Yes" : "No"));
    }
}
