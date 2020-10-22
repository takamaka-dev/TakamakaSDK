package io.takamaka.sdk.wallet;

/**
 *
 * @author giovanni.antino@h2tcoin.com
 */
public class TkmCypherBean {

    private boolean valid;
    private String signature;
    private Exception ex;

    public Exception getEx() {
        return ex;
    }

    protected void setEx(Exception ex) {
        this.ex = ex;
    }

    public boolean isValid() {
        return valid;
    }

    protected void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getSignature() {
        return signature;
    }

    protected void setSignature(String signature) {
        this.signature = signature;
    }

}
