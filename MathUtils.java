import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class MathUtils {
    public static BigInteger lcm(BigInteger a, BigInteger b) {
        return a.multiply(b).abs().divide(a.gcd(b));
    }

    public static BigInteger gcd(BigInteger a, BigInteger b) {
        return a.gcd(b);
    }

    public static List<List<Share>> combinations(List<Share> list, int k) {
        List<List<Share>> result = new ArrayList<>();
        combine(list, 0, k, new ArrayList<>(), result);
        return result;
    }

    private static void combine(List<Share> list, int start, int k, List<Share> temp, List<List<Share>> result) {
        if (k == 0) {
            result.add(new ArrayList<>(temp));
            return;
        }
        for (int i = start; i <= list.size() - k; i++) {
            temp.add(list.get(i));
            combine(list, i + 1, k - 1, temp, result);
            temp.remove(temp.size() - 1);
        }
    }

    public static BigInteger lagrangeInterpolation(List<Share> shares) {
        BigInteger result = BigInteger.ZERO;
        for (int i = 0; i < shares.size(); i++) {
            BigInteger xi = BigInteger.valueOf(shares.get(i).getX());
            BigInteger yi = shares.get(i).getY();
            BigInteger numerator = BigInteger.ONE;
            BigInteger denominator = BigInteger.ONE;
            for (int j = 0; j < shares.size(); j++) {
                if (i == j) continue;
                BigInteger xj = BigInteger.valueOf(shares.get(j).getX());
                numerator = numerator.multiply(xj.negate());
                denominator = denominator.multiply(xi.subtract(xj));
            }
            BigInteger term = yi.multiply(numerator).divide(denominator);
            result = result.add(term);
        }
        return result;
    }
}