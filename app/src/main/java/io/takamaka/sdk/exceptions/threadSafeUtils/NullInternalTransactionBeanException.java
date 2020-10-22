package io.takamaka.sdk.exceptions.threadSafeUtils;

public class NullInternalTransactionBeanException extends ThreadSafeUtilsException {

    public NullInternalTransactionBeanException() {
        super();
    }

    public NullInternalTransactionBeanException(String msg) {
        super(msg);
    }

    public NullInternalTransactionBeanException(Throwable er) {
        super(er);
    }

    public NullInternalTransactionBeanException(String msg,Throwable er) {
        super(msg, er);
    }
}
