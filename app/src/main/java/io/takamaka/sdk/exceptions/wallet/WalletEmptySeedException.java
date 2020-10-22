package io.takamaka.sdk.exceptions.wallet;

public class WalletEmptySeedException extends WalletException {

    public WalletEmptySeedException() {
        super();
    }

    public WalletEmptySeedException(String msg) {
        super(msg);
    }

    public WalletEmptySeedException(Throwable er) {
        super(er);
    }

    public WalletEmptySeedException(String msg,Throwable er) {
        super(msg, er);
    }
}
