package com.bc.rm.server.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import static java.lang.System.out;

/**
 * 描述 http请求处理模块
 *
 * @author dd
 * @version 1.0
 * 日期：2019年11月18日
 */
@Configuration
@Slf4j
public class HttpHelper {
    private static RequestConfig requestConfig;

    // get 请求
    public static String httpGet(String url, Header[] headers) throws Exception {
        HttpUriRequest uriRequest = new HttpGet(url);
        if (null != headers) {
            uriRequest.setHeaders(headers);
        }

        CloseableHttpClient httpClient = null;
        try {
            httpClient = declareHttpClientSSL(url);
            CloseableHttpResponse httpresponse = httpClient.execute(uriRequest);
            HttpEntity httpEntity = httpresponse.getEntity();
            String result = EntityUtils.toString(httpEntity, REQ_ENCODEING_UTF8);
            return result;
        } catch (ClientProtocolException e) {
            out.println(String.format("http请求失败，uri{%s},exception{%s}", new Object[]{url, e}));
        } catch (IOException e) {
            out.println(String.format("IO Exception，uri{%s},exception{%s}", new Object[]{url, e}));
        } finally {
            if (null != httpClient) {
                httpClient.close();
            }
        }
        return null;
    }

    // post 请求
    public static String httpPost(String url, Header[] headers, String params) throws Exception {
        HttpPost post = new HttpPost(url);
        if (null != headers) {
            post.setHeaders(headers);
        }
        post.setEntity(new StringEntity(params, Charset.forName("utf-8")));
        HttpResponse httpresponse = null;
        CloseableHttpClient httpClient = null;
        try {
            httpClient = declareHttpClientSSL(url);
            httpresponse = httpClient.execute(post);
            HttpEntity httpEntity = httpresponse.getEntity();
            String result = EntityUtils.toString(httpEntity, REQ_ENCODEING_UTF8);
            return result;
        } catch (ClientProtocolException e) {
            out.println(String.format("http请求失败，uri{%s},exception{%s}", new Object[]{url, e}));
        } catch (IOException e) {
            out.println(String.format("IO Exception，uri{%s},exception{%s}", new Object[]{url, e}));
        } finally {
            if (null != httpClient) {
                httpClient.close();
            }
        }
        return null;
    }


    // put 请求
    public static String httpPut(String url, Header[] headers, String params) throws Exception {
        HttpPut put = new HttpPut(url);
        if (null != headers) {
            put.setHeaders(headers);
        }
        // 设置传输编码格式
        if (!StringUtils.isEmpty(params)){
            put.setEntity(new StringEntity(params, Charset.forName("utf-8")));
        }
        HttpResponse httpresponse = null;
        CloseableHttpClient httpClient = null;
        try {
            httpClient = declareHttpClientSSL(url);
            httpresponse = httpClient.execute(put);
            HttpEntity httpEntity = httpresponse.getEntity();
            String result = EntityUtils.toString(httpEntity, REQ_ENCODEING_UTF8);
            return result;
        } catch (ClientProtocolException e) {
            out.println(String.format("http请求失败，uri{%s},exception{%s}", new Object[]{url, e}));
        } catch (IOException e) {
            out.println(String.format("IO Exception，uri{%s},exception{%s}", new Object[]{url, e}));
        } finally {
            if (null != httpClient) {
                httpClient.close();
            }
        }
        return null;
    }

    // put 请求
    public static String httpPutByte2(String url, byte[] filebytes, Header[] headers) throws IOException {
        HttpPut put = new HttpPut(url);
        if (null != headers) {
            put.setHeaders(headers);
        }
        // 设置传输编码格式
        if (filebytes != null) {
            put.setEntity(new ByteArrayEntity(filebytes));
        }
        HttpResponse httpresponse = null;
        CloseableHttpClient httpClient = null;
        try {
            httpClient = declareHttpClientSSL(url);
            httpresponse = httpClient.execute(put);
            HttpEntity httpEntity = httpresponse.getEntity();
            String result = EntityUtils.toString(httpEntity, REQ_ENCODEING_UTF8);
            return result;
        } catch (ClientProtocolException e) {
            out.println(String.format("http请求失败，uri{%s},exception{%s}", new Object[]{url, e}));
        } catch (IOException e) {
            out.println(String.format("IO Exception，uri{%s},exception{%s}", new Object[]{url, e}));
        } finally {
            if (null != httpClient) {
                httpClient.close();
            }
        }
        return null;
    }


   /* //put 放入字节流方法
    public static String httpPutByte(String url, byte[] filebytes, Header[] headers) throws IOException {
        HttpResponse httpresponse = null;
        CloseableHttpClient httpClient = null;
        HttpRequestMethedEnum requestMethod = HttpRequestMethedEnum.HttpPut;
        HttpRequestBase request = requestMethod.createRequest(url);
        request.setConfig(requestConfig);
        if (filebytes != null) {
            ((HttpEntityEnclosingRequest) request).setEntity(new ByteArrayEntity(filebytes));
        }
        //放入header
        int length = headers.length;
        for (int i = 0; i < length; i++) {
            request.setHeader(headers[i].getName(), headers[i].getValue());
        }
        // 设置传输编码格式
        StringEntity stringEntity = new StringEntity("", REQ_ENCODEING_UTF8);
        stringEntity.setContentEncoding(REQ_ENCODEING_UTF8);
        ((HttpEntityEnclosingRequest) request).setEntity(stringEntity);
        try {
            httpClient = declareHttpClientSSL(url);
            httpresponse = httpClient.execute(request);
            HttpEntity httpEntity = httpresponse.getEntity();
            String result = EntityUtils.toString(httpEntity, REQ_ENCODEING_UTF8);
            return result;
        } catch (ClientProtocolException e) {
            out.println(String.format("http请求失败，uri{%s},exception{%s}", new Object[]{url, e}));
        } catch (IOException e) {
            out.println(String.format("IO Exception，uri{%s},exception{%s}", new Object[]{url, e}));
        } finally {
            if (null != httpClient) {
                httpClient.close();
            }
        }
        return url;
    }*/

    private static CloseableHttpClient declareHttpClientSSL(String url) {
        if (url.startsWith("https://")) {
            return sslClient();
        } else {
            return HttpClientBuilder.create().setConnectionManager(httpClientConnectionManager).build();
        }
    }

    /**
     * 设置SSL请求处理
     *
     * @param
     */
    private static CloseableHttpClient sslClient() {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkClientTrusted(X509Certificate[] xcs, String str) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] xcs, String str) {
                }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLConnectionSocketFactory sslConnectionSocketFactory = SSLConnectionSocketFactory.getSocketFactory();
            return HttpClients.custom().setSSLSocketFactory(sslConnectionSocketFactory).build();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (KeyManagementException e) {
            throw new RuntimeException(e);
        }
    }

    // this is config
    private static final String REQ_ENCODEING_UTF8 = "utf-8";
    private static PoolingHttpClientConnectionManager httpClientConnectionManager;

    public HttpHelper() {
        httpClientConnectionManager = new PoolingHttpClientConnectionManager();
        httpClientConnectionManager.setMaxTotal(100);
        httpClientConnectionManager.setDefaultMaxPerRoute(20);
    }

    // get 请求
    public static String httpGet(String url) throws Exception {
        return httpGet(url, null);
    }


}
