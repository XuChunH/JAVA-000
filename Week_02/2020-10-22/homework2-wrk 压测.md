压测命令: `wrk -t12 -c400 -d30s http://localhost:8088/api/hello`

1. 首先可以看到的是串行gc的表现是最差的
2. 在 2G 的情况下, 并行和CMS 相近, 单是 G1 较差
3. 在 8G 的情况下, G1 的表现更好, 而并行要优于CMS

# 压测数据

## 2G

### 串行

```
Running 30s test @ http://localhost:8088/api/hello
  12 threads and 400 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    16.75ms   43.54ms 598.83ms   95.79%
    Req/Sec     3.46k     1.16k   16.05k    75.56%
  1216152 requests in 30.10s, 145.19MB read
  Socket errors: connect 0, read 441, write 0, timeout 0
Requests/sec:  40401.51
Transfer/sec:      4.82MB
```

### 并行

```
Running 30s test @ http://localhost:8088/api/hello
  12 threads and 400 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    13.79ms   25.62ms 449.15ms   93.92%
    Req/Sec     3.44k     1.22k   20.18k    76.60%
  1223708 requests in 30.10s, 146.10MB read
  Socket errors: connect 0, read 516, write 0, timeout 0
Requests/sec:  40660.07
Transfer/sec:      4.85MB
```

### CMS

```
Running 30s test @ http://localhost:8088/api/hello
  12 threads and 400 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    16.05ms   34.44ms 834.90ms   94.03%
    Req/Sec     3.46k     1.22k    8.84k    71.48%
  1232903 requests in 30.08s, 147.19MB read
  Socket errors: connect 0, read 401, write 0, timeout 0
Requests/sec:  40983.81
Transfer/sec:      4.89MB
```

### G1

```
Running 30s test @ http://localhost:8088/api/hello
  12 threads and 400 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    21.99ms   41.37ms 718.99ms   91.45%
    Req/Sec     2.93k     1.25k   12.45k    56.98%
  1046556 requests in 30.07s, 124.95MB read
  Socket errors: connect 0, read 341, write 0, timeout 0
Requests/sec:  34802.43
Transfer/sec:      4.15MB

```

## 8G 下的数据

### 串行

```
Running 30s test @ http://localhost:8088/api/hello
  12 threads and 400 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    25.97ms   49.63ms 779.09ms   91.02%
    Req/Sec     2.54k     1.11k    8.57k    61.51%
  906487 requests in 30.09s, 108.22MB read
  Socket errors: connect 0, read 422, write 0, timeout 0
Requests/sec:  30127.15
Transfer/sec:      3.60MB
```

### 并行

```
Running 30s test @ http://localhost:8088/api/hello
  12 threads and 400 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    19.89ms   37.59ms 687.93ms   92.52%
    Req/Sec     2.78k     1.17k    8.76k    61.41%
  997208 requests in 30.10s, 119.05MB read
  Socket errors: connect 0, read 413, write 0, timeout 0
Requests/sec:  33132.40
Transfer/sec:      3.96MB

```

### CMS

```
Running 30s test @ http://localhost:8088/api/hello
  12 threads and 400 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    22.72ms   41.69ms 740.23ms   91.60%
    Req/Sec     2.60k     1.08k    8.95k    61.14%
  931935 requests in 30.09s, 111.26MB read
  Socket errors: connect 0, read 339, write 0, timeout 0
Requests/sec:  30974.34
Transfer/sec:      3.70MB
```

### G1

```
Running 30s test @ http://localhost:8088/api/hello
  12 threads and 400 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    23.45ms   54.26ms 650.18ms   92.88%
    Req/Sec     3.16k     1.28k    5.77k    63.73%
  1110952 requests in 30.04s, 132.63MB read
  Socket errors: connect 0, read 360, write 0, timeout 0
Requests/sec:  36977.76
Transfer/sec:      4.41MB

```