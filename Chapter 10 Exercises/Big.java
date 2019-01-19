import java.math.BigInteger;
import java.util.Scanner;

public class Big {

    // Return n!
    public static BigInteger factorial(int n) {
        BigInteger ret = BigInteger.ONE;
        for (int i = 1; i <= n; i++) {
            ret = ret.multiply(BigInteger.valueOf(i));
        }

        return ret;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        for (int i = 1; i <= n; i++) {
            System.out.println(i + "! = " + factorial(i));
        }
    }


}
