package com.xq.xtool.ssl;

import javax.net.ssl.*;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

public class TrustCertificate implements SSLConfig {

    private final InputStream inputStream;
    private final Certificate certificate;

    public TrustCertificate(InputStream cerInputStream) {
        this.inputStream = cerInputStream;
        this.certificate = makeCertificate();
    }

    public HostnameVerifier getHostnameVerifier() {
        return (s, sslSession) -> true;
    }

    private Certificate makeCertificate() {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            return certificateFactory.generateCertificate(this.inputStream);
        } catch (CertificateException e) {
            throw new RuntimeException(e);
        }
    }

    private KeyStore getKeyStore() throws Exception {
        final KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null);
        keyStore.setCertificateEntry("cer", this.certificate);
        return keyStore;
    }

    private KeyManagerFactory getKeyManagerFactory() throws Exception {
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(this.getKeyStore(), null);
        return keyManagerFactory;
    }

    private TrustManagerFactory getTrustManagerFactory() throws Exception {

        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(this.getKeyStore());
        return trustManagerFactory;
    }

    public X509TrustManager getX509TrustManager() throws Exception {
        TrustManager[] trustManagers = this.getTrustManagerFactory().getTrustManagers();
        return (X509TrustManager) trustManagers[0];
    }

    public SSLSocketFactory getSSLSocketFactory() throws Exception {
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(
                this.getKeyManagerFactory().getKeyManagers(),
                this.getTrustManagerFactory().getTrustManagers(),
                new SecureRandom());
        return sslContext.getSocketFactory();
    }

}