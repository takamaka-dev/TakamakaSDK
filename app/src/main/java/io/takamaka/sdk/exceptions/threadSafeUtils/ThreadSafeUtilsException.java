package io.takamaka.sdk.exceptions.threadSafeUtils;

public class ThreadSafeUtilsException extends Exception {

    public ThreadSafeUtilsException() {
        super();
    }

    public ThreadSafeUtilsException(String msg) {
        super(msg);
    }

    public ThreadSafeUtilsException(Throwable er) {
        super(er);
    }

    public ThreadSafeUtilsException(String msg,Throwable er) {
        super(msg, er);
    }
}
