/**
 * Exercise 2
 *
 * Write a method called letterHist that takes a string as a parameter and returns a
 * histogram of the letters in the string.
 */

public class LetterHistogram {

    // Prints out a histogram, displaying the frequency of each letter in the string
    public static void letterHist(String s) {
        int[] freq = new int[26];

        System.out.println("In the string, '" + s + "', the frequency of each letter is: ");

        s = s.toUpperCase();

        for (int i = 0; i < s.length(); i++) {
            freq[s.charAt(i) - 'A']++;
        }

        for (int i = 0; i < 26; i++) {
            char letter = (char) ('A' + i);

            System.out.println(Character.toUpperCase(letter) + ": " + freq[i]);
        }
    }

    public static void main(String[] args) {
        letterHist("happy");
    }
}
