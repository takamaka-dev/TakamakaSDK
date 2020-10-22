package io.takamaka.sdk.globalContext;

import io.takamaka.sdk.main.defaults.DefaultInitParameters;
import java.math.BigInteger;

/**
 *
 * @author giovanni.antino@h2tcoin.com
 */
public final class KeyContexts {

    public static final String WALLET_CURRENT_VERSION = "0.1";
    public static final String PUBLICKEY_CURRENT_VERSION = "0.1";
    public static final String WALLET_JSON_AES = "AES";
    public static final String WALLET_JSON_AES_CBC_CS3 = "AES/CBC/CS3Padding";
    public static final String WALLET_JSON_BC = "BC";
    //                                             wallet key chain
    /**
     *
     */
    public static final String WALLET_KEY_CHAIN = "__WKCH__";

    /**
     * Number of zeros for which one must multiply 1 to obtain unity. If you
     * want to get the value equivalent to two green tokens you need to multiply
     * 2 * {@code NUMBER_OF_ZEROS_SHIFT_DECIMAL}
     */
    public static final BigInteger NUMBER_OF_ZEROS_SHIFT_DECIMAL = BigInteger.TEN.pow(DefaultInitParameters.NUMBER_OF_ZEROS);

    /**
     *
     */
    public static final String WALLET_LOGGER = "wallet_logger";
    /**
     * Number of zeros for which one must multiply 1 to obtain 0,1. If you want
     * to get the value equivalent to zero point two green tokens you need to
     * multiply 2 * {@code NUMBER_OF_ZEROS_SHIFT_DECI}
     */
    public static final BigInteger NUMBER_OF_ZEROS_SHIFT_DECI = BigInteger.TEN.pow(DefaultInitParameters.NUMBER_OF_ZEROS - 1);

    /**
     *
     */
    public static final String REST_LOGGER = "rest_logger";

    /**
     *
     */
    public static final String NODE_LOGGER = "node_logger";

    /**
     *
     */
    public static final String LOGGER = "logger";

    /**
     *
     */
    public static final String GREEN_COIN_INIT_AMOUNT = "30000000";

    /**
     *
     */
    public static final String RED_COIN_INIT_AMOUNT = "70000000000";

    /**
     *
     */
    public static final String SLOT_TIME_ROUNDING = "30000";

    /**
     *
     */
    public static final int DEFAULT_DIRECT_CONNECT_PORT = 61006;

    /**
     * tollerace in time for accepting new slot
     */
    public static final int MAXIMUM_SLOT_TOLLERANCE = 2;

    /**
     * define the fake hash fo uninitialized blockchain
     */
    public static final String UNINITIALIZED_BLOCKCHAIN_DEFAULT_HASH = "ZERO";

    /**
     * skipped block
     */
    public static final String SKIPPED_BLOCK_DEFAULT_HASH = "SKIPPED_";

    //public static long TRANSACTION_VALIDITY_THRESHOLD = 600000L;
    /**
     *
     */
    public static enum WalletCypher {

        /**
         *
         */
        Ed25519,
        /**
         *
         */
        Tink,
        /**
         *
         */
        Ed25519BC,
        /**
         * bouncy castle, provable secure, I, Round 1
         */
        BCQTESLA_PS_1,
        /**
         * bouncy castle, provable secure, I, Round 2
         */
        BCQTESLA_PS_1_R2
    }

    /**
     *
     */
    public static enum TransactionType {

        /**
         * red or green token transaction transfer
         */
        PAY,
        /**
         *
         */
        STAKE,
        /**
         *
         */
        STAKE_UNDO,//remove all bet
        /**
         *
         */
        COINBASE,
        /**
         *
         */
        PREVIOUS_BLOCK,
        /**
         *
         */
        BLOCK_HASH,
        /**
         * transaction valid only in initialization
         */
        DECLARATION, //used for building balances at the beginning of time
        /**
         * @deprecated
         */
        BLOCK_CLOSE, //REMOVED

        /**
         *
         */
        BLOCK,
        /**
         *
         */
        REGISTER_MAIN,
        /**
         *
         */
        REGISTER_OVERFLOW,
        /**
         *
         */
        DEREGISTER_MAIN,
        /**
         *
         */
        DEREGISTER_OVERFLOW,
        /**
         *
         */
        ASSIGN_OVERFLOW,
        /**
         *
         */
        UNASSIGN_OVERFLOW,
        /**
         *
         */
        UNDEFINED,
        /**
         * require the next valid block using the last valid block hash
         *
         * @deprecated
         */
        REQUEST_BLOCK,
        /**
         * reply to a request block transaction containing the nearest valid
         * block or the first one if requestor is in a chain split
         *
         * @deprecated
         */
        RETURN_BLOCK,
        /**
         * generic data signing in the blockchain(ex. file signature)
         */
        BLOB,
        /**
         * signed state pointer bean from, to, epoch, slot, inside the message
         * is the base64 blockhash
         */
        STATE_POINTER_TRANSACTION,
        //SMART CONTRACT
        /**
         *
         */
        S_CONTRACT_DEPLOY,
        /**
         *
         */
        S_CONTRACT_INSTANCE,
        /**
         *
         */
        S_CONTRACT_CALL,
        /**
         * require account creation
         */
        S_CREATE_ACCOUNT
    }

    /**
     * define the constants for coin colors
     */
    public static enum CoinColors {

        /**
         *
         */
        UNDEFINED,
        /**
         *
         */
        RED,
        /**
         *
         */
        GREEN
    }

    /**
     * If a transaction move only red coin is {@code RED}, if a transaction move
     * only green coin is {@code GREEN}, if a transaction move both red and
     * green coin is {@code RED_GREEN} undefined otherwise. Coin used for pay
     * the operation is not taken in account in this evaluation.
     */
    public static enum TransactionColor {

        /**
         *
         */
        UNDEFINED,
        /**
         *
         */
        RED,
        /**
         *
         */
        GREEN,
        /**
         *
         */
        RED_GREEN
    }

    /**
     *
     */
    public static enum StakeStatus {

        /**
         *
         */
        VALID,
        /**
         *
         */
        INVALID,
        /**
         *
         */
        SYNTAX_ERROR_OR_CORRUPTED
    }

    /**
     * used inside internal block bean to mark the validity of transaction
     * inside a block
     */
    public static enum InternalBlockTransactionState {

        /**
         * included valid
         */
        VALID,
        /**
         * included non valid
         */
        INVALID,
        /**
         *
         */
        SYNTAX_ERROR_OR_CORRUPTED
    }

    /**
     * default null address
     */
    public static String DEFAULT_NULL_ADDRESS = "0x0";

    /**
     * Used in EXPIRATION TABLE CANDIDATE in transaction queue, not part of the
     * current blockchain ADDED existing in a block CORRUPTED transaction syntax
     * error or signature error
     */
    public static enum ExpirationTransactionState {

        /**
         *
         */
        CANDIDATE,
        /**
         *
         */
        ADDED,
        /**
         *
         */
        CORRUPTED
    }

    /**
     *
     */
    public static enum TransactionListLength {

        /**
         *
         */
        LAST_20,
        /**
         *
         */
        LAST_50,
        /**
         *
         */
        LAST_100,
        /**
         *
         */
        LIST_ALL
    }

    /**
     *
     */
    public static enum AddressType {

        /**
         *
         */
        MAIN,
        /**
         *
         */
        OVERFLOW,
        /**
         *
         */
        UNDEFINED
    }

    /**
     * Define the status of a undo transaction. {@code NOT_UP_TO_DATE} means
     * that in blockchain exist already a transaction with expiration more
     * recent than the current transaction.
     */
    public static enum StakeUndoState {

        /**
         *
         */
        VALID,
        /**
         *
         */
        INVALID,
        /**
         *
         */
        NOT_UP_TO_DATE,
        /**
         *
         */
        VALID_SYNTAX,
        /**
         *
         */
        CORRUPTED
    }

    /**
     * Define the status of a registration transaction.
     */
    public static enum AddressRegistration {

        /**
         *
         */
        VALID,
        /**
         *
         */
        VALID_SYNTAX,
        /**
         *
         */
        INVALID,
        /**
         *
         */
        DUPLICATED,
        /**
         *
         */
        CORRUPTED
    }

    /**
     *
     */
    public static enum AddressAssignment {

        /**
         *
         */
        VALID,
        /**
         *
         */
        VALID_SYNTAX,
        /**
         *
         */
        INVALID,
        /**
         *
         */
        DUPLICATED,
        /**
         *
         */
        LOOP_ASSIGNMENT,
        /**
         *
         */
        DUPLICATED_ASSIGNMENT_TO_MAIN,
        /**
         *
         */
        CORRUPTED,
        /**
         *
         */
        UNREGISTERED_ADDRESS_MAIN,
        /**
         *
         */
        UNREGISTERED_ADDRESS_OVERFLOW,
        /**
         *
         */
        OVERFLOW_USED_INSTEAD_OF_MAIN,
        /**
         *
         */
        MAIN_USED_INSTEAD_OF_OVERFLOW
    }

    /**
     * declaretion state transaction status
     */
    public static enum DeclarationState {

        /**
         *
         */
        INVALID,
        /**
         *
         */
        VALID_SYNTAX,
        /**
         *
         */
        CORRUPTED,
        /**
         *
         */
        VALID
    }

    /**
     * pay transaction status
     */
    public static enum PayState {

        /**
         *
         */
        INVALID,
        /**
         *
         */
        VALID_SYNTAX,
        /**
         *
         */
        CORRUPTED,
        /**
         *
         */
        VALID,
        /**
         *
         */
        INSUFFICIENTS_FOUNDS,
    }

    /**
     *
     */
    public static enum StateOperation {
        /**
         * save current state to file
         */
        PERSIST_TO_FILE,
        /**
         * read current state from file
         */
        READ_FROM_FILE,
        /**
         * clean then init current state to default values and persist resetted
         * property file
         */
        INIT_ZERO_STATE,
        /**
         * delete chain folder
         */
        RESET_CHAIN,
        /**
         * delete chain folder and log folder
         */
        RESET_CHAIN_AND_LOG_FOLDER,
        /**
         * save current state to default (startup) blochchain state
         */
        PERSIST_TO_DEFAULT_FILE,
        /**
         * read the current state from the default blockchain state
         */
        READ_FROM_DEFAULT_FILE,
        /**
         * call to mine a new block using transaction in current queue
         */
        MINE_NEXT_BLOCK,
        /**
         * compute next step
         */
        NEXT_STEP,
        /**
         * skip block, never call on uninitialized blockchain
         */
        SKIP_BLOCK,
        /**
         * query the node
         */
        QUERY_STATE,
        /**
         * init the chain using the default zero block
         */
        INIT_DEFAULT_CHAIN,
        /**
         * try to update chain status using the submitted block
         */
        LOAD_BLOCK_IN_CHAIN,
        /**
         * return a chain info bean with current chain state
         */
        //GET_CURRENT_CHAIN_INFO,

    }

    /**
     *
     */
    public static enum QueryType {
        /**
         * get the balance of a wallet
         */
        GET_BALANCE,
        /**
         * get the stake bets of a wallet
         */
        STAKE_BETS,
        /**
         *
         */
        UNDEFINED,
        /**
         * pass a block to the current chain
         */
        LOAD_BLOCK,
        /**
         * request a chain info block
         */
        GET_CHAIN_INFO,
        /**
         * search for the indicated block and return previousBlock and blockHash
         * of the next one
         */
        BLOCK_SEARCH
    }

    /**
     *
     */
    public static final String SYSPROPS_PREVIOUS_TAIL = "_PREVIOUS_BLOCK";

    /**
     *
     */
    public static final String SYSPROPS_SLOT = "SL_";

    /**
     *
     */
    public static final String SYSPROPS_CURRENT = "CURRENT_SLOT_DISTRIBUTION";

    /**
     *
     */
    public static final String SYSPROPS_NEXT = "NEXT_SLOT_DISTRIBUTION";

    /**
     *
     */
    public static final String SYSPROPS_NODE = "NODE";

    /**
     *
     */
    public static final String SYSPROPS_NODE_SLOTS = "NODE_SLOTS";

    /**
     *
     */
    public static final String SYSPROPS_NODE_KEY = "NODE_KEY";
    //public static final String SYSPROPS_PREVIOUS_XML_ROOT = "PREVIOUS_BLOCK";
    //public static final String SYSPROPS_CURRENT_XML_ROOT = "CURRENT_BLOCK";

    /**
     * state properties
     */
    public static enum SysProps {
        /**
         *
         */
        EPOCH,
        /**
         *
         */
        SLOT,
        /**
         *
         */
        BLOCK_HASH,
        /**
         *
         */
        CURRENT_DATABASE,
        /**
         *
         */
        CHAIN_WEIGHT,
        /**
         *
         */
        IS_FIRST_BLOCK,
        /**
         *
         */
        CURRENT_STATE_TIME,
        /**
         *
         */
        DATABASE_NAME,
        /**
         *
         */
        CURRENT_EPOCH_SLOT_WEIGHT,
        /**
         *
         */
        NEXT_EPOCH_SLOT_WEIGHT,
        /**
         *
         */
        CURRENT_EPOCH_SLOT_DISTRIBUTION,
        /**
         *
         */
        NEXT_EPOCH_SLOT_DISTRIBUTION,
        /**
         *
         */
        WORLD_INIT_TIME,
        /**
         *
         */
        IS_SKIPPED_STATE,

    }

    /**
     *
     */
    public static enum HashFormat {

        /**
         *
         */
        BIGINTEGER,
        /**
         *
         */
        STRING
    }

    /**
     *
     */
    public static enum TransactionQueryType {

        /**
         *
         */
        BLOCK_SEARCH_REQUEST,
        /**
         *
         */
        BLOCK_SEARCH_RESPONSE,
        /**
         *
         */
        BLOCK_DOWNLOAD_REQUEST,
        /**
         *
         */
        BLOCK_DOWNLOAD_RESPONSE
    }

    public static enum CypherOperation {
        SIGN,
        ENCRYPT,
        VERIFY
    }

    public static enum InternalTransactionSyntaxValidity {
        NULL_MANDATORY_FIELD,
        NULL_ITB
    }

    public static enum InternalBlockSyntaxValidity {
        NULL_MANDATORY_FIELD,
        NULL_IBB
    }

    public static enum TransactionSyntaxValidity {
        NULL_MANDATORY_FIELD,
        NULL_TB
    }

}
