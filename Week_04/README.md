# 作业1

思路：
1. 让获取结果的线程等待
2. 让获取结果的线程在一个无限循环里不断的获取结果，直到获取到结果。获取结果的逻辑可以根据状态、队列等

类名 | 说明
---|---
LockSum | 使用 `java.util.concurrent.locks.Lock`, 但是需要配合 condition
SynchronizedSum | 使用 synchronized 关键字, 但是需要配合 wait 和 notify
SemaphoreSum | 使用 Semaphore，跟限流有点类似
JoinSum | 使用 join, 让其他线程执行完
CuntDownLatchSum | 使用 CuntDownLatch 等待所有的计算线程都计算完
CyclicBarrierSum | 使用 CyclicBarrier
BlockQueueSum | 使用阻塞队列
FutureCallableSum | 使用 Future + Callable
FutureTaskSum | 使用 FutureTask
CompletableFutureSum | 使用 CompletableFuture, 其实 Guava、Spring、Netty 里都有类似的封装, 这里就不写了

# 作业2

