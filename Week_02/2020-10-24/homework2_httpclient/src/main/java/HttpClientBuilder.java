import org.apache.commons.codec.Charsets;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author yehui
 * @date 2020/10/28
 */
public class HttpClientBuilder {

    /**
     * 构造 httpClient
     *
     * @return
     */
    public static CloseableHttpClient buildHttpClient() {
        // 传递trace
        org.apache.http.impl.client.HttpClientBuilder apacheHttpClientBuilder = org.apache.http.impl.client.HttpClientBuilder.create();
        // ssl factory
        LayeredConnectionSocketFactory sslSocketFactory = SSLConnectionSocketFactory.getSocketFactory();

        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
            .register("http", PlainConnectionSocketFactory.getSocketFactory())
            .register("https", sslSocketFactory)
            .build();

        // 连接池属性, 给个默认的配置
        HttpClientConfig httpClientConfig = new HttpClientConfig();

        // 使用池化, 如果每次新建一个 httpclient, 会重新建 socket 连接, 性能差
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        // 最大连接数
        connectionManager.setMaxTotal(httpClientConfig.getMaxTotal());
        // 每个路由的默认最大连接数
        connectionManager.setDefaultMaxPerRoute(httpClientConfig.getDefaultMaxPerRoute());
        // 检查连接是否stale的间隔
        connectionManager.setValidateAfterInactivity(httpClientConfig.getValidateAfterInactivity());


        // 默认请求配置
        final RequestConfig.Builder requestConfigBuilder = RequestConfig.custom()
            .setSocketTimeout(httpClientConfig.getSocketTimeout())
            .setConnectTimeout(httpClientConfig.getConnectTimeout())
            .setConnectionRequestTimeout(httpClientConfig.getConnectionRequestTimeout());

        RequestConfig requestConfig = requestConfigBuilder.build();
        final ConnectionConfig connectionConfig = ConnectionConfig.custom()
            // 默认使用UTF-8
            .setCharset(Charsets.UTF_8)
            .build();

        return apacheHttpClientBuilder.evictExpiredConnections()
            // 默认禁止重试
            .disableAutomaticRetries()
            .setDefaultConnectionConfig(connectionConfig)
            .setDefaultRequestConfig(requestConfig)
            .setConnectionManager(connectionManager)
            .build();
    }

    public static void main(String[] args) throws IOException {

        final CloseableHttpClient httpClient = buildHttpClient();

        final HttpGet httpGet = new HttpGet("http://localhost:8801");
        final CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        final HttpEntity entity = httpResponse.getEntity();
        System.out.println("结果: " + EntityUtils.toString(entity, Charsets.UTF_8));

    }

}
