/**
 * Exercise 6
 */

import java.util.Arrays;

public class Doubloon {

    // A word is said to be a “doubloon” if every letter that appears in the word appears exactly twice
    public static boolean isDoubloon(String s) {
        if (s.length() % 2 != 0)
            return false;

        s = s.toLowerCase();

        char[] arr = s.toCharArray();
        Arrays.sort(arr);

        for (int i = 0; i < s.length(); i += 2) {
            if (arr[i] != arr[i + 1])
                return false;
        }

        return true;
    }

    public static void main(String[] args) {
        String s = "appearer";
        System.out.println("Is '" + s + "' a doubloon word? " + (isDoubloon(s) ? "Yes" : "No"));
    }

}
