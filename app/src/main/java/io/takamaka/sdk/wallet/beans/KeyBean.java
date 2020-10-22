package io.takamaka.sdk.wallet.beans;


import io.takamaka.sdk.globalContext.KeyContexts;

/**
 *
 * @author francesco.pasetto@takamaka.io
 */
public class KeyBean {

    private String version=KeyContexts.WALLET_CURRENT_VERSION;
    private String algorithm;
    private KeyContexts.WalletCypher cypher;
    private String seed;
    private String words;

    public KeyBean(String algorithm, KeyContexts.WalletCypher cypher, String seed, String words) {
        this.algorithm = algorithm;
        this.cypher = cypher;
        this.seed = seed;
        this.words = words;
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

    public KeyContexts.WalletCypher getCypher() {
        return cypher;
    }

    public void setCypher(KeyContexts.WalletCypher cypher) {
        this.cypher = cypher;
    }

    public String getSeed() {
        return seed;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

}
