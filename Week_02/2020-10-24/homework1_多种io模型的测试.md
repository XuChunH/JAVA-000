# 实验与结果分析

老师提供的例子需要设置 `Content-Length`， 否则无法识别为正确的 http 请求.

代码需要加入如下一句：

```
printWriter.println("Content-Length:" + "hello,nio".getBytes().length);
```

测试数据见下一章。

从测试结果来看, netty 的非阻塞模型性能最好，接下来是 单线程和线程池, 最后是阻塞的模型

对于单线程的处理方式, 每次新建一个线程, 代价高昂。而且还会快速的消耗掉内存，触发gc。

对于线程池, 当超过线程池的能力范围之后, 性能也会很糟糕

# 附录-压测数据

## 阻塞

```
➜  nio01 wrk  -c40 -d30s http://127.0.0.1:8801  
Running 30s test @ http://127.0.0.1:8801
  2 threads and 40 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency   885.61ms  159.67ms 954.82ms   92.13%
    Req/Sec     9.56      7.24    30.00     59.62%
  356 requests in 30.09s, 44.34KB read
  Socket errors: connect 0, read 1284, write 6, timeout 0
Requests/sec:     11.83
Transfer/sec:      1.47KB
```

## 一个请求一个线程

```
➜  nio01 wrk  -c40 -d30s http://127.0.0.1:8802
Running 30s test @ http://127.0.0.1:8802
  2 threads and 40 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    26.76ms   14.28ms 105.54ms   94.57%
    Req/Sec    16.56     24.30   237.00     95.03%
  736 requests in 30.06s, 0.86MB read
  Socket errors: connect 0, read 49422, write 24, timeout 0
Requests/sec:     24.48
Transfer/sec:     29.36KB
```

## 线程池

```
➜  nio01 wrk  -c40 -d30s http://127.0.0.1:8803
Running 30s test @ http://127.0.0.1:8803
  2 threads and 40 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    28.17ms   16.24ms  98.73ms   93.21%
    Req/Sec    12.98     18.52   191.00     94.65%
  545 requests in 30.10s, 835.76KB read
  Socket errors: connect 0, read 49173, write 41, timeout 0
Requests/sec:     18.10
Transfer/sec:     27.76KB
```

## netty

```
➜  nio01 wrk  -c40 -d30s http://127.0.0.1:8808/test                    
Running 30s test @ http://127.0.0.1:8808/test
  2 threads and 40 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency   336.64us  784.14us  57.48ms   99.55%
    Req/Sec    63.59k     6.51k   73.60k    87.87%
  3808821 requests in 30.10s, 395.93MB read
Requests/sec: 126535.52
Transfer/sec:     13.15MB

```

