package com.yehui.homwork1;

import java.util.Objects;
import java.util.concurrent.Semaphore;

/**
 * @author yehui
 * @date 2020/11/10
 */
public class SemaphoreSum {

    private Semaphore semaphore = new Semaphore(-1);

    private Integer result;

    public static void main(String[] args) throws InterruptedException {

        long start = System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法

        final SemaphoreSum synchronizedSum = new SemaphoreSum();
        final Thread thread = new Thread(synchronizedSum::sum);
        thread.start();

        final Integer result = synchronizedSum.get();

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + result);

        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

        // 然后退出main线程
    }

    public void sum() {

        result = fibo(36);
        semaphore.release(2);
    }

    private int fibo(int a) {

        if (a < 2) {
            return 1;
        }
        return fibo(a - 1) + fibo(a - 2);
    }

    public Integer get() throws InterruptedException {

        semaphore.acquire();
        Integer temp = result;
        semaphore.release();
        return temp;
    }


}
