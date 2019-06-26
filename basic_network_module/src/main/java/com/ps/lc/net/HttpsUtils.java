package com.ps.lc.net;

import android.text.TextUtils;
import android.util.Log;

import com.ps.lc.net.certificate.HttpsCertificate;
import com.ps.lc.net.certificate.HttpsHostnameVerifier;
import com.ps.lc.net.exception.ApiException;
import com.ps.lc.net.exception.HttpException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * Created by zhangwulin on 2016/12/8.
 * Email:zhangwulin@feitaikeji.com
 * https工具类
 */

public class HttpsUtils {
    private HttpsUtils() {
    }

    /**
     * 配置okhttp 使用https
     * 双向验证,这里不允许没证书，没证书就没意义了
     */
    public static void setHttps(OkHttpClient.Builder builder, HttpsCertificate ppCertificate) throws ApiException {
        Buffer buffer = null;
        if (null == ppCertificate) {
            throw getHttpsValidateException();
        }
        try {
            buffer = new Buffer();
            TrustManager[] trustManagers = prepareTrustManager(null == ppCertificate.getSCertificate() ? null :
                    ppCertificate.getContext().getAssets().open(ppCertificate.getSCertificate()), ppCertificate.getSPwd());
            Utils.closeQuietly(buffer);
            if (null == trustManagers || 1 != trustManagers.length || !(trustManagers[0] instanceof X509TrustManager)) {
                throw getHttpsValidateException();
            }
            X509TrustManager trustManager = (X509TrustManager) trustManagers[0];
            buffer = new Buffer();
            KeyManager[] keyManagers = null;
            if (!TextUtils.isEmpty(ppCertificate.getCCertificate())) {
                keyManagers = prepareKeyManager(ppCertificate.getContext().getAssets().open(ppCertificate.getCCertificate()), ppCertificate.getCPwd());
                Utils.closeQuietly(buffer);
                if (null == keyManagers) {
                    throw getHttpsValidateException();
                }
            }
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagers, trustManagers, null);
            builder.sslSocketFactory(sslContext.getSocketFactory(), trustManager);
            builder.hostnameVerifier(new HttpsHostnameVerifier());
        } catch (NoSuchAlgorithmException | KeyManagementException | IOException e) {
            throw getHttpsValidateException();
        } finally {
            Utils.closeQuietly(buffer);
        }
    }

    public static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory sSLSocketFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new X509TrustManager() {

                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    // unused
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    // unused
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }}, new SecureRandom());
            sSLSocketFactory = sc.getSocketFactory();
        } catch (Exception e) {
            Log.getStackTraceString(e);
        }

        return sSLSocketFactory;
    }

    /**
     * 获取https验证异常
     *
     * @return 验证异常
     */
    private static HttpException getHttpsValidateException() {
        return NetworkCode.getHttpException(NetworkCode.EXCEPTION_10000);
    }


    /**
     * 获取信任管理器 X.509格式
     */
    private static TrustManager[] prepareTrustManager(InputStream sCertificate, String sPwd) {
        try {
            TrustManagerFactory trustManagerFactory;
            if (null != sCertificate) {
                KeyStore keyStore = KeyStore.getInstance("BKS");
                if (null == sPwd || "".equals(sPwd.trim())) {
                    keyStore.load(null, null);
                } else {
                    keyStore.load(sCertificate, sPwd.toCharArray());
                }
                trustManagerFactory = TrustManagerFactory.getInstance("X509");
                trustManagerFactory.init(keyStore);
            } else {
                trustManagerFactory = TrustManagerFactory.getInstance(
                        TrustManagerFactory.getDefaultAlgorithm());
                trustManagerFactory.init((KeyStore) null);
            }

            return trustManagerFactory.getTrustManagers();
        } catch (NoSuchAlgorithmException | CertificateException | KeyStoreException | IOException e) {
            Log.getStackTraceString(e);
        } finally {
            Utils.closeQuietly(sCertificate);
        }
        return null;

    }


    /**
     * 获取密钥管理器
     */
    private static KeyManager[] prepareKeyManager(InputStream cCertificate, String cPwd) {
        try {
            if (cCertificate == null || cPwd == null) {
                return null;
            }

            KeyStore clientKeyStore = KeyStore.getInstance("BKS");
            clientKeyStore.load(cCertificate, cPwd.toCharArray());
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("X509");
//            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(clientKeyStore, cPwd.toCharArray());
            return keyManagerFactory.getKeyManagers();

        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException | CertificateException | IOException e) {
            Log.getStackTraceString(e);
        }
        return null;
    }


    /**
     * 写入文件
     *
     * @param responseBody
     * @param file
     * @throws IOException
     */
    public static void writeFile(ResponseBody responseBody, File file) throws IOException {
        if (null != file.getParentFile() && !file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        long allLength = responseBody.contentLength();
        RandomAccessFile randomAccessFile = null;
        FileChannel channelOut = null;
        InputStream is = null;
        try {
            randomAccessFile = new RandomAccessFile(file, "rwd");
            channelOut = randomAccessFile.getChannel();
            MappedByteBuffer mappedBuffer = channelOut.map(FileChannel.MapMode.READ_WRITE, 0, allLength);
            byte[] buffer = new byte[1024 * 8];
            is = responseBody.byteStream();
            int len;
            while ((len = is.read(buffer)) != -1) {
                mappedBuffer.put(buffer, 0, len);
            }
        } finally {
            Utils.closeQuietly(is);
            Utils.closeQuietly(channelOut);
            Utils.closeQuietly(randomAccessFile);
        }
    }


}
