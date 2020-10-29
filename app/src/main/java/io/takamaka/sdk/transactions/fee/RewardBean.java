package io.takamaka.sdk.transactions.fee;

import java.io.Serializable;
import java.math.BigInteger;


public class RewardBean implements Serializable {

    private final Object GREEN_LOCK = new Object();
    private final Object RED_LOCK = new Object();

    private BigInteger greenValue;
    private BigInteger redValue;

    public BigInteger getGreenValue() {
        synchronized (GREEN_LOCK) {
            return greenValue;
        }
    }

    public void setGreenValue(BigInteger greenValue) {
        synchronized (GREEN_LOCK) {
            this.greenValue = greenValue;
        }
    }

    public BigInteger getRedValue() {
        synchronized (RED_LOCK) {
            return redValue;
        }
    }

    public void setRedValue(BigInteger redValue) {
        synchronized (RED_LOCK) {
            this.redValue = redValue;
        }
    }

    public void addGreen(BigInteger val) {
        synchronized (GREEN_LOCK) {
            this.greenValue = this.greenValue.add(val);
        }
    }

    public void addRed(BigInteger val) {
        synchronized (RED_LOCK) {
            this.redValue = this.redValue.add(val);
        }
    }

}
