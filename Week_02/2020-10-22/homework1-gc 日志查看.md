# 分析
1. 在 512m 下，堆是比较小的, 很清楚的看到了各个gc的过程。实际里需要分析gc的频率、gc时间等来判断系统的特点，设置切合的参数
2. 在几种情况下, cms和g1 相对来说的吞吐是比较好的，堆越大，g1 的效果越好，跟各个垃圾回收器适用的场景比较切合的
3. 在 512M 下，并行 gc 吞吐是最差的，而且发生了多次 fullGC， 具体原因还没有探究，这个记个 todo, 后续不加班了，再看看
4. 本次作业的不足, 没有进行详细的统计分析，上面的分析是主观的，同样的，这个记个 todo, 后续不加班了，在严谨的分析下。人生苦短，学习不能停！！

# gc 日志记录

# 512M 下各个垃圾回收器的gc日志

## 串行

`java -XX:+UseSerialGC -Xms512m -Xmx512m -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis`

日志: 

```
2020-10-27T01:08:26.428-0800: [GC (Allocation Failure) 2020-10-27T01:08:26.428-0800: [DefNew: 139776K->17472K(157248K), 0.0243569 secs] 139776K->46404K(506816K), 0.0243976 secs] [Times: user=0.02 sys=0.01, real=0.03 secs] 
2020-10-27T01:08:26.479-0800: [GC (Allocation Failure) 2020-10-27T01:08:26.479-0800: [DefNew: 157248K->17471K(157248K), 0.0309485 secs] 186180K->88100K(506816K), 0.0309803 secs] [Times: user=0.02 sys=0.02, real=0.04 secs] 
2020-10-27T01:08:26.532-0800: [GC (Allocation Failure) 2020-10-27T01:08:26.532-0800: [DefNew: 157247K->17470K(157248K), 0.0264294 secs] 227876K->133568K(506816K), 0.0264615 secs] [Times: user=0.02 sys=0.01, real=0.02 secs] 
2020-10-27T01:08:26.581-0800: [GC (Allocation Failure) 2020-10-27T01:08:26.581-0800: [DefNew: 157246K->17471K(157248K), 0.0256385 secs] 273344K->178087K(506816K), 0.0256725 secs] [Times: user=0.01 sys=0.01, real=0.02 secs] 
2020-10-27T01:08:26.632-0800: [GC (Allocation Failure) 2020-10-27T01:08:26.632-0800: [DefNew: 156704K->17470K(157248K), 0.0223958 secs] 317319K->216098K(506816K), 0.0224295 secs] [Times: user=0.01 sys=0.01, real=0.02 secs] 
2020-10-27T01:08:26.673-0800: [GC (Allocation Failure) 2020-10-27T01:08:26.673-0800: [DefNew: 157246K->17465K(157248K), 0.0256448 secs] 355874K->259671K(506816K), 0.0256770 secs] [Times: user=0.02 sys=0.01, real=0.02 secs] 
2020-10-27T01:08:26.721-0800: [GC (Allocation Failure) 2020-10-27T01:08:26.721-0800: [DefNew: 157241K->17470K(157248K), 0.0272017 secs] 399447K->306231K(506816K), 0.0272354 secs] [Times: user=0.02 sys=0.01, real=0.02 secs] 
2020-10-27T01:08:26.767-0800: [GC (Allocation Failure) 2020-10-27T01:08:26.767-0800: [DefNew: 157246K->17470K(157248K), 0.0269001 secs] 446007K->350755K(506816K), 0.0269310 secs] [Times: user=0.01 sys=0.01, real=0.03 secs] 
2020-10-27T01:08:26.815-0800: [GC (Allocation Failure) 2020-10-27T01:08:26.815-0800: [DefNew: 157246K->157246K(157248K), 0.0000175 secs]2020-10-27T01:08:26.815-0800: [Tenured: 333285K->272252K(349568K), 0.0370802 secs] 490531K->272252K(506816K), [Metaspace: 2706K->2706K(1056768K)], 0.0371457 secs] [Times: user=0.04 sys=0.00, real=0.04 secs] 
2020-10-27T01:08:26.871-0800: [GC (Allocation Failure) 2020-10-27T01:08:26.871-0800: [DefNew: 139711K->17470K(157248K), 0.0064157 secs] 411963K->318503K(506816K), 0.0064508 secs] [Times: user=0.01 sys=0.00, real=0.00 secs] 
2020-10-27T01:08:26.896-0800: [GC (Allocation Failure) 2020-10-27T01:08:26.896-0800: [DefNew: 157246K->157246K(157248K), 0.0000147 secs]2020-10-27T01:08:26.896-0800: [Tenured: 301032K->295338K(349568K), 0.0368319 secs] 458279K->295338K(506816K), [Metaspace: 2706K->2706K(1056768K)], 0.0368899 secs] [Times: user=0.03 sys=0.00, real=0.04 secs] 
2020-10-27T01:08:26.952-0800: [GC (Allocation Failure) 2020-10-27T01:08:26.952-0800: [DefNew: 139776K->17471K(157248K), 0.0061350 secs] 435114K->340227K(506816K), 0.0061772 secs] [Times: user=0.01 sys=0.00, real=0.00 secs] 
2020-10-27T01:08:26.977-0800: [GC (Allocation Failure) 2020-10-27T01:08:26.977-0800: [DefNew: 157247K->157247K(157248K), 0.0000165 secs]2020-10-27T01:08:26.977-0800: [Tenured: 322755K->317227K(349568K), 0.0416080 secs] 480003K->317227K(506816K), [Metaspace: 2706K->2706K(1056768K)], 0.0416738 secs] [Times: user=0.04 sys=0.00, real=0.04 secs] 
2020-10-27T01:08:27.041-0800: [GC (Allocation Failure) 2020-10-27T01:08:27.041-0800: [DefNew: 139776K->139776K(157248K), 0.0000165 secs]2020-10-27T01:08:27.041-0800: [Tenured: 317227K->310306K(349568K), 0.0426465 secs] 457003K->310306K(506816K), [Metaspace: 2706K->2706K(1056768K)], 0.0427136 secs] [Times: user=0.04 sys=0.00, real=0.04 secs] 
2020-10-27T01:08:27.110-0800: [GC (Allocation Failure) 2020-10-27T01:08:27.110-0800: [DefNew: 139622K->17470K(157248K), 0.0130270 secs] 449929K->362777K(506816K), 0.0130622 secs] [Times: user=0.01 sys=0.00, real=0.01 secs] 
2020-10-27T01:08:27.143-0800: [GC (Allocation Failure) 2020-10-27T01:08:27.143-0800: [DefNew: 157246K->157246K(157248K), 0.0000174 secs]2020-10-27T01:08:27.143-0800: [Tenured: 345306K->346401K(349568K), 0.0414062 secs] 502553K->346401K(506816K), [Metaspace: 2706K->2706K(1056768K)], 0.0414729 secs] [Times: user=0.04 sys=0.00, real=0.04 secs] 
2020-10-27T01:08:27.204-0800: [GC (Allocation Failure) 2020-10-27T01:08:27.204-0800: [DefNew: 139776K->139776K(157248K), 0.0000162 secs]2020-10-27T01:08:27.205-0800: [Tenured: 346401K->349490K(349568K), 0.0444831 secs] 486177K->354689K(506816K), [Metaspace: 2706K->2706K(1056768K)], 0.0445455 secs] [Times: user=0.04 sys=0.00, real=0.04 secs] 
2020-10-27T01:08:27.276-0800: [Full GC (Allocation Failure) 2020-10-27T01:08:27.276-0800: [Tenured: 349490K->349443K(349568K), 0.0602165 secs] 506611K->362534K(506816K), [Metaspace: 2706K->2706K(1056768K)], 0.0602817 secs] [Times: user=0.06 sys=0.00, real=0.06 secs] 
执行结束!共生成对象次数:9983
Heap
 def new generation   total 157248K, used 146105K [0x00000007a0000000, 0x00000007aaaa0000, 0x00000007aaaa0000)
  eden space 139776K, 100% used [0x00000007a0000000, 0x00000007a8880000, 0x00000007a8880000)
  from space 17472K,  36% used [0x00000007a9990000, 0x00000007a9fbe5d8, 0x00000007aaaa0000)
  to   space 17472K,   0% used [0x00000007a8880000, 0x00000007a8880000, 0x00000007a9990000)
 tenured generation   total 349568K, used 349443K [0x00000007aaaa0000, 0x00000007c0000000, 0x00000007c0000000)
   the space 349568K,  99% used [0x00000007aaaa0000, 0x00000007bffe0d30, 0x00000007bffe0e00, 0x00000007c0000000)
 Metaspace       used 2712K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 295K, capacity 386K, committed 512K, reserved 1048576K

```

## 并行

`java -Xms512m -Xmx512m -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis`

日志:

```
2020-10-27T01:10:01.303-0800: [GC (Allocation Failure) [PSYoungGen: 131437K->21477K(153088K)] 131437K->38464K(502784K), 0.0148446 secs] [Times: user=0.02 sys=0.10, real=0.01 secs] 
2020-10-27T01:10:01.338-0800: [GC (Allocation Failure) [PSYoungGen: 153061K->21503K(153088K)] 170048K->76837K(502784K), 0.0223210 secs] [Times: user=0.03 sys=0.14, real=0.03 secs] 
2020-10-27T01:10:01.376-0800: [GC (Allocation Failure) [PSYoungGen: 153087K->21496K(153088K)] 208421K->122933K(502784K), 0.0205137 secs] [Times: user=0.04 sys=0.11, real=0.02 secs] 
2020-10-27T01:10:01.415-0800: [GC (Allocation Failure) [PSYoungGen: 153080K->21503K(153088K)] 254517K->157939K(502784K), 0.0175644 secs] [Times: user=0.04 sys=0.10, real=0.02 secs] 
2020-10-27T01:10:01.457-0800: [GC (Allocation Failure) [PSYoungGen: 152987K->21488K(153088K)] 289423K->195581K(502784K), 0.0186281 secs] [Times: user=0.04 sys=0.10, real=0.02 secs] 
2020-10-27T01:10:01.494-0800: [GC (Allocation Failure) [PSYoungGen: 153072K->21501K(80384K)] 327165K->237382K(430080K), 0.0204036 secs] [Times: user=0.05 sys=0.12, real=0.02 secs] 
2020-10-27T01:10:01.524-0800: [GC (Allocation Failure) [PSYoungGen: 79678K->36227K(116736K)] 295559K->256779K(466432K), 0.0050394 secs] [Times: user=0.03 sys=0.01, real=0.00 secs] 
2020-10-27T01:10:01.537-0800: [GC (Allocation Failure) [PSYoungGen: 95105K->52161K(116736K)] 315656K->278150K(466432K), 0.0069694 secs] [Times: user=0.06 sys=0.01, real=0.01 secs] 
2020-10-27T01:10:01.556-0800: [GC (Allocation Failure) [PSYoungGen: 110811K->57838K(116736K)] 336800K->296125K(466432K), 0.0115878 secs] [Times: user=0.06 sys=0.03, real=0.01 secs] 
2020-10-27T01:10:01.577-0800: [GC (Allocation Failure) [PSYoungGen: 116359K->40365K(116736K)] 354645K->314371K(466432K), 0.0171680 secs] [Times: user=0.03 sys=0.11, real=0.02 secs] 
2020-10-27T01:10:01.603-0800: [GC (Allocation Failure) [PSYoungGen: 99245K->15848K(116736K)] 373251K->326709K(466432K), 0.0181685 secs] [Times: user=0.03 sys=0.10, real=0.02 secs] 
2020-10-27T01:10:01.621-0800: [Full GC (Ergonomics) [PSYoungGen: 15848K->0K(116736K)] [ParOldGen: 310860K->232067K(349696K)] 326709K->232067K(466432K), [Metaspace: 2706K->2706K(1056768K)], 0.0297770 secs] [Times: user=0.24 sys=0.01, real=0.03 secs] 
2020-10-27T01:10:01.660-0800: [GC (Allocation Failure) [PSYoungGen: 58880K->21836K(116736K)] 290947K->253904K(466432K), 0.0030168 secs] [Times: user=0.03 sys=0.00, real=0.00 secs] 
2020-10-27T01:10:01.673-0800: [GC (Allocation Failure) [PSYoungGen: 80716K->18567K(116736K)] 312784K->271868K(466432K), 0.0045410 secs] [Times: user=0.04 sys=0.00, real=0.00 secs] 
2020-10-27T01:10:01.687-0800: [GC (Allocation Failure) [PSYoungGen: 77447K->18865K(116736K)] 330748K->289736K(466432K), 0.0044116 secs] [Times: user=0.03 sys=0.00, real=0.01 secs] 
2020-10-27T01:10:01.701-0800: [GC (Allocation Failure) [PSYoungGen: 77612K->22718K(116736K)] 348483K->311794K(466432K), 0.0046643 secs] [Times: user=0.04 sys=0.00, real=0.00 secs] 
2020-10-27T01:10:01.716-0800: [GC (Allocation Failure) [PSYoungGen: 81598K->24787K(116736K)] 370674K->335945K(466432K), 0.0054098 secs] [Times: user=0.04 sys=0.00, real=0.01 secs] 
2020-10-27T01:10:01.722-0800: [Full GC (Ergonomics) [PSYoungGen: 24787K->0K(116736K)] [ParOldGen: 311157K->275019K(349696K)] 335945K->275019K(466432K), [Metaspace: 2706K->2706K(1056768K)], 0.0312204 secs] [Times: user=0.28 sys=0.00, real=0.03 secs] 
2020-10-27T01:10:01.765-0800: [GC (Allocation Failure) [PSYoungGen: 58863K->17463K(116736K)] 333883K->292483K(466432K), 0.0025129 secs] [Times: user=0.02 sys=0.00, real=0.00 secs] 
2020-10-27T01:10:01.777-0800: [GC (Allocation Failure) [PSYoungGen: 76123K->18591K(116736K)] 351143K->308797K(466432K), 0.0040028 secs] [Times: user=0.04 sys=0.00, real=0.01 secs] 
2020-10-27T01:10:01.792-0800: [GC (Allocation Failure) [PSYoungGen: 77011K->22686K(116736K)] 367217K->330665K(466432K), 0.0045633 secs] [Times: user=0.04 sys=0.00, real=0.00 secs] 
2020-10-27T01:10:01.796-0800: [Full GC (Ergonomics) [PSYoungGen: 22686K->0K(116736K)] [ParOldGen: 307979K->290129K(349696K)] 330665K->290129K(466432K), [Metaspace: 2706K->2706K(1056768K)], 0.0326236 secs] [Times: user=0.28 sys=0.01, real=0.03 secs] 
2020-10-27T01:10:01.841-0800: [GC (Allocation Failure) [PSYoungGen: 58880K->23531K(116736K)] 349009K->313660K(466432K), 0.0030796 secs] [Times: user=0.02 sys=0.00, real=0.00 secs] 
2020-10-27T01:10:01.856-0800: [GC (Allocation Failure) [PSYoungGen: 82411K->23493K(116736K)] 372540K->336182K(466432K), 0.0060975 secs] [Times: user=0.05 sys=0.00, real=0.01 secs] 
2020-10-27T01:10:01.862-0800: [Full GC (Ergonomics) [PSYoungGen: 23493K->0K(116736K)] [ParOldGen: 312689K->300137K(349696K)] 336182K->300137K(466432K), [Metaspace: 2706K->2706K(1056768K)], 0.0356529 secs] [Times: user=0.31 sys=0.01, real=0.03 secs] 
2020-10-27T01:10:01.912-0800: [GC (Allocation Failure) [PSYoungGen: 58811K->19451K(116736K)] 358949K->319589K(466432K), 0.0026921 secs] [Times: user=0.03 sys=0.00, real=0.00 secs] 
2020-10-27T01:10:01.927-0800: [GC (Allocation Failure) [PSYoungGen: 78331K->18178K(116736K)] 378469K->337514K(466432K), 0.0070411 secs] [Times: user=0.03 sys=0.02, real=0.01 secs] 
2020-10-27T01:10:01.934-0800: [Full GC (Ergonomics) [PSYoungGen: 18178K->0K(116736K)] [ParOldGen: 319335K->312866K(349696K)] 337514K->312866K(466432K), [Metaspace: 2706K->2706K(1056768K)], 0.0371795 secs] [Times: user=0.30 sys=0.00, real=0.04 secs] 
2020-10-27T01:10:01.982-0800: [GC (Allocation Failure) [PSYoungGen: 58362K->22855K(116736K)] 371228K->335721K(466432K), 0.0028856 secs] [Times: user=0.03 sys=0.00, real=0.00 secs] 
2020-10-27T01:10:01.995-0800: [GC (Allocation Failure) [PSYoungGen: 81210K->20492K(116736K)] 394076K->355578K(466432K), 0.0117009 secs] [Times: user=0.03 sys=0.05, real=0.01 secs] 
2020-10-27T01:10:02.007-0800: [Full GC (Ergonomics) [PSYoungGen: 20492K->0K(116736K)] [ParOldGen: 335085K->323787K(349696K)] 355578K->323787K(466432K), [Metaspace: 2706K->2706K(1056768K)], 0.0357528 secs] [Times: user=0.31 sys=0.01, real=0.04 secs] 
2020-10-27T01:10:02.055-0800: [Full GC (Ergonomics) [PSYoungGen: 58880K->0K(116736K)] [ParOldGen: 323787K->325405K(349696K)] 382667K->325405K(466432K), [Metaspace: 2706K->2706K(1056768K)], 0.0365809 secs] [Times: user=0.32 sys=0.00, real=0.04 secs] 
2020-10-27T01:10:02.103-0800: [Full GC (Ergonomics) [PSYoungGen: 58392K->0K(116736K)] [ParOldGen: 325405K->322496K(349696K)] 383798K->322496K(466432K), [Metaspace: 2706K->2706K(1056768K)], 0.0347022 secs] [Times: user=0.30 sys=0.00, real=0.03 secs] 
2020-10-27T01:10:02.153-0800: [Full GC (Ergonomics) [PSYoungGen: 58680K->0K(116736K)] [ParOldGen: 322496K->322742K(349696K)] 381177K->322742K(466432K), [Metaspace: 2706K->2706K(1056768K)], 0.0352064 secs] [Times: user=0.31 sys=0.00, real=0.03 secs] 
2020-10-27T01:10:02.205-0800: [Full GC (Ergonomics) [PSYoungGen: 58880K->0K(116736K)] [ParOldGen: 322742K->324292K(349696K)] 381622K->324292K(466432K), [Metaspace: 2706K->2706K(1056768K)], 0.0370496 secs] [Times: user=0.32 sys=0.00, real=0.04 secs] 
执行结束!共生成对象次数:8120
Heap
 PSYoungGen      total 116736K, used 2564K [0x00000007b5580000, 0x00000007c0000000, 0x00000007c0000000)
  eden space 58880K, 4% used [0x00000007b5580000,0x00000007b5801158,0x00000007b8f00000)
  from space 57856K, 0% used [0x00000007b8f00000,0x00000007b8f00000,0x00000007bc780000)
  to   space 57856K, 0% used [0x00000007bc780000,0x00000007bc780000,0x00000007c0000000)
 ParOldGen       total 349696K, used 324292K [0x00000007a0000000, 0x00000007b5580000, 0x00000007b5580000)
  object space 349696K, 92% used [0x00000007a0000000,0x00000007b3cb13a8,0x00000007b5580000)
 Metaspace       used 2712K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 295K, capacity 386K, committed 512K, reserved 1048576K
```

## cms

`java -XX:+UseConcMarkSweepGC -Xms512m -Xmx512m -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis`

日志：

```
2020-10-27T01:12:13.736-0800: [GC (Allocation Failure) 2020-10-27T01:12:13.736-0800: [ParNew: 139745K->17470K(157248K), 0.0215509 secs] 139745K->54168K(506816K), 0.0215917 secs] [Times: user=0.04 sys=0.13, real=0.02 secs] 
2020-10-27T01:12:13.781-0800: [GC (Allocation Failure) 2020-10-27T01:12:13.781-0800: [ParNew: 157246K->17472K(157248K), 0.0246662 secs] 193944K->103881K(506816K), 0.0247341 secs] [Times: user=0.04 sys=0.14, real=0.02 secs] 
2020-10-27T01:12:13.827-0800: [GC (Allocation Failure) 2020-10-27T01:12:13.827-0800: [ParNew: 157248K->17470K(157248K), 0.0278599 secs] 243657K->144989K(506816K), 0.0279085 secs] [Times: user=0.19 sys=0.02, real=0.03 secs] 
2020-10-27T01:12:13.874-0800: [GC (Allocation Failure) 2020-10-27T01:12:13.874-0800: [ParNew: 157246K->17470K(157248K), 0.0272278 secs] 284765K->191456K(506816K), 0.0272643 secs] [Times: user=0.25 sys=0.01, real=0.03 secs] 
2020-10-27T01:12:13.918-0800: [GC (Allocation Failure) 2020-10-27T01:12:13.918-0800: [ParNew: 157246K->17471K(157248K), 0.0313341 secs] 331232K->240997K(506816K), 0.0313694 secs] [Times: user=0.24 sys=0.02, real=0.04 secs] 
2020-10-27T01:12:13.950-0800: [GC (CMS Initial Mark) [1 CMS-initial-mark: 223526K(349568K)] 241285K(506816K), 0.0001695 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T01:12:13.950-0800: [CMS-concurrent-mark-start]
2020-10-27T01:12:13.952-0800: [CMS-concurrent-mark: 0.002/0.002 secs] [Times: user=0.01 sys=0.00, real=0.00 secs] 
2020-10-27T01:12:13.952-0800: [CMS-concurrent-preclean-start]
2020-10-27T01:12:13.952-0800: [CMS-concurrent-preclean: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T01:12:13.952-0800: [CMS-concurrent-abortable-preclean-start]
2020-10-27T01:12:13.971-0800: [GC (Allocation Failure) 2020-10-27T01:12:13.971-0800: [ParNew: 157088K->17471K(157248K), 0.0303412 secs] 380614K->289541K(506816K), 0.0303758 secs] [Times: user=0.24 sys=0.02, real=0.03 secs] 
2020-10-27T01:12:14.024-0800: [GC (Allocation Failure) 2020-10-27T01:12:14.024-0800: [ParNew: 157247K->17471K(157248K), 0.0293876 secs] 429317K->331014K(506816K), 0.0294245 secs] [Times: user=0.24 sys=0.02, real=0.03 secs] 
2020-10-27T01:12:14.075-0800: [GC (Allocation Failure) 2020-10-27T01:12:14.075-0800: [ParNew: 157247K->157247K(157248K), 0.0000200 secs]2020-10-27T01:12:14.075-0800: [CMS2020-10-27T01:12:14.075-0800: [CMS-concurrent-abortable-preclean: 0.003/0.122 secs] [Times: user=0.55 sys=0.04, real=0.12 secs] 
 (concurrent mode failure): 313542K->257710K(349568K), 0.0496058 secs] 470790K->257710K(506816K), [Metaspace: 2706K->2706K(1056768K)], 0.0496729 secs] [Times: user=0.05 sys=0.00, real=0.05 secs] 
2020-10-27T01:12:14.143-0800: [GC (Allocation Failure) 2020-10-27T01:12:14.143-0800: [ParNew: 139086K->17470K(157248K), 0.0061891 secs] 396796K->301593K(506816K), 0.0062263 secs] [Times: user=0.06 sys=0.00, real=0.00 secs] 
2020-10-27T01:12:14.149-0800: [GC (CMS Initial Mark) [1 CMS-initial-mark: 284123K(349568K)] 302300K(506816K), 0.0001949 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
2020-10-27T01:12:14.150-0800: [CMS-concurrent-mark-start]
2020-10-27T01:12:14.150-0800: [CMS-concurrent-mark: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T01:12:14.150-0800: [CMS-concurrent-preclean-start]
2020-10-27T01:12:14.151-0800: [CMS-concurrent-preclean: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T01:12:14.151-0800: [CMS-concurrent-abortable-preclean-start]
2020-10-27T01:12:14.172-0800: [GC (Allocation Failure) 2020-10-27T01:12:14.172-0800: [ParNew: 157246K->17470K(157248K), 0.0138942 secs] 441369K->344724K(506816K), 0.0139313 secs] [Times: user=0.13 sys=0.01, real=0.01 secs] 
2020-10-27T01:12:14.186-0800: [CMS-concurrent-abortable-preclean: 0.001/0.035 secs] [Times: user=0.15 sys=0.01, real=0.03 secs] 
2020-10-27T01:12:14.187-0800: [GC (CMS Final Remark) [YG occupancy: 23515 K (157248 K)]2020-10-27T01:12:14.187-0800: [Rescan (parallel) , 0.0002865 secs]2020-10-27T01:12:14.187-0800: [weak refs processing, 0.0000132 secs]2020-10-27T01:12:14.187-0800: [class unloading, 0.0001882 secs]2020-10-27T01:12:14.187-0800: [scrub symbol table, 0.0002688 secs]2020-10-27T01:12:14.187-0800: [scrub string table, 0.0001701 secs][1 CMS-remark: 327253K(349568K)] 350768K(506816K), 0.0009737 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T01:12:14.188-0800: [CMS-concurrent-sweep-start]
2020-10-27T01:12:14.188-0800: [CMS-concurrent-sweep: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T01:12:14.188-0800: [CMS-concurrent-reset-start]
2020-10-27T01:12:14.188-0800: [CMS-concurrent-reset: 0.000/0.000 secs] [Times: user=0.01 sys=0.00, real=0.00 secs] 
2020-10-27T01:12:14.205-0800: [GC (Allocation Failure) 2020-10-27T01:12:14.205-0800: [ParNew: 157099K->157099K(157248K), 0.0000182 secs]2020-10-27T01:12:14.205-0800: [CMS: 300008K->295269K(349568K), 0.0423587 secs] 457107K->295269K(506816K), [Metaspace: 2706K->2706K(1056768K)], 0.0424256 secs] [Times: user=0.04 sys=0.00, real=0.04 secs] 
2020-10-27T01:12:14.247-0800: [GC (CMS Initial Mark) [1 CMS-initial-mark: 295269K(349568K)] 295557K(506816K), 0.0002166 secs] [Times: user=0.01 sys=0.00, real=0.00 secs] 
2020-10-27T01:12:14.248-0800: [CMS-concurrent-mark-start]
2020-10-27T01:12:14.248-0800: [CMS-concurrent-mark: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T01:12:14.248-0800: [CMS-concurrent-preclean-start]
2020-10-27T01:12:14.249-0800: [CMS-concurrent-preclean: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T01:12:14.249-0800: [CMS-concurrent-abortable-preclean-start]
2020-10-27T01:12:14.269-0800: [GC (Allocation Failure) 2020-10-27T01:12:14.269-0800: [ParNew: 139776K->17471K(157248K), 0.0067006 secs] 435045K->341986K(506816K), 0.0067383 secs] [Times: user=0.06 sys=0.00, real=0.01 secs] 
2020-10-27T01:12:14.297-0800: [GC (Allocation Failure) 2020-10-27T01:12:14.297-0800: [ParNew: 157247K->157247K(157248K), 0.0000196 secs]2020-10-27T01:12:14.297-0800: [CMS2020-10-27T01:12:14.297-0800: [CMS-concurrent-abortable-preclean: 0.002/0.048 secs] [Times: user=0.10 sys=0.00, real=0.05 secs] 
 (concurrent mode failure): 324515K->305704K(349568K), 0.0449597 secs] 481762K->305704K(506816K), [Metaspace: 2706K->2706K(1056768K)], 0.0450298 secs] [Times: user=0.05 sys=0.00, real=0.05 secs] 
2020-10-27T01:12:14.364-0800: [GC (Allocation Failure) 2020-10-27T01:12:14.364-0800: [ParNew: 139776K->17471K(157248K), 0.0083986 secs] 445480K->348288K(506816K), 0.0084365 secs] [Times: user=0.06 sys=0.00, real=0.01 secs] 
2020-10-27T01:12:14.372-0800: [GC (CMS Initial Mark) [1 CMS-initial-mark: 330816K(349568K)] 348576K(506816K), 0.0002214 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T01:12:14.372-0800: [CMS-concurrent-mark-start]
2020-10-27T01:12:14.373-0800: [CMS-concurrent-mark: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T01:12:14.373-0800: [CMS-concurrent-preclean-start]
2020-10-27T01:12:14.374-0800: [CMS-concurrent-preclean: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T01:12:14.374-0800: [CMS-concurrent-abortable-preclean-start]
2020-10-27T01:12:14.374-0800: [CMS-concurrent-abortable-preclean: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T01:12:14.374-0800: [GC (CMS Final Remark) [YG occupancy: 30676 K (157248 K)]2020-10-27T01:12:14.374-0800: [Rescan (parallel) , 0.0003392 secs]2020-10-27T01:12:14.374-0800: [weak refs processing, 0.0000133 secs]2020-10-27T01:12:14.374-0800: [class unloading, 0.0002205 secs]2020-10-27T01:12:14.375-0800: [scrub symbol table, 0.0003152 secs]2020-10-27T01:12:14.375-0800: [scrub string table, 0.0001876 secs][1 CMS-remark: 330816K(349568K)] 361493K(506816K), 0.0011255 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T01:12:14.375-0800: [CMS-concurrent-sweep-start]
2020-10-27T01:12:14.376-0800: [CMS-concurrent-sweep: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T01:12:14.376-0800: [CMS-concurrent-reset-start]
2020-10-27T01:12:14.377-0800: [CMS-concurrent-reset: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T01:12:14.397-0800: [GC (Allocation Failure) 2020-10-27T01:12:14.397-0800: [ParNew: 157247K->17471K(157248K), 0.0093072 secs] 448493K->349637K(506816K), 0.0093433 secs] [Times: user=0.08 sys=0.01, real=0.01 secs] 
2020-10-27T01:12:14.406-0800: [GC (CMS Initial Mark) [1 CMS-initial-mark: 332165K(349568K)] 352656K(506816K), 0.0001858 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T01:12:14.406-0800: [CMS-concurrent-mark-start]
2020-10-27T01:12:14.407-0800: [CMS-concurrent-mark: 0.001/0.001 secs] [Times: user=0.01 sys=0.00, real=0.00 secs] 
2020-10-27T01:12:14.407-0800: [CMS-concurrent-preclean-start]
2020-10-27T01:12:14.408-0800: [CMS-concurrent-preclean: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T01:12:14.408-0800: [CMS-concurrent-abortable-preclean-start]
2020-10-27T01:12:14.408-0800: [CMS-concurrent-abortable-preclean: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T01:12:14.408-0800: [GC (CMS Final Remark) [YG occupancy: 33632 K (157248 K)]2020-10-27T01:12:14.409-0800: [Rescan (parallel) , 0.0007176 secs]2020-10-27T01:12:14.409-0800: [weak refs processing, 0.0000166 secs]2020-10-27T01:12:14.409-0800: [class unloading, 0.0002022 secs]2020-10-27T01:12:14.409-0800: [scrub symbol table, 0.0002822 secs]2020-10-27T01:12:14.410-0800: [scrub string table, 0.0001844 secs][1 CMS-remark: 332165K(349568K)] 365798K(506816K), 0.0014536 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
2020-10-27T01:12:14.410-0800: [CMS-concurrent-sweep-start]
2020-10-27T01:12:14.411-0800: [CMS-concurrent-sweep: 0.001/0.001 secs] [Times: user=0.01 sys=0.00, real=0.00 secs] 
2020-10-27T01:12:14.411-0800: [CMS-concurrent-reset-start]
2020-10-27T01:12:14.411-0800: [CMS-concurrent-reset: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T01:12:14.429-0800: [GC (Allocation Failure) 2020-10-27T01:12:14.429-0800: [ParNew: 157247K->157247K(157248K), 0.0000239 secs]2020-10-27T01:12:14.429-0800: [CMS: 295747K->323009K(349568K), 0.0503949 secs] 452995K->323009K(506816K), [Metaspace: 2706K->2706K(1056768K)], 0.0504707 secs] [Times: user=0.05 sys=0.00, real=0.05 secs] 
2020-10-27T01:12:14.480-0800: [GC (CMS Initial Mark) [1 CMS-initial-mark: 323009K(349568K)] 325838K(506816K), 0.0002154 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T01:12:14.480-0800: [CMS-concurrent-mark-start]
2020-10-27T01:12:14.481-0800: [CMS-concurrent-mark: 0.001/0.001 secs] [Times: user=0.01 sys=0.00, real=0.00 secs] 
2020-10-27T01:12:14.481-0800: [CMS-concurrent-preclean-start]
2020-10-27T01:12:14.482-0800: [CMS-concurrent-preclean: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T01:12:14.482-0800: [CMS-concurrent-abortable-preclean-start]
2020-10-27T01:12:14.501-0800: [GC (Allocation Failure) 2020-10-27T01:12:14.501-0800: [ParNew: 139776K->139776K(157248K), 0.0000204 secs]2020-10-27T01:12:14.501-0800: [CMS2020-10-27T01:12:14.501-0800: [CMS-concurrent-abortable-preclean: 0.001/0.020 secs] [Times: user=0.02 sys=0.00, real=0.02 secs] 
 (concurrent mode failure): 323009K->326728K(349568K), 0.0488878 secs] 462785K->326728K(506816K), [Metaspace: 2706K->2706K(1056768K)], 0.0489589 secs] [Times: user=0.05 sys=0.00, real=0.05 secs] 
2020-10-27T01:12:14.571-0800: [GC (Allocation Failure) 2020-10-27T01:12:14.571-0800: [ParNew: 139776K->139776K(157248K), 0.0000214 secs]2020-10-27T01:12:14.571-0800: [CMS: 326728K->338236K(349568K), 0.0533765 secs] 466504K->338236K(506816K), [Metaspace: 2706K->2706K(1056768K)], 0.0534481 secs] [Times: user=0.05 sys=0.00, real=0.05 secs] 
2020-10-27T01:12:14.625-0800: [GC (CMS Initial Mark) [1 CMS-initial-mark: 338236K(349568K)] 338406K(506816K), 0.0002007 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T01:12:14.625-0800: [CMS-concurrent-mark-start]
2020-10-27T01:12:14.626-0800: [CMS-concurrent-mark: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T01:12:14.626-0800: [CMS-concurrent-preclean-start]
2020-10-27T01:12:14.627-0800: [CMS-concurrent-preclean: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T01:12:14.627-0800: [CMS-concurrent-abortable-preclean-start]
2020-10-27T01:12:14.627-0800: [CMS-concurrent-abortable-preclean: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T01:12:14.627-0800: [GC (CMS Final Remark) [YG occupancy: 13027 K (157248 K)]2020-10-27T01:12:14.627-0800: [Rescan (parallel) , 0.0003042 secs]2020-10-27T01:12:14.627-0800: [weak refs processing, 0.0000123 secs]2020-10-27T01:12:14.627-0800: [class unloading, 0.0002175 secs]2020-10-27T01:12:14.627-0800: [scrub symbol table, 0.0002972 secs]2020-10-27T01:12:14.627-0800: [scrub string table, 0.0001916 secs][1 CMS-remark: 338236K(349568K)] 351263K(506816K), 0.0010677 secs] [Times: user=0.01 sys=0.00, real=0.00 secs] 
2020-10-27T01:12:14.628-0800: [CMS-concurrent-sweep-start]
2020-10-27T01:12:14.628-0800: [CMS-concurrent-sweep: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T01:12:14.628-0800: [CMS-concurrent-reset-start]
2020-10-27T01:12:14.629-0800: [CMS-concurrent-reset: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T01:12:14.648-0800: [GC (Allocation Failure) 2020-10-27T01:12:14.648-0800: [ParNew: 139776K->139776K(157248K), 0.0000199 secs]2020-10-27T01:12:14.648-0800: [CMS: 337204K->341150K(349568K), 0.0543150 secs] 476980K->341150K(506816K), [Metaspace: 2706K->2706K(1056768K)], 0.0543870 secs] [Times: user=0.05 sys=0.00, real=0.06 secs] 
2020-10-27T01:12:14.702-0800: [GC (CMS Initial Mark) [1 CMS-initial-mark: 341150K(349568K)] 344037K(506816K), 0.0002096 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T01:12:14.702-0800: [CMS-concurrent-mark-start]
执行结束!共生成对象次数:10016
Heap
 par new generation   total 157248K, used 5682K [0x00000007a0000000, 0x00000007aaaa0000, 0x00000007aaaa0000)
  eden space 139776K,   4% used [0x00000007a0000000, 0x00000007a058cbe8, 0x00000007a8880000)
  from space 17472K,   0% used [0x00000007a8880000, 0x00000007a8880000, 0x00000007a9990000)
  to   space 17472K,   0% used [0x00000007a9990000, 0x00000007a9990000, 0x00000007aaaa0000)
 concurrent mark-sweep generation total 349568K, used 341150K [0x00000007aaaa0000, 0x00000007c0000000, 0x00000007c0000000)
 Metaspace       used 2712K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 295K, capacity 386K, committed 512K, reserved 1048576K
```

## g1

`java -XX:+UseG1GC -Xms512m -Xmx512m -XX:+PrintGC -XX:+PrintGCDateStamps GCLogAnalysis`

日志:

```
2020-10-27T01:15:58.307-0800: [GC pause (G1 Evacuation Pause) (young) 32M->14M(512M), 0.0034322 secs]
2020-10-27T01:15:58.316-0800: [GC pause (G1 Evacuation Pause) (young) 39M->21M(512M), 0.0026830 secs]
2020-10-27T01:15:58.337-0800: [GC pause (G1 Evacuation Pause) (young) 74M->39M(512M), 0.0063516 secs]
2020-10-27T01:15:58.370-0800: [GC pause (G1 Evacuation Pause) (young) 124M->61M(512M), 0.0081719 secs]
2020-10-27T01:15:58.424-0800: [GC pause (G1 Evacuation Pause) (young) 201M->105M(512M), 0.0134229 secs]
2020-10-27T01:15:58.458-0800: [GC pause (G1 Evacuation Pause) (young) 222M->146M(512M), 0.0120200 secs]
2020-10-27T01:15:58.497-0800: [GC pause (G1 Evacuation Pause) (young) 294M->190M(512M), 0.0163513 secs]
2020-10-27T01:15:58.528-0800: [GC pause (G1 Humongous Allocation) (young) (initial-mark) 281M->218M(512M), 0.0070753 secs]
2020-10-27T01:15:58.535-0800: [GC concurrent-root-region-scan-start]
2020-10-27T01:15:58.535-0800: [GC concurrent-root-region-scan-end, 0.0001538 secs]
2020-10-27T01:15:58.535-0800: [GC concurrent-mark-start]
2020-10-27T01:15:58.537-0800: [GC concurrent-mark-end, 0.0012587 secs]
2020-10-27T01:15:58.537-0800: [GC remark, 0.0008898 secs]
2020-10-27T01:15:58.538-0800: [GC cleanup 227M->227M(512M), 0.0005689 secs]
2020-10-27T01:15:58.586-0800: [GC pause (G1 Evacuation Pause) (young)-- 421M->349M(512M), 0.0136542 secs]
2020-10-27T01:15:58.601-0800: [GC pause (G1 Evacuation Pause) (mixed) 355M->336M(512M), 0.0040278 secs]
2020-10-27T01:15:58.605-0800: [GC pause (G1 Humongous Allocation) (young) (initial-mark) 339M->337M(512M), 0.0011624 secs]
2020-10-27T01:15:58.606-0800: [GC concurrent-root-region-scan-start]
2020-10-27T01:15:58.606-0800: [GC concurrent-root-region-scan-end, 0.0000776 secs]
2020-10-27T01:15:58.606-0800: [GC concurrent-mark-start]
2020-10-27T01:15:58.607-0800: [GC concurrent-mark-end, 0.0005573 secs]
2020-10-27T01:15:58.607-0800: [GC remark, 0.0016807 secs]
2020-10-27T01:15:58.609-0800: [GC cleanup 340M->338M(512M), 0.0006302 secs]
2020-10-27T01:15:58.610-0800: [GC concurrent-cleanup-start]
2020-10-27T01:15:58.610-0800: [GC concurrent-cleanup-end, 0.0000113 secs]
2020-10-27T01:15:58.620-0800: [GC pause (G1 Evacuation Pause) (young) 418M->355M(512M), 0.0034246 secs]
2020-10-27T01:15:58.627-0800: [GC pause (G1 Evacuation Pause) (mixed) 374M->315M(512M), 0.0029133 secs]
2020-10-27T01:15:58.633-0800: [GC pause (G1 Evacuation Pause) (mixed) 341M->286M(512M), 0.0033953 secs]
2020-10-27T01:15:58.641-0800: [GC pause (G1 Evacuation Pause) (mixed) 315M->278M(512M), 0.0030751 secs]
2020-10-27T01:15:58.644-0800: [GC pause (G1 Humongous Allocation) (young) (initial-mark) 279M->277M(512M), 0.0012682 secs]
2020-10-27T01:15:58.646-0800: [GC concurrent-root-region-scan-start]
2020-10-27T01:15:58.646-0800: [GC concurrent-root-region-scan-end, 0.0001134 secs]
2020-10-27T01:15:58.646-0800: [GC concurrent-mark-start]
2020-10-27T01:15:58.646-0800: [GC concurrent-mark-end, 0.0006484 secs]
2020-10-27T01:15:58.647-0800: [GC remark, 0.0011607 secs]
2020-10-27T01:15:58.648-0800: [GC cleanup 282M->282M(512M), 0.0006635 secs]
2020-10-27T01:15:58.670-0800: [GC pause (G1 Evacuation Pause) (young)-- 422M->356M(512M), 0.0041604 secs]
2020-10-27T01:15:58.677-0800: [GC pause (G1 Evacuation Pause) (mixed) 371M->337M(512M), 0.0045176 secs]
2020-10-27T01:15:58.682-0800: [GC pause (G1 Humongous Allocation) (young) (initial-mark) 339M->338M(512M), 0.0012962 secs]
2020-10-27T01:15:58.683-0800: [GC concurrent-root-region-scan-start]
2020-10-27T01:15:58.683-0800: [GC concurrent-root-region-scan-end, 0.0001028 secs]
2020-10-27T01:15:58.683-0800: [GC concurrent-mark-start]
2020-10-27T01:15:58.684-0800: [GC concurrent-mark-end, 0.0006366 secs]
2020-10-27T01:15:58.684-0800: [GC remark, 0.0011677 secs]
2020-10-27T01:15:58.685-0800: [GC cleanup 343M->343M(512M), 0.0006842 secs]
2020-10-27T01:15:58.698-0800: [GC pause (G1 Evacuation Pause) (young) 420M->358M(512M), 0.0031070 secs]
2020-10-27T01:15:58.703-0800: [GC pause (G1 Evacuation Pause) (mixed) 381M->321M(512M), 0.0029016 secs]
2020-10-27T01:15:58.710-0800: [GC pause (G1 Evacuation Pause) (mixed) 352M->314M(512M), 0.0043124 secs]
2020-10-27T01:15:58.715-0800: [GC pause (G1 Humongous Allocation) (young) (initial-mark) 317M->314M(512M), 0.0014177 secs]
2020-10-27T01:15:58.717-0800: [GC concurrent-root-region-scan-start]
2020-10-27T01:15:58.717-0800: [GC concurrent-root-region-scan-end, 0.0000898 secs]
2020-10-27T01:15:58.717-0800: [GC concurrent-mark-start]
2020-10-27T01:15:58.717-0800: [GC concurrent-mark-end, 0.0006396 secs]
2020-10-27T01:15:58.718-0800: [GC remark, 0.0012448 secs]
2020-10-27T01:15:58.719-0800: [GC cleanup 320M->320M(512M), 0.0006723 secs]
2020-10-27T01:15:58.734-0800: [GC pause (G1 Evacuation Pause) (young) 410M->340M(512M), 0.0036065 secs]
2020-10-27T01:15:58.741-0800: [GC pause (G1 Evacuation Pause) (mixed) 359M->316M(512M), 0.0048636 secs]
2020-10-27T01:15:58.746-0800: [GC pause (G1 Humongous Allocation) (young) (initial-mark) 318M->316M(512M), 0.0013563 secs]
2020-10-27T01:15:58.747-0800: [GC concurrent-root-region-scan-start]
2020-10-27T01:15:58.748-0800: [GC concurrent-root-region-scan-end, 0.0001205 secs]
2020-10-27T01:15:58.748-0800: [GC concurrent-mark-start]
2020-10-27T01:15:58.749-0800: [GC concurrent-mark-end, 0.0009326 secs]
2020-10-27T01:15:58.749-0800: [GC remark, 0.0013548 secs]
2020-10-27T01:15:58.750-0800: [GC cleanup 323M->323M(512M), 0.0007100 secs]
2020-10-27T01:15:58.764-0800: [GC pause (G1 Evacuation Pause) (young) 407M->341M(512M), 0.0042483 secs]
2020-10-27T01:15:58.771-0800: [GC pause (G1 Evacuation Pause) (mixed) 361M->328M(512M), 0.0048412 secs]
2020-10-27T01:15:58.776-0800: [GC pause (G1 Humongous Allocation) (young) (initial-mark) 329M->328M(512M), 0.0014219 secs]
2020-10-27T01:15:58.778-0800: [GC concurrent-root-region-scan-start]
2020-10-27T01:15:58.778-0800: [GC concurrent-root-region-scan-end, 0.0001137 secs]
2020-10-27T01:15:58.778-0800: [GC concurrent-mark-start]
2020-10-27T01:15:58.779-0800: [GC concurrent-mark-end, 0.0009066 secs]
2020-10-27T01:15:58.779-0800: [GC remark, 0.0012540 secs]
2020-10-27T01:15:58.780-0800: [GC cleanup 335M->335M(512M), 0.0006877 secs]
2020-10-27T01:15:58.795-0800: [GC pause (G1 Evacuation Pause) (young) 407M->346M(512M), 0.0037805 secs]
2020-10-27T01:15:58.801-0800: [GC pause (G1 Evacuation Pause) (mixed) 367M->334M(512M), 0.0048456 secs]
2020-10-27T01:15:58.806-0800: [GC pause (G1 Humongous Allocation) (young) (initial-mark) 336M->334M(512M), 0.0015530 secs]
2020-10-27T01:15:58.808-0800: [GC concurrent-root-region-scan-start]
2020-10-27T01:15:58.808-0800: [GC concurrent-root-region-scan-end, 0.0001128 secs]
2020-10-27T01:15:58.808-0800: [GC concurrent-mark-start]
2020-10-27T01:15:58.809-0800: [GC concurrent-mark-end, 0.0008745 secs]
2020-10-27T01:15:58.809-0800: [GC remark, 0.0013295 secs]
2020-10-27T01:15:58.810-0800: [GC cleanup 342M->342M(512M), 0.0006250 secs]
2020-10-27T01:15:58.823-0800: [GC pause (G1 Evacuation Pause) (young) 419M->357M(512M), 0.0035243 secs]
2020-10-27T01:15:58.830-0800: [GC pause (G1 Evacuation Pause) (mixed) 379M->345M(512M), 0.0046128 secs]
2020-10-27T01:15:58.835-0800: [GC pause (G1 Humongous Allocation) (young) (initial-mark) 346M->345M(512M), 0.0015583 secs]
2020-10-27T01:15:58.836-0800: [GC concurrent-root-region-scan-start]
2020-10-27T01:15:58.837-0800: [GC concurrent-root-region-scan-end, 0.0000738 secs]
2020-10-27T01:15:58.837-0800: [GC concurrent-mark-start]
2020-10-27T01:15:58.837-0800: [GC concurrent-mark-end, 0.0008011 secs]
2020-10-27T01:15:58.837-0800: [GC remark, 0.0013027 secs]
2020-10-27T01:15:58.839-0800: [GC cleanup 350M->350M(512M), 0.0006304 secs]
2020-10-27T01:15:58.849-0800: [GC pause (G1 Evacuation Pause) (young) 410M->364M(512M), 0.0027498 secs]
2020-10-27T01:15:58.856-0800: [GC pause (G1 Evacuation Pause) (mixed) 390M->350M(512M), 0.0047829 secs]
2020-10-27T01:15:58.861-0800: [GC pause (G1 Humongous Allocation) (young) (initial-mark) 351M->350M(512M), 0.0016589 secs]
2020-10-27T01:15:58.863-0800: [GC concurrent-root-region-scan-start]
2020-10-27T01:15:58.863-0800: [GC concurrent-root-region-scan-end, 0.0000452 secs]
2020-10-27T01:15:58.863-0800: [GC concurrent-mark-start]
2020-10-27T01:15:58.864-0800: [GC concurrent-mark-end, 0.0009696 secs]
2020-10-27T01:15:58.864-0800: [GC remark, 0.0013535 secs]
2020-10-27T01:15:58.865-0800: [GC cleanup 359M->359M(512M), 0.0006454 secs]
2020-10-27T01:15:58.874-0800: [GC pause (G1 Evacuation Pause) (young) 407M->366M(512M), 0.0030373 secs]
2020-10-27T01:15:58.880-0800: [GC pause (G1 Evacuation Pause) (mixed) 388M->356M(512M), 0.0041813 secs]
2020-10-27T01:15:58.885-0800: [GC pause (G1 Humongous Allocation) (young) (initial-mark) 359M->357M(512M), 0.0016363 secs]
2020-10-27T01:15:58.887-0800: [GC concurrent-root-region-scan-start]
2020-10-27T01:15:58.887-0800: [GC concurrent-root-region-scan-end, 0.0000931 secs]
2020-10-27T01:15:58.887-0800: [GC concurrent-mark-start]
2020-10-27T01:15:58.888-0800: [GC concurrent-mark-end, 0.0009214 secs]
2020-10-27T01:15:58.888-0800: [GC remark, 0.0013447 secs]
2020-10-27T01:15:58.889-0800: [GC cleanup 364M->364M(512M), 0.0007198 secs]
2020-10-27T01:15:58.894-0800: [GC pause (G1 Evacuation Pause) (young) 390M->365M(512M), 0.0025465 secs]
2020-10-27T01:15:58.900-0800: [GC pause (G1 Evacuation Pause) (mixed) 391M->353M(512M), 0.0044313 secs]
2020-10-27T01:15:58.906-0800: [GC pause (G1 Humongous Allocation) (young) (initial-mark) 365M->354M(512M), 0.0017387 secs]
2020-10-27T01:15:58.908-0800: [GC concurrent-root-region-scan-start]
2020-10-27T01:15:58.908-0800: [GC concurrent-root-region-scan-end, 0.0001077 secs]
2020-10-27T01:15:58.908-0800: [GC concurrent-mark-start]
2020-10-27T01:15:58.909-0800: [GC concurrent-mark-end, 0.0007903 secs]
2020-10-27T01:15:58.909-0800: [GC remark, 0.0015080 secs]
2020-10-27T01:15:58.910-0800: [GC cleanup 359M->359M(512M), 0.0006655 secs]
2020-10-27T01:15:58.917-0800: [GC pause (G1 Evacuation Pause) (young) 398M->363M(512M), 0.0031149 secs]
2020-10-27T01:15:58.924-0800: [GC pause (G1 Evacuation Pause) (mixed) 389M->357M(512M), 0.0042258 secs]
2020-10-27T01:15:58.929-0800: [GC pause (G1 Humongous Allocation) (young) (initial-mark) 358M->357M(512M), 0.0014882 secs]
2020-10-27T01:15:58.931-0800: [GC concurrent-root-region-scan-start]
2020-10-27T01:15:58.931-0800: [GC concurrent-root-region-scan-end, 0.0000836 secs]
2020-10-27T01:15:58.931-0800: [GC concurrent-mark-start]
2020-10-27T01:15:58.932-0800: [GC concurrent-mark-end, 0.0009233 secs]
2020-10-27T01:15:58.932-0800: [GC remark, 0.0014479 secs]
2020-10-27T01:15:58.933-0800: [GC cleanup 365M->365M(512M), 0.0006378 secs]
2020-10-27T01:15:58.938-0800: [GC pause (G1 Evacuation Pause) (young) 392M->369M(512M), 0.0023572 secs]
2020-10-27T01:15:58.944-0800: [GC pause (G1 Evacuation Pause) (mixed) 394M->359M(512M), 0.0050028 secs]
2020-10-27T01:15:58.950-0800: [GC pause (G1 Humongous Allocation) (young) (initial-mark) 364M->360M(512M), 0.0017291 secs]
2020-10-27T01:15:58.952-0800: [GC concurrent-root-region-scan-start]
2020-10-27T01:15:58.952-0800: [GC concurrent-root-region-scan-end, 0.0000910 secs]
2020-10-27T01:15:58.952-0800: [GC concurrent-mark-start]
2020-10-27T01:15:58.953-0800: [GC concurrent-mark-end, 0.0008316 secs]
2020-10-27T01:15:58.953-0800: [GC remark, 0.0013615 secs]
2020-10-27T01:15:58.955-0800: [GC cleanup 366M->366M(512M), 0.0007329 secs]
2020-10-27T01:15:58.960-0800: [GC pause (G1 Evacuation Pause) (young) 394M->371M(512M), 0.0025990 secs]
2020-10-27T01:15:58.967-0800: [GC pause (G1 Evacuation Pause) (mixed) 399M->364M(512M), 0.0048638 secs]
2020-10-27T01:15:58.973-0800: [GC pause (G1 Humongous Allocation) (young) (initial-mark) 367M->366M(512M), 0.0014725 secs]
2020-10-27T01:15:58.974-0800: [GC concurrent-root-region-scan-start]
2020-10-27T01:15:58.974-0800: [GC concurrent-root-region-scan-end, 0.0000922 secs]
2020-10-27T01:15:58.975-0800: [GC concurrent-mark-start]
2020-10-27T01:15:58.975-0800: [GC concurrent-mark-end, 0.0007619 secs]
2020-10-27T01:15:58.975-0800: [GC remark, 0.0015434 secs]
2020-10-27T01:15:58.977-0800: [GC cleanup 371M->371M(512M), 0.0007074 secs]
2020-10-27T01:15:58.981-0800: [GC pause (G1 Evacuation Pause) (young) 396M->374M(512M), 0.0024794 secs]
2020-10-27T01:15:58.987-0800: [GC pause (G1 Evacuation Pause) (mixed) 401M->370M(512M), 0.0051245 secs]
2020-10-27T01:15:58.993-0800: [GC pause (G1 Humongous Allocation) (young) (initial-mark) 370M->370M(512M), 0.0015168 secs]
2020-10-27T01:15:58.994-0800: [GC concurrent-root-region-scan-start]
2020-10-27T01:15:58.994-0800: [GC concurrent-root-region-scan-end, 0.0001103 secs]
2020-10-27T01:15:58.994-0800: [GC concurrent-mark-start]
2020-10-27T01:15:58.995-0800: [GC concurrent-mark-end, 0.0007706 secs]
2020-10-27T01:15:58.995-0800: [GC remark, 0.0014355 secs]
2020-10-27T01:15:58.997-0800: [GC cleanup 375M->375M(512M), 0.0007478 secs]
2020-10-27T01:15:59.002-0800: [GC pause (G1 Evacuation Pause) (young) 398M->374M(512M), 0.0017573 secs]
2020-10-27T01:15:59.007-0800: [GC pause (G1 Evacuation Pause) (mixed) 400M->367M(512M), 0.0041402 secs]
2020-10-27T01:15:59.012-0800: [GC pause (G1 Humongous Allocation) (young) (initial-mark) 368M->367M(512M), 0.0016510 secs]
2020-10-27T01:15:59.013-0800: [GC concurrent-root-region-scan-start]
2020-10-27T01:15:59.013-0800: [GC concurrent-root-region-scan-end, 0.0000542 secs]
2020-10-27T01:15:59.013-0800: [GC concurrent-mark-start]
2020-10-27T01:15:59.014-0800: [GC concurrent-mark-end, 0.0008555 secs]
2020-10-27T01:15:59.014-0800: [GC remark, 0.0014390 secs]
2020-10-27T01:15:59.016-0800: [GC cleanup 375M->375M(512M), 0.0007821 secs]
2020-10-27T01:15:59.021-0800: [GC pause (G1 Evacuation Pause) (young) 398M->379M(512M), 0.0022986 secs]
2020-10-27T01:15:59.027-0800: [GC pause (G1 Evacuation Pause) (mixed) 405M->377M(512M), 0.0038219 secs]
2020-10-27T01:15:59.032-0800: [GC pause (G1 Humongous Allocation) (young) (initial-mark) 383M->380M(512M), 0.0015754 secs]
2020-10-27T01:15:59.033-0800: [GC concurrent-root-region-scan-start]
2020-10-27T01:15:59.033-0800: [GC concurrent-root-region-scan-end, 0.0000900 secs]
2020-10-27T01:15:59.033-0800: [GC concurrent-mark-start]
2020-10-27T01:15:59.034-0800: [GC concurrent-mark-end, 0.0008216 secs]
2020-10-27T01:15:59.034-0800: [GC remark, 0.0013693 secs]
2020-10-27T01:15:59.036-0800: [GC cleanup 385M->385M(512M), 0.0006946 secs]
2020-10-27T01:15:59.040-0800: [GC pause (G1 Evacuation Pause) (young) 407M->386M(512M), 0.0023149 secs]
2020-10-27T01:15:59.047-0800: [GC pause (G1 Evacuation Pause) (mixed)-- 416M->388M(512M), 0.0045350 secs]
2020-10-27T01:15:59.051-0800: [GC pause (G1 Humongous Allocation) (young) (initial-mark) 389M->388M(512M), 0.0015973 secs]
2020-10-27T01:15:59.053-0800: [GC concurrent-root-region-scan-start]
2020-10-27T01:15:59.053-0800: [GC concurrent-root-region-scan-end, 0.0000787 secs]
2020-10-27T01:15:59.053-0800: [GC concurrent-mark-start]
2020-10-27T01:15:59.054-0800: [GC concurrent-mark-end, 0.0008314 secs]
2020-10-27T01:15:59.054-0800: [GC remark, 0.0014507 secs]
2020-10-27T01:15:59.056-0800: [GC cleanup 395M->395M(512M), 0.0006982 secs]
2020-10-27T01:15:59.061-0800: [GC pause (G1 Evacuation Pause) (young) 423M->398M(512M), 0.0023895 secs]
2020-10-27T01:15:59.068-0800: [GC pause (G1 Evacuation Pause) (mixed)-- 424M->419M(512M), 0.0036207 secs]
2020-10-27T01:15:59.072-0800: [GC pause (G1 Humongous Allocation) (young) (initial-mark) 421M->421M(512M), 0.0015781 secs]
2020-10-27T01:15:59.074-0800: [GC concurrent-root-region-scan-start]
2020-10-27T01:15:59.074-0800: [GC concurrent-root-region-scan-end, 0.0000724 secs]
2020-10-27T01:15:59.074-0800: [GC concurrent-mark-start]
2020-10-27T01:15:59.075-0800: [GC concurrent-mark-end, 0.0008334 secs]
2020-10-27T01:15:59.075-0800: [GC remark, 0.0013779 secs]
2020-10-27T01:15:59.076-0800: [GC cleanup 426M->426M(512M), 0.0006440 secs]
2020-10-27T01:15:59.080-0800: [GC pause (G1 Evacuation Pause) (young)-- 445M->438M(512M), 0.0016734 secs]
2020-10-27T01:15:59.083-0800: [GC pause (G1 Humongous Allocation) (mixed)-- 447M->444M(512M), 0.0012282 secs]
2020-10-27T01:15:59.085-0800: [GC pause (G1 Humongous Allocation) (mixed)-- 446M->446M(512M), 0.0015527 secs]
2020-10-27T01:15:59.087-0800: [GC pause (G1 Evacuation Pause) (young) (initial-mark) 446M->446M(512M), 0.0008059 secs]
2020-10-27T01:15:59.087-0800: [GC concurrent-root-region-scan-start]
2020-10-27T01:15:59.087-0800: [GC concurrent-root-region-scan-end, 0.0000132 secs]
2020-10-27T01:15:59.088-0800: [GC concurrent-mark-start]
2020-10-27T01:15:59.088-0800: [GC pause (G1 Evacuation Pause) (young)-- 447M->446M(512M), 0.0009271 secs]
2020-10-27T01:15:59.089-0800: [GC pause (G1 Evacuation Pause) (young)-- 447M->447M(512M), 0.0009292 secs]
2020-10-27T01:15:59.090-0800: [Full GC (Allocation Failure)  447M->330M(512M), 0.0336319 secs]
2020-10-27T01:15:59.124-0800: [GC concurrent-mark-abort]
2020-10-27T01:15:59.124-0800: [GC pause (G1 Humongous Allocation) (young) (initial-mark) 330M->330M(512M), 0.0013521 secs]
2020-10-27T01:15:59.126-0800: [GC concurrent-root-region-scan-start]
2020-10-27T01:15:59.126-0800: [GC concurrent-root-region-scan-end, 0.0000600 secs]
2020-10-27T01:15:59.126-0800: [GC concurrent-mark-start]
2020-10-27T01:15:59.127-0800: [GC concurrent-mark-end, 0.0007589 secs]
2020-10-27T01:15:59.127-0800: [GC remark, 0.0013973 secs]
2020-10-27T01:15:59.128-0800: [GC cleanup 335M->335M(512M), 0.0006971 secs]
2020-10-27T01:15:59.134-0800: [GC pause (G1 Evacuation Pause) (young) 364M->341M(512M), 0.0023117 secs]
2020-10-27T01:15:59.137-0800: [GC pause (G1 Humongous Allocation) (young) (initial-mark) 343M->340M(512M), 0.0014478 secs]
2020-10-27T01:15:59.138-0800: [GC concurrent-root-region-scan-start]
2020-10-27T01:15:59.138-0800: [GC concurrent-root-region-scan-end, 0.0000624 secs]
2020-10-27T01:15:59.138-0800: [GC concurrent-mark-start]
2020-10-27T01:15:59.139-0800: [GC concurrent-mark-end, 0.0007809 secs]
2020-10-27T01:15:59.139-0800: [GC remark, 0.0012511 secs]
2020-10-27T01:15:59.141-0800: [GC cleanup 346M->346M(512M), 0.0006596 secs]
2020-10-27T01:15:59.145-0800: [GC pause (G1 Evacuation Pause) (young) 372M->346M(512M), 0.0022868 secs]
2020-10-27T01:15:59.152-0800: [GC pause (G1 Evacuation Pause) (mixed) 372M->347M(512M), 0.0023313 secs]
2020-10-27T01:15:59.154-0800: [GC pause (G1 Humongous Allocation) (young) (initial-mark) 348M->348M(512M), 0.0013732 secs]
2020-10-27T01:15:59.156-0800: [GC concurrent-root-region-scan-start]
2020-10-27T01:15:59.156-0800: [GC concurrent-root-region-scan-end, 0.0000477 secs]
2020-10-27T01:15:59.156-0800: [GC concurrent-mark-start]
2020-10-27T01:15:59.157-0800: [GC concurrent-mark-end, 0.0008119 secs]
2020-10-27T01:15:59.157-0800: [GC remark, 0.0013160 secs]
2020-10-27T01:15:59.158-0800: [GC cleanup 354M->354M(512M), 0.0006733 secs]
2020-10-27T01:15:59.163-0800: [GC pause (G1 Evacuation Pause) (young) 377M->356M(512M), 0.0018652 secs]
2020-10-27T01:15:59.168-0800: [GC pause (G1 Evacuation Pause) (mixed) 384M->356M(512M), 0.0038165 secs]
2020-10-27T01:15:59.173-0800: [GC pause (G1 Humongous Allocation) (young) (initial-mark) 357M->356M(512M), 0.0015661 secs]
2020-10-27T01:15:59.174-0800: [GC concurrent-root-region-scan-start]
2020-10-27T01:15:59.174-0800: [GC concurrent-root-region-scan-end, 0.0001322 secs]
2020-10-27T01:15:59.174-0800: [GC concurrent-mark-start]
2020-10-27T01:15:59.175-0800: [GC concurrent-mark-end, 0.0007725 secs]
2020-10-27T01:15:59.175-0800: [GC remark, 0.0014433 secs]
2020-10-27T01:15:59.177-0800: [GC cleanup 363M->363M(512M), 0.0006318 secs]
2020-10-27T01:15:59.181-0800: [GC pause (G1 Evacuation Pause) (young) 390M->368M(512M), 0.0025221 secs]
2020-10-27T01:15:59.188-0800: [GC pause (G1 Evacuation Pause) (mixed)-- 395M->376M(512M), 0.0040327 secs]
2020-10-27T01:15:59.192-0800: [GC pause (G1 Humongous Allocation) (young) (initial-mark) 377M->375M(512M), 0.0013897 secs]
2020-10-27T01:15:59.194-0800: [GC concurrent-root-region-scan-start]
2020-10-27T01:15:59.194-0800: [GC concurrent-root-region-scan-end, 0.0000534 secs]
2020-10-27T01:15:59.194-0800: [GC concurrent-mark-start]
2020-10-27T01:15:59.195-0800: [GC concurrent-mark-end, 0.0007930 secs]
2020-10-27T01:15:59.195-0800: [GC remark, 0.0013271 secs]
2020-10-27T01:15:59.196-0800: [GC cleanup 380M->380M(512M), 0.0006533 secs]
2020-10-27T01:15:59.201-0800: [GC pause (G1 Evacuation Pause) (young) 409M->384M(512M), 0.0025181 secs]
2020-10-27T01:15:59.209-0800: [GC pause (G1 Evacuation Pause) (mixed)-- 412M->401M(512M), 0.0029443 secs]
2020-10-27T01:15:59.212-0800: [GC pause (G1 Humongous Allocation) (young) (initial-mark) 404M->402M(512M), 0.0015621 secs]
2020-10-27T01:15:59.214-0800: [GC concurrent-root-region-scan-start]
2020-10-27T01:15:59.214-0800: [GC concurrent-root-region-scan-end, 0.0000860 secs]
2020-10-27T01:15:59.214-0800: [GC concurrent-mark-start]
2020-10-27T01:15:59.215-0800: [GC concurrent-mark-end, 0.0007855 secs]
2020-10-27T01:15:59.215-0800: [GC remark, 0.0012613 secs]
2020-10-27T01:15:59.216-0800: [GC cleanup 407M->407M(512M), 0.0006506 secs]
2020-10-27T01:15:59.220-0800: [GC pause (G1 Evacuation Pause) (young)-- 426M->419M(512M), 0.0016476 secs]
2020-10-27T01:15:59.223-0800: [GC pause (G1 Evacuation Pause) (mixed)-- 428M->426M(512M), 0.0014034 secs]
2020-10-27T01:15:59.225-0800: [GC pause (G1 Humongous Allocation) (mixed)-- 428M->426M(512M), 0.0013664 secs]
2020-10-27T01:15:59.226-0800: [GC pause (G1 Humongous Allocation) (young) (initial-mark) 428M->427M(512M), 0.0008144 secs]
2020-10-27T01:15:59.227-0800: [GC concurrent-root-region-scan-start]
2020-10-27T01:15:59.227-0800: [GC concurrent-root-region-scan-end, 0.0000429 secs]
2020-10-27T01:15:59.227-0800: [GC concurrent-mark-start]
2020-10-27T01:15:59.227-0800: [GC pause (G1 Humongous Allocation) (young)-- 428M->427M(512M), 0.0010135 secs]
2020-10-27T01:15:59.229-0800: [GC pause (G1 Evacuation Pause) (young) 428M->427M(512M), 0.0008622 secs]
2020-10-27T01:15:59.230-0800: [GC pause (G1 Evacuation Pause) (young)-- 428M->428M(512M), 0.0009707 secs]
2020-10-27T01:15:59.231-0800: [GC concurrent-mark-end, 0.0038245 secs]
2020-10-27T01:15:59.231-0800: [GC remark, 0.0011483 secs]
2020-10-27T01:15:59.232-0800: [GC cleanup 428M->427M(512M), 0.0003898 secs]
2020-10-27T01:15:59.233-0800: [GC concurrent-cleanup-start]
2020-10-27T01:15:59.233-0800: [GC concurrent-cleanup-end, 0.0000106 secs]
2020-10-27T01:15:59.233-0800: [GC pause (G1 Evacuation Pause) (young)-- 429M->428M(512M), 0.0008953 secs]
2020-10-27T01:15:59.234-0800: [GC pause (G1 Evacuation Pause) (mixed)-- 429M->429M(512M), 0.0008605 secs]
2020-10-27T01:15:59.235-0800: [Full GC (Allocation Failure)  429M->333M(512M), 0.0348324 secs]
2020-10-27T01:15:59.270-0800: [GC pause (G1 Humongous Allocation) (young) (initial-mark) 334M->334M(512M), 0.0011313 secs]
2020-10-27T01:15:59.272-0800: [GC concurrent-root-region-scan-start]
2020-10-27T01:15:59.272-0800: [GC concurrent-root-region-scan-end, 0.0000908 secs]
2020-10-27T01:15:59.272-0800: [GC concurrent-mark-start]
2020-10-27T01:15:59.272-0800: [GC concurrent-mark-end, 0.0007019 secs]
2020-10-27T01:15:59.272-0800: [GC remark, 0.0013263 secs]
2020-10-27T01:15:59.274-0800: [GC cleanup 339M->339M(512M), 0.0007465 secs]
2020-10-27T01:15:59.279-0800: [GC pause (G1 Evacuation Pause) (young) 371M->345M(512M), 0.0027115 secs]
2020-10-27T01:15:59.282-0800: [GC pause (G1 Humongous Allocation) (young) (initial-mark) 346M->345M(512M), 0.0014213 secs]
2020-10-27T01:15:59.284-0800: [GC concurrent-root-region-scan-start]
2020-10-27T01:15:59.284-0800: [GC concurrent-root-region-scan-end, 0.0001106 secs]
2020-10-27T01:15:59.284-0800: [GC concurrent-mark-start]
2020-10-27T01:15:59.285-0800: [GC concurrent-mark-end, 0.0006912 secs]
2020-10-27T01:15:59.285-0800: [GC remark, 0.0013465 secs]
2020-10-27T01:15:59.286-0800: [GC cleanup 350M->350M(512M), 0.0006900 secs]
执行结束!共生成对象次数:10652
```

# 2G 下各个垃圾回收器的gc日志

## 串行

`java -XX:+UseSerialGC -Xms2G -Xmx2G -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis`

日志: 

```
2020-10-27T01:18:07.706-0800: [GC (Allocation Failure) 2020-10-27T01:18:07.706-0800: [DefNew: 559232K->69888K(629120K), 0.0870976 secs] 559232K->153402K(2027264K), 0.0871349 secs] [Times: user=0.05 sys=0.03, real=0.09 secs] 
2020-10-27T01:18:07.874-0800: [GC (Allocation Failure) 2020-10-27T01:18:07.874-0800: [DefNew: 629120K->69887K(629120K), 0.1037708 secs] 712634K->281857K(2027264K), 0.1038029 secs] [Times: user=0.06 sys=0.04, real=0.10 secs] 
2020-10-27T01:18:08.040-0800: [GC (Allocation Failure) 2020-10-27T01:18:08.040-0800: [DefNew: 629119K->69887K(629120K), 0.0722838 secs] 841089K->397152K(2027264K), 0.0723220 secs] [Times: user=0.05 sys=0.03, real=0.07 secs] 
2020-10-27T01:18:08.176-0800: [GC (Allocation Failure) 2020-10-27T01:18:08.176-0800: [DefNew: 629119K->69887K(629120K), 0.0868865 secs] 956384K->512206K(2027264K), 0.0869218 secs] [Times: user=0.05 sys=0.03, real=0.09 secs] 
2020-10-27T01:18:08.328-0800: [GC (Allocation Failure) 2020-10-27T01:18:08.328-0800: [DefNew: 629119K->69887K(629120K), 0.0805547 secs] 1071438K->641718K(2027264K), 0.0805857 secs] [Times: user=0.05 sys=0.03, real=0.08 secs] 
执行结束!共生成对象次数:10759
Heap
 def new generation   total 629120K, used 127021K [0x0000000740000000, 0x000000076aaa0000, 0x000000076aaa0000)
  eden space 559232K,  10% used [0x0000000740000000, 0x00000007437cb700, 0x0000000762220000)
  from space 69888K,  99% used [0x0000000766660000, 0x000000076aa9fff0, 0x000000076aaa0000)
  to   space 69888K,   0% used [0x0000000762220000, 0x0000000762220000, 0x0000000766660000)
 tenured generation   total 1398144K, used 571830K [0x000000076aaa0000, 0x00000007c0000000, 0x00000007c0000000)
   the space 1398144K,  40% used [0x000000076aaa0000, 0x000000078d90db98, 0x000000078d90dc00, 0x00000007c0000000)
 Metaspace       used 2712K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 295K, capacity 386K, committed 512K, reserved 1048576K

```

## 并行

`java -Xms2G -Xmx2G -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis`

日志:

```
2020-10-27T01:19:03.043-0800: [GC (Allocation Failure) [PSYoungGen: 524800K->87039K(611840K)] 524800K->143705K(2010112K), 0.0551466 secs] [Times: user=0.06 sys=0.36, real=0.05 secs] 
2020-10-27T01:19:03.167-0800: [GC (Allocation Failure) [PSYoungGen: 611839K->87033K(611840K)] 668505K->252124K(2010112K), 0.0676084 secs] [Times: user=0.07 sys=0.44, real=0.07 secs] 
2020-10-27T01:19:03.293-0800: [GC (Allocation Failure) [PSYoungGen: 611833K->87036K(611840K)] 776924K->359947K(2010112K), 0.0546618 secs] [Times: user=0.14 sys=0.30, real=0.05 secs] 
2020-10-27T01:19:03.413-0800: [GC (Allocation Failure) [PSYoungGen: 611836K->87035K(611840K)] 884747K->473206K(2010112K), 0.0598121 secs] [Times: user=0.14 sys=0.33, real=0.06 secs] 
2020-10-27T01:19:03.534-0800: [GC (Allocation Failure) [PSYoungGen: 611835K->87028K(611840K)] 998006K->585308K(2010112K), 0.0593772 secs] [Times: user=0.14 sys=0.32, real=0.06 secs] 
2020-10-27T01:19:03.660-0800: [GC (Allocation Failure) [PSYoungGen: 611828K->87032K(320000K)] 1110108K->708500K(1718272K), 0.0622633 secs] [Times: user=0.14 sys=0.35, real=0.06 secs] 
2020-10-27T01:19:03.754-0800: [GC (Allocation Failure) [PSYoungGen: 319992K->136973K(465920K)] 941460K->764914K(1864192K), 0.0152388 secs] [Times: user=0.14 sys=0.01, real=0.02 secs] 
执行结束!共生成对象次数:13498
Heap
 PSYoungGen      total 465920K, used 356612K [0x0000000795580000, 0x00000007c0000000, 0x00000007c0000000)
  eden space 232960K, 94% used [0x0000000795580000,0x00000007a2bfdc10,0x00000007a3900000)
  from space 232960K, 58% used [0x00000007a3900000,0x00000007abec35a0,0x00000007b1c80000)
  to   space 232960K, 0% used [0x00000007b1c80000,0x00000007b1c80000,0x00000007c0000000)
 ParOldGen       total 1398272K, used 627940K [0x0000000740000000, 0x0000000795580000, 0x0000000795580000)
  object space 1398272K, 44% used [0x0000000740000000,0x0000000766539338,0x0000000795580000)
 Metaspace       used 2712K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 295K, capacity 386K, committed 512K, reserved 1048576K
```

## cms

`java -XX:+UseConcMarkSweepGC -Xms2G -Xmx2G -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis`

日志：

```
2020-10-27T01:20:24.522-0800: [GC (Allocation Failure) 2020-10-27T01:20:24.522-0800: [ParNew: 559232K->69887K(629120K), 0.0555254 secs] 559232K->145150K(2027264K), 0.0555664 secs] [Times: user=0.08 sys=0.34, real=0.05 secs] 
2020-10-27T01:20:24.650-0800: [GC (Allocation Failure) 2020-10-27T01:20:24.650-0800: [ParNew: 629119K->69888K(629120K), 0.0532057 secs] 704382K->262081K(2027264K), 0.0532400 secs] [Times: user=0.11 sys=0.32, real=0.05 secs] 
2020-10-27T01:20:24.763-0800: [GC (Allocation Failure) 2020-10-27T01:20:24.763-0800: [ParNew: 629120K->69888K(629120K), 0.0637946 secs] 821313K->385272K(2027264K), 0.0638276 secs] [Times: user=0.60 sys=0.04, real=0.06 secs] 
2020-10-27T01:20:24.894-0800: [GC (Allocation Failure) 2020-10-27T01:20:24.894-0800: [ParNew: 629120K->69887K(629120K), 0.0617871 secs] 944504K->501486K(2027264K), 0.0618190 secs] [Times: user=0.58 sys=0.03, real=0.06 secs] 
2020-10-27T01:20:25.016-0800: [GC (Allocation Failure) 2020-10-27T01:20:25.016-0800: [ParNew: 629119K->69887K(629120K), 0.0673803 secs] 1060718K->631118K(2027264K), 0.0674151 secs] [Times: user=0.63 sys=0.04, real=0.07 secs] 
2020-10-27T01:20:25.145-0800: [GC (Allocation Failure) 2020-10-27T01:20:25.145-0800: [ParNew: 629119K->69887K(629120K), 0.0683282 secs] 1190350K->749736K(2027264K), 0.0683617 secs] [Times: user=0.62 sys=0.04, real=0.07 secs] 
执行结束!共生成对象次数:14238
Heap
 par new generation   total 629120K, used 503472K [0x0000000740000000, 0x000000076aaa0000, 0x000000076aaa0000)
  eden space 559232K,  77% used [0x0000000740000000, 0x000000075a76c4e8, 0x0000000762220000)
  from space 69888K,  99% used [0x0000000762220000, 0x000000076665ff08, 0x0000000766660000)
  to   space 69888K,   0% used [0x0000000766660000, 0x0000000766660000, 0x000000076aaa0000)
 concurrent mark-sweep generation total 1398144K, used 679849K [0x000000076aaa0000, 0x00000007c0000000, 0x00000007c0000000)
 Metaspace       used 2712K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 295K, capacity 386K, committed 512K, reserved 1048576K
```

## g1

`java -XX:+UseG1GC -Xms2G -Xmx2G -XX:+PrintGC -XX:+PrintGCDateStamps GCLogAnalysis`

日志:

```
2020-10-27T01:23:09.969-0800: [GC pause (G1 Evacuation Pause) (young) 131M->42M(2048M), 0.0105633 secs]
2020-10-27T01:23:10.007-0800: [GC pause (G1 Evacuation Pause) (young) 154M->75M(2048M), 0.0099231 secs]
2020-10-27T01:23:10.037-0800: [GC pause (G1 Evacuation Pause) (young) 184M->105M(2048M), 0.0103628 secs]
2020-10-27T01:23:10.067-0800: [GC pause (G1 Evacuation Pause) (young) 218M->144M(2048M), 0.0124554 secs]
2020-10-27T01:23:10.097-0800: [GC pause (G1 Evacuation Pause) (young) 252M->175M(2048M), 0.0111743 secs]
2020-10-27T01:23:10.143-0800: [GC pause (G1 Evacuation Pause) (young) 333M->223M(2048M), 0.0211741 secs]
2020-10-27T01:23:10.190-0800: [GC pause (G1 Evacuation Pause) (young) 380M->264M(2048M), 0.0161812 secs]
2020-10-27T01:23:10.300-0800: [GC pause (G1 Evacuation Pause) (young) 578M->354M(2048M), 0.0279082 secs]
2020-10-27T01:23:10.357-0800: [GC pause (G1 Evacuation Pause) (young) 577M->413M(2048M), 0.0199509 secs]
2020-10-27T01:23:10.740-0800: [GC pause (G1 Evacuation Pause) (young) 1456M->589M(2048M), 0.0649671 secs]
2020-10-27T01:23:10.835-0800: [GC pause (G1 Evacuation Pause) (young) 787M->619M(2048M), 0.0154945 secs]
执行结束!共生成对象次数:10746
```

# 2G 下各个垃圾回收器的gc日志

## 串行

`java -XX:+UseSerialGC -Xms8G -Xmx8G -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis`

日志: 

```
执行结束!共生成对象次数:7519
Heap
 def new generation   total 2516544K, used 2114963K [0x00000005c0000000, 0x000000066aaa0000, 0x000000066aaa0000)
  eden space 2236928K,  94% used [0x00000005c0000000, 0x0000000641164ea8, 0x0000000648880000)
  from space 279616K,   0% used [0x0000000648880000, 0x0000000648880000, 0x0000000659990000)
  to   space 279616K,   0% used [0x0000000659990000, 0x0000000659990000, 0x000000066aaa0000)
 tenured generation   total 5592448K, used 0K [0x000000066aaa0000, 0x00000007c0000000, 0x00000007c0000000)
   the space 5592448K,   0% used [0x000000066aaa0000, 0x000000066aaa0000, 0x000000066aaa0200, 0x00000007c0000000)
 Metaspace       used 2712K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 295K, capacity 386K, committed 512K, reserved 1048576K

```

## 并行

`java -Xms8G -Xmx8G -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis`

日志:

```
2020-10-27T01:27:02.901-0800: [GC (Allocation Failure) [PSYoungGen: 2097664K->318296K(2446848K)] 2097664K->318304K(8039424K), 0.1208252 secs] [Times: user=0.13 sys=0.78, real=0.12 secs] 
执行结束!共生成对象次数:7947
Heap
 PSYoungGen      total 2446848K, used 381452K [0x0000000715580000, 0x00000007c0000000, 0x00000007c0000000)
  eden space 2097664K, 3% used [0x0000000715580000,0x000000071932cf48,0x0000000795600000)
  from space 349184K, 91% used [0x0000000795600000,0x00000007a8cd6300,0x00000007aab00000)
  to   space 349184K, 0% used [0x00000007aab00000,0x00000007aab00000,0x00000007c0000000)
 ParOldGen       total 5592576K, used 8K [0x00000005c0000000, 0x0000000715580000, 0x0000000715580000)
  object space 5592576K, 0% used [0x00000005c0000000,0x00000005c0002000,0x0000000715580000)
 Metaspace       used 2712K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 295K, capacity 386K, committed 512K, reserved 1048576K

```

## cms

`java -XX:+UseConcMarkSweepGC -Xms8G -Xmx8G -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis`

日志：

```
2020-10-27T01:27:46.380-0800: [GC (Allocation Failure) 2020-10-27T01:27:46.380-0800: [ParNew: 681600K->85183K(766784K), 0.0797175 secs] 681600K->191394K(8303424K), 0.0797633 secs] [Times: user=0.20 sys=0.43, real=0.08 secs] 
2020-10-27T01:27:46.541-0800: [GC (Allocation Failure) 2020-10-27T01:27:46.541-0800: [ParNew: 766783K->85184K(766784K), 0.0727982 secs] 872994K->323961K(8303424K), 0.0728324 secs] [Times: user=0.20 sys=0.40, real=0.07 secs] 
2020-10-27T01:27:46.688-0800: [GC (Allocation Failure) 2020-10-27T01:27:46.688-0800: [ParNew: 766784K->85182K(766784K), 0.0839767 secs] 1005561K->458265K(8303424K), 0.0840080 secs] [Times: user=0.79 sys=0.05, real=0.09 secs] 
2020-10-27T01:27:46.849-0800: [GC (Allocation Failure) 2020-10-27T01:27:46.849-0800: [ParNew: 766782K->85182K(766784K), 0.0791311 secs] 1139865K->589022K(8303424K), 0.0791634 secs] [Times: user=0.74 sys=0.04, real=0.08 secs] 
2020-10-27T01:27:47.025-0800: [GC (Allocation Failure) 2020-10-27T01:27:47.025-0800: [ParNew: 766782K->85183K(766784K), 0.0789505 secs] 1270622K->721634K(8303424K), 0.0789849 secs] [Times: user=0.74 sys=0.04, real=0.08 secs] 
执行结束!共生成对象次数:12864
Heap
 par new generation   total 766784K, used 112642K [0x00000005c0000000, 0x00000005f4000000, 0x00000005f4000000)
  eden space 681600K,   4% used [0x00000005c0000000, 0x00000005c1ad0d48, 0x00000005e99a0000)
  from space 85184K,  99% used [0x00000005eecd0000, 0x00000005f3fffd80, 0x00000005f4000000)
  to   space 85184K,   0% used [0x00000005e99a0000, 0x00000005e99a0000, 0x00000005eecd0000)
 concurrent mark-sweep generation total 7536640K, used 636451K [0x00000005f4000000, 0x00000007c0000000, 0x00000007c0000000)
 Metaspace       used 2712K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 295K, capacity 386K, committed 512K, reserved 1048576K

```

## g1

`java -XX:+UseG1GC -Xms8G -Xmx8G -XX:+PrintGC -XX:+PrintGCDateStamps GCLogAnalysis`

日志:

```
2020-10-27T01:24:30.280-0800: [GC pause (G1 Evacuation Pause) (young) 408M->132M(8192M), 0.0484497 secs]
2020-10-27T01:24:30.388-0800: [GC pause (G1 Evacuation Pause) (young) 488M->217M(8192M), 0.0325504 secs]
2020-10-27T01:24:30.467-0800: [GC pause (G1 Evacuation Pause) (young) 573M->307M(8192M), 0.0393888 secs]
2020-10-27T01:24:30.548-0800: [GC pause (G1 Evacuation Pause) (young) 663M->408M(8192M), 0.0425433 secs]
2020-10-27T01:24:30.642-0800: [GC pause (G1 Evacuation Pause) (young) 764M->500M(8192M), 0.0379947 secs]
2020-10-27T01:24:30.722-0800: [GC pause (G1 Evacuation Pause) (young) 856M->601M(8192M), 0.0414938 secs]
2020-10-27T01:24:30.806-0800: [GC pause (G1 Evacuation Pause) (young) 957M->701M(8192M), 0.0404006 secs]
2020-10-27T01:24:30.890-0800: [GC pause (G1 Evacuation Pause) (young) 1057M->799M(8192M), 0.0380363 secs]
2020-10-27T01:24:30.971-0800: [GC pause (G1 Evacuation Pause) (young) 1155M->896M(8192M), 0.0398521 secs]
2020-10-27T01:24:31.052-0800: [GC pause (G1 Evacuation Pause) (young) 1252M->997M(8192M), 0.0396097 secs]
执行结束!共生成对象次数:13876
```