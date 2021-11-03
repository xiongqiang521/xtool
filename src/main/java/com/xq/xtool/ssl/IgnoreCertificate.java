package com.xq.xtool.ssl;

import javax.net.ssl.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class IgnoreCertificate implements SSLConfig {

    public HostnameVerifier getHostnameVerifier() {
        return (s, sslSession) -> true;
    }

    public X509TrustManager getX509TrustManager() throws Exception {
        return new IgnoreTrustManager();
    }

    public SSLSocketFactory getSSLSocketFactory() throws Exception {
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, new TrustManager[]{new IgnoreTrustManager()}, null);
        return sslContext.getSocketFactory();
    }

    private static class IgnoreTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

}