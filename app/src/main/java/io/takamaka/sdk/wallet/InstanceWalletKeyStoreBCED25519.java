package io.takamaka.sdk.wallet;

import com.google.gson.JsonObject;

import io.takamaka.sdk.wallet.SeedGenerator;
import io.takamaka.sdk.wallet.beans.KeyBean;
import io.takamaka.sdk.exceptions.threadSafeUtils.HashAlgorithmNotFoundException;
import io.takamaka.sdk.exceptions.threadSafeUtils.HashEncodeException;
import io.takamaka.sdk.exceptions.threadSafeUtils.HashProviderNotFoundException;
import io.takamaka.sdk.exceptions.wallet.InvalidWalletIndexException;
import io.takamaka.sdk.exceptions.wallet.PublicKeySerializzationException;
import io.takamaka.sdk.exceptions.wallet.UnlockWalletException;
import io.takamaka.sdk.globalContext.KeyContexts;
import io.takamaka.sdk.main.defaults.DefaultInitParameters;
import io.takamaka.sdk.utils.FileHelper;
import io.takamaka.sdk.utils.SeededRandom;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.generators.Ed25519KeyPairGenerator;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.Ed25519KeyGenerationParameters;
import org.bouncycastle.crypto.params.Ed25519PublicKeyParameters;
import org.bouncycastle.util.encoders.UrlBase64;
import org.json.JSONObject;


public class InstanceWalletKeyStoreBCED25519 implements InstanceWalletKeystoreInterface {

    private Map<Integer, AsymmetricCipherKeyPair> signKeys;
    private Map<Integer, String> hexPublicKeys;
    private Map<Integer, byte[]> bytePublicKeys;
    private String seed;
    private String currentWalletName;
    private boolean isInitialized; //default to false
    private final static KeyContexts.WalletCypher walletCypher = KeyContexts.WalletCypher.Ed25519BC;
    private final Object constructorLock = new Object();
    private final Object getKeyPairAtIndexLock = new Object();
    private final Object getPublicKeyAtIndexHexLock = new Object();
    private final Object getPublicKeyAtIndexByteLock = new Object();

    @Override
    public KeyContexts.WalletCypher getWalletCypher() {
        return walletCypher;
    }

    @Override
    public String getCurrentWalletID() {
        return currentWalletName + walletCypher.name();
    }

    /**
     * Initialize the wallet by creating both the list of words and the seed.
     * @param walletName
     * @throws UnlockWalletException
     */
    public InstanceWalletKeyStoreBCED25519(String walletName) throws UnlockWalletException {
        synchronized (constructorLock) {
            if (!isInitialized) {
                try {
                    currentWalletName = walletName + DefaultInitParameters.WALLET_EXTENSION;
                    signKeys = Collections.synchronizedMap(new HashMap<Integer, AsymmetricCipherKeyPair>());
                    hexPublicKeys = Collections.synchronizedMap(new HashMap<Integer, String>());
                    bytePublicKeys = Collections.synchronizedMap(new HashMap<Integer, byte[]>());
                    initWallet("Password");
                } catch (IOException | NoSuchAlgorithmException | HashEncodeException | InvalidKeySpecException | HashAlgorithmNotFoundException | HashProviderNotFoundException ex) {
                    Logger.getLogger(InstanceWalletKeyStoreBCED25519.class.getName()).log(Level.SEVERE, null, ex);
                }
                isInitialized = true;
            }
        }

    }

    /**
     * Initialize the wallet by creating both the list of words and the seed.
     * @param walletName
     * @param password
     * @throws UnlockWalletException
     */
    public InstanceWalletKeyStoreBCED25519(String walletName, String password) throws UnlockWalletException {
        synchronized (constructorLock) {
            if (!isInitialized) {
                try {
                    currentWalletName = walletName + DefaultInitParameters.WALLET_EXTENSION;
                    signKeys = Collections.synchronizedMap(new HashMap<Integer, AsymmetricCipherKeyPair>());
                    hexPublicKeys = Collections.synchronizedMap(new HashMap<Integer, String>());
                    bytePublicKeys = Collections.synchronizedMap(new HashMap<Integer, byte[]>());
                    initWallet(password);
                } catch (IOException | NoSuchAlgorithmException | HashEncodeException | InvalidKeySpecException | HashAlgorithmNotFoundException | HashProviderNotFoundException ex) {
                    Logger.getLogger(InstanceWalletKeyStoreBCED25519.class.getName()).log(Level.SEVERE, null, ex);
                }
                isInitialized = true;
            }
        }

    }

    private void initWallet(String password) throws IOException, NoSuchAlgorithmException, HashEncodeException, InvalidKeySpecException, HashAlgorithmNotFoundException, HashProviderNotFoundException, UnlockWalletException {
        if (!FileHelper.walletDirExists()) {
            FileHelper.createDir(FileHelper.getDefaultWalletDirectoryPath());
        }

        if (!FileHelper.fileExists(Paths.get(FileHelper.getDefaultWalletDirectoryPath().toString(), currentWalletName))) {
            //System.out.println("no default wallet");
            List<String> words = SeedGenerator.generateWords();
            seed = SeedGenerator.generateSeedPWH(words);
            //System.out.println(seed);

            String concat = words.get(0);
            for (int i = 1; i < words.size(); i++) {
                concat += " " + words.get(i);
            }

            //System.out.println("words: "+ concat);
            KeyBean kb = new KeyBean("POWSEED", KeyContexts.WalletCypher.Ed25519BC, seed, concat);
            try {
                WalletHelper.writeKeyFile(FileHelper.getDefaultWalletDirectoryPath(), currentWalletName, kb, password);

                /*
                    String seed = SeedGenerator.generateSeedPWH(SeedGenerator.generateWords());
                    FileHelper.writeStringToFile(FileHelper.getDefaultWalletDirectoryPath(), currentWalletName, seed, false);
                 */
            } catch (NoSuchProviderException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
                Logger.getLogger(InstanceWalletKeyStoreBCED25519.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Path currentWalletPath = Paths.get(FileHelper.getDefaultWalletDirectoryPath().toString(), currentWalletName);

        if (FileHelper.fileExists(currentWalletPath)) {
            try {
                //System.out.println("loading " + currentWalletName + " wallet seed...");
                seed = WalletHelper.readKeyFile(currentWalletPath, password).getSeed();
                //System.out.println("seed loaded");
                //System.out.println(seed);
            } catch (InvalidAlgorithmParameterException | FileNotFoundException | NoSuchProviderException | NoSuchPaddingException | InvalidKeyException ex) {
                Logger.getLogger(InstanceWalletKeyStoreBCED25519.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public AsymmetricCipherKeyPair getKeyPairAtIndex(int i) throws InvalidWalletIndexException {
        if (!signKeys.containsKey(i)) {
            synchronized (getKeyPairAtIndexLock) {
                //call key creation
                Ed25519KeyPairGenerator keyPairGenerator = new Ed25519KeyPairGenerator();
                if (i < 0 || i >= Integer.MAX_VALUE) {
                    throw new InvalidWalletIndexException("index outside wallet range");
                }
                keyPairGenerator.init(new Ed25519KeyGenerationParameters(new SeededRandom(seed, KeyContexts.WALLET_KEY_CHAIN, i + 1)));
                signKeys.put(i, keyPairGenerator.generateKeyPair());
            }
        }
        return signKeys.get(i);
    }

    @Override
    public String getPublicKeyAtIndexURL64(int i) throws InvalidWalletIndexException, PublicKeySerializzationException {
        if (!hexPublicKeys.containsKey(i)) {
            synchronized (getPublicKeyAtIndexHexLock) {
                try {
                    AsymmetricCipherKeyPair keyPairAtIndex = getKeyPairAtIndex(i);
                    UrlBase64 b64e = new UrlBase64();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    AsymmetricKeyParameter aPublic = keyPairAtIndex.getPublic();
                    Ed25519PublicKeyParameters publicKey = (Ed25519PublicKeyParameters) aPublic;
                    b64e.encode(publicKey.getEncoded(), baos);
                    hexPublicKeys.put(i, baos.toString());
                    baos.close();
                } catch (IOException ex) {
                    System.err.println("Wallet can not serialize public key");
                    throw new PublicKeySerializzationException(ex);
                }

            }
        }
        return hexPublicKeys.get(i);
    }

    @Override
    public byte[] getPublicKeyAtIndexByte(int i) throws InvalidWalletIndexException, PublicKeySerializzationException {
        if (!bytePublicKeys.containsKey(i)) {
            synchronized (getPublicKeyAtIndexByteLock) {
                try {
                    AsymmetricCipherKeyPair keyPairAtIndex = getKeyPairAtIndex(i);
                    UrlBase64 b64e = new UrlBase64();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    AsymmetricKeyParameter aPublic = keyPairAtIndex.getPublic();
                    Ed25519PublicKeyParameters publicKey = (Ed25519PublicKeyParameters) aPublic;
                    b64e.encode(publicKey.getEncoded(), baos);
                    bytePublicKeys.put(i, baos.toByteArray());
                    baos.close();
                } catch (IOException ex) {
                    System.err.println("Wallet can not serialize public key");
                    throw new PublicKeySerializzationException(ex);
                }

            }
        }
        return bytePublicKeys.get(i);
    }

    @Override
    public int compareTo(InstanceWalletKeystoreInterface t) {
        return getCurrentWalletID().compareTo(t.getCurrentWalletID());
    }

}
