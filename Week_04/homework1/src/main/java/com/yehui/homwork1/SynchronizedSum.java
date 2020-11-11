package com.yehui.homwork1;

import java.util.Objects;

/**
 * @author yehui
 * @date 2020/11/10
 */
public class SynchronizedSum {

    private Integer result;

    public static void main(String[] args) {

        long start = System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法

        final SynchronizedSum synchronizedSum = new SynchronizedSum();
        final Thread thread = new Thread(synchronizedSum::sum);
        thread.start();

        final Integer result = synchronizedSum.get();

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + result);

        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

        // 然后退出main线程
    }

    public synchronized void sum() {

        result = fibo(36);
        this.notifyAll();
    }

    private int fibo(int a) {

        if (a < 2) {
            return 1;
        }
        return fibo(a - 1) + fibo(a - 2);
    }

    public synchronized Integer get() {
        while (Objects.isNull(result)) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


}
