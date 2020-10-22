package io.takamaka.sdk.wallet;

import io.takamaka.sdk.exceptions.threadSafeUtils.HashCompositionException;
import io.takamaka.sdk.exceptions.threadSafeUtils.InclusionHashCreationException;
import io.takamaka.sdk.exceptions.threadSafeUtils.NullInternalTransactionBeanException;
import io.takamaka.sdk.exceptions.wallet.TransactionCanNotBeCreatedException;
import io.takamaka.sdk.exceptions.wallet.TransactionCanNotBeSignedException;
import io.takamaka.sdk.exceptions.wallet.WalletException;
import io.takamaka.sdk.transactions.InternalTransactionBean;
import io.takamaka.sdk.transactions.TransactionBean;
import io.takamaka.sdk.utils.threadSafeUtils.TkmTextUtils;
import java.util.logging.Level;

/**
 *
 * @author giovanni.antino@h2tcoin.com
 */
public class TkmWallet {

    /**
     * It returns the Chrypted envelope used later in TKM chain
     * @param itb Java Bean Class - it contains all the transaction elements needed
     * @param iwk is an abstract layer that represents the involved wallet
     * @param signKey is the chosen index needed for the creation of the wallet public address
     * @return TransactionBean - Java Bean Class - it contains the wrapped elements located inside the itb object
     * @throws TransactionCanNotBeCreatedException
     */
    public static TransactionBean createGenericTransaction(InternalTransactionBean itb, InstanceWalletKeystoreInterface iwk, int signKey) throws TransactionCanNotBeCreatedException {
        try {
            //todo verify itb transaction
            itb.setTransactionHash(TkmTextUtils.internalTransactionBeanHash(itb));
            TransactionBean tb = new TransactionBean();
            tb.setRandomSeed(TkmTextUtils.generateWalletRandomString());
            tb.setPublicKey(iwk.getPublicKeyAtIndexURL64(signKey));
            tb.setMessage(TkmTextUtils.toJson(itb));
            tb.setWalletCypher(iwk.getWalletCypher());
            TkmCypherBean signatureBean = new TkmCypherBean();

            switch (tb.getWalletCypher()) {
                case Ed25519BC:
                    signatureBean = TkmCypherProviderBCED25519.sign(iwk.getKeyPairAtIndex(signKey), tb.getMessage() + tb.getRandomSeed() + tb.getWalletCypher().name());
                    if (!signatureBean.isValid()) {
                        throw new TransactionCanNotBeSignedException(signatureBean.getEx());
                    }
                    break;
                default:
                    signatureBean.setValid(false);
                    //signatureBean.setEx("UNKNOWN CYPHER");
            }

            tb.setSignature(signatureBean.getSignature());
            return tb;

        } catch (NullInternalTransactionBeanException | HashCompositionException | WalletException ex) {
            throw new TransactionCanNotBeCreatedException(ex);
        }

    }

    /**
     * It verifies the Transaction Bean content going through all the wrapped chain elements parsed
     *
     * use this function with caution, check for null values. For transaction
     * integrity check use {@code verifyTransactionIntegrity}, for block
     * integrity check use {@code BlockUtils.decodeBlock(String blockJson)}
     *
     * @param tb - Java Class Bean - Chrypted envelope
     * @return TkmCypherBean
     */
    public static TkmCypherBean verifySign(TransactionBean tb) {
        TkmCypherBean tcb = null;
        switch (tb.getWalletCypher()) {
            case Ed25519BC:
                tcb = TkmCypherProviderBCED25519.verify(tb.getPublicKey(), tb.getSignature(), tb.getMessage() + tb.getRandomSeed() + tb.getWalletCypher().name());
                break;

            default:
                tcb = new TkmCypherBean();
                tcb.setValid(false);
        }
        if (!tcb.isValid()) {
        }
        return tcb;
    }

    public static TransactionBox verifyTransactionIntegrity(String transactionJson) {
        TransactionBean tb = TkmTextUtils.transactionBeanFromJson(transactionJson);
        TransactionBox veriTransactionIntegrity = verifyTransactionIntegrity(tb, transactionJson);
        return veriTransactionIntegrity;
    }

    /**
     * It creates a transaction box containing both Transaction bean and itb message
     * Only when the verifySign method sets a valid transaction
     * @param tb Java Class Bean - Chrypted envelope
     * @param transactionJson
     * @return
     */
    public static TransactionBox verifyTransactionIntegrity(TransactionBean tb, String transactionJson) {
        TransactionBox result = new TransactionBox();
        if (tb != null) {
            TkmCypherBean cyBean = verifySign(tb);
            if (cyBean.isValid()) {
                //this function call syntax check
                InternalTransactionBean itb = TkmTextUtils.internalTransactionBeanFromJson(tb.getMessage());
                //if itb null syntax error
                boolean signerFieldsVerification = verifySigner(tb, itb);
                if (!signerFieldsVerification) {
                    //syntax check error add print here for debug
                }
                if (itb != null & signerFieldsVerification) {

                    try {
                        String sith = TkmTextUtils.singleTransactionInclusionHash(itb.getTransactionHash(), tb.getPublicKey(), tb.getSignature(), tb.getRandomSeed(), tb.getWalletCypher().name());
                        result.setSingleInclusionTransactionHash(sith);
                        result.setTb(tb);
                        result.setItb(itb);
                        result.setTransactionJson(transactionJson);
                        result.setValid(true);
                    } catch (InclusionHashCreationException ex) {
                    }
                }
            } else {
                if (cyBean != null && cyBean.getEx() != null) {
                    cyBean.getEx().printStackTrace();
                }
            }
        }
        return result;
    }


    /**
     * It creates a transaction box containing both Transaction bean and itb message
     * Only when the verifySign method sets a valid transaction
     * @param tb Java Class Bean - Chrypted envelope
     * @return
     */
    public static TransactionBox verifyTransactionIntegrity(TransactionBean tb) {
        TransactionBox result = new TransactionBox();
        if (tb != null) {
            TkmCypherBean cyBean = verifySign(tb);//here
            if (cyBean.isValid()) {
                InternalTransactionBean itb = TkmTextUtils.internalTransactionBeanFromJson(tb.getMessage());
                boolean signerFieldsVerification = verifySigner(tb, itb);
                if (!signerFieldsVerification) {
                }
                if (itb != null & signerFieldsVerification) {

                    try {
                        String sith = TkmTextUtils.singleTransactionInclusionHash(itb.getTransactionHash(), tb.getPublicKey(), tb.getSignature(), tb.getRandomSeed(), tb.getWalletCypher().name());
                        result.setSingleInclusionTransactionHash(sith);
                        result.setTb(tb);
                        result.setItb(itb);
                        result.setTransactionJson(TkmTextUtils.toJson(tb));
                        result.setValid(true);
                    } catch (InclusionHashCreationException ex) {
                    }
                }
            } else {
            }
        }
        return result;
    }

    private static boolean verifySigner(TransactionBean tb, InternalTransactionBean itb) {
        if (itb == null | tb == null) {
            return false;
        } else {
            switch (itb.getTransactionType()) {
                // signer == form
                case ASSIGN_OVERFLOW:
                case BLOB:
                case BLOCK_HASH:
                case S_CONTRACT_CALL:
                case S_CONTRACT_DEPLOY:
                case S_CONTRACT_INSTANCE:
                case PREVIOUS_BLOCK:
                case DEREGISTER_MAIN:
                case DEREGISTER_OVERFLOW:
                case REGISTER_MAIN:
                case REGISTER_OVERFLOW:
                case STAKE:
                case STAKE_UNDO:
                case STATE_POINTER_TRANSACTION:
                case UNASSIGN_OVERFLOW:
                case PAY:
                    return itb.getFrom().equals(tb.getPublicKey());
                //signer == to
                case DECLARATION:
                    //no check on declarations
                    return true;
                case COINBASE:
                    return itb.getTo().equals(tb.getPublicKey());
                case BLOCK:
                case UNDEFINED:
                default:
                    return false;
            }
        }
    }

}
