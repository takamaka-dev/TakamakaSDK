package io.takamaka.sdk.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import org.bouncycastle.util.Strings;

/**
 *
 * @author giovanni.antino@h2tcoin.com
 */
public class SeededRandom extends SecureRandom {

    private String seed;
    private String scope;
    private int keyNumber;
    //private int keyLength;

    /**
     *
     * @param seed
     * @param scope
     * @param keyNumber
     */
    public SeededRandom(String seed, String scope, int keyNumber) {
        this.seed = seed;
        this.scope = scope;
        this.keyNumber = keyNumber;
    }

    @Override
    public void nextBytes(byte[] bytes) {
        PBEKeySpec spec = new PBEKeySpec(Strings.asCharArray(Strings.toByteArray(seed)),
                Strings.toByteArray(scope),
                keyNumber, 8 * bytes.length);
        SecretKeyFactory skf = null;
        try {
            skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        } catch (NoSuchAlgorithmException ex) {
        }
        byte[] encoded = null;
        try {
            encoded = skf.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException ex) {
        }
        for (int i = 0; i != bytes.length; i++) {
            bytes[i] = encoded[i];
        }
    }

}
