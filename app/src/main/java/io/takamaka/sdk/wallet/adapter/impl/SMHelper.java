package io.takamaka.sdk.wallet.adapter.impl;

import io.takamaka.sdk.exceptions.threadSafeUtils.HashEncodeException;
import io.takamaka.sdk.exceptions.wallet.WalletException;
import io.takamaka.sdk.globalContext.FixedParameters;
import io.takamaka.sdk.wallet.InstanceWalletKeystoreInterface;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;
import org.bouncycastle.util.encoders.UrlBase64;

/**
 *
 * @author giovanni.antino@h2tcoin.com
 */
public class SMHelper {



    public static final byte[] hash(byte[] inputB) {
        try {
            //set hashing provider
            if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
                Security.addProvider(new BouncyCastleProvider());
            }
            MessageDigest digest = MessageDigest.getInstance(SMDefaults.HASH_ALGORITHM, BouncyCastleProvider.PROVIDER_NAME);
            digest.reset();
            ByteBuffer bb = ByteBuffer.wrap(inputB);
            digest.update(bb);
            byte[] digestResult = digest.digest();
            bb.clear();
            return digestResult;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static final String toHEX(byte[] inputB) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Hex.encode(inputB, baos);
            String res = baos.toString(SMDefaults.ENCODING);
            baos.close();
            return res;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static final String toBASE64URL(byte[] inputB) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            UrlBase64.encode(inputB, baos);
            String res = baos.toString(SMDefaults.ENCODING);
            baos.close();
            return res;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static final String toBASE64(byte[] inputB) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            org.bouncycastle.util.encoders.Base64.encode(inputB, baos);
            String res = baos.toString(SMDefaults.ENCODING);
            baos.close();
            return res;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static final byte[] fromBase64ToByte(String base64) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            org.bouncycastle.util.encoders.Base64.decode(base64, baos);
            byte[] res = base64.getBytes();
            baos.close();
            return res;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    //public static final String
}
