package io.takamaka.sdk.exceptions.threadSafeUtils;

public class InclusionHashCreationException extends ThreadSafeUtilsException {

    public InclusionHashCreationException() {
        super();
    }

    public InclusionHashCreationException(String msg) {
        super(msg);
    }

    public InclusionHashCreationException(Throwable er) {
        super(er);
    }

    public InclusionHashCreationException(String msg,Throwable er) {
        super(msg, er);
    }
}
