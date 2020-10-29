package io.takamaka.sdk.block;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;


public class TkmRewardBean implements Serializable {

    private String url64Addr;
    private BigInteger greenValue;
    private BigInteger redValue;
    private BigInteger frozenFeeRed;
    private BigInteger frozenFeeGreen;
    private int penaltySlots;

    public TkmRewardBean(String toUrl64Addr, BigInteger greenValue, BigInteger redValue, BigInteger frozenFeeRed, BigInteger frozenFeeGreen, int penaltySlots) {
        this.url64Addr = toUrl64Addr;
        this.greenValue = greenValue;
        this.redValue = redValue;
        this.frozenFeeRed = frozenFeeRed;
        this.frozenFeeGreen = frozenFeeGreen;
        this.penaltySlots = penaltySlots;
    }

    public String getUrl64Addr() {
        return url64Addr;
    }

    public void setUrl64Addr(String url64Addr) {
        this.url64Addr = url64Addr;
    }

    public BigInteger getGreenValue() {
        return greenValue;
    }

    public void setGreenValue(BigInteger greenValue) {
        this.greenValue = greenValue;
    }

    public BigInteger getRedValue() {
        return redValue;
    }

    public void setRedValue(BigInteger redValue) {
        this.redValue = redValue;
    }

    public BigInteger getFrozenFeeRed() {
        return frozenFeeRed;
    }

    public void setFrozenFeeRed(BigInteger frozenFeeRed) {
        this.frozenFeeRed = frozenFeeRed;
    }

    public BigInteger getFrozenFeeGreen() {
        return frozenFeeGreen;
    }

    public void setFrozenFeeGreen(BigInteger frozenFeeGreen) {
        this.frozenFeeGreen = frozenFeeGreen;
    }

    public int getPenaltySlots() {
        return penaltySlots;
    }

    public void setPenaltySlots(int penaltySlots) {
        this.penaltySlots = penaltySlots;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.url64Addr);
        hash = 23 * hash + Objects.hashCode(this.greenValue);
        hash = 23 * hash + Objects.hashCode(this.redValue);
        hash = 23 * hash + Objects.hashCode(this.frozenFeeRed);
        hash = 23 * hash + Objects.hashCode(this.frozenFeeGreen);
        hash = 23 * hash + this.penaltySlots;
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
        final TkmRewardBean other = (TkmRewardBean) obj;
        if (this.penaltySlots != other.penaltySlots) {
            return false;
        }
        if (!Objects.equals(this.url64Addr, other.url64Addr)) {
            return false;
        }
        if (!Objects.equals(this.greenValue, other.greenValue)) {
            return false;
        }
        if (!Objects.equals(this.redValue, other.redValue)) {
            return false;
        }
        if (!Objects.equals(this.frozenFeeRed, other.frozenFeeRed)) {
            return false;
        }
        if (!Objects.equals(this.frozenFeeGreen, other.frozenFeeGreen)) {
            return false;
        }
        return true;
    }

}
