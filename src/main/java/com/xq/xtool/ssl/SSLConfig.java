package com.xq.xtool.ssl;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

/**
 * Description: 配置类的接口，统一管理，方便调用
 *
 * @author 13797
 * @version v0.0.1
 * 2021/11/3 21:38
 */
public interface SSLConfig {
    public HostnameVerifier getHostnameVerifier() throws Exception;
    public SSLSocketFactory getSSLSocketFactory() throws Exception;
    public X509TrustManager getX509TrustManager() throws Exception;
}
