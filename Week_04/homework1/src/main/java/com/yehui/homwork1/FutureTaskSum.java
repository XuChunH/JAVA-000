package com.yehui.homwork1;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * @author yehui
 * @date 2020/11/11
 */
public class FutureTaskSum {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        long start = System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法

        final ExecutorService executorService = Executors.newFixedThreadPool(1);
        final FutureTask<Integer> futureTask = new FutureTask<Integer>(() -> {
            final FutureTaskSum futureCallableSum = new FutureTaskSum();
            return futureCallableSum.sum();
        });
        executorService.submit(futureTask);

        Integer result = futureTask.get();
        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + result);

        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

        // 然后退出main线程
    }

    public int sum() {

        return fibo(36);

    }

    private int fibo(int a) {

        if (a < 2) {
            return 1;
        }
        return fibo(a - 1) + fibo(a - 2);
    }

}
