| 命令 | 作用 |
|---|---|
|jps|常看java进程相关信息|
|jstack|分析线程栈|
|jmap|查看jvm对象详情|
|jstat|gc相关指标的统计数据|

自己处理过的一个例子, 一个服务刚启动时候的吞吐很低，排除了gc、jit等原因之后, 用 `jstack` 分析了下线程栈，发现业务线程基本上都在等待数据库连接的建立, 排查代码发现, datasource 没有初始化

下面是用 `jstack -l` 查看本机 idea 的一个例子

```
2020-10-18 16:45:04
Full thread dump Java HotSpot(TM) 64-Bit Server VM (14.0.2+12-46 mixed mode, sharing):

Threads class SMR info:
_java_thread_list=0x00007fe7eb140ae0, length=12, elements={
0x00007fe7e9808800, 0x00007fe7e980b800, 0x00007fe7e681a800, 0x00007fe7e681d800,
0x00007fe7e681e000, 0x00007fe7e7833800, 0x00007fe7e580e000, 0x00007fe7e700e000,
0x00007fe7e6874800, 0x00007fe7e512b800, 0x00007fe7e7a72000, 0x00007fe7e7a73000
}

"Reference Handler" #2 daemon prio=10 os_prio=31 cpu=0.55ms elapsed=2096.08s tid=0x00007fe7e9808800 nid=0x3503 waiting on condition  [0x000070000bd13000]
   java.lang.Thread.State: RUNNABLE
	at java.lang.ref.Reference.waitForReferencePendingList(java.base@14.0.2/Native Method)
	at java.lang.ref.Reference.processPendingReferences(java.base@14.0.2/Reference.java:241)
	at java.lang.ref.Reference$ReferenceHandler.run(java.base@14.0.2/Reference.java:213)

   Locked ownable synchronizers:
	- None

"Finalizer" #3 daemon prio=8 os_prio=31 cpu=0.41ms elapsed=2096.08s tid=0x00007fe7e980b800 nid=0x3603 in Object.wait()  [0x000070000be16000]
   java.lang.Thread.State: WAITING (on object monitor)
	at java.lang.Object.wait(java.base@14.0.2/Native Method)
	- waiting on <0x00000007d4f67830> (a java.lang.ref.ReferenceQueue$Lock)
	at java.lang.ref.ReferenceQueue.remove(java.base@14.0.2/ReferenceQueue.java:155)
	- locked <0x00000007d4f67830> (a java.lang.ref.ReferenceQueue$Lock)
	at java.lang.ref.ReferenceQueue.remove(java.base@14.0.2/ReferenceQueue.java:176)
	at java.lang.ref.Finalizer$FinalizerThread.run(java.base@14.0.2/Finalizer.java:170)

   Locked ownable synchronizers:
	- None

"Signal Dispatcher" #4 daemon prio=9 os_prio=31 cpu=0.79ms elapsed=2096.08s tid=0x00007fe7e681a800 nid=0xa803 runnable  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

   Locked ownable synchronizers:
	- None

"Service Thread" #5 daemon prio=9 os_prio=31 cpu=0.06ms elapsed=2096.08s tid=0x00007fe7e681d800 nid=0xa703 runnable  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

   Locked ownable synchronizers:
	- None

"C2 CompilerThread0" #6 daemon prio=9 os_prio=31 cpu=491.83ms elapsed=2096.08s tid=0x00007fe7e681e000 nid=0xa403 waiting on condition  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE
   No compile task

   Locked ownable synchronizers:
	- None

"C1 CompilerThread0" #9 daemon prio=9 os_prio=31 cpu=509.43ms elapsed=2096.08s tid=0x00007fe7e7833800 nid=0x5803 waiting on condition  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE
   No compile task

   Locked ownable synchronizers:
	- None

"Sweeper thread" #10 daemon prio=9 os_prio=31 cpu=6.65ms elapsed=2096.08s tid=0x00007fe7e580e000 nid=0x9f03 runnable  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

   Locked ownable synchronizers:
	- None

"Notification Thread" #11 daemon prio=9 os_prio=31 cpu=0.07ms elapsed=2096.06s tid=0x00007fe7e700e000 nid=0x5a03 runnable  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

   Locked ownable synchronizers:
	- None

"Common-Cleaner" #12 daemon prio=8 os_prio=31 cpu=3.28ms elapsed=2096.06s tid=0x00007fe7e6874800 nid=0x9c03 in Object.wait()  [0x000070000c734000]
   java.lang.Thread.State: TIMED_WAITING (on object monitor)
	at java.lang.Object.wait(java.base@14.0.2/Native Method)
	- waiting on <0x00000007d4f682d8> (a java.lang.ref.ReferenceQueue$Lock)
	at java.lang.ref.ReferenceQueue.remove(java.base@14.0.2/ReferenceQueue.java:155)
	- locked <0x00000007d4f682d8> (a java.lang.ref.ReferenceQueue$Lock)
	at jdk.internal.ref.CleanerImpl.run(java.base@14.0.2/CleanerImpl.java:148)
	at java.lang.Thread.run(java.base@14.0.2/Thread.java:832)
	at jdk.internal.misc.InnocuousThread.run(java.base@14.0.2/InnocuousThread.java:134)

   Locked ownable synchronizers:
	- None

"JPS event loop" #15 prio=5 os_prio=31 cpu=51.52ms elapsed=2095.40s tid=0x00007fe7e512b800 nid=0x8f03 runnable  [0x000070000cf4c000]
   java.lang.Thread.State: RUNNABLE
	at sun.nio.ch.KQueue.poll(java.base@14.0.2/Native Method)
	at sun.nio.ch.KQueueSelectorImpl.doSelect(java.base@14.0.2/KQueueSelectorImpl.java:122)
	at sun.nio.ch.SelectorImpl.lockAndDoSelect(java.base@14.0.2/SelectorImpl.java:129)
	- locked <0x00000007e1a002e8> (a sun.nio.ch.Util$2)
	- locked <0x00000007e1a00288> (a sun.nio.ch.KQueueSelectorImpl)
	at sun.nio.ch.SelectorImpl.select(java.base@14.0.2/SelectorImpl.java:146)
	at io.netty.channel.nio.NioEventLoop.select(NioEventLoop.java:803)
	at io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:457)
	at io.netty.util.concurrent.SingleThreadEventExecutor$4.run(SingleThreadEventExecutor.java:989)
	at io.netty.util.internal.ThreadExecutorMap$2.run(ThreadExecutorMap.java:74)
	at java.lang.Thread.run(java.base@14.0.2/Thread.java:832)

   Locked ownable synchronizers:
	- None

"DestroyJavaVM" #18 prio=5 os_prio=31 cpu=1210.02ms elapsed=2094.67s tid=0x00007fe7e7a72000 nid=0x1903 waiting on condition  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

   Locked ownable synchronizers:
	- None

"Attach Listener" #19 daemon prio=9 os_prio=31 cpu=1.14ms elapsed=76.43s tid=0x00007fe7e7a73000 nid=0x9a0b waiting on condition  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

   Locked ownable synchronizers:
	- None

"VM Thread" os_prio=31 cpu=103.67ms elapsed=2096.09s tid=0x00007fe7e50ca800 nid=0x3303 runnable

"GC Thread#0" os_prio=31 cpu=7.87ms elapsed=2096.10s tid=0x00007fe7e781d000 nid=0x4d03 runnable

"GC Thread#1" os_prio=31 cpu=7.54ms elapsed=2095.71s tid=0x00007fe7e986c000 nid=0x9403 runnable

"GC Thread#2" os_prio=31 cpu=7.39ms elapsed=2095.71s tid=0x00007fe7e986c800 nid=0x5f03 runnable

"GC Thread#3" os_prio=31 cpu=7.61ms elapsed=2095.71s tid=0x00007fe7e986d800 nid=0x9303 runnable

"GC Thread#4" os_prio=31 cpu=7.55ms elapsed=2095.71s tid=0x00007fe7e986e000 nid=0x6203 runnable

"GC Thread#5" os_prio=31 cpu=7.49ms elapsed=2095.71s tid=0x00007fe7e986f000 nid=0x9103 runnable

"G1 Main Marker" os_prio=31 cpu=0.23ms elapsed=2096.10s tid=0x00007fe7e7823800 nid=0x4b03 runnable

"G1 Conc#0" os_prio=31 cpu=3.56ms elapsed=2096.10s tid=0x00007fe7e5009000 nid=0x4803 runnable

"G1 Conc#1" os_prio=31 cpu=3.34ms elapsed=2094.79s tid=0x00007fe7e69e0800 nid=0x6503 runnable

"G1 Conc#2" os_prio=31 cpu=3.24ms elapsed=2094.79s tid=0x00007fe7e5137800 nid=0x8b03 runnable

"G1 Refine#0" os_prio=31 cpu=0.04ms elapsed=2096.10s tid=0x00007fe7e50ba000 nid=0x3103 runnable

"G1 Young RemSet Sampling" os_prio=31 cpu=299.50ms elapsed=2096.10s tid=0x00007fe7e7824800 nid=0x4603 runnable
"VM Periodic Task Thread" os_prio=31 cpu=1273.93ms elapsed=2096.06s tid=0x00007fe7e6873800 nid=0x5c03 waiting on condition

JNI global refs: 23, weak refs: 45
```