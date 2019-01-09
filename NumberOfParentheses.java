/**
 * Exercise 3
 */

public class NumberOfParentheses {

    // Finds number of open and closing parentheses in the given string
    public static int solve(String s) {
        int count = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                count++;
            } else if (c == ')') {
                count--;
            }
        }

        return count;
    }

    public static void main(String[] args) {
        String s1 = "((3 + 7) * 2)";
        String s2 = "((((((((((((fdsjkfdsa))) + fdsafdas ())";
        String s3 = "I like watching ()())) anime and eating ((food))(";

        System.out.println("s1 count: " + solve(s1));
        System.out.println("s2 count: " + solve(s2));
        System.out.println("s3 count: " + solve(s3));
    }
}
