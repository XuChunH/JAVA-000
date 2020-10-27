import java.util.Objects;
import java.util.StringJoiner;

/**
 * http-client 配置
 */
public class HttpClientConfig {

    /**
     * 设置整个连接池最大连接数 根据自己的场景决定
     */
    private Integer maxTotal = 200;

    /**
     * 路由是对maxTotal的细分
     */
    private Integer defaultMaxPerRoute = 100;

    /**
     * 连接上服务器(握手成功)的时间，超出该时间抛出connect timeout
     */
    private Integer connectTimeout = 1000;

    /**
     * 从连接池中获取连接的超时时间，超过该时间未拿到可用连接
     * 会抛出org.apache.http.conn.ConnectionPoolTimeoutException: Timeout waiting for connection from pool
     */
    private Integer connectionRequestTimeout = 500;

    /**
     * 服务器返回数据(response)的时间，超过该时间抛出read timeout
     */
    private Integer socketTimeout = 10000;

    /**
     * 检查连接过期的间隔（in milliseconds）
     * Determines whether stale connection check is to be used. The stale
     * connection check can cause up to 30 millisecond overhead per request and
     * should be used only when appropriate. For performance critical
     * operations this check should be disabled.
     */
    private Integer validateAfterInactivity = 500;

    public Integer getMaxTotal() {

        return maxTotal;
    }

    public void setMaxTotal(Integer maxTotal) {

        this.maxTotal = maxTotal;
    }

    public Integer getDefaultMaxPerRoute() {

        return defaultMaxPerRoute;
    }

    public void setDefaultMaxPerRoute(Integer defaultMaxPerRoute) {

        this.defaultMaxPerRoute = defaultMaxPerRoute;
    }

    public Integer getConnectTimeout() {

        return connectTimeout;
    }

    public void setConnectTimeout(Integer connectTimeout) {

        this.connectTimeout = connectTimeout;
    }

    public Integer getConnectionRequestTimeout() {

        return connectionRequestTimeout;
    }

    public void setConnectionRequestTimeout(Integer connectionRequestTimeout) {

        this.connectionRequestTimeout = connectionRequestTimeout;
    }

    public Integer getSocketTimeout() {

        return socketTimeout;
    }

    public void setSocketTimeout(Integer socketTimeout) {

        this.socketTimeout = socketTimeout;
    }

    public Integer getValidateAfterInactivity() {

        return validateAfterInactivity;
    }

    public void setValidateAfterInactivity(Integer validateAfterInactivity) {

        this.validateAfterInactivity = validateAfterInactivity;
    }

    @Override
    public String toString() {

        return new StringJoiner(", ", HttpClientConfig.class.getSimpleName() + "[", "]").add("maxTotal=" + maxTotal)
            .add("defaultMaxPerRoute=" + defaultMaxPerRoute)
            .add("connectTimeout=" + connectTimeout)
            .add("connectionRequestTimeout=" + connectionRequestTimeout)
            .add("socketTimeout=" + socketTimeout)
            .add("validateAfterInactivity=" + validateAfterInactivity)
            .toString();
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HttpClientConfig that = (HttpClientConfig)o;
        return Objects.equals(maxTotal, that.maxTotal) && Objects.equals(defaultMaxPerRoute, that.defaultMaxPerRoute) && Objects.equals(
            connectTimeout, that.connectTimeout) && Objects.equals(connectionRequestTimeout, that.connectionRequestTimeout) && Objects.equals(
            socketTimeout, that.socketTimeout) && Objects.equals(validateAfterInactivity, that.validateAfterInactivity);
    }

    @Override
    public int hashCode() {

        return Objects.hash(maxTotal, defaultMaxPerRoute, connectTimeout, connectionRequestTimeout, socketTimeout, validateAfterInactivity);
    }
}
