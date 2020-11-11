package com.yehui.homwork1;

import java.util.Objects;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yehui
 * @date 2020/11/10
 */
public class LockSum {

    private Integer result;

    private final Lock lock = new ReentrantLock();

    final Condition condition = lock.newCondition();

    public static void main(String[] args) {

        long start = System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法

        final LockSum lockSum = new LockSum();
        final Thread thread = new Thread(lockSum::sum);
        thread.start();

        final Integer result = lockSum.get();

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + result);

        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

        // 然后退出main线程
    }

    public void sum() {

        lock.lock();
        try {
            result = fibo(36);
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    private int fibo(int a) {

        if (a < 2) {
            return 1;
        }
        return fibo(a - 1) + fibo(a - 2);
    }

    public Integer get() {

        lock.lock();
        try {
            while (Objects.isNull(result)) {
                condition.await();
            }
            return result;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } finally {
            lock.unlock();
        }
    }

}
