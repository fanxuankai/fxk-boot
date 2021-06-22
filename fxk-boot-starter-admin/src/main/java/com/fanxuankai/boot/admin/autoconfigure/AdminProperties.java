package com.fanxuankai.boot.admin.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @author fanxuankai
 */
@ConfigurationProperties(prefix = "fxk.admin")
public class AdminProperties {
    @NestedConfigurationProperty
    private Rsa rsa;

    public Rsa getRsa() {
        return rsa;
    }

    public void setRsa(Rsa rsa) {
        this.rsa = rsa;
    }

    public static class Rsa {
        private String privateKey;
        private String publicKey;

        public String getPrivateKey() {
            return privateKey;
        }

        public void setPrivateKey(String privateKey) {
            this.privateKey = privateKey;
        }

        public String getPublicKey() {
            return publicKey;
        }

        public void setPublicKey(String publicKey) {
            this.publicKey = publicKey;
        }
    }
}
