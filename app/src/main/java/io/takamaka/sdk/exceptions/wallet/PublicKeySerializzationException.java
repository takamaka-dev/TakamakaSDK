package io.takamaka.sdk.exceptions.wallet;

public class PublicKeySerializzationException extends WalletException {

    public PublicKeySerializzationException() {
        super();
    }

    public PublicKeySerializzationException(String msg) {
        super(msg);
    }

    public PublicKeySerializzationException(Throwable er) {
        super(er);
    }

    public PublicKeySerializzationException(String msg,Throwable er) {
        super(msg, er);
    }
}
