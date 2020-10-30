package io.takamaka.sdk.utils.threadSafeUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.takamaka.demo.utils.UserWalletBean;
import io.takamaka.sdk.globalContext.FixedParameters;
import io.takamaka.sdk.main.defaults.DefaultInitParameters;
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
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Thread safe methods for use
 *
 * @author giovanni.antino@h2tcoin.com
 */
public class TkmTextUtils {
    /**
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
     * @param tb
     * @return
     */
    public static String toJson(TransactionBean tb) {
        Gson gson = new Gson();
        return gson.toJson(tb, TransactionBean.class);
    }

    /**
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
     * <p>
     * sb.append(itb.getTo());<br>
     * <p>
     * sb.append(itb.getMessage());<br>
     * <p>
     * sb.append(itb.getNotBefore().getTime());<br>
     * <p>
     * sb.append(itb.getRedValue());<br>
     * <p>
     * sb.append(itb.getGreenValue());<br>
     * <p>
     * sb.append(itb.getTransactionType().name());<br>
     * <p>
     * sb.append(itb.getEpoch());<br>
     * <p>
     * sb.append(itb.getSlot());<br>
     * <p>
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
            String hash = io.takamaka.sdk.utils.TkmSignUtils.Hash256(sb.toString());
            System.out.println("hashone");
            return hash;
        } catch (HashEncodeException | HashAlgorithmNotFoundException | HashProviderNotFoundException ex) {
            System.out.println("va in eccezione");
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
            return io.takamaka.sdk.utils.TkmSignUtils.Hash256(itbHash + addr + sig + random + walletCypher);
        } catch (HashEncodeException | HashAlgorithmNotFoundException | HashProviderNotFoundException ex) {
            throw new InclusionHashCreationException(ex);
        }
    }

    public static UserWalletBean getUserWalletBeanFromJson(String jsonString) {
        Gson gson = new Gson();
        UserWalletBean fb = gson.fromJson(jsonString, UserWalletBean.class);
        return fb;
    }

    public static final String formatNumber(String nanoTK) {
        try {
            Locale l = Locale.getDefault();
            NumberFormat formatter = NumberFormat.getNumberInstance(l);
            BigDecimal movePointLeft = new BigDecimal(nanoTK).movePointLeft(DefaultInitParameters.NUMBER_OF_ZEROS);
            //String format = NumberFormat.getNumberInstance(l).format(movePointLeft);
            DecimalFormatSymbols dcs = DecimalFormatSymbols.getInstance(l);
            char decimalSeparator = dcs.getDecimalSeparator();
            char groupingSeparator = dcs.getGroupingSeparator();
            BigInteger integerPart = movePointLeft.toBigInteger().mod(new BigInteger("10").pow(DefaultInitParameters.NUMBER_OF_ZEROS));
            String formattedIntegerPArt = NumberFormat.getNumberInstance(l).format(integerPart);
            //movePointLeft
            String toString = movePointLeft.toPlainString();
            String[] split = StringUtils.split(toString, ".");
            String res = formattedIntegerPArt + decimalSeparator + split[1];
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static final String formatNumberWithShift(String nanoTK, int decimals) {
        //NumberFormat formatter = NumberFormat.getNumberInstance(l);
        Locale l = Locale.getDefault();
        return formatNumberWithShift(nanoTK, decimals, l);
    }

    public static final String formatNumberWithShift(String nanoTK, int decimals, Locale l) {
        //NumberFormat formatter = NumberFormat.getNumberInstance(l);
        BigDecimal movePointLeft = new BigDecimal(nanoTK).movePointLeft(DefaultInitParameters.NUMBER_OF_ZEROS);
        MathContext mc = new MathContext(decimals);
        BigDecimal bd = movePointLeft.round(mc);
        //String format = NumberFormat.getNumberInstance(l).format(bd);
        DecimalFormatSymbols dcs = DecimalFormatSymbols.getInstance(l);
        char decimalSeparator = dcs.getDecimalSeparator();
        char groupingSeparator = dcs.getGroupingSeparator();


        DecimalFormat df = new DecimalFormat("#,##0.00");
        DecimalFormatSymbols decimalFormatSymbols = df.getDecimalFormatSymbols();
        decimalFormatSymbols.setGroupingSeparator(groupingSeparator);
        decimalFormatSymbols.setDecimalSeparator(decimalSeparator);
        df.setDecimalFormatSymbols(decimalFormatSymbols);
        //DecimalFormat df = new DecimalFormat("#,###.00");
        System.out.println("Format finale " + df.format(bd));
        return df.format(bd);
        /*
        BigInteger integerPart = bd.toBigInteger().mod(new BigInteger("10").pow(DefaultInitParameters.NUMBER_OF_ZEROS));
        String formattedIntegerPArt = NumberFormat.getNumberInstance(l).format(integerPart);
        //movePointLeft
        String toString = bd.toPlainString();
        System.out.println("Valore nella home: " + toString);

        String[] split = StringUtils.split(toString, ".");
        String res = formattedIntegerPArt + decimalSeparator + split[1];
        return res;
        */
    }

    public static final String formatNumber(String nanoTK, Locale l) {
        try {
            NumberFormat formatter = NumberFormat.getNumberInstance(l);
            BigDecimal movePointLeft = new BigDecimal(nanoTK).movePointLeft(DefaultInitParameters.NUMBER_OF_ZEROS);
            String format = NumberFormat.getNumberInstance(l).format(movePointLeft);
            DecimalFormatSymbols dcs = DecimalFormatSymbols.getInstance(l);
            char decimalSeparator = dcs.getDecimalSeparator();
            char groupingSeparator = dcs.getGroupingSeparator();
            BigInteger integerPart = movePointLeft.toBigInteger().mod(new BigInteger("10").pow(DefaultInitParameters.NUMBER_OF_ZEROS));
            String formattedIntegerPArt = NumberFormat.getNumberInstance(l).format(integerPart);
            //movePointLeft
            String toString = movePointLeft.toPlainString();
            String[] split = StringUtils.split(toString, ".");
            String res = formattedIntegerPArt + decimalSeparator + split[1];
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static final String applyLocale(BigDecimal nanoTK, Locale l) {
        try {
            NumberFormat formatter = NumberFormat.getNumberInstance(l);
            String format = NumberFormat.getNumberInstance(l).format(nanoTK);
            DecimalFormatSymbols dcs = DecimalFormatSymbols.getInstance(l);
            char decimalSeparator = dcs.getDecimalSeparator();
            char groupingSeparator = dcs.getGroupingSeparator();
            BigInteger integerPart = nanoTK.toBigInteger().mod(new BigInteger("10").pow(DefaultInitParameters.NUMBER_OF_ZEROS));
            String formattedIntegerPArt = NumberFormat.getNumberInstance(l).format(integerPart);
            //movePointLeft
            String toString = nanoTK.toPlainString();
            String[] split = StringUtils.split(toString, ".");
            String res = formattedIntegerPArt + decimalSeparator + split[1];
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static final String roundDecimal(String value, int decimals) {
        return roundDecimal(value, decimals, Locale.getDefault());
    }

    public static final String roundDecimal(String value, int decimals, Locale l) {
        MathContext mc = new MathContext(decimals);
        BigDecimal bd = new BigDecimal(value).round(mc);
        return applyLocale(bd, l);
    }


    public static BigInteger validateBI(String sValue) {
        BigInteger g = null;
        if (!isNullOrBlank(sValue)) {
            sValue = sValue.trim();
            try {
                BigDecimal gb = new BigDecimal("" + sValue);
                BigDecimal gbWei = gb.movePointRight(DefaultInitParameters.NUMBER_OF_ZEROS);
                g = gbWei.toBigInteger();
            } catch (Exception e) {
                //Log.logStacktrace(Level.INFO, e);
                //val.setText("");
            }
        }
        return g;
    }
}
