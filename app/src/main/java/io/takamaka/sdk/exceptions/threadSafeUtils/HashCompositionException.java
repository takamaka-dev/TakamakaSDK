package io.takamaka.sdk.exceptions.threadSafeUtils;

public class HashCompositionException extends ThreadSafeUtilsException {

    public HashCompositionException() {
        super();
    }

    public HashCompositionException(String msg) {
        super(msg);
    }

    public HashCompositionException(Throwable er) {
        super(er);
    }

    public HashCompositionException(String msg,Throwable er) {
        super(msg, er);
    }
}
