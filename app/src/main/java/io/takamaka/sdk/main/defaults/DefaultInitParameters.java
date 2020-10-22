package io.takamaka.sdk.main.defaults;

import java.math.BigInteger;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 *
 * @author giovanni.antino@h2tcoin.com
 */
public class DefaultInitParameters {

    /**
     * default wallet name
     */
    public static String WALLET_NAME = "default_wallet";

    /**
     * default wallet file extension
     */
    public static String WALLET_EXTENSION = ".wallet";

    /**
     * transaction wallet file extension
     */
    public static String TRANSACTION_EXTENSION = ".trx";

    /**
     * root folder name for the application
     */
    public static String APPLICATION_ROOT_FOLDER_NAME = ".tkm-chain";
    /**
     * test path hotmoka
     */
    public static String HOTMOKA_TEST_FOLDER_NAME = "hotmoka-test";

    public static String HOTMOKA_FILES_FOLDER_NAME = "hotmoka-files";

    /**
     * zero block file number
     */
    public static String ZERO_BLOCK_FILE_NUMBER = "";

    public static int MEM_CACHE_TRANSACTIONS = 15000;

    /**
     * this value establishes in how many parts the unit can be divided
     */
    public static final int NUMBER_OF_ZEROS = 9;

    public static final int MAX_MEMORY_BLOCK_SIZE = 50;
    //with these two parameters a transaction from 600 bytes scales to 0,08 tokens
    public static final BigInteger FEE_SCALE_MULT = new BigInteger("5");
    public static final BigInteger FEE_SCALE_DIV = new BigInteger("15");

    public static final BigInteger DISK_SCALE = new BigInteger("100");
    public static final BigInteger MEM_SCALE = BigInteger.TEN;
    public static final BigInteger CPU_SCALE = BigInteger.ONE;
    //public static final BigInteger CPU_SCALE = new BigInteger("1");
    /**
     * define the desired maximum number of client in the network. The minimum
     * target number will be calculated halving this value
     */
    public static final String TARGET_CLIENT_NUMBER_MAX = "400";
    //    public static final String TARGET_CLIENT_NUMBER_MAX = "4";
    public static final int TARGET_CLIENT_NUMBER_MAX_INT = Integer.parseInt(TARGET_CLIENT_NUMBER_MAX);
    public static final BigInteger TARGET_CLIENT_NUMBER_MAX_BI = new BigInteger(TARGET_CLIENT_NUMBER_MAX);
    public static final BigInteger YEARS_MOORE_LAW = new BigInteger("10");
    public static final int SLOT_PER_EPOCH_INT = 24000;
    //public static final int SLOT_PER_EPOCH_INT = 60;
    public static final int BLOCK_PENATY_LIMIT = SLOT_PER_EPOCH_INT / (TARGET_CLIENT_NUMBER_MAX_INT / 2);

    public static final BigInteger SLOT_PER_EPOCH = new BigInteger("" + SLOT_PER_EPOCH_INT);
    public static final BigInteger MAX_ALLOWED_SLOTS_PER_EPOCH = SLOT_PER_EPOCH.divide(TARGET_CLIENT_NUMBER_MAX_BI).multiply(BigInteger.ONE.add(BigInteger.ONE));
    public static final int TRANSACTION_LIMIT_MB_PER_BLOCK = 6;
    public static final int TRANSACTION_LIMIT_NUMBER_PER_BLOCK = 10000;
    public static final int PAYBACK_LIMIT_SIZE_MB = 2;
    public static final int PENALTY_BLOCKS_PER_BLOCK_OVER_THE_LIMIT = 2;

    /**
     * transaction validity threshold in milliseconds as long value
     */
    public static long TRANSACTION_VALIDITY_THRESHOLD = 600000L; //10 min
    /**
     * block permanence in broadcast buffer queue validity threshold in
     * milliseconds as long value
     */
    public static long BROADCAST_BUFFER_QUEUE_VALIDITY_THRESHOLD = 600000L; //10 min
    /**
     * fast sync execution limit
     */
    public static long FAST_SYNC_EXECUTION_TIME_SPACE = 3000L; //10 min
    /**
     *
     */
    public static long THREAD_POOL_EXECUTOR_LIMIT_VALUE = 1000L; //1 sec
    public static TimeUnit THREAD_POOL_EXECUTOR_LIMIT_UNIT = TimeUnit.MILLISECONDS; //1 sec

    public static long TKM_BUFFER_QUEUE_VALIDITY_THRESHOLD = 600000L; //10 min
    public static int TKM_BUFFER_QUEUE_TRANSACTIONS_NUMBER = 25000; //10 min
    /**
     *
     */
    public static final int REQUEST_PAY_MESSAGE_LIMIT = 200;

    /**
     * number of blocks in boradcast buffer queue
     */
    public static int MAX_NUMBER_OF_BLOCKS_IN_BROADCAST_BUFFER_QUEUE = 50;

    /**
     * Minimum stake bet in unit format. Use {@code TkmTK} before apply
     */
    public static final int MINIMUM_STAKE_BET_UNIT = 200;
    /**
     * Minimum balance for staking
     */
    /**
     * temporary seed for VRF
     */
    public static final String RIGGED_VRF_SEED = "Simple. I got very bored and depressed, so I went and plugged myself in to its external computer feed. I talked to the computer at great length and explained my view of the Universe to it";
    /**
     * node wallet name
     */
    public static final String NODE_STATE_NAME = "node_state_default";
    /**
     * node wallet name
     */
    public static final String NODE_WALLET_NAME = "node_cat_walking_on_the_keyboard_dakjhdgahfjah";
    /**
     * name for wallets used in forward secure secure node
     */
    public static final String TEMPORARY_NODE_WALLET = "temporary_node_wallet_";
    /**
     *
     */
    public static final int EPHEMERAL_KEYS_SIZE = 1024;

    public static final int LIMIT_PROPOSED_KEYS_NUMBER = 100;

    public static final int LIMIT_BACKTRACK = 25;

    public static final int LIMIT_PROPOSED_NODE_KEYS = 2;
    /**
     * number of transaction in wallet history
     */
    public static final int LIMIT_PROPOSED_HISTORY_TRANSACTIONS_SIZE = 20;

    /**
     * use ED for test and qtesla for productions
     */
    //public static final KeyContexts.WalletCypher EPHEMERAL_BLOCK_CYPHER = KeyContexts.WalletCypher.Ed25519BC;
    public static int BLOCK_POOL_LIMIT = 50;
    public static final int[] TO_DATA_LENGTH_WHITELIST = new int[]{44, 19840};


    // PERMITTED_CYPHERS_FOR_MINING
    /**
     * enables the functions usable only in the test environment. In production
     * the flag must be set ALWAYS to false
     */
    public static final boolean TEST_FUNCTIONS_ENABLED = true;
    public static final boolean SIMULATION_FUNCTIONS_ENABLED = true;
    /**
     * stop the execution of the node at the end of the simulation
     */
    public static boolean simulationFunctionKillAtTarget = false;
    public static final boolean DUMP_VRF_METADATA = true;
    //public static final Date SIMULATION_DATE = new Date(new Date().getTime() - (1000L * 30L * 50L)); //milisec + sec + slot nr
    //1585091527000
    //public static final Date SIMULATION_DATE = new Date(1585091527000L); //simulate production start
    //public static final Date SIMULATION_DATE = new Date(new Date().getTime() - (1000L * 30L * 22560L)); //milisec + sec + slot nr
    public static final Date SIMULATION_DATE = new Date(new Date().getTime() - (1000L * 30L * 20L)); //milisec + sec + slot nr

    public static final String BLOCKS_ID_PATTERN_EXPR = "^E[0-9]{1,6}S[0-9]{1,6}$";
    public static final Pattern BLOCKS_ID_PATTERN = Pattern.compile(BLOCKS_ID_PATTERN_EXPR);

    public static boolean isReplicaNode = true;
    public static final String ELASTICSEARCH_HOST = "localhost";
    public static final int ELASTICSEARCH_PORT = 9200;

    public static final int SEARCH_RESULT_LIMIT = 50;


    public static final String FULL_ADDRESS_MAIN_LIST = "assignoverflow";
    public static final String FULL_ADDRESS_MAIN_LIST_STATS = "mainlist";
    public static final String FULL_OVERFLOW_LIST = "registeroverflow";
    public static final String REQUEST_STAKE_LIST = "stake/";
    public static final String REQUEST_APP_VERSION = "version.json";
    public static final String REQUEST_BLOCKCHAIN_SETTINGS = "blockchainsettings";

    public static final String SIMPLE_WALLET_APPLICATION_NAME = "SimpleWallet";
    public static final String SIMPLE_WALLET_APPLICATION_NUMBER = "0.12h";

    public static final int QTESLA_COMPRESSED_ADDRESSES_FOLDER_LEVELS = 4;
    public static int LRBEANS_LIVENESS = 40;
    public static long LRBEANS_VALIDITY_THRESHOLD = 300000L; //5 min

    /*
    public static final BaseurlBean BASEURL_EXPLORER_PRODUCTION = new BaseurlBean(SWInt.i().getMessage("main_net_tag") + " - PRODUCTION EXPLORER", "https://exp.takamaka.dev/");
    public static final BaseurlBean BASEURL_EXPLORER_TEST = new BaseurlBean(SWInt.i().getMessage("test_tag") + " - TEST EXPLORER", "https://testexplorer.takamaka.dev/");
    public static final BaseurlBean BASEURL_EXPLORER_LOCALHOST = new BaseurlBean(SWInt.i().getMessage("local_tag") + " - LOCALHOST EXPLORER", "http://127.0.0.1:5000/");
    public static final BaseurlBean BASEURL_EXPLORER_ALL[] = new BaseurlBean[]{
        BASEURL_EXPLORER_PRODUCTION,
        BASEURL_EXPLORER_TEST,
        BASEURL_EXPLORER_LOCALHOST
    };
    public static final BaseurlBean BASEURL_EXPLORER_DEFAULT = BASEURL_EXPLORER_PRODUCTION;
     */


    //
    // SMART CONTRACT
    //
    /**
     * takamaka code jar
     */
    public static String TAKAMAKA_CODE_JAR_RESOURCE = "io-takamaka-code-1.0.0.jar";

}
