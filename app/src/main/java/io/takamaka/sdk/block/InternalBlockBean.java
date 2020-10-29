package io.takamaka.sdk.block;

import io.takamaka.sdk.globalContext.KeyContexts;
import io.takamaka.sdk.transactions.TransactionBean;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;


public class InternalBlockBean implements Serializable {

    private TransactionBean coinbase;
    private TransactionBean previousBlock;
    private TransactionBean blockHash;
    private ConcurrentSkipListMap<String, String> forwardKeys;
    /**
     * sorting string,reward bean
     */
    private ConcurrentSkipListMap<String, TkmRewardBean> rewardList;
    private ConcurrentSkipListSet<PrivateBlockTxBean> transactions;

    /**
     * @return the coinbase
     */
    public TransactionBean getCoinbase() {
        return coinbase;
    }

    /**
     * return the transaction containing the previous block hash
     *
     * @return the previousBlock
     */
    public TransactionBean getPreviousBlock() {
        return previousBlock;
    }

    /**
     * return the transaction containing the block hash
     *
     * @return the blockHash
     */
    public TransactionBean getBlockHash() {
        return blockHash;
    }

    /**
     * @param coinbase the coinbase to set
     */
    public void setCoinbase(TransactionBean coinbase) {
        this.coinbase = coinbase;
    }

    /**
     * @param previousBlock the previousBlock to set
     */
    public void setPreviousBlock(TransactionBean previousBlock) {
        this.previousBlock = previousBlock;
    }

    /**
     * @param blockHash the blockHash to set
     */
    public void setBlockHash(TransactionBean blockHash) {
        this.blockHash = blockHash;
    }

    /**
     * @return the transactions
     */
    public ConcurrentSkipListSet<PrivateBlockTxBean> getTransactions() {
        return transactions;
    }

    /**
     * @param transactions the transactions to set
     */
    public void setTransactions(ConcurrentSkipListSet<PrivateBlockTxBean> transactions) {
        this.transactions = transactions;
    }

    /**
     * EpochSlot, Public Key
     *
     * @return
     */
    public ConcurrentSkipListMap<String, String> getForwardKeyes() {
        return forwardKeys;
    }

    public void setForwardKeys(ConcurrentSkipListMap<String, String> forwardKeys) {
        this.forwardKeys = forwardKeys;
    }

    /**
     * sorting string,reward bean
     *
     * @return
     */
    public ConcurrentSkipListMap<String, TkmRewardBean> getRewardList() {
        return rewardList;
    }

    /**
     * sorting string,reward bean
     *
     * @param rewardList
     */
    public void setRewardList(ConcurrentSkipListMap<String, TkmRewardBean> rewardList) {
        this.rewardList = rewardList;
    }

    public KeyContexts.TransactionType getTransactionType() {
        return KeyContexts.TransactionType.BLOCK;
    }

    /**
     * return block hash retriving it from message of block transaction
     *
     * @deprecated
     * @return
     * @throws java.security.NoSuchAlgorithmException
     */
    public String getTransactionHash() throws NoSuchAlgorithmException {
        throw new UnsupportedOperationException("operation being reworked");

    }

    /**
     * Returns the slot time corresponding to the current block
     *
     * @deprecated
     * @return @throws java.security.NoSuchAlgorithmException
     */
    public Date getNotBefore() throws NoSuchAlgorithmException {
        throw new UnsupportedOperationException("operation being reworked");

    }

}
