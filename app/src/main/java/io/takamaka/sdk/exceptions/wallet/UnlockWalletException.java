package io.takamaka.sdk.exceptions.wallet;

public class UnlockWalletException extends WalletException {

    public UnlockWalletException() {
        super();
    }

    public UnlockWalletException(String msg) {
        super(msg);
    }

    public UnlockWalletException(Throwable er) {
        super(er);
    }

    public UnlockWalletException(String msg,Throwable er) {
        super(msg, er);
    }
}
