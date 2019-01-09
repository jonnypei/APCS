/**
 * Exercise 4
 */

public class Recurse {

    /**
     * Returns the first character of the given String.
     */
    public static char first(String s) {
        return s.charAt(0);
    }

    /**
     * Returns all but the first letter of the given String.
     */
    public static String rest(String s) {
        return s.substring(1);
    }


    /**
     * Returns all but the first and last letter of the String.
     */
    public static String middle(String s) {
        return s.substring(1, s.length() - 1);
    }

    /**
     * Returns the length of the given String.
     */
    public static int length(String s) {
        return s.length();
    }

    /**
     * Displays the letters of a given string, one on each line
     */
    public static void printString(String s) {
        if (length(s) == 0)
            return;

        System.out.println(first(s));
        printString(rest(s));
        return;
    }

    /**
     * Does same thing as printString but instead displays the letters
     * backwards (again, one letter per line)
     */
    public static void printBackward(String s) {
        if (length(s) == 0)
            return;

        if (length(s) == 1) {
            System.out.println(s);
            return;
        }

        String temp = first(s) + middle(s);

        for (int i = 0; i < length(temp); i++)
            s = rest(s);

        System.out.println(s);
        printBackward(temp);
        return;
    }

    /**
     * Returns a string containing the same letters as the given string,
     * but in reverse order
     */
    public static String reverseString(String s) {
        if (length(s) == 0)
            return s;

        return reverseString(rest(s)) + first(s);
    }

    /**
     * Returns a boolean describing whether the given string is a palindrome
     */
    public static boolean isPalindrome(String s) {
        if (length(s) == 0 || length(s) == 1)
            return true;

        String temp = middle(s);
        char first = first(s);

        for (int i = 0; i < length(temp) + 1; i++)
            s = rest(s);
        char last = first(s);

        if (first == last)
            return isPalindrome(temp);

        return false;
    }

    public static void main(String[] args) {
        String s = "I_like_eating_cookies";

        System.out.println("s = " + s);
        System.out.println("\nThe first character of s is: " + first(s));
        System.out.println("\nAll but the first letter of s is: " + rest(s));
        System.out.println("\nAll but the first and last letter of s is: " + middle(s));
        System.out.println("\nThe length of s is: " + length(s));

        System.out.println("\nAll the letters of s, forwards:");
        printString(s);

        System.out.println("\nAll the letters of s, backwards:");
        printBackward(s);

        String reverse = reverseString(s);
        System.out.println("\nThis is s reversed: " + reverse);

        String palindrome = "racecar";
        System.out.println("\nIs '" + palindrome + "' a palindrome? " + (isPalindrome(palindrome) ? "Yes" : "No"));
    }
}
