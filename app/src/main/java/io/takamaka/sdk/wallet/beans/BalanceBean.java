package io.takamaka.sdk.wallet.beans;

public class BalanceBean {
    private String address;
    private String greenBalance;
    private String redBalance;
    private String greenPenalty;
    private String redPenalty;
    private String penaltySlots;
    private String generatorSith;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGreenBalance() {
        return greenBalance;
    }

    public void setGreenBalance(String greenBalance) {
        this.greenBalance = greenBalance;
    }

    public String getRedBalance() {
        return redBalance;
    }

    public void setRedBalance(String redBalance) {
        this.redBalance = redBalance;
    }

    public String getGreenPenalty() {
        return greenPenalty;
    }

    public void setGreenPenalty(String greenPenalty) {
        this.greenPenalty = greenPenalty;
    }

    public String getRedPenalty() {
        return redPenalty;
    }

    public void setRedPenalty(String redPenalty) {
        this.redPenalty = redPenalty;
    }

    public String getPenaltySlots() {
        return penaltySlots;
    }

    public void setPenaltySlots(String penaltySlots) {
        this.penaltySlots = penaltySlots;
    }

    public String getGeneratorSith() {
        return generatorSith;
    }

    public void setGeneratorSith(String generatorSith) {
        this.generatorSith = generatorSith;
    }
}
