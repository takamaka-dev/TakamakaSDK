package io.takamaka.sdk.exceptions.wallet;

public class InvalidCypherException extends WalletException {

    public InvalidCypherException() {
        super();
    }

    public InvalidCypherException(String msg) {
        super(msg);
    }

    public InvalidCypherException(Throwable er) {
        super(er);
    }

    public InvalidCypherException(String msg,Throwable er) {
        super(msg, er);
    }
}
