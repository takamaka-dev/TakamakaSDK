package io.takamaka.sdk.exceptions.threadSafeUtils;

public class HashEncodeException extends ThreadSafeUtilsException {

    public HashEncodeException() {
        super();
    }

    public HashEncodeException(String msg) {
        super(msg);
    }

    public HashEncodeException(Throwable er) {
        super(er);
    }

    public HashEncodeException(String msg,Throwable er) {
        super(msg, er);
    }
}
