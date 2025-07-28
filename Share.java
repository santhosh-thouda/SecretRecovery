import java.math.BigInteger;
import java.util.Objects;

public class Share {
    private final int x;
    private final BigInteger y;

    public Share(int x, BigInteger y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public BigInteger getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Share share = (Share) o;
        return x == share.x && Objects.equals(y, share.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}