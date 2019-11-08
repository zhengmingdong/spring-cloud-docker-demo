package com.zynn.common.core.pair;

/**
 * 四元不可变元组
 *
 * @param <L>  左边的值
 * @param <CL> 中间左边的值
 * @param <CR> 中间右边的值
 * @param <R>  右边的值
 * @author 袁毅雄
 * @description 四元元组
 * @date 2019/4/9
 */
public class ImmutableFourfold<L, CL, CR, R> extends Fourfold<L, CL, CR, R> {

    private static final ImmutableFourfold NULL = of((Object) null, (Object) null, (Object) null, (Object) null);
    private static final long serialVersionUID = 1L;
    public final L left;
    public final CL centreLeft;
    public final CR centreRight;
    public final R right;

    public ImmutableFourfold(L left, CL centreLeft, CR centreRight, R right) {
        this.left = left;
        this.centreLeft = centreLeft;
        this.centreRight = centreRight;
        this.right = right;
    }

    public static <L, CL, CR, R> ImmutableFourfold<L, CL, CR, R> nullFourfold() {
        return NULL;
    }

    public static <L, CL, CR, R> ImmutableFourfold<L, CL, CR, R> of(L left, CL centreLeft, CR centreRight, R right) {
        return new ImmutableFourfold(left, centreLeft, centreRight, right);
    }

    @Override
    public L getLeft() {
        return this.left;
    }

    @Override
    public CL getCentreLeft() {
        return this.centreLeft;
    }

    @Override
    public CR getCentreRight() {
        return this.centreRight;
    }

    @Override
    public R getRight() {
        return this.right;
    }
}