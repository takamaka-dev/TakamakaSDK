package io.takamaka.sdk.exceptions.threadSafeUtils;

public class KeyDecodeException extends ThreadSafeUtilsException {

    public KeyDecodeException() {
        super();
    }

    public KeyDecodeException(String msg) {
        super(msg);
    }

    public KeyDecodeException(Throwable er) {
        super(er);
    }

    public KeyDecodeException(String msg,Throwable er) {
        super(msg, er);
    }
}
