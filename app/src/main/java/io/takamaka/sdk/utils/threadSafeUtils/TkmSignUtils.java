package io.takamaka.sdk.utils.threadSafeUtils;

import io.takamaka.sdk.exceptions.threadSafeUtils.HashAlgorithmNotFoundException;
import io.takamaka.sdk.exceptions.threadSafeUtils.HashEncodeException;
import io.takamaka.sdk.exceptions.threadSafeUtils.HashProviderNotFoundException;
import io.takamaka.sdk.exceptions.threadSafeUtils.KeyDecodeException;
import io.takamaka.sdk.globalContext.FixedParameters;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.digests.SHAKEDigest;
import org.bouncycastle.crypto.params.Ed25519PublicKeyParameters;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.crypto.qtesla.QTESLAPublicKeyParameters;
import org.bouncycastle.pqc.crypto.qtesla.QTESLASecurityCategory;
import org.bouncycastle.util.encoders.Hex;
import org.bouncycastle.util.encoders.UrlBase64;

/**
 *
 * @author giovanni.antino@h2tcoin.com
 */
public class TkmSignUtils {

    public static AsymmetricCipherKeyPair stringPublicKeyToKeyPairBCEd25519(String publicKey) throws KeyDecodeException {
        try {
            UrlBase64 b64e = new UrlBase64();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            UrlBase64.decode(publicKey, baos);
            Ed25519PublicKeyParameters edPublicKey = new Ed25519PublicKeyParameters(baos.toByteArray(), 0);
            baos.close();
            AsymmetricCipherKeyPair ackp = new AsymmetricCipherKeyPair(edPublicKey, null);
            return ackp;
        } catch (Exception ex) {
            throw new KeyDecodeException(ex);
        }
    }

    public static AsymmetricCipherKeyPair stringPublicKeyToKeyPairBCQTESLAPSSC1(String publicKey) throws KeyDecodeException {
        try {
            //UrlBase64 b64e = new UrlBase64();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            UrlBase64.decode(publicKey, baos);
            QTESLAPublicKeyParameters edPublicKey = new QTESLAPublicKeyParameters(QTESLASecurityCategory.PROVABLY_SECURE_I, baos.toByteArray());
            baos.close();
            AsymmetricCipherKeyPair ackp = new AsymmetricCipherKeyPair(edPublicKey, null);
            return ackp;
        } catch (Exception ex) {
            throw new KeyDecodeException(ex);
        }
    }


    public static String Hash256(String input) throws HashEncodeException, HashAlgorithmNotFoundException, HashProviderNotFoundException {
        return TkmSignUtils.Hash(input, FixedParameters.HASH_256_ALGORITHM);
    }

    public static String Hash512(String input) throws HashEncodeException, HashAlgorithmNotFoundException, HashProviderNotFoundException {
        return TkmSignUtils.Hash(input, FixedParameters.HASH_512_ALGORITHM);
    }

    public static byte[] Hash256byte(String input) throws HashEncodeException, HashAlgorithmNotFoundException, HashProviderNotFoundException {
        return TkmSignUtils.HashByte(input, FixedParameters.HASH_256_ALGORITHM);
    }

    public static byte[] Hash384byte(String input) throws HashEncodeException, HashAlgorithmNotFoundException, HashProviderNotFoundException {
        return TkmSignUtils.HashByte(input, FixedParameters.HASH_384_ALGORITHM);
    }

    public static byte[] Hash512byte(String input) throws HashEncodeException, HashAlgorithmNotFoundException, HashProviderNotFoundException {
        return TkmSignUtils.HashByte(input, FixedParameters.HASH_512_ALGORITHM);
    }

    public static String PWHashB64(String input, String salt, int iterations, int bitLegnthKey) throws HashEncodeException, HashAlgorithmNotFoundException, HashProviderNotFoundException, InvalidKeySpecException, NoSuchAlgorithmException {
        try {
            PBEKeySpec spec = new PBEKeySpec(input.toCharArray(), salt.getBytes(), iterations, bitLegnthKey);
            SecretKeyFactory skf = SecretKeyFactory.getInstance(FixedParameters.HASH_PWH_ALGORITHM);
            byte[] hash = skf.generateSecret(spec).getEncoded();
            ByteBuffer bb = ByteBuffer.wrap(hash);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            UrlBase64.encode(bb.array(), baos);
            bb.clear();
            String out = baos.toString(FixedParameters.CHARSET.name());
            baos.close();
            return out;
        } catch (Exception ex) {
            Logger.getLogger(TkmSignUtils.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static byte[] PWHash(String input, String salt, int iterations, int bitLegnthKey) throws HashEncodeException, HashAlgorithmNotFoundException, HashProviderNotFoundException, InvalidKeySpecException, NoSuchAlgorithmException {
        PBEKeySpec spec = new PBEKeySpec(input.toCharArray(), salt.getBytes(), iterations, bitLegnthKey);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(FixedParameters.HASH_PWH_ALGORITHM);
        return skf.generateSecret(spec).getEncoded();
    }

    /**
     *
     * @param input
     * @param hashType
     * @return
     * @throws HashEncodeException
     * @throws HashAlgorithmNotFoundException
     * @throws HashProviderNotFoundException
     */
    private static String Hash(String input, String hashType) throws HashEncodeException, HashAlgorithmNotFoundException, HashProviderNotFoundException {
        try {
            //Base64 b64enc = new Base64();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //string -> byte[]
            byte[] inputBytes = input.getBytes(FixedParameters.CHARSET);
            //set hashing provider
            if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
                Security.addProvider(new BouncyCastleProvider());
            }
            MessageDigest digest = MessageDigest.getInstance(hashType, BouncyCastleProvider.PROVIDER_NAME);
            digest.reset();
            ByteBuffer bb = ByteBuffer.wrap(inputBytes);
            digest.update(bb);
            UrlBase64.encode(digest.digest(), baos);
            bb.clear();
            String out = baos.toString(FixedParameters.CHARSET.name());
            baos.close();
            return out;

        } catch (Exception ex) {
            throw new HashEncodeException(ex);
        }
    }

    public static byte[] Hash256Byte(byte[] input, String hashType) throws NoSuchAlgorithmException, NoSuchProviderException {
        byte[] result = null;
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
        MessageDigest digest = MessageDigest.getInstance(hashType, BouncyCastleProvider.PROVIDER_NAME);
        digest.reset();
        ByteBuffer bb = ByteBuffer.wrap(input);
        digest.update(bb);
        result = digest.digest();
        digest.reset();
        bb.clear();
        return result;
    }

    private static byte[] HashByte(String input, String hashType) throws HashEncodeException, HashAlgorithmNotFoundException, HashProviderNotFoundException {
        try {
            //Base64 b64enc = new Base64();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //string -> byte[]
            byte[] inputBytes = input.getBytes(FixedParameters.CHARSET);
            //set hashing provider
            if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
                Security.addProvider(new BouncyCastleProvider());
            }
            MessageDigest digest = MessageDigest.getInstance(hashType, BouncyCastleProvider.PROVIDER_NAME);
            digest.reset();
            ByteBuffer bb = ByteBuffer.wrap(inputBytes);
            digest.update(bb);
            UrlBase64.encode(digest.digest(), baos);
            bb.clear();
            byte[] out = baos.toByteArray();
            baos.close();
            return out;

        } catch (Exception ex) {
            throw new HashEncodeException(ex);
        }
    }


    public static String Hash256ToHex(String input) throws HashEncodeException, HashAlgorithmNotFoundException, HashProviderNotFoundException {
        return TkmSignUtils.HashToHex(input, FixedParameters.HASH_256_ALGORITHM);
    }

    public static String Hash384ToHex(String input) throws HashEncodeException, HashAlgorithmNotFoundException, HashProviderNotFoundException {
        return TkmSignUtils.HashToHex(input, FixedParameters.HASH_384_ALGORITHM);
    }

    public static String Hash512ToHex(String input) throws HashEncodeException, HashAlgorithmNotFoundException, HashProviderNotFoundException {
        return TkmSignUtils.HashToHex(input, FixedParameters.HASH_512_ALGORITHM);
    }

    public static String fromByteArrayToB64URL(byte[] input) {
        String out = null;
        try {
            //UrlBase64 b64e = new UrlBase64();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //Base64.encode(input, baos);
            UrlBase64.encode(input, baos);
            out = baos.toString(FixedParameters.CHARSET.name());
            baos.close();
        } catch (Exception ex) {
            Logger.getLogger(TkmSignUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return out;
    }

    private static String HashToHex(String input, String hashType) throws HashEncodeException, HashAlgorithmNotFoundException, HashProviderNotFoundException {
        try {
            Hex henc = new Hex();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //string -> byte[]
            byte[] inputBytes = input.getBytes(FixedParameters.CHARSET);
            //set hashing provider
            if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
                Security.addProvider(new BouncyCastleProvider());
            }
            MessageDigest digest = MessageDigest.getInstance(hashType, BouncyCastleProvider.PROVIDER_NAME);
            digest.reset();
            ByteBuffer bb = ByteBuffer.wrap(inputBytes);
            digest.update(bb);
            Hex.encode(digest.digest(), baos);
            bb.clear();
            return baos.toString(FixedParameters.CHARSET.name());

        } catch (Exception ex) {
            throw new HashEncodeException(ex);
        }
    }

    /**
     * from url base64 to byte, null if invalid
     *
     * @param input
     * @return
     */
    public static byte[] fromB64URLToByteArray(String input) {
        byte[] res = null;
        try {
            //UrlBase64 b64e = new UrlBase64();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            UrlBase64.decode(input, baos);
            res = baos.toByteArray();

            baos.close();
        } catch (Exception ex) {
        }
        return res;
    }

    /**
     * convert transactionbox "from", "to" address to hex format
     *
     * @param input
     * @return
     */
    public static String fromB64UrlToHEX(String input) {
        byte[] b64ToByte = fromB64URLToByteArray(input);
        if (b64ToByte != null) {
            return fromByteArrayToHexString(b64ToByte);
        }
        return null;
    }

    /**
     * from byte to hex, null if invalid
     *
     * @param input
     * @return
     */
    public static String fromByteArrayToHexString(byte[] input) {
        String res = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Hex.encode(input, baos);
            res = baos.toString(FixedParameters.CHARSET.name());
            baos.close();
        } catch (Exception ex) {
            Logger.getLogger(TkmSignUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    @Deprecated
    public static String toCompressedB64(byte[] input) {
        return fromByteArrayToB64URL(TkmCompressionUtils.deflate(input));
    }

    @Deprecated
    public static byte[] fromCompressedB64(String input) {
        return TkmCompressionUtils.inflate(fromB64URLToByteArray(input));
    }

    /**
     * Return the hex of the Ripemd160 hash of the input string.
     *
     * @param publicKey
     * @return
     */
    public static String Hash160ToHex(String publicKey) {
        try {
            return TkmSignUtils.HashToHex(publicKey, FixedParameters.HASH_160_ALGORITHM);
        } catch (HashEncodeException | HashAlgorithmNotFoundException | HashProviderNotFoundException ex) {
        }
        return null;
    }

    public static String fromStringToHexString(String message) {
        String res = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] mBytes = message.getBytes(FixedParameters.CHARSET);
            Hex.encode(mBytes, baos);
            res = baos.toString(FixedParameters.CHARSET.name());
            baos.close();
        } catch (Exception ex) {
        }
        return res;
    }

    /**
     *
     * @param message
     * @return null if decode fail
     */
    public static byte[] fromHexToByteArray(String message) {
        byte[] res = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //byte[] mBytes = hexMessage.getBytes(FixedParameters.CHARSET);
            Hex.decode(message, baos);
            res = baos.toByteArray();
            baos.close();
        } catch (Exception ex) {
        }
        return res;
    }

    public static String fromHexToString(String hexMessage) {
        String res = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //byte[] mBytes = hexMessage.getBytes(FixedParameters.CHARSET);
            Hex.decode(hexMessage, baos);
            res = baos.toString(FixedParameters.CHARSET.name());
            baos.close();
        } catch (Exception ex) {
        }
        return res;
    }

    public static byte[] fromStringToByteArray(String message) {
        return message.getBytes(FixedParameters.CHARSET);
    }

    public static char[] fromStringToCharArray(String message) {
        return message.toCharArray();//message.getBytes(FixedParameters.CHARSET);
    }

    public static String fromCharArrayToString(char[] message) {
        return new String(message);
    }

    public static String fromStringToBase64URL(String message) {
        return fromByteArrayToB64URL(fromStringToByteArray(message));
    }

    public static String fromBase64URLToString(String message) {
        try {
            return new String(fromB64URLToByteArray(message), FixedParameters.CHARSET.name());

        } catch (UnsupportedEncodingException ex) {
            return null;
        }
    }

    private static long textToLongID(String input, int bitLen) throws HashEncodeException, HashAlgorithmNotFoundException, HashProviderNotFoundException {
        try {
            if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
                Security.addProvider(new BouncyCastleProvider());
            }
            byte[] byIn = fromStringToByteArray(input);
            SHAKEDigest sh = new SHAKEDigest(bitLen);
            sh.reset();
            sh.update(byIn, 0, byIn.length);
            byte[] byteRes = new byte[bitLen / 8];
            sh.doFinal(byteRes, 0, bitLen / 8);
            //MessageDigest digest = MessageDigest.getInstance(hashType, BouncyCastleProvider.PROVIDER_NAME);
            ByteBuffer buffer = ByteBuffer.allocate(bitLen / 8);
            buffer.put(byteRes);
            buffer.flip();//need flip 
            return buffer.getLong();
        } catch (Exception ex) {
            throw new HashEncodeException(ex);
        }
    }

    public static long getLongID(String text) {
        try {
            return textToLongID(text, 256);
        } catch (HashEncodeException | HashAlgorithmNotFoundException | HashProviderNotFoundException ex) {
            return 0L;
        }
    }


}
