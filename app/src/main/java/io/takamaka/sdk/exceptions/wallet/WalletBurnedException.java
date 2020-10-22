package io.takamaka.sdk.exceptions.wallet;

public class WalletBurnedException extends WalletException {

    public WalletBurnedException() {
        super();
    }

    public WalletBurnedException(String msg) {
        super(msg);
    }

    public WalletBurnedException(Throwable er) {
        super(er);
    }

    public WalletBurnedException(String msg,Throwable er) {
        super(msg, er);
    }
}
