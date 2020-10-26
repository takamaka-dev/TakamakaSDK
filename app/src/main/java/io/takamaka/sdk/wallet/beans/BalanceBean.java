package io.takamaka.sdk.wallet.beans;

public class BalanceBean {
    private String address;
    private long greenBalance;
    private long redBalance;
    private int greenPenalty;
    private int redPenalty;
    private int penaltySlots;
    private String generatorSith;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getGreenBalance() {
        return greenBalance;
    }

    public void setGreenBalance(long greenBalance) {
        this.greenBalance = greenBalance;
    }

    public long getRedBalance() {
        return redBalance;
    }

    public void setRedBalance(long redBalance) {
        this.redBalance = redBalance;
    }

    public int getGreenPenalty() {
        return greenPenalty;
    }

    public void setGreenPenalty(int greenPenalty) {
        this.greenPenalty = greenPenalty;
    }

    public int getRedPenalty() {
        return redPenalty;
    }

    public void setRedPenalty(int redPenalty) {
        this.redPenalty = redPenalty;
    }

    public int getPenaltySlots() {
        return penaltySlots;
    }

    public void setPenaltySlots(int penaltySlots) {
        this.penaltySlots = penaltySlots;
    }

    public String getGeneratorSith() {
        return generatorSith;
    }

    public void setGeneratorSith(String generatorSith) {
        this.generatorSith = generatorSith;
    }
}
