package io.takamaka.sdk.globalContext;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FixedParameters {

    /**
     * filename of the current application state
     */
    public static final String CURRENT_STATE_FILE = "defaultState";

    /**
     * use this parameter to initialize the wallet name in constructors or in
     * cases the user does not specify it
     */
    public static final String DEFAULT_WALLET_NAME = "default_wallet";

    /**
     * extension of the application state files
     */
    public static final String STATE_FILE_EXTENSION = ".state";

    /**
     * extension of the block files
     */
    public static final String BLOCK_FILE_EXTENSION = ".block";

    public static final String STRING_ZERO = "ZERO";
    public static final String STRING_SKIPPED = "SKIPPED";

    /**
     * wallet folder name inside application root folder
     */
    public static final String WALLET_FOLDER = "wallets";

    public static final String SETTINGS_FILE_JSON = "settings/sdk_json_endpoints.json";

    /**
     * public keys folder name inside application root folder
     */
    public static final String PUBLICKEY_FOLDER = "publickeys";

    /**
     * transactions folder name inside application root folder
     */
    public static final String TRANSACTION_FOLDER = "transactions";

    /**
     * address book folder name inside application root folder
     */
    public static final String ADDRESSBOOK_FOLDER = "addressbook";

    /**
     * extensiomn of the public key files
     */
    public static final String PUBLICKEY_EXTENSION = ".public";

    /**
     * folder for ephemeral wallet
     */
    public static final String WALLET_EPHEMERAL_FOLDER = "ephemeral";

    /**
     * wallet folder name inside application root folder
     */
    public static final String WALLET_FILENAME = "_h2twallet.wall";

    /**
     * address book folder name inside wallet root folder
     */
    public static final String ADDRESS_BOOK_FOLDER = "addressbook";

    /**
     * zero block folder name inside root folder
     */
    public static final String ZERO_BLOCK_FOLDER = "ZeroBlockFolder";

    /**
     * logs folder name inside application root folder
     */
    public static final String LOGS_FOLDER = "logs";

    /**
     * logs folder name inside application root folder
     */
    public static final String REPLICA_NODE_ENABLED = "isReplicaNode.options";

    /**
     * chain folder name inside application root folder
     */
    public static final String CHAIN_FOLDER = "chain";

    /**
     * epoch folder prefix inside chain folder
     */
    public static final String EPOCH_FOLDER_PREFIX = "epoch_";

    /**
     * slot folder prefix inside epoch folder
     */
    public static final String SLOT_FOLDER_PREFIX = "slot_";

    /**
     * compressed qtesla addresses
     */
    public static final String REFERENCE_QTESLA_ADDR_FOLDER = "reference_qtesla_addresses";

    /**
     * reference qtesla addresses prefix
     */
    public static final String REFERENCE_QTESLA_ADDR_PREFIX = "r|";

    /**
     * slot folder for the storage of TransactionBox objects
     */
    public static final String TRANSACTION_BOX_FOLDER = "transactionBoxFolder";

    /**
     * chain lock file name inside application root folder
     */
    public static final String CHAIN_LOCK_FILE = "chainLockFile.lock";

    /**
     * separator for assigned slot inside state file
     */
    public static final String STATE_SLOT_SEPARATOR = ",";

    /**
     * state database prefix inside slot directory
     */
    public static final String STATE_DB_PREFIX = "STATE_DB_";
    /**
     * forward chain tollerance mills
     */
    public static final long FORWARD_STATE_TOLLERANCE_MILLS = 30500L;

    public static final String HOLDER_STAKES_BY_TARGET_EXTENSION = ".HSBT";
    public static final String LIST_STAKES_DISTRIBUTION_EXTENSION = ".LSD";
    public static final String VRF_DISTRIBUTION_EXTENSION = ".VRF";
    public static final String EPOCH_WEIGHT_EXTENSION = ".EPOCH_WEIGHT";

    /**
     * transactions folder name inside application root folder
     */
    public static final String NODE_NETWORK_SETTINGS_FILE = "node_network_settings.xml";

    /**
     *
     */
    public static final String LOG_PREFIX_SEPARATOR = "-";
    /**
     *
     */
    public static final int WALLET_DICTIONARY_LENGTH = 2048;
    /**
     *
     */
    public static final int WALLET_WORDS_NUMBER = 25;
    /**
     * message digest PWH
     */
    public static final String HASH_PWH_ALGORITHM = "PBKDF2WithHmacSHA512";

    public static final String WALLET_DICTIONARY_FILE = "dictionary.txt";
    /**
     * folder inside root app directory where the waiting transaction file queue
     * is located
     */
    public static final String TRANSACTION_BOX_CACHING_FOLDER = "tbox_cache_folder";
    public static final String USER_WALLETS_FOLDER = "user_wallets_folder";
    public static final String USER_WALLETS_FILE_EXTENSION = ".userWallet";
    public static final String USER_WALLETS_PREFIX = "LOCKED_PERSONAL_";
    public static final String USER_WALLETS_RECOVERY_WORDS_EXTENSION = ".words.txt";
    public static final String USER_WALLETS_HINT = "recovery_";

    public static final String TRANSACTIONS_DUMP_FOLDER = "transactions_dump_folder";
    public static final String SIMULATION_DUMP_FOLDER = "simulation_dump_folder";

    public static final String SETTINGS_FOLDER = "settings_folder";
    public static final String SETTINGS_BOOKMARKS = "bookmarks.json";
    public static final String SETTINGS_TX = "transactions_endpoints.json";
    public static final String API_SETTINGS = "api.json";
    public static final String FAST_SETTINGS = "fast_tag.json";
    public static final String FAVOURITE_TO_BOOKMARKS = "to_addresses.json";

    public static final String MAIN_ALIASES_SETTINGS = "main_aliases.json";
    public static final String CASHBACK_SETTINGS_FILENAME = "cashback.json";
    public static final String CASHBACK_SETTINGS_WALLET_NAME = "CASHBACK_WALLET";
    public static KeyContexts.WalletCypher CASHBACK_SETTINGS_WALLET_TYPE = KeyContexts.WalletCypher.Ed25519BC;

    public static final String BASEURL_SETTINGS_FILENAME_EXPLORER = "explorer_endpoints.json";

    public static final String API_BALANCE_ENDPOINT = "balanceof/";

    /**
     * folder inside root app directory where the links between MAIN and
     * OVERFLOW nodes
     */
    public static final String NODE_MANAGEMENT_FOLDER = "node_management_folder";

    /**
     * name of the json containing the simple wallet transaction history
     */
    public static final String TRANSACTION_HISTORY_FILENAME = "transaction_history.json";

    /**
     * 1 - 16 files 2 - 256 files 3 - 4096 files and so on... the number of
     * files is 16^depth
     *
     */
    public static enum HexKeyWriter {
        /**
         * balance folder extension 3
         */
        BALANCE(4),
        NODE(2),
        STAKE_UNDO(3),
        STAKE(4),
        OVER_THE_LIMIT(4),
        BLOCKS(4),
        //TRANSACTION_REFERENCE(4),
        ACCEPTED_BET(4);

        private final int depth;

        private HexKeyWriter(int depth) {
            this.depth = depth;
        }

        public int getDepth() {
            return depth;
        }

    }

    public static int COMPRESSION_LEVEL = 9;

    public static final String[] HEX_KEY_NUM = {
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f",};

    /**
     * Logger type
     */
    public static enum LogType {
        /**
         * default file for log
         */
        LOGGER,
        /**
         * rest api log
         */
        REST,
        /**
         * cli and command node log
         */
        NODE,
        /**
         * cli and command wallet log
         */
        WALLET
    }

    /**
     * application charset
     */
    public static final Charset CHARSET = StandardCharsets.UTF_8;

    /**
     * message digest 256
     */
    public static final String HASH_256_ALGORITHM = "SHA3-256";

    /**
     * message digest 256
     */
    public static final String HASH_384_ALGORITHM = "SHA3-384";

    /**
     * message digest RIP MD 160
     */
    public static final String HASH_160_ALGORITHM = "RipeMD160";

    /**
     * message digest 512
     */
    public static final String HASH_512_ALGORITHM = "SHA3-512";

    /**
     * transaction execution order
     */
    public static final KeyContexts.TransactionType[] TRANSACTION_EXECUTION_ORDER = {
            KeyContexts.TransactionType.DECLARATION,
            KeyContexts.TransactionType.STAKE_UNDO,
            KeyContexts.TransactionType.DEREGISTER_OVERFLOW,
            KeyContexts.TransactionType.DEREGISTER_MAIN,
            KeyContexts.TransactionType.REGISTER_MAIN,
            KeyContexts.TransactionType.REGISTER_OVERFLOW,
            KeyContexts.TransactionType.ASSIGN_OVERFLOW,
            KeyContexts.TransactionType.UNASSIGN_OVERFLOW,
            KeyContexts.TransactionType.STAKE,
            KeyContexts.TransactionType.PAY,
            KeyContexts.TransactionType.BLOB,
            KeyContexts.TransactionType.S_CONTRACT_DEPLOY,
            KeyContexts.TransactionType.S_CONTRACT_INSTANCE,
            KeyContexts.TransactionType.S_CONTRACT_CALL
            //KeyContexts.TransactionType.COINBASE
            //KeyContexts.TransactionType.PREVIOUS_BLOCK,
            //KeyContexts.TransactionType.BLOCK,
            //KeyContexts.TransactionType.BLOCK_HASH
            //KeyContexts.TransactionType.UNDEFINED
    };

    /**
     * transaction permitted in step zero for chain initialization
     */
    public static final KeyContexts.TransactionType[] FILTER_TRANSACTION_EXPIRATION_BOOTSTRAP_LIST = {
            KeyContexts.TransactionType.DECLARATION,
            KeyContexts.TransactionType.STAKE_UNDO,
            KeyContexts.TransactionType.DEREGISTER_OVERFLOW,
            KeyContexts.TransactionType.DEREGISTER_MAIN,
            KeyContexts.TransactionType.REGISTER_MAIN,
            KeyContexts.TransactionType.REGISTER_OVERFLOW,
            KeyContexts.TransactionType.ASSIGN_OVERFLOW,
            KeyContexts.TransactionType.UNASSIGN_OVERFLOW,
            KeyContexts.TransactionType.STAKE,
            KeyContexts.TransactionType.PAY,
            KeyContexts.TransactionType.BLOB,
            KeyContexts.TransactionType.S_CONTRACT_DEPLOY,
            KeyContexts.TransactionType.S_CONTRACT_INSTANCE,
            KeyContexts.TransactionType.S_CONTRACT_CALL
            //KeyContexts.TransactionType.COINBASE
            //KeyContexts.TransactionType.PREVIOUS_BLOCK,
            //KeyContexts.TransactionType.BLOCK,
            //KeyContexts.TransactionType.BLOCK_HASH
            //KeyContexts.TransactionType.UNDEFINED
    };


    public static final List<KeyContexts.TransactionType> FILTER_TRANSACTION_EXPIRATION_BOOTSTRAP
            = new ArrayList<KeyContexts.TransactionType>(
            Arrays.asList(FILTER_TRANSACTION_EXPIRATION_BOOTSTRAP_LIST)
    );

    /**
     * transaction permitted in step OTHER than zero
     */
    public static final KeyContexts.TransactionType[] FILTER_TRANSACTION_EXPIRATION_NORMAL_OPERATION_LIST = {
            //    KeyContexts.TransactionType.DECLARATION,
            KeyContexts.TransactionType.STAKE_UNDO,
            KeyContexts.TransactionType.DEREGISTER_OVERFLOW,
            KeyContexts.TransactionType.DEREGISTER_MAIN,
            KeyContexts.TransactionType.REGISTER_MAIN,
            KeyContexts.TransactionType.REGISTER_OVERFLOW,
            KeyContexts.TransactionType.ASSIGN_OVERFLOW,
            KeyContexts.TransactionType.UNASSIGN_OVERFLOW,
            KeyContexts.TransactionType.STAKE,
            KeyContexts.TransactionType.PAY,
            KeyContexts.TransactionType.BLOB,
            KeyContexts.TransactionType.S_CONTRACT_DEPLOY,
            KeyContexts.TransactionType.S_CONTRACT_INSTANCE,
            KeyContexts.TransactionType.S_CONTRACT_CALL
            //KeyContexts.TransactionType.COINBASE
            //KeyContexts.TransactionType.PREVIOUS_BLOCK,
            //KeyContexts.TransactionType.BLOCK,
            //KeyContexts.TransactionType.BLOCK_HASH
            //KeyContexts.TransactionType.UNDEFINED
    };


    public static final List<KeyContexts.TransactionType> FILTER_TRANSACTION_EXPIRATION_NORMAL_OPERATION
            = new ArrayList<KeyContexts.TransactionType>(
            Arrays.asList(FILTER_TRANSACTION_EXPIRATION_NORMAL_OPERATION_LIST)
    );

    public enum WalletError {
        INIZIALIZE(1, "I can't inizialize wallet files. Check for writing permissions."),
        CRYPTO(2, "Something went wrong with cryptography."),
        PKEY_READ(3, "I can't read the public key."),
        PKEY_UNLOCK(4, "I can't unlock the wallet\\wrong password."),
        WALLET_IO(5, "Wallet I/O problems."),
        RECOVERY_WORDS(6, "Problems with recovery words."),
        SETUP(7, "Setup problems."),
        LOCKED(8, "Wallet is locked."),
        RESTORE(9, "I can't restore wallet.");

        private final int code;
        private final String description;

        private WalletError(int code, String description) {
            this.code = code;
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        public int getCode() {
            return code;
        }

        @Override
        public String toString() {
            return "";
        }
    }

}
