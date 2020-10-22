package io.takamaka.sdk.exceptions.threadSafeUtils;

public class HashProviderNotFoundException extends ThreadSafeUtilsException {

    public HashProviderNotFoundException() {
        super();
    }

    public HashProviderNotFoundException(String msg) {
        super(msg);
    }

    public HashProviderNotFoundException(Throwable er) {
        super(er);
    }

    public HashProviderNotFoundException(String msg,Throwable er) {
        super(msg, er);
    }
}
