package io.takamaka.sdk.wallet;

import io.takamaka.sdk.wallet.beans.KeyBean;
import io.takamaka.sdk.wallet.beans.EncKeyBean;
import io.takamaka.sdk.wallet.beans.PublicKeyBean;
import com.google.gson.JsonSyntaxException;
import io.takamaka.sdk.exceptions.threadSafeUtils.HashAlgorithmNotFoundException;
import io.takamaka.sdk.exceptions.threadSafeUtils.HashEncodeException;
import io.takamaka.sdk.exceptions.threadSafeUtils.HashProviderNotFoundException;
import io.takamaka.sdk.exceptions.wallet.InvalidCypherException;
import io.takamaka.sdk.exceptions.wallet.InvalidWalletIndexException;
import io.takamaka.sdk.exceptions.wallet.PublicKeySerializzationException;
import io.takamaka.sdk.exceptions.wallet.UnlockWalletException;
import io.takamaka.sdk.globalContext.FixedParameters;
import static io.takamaka.sdk.globalContext.FixedParameters.PUBLICKEY_EXTENSION;
import io.takamaka.sdk.globalContext.FixedParameters.WalletError;
import io.takamaka.sdk.globalContext.KeyContexts;
import io.takamaka.sdk.globalContext.KeyContexts.WalletCypher;
import io.takamaka.sdk.main.defaults.DefaultInitParameters;
import io.takamaka.sdk.utils.FileHelper;
import io.takamaka.sdk.utils.threadSafeUtils.TkmSignUtils;
import io.takamaka.sdk.utils.threadSafeUtils.TkmTextUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author francesco.pasetto@takamaka.io
 */
public class WalletHelper {

    public static InstanceWalletKeystoreInterface readWallet(String filename, String password) throws InvalidCypherException, FileNotFoundException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, UnlockWalletException {
        Path pt = Paths.get(FileHelper.getDefaultWalletDirectoryPath().toString(), filename + DefaultInitParameters.WALLET_EXTENSION);
        return readWalletInternal(filename, pt, password);
    }

    public static InstanceWalletKeystoreInterface readWalletRecoveryGui(String filename, String password) throws InvalidCypherException, FileNotFoundException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, UnlockWalletException {
        Path pt = Paths.get(FileHelper.getDefaultWalletDirectoryPath().toString(), filename);
        return readWalletInternal(filename, pt, password);
    }

    private static InstanceWalletKeystoreInterface readWalletInternal(String filename, Path pt, String password) throws InvalidCypherException, FileNotFoundException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, UnlockWalletException {
        return (new InstanceWalletKeyStoreBCED25519(filename, password));
    }

    public static String getRecoveryWords(String filename, String password) throws InvalidCypherException, FileNotFoundException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, UnlockWalletException {
        KeyBean key = readKeyFile(Paths.get(FileHelper.getDefaultWalletDirectoryPath().toString(), filename + DefaultInitParameters.WALLET_EXTENSION), password);
        return key.getWords();
    }

    public static Path writeKeyFile(Path path, String filename, KeyBean key, String password) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
        String json = TkmTextUtils.toJson(key);

        /* if (password.length != 16 && password.length != 24 && password.length != 32) {
            throw new IllegalArgumentException("Password wrong length for AES key");
        }*/
        try {
            SecretKey sk = new SecretKeySpec(TkmSignUtils.PWHash(password, "TakamakaWallet", 1, 256), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, sk);

            byte[][] encJson = new byte[][]{cipher.getIV(), cipher.doFinal(json.getBytes(FixedParameters.CHARSET))};

            EncKeyBean ekb = new EncKeyBean(KeyContexts.WALLET_JSON_AES, encJson);

            FileHelper.writeStringToFile(path, filename, TkmTextUtils.toJson(ekb), false);
            Path walletPath = Paths.get(path.toString(), filename);
            return walletPath;
        } catch (HashEncodeException | HashAlgorithmNotFoundException | HashProviderNotFoundException | InvalidKeySpecException ex) {
            Logger.getLogger(WalletHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * wallet cypher - it returns the words list needed for wallet recovery procedure
     *
     * @param filename
     * @param password
     * @return
     * @throws FileNotFoundException
     * @throws NoSuchProviderException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     * @throws UnlockWalletException
     */
    public static KeyBean readKeyFile(Path filename, String password) throws FileNotFoundException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, UnlockWalletException {
        String encJson = FileHelper.readStringFromFile(filename);
//        System.out.println("Encoded Json");
//        System.out.println(encJson);
        byte[][] wallet = TkmTextUtils.enckeyBeanFromJson(encJson).getWallet();
        /*
        System.out.println("Byte Array Zero");
        System.out.println(Arrays.toString(wallet[0]));
        System.out.println("Byte Array Uno");
        System.out.println(Arrays.toString(wallet[1]));
         */
        String json;

        try {
            /*
            System.out.println("password");
            System.out.println(password);
            System.out.println("salt");
            System.out.println("TakamakaWallet");
            System.out.println("Iterazioni");
            System.out.println(1);
            System.out.println("Bit di output");
            System.out.println(256);
             */
            byte[] passwordDigest = TkmSignUtils.PWHash(password, "TakamakaWallet", 1, 256);
            /*
            System.out.println("Password Digest");
            System.out.println(Arrays.toString(passwordDigest));
             */
            SecretKey sk = new SecretKeySpec(passwordDigest, "AES");
            //System.out.println(sk.toString());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            //System.out.println("Algoritmo AES, Concatenatione CBC, Padding PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, sk, new IvParameterSpec(wallet[0]));
            json = new String(cipher.doFinal(wallet[1]));
            //System.out.println("JSON:\n" + json);
            return TkmTextUtils.keyBeanFromJson(json);

        } catch (JsonSyntaxException | IllegalBlockSizeException | BadPaddingException | HashEncodeException | HashAlgorithmNotFoundException | HashProviderNotFoundException | InvalidKeySpecException | NoSuchAlgorithmException ex) {
            throw new UnlockWalletException();
        }
    }

    /**
     * Spawns a refurbished wallet
     * @param words an ordered list of words used for wallet restore procedure
     * @param path is the path on device where the wallet will be restored and saved
     * @param filename the wallet internal name
     * @param cypher the wallet cypher
     * @param newPassword the new chosen password
     * @return Path where the wallet has been created
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws NoSuchPaddingException
     */
    public static Path importKeyFromWords(List<String> words, Path path, String filename, KeyContexts.WalletCypher cypher, String newPassword) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException {
        String seed;
        try {
            seed = SeedGenerator.generateSeedPWH(words);
            //System.out.println(seed);
            String concat = words.get(0);
            for (int i = 1; i < words.size(); i++) {
                concat += " " + words.get(i);
            }
            KeyBean kb = new KeyBean(KeyContexts.WALLET_JSON_AES, cypher, seed, concat);
            Path walltePath = writeKeyFile(path, filename, kb, newPassword);
            return walltePath;
        } catch (HashEncodeException | InvalidKeySpecException | HashAlgorithmNotFoundException | HashProviderNotFoundException | NoSuchProviderException | IllegalBlockSizeException | BadPaddingException | IOException ex) {
            Logger.getLogger(WalletHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static boolean verifyWordsIntegrity(List<String> words) {
        return SeedGenerator.verifySeedWords(words);
    }

    public static void writePublicKey(String walletname, String password, int keyIndex, WalletCypher cypher) throws InvalidCypherException, InvalidWalletIndexException, PublicKeySerializzationException, UnlockWalletException, IOException {
        switch (cypher) {
            case Ed25519BC:
                InstanceWalletKeyStoreBCED25519 wallet = new InstanceWalletKeyStoreBCED25519(walletname, password);
                String pk = wallet.getPublicKeyAtIndexURL64(keyIndex);
                PublicKeyBean pkb = new PublicKeyBean(cypher, KeyContexts.PUBLICKEY_CURRENT_VERSION, pk);
                FileHelper.writeStringToFile(FileHelper.getPublicKeyDirectoryPath(), walletname + FixedParameters.PUBLICKEY_EXTENSION, TkmTextUtils.toJson(pkb), false);
                break;
            default:
                throw new InvalidCypherException();
        }

    }

    public static PublicKeyBean readPublicKey(String publicKeyName) {
        try {
            String json = FileHelper.readStringFromFile(Paths.get(FileHelper.getPublicKeyDirectoryPath().toString(), publicKeyName + FixedParameters.PUBLICKEY_EXTENSION));
            return TkmTextUtils.publicKeyBeanFromJson(json);
        } catch (FileNotFoundException ex) {
            System.err.println(WalletError.PKEY_READ);
            return null;
        }
    }

    public static Map<String, PublicKeyBean> listPublicKeys() {
        Map<String, PublicKeyBean> ret = new LinkedHashMap<String, PublicKeyBean>();
        File folder = new File(FileHelper.getPublicKeyDirectoryPath().toString());
        File[] listOfFiles = folder.listFiles();
        for (File f : listOfFiles) {
            if (f.isFile()) {
                String p = f.getName().substring(0, f.getName().length() - PUBLICKEY_EXTENSION.length());
                ret.put(p, readPublicKey(p));
            }
        }
        return ret;
    }
}
