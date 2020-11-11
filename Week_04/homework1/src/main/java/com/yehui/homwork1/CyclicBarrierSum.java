package com.yehui.homwork1;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

/**
 * @author yehui
 * @date 2020/11/10
 */
public class CyclicBarrierSum {

    private CyclicBarrier cyclicBarrier;

    public CyclicBarrier getCyclicBarrier() {

        return cyclicBarrier;
    }

    public void setCyclicBarrier(CyclicBarrier cyclicBarrier) {

        this.cyclicBarrier = cyclicBarrier;
    }

    private Integer result;

    public static void main(String[] args) throws InterruptedException {

        long start = System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法

        final CyclicBarrierSum cyclicBarrierSum = new CyclicBarrierSum();
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(1, () -> {
            final Integer result;
            try {
                result = cyclicBarrierSum.get();
                // 确保  拿到result 并输出
                System.out.println("异步计算结果为：" + result);

                System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        cyclicBarrierSum.setCyclicBarrier(cyclicBarrier);

        final Thread thread = new Thread(() -> {
            try {
                cyclicBarrierSum.sum();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();

        // 然后退出main线程
    }

    public void sum() throws BrokenBarrierException, InterruptedException {

        result = fibo(36);
        cyclicBarrier.await();
    }

    private int fibo(int a) {

        if (a < 2) {
            return 1;
        }
        return fibo(a - 1) + fibo(a - 2);
    }

    public Integer get() throws InterruptedException {

        return result;
    }


}
