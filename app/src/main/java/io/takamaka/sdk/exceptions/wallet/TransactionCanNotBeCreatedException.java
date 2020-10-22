package io.takamaka.sdk.exceptions.wallet;

public class TransactionCanNotBeCreatedException extends WalletException {

    /**
     *
     */
    public TransactionCanNotBeCreatedException() {
        super();
    }

    /**
     *
     * @param message
     */
    public TransactionCanNotBeCreatedException(String message) {
        super(message);
    }

    /**
     *
     * @param err
     */
    public TransactionCanNotBeCreatedException(Throwable err) {
        super(err);
    }

    /**
     *
     * @param message
     * @param err
     */
    public TransactionCanNotBeCreatedException(String message, Throwable err) {
        super(message, err);
    }
}
