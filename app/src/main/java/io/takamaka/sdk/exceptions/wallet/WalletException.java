package io.takamaka.sdk.exceptions.wallet;

public class WalletException extends Exception {

    public WalletException() {
        super();
    }

    public WalletException(String msg) {
        super(msg);
    }

    public WalletException(Throwable er) {
        super(er);
    }

    public WalletException(String msg,Throwable er) {
        super(msg, er);
    }
}
