package io.takamaka.sdk.exceptions.wallet;

public class TransactionCanNotBeSignedException extends WalletException {

    /**
     *
     */
    public TransactionCanNotBeSignedException() {
        super();
    }

    /**
     *
     * @param message
     */
    public TransactionCanNotBeSignedException(String message) {
        super(message);
    }

    /**
     *
     * @param err
     */
    public TransactionCanNotBeSignedException(Throwable err) {
        super(err);
    }

    /**
     *
     * @param message
     * @param err
     */
    public TransactionCanNotBeSignedException(String message, Throwable err) {
        super(message, err);
    }
}
