package io.takamaka.sdk.transactions;

import io.takamaka.sdk.globalContext.KeyContexts.TransactionType;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.Objects;

public class InternalTransactionBean implements Serializable {

    private String from;
    private String to;
    private String message;
    private long notBefore;
    private BigInteger redValue;
    private BigInteger greenValue;
    private TransactionType transactionType;
    private String transactionHash;
    private Integer epoch;
    private Integer slot;

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.from);
        hash = 79 * hash + Objects.hashCode(this.to);
        hash = 79 * hash + Objects.hashCode(this.message);
        hash = 79 * hash + (int) (this.notBefore ^ (this.notBefore >>> 32));
        hash = 79 * hash + Objects.hashCode(this.redValue);
        hash = 79 * hash + Objects.hashCode(this.greenValue);
        hash = 79 * hash + Objects.hashCode(this.transactionType);
        hash = 79 * hash + Objects.hashCode(this.transactionHash);
        hash = 79 * hash + Objects.hashCode(this.epoch);
        hash = 79 * hash + Objects.hashCode(this.slot);
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
        final InternalTransactionBean other = (InternalTransactionBean) obj;
        if (this.notBefore != other.notBefore) {
            return false;
        }
        if (!Objects.equals(this.from, other.from)) {
            return false;
        }
        if (!Objects.equals(this.to, other.to)) {
            return false;
        }
        if (!Objects.equals(this.message, other.message)) {
            return false;
        }
        if (!Objects.equals(this.transactionHash, other.transactionHash)) {
            return false;
        }
        if (!Objects.equals(this.redValue, other.redValue)) {
            return false;
        }
        if (!Objects.equals(this.greenValue, other.greenValue)) {
            return false;
        }
        if (this.transactionType != other.transactionType) {
            return false;
        }
        if (!Objects.equals(this.epoch, other.epoch)) {
            return false;
        }
        if (!Objects.equals(this.slot, other.slot)) {
            return false;
        }
        return true;
    }

    /**
     * @return the from address
     */
    public String getFrom() {
        return from;
    }

    /**
     * @return the to address
     */
    public String getTo() {
        return to;
    }

    /**
     * @return internal transaction timestamp
     * this field is set from the author of the transaction
     * the maximum allowed time in which a transaction can be included in a block,
     * can be at most current time + 10 minutes.
     * Transactions with a lower timestamp than the block creation time are discarded
     */
    public Date getNotBefore() {
        return new java.util.Date(notBefore);
    }

    /**
     * @return the redValue of the token
     */
    public BigInteger getRedValue() {
        return redValue;
    }

    /**
     * @return the greenValue of the token
     */
    public BigInteger getGreenValue() {
        return greenValue;
    }

    /**
     * @return the transactionHash
     */
    public String getTransactionHash() {
        return transactionHash;
    }

    /**
     * @param from the from to set
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * @param to the to to set
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * @param notBefore the notBefore to set
     */
    public void setNotBefore(Date notBefore) {
        this.notBefore = notBefore.getTime();
    }

    /**
     * @param redValue the redValue to set
     */
    public void setRedValue(BigInteger redValue) {
        this.redValue = redValue;
    }

    /**
     * @param greenValue the greenValue to set
     */
    public void setGreenValue(BigInteger greenValue) {
        this.greenValue = greenValue;
    }

    /**
     * @param transactionHash the transactionHash to set
     */
    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }

    /**
     * @return the message of the transaction
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set in the transaction
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the transactionType
     */
    public TransactionType getTransactionType() {
        return transactionType;
    }

    /**
     * @param transactionType the transactionType to set
     */
    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    /**
     * @return the epoch
     */
    public Integer getEpoch() {
        return epoch;
    }

    /**
     * @return the time slot in which the transaction is included
     */
    public Integer getSlot() {
        return slot;
    }

    /**
     * @param epoch the epoch to set
     */
    public void setEpoch(Integer epoch) {
        this.epoch = epoch;
    }

    /**
     * @param slot the slot to set
     */
    public void setSlot(Integer slot) {
        this.slot = slot;
    }

}
