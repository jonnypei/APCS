import java.math.BigInteger;

public class Exercise5 {

    // Returns x^n
    public static BigInteger pow(int x, int n) {
        if (n == 0)
            return BigInteger.ONE;

        BigInteger t = pow(x,n / 2);

        if (n % 2 == 0) {
            return t.multiply(t);
        } else {
            return t.multiply(t).multiply(BigInteger.valueOf(x));
        }
    }

    public static void main(String[] args) {
        int x = 5;
        int n = 5;

        System.out.println(pow(x, n));
    }
}
