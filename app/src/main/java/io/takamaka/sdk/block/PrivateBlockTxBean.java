package io.takamaka.sdk.block;

import io.takamaka.sdk.globalContext.KeyContexts;
import io.takamaka.sdk.transactions.TransactionBean;
import java.io.Serializable;
import java.util.Objects;

public class PrivateBlockTxBean implements Serializable, Comparable<PrivateBlockTxBean> {

    private TransactionBean tb;
    private String singleInclusionTransactionHash;
    private KeyContexts.InternalBlockTransactionState transactionValidity;

    /**
     * @return the tb
     */
    public TransactionBean getTb() {
        return tb;
    }

    /**
     * @return the transactionValidity
     */
    public KeyContexts.InternalBlockTransactionState getTransactionValidity() {
        return transactionValidity;
    }

    /**
     * @param tb the tb to set
     */
    public void setTb(TransactionBean tb) {
        this.tb = tb;
    }

    /**
     * @param transactionValidity the transactionValidity to set
     */
    public void setTransactionValidity(KeyContexts.InternalBlockTransactionState transactionValidity) {
        this.transactionValidity = transactionValidity;
    }

    public String getSingleInclusionTransactionHash() {
        return singleInclusionTransactionHash;
    }

    public void setSingleInclusionTransactionHash(String singleInclusionTransactionHash) {
        this.singleInclusionTransactionHash = singleInclusionTransactionHash;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.tb);
        hash = 31 * hash + Objects.hashCode(this.singleInclusionTransactionHash);
        hash = 31 * hash + Objects.hashCode(this.transactionValidity);
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
        final PrivateBlockTxBean other = (PrivateBlockTxBean) obj;
        if (!Objects.equals(this.singleInclusionTransactionHash, other.singleInclusionTransactionHash)) {
            return false;
        }
        if (!Objects.equals(this.tb, other.tb)) {
            return false;
        }
        if (this.transactionValidity != other.transactionValidity) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(PrivateBlockTxBean t) {
        return this.singleInclusionTransactionHash.compareTo(t.singleInclusionTransactionHash);
    }

}
