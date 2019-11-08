package com.zynn.common.core.pair;

import org.apache.commons.lang3.builder.CompareToBuilder;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author 袁毅雄
 * @description 四元元组
 * @date 2019/4/11
 */
public abstract class Fourfold<L, CL, CR, R> implements Comparable<Fourfold<L, CL, CR, R>>, Serializable {
    private static final long serialVersionUID = 1L;

    public static <L, CL, CR, R> Fourfold<L, CL, CR, R> of(L left, CL centreLeft, CR centreRight, R right) {
        return new ImmutableFourfold(left, centreLeft, centreRight, right);
    }

    public abstract L getLeft();

    public abstract CL getCentreLeft();

    public abstract CR getCentreRight();

    public abstract R getRight();

    public int compareTo(Fourfold<L, CL, CR, R> other) {
        return (new CompareToBuilder())
                .append(this.getLeft(), other.getLeft())
                .append(this.getCentreLeft(), other.getCentreLeft())
                .append(this.getCentreRight(), other.getCentreRight())
                .append(this.getRight(), other.getRight())
                .toComparison();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof Fourfold)) {
            return false;
        } else {
            Fourfold<?, ?, ?, ?> other = (Fourfold) obj;
            return Objects.equals(this.getLeft(), other.getLeft()) &&
                    Objects.equals(this.getCentreLeft(), other.getCentreLeft()) &&
                    Objects.equals(this.getCentreRight(), other.getCentreRight()) &&
                    Objects.equals(this.getRight(), other.getRight());
        }
    }

    public int hashCode() {
        return (this.getLeft() == null ? 0 : this.getLeft().hashCode()) ^
                (this.getCentreLeft() == null ? 0 : this.getCentreLeft().hashCode()) ^
                (this.getCentreRight() == null ? 0 : this.getCentreRight().hashCode()) ^
                (this.getRight() == null ? 0 : this.getRight().hashCode());
    }

    public String toString() {
        return "(" + this.getLeft() + "," + this.getCentreLeft() + "," + "," + this.getCentreRight() + "," + this.getRight() + ")";
    }

    public String toString(String format) {
        return String.format(format, this.getLeft(), this.getCentreLeft(), this.getCentreRight(), this.getRight());
    }

}

