package io.takamaka.sdk.wallet;

import io.takamaka.sdk.globalContext.KeyContexts;
import io.takamaka.sdk.transactions.InternalTransactionBean;
import io.takamaka.sdk.transactions.TransactionBean;
import io.takamaka.sdk.utils.threadSafeUtils.TkmTextUtils;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author giovanni.antino@h2tcoin.com
 */
public class TransactionBox implements Serializable, Comparable<TransactionBox> {

    private String singleInclusionTransactionHash;
    /**
     * json(GSon) of the TransactionBean
     */
    private String transactionJson;
    private TransactionBean tb;
    private InternalTransactionBean itb;
    /**
     * indicates if the transaction is valid and thus valid
     */
    private boolean valid;

    public String getSingleInclusionTransactionHash() {
        return singleInclusionTransactionHash;
    }

    protected void setSingleInclusionTransactionHash(String singleInclusionTransactionHash) {
        this.singleInclusionTransactionHash = singleInclusionTransactionHash;
    }

    public String getTransactionJson() {
        return transactionJson;
    }

    protected void setTransactionJson(String transactionJson) {
        this.transactionJson = transactionJson;
    }

    public TransactionBean getTb() {
        return tb;
    }

    protected void setTb(TransactionBean tb) {
        this.tb = tb;
    }

    public InternalTransactionBean getItb() {
        return itb;
    }

    protected void setItb(InternalTransactionBean itb) {
        this.itb = itb;
    }

    public boolean isValid() {
        return valid;
    }

    protected void setValid(boolean valid) {
        this.valid = valid;
    }

    public String to() {
        return getItb().getTo();
    }

    public String from() {
        return getItb().getFrom();
    }

    public String messageInternalTB() {
        return getItb().getMessage();
    }

    public long notBeforeUNIX() {
        return getItb().getNotBefore().getTime();
    }

    public Date notBeforeDate() {
        return getItb().getNotBefore();
    }

    public BigInteger redValue() {
        return getItb().getRedValue();
    }

    public BigInteger greenValue() {
        return getItb().getGreenValue();
    }

    public KeyContexts.TransactionType type() {
        return getItb().getTransactionType();
    }

    public String transactionHash() {
        return getItb().getTransactionHash();
    }

    public Integer epoch() {
        return getItb().getEpoch();
    }

    public Integer slot() {
        return getItb().getSlot();
    }

    public String publicKey() {
        return tb.getPublicKey();
    }

    public String signature() {
        return tb.getSignature();
    }

    public String messageTB() {
        return tb.getMessage();
    }

    public String randomSeed() {
        return tb.getRandomSeed();
    }

    public KeyContexts.WalletCypher walletCypher() {
        return tb.getWalletCypher();
    }

    public String sith() {
        return getSingleInclusionTransactionHash();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.singleInclusionTransactionHash);
        hash = 71 * hash + Objects.hashCode(this.transactionJson);
        hash = 71 * hash + Objects.hashCode(this.tb);
        hash = 71 * hash + Objects.hashCode(this.itb);
        hash = 71 * hash + (this.valid ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TransactionBox other = (TransactionBox) obj;
        if (this.valid != other.valid) {
            return false;
        }
        if (!Objects.equals(this.singleInclusionTransactionHash, other.singleInclusionTransactionHash)) {
            return false;
        }
        if (!Objects.equals(this.transactionJson, other.transactionJson)) {
            return false;
        }
        if (!Objects.equals(this.tb, other.tb)) {
            return false;
        }
        if (!Objects.equals(this.itb, other.itb)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(TransactionBox t) {
        String externalObject = TkmTextUtils.getSortingString(t.getSingleInclusionTransactionHash());
        String internalObject = TkmTextUtils.getSortingString(this.getSingleInclusionTransactionHash());
        return internalObject.compareTo(externalObject);
    }

}
