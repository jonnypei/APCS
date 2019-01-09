/**
 * Exercise 8
 */

public class Scrabble {

    // Returns whether you can spell the given word using the given tiles
    public static boolean canSpell(String tiles, String word) {
        char[] arr = word.toCharArray();

        for (int i = 0; i < tiles.length(); i++) {
            for (int j = 0; j < word.length(); j++) {
                if (tiles.charAt(i) == arr[j])
                    arr[j] = ' ';
            }
        }

        for (char c : arr)
            if (c != ' ')
                return false;

        return true;
    }

    public static void main(String[] args) {
        String tiles = "quijibo";
        String word = "jib";

        System.out.println("Using the tiles '" + tiles + "', can we spell the word '" + word + "'? "
                           + (canSpell(tiles, word) ? "Yes" : "No"));
    }
}
