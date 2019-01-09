/**
 * Exercise 1
 */

public class Test {

    // Displays the type of the given object
    public static void type(Object value) {
        if (value.getClass() == Integer.class) {
            System.out.println("int");
        } else if (value.getClass() == String.class) {
            System.out.println("String");
        } else if (value.getClass() == Character.class) {
            System.out.println("char");
        } else if (value.getClass() == Double.class) {
            System.out.println("double");
        } else if (value.getClass() == Boolean.class) {
            System.out.println("boolean");
        }
    }

    public static void main(String[] args) {
        boolean b1 = true;
        boolean b2 = false;
        char c1 = 'b';
        char c2 = 'f';
        int i1 = 123;
        int i2 = 54;
        double d1 = 32.56;
        double d2 = 5.9;
        String s1 = "GoodMorning";
        String s2 = "Happy Birthday";

        // boolean + boolean (Doesn't work)
        //Object o1 = b1 + b2;
        System.out.println("boolean + boolean -> error");

        // boolean + char (Doesn't work)
        //Object o2 = b1 + c1;
        System.out.println("boolean + char -> error");

        // boolean + int (Doesn't work)
        //Object o3 = b1 + i1;
        System.out.println("boolean + int -> error");

        // boolean + double (Doesn't work)
        //Object o4 = b1 + d1;
        System.out.println("boolean + double -> error");

        // boolean + String
        Object o5 = b1 + s1;
        System.out.print("boolean + String -> ");
        type(o5);

        // char + char
        Object o6 = c1 + c2;
        System.out.print("char + char -> ");
        type(o6);

        // char + int
        Object o7 = c1 + i1;
        System.out.print("char + int -> ");
        type(o7);

        // char + double
        Object o8 = c1 + d1;
        System.out.print("char + double -> ");
        type(o8);

        // char + String
        Object o9 = c1 + s1;
        System.out.print("char + String -> ");
        type(o9);

        // int + int
        Object o10 = i1 + i2;
        System.out.print("int + int -> ");
        type(o10);

        // int + double
        Object o11 = i1 + d1;
        System.out.print("int + double -> ");
        type(o11);

        // int + String
        Object o12 = i1 + s1;
        System.out.print("int + String -> ");
        type(o12);

        // double + double
        Object o13 = d1 + d2;
        System.out.print("double + double -> ");
        type(o13);

        // double + String
        Object o14 = d1 + s1;
        System.out.print("double + String -> ");
        type(o14);

        // String + String
        Object o15 = s1 + s2;
        System.out.print("String + String -> ");
        type(o15);

        // If x is a char, and you perform the incrementation, x++, on it, then you just
        // increase the "character integer code" of the char, and there is thus no error.
        // However, if you perform the incrementation, x = x + 1, since a char is being
        // added to an int, then the entire expression, "x + 1", will be "upgraded" to
        // an int. Thus, when you try to assign an int value to a char, there will be an
        // error message.

        // Adding "" to any other type, the entire expression will automatically upgrade
        // to a String

        // For any data type, you can assign any value type that is less than or equal to
        // the "level" of that data type. For example, you can assign an int to a double
        // but not vice versa because int is on a lower level than double.
    }
}
