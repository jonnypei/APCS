/**
 * Exercise 7
 */

import java.util.*;

public class Anagram {

    // Two words are anagrams if they contain the same letters and the same number of each letter
    public static boolean areAnagrams(String s1, String s2) {
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();

        char[] arr1 = s1.toCharArray();
        char[] arr2 = s2.toCharArray();

        Arrays.sort(arr1);
        Arrays.sort(arr2);

        if (Arrays.equals(arr1, arr2))
            return true;

        return false;
    }

    public static void main(String[] args) {
        String s1 = "allen downey";
        String s2 = "well annoyed";

        System.out.println("Are '" + s1 + "' and '" + s2 + "' anagrams? " + (areAnagrams(s1, s2) ? "Yes" : "No"));
    }
}
