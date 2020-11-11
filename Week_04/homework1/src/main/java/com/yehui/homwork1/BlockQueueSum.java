package com.yehui.homwork1;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;

/**
 * @author yehui
 * @date 2020/11/10
 */
public class BlockQueueSum {

    private ArrayBlockingQueue<Integer> blockingQueue;

    public BlockQueueSum(ArrayBlockingQueue<Integer> blockingQueue) {

        this.blockingQueue = blockingQueue;
    }

    public static void main(String[] args) throws InterruptedException {

        long start = System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法

        final ArrayBlockingQueue<Integer> arrayBlockingQueue = new ArrayBlockingQueue<>(1);
        final BlockQueueSum blockQueueSum = new BlockQueueSum(arrayBlockingQueue);

        final Thread thread = new Thread(() -> {
            try {
                blockQueueSum.sum();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();

        Integer result = blockQueueSum.get();
        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + result);

        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

        // 然后退出main线程
    }

    public void sum() throws InterruptedException {

        blockingQueue.put(fibo(36));
    }

    private int fibo(int a) {

        if (a < 2) {
            return 1;
        }
        return fibo(a - 1) + fibo(a - 2);
    }

    public Integer get() throws InterruptedException {
        return blockingQueue.take();
    }


}
