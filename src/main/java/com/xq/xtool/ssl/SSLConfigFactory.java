package com.xq.xtool.ssl;

import java.io.InputStream;

/**
 * Description: ssl配置的工厂类，统一调用入口
 *
 * @author 13797
 * @version v0.0.1
 * 2021/11/3 21:41
 */
public class SSLConfigFactory {
    public static SSLConfig ignoreCertificate() {
        return new IgnoreCertificate();
    }

    public static SSLConfig trustCertificate(InputStream cerInputStream) {
        return new TrustCertificate(cerInputStream);
    }
}
