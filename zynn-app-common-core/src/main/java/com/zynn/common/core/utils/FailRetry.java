package com.zynn.common.core.utils;

import com.google.common.collect.Lists;
import com.zynn.common.core.function.Operation;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Sets;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.*;
import java.util.stream.Collectors;

/**
 * @author 袁毅雄
 * @description 工具
 * 1. : ids 查询一个列表
 * 2. 失败重试: id 查询一个对象
 * @date 2019/4/21
 */
@Slf4j
public class FailRetry {

    /**
     * 失败重试次数
     */
    private final static Integer DEFAULT_TRY_FREQUENCY = 10;

    /**
     * 失败之后休眠时间
     */
    private final static Long DEFAULT_TRY_INTERVAL_TIME = 1L;

    /**
     * 批量查询限制数
     */
    private final static Integer DEFAULT_BATCH_SIZE = 200;

    /**
     * @return Object
     * @Author 袁毅雄
     * @Description 失败重试: id 查询一个对象, 最多重试 10 次，每次重试时间间隔 200 毫秒
     * @Date 2019/4/16
     * @Param [id, queryOperation, successOperation, failOperation]
     **/
    public static <T> T one(Long id, LongFunction<T> queryOperation, BiConsumer<Integer, T> successOperation, IntFunction<T> failOperation) {
        return one(id, DEFAULT_TRY_FREQUENCY, DEFAULT_TRY_INTERVAL_TIME, queryOperation, successOperation, failOperation);
    }

    /**
     * @return Object
     * @Author 袁毅雄
     * @Description 失败重试: id 查询一个对象, 最多重试 tryFrequency 次，每次重试时间间隔 tryIntervalTime 毫秒
     * @Date 2019/4/16
     * @Param [id, tryFrequency, tryIntervalTime, queryOperation, successOperation, failOperation]
     **/
    public static <T> T one(Long id, Integer tryFrequency, Long tryIntervalTime, LongFunction<T> queryOperation, BiConsumer<Integer, T> successOperation, IntFunction<T> failOperation) {
        if (Objects.isNull(id)) {
            log.error("查询参数有误");
            return failOperation.apply(0);
        }
        T result = null;
        for (int i = 1; i <= tryFrequency; i++) {
            try {
                result = queryOperation.apply(id);
                if (Objects.nonNull(result)) {
                    successOperation.accept(i, result);
                    break;
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                sleep(tryIntervalTime);
                continue;
            }
        }
        return Objects.nonNull(result) ? result : failOperation.apply(tryFrequency);
    }


    /**
     * @return List
     * @Author 袁毅雄
     * @Description 失败重试: ids 查询一个列表, 最多重试 10 次，每次重试时间间隔 200 毫秒
     * @Date 2019/4/16
     * @Param [ids, queryOperation, successOperation, failOperation]
     **/
    public static <T> List<T> lists(List<Long> ids, Function<List<Long>, List<T>> queryOperation, BiConsumer<Integer, List<T>> successOperation, IntFunction<List<T>> failOperation) {
        Set<Long> idSet = Optional.ofNullable(ids).orElse(Lists.newArrayList()).stream().collect(Collectors.toSet());
        return lists(idSet, queryOperation, successOperation, failOperation);
    }

    /**
     * @return List
     * @Author 袁毅雄
     * @Description 失败重试: ids 查询一个列表, 最多重试 10 次，每次重试时间间隔 200 毫秒
     * @Date 2019/4/16
     * @Param [ids, queryOperation, successOperation, failOperation]
     **/
    public static <T> List<T> lists(Set<Long> ids, Function<List<Long>, List<T>> queryOperation, BiConsumer<Integer, List<T>> successOperation, IntFunction<List<T>> failOperation) {
        List<Long> idList = Optional.ofNullable(ids).orElse(Sets.newHashSet()).stream().collect(Collectors.toList());
        return lists(idList, DEFAULT_TRY_FREQUENCY, DEFAULT_TRY_INTERVAL_TIME, queryOperation, successOperation, failOperation);
    }

    /**
     * @return List
     * @Author 袁毅雄
     * @Description 失败重试: ids 查询一个列表, 最多重试 tryFrequency 次，每次重试时间间隔 tryIntervalTime 毫秒
     * @Date 2019/4/16
     * @Param [ids, tryFrequency, tryIntervalTime, queryOperation, successOperation, failOperation]
     **/
    private static <T> List<T> lists(List<Long> idList, Integer tryFrequency, Long tryIntervalTime, Function<List<Long>, List<T>> queryOperation, BiConsumer<Integer, List<T>> successOperation, IntFunction<List<T>> failOperation) {
        List<T> result = null;
        for (int i = 1; i <= tryFrequency; i++) {
            try {
                result = batchLists(idList, DEFAULT_BATCH_SIZE, queryOperation);
                if (Objects.nonNull(result) && !result.isEmpty()) {
                    successOperation.accept(i, result);
                    break;
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                sleep(tryIntervalTime);
                continue;
            }
        }
        return Objects.nonNull(result) ? result : failOperation.apply(tryFrequency);
    }

    /***
     * 批量查询,跨服务调度参数过长问题
     * @param idList
     * @param batchSize
     * @param queryOperation
     * @param <T>
     * @return
     */
    private static <T> List<T> batchLists(List<Long> idList, int batchSize, Function<List<Long>, List<T>> queryOperation) {
        List<T> result = Lists.newArrayList();
        if (CollectionUtils.isEmpty(idList)) {
            log.error("查询参数有误");
            return result;
        }
        for (int i = 0, j = i + batchSize; i < idList.size(); i += batchSize, j += batchSize) {
            final int begin = i < idList.size() ? i : idList.size() - 1;
            final int end = j < idList.size() ? j : idList.size();
            List<T> itmeResult = queryOperation.apply(idList.subList(begin, end));
            if (!CollectionUtils.isEmpty(itmeResult)) {
                result.addAll(itmeResult);
            }
            /**延缓请求并发**/
            sleep(10L);
        }
        return result;
    }


    /**
     * @Author 袁毅雄
     * @Description 失败重试: 最多重试 10 次，每次重试时间间隔 200 毫秒
     * @Date 2019/4/16
     * @Param [operation, successOperation, failOperation]
     **/
    public static void operation(Operation operation, IntConsumer successOperation, IntConsumer failOperation) {
        operation(DEFAULT_TRY_FREQUENCY, DEFAULT_TRY_INTERVAL_TIME, operation, successOperation, failOperation);
    }

    /**
     * @Author 袁毅雄
     * @Description 失败重试: 最多重试 tryFrequency 次，每次重试时间间隔 tryIntervalTime 毫秒
     * @Date 2019/4/16
     * @Param [tryFrequency, tryIntervalTime, operation, affirmOperation, successOperation, failOperation]
     **/
    public static void operation(Integer tryFrequency, Long tryIntervalTime, Operation operation, IntConsumer successOperation, IntConsumer failOperation) {
        int n = 0;
        for (int i = 1; i <= tryFrequency; i++) {
            try {
                operation.operation();
                n++;
                break;
            } catch (Exception e) {
                log.error(e.getMessage());
                sleep(tryIntervalTime);
                continue;
            }
        }
        if (n <= FailRetry.DEFAULT_TRY_FREQUENCY) {
            successOperation.accept(n);
        } else {
            failOperation.accept(n);
        }
    }

    /**
     * @Author 袁毅雄
     * @Description 失败重试, 手动控制正确失败进行强制确认: 最多重试 10 次，每次重试时间间隔 200 毫秒
     * @Date 2019/4/16
     * @Param [operation, successOperation, failOperation]
     **/
    public static void operation(Operation operation, BooleanSupplier affirmOperation, IntConsumer successOperation, IntConsumer failOperation) {
        operation(DEFAULT_TRY_FREQUENCY, DEFAULT_TRY_INTERVAL_TIME, operation, affirmOperation, successOperation, failOperation);
    }

    /**
     * @Author 袁毅雄
     * @Description 失败重试, 手动控制正确失败进行强制确认: 最多重试 tryFrequency 次，每次重试时间间隔 tryIntervalTime 毫秒
     * @Date 2019/4/16
     * @Param [tryFrequency, tryIntervalTime, operation, affirmOperation, successOperation, failOperation]
     **/
    public static void operation(Integer tryFrequency, Long tryIntervalTime, Operation operation, BooleanSupplier affirmOperation, IntConsumer successOperation, IntConsumer failOperation) {
        int n = 0;
        for (int i = 1; i <= tryFrequency; i++) {
            try {
                operation.operation();
                n++;
                //强制确认操作是否正确
                sleep(tryIntervalTime);
                if (!affirmOperation.getAsBoolean()) {
                    sleep(tryIntervalTime);
                    continue;
                }
                break;
            } catch (Exception e) {
                log.error(e.getMessage());
                sleep(tryIntervalTime);
                continue;
            }
        }
        if (n <= FailRetry.DEFAULT_TRY_FREQUENCY) {
            successOperation.accept(n);
        } else {
            failOperation.accept(n);
        }
    }

    /**
     * 线程睡眠
     *
     * @param tryIntervalTime
     */
    private static void sleep(Long tryIntervalTime) {
        try {
            Thread.sleep(tryIntervalTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
