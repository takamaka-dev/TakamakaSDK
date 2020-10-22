package io.takamaka.sdk.utils.threadSafeUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.takamaka.sdk.globalContext.FixedParameters;
import io.takamaka.sdk.transactions.TransactionUtils;
import io.takamaka.sdk.wallet.beans.KeyBean;
import io.takamaka.sdk.wallet.beans.EncKeyBean;
import io.takamaka.sdk.wallet.beans.PublicKeyBean;
import io.takamaka.sdk.exceptions.threadSafeUtils.NullInternalTransactionBeanException;
import io.takamaka.sdk.exceptions.threadSafeUtils.HashCompositionException;
import io.takamaka.sdk.transactions.InternalTransactionBean;
import io.takamaka.sdk.exceptions.threadSafeUtils.HashAlgorithmNotFoundException;
import io.takamaka.sdk.exceptions.threadSafeUtils.HashEncodeException;
import io.takamaka.sdk.exceptions.threadSafeUtils.HashProviderNotFoundException;
import io.takamaka.sdk.exceptions.threadSafeUtils.InclusionHashCreationException;
import io.takamaka.sdk.transactions.TransactionBean;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigInteger;

/**
 * Thread safe methods for use
 *
 * @author giovanni.antino@h2tcoin.com
 */
public class TkmTextUtils {
    /**
     *
     * @param text
     * @return true if text is null or blank
     */
    public static boolean isNullOrBlank(String text) {
        return (text == null
                || ("".equals(text.trim()))
                || (text.isEmpty()));
    }

    public static String toJson(KeyBean key) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(key, KeyBean.class);
    }

    public static String toJson(EncKeyBean key) {
        Gson gson = new Gson();
        return gson.toJson(key, EncKeyBean.class);
    }

    /**
     *
     * @param tb
     * @return
     */
    public static String toJson(TransactionBean tb) {
        Gson gson = new Gson();
        return gson.toJson(tb, TransactionBean.class);
    }

    /**
     *
     * @param itb
     * @return
     */
    public static String toJson(InternalTransactionBean itb) {
        Gson gson = new Gson();
        return gson.toJson(itb, InternalTransactionBean.class);
    }

    public static EncKeyBean enckeyBeanFromJson(String encKeyBeanJson) {
        Gson gson = new Gson();
        return gson.fromJson(encKeyBeanJson, EncKeyBean.class);
    }

    public static KeyBean keyBeanFromJson(String keyBeanJson) {
        Gson gson = new Gson();
        return gson.fromJson(keyBeanJson, KeyBean.class);
    }

    public static PublicKeyBean publicKeyBeanFromJson(String publickeyBeanJson) {
        Gson gson = new Gson();
        return gson.fromJson(publickeyBeanJson, PublicKeyBean.class);
    }

    public static String toJson(PublicKeyBean key) {
        Gson gson = new Gson();
        return gson.toJson(key, PublicKeyBean.class);
    }

    /**
     * ITB Hash. It puts all the itb fields together, than it generates the hash
     * code
     * <br>
     * StringBuilder sb = new StringBuilder();
     * <br>
     * sb.append(itb.getFrom());<br>
     *
     * sb.append(itb.getTo());<br>
     *
     * sb.append(itb.getMessage());<br>
     *
     * sb.append(itb.getNotBefore().getTime());<br>
     *
     * sb.append(itb.getRedValue());<br>
     *
     * sb.append(itb.getGreenValue());<br>
     *
     * sb.append(itb.getTransactionType().name());<br>
     *
     * sb.append(itb.getEpoch());<br>
     *
     * sb.append(itb.getSlot());<br>
     *
     * String hash = TkmSignUtils.Hash256(sb.toString());<br>
     *
     * @param itb
     * @return String the hash encoded
     * @throws NullInternalTransactionBeanException
     * @throws HashCompositionException
     */
    public static String internalTransactionBeanHash(InternalTransactionBean itb) throws NullInternalTransactionBeanException, HashCompositionException {
        try {
            if (itb == null) {
                throw new NullInternalTransactionBeanException("null itb");
            }
            StringBuilder sb = new StringBuilder();
            sb.append(itb.getFrom());
            sb.append(itb.getTo());
            sb.append(itb.getMessage());
            sb.append(itb.getNotBefore().getTime());
            sb.append(itb.getRedValue());
            sb.append(itb.getGreenValue());
            sb.append(itb.getTransactionType().name());
            sb.append(itb.getEpoch());
            sb.append(itb.getSlot());
            //F.y(sb.toString());
            String hash = TkmSignUtils.Hash256(sb.toString());
            return hash;
        } catch (HashEncodeException | HashAlgorithmNotFoundException | HashProviderNotFoundException ex) {
            throw new HashCompositionException(ex);
        }
    }

    /**
     * Transaction Bean Random string generator
     *
     * @return a 4 digit alphanumeric random string
     */
    public static String generateWalletRandomString() {
        return RandomStringUtils.randomAlphanumeric(4);
    }

    /**
     *
     * @param transactionBeanJson
     * @return
     */
    public static TransactionBean transactionBeanFromJson(String transactionBeanJson) {
        Gson gson = new Gson();
        TransactionBean result = new TransactionBean();
        result = gson.fromJson(transactionBeanJson, TransactionBean.class);
        if (TransactionUtils.isTransactionBeanValid(result).isValidSyntax()) {
            return result;
        }
        return null;
    }

    /**
     * function for sorting hash
     *
     * @param input
     * @return
     */
    public static String getSortingString(String input) {
        return new BigInteger(1, input.getBytes(FixedParameters.CHARSET)).toString();
    }

    /**
     *
     * @param internalTransactionBeanJson
     * @return
     */
    public static InternalTransactionBean internalTransactionBeanFromJson(String internalTransactionBeanJson) {
        Gson gson = new Gson();
        InternalTransactionBean result = new InternalTransactionBean();
        result = gson.fromJson(internalTransactionBeanJson, InternalTransactionBean.class);
        if (TransactionUtils.isInternalTransactionBeanValid(result).isValidSyntax()) {
            return result;
        }
        return null;
    }

    /**
     * Hash used inside block for transaction identification
     *
     * @param itbHash
     * @param addr
     * @param sig
     * @param random
     * @param walletCypher
     * @return
     * @throws InclusionHashCreationException
     */
    public static String singleTransactionInclusionHash(String itbHash, String addr, String sig, String random, String walletCypher) throws InclusionHashCreationException {
        try {
            return TkmSignUtils.Hash256(itbHash + addr + sig + random + walletCypher);
        } catch (HashEncodeException | HashAlgorithmNotFoundException | HashProviderNotFoundException ex) {
            throw new InclusionHashCreationException(ex);
        }
    }
}
