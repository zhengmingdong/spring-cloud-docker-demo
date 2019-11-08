package com.zynn.common.core.pair;

/**
 * 四元可变元组
 *
 * @param <L>  左边的值
 * @param <CL> 中间左边的值
 * @param <CR> 中间右边的值
 * @param <R>  右边的值
 * @author 袁毅雄
 * @description 四元元组
 * @date 2019/4/9
 */
public class MutableFourfold<L, CL, CR, R> extends Fourfold<L, CL, CR, R> {

    private static final MutableFourfold NULL = of((Object) null, (Object) null, (Object) null, (Object) null);
    private static final long serialVersionUID = 1L;
    public L left;
    public CL centreLeft;
    public CR centreRight;
    public R right;

    public MutableFourfold(L left, CL centreLeft, CR centreRight, R right) {
        this.left = left;
        this.centreLeft = centreLeft;
        this.centreRight = centreRight;
        this.right = right;
    }

    public static <L, CL, CR, R> MutableFourfold<L, CL, CR, R> nullFourfold() {
        return NULL;
    }

    public static <L, CL, CR, R> MutableFourfold<L, CL, CR, R> of(L left, CL centreLeft, CR centreRight, R right) {
        return new MutableFourfold(left, centreLeft, centreRight, right);
    }

    @Override
    public L getLeft() {
        return this.left;
    }

    public L setLeft(L left) {
        this.left = left;
        return this.left;
    }

    @Override
    public CL getCentreLeft() {
        return this.centreLeft;
    }

    public CL setCentreLeft(CL centreLeft) {
        this.centreLeft = centreLeft;
        return this.centreLeft;
    }

    @Override
    public CR getCentreRight() {
        return this.centreRight;
    }


    public CR setCentreRight(CR centreRight) {
        this.centreRight = centreRight;
        return this.centreRight;
    }

    @Override
    public R getRight() {
        return this.right;
    }

    public R setRight(R right) {
        this.right = right;
        return this.right;
    }
}