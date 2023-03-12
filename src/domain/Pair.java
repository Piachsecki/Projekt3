package domain;

import java.util.Objects;

public final class Pair<T, U> {
    private final T t;
    private final U u;

    public Pair(T t, U u) {
        this.t = t;
        this.u = u;
    }

    public T t() {
        return t;
    }

    public U u() {
        return u;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Pair) obj;
        return Objects.equals(this.t, that.t) &&
                Objects.equals(this.u, that.u);
    }

    @Override
    public int hashCode() {
        return Objects.hash(t, u);
    }

    @Override
    public String toString() {
        return "Pair[" +
                "t=" + t + ", " +
                "u=" + u + ']';
    }


}
