package io.takamaka.sdk.wallet;

import io.takamaka.sdk.exceptions.wallet.WalletException;
import io.takamaka.sdk.globalContext.KeyContexts;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;

public interface InstanceWalletKeystoreInterface extends Comparable<InstanceWalletKeystoreInterface> {

    /**
     *
     * @return the wallet cypher
     */
    KeyContexts.WalletCypher getWalletCypher();

    /**
     * @param i
     * @return the key pair public and private address related to the index
     * @throws WalletException
     */
    AsymmetricCipherKeyPair getKeyPairAtIndex(int i) throws WalletException;

    /**
     * @param i
     * @return the public key as byte array
     * @throws WalletException
     */
    byte[] getPublicKeyAtIndexByte(int i) throws WalletException;

    /**
     * @param i
     * @return the public key as URL64 image String
     * @throws WalletException
     */
    String getPublicKeyAtIndexURL64(int i) throws WalletException;

    /**
     * @return the current wallet ID
     */
    String getCurrentWalletID();

}
