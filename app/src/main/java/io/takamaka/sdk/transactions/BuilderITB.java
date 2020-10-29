package io.takamaka.sdk.transactions;

import io.takamaka.sdk.exceptions.threadSafeUtils.HashCompositionException;
import io.takamaka.sdk.exceptions.threadSafeUtils.NullInternalTransactionBeanException;
import io.takamaka.sdk.globalContext.KeyContexts;
import io.takamaka.sdk.utils.threadSafeUtils.TkmTextUtils;
import io.takamaka.sdk.block.InternalBlockBean;
import io.takamaka.sdk.block.PrivateBlockTxBean;
import io.takamaka.sdk.block.TkmRewardBean;
import java.math.BigInteger;
import java.util.Date;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.logging.Level;


public class BuilderITB {

    private static InternalTransactionBean common(KeyContexts.TransactionType tType, Date notBefore) {
        InternalTransactionBean result = new InternalTransactionBean();
        result.setTransactionType(tType);
        result.setNotBefore(notBefore);
        System.out.println("Internal transaction bean primo: " + result);
        return result;
    }

    /**
     *
     * @param from
     * @param to
     * @param greenValue
     * @param redValue
     * @param message
     * @param notBefore
     * @return
     */
    public static InternalTransactionBean pay(String from, String to, BigInteger greenValue, BigInteger redValue, String message, Date notBefore) {

        try {
            InternalTransactionBean result = BuilderITB.common(KeyContexts.TransactionType.PAY, notBefore);
            System.out.println("Internal transaction bean secondo: " + result);
            result.setFrom(from);
            result.setTo(to);
            result.setGreenValue(greenValue);
            result.setRedValue(redValue);
            result.setMessage(message);
            result.setTransactionHash(TkmTextUtils.internalTransactionBeanHash(result));

            return result;
        } catch (NullInternalTransactionBeanException | HashCompositionException ex) {
            return null;
        }
    }

    /**
     *
     * @param from
     * @param to
     * @param greenValue
     * @param redValue
     * @param message
     * @return
     */
    public static InternalTransactionBean pay(String from, String to, BigInteger greenValue, BigInteger redValue, String message) {
        return BuilderITB.pay(from, to, greenValue, redValue, message, new Date());
    }

    /**
     *
     * @param from
     * @param to
     * @param greenValue
     * @param message
     * @param notBefore
     * @return
     */
    public static InternalTransactionBean stake(String from, String to, BigInteger greenValue, String message, Date notBefore) {

        try {
            InternalTransactionBean result = BuilderITB.common(KeyContexts.TransactionType.STAKE, notBefore);
            result.setFrom(from);
            result.setTo(to);
            result.setGreenValue(greenValue);
            result.setMessage(message);
            result.setTransactionHash(TkmTextUtils.internalTransactionBeanHash(result));

            return result;
        } catch (NullInternalTransactionBeanException | HashCompositionException ex) {
            return null;
        }
    }

    /**
     *
     * @param from holder address
     * @param to destination main address
     * @param greenValue token in mTKG
     * @param message
     * @return
     */
    public static InternalTransactionBean stake(String from, String to, BigInteger greenValue, String message) {
        return BuilderITB.stake(from, to, greenValue, message, new Date());
    }

    /**
     *
     * @param from
     * @param message
     * @param notBefore
     * @return
     */
    public static InternalTransactionBean stakeUndo(String from, String message, Date notBefore) {

        try {
            InternalTransactionBean result = BuilderITB.common(KeyContexts.TransactionType.STAKE_UNDO, notBefore);
            result.setFrom(from);
            result.setMessage(message);
            result.setTransactionHash(TkmTextUtils.internalTransactionBeanHash(result));

            return result;
        } catch (NullInternalTransactionBeanException | HashCompositionException ex) {
            return null;
        }
    }

    /**
     *
     * @param from
     * @param message
     * @return
     */
    public static InternalTransactionBean stakeUndo(String from, String message) {
        return BuilderITB.stakeUndo(from, message, new Date());
    }

    /**
     *
     * @param from
     * @param to
     * @param message
     * @param notBefore
     * @return
     */
    public static InternalTransactionBean blob(String from, String to, String message, Date notBefore) {

        try {
            InternalTransactionBean result = BuilderITB.common(KeyContexts.TransactionType.BLOB, notBefore);
            result.setFrom(from);
            result.setMessage(message);
            result.setTransactionHash(TkmTextUtils.internalTransactionBeanHash(result));

            return result;
        } catch (NullInternalTransactionBeanException | HashCompositionException ex) {
            return null;
        }
    }

    /**
     *
     * @param from
     * @param to
     * @param message
     * @return
     */
    public static InternalTransactionBean blob(String from, String to, String message) {
        return BuilderITB.blob(from, to, message, new Date());
    }

    /**
     *
     * @param to
     * @param greenValue
     * @param redValue
     * @param message
     * @param notBefore
     * @return
     */
    public static InternalTransactionBean declaration(String to, BigInteger greenValue, BigInteger redValue, String message, Date notBefore) {

        try {
            InternalTransactionBean result = BuilderITB.common(KeyContexts.TransactionType.DECLARATION, notBefore);
            result.setTo(to);
            result.setGreenValue(greenValue);
            result.setRedValue(redValue);
            result.setMessage(message);
            result.setTransactionHash(TkmTextUtils.internalTransactionBeanHash(result));

            return result;
        } catch (NullInternalTransactionBeanException | HashCompositionException ex) {
            return null;
        }
    }

    /**
     *
     * @param to
     * @param greenValue
     * @param redValue
     * @param message
     * @return
     */
    public static InternalTransactionBean declaration(String to, BigInteger greenValue, BigInteger redValue, String message) {
        return BuilderITB.declaration(to, greenValue, redValue, message, new Date());
    }

    /**
     *
     * @param from
     * @param message
     * @param notBefore
     * @return
     */
    public static InternalTransactionBean registerMain(String from, String message, Date notBefore) {

        try {
            InternalTransactionBean result = BuilderITB.common(KeyContexts.TransactionType.REGISTER_MAIN, notBefore);
            result.setFrom(from);
            result.setMessage(message);
            result.setTransactionHash(TkmTextUtils.internalTransactionBeanHash(result));

            return result;
        } catch (NullInternalTransactionBeanException | HashCompositionException ex) {
            return null;
        }
    }

    /**
     *
     * @param from
     * @param message
     * @return
     */
    public static InternalTransactionBean registerMain(String from, String message) {
        return BuilderITB.registerMain(from, message, new Date());
    }

    /**
     *
     * @param from
     * @param message
     * @param notBefore
     * @return
     */
    public static InternalTransactionBean registerOverflow(String from, String message, Date notBefore) {

        try {
            InternalTransactionBean result = BuilderITB.common(KeyContexts.TransactionType.REGISTER_OVERFLOW, notBefore);
            result.setFrom(from);
            result.setMessage(message);
            result.setTransactionHash(TkmTextUtils.internalTransactionBeanHash(result));

            return result;
        } catch (NullInternalTransactionBeanException | HashCompositionException ex) {
            return null;
        }
    }

    /**
     *
     * @param from
     * @param message
     * @return
     */
    public static InternalTransactionBean registerOverflow(String from, String message) {
        return BuilderITB.registerOverflow(from, message, new Date());
    }

    /**
     *
     * @param from
     * @param message
     * @param notBefore
     * @return
     */
    public static InternalTransactionBean deregisterOverflow(String from, String message, Date notBefore) {

        try {
            InternalTransactionBean result = BuilderITB.common(KeyContexts.TransactionType.DEREGISTER_OVERFLOW, notBefore);
            result.setFrom(from);
            result.setMessage(message);
            result.setTransactionHash(TkmTextUtils.internalTransactionBeanHash(result));

            return result;
        } catch (NullInternalTransactionBeanException | HashCompositionException ex) {
            return null;
        }
    }

    /**
     *
     * @param from
     * @param message
     * @return
     */
    public static InternalTransactionBean deregisterOverflow(String from, String message) {
        return BuilderITB.deregisterOverflow(from, message, new Date());
    }

    /**
     *
     * @param from
     * @param message
     * @param notBefore
     * @return
     */
    public static InternalTransactionBean deregisterMain(String from, String message, Date notBefore) {

        try {
            InternalTransactionBean result = BuilderITB.common(KeyContexts.TransactionType.DEREGISTER_MAIN, notBefore);
            result.setFrom(from);
            result.setMessage(message);
            result.setTransactionHash(TkmTextUtils.internalTransactionBeanHash(result));

            return result;
        } catch (NullInternalTransactionBeanException | HashCompositionException ex) {
            return null;
        }
    }

    /**
     *
     * @param from
     * @param message
     * @return
     */
    public static InternalTransactionBean deregisterMain(String from, String message) {
        return BuilderITB.deregisterMain(from, message, new Date());
    }

    /**
     * assign the overflow address (to) to the main address (from)
     *
     * @param from main address
     * @param to overflow addess
     * @param message
     * @param notBefore
     * @return
     */
    public static InternalTransactionBean assignOverflow(String from, String to, String message, Date notBefore) {

        try {
            InternalTransactionBean result = BuilderITB.common(KeyContexts.TransactionType.ASSIGN_OVERFLOW, notBefore);
            result.setFrom(from);
            result.setTo(to);
            result.setMessage(message);
            result.setTransactionHash(TkmTextUtils.internalTransactionBeanHash(result));

            return result;
        } catch (NullInternalTransactionBeanException | HashCompositionException ex) {
            return null;
        }
    }

    /**
     * assign the overflow address (to) to the main address (from)
     *
     * @param from main address
     * @param to overflow addess
     * @param message
     * @return
     */
    public static InternalTransactionBean assignOverflow(String from, String to, String message) {
        return BuilderITB.assignOverflow(from, to, message, new Date());
    }

    /**
     *
     * @param from
     * @param to
     * @param message
     * @param notBefore
     * @return
     */
    public static InternalTransactionBean unassignOverflow(String from, String to, String message, Date notBefore) {

        try {
            InternalTransactionBean result = BuilderITB.common(KeyContexts.TransactionType.UNASSIGN_OVERFLOW, notBefore);
            result.setFrom(from);
            result.setTo(to);
            result.setMessage(message);
            result.setTransactionHash(TkmTextUtils.internalTransactionBeanHash(result));

            return result;
        } catch (NullInternalTransactionBeanException | HashCompositionException ex) {
            return null;
        }
    }

    /**
     *
     * @param from
     * @param to
     * @param message
     * @return
     */
    public static InternalTransactionBean unassignOverflow(String from, String to, String message) {
        return BuilderITB.unassignOverflow(from, to, message, new Date());
    }

    /**
     *
     * @param from
     * @param to
     * @param epoch
     * @param slot
     * @param message
     * @param notBefore
     * @return
     */
    public static InternalTransactionBean statePointerTransaction(String from, String to, int epoch, int slot, String message, Date notBefore) {

        try {
            InternalTransactionBean result = BuilderITB.common(KeyContexts.TransactionType.STATE_POINTER_TRANSACTION, notBefore);
            result.setFrom(from);
            result.setTo(to);
            result.setEpoch(epoch);
            result.setSlot(slot);
            result.setMessage(message);
            result.setTransactionHash(TkmTextUtils.internalTransactionBeanHash(result));

            return result;
        } catch (NullInternalTransactionBeanException | HashCompositionException ex) {
            return null;
        }
    }

    /**
     *
     * @param from
     * @param to
     * @param epoch
     * @param slot
     * @param message
     * @return
     */
    public static InternalTransactionBean statePointerTransaction(String from, String to, int epoch, int slot, String message) {
        return BuilderITB.statePointerTransaction(from, to, epoch, slot, message, new Date());
    }

    /**
     *
     * @param from
     * @param message
     * @param notBefore
     * @return
     */
    public static InternalBlockBean block(TransactionBean coinbase, TransactionBean previousBlock, TransactionBean blockHash, ConcurrentSkipListMap<String, String> forwardKeys, ConcurrentSkipListMap<String, TkmRewardBean> rewardList, ConcurrentSkipListSet<PrivateBlockTxBean> transactions) {

        InternalBlockBean result = new  InternalBlockBean();
        result.setCoinbase(coinbase);
        result.setPreviousBlock(previousBlock);
        result.setBlockHash(blockHash);
        result.setForwardKeys(forwardKeys);
        result.setRewardList(rewardList);
        result.setTransactions(transactions);
        return result;
    }

    /**
     *
     * @param from
     * @param epoch
     * @param slot
     * @param message
     * @param notBefore
     * @return
     */
    public static InternalTransactionBean blockHash(String from, int epoch, int slot, String message, Date notBefore) {

        try {
            InternalTransactionBean result = BuilderITB.common(KeyContexts.TransactionType.BLOCK_HASH, notBefore);
            result.setFrom(from);
            result.setEpoch(epoch);
            result.setSlot(slot);
            result.setMessage(message);
            result.setTransactionHash(TkmTextUtils.internalTransactionBeanHash(result));

            return result;
        } catch (NullInternalTransactionBeanException | HashCompositionException ex) {
            return null;
        }
    }

    /**
     *
     * @param from
     * @param epoch
     * @param slot
     * @param message
     * @return
     */
    public static InternalTransactionBean blockHash(String from, int epoch, int slot, String message) {
        return BuilderITB.blockHash(from, epoch, slot, message, new Date());
    }

    /**
     *
     * @param to
     * @param epoch
     * @param slot
     * @param greenValue
     * @param redValue
     * @param message
     * @param notBefore
     * @return
     */
    public static InternalTransactionBean coinBase(String to, int epoch, int slot, BigInteger greenValue, BigInteger redValue, String message, Date notBefore) {

        try {
            InternalTransactionBean result = BuilderITB.common(KeyContexts.TransactionType.COINBASE, notBefore);
            result.setTo(to);
            result.setEpoch(epoch);
            result.setSlot(slot);
            result.setGreenValue(greenValue);
            result.setRedValue(redValue);
            result.setMessage(message);
            result.setTransactionHash(TkmTextUtils.internalTransactionBeanHash(result));

            return result;
        } catch (NullInternalTransactionBeanException | HashCompositionException ex) {
            return null;
        }
    }

    /**
     *
     * @param to
     * @param epoch
     * @param slot
     * @param greenValue
     * @param redValue
     * @param message
     * @return
     */
    public static InternalTransactionBean coinBase(String to, int epoch, int slot, BigInteger greenValue, BigInteger redValue, String message) {
        return BuilderITB.coinBase(to, epoch, slot, greenValue, redValue, message, new Date());
    }
//    cambiare le transazioni di contratto in tre
//            contract_deploy
//            contract_instance
//            contract_call

    /**
     *
     * @param from
     * @param message
     * @param notBefore
     * @return
     */
    public static InternalTransactionBean contractDeploy(String from, String message, Date notBefore) {

        try {
            InternalTransactionBean result = BuilderITB.common(KeyContexts.TransactionType.S_CONTRACT_DEPLOY, notBefore);
            result.setFrom(from);
            result.setMessage(message);
            result.setTransactionHash(TkmTextUtils.internalTransactionBeanHash(result));

            return result;
        } catch (NullInternalTransactionBeanException | HashCompositionException ex) {
            return null;
        }
    }

    /**
     *
     * @param from
     * @param message
     * @return
     */
    public static InternalTransactionBean contractDeploy(String from, String message) {
        return BuilderITB.contractDeploy(from, message, new Date());
    }

    /**
     *
     * @param from
     * @param to
     * @param message
     * @param notBefore
     * @return
     */
    public static InternalTransactionBean contractInstance(String from, String to, String message, Date notBefore) {

        try {
            InternalTransactionBean result = BuilderITB.common(KeyContexts.TransactionType.S_CONTRACT_INSTANCE, notBefore);
            result.setFrom(from);
            result.setFrom(to);
            result.setMessage(message);
            result.setTransactionHash(TkmTextUtils.internalTransactionBeanHash(result));

            return result;
        } catch (NullInternalTransactionBeanException | HashCompositionException ex) {
            return null;
        }
    }

    /**
     *
     * @param from
     * @param to
     * @param message
     * @return
     */
    public static InternalTransactionBean contractInstance(String from, String to, String message) {
        return BuilderITB.contractInstance(from, to, message, new Date());
    }

    /**
     *
     * @param from
     * @param to
     * @param message
     * @param notBefore
     * @return
     */
    public static InternalTransactionBean contractCall(String from, String to, String message, Date notBefore) {

        try {
            InternalTransactionBean result = BuilderITB.common(KeyContexts.TransactionType.S_CONTRACT_CALL, notBefore);
            result.setFrom(from);
            result.setMessage(message);
            result.setTransactionHash(TkmTextUtils.internalTransactionBeanHash(result));

            return result;
        } catch (NullInternalTransactionBeanException | HashCompositionException ex) {
            return null;
        }
    }

    /**
     *
     * @param from
     * @param to
     * @param message
     * @return
     */
    public static InternalTransactionBean contractCall(String from, String to, String message) {
        return BuilderITB.contractCall(from, to, message, new Date());
    }

    /**
     *
     * @param from
     * @param epoch
     * @param slot
     * @param message
     * @param notBefore
     * @return
     */
    public static InternalTransactionBean previousBlock(String from, int epoch, int slot, String message, Date notBefore) {

        try {
            InternalTransactionBean result = BuilderITB.common(KeyContexts.TransactionType.PREVIOUS_BLOCK, notBefore);
            result.setFrom(from);
            result.setEpoch(epoch);
            result.setSlot(slot);
            result.setMessage(message);
            result.setTransactionHash(TkmTextUtils.internalTransactionBeanHash(result));

            return result;
        } catch (NullInternalTransactionBeanException | HashCompositionException ex) {
            return null;
        }
    }

    /**
     *
     * @param from
     * @param epoch
     * @param slot
     * @param message
     * @return
     */
    public static InternalTransactionBean previousBlock(String from, int epoch, int slot, String message) {
        return BuilderITB.previousBlock(from, epoch, slot, message, new Date());
    }

}
