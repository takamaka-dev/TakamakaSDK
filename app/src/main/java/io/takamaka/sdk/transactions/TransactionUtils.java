package io.takamaka.sdk.transactions;

import io.takamaka.sdk.exceptions.wallet.WalletException;
import io.takamaka.sdk.globalContext.KeyContexts;
import io.takamaka.sdk.main.defaults.DefaultInitParameters;
import io.takamaka.sdk.utils.threadSafeUtils.TkmSignUtils;
import io.takamaka.sdk.utils.threadSafeUtils.TkmTK;
import io.takamaka.sdk.utils.threadSafeUtils.TkmTextUtils;
import io.takamaka.sdk.wallet.TransactionBox;
import io.takamaka.sdk.wallet.adapter.impl.SMHelper;
import java.math.BigInteger;
import java.util.Arrays;
import org.apache.commons.lang3.ArrayUtils;

/**
 *
 * @author iris.dimni
 */
public class TransactionUtils {

    /**
     * method takes a TransactionBean object in input and returns true if the
     * parameter and its internal fields are not null or empty, returns false
     * otherwise
     *
     * @param input
     * @return
     */
    public static TransactionSyntaxBean isTransactionBeanValid(TransactionBean input) {
        TransactionSyntaxBean result = new TransactionSyntaxBean();
        result.setItsv(KeyContexts.TransactionSyntaxValidity.NULL_TB);
        result.setValidSyntax(false);

        if (input == null) {
            return result;
        }
        result.setItsv(KeyContexts.TransactionSyntaxValidity.NULL_MANDATORY_FIELD);
        if (TkmTextUtils.isNullOrBlank(input.getPublicKey())
                || TkmTextUtils.isNullOrBlank(input.getMessage())
                || TkmTextUtils.isNullOrBlank(input.getRandomSeed())
                || TkmTextUtils.isNullOrBlank(input.getSignature())) {
            result.setValidSyntax(false);
            return result;
        }
        result.setValidSyntax(true);
        return result;
    }

    /**
     * method takes a InternalTransactionBean object in input and returns true
     * if the parameter and its mandatory internal fields are not null or empty,
     * returns false otherwise
     *
     * @param input
     * @return
     */
    public static InternalTransactionSyntaxBean isInternalTransactionBeanValid(InternalTransactionBean input) {
        InternalTransactionSyntaxBean result = new InternalTransactionSyntaxBean();
        result.setItsv(KeyContexts.InternalTransactionSyntaxValidity.NULL_ITB);
        result.setValidSyntax(false);

        if (input == null) {
            return result;
        }

        result.setItsv(KeyContexts.InternalTransactionSyntaxValidity.NULL_MANDATORY_FIELD);
        if (input.getTransactionType() == null
                || input.getNotBefore() == null
                || TkmTextUtils.isNullOrBlank(input.getTransactionHash())) {
            return result;
        } else {
            switch (input.getTransactionType()) {
                case ASSIGN_OVERFLOW:
                case UNASSIGN_OVERFLOW:
                    if (TkmTextUtils.isNullOrBlank(input.getTo())
                            || TkmTextUtils.isNullOrBlank(input.getFrom())) {
                        return result;
                    } else {
                        //to must be a valid url64
                        if (!ArrayUtils.contains(DefaultInitParameters.TO_DATA_LENGTH_WHITELIST, input.getTo().length())) {
                            //F.r("invalid length");
                            return result;
                        }
                        String toB64String = TkmSignUtils.fromB64UrlToHEX(input.getTo());
                        //F.r("decode data: " + toB64String);
                        if (TkmTextUtils.isNullOrBlank(toB64String)) {
                            return result;
                        }
                    }
                    break;

                case BLOB:
                    if (TkmTextUtils.isNullOrBlank(input.getMessage())
                            || TkmTextUtils.isNullOrBlank(input.getFrom())) {
                        return result;
                    }
                    break;
                case BLOCK:
                    //This type of transaction can't appear inside an InternalTransactionBean
                    result.setExtendedMessage("Found incompatible transaction type for an InternalTransactionBean: BLOCK");
                    return result;
                case BLOCK_HASH:
                case PREVIOUS_BLOCK:
                    if (TkmTextUtils.isNullOrBlank(input.getMessage())
                            || TkmTextUtils.isNullOrBlank(input.getFrom())
                            || input.getEpoch() == null
                            || input.getSlot() == null) {
                        return result;
                    }
                    break;
                case COINBASE:
                    if (TkmTextUtils.isNullOrBlank(input.getTo())
                            || input.getEpoch() == null
                            || input.getSlot() == null
                            || input.getGreenValue() == null) {
                        return result;
                    } else {
                        //to must be a valid url64
                        if (!ArrayUtils.contains(DefaultInitParameters.TO_DATA_LENGTH_WHITELIST, input.getTo().length())) {
                            //F.r("invalid length");
                            return result;
                        }
                        String toB64String = TkmSignUtils.fromB64UrlToHEX(input.getTo());
                        //F.r("decode data: " + toB64String);
                        if (TkmTextUtils.isNullOrBlank(toB64String)) {
                            return result;
                        }
                    }
                    break;
                case S_CONTRACT_DEPLOY:
                    if (TkmTextUtils.isNullOrBlank(input.getMessage())
                            || TkmTextUtils.isNullOrBlank(input.getFrom())) {
                        return result;
                    }
                    break;
                case S_CONTRACT_CALL:
                case S_CONTRACT_INSTANCE:
                    if (TkmTextUtils.isNullOrBlank(input.getMessage())
                            || TkmTextUtils.isNullOrBlank(input.getTo())
                            || TkmTextUtils.isNullOrBlank(input.getFrom())) {
                        return result;
                    }
                    break;
                case DECLARATION:
                    if (TkmTextUtils.isNullOrBlank(input.getTo())
                            || (input.getGreenValue() == null && input.getRedValue() == null)
                            || ((input.getGreenValue() != null && input.getGreenValue().compareTo(BigInteger.ZERO) <= 0) && (input.getRedValue() != null && input.getRedValue().compareTo(BigInteger.ZERO) <= 0))) {
                        return result;
                    } else {
                        //to must be a valid url64
                        if (!ArrayUtils.contains(DefaultInitParameters.TO_DATA_LENGTH_WHITELIST, input.getTo().length())) {
                            //F.r("invalid length");
                            return result;
                        }
                        String toB64String = TkmSignUtils.fromB64UrlToHEX(input.getTo());
                        //F.r("decode data: " + toB64String);
                        if (TkmTextUtils.isNullOrBlank(toB64String)) {
                            return result;
                        }
                    }
                    break;
                case DEREGISTER_MAIN:
                case DEREGISTER_OVERFLOW:
                case REGISTER_OVERFLOW:
                case REGISTER_MAIN:
                case STAKE_UNDO:
                    if (TkmTextUtils.isNullOrBlank(input.getFrom())) {
                        return result;
                    }
                    break;
                case PAY:
                    if (TkmTextUtils.isNullOrBlank(input.getTo())
                            || TkmTextUtils.isNullOrBlank(input.getFrom())
                            || ((input.getGreenValue() != null && input.getGreenValue().compareTo(BigInteger.ZERO) <= 0) && (input.getRedValue() != null && input.getRedValue().compareTo(BigInteger.ZERO) <= 0))) {
                        return result;
                    } else {
                        //to must be a valid url64
                        String toB64String = TkmSignUtils.fromB64UrlToHEX(input.getTo());
                        if (TkmTextUtils.isNullOrBlank(toB64String)) {
                            return result;
                        }
                    }
                    break;
                case STAKE:
                    if (TkmTextUtils.isNullOrBlank(input.getTo())
                            || TkmTextUtils.isNullOrBlank(input.getFrom())
                            || input.getGreenValue() == null
                            || ((input.getGreenValue().compareTo(BigInteger.ZERO) <= 0) | (input.getGreenValue().compareTo(TkmTK.unitTK(DefaultInitParameters.MINIMUM_STAKE_BET_UNIT)) < 0))) {
                        return result;
                    } else {
                        //to must be a valid url64
                        if (!ArrayUtils.contains(DefaultInitParameters.TO_DATA_LENGTH_WHITELIST, input.getTo().length())) {
                            //F.r("invalid length");
                            return result;
                        }
                        String toB64String = TkmSignUtils.fromB64UrlToHEX(input.getTo());
                        //F.r("decode data: " + toB64String);
                        if (TkmTextUtils.isNullOrBlank(toB64String)) {
                            return result;
                        }
                    }
                    break;
                case STATE_POINTER_TRANSACTION:
                    if (TkmTextUtils.isNullOrBlank(input.getMessage())
                            || TkmTextUtils.isNullOrBlank(input.getFrom())
                            || TkmTextUtils.isNullOrBlank(input.getTo())
                            || input.getEpoch() == null
                            || input.getSlot() == null) {
                        return result;
                    }
                    break;

                case REQUEST_BLOCK:
                case RETURN_BLOCK:
                case BLOCK_CLOSE:
                case UNDEFINED:
                default:
                    return result;
            }
        }

        result.setValidSyntax(true);
        return result;
    }

}
