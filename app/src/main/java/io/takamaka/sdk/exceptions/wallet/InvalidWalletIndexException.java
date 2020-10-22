package io.takamaka.sdk.exceptions.wallet;

public class InvalidWalletIndexException extends WalletException {

    public InvalidWalletIndexException() {
        super();
    }

    public InvalidWalletIndexException(String msg) {
        super(msg);
    }

    public InvalidWalletIndexException(Throwable er) {
        super(er);
    }

    public InvalidWalletIndexException(String msg,Throwable er) {
        super(msg, er);
    }
}
