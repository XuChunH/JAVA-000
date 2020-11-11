package com.yehui.homwork1;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * @author yehui
 * @date 2020/11/10
 */
public class CuntDownLatchSum {

    private CountDownLatch countDownLatch;

    public void setCountDownLatch(CountDownLatch countDownLatch) {

        this.countDownLatch = countDownLatch;
    }

    private Integer result;

    public static void main(String[] args) throws InterruptedException {

        long start = System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法

        final CuntDownLatchSum cuntDownLatchSum = new CuntDownLatchSum();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        cuntDownLatchSum.setCountDownLatch(countDownLatch);

        final Thread thread = new Thread(() -> {
            try {
                cuntDownLatchSum.sum();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();

        Integer result = cuntDownLatchSum.get();
        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + result);

        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

        // 然后退出main线程
    }

    public void sum() throws BrokenBarrierException, InterruptedException {

        result = fibo(36);
        countDownLatch.countDown();
    }

    private int fibo(int a) {

        if (a < 2) {
            return 1;
        }
        return fibo(a - 1) + fibo(a - 2);
    }

    public Integer get() throws InterruptedException {
        countDownLatch.await();
        return result;
    }


}
