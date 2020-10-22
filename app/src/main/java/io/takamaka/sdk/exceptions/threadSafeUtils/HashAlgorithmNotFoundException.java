package io.takamaka.sdk.exceptions.threadSafeUtils;

public class HashAlgorithmNotFoundException extends ThreadSafeUtilsException {

    public HashAlgorithmNotFoundException() {
        super();
    }

    public HashAlgorithmNotFoundException(String msg) {
        super(msg);
    }

    public HashAlgorithmNotFoundException(Throwable er) {
        super(er);
    }

    public HashAlgorithmNotFoundException(String msg,Throwable er) {
        super(msg, er);
    }
}
