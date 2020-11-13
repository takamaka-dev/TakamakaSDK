package io.takamaka.sdk.main.defaults;

import android.content.ContextWrapper;

import java.math.BigInteger;

import io.takamaka.demo.MainController;
import io.takamaka.sdk.utils.ComboItemSettingsBookmarkUrl;

/**
 *
 * @author giovanni.antino@h2tcoin.com
 */
public class DefaultInitParameters {

    /**
     * default wallet file extension
     */
    public static String APPLICATION_HOME = null;//"/storage/emulated/0/Android/data/io.takamaka/files/";
    public static String WALLET_EXTENSION = ".wallet";

    public static final BigInteger DISK_SCALE = new BigInteger("100");
    public static final BigInteger MEM_SCALE = BigInteger.TEN;
    public static final BigInteger CPU_SCALE = BigInteger.ONE;
    public static final String TARGET_CLIENT_NUMBER_MAX = "400";
    public static final BigInteger TARGET_CLIENT_NUMBER_MAX_BI = new BigInteger(TARGET_CLIENT_NUMBER_MAX);
    public static final BigInteger YEARS_MOORE_LAW = new BigInteger("10");
    public static final BigInteger FEE_SCALE_MULT = new BigInteger("5");
    public static final BigInteger FEE_SCALE_DIV = new BigInteger("15");
    /**
     * root folder name for the application
     */
    public static String APPLICATION_ROOT_FOLDER_NAME = "tkm-chain";
    /**
     * test path hotmoka
     */
    public static String HOTMOKA_TEST_FOLDER_NAME = "hotmoka-test";

    public static String HOTMOKA_FILES_FOLDER_NAME = "hotmoka-files";

    /**
     * zero block file number
     */
    public static String ZERO_BLOCK_FILE_NUMBER = "";


    /**
     * this value establishes in how many parts the unit can be divided
     */
    public static final int NUMBER_OF_ZEROS = 9;

    public static final int BIG_INTEGER = 1000000000;
    /**
     * Minimum stake bet in unit format. Use {@code TkmTK} before apply
     */
    public static final int MINIMUM_STAKE_BET_UNIT = 200;

    /**
     * temporary seed for VRF
     */
    public static final int[] TO_DATA_LENGTH_WHITELIST = new int[]{44, 19840};

    public static final ComboItemSettingsBookmarkUrl DEFAULT_SEND_TRANSACTION_URL = new ComboItemSettingsBookmarkUrl("MAIN_NET - DEV.TAKAMAKA.IO", "https://dev.takamaka.io/api/V2/nodeapi/transaction/", "not defined");

    public static final ComboItemSettingsBookmarkUrl DEFAULT_API_URL = new ComboItemSettingsBookmarkUrl("MAIN_NET - DEV.TAKAMAKA.IO - API ENDPOINT", "https://dev.takamaka.io/api/V2/nodeapi/", "not defined", "address");


}
