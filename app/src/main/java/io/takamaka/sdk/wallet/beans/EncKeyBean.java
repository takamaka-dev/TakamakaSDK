package io.takamaka.sdk.wallet.beans;

import io.takamaka.sdk.globalContext.KeyContexts;

public class EncKeyBean {

    private String version = KeyContexts.WALLET_CURRENT_VERSION;
    private String algorithm;
    private byte[][] wallet;

    public EncKeyBean(String algorithm, byte[][] wallet) {
        this.algorithm = algorithm;
        this.wallet = wallet;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public byte[][] getWallet() {
        return wallet;
    }

    public void setWallet(byte[][] wallet) {
        this.wallet = wallet;
    }
}
