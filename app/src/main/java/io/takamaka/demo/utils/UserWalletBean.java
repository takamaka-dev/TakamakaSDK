package io.takamaka.demo.utils;

import io.takamaka.sdk.globalContext.KeyContexts;
import io.takamaka.sdk.utils.threadSafeUtils.TkmSignUtils;
import java.io.Serializable;
import java.util.Objects;

public class UserWalletBean implements Serializable, Comparable<UserWalletBean> {

    private String encryptedWallet = null;
    private KeyContexts.WalletCypher cypher = null;
    private boolean valid = false;
    private char[] walletmnemonicChars = null;
    private String internalName = "";

    public char[] getWalletmnemonicChars() {
        return walletmnemonicChars;
    }

    public void setWalletmnemonicChars(char[] walletmnemonicChars) {
        this.walletmnemonicChars = walletmnemonicChars;
    }

    public String getInternalName() {
        return internalName;
    }

    public void setInternalName(String internalName) {
        this.internalName = internalName;
    }

    public String getWalletmnemonic() {
        return TkmSignUtils.fromCharArrayToString(walletmnemonicChars);
    }

    public void setWalletmnemonic(String walletmnemonic) {
        this.walletmnemonicChars = TkmSignUtils.fromStringToCharArray(walletmnemonic);
    }

    public String getEncryptedWallet() {
        return encryptedWallet;
    }

    public void setEncryptedWallet(String encryptedWallet) {
        this.encryptedWallet = encryptedWallet;
    }

    public KeyContexts.WalletCypher getCypher() {
        return cypher;
    }

    public void setCypher(KeyContexts.WalletCypher cypher) {
        this.cypher = cypher;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + Objects.hashCode(this.encryptedWallet);
        hash = 11 * hash + Objects.hashCode(this.cypher);
        hash = 11 * hash + (this.valid ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserWalletBean other = (UserWalletBean) obj;
        if (this.valid != other.valid) {
            return false;
        }
        if (!Objects.equals(this.encryptedWallet, other.encryptedWallet)) {
            return false;
        }
        if (this.cypher != other.cypher) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(UserWalletBean o) {
        return encryptedWallet.compareTo(encryptedWallet);
    }

}
