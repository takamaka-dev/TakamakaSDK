package io.takamaka.demo.utils;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.ConcurrentSkipListMap;

import io.takamaka.sdk.globalContext.FixedParameters;
import io.takamaka.sdk.main.defaults.DefaultInitParameters;
import io.takamaka.sdk.utils.ComboItemSettingsBookmarkUrl;
import io.takamaka.sdk.wallet.InstanceWalletKeystoreInterface;
import io.takamaka.sdk.wallet.NewWalletBean;
import io.takamaka.sdk.wallet.beans.BalanceBean;
import okhttp3.OkHttpClient;


public class SWTracker {
    private static int currIndex = 0;
    private static String currentSetting = "prod";
    private static final Object SWTLOCK = new Object();
    private static NewWalletBean nwb = null;
    private static BalanceBean bb = null;

    private static UserWalletBean selectedUWB;
    private static InstanceWalletKeystoreInterface iwk;
    private static int currentAddressNumber = 0;

    private static HashMap<String, HashMap> settings = new HashMap<>();
    private static ComboItemSettingsBookmarkUrl currentApiUrl = DefaultInitParameters.DEFAULT_API_URL;
    private static ComboItemSettingsBookmarkUrl currentTransactionsEndpoint = DefaultInitParameters.DEFAULT_SEND_TRANSACTION_URL;
    private static ConcurrentSkipListMap<String, ComboItemSettingsBookmarkUrl> explorerBaseurlMap;
    private static ConcurrentSkipListMap<String, ComboItemSettingsBookmarkUrl> bookmarksUrlShortener;
    private static ConcurrentSkipListMap<String, ComboItemSettingsBookmarkUrl> sendTransactionUrl;
    private static ConcurrentSkipListMap<String, ComboItemSettingsBookmarkUrl> apiUrl;
    private static ConcurrentSkipListMap<String, ComboItemSettingsBookmarkUrl> fastTag;

    public static String getCurrentSetting() {
        return currentSetting;
    }

    public static void setCurrentSetting(String currentSetting) {
        SWTracker.currentSetting = currentSetting;
    }

    public static HashMap<String, HashMap> getSettings() {
        return settings;
    }

    public static void setSettings(HashMap<String, HashMap> settings) {
        SWTracker.settings = settings;
    }

    public void resetUser() {
        iwk = null;
        selectedUWB = null;
        nwb = null;
        bb = null;
    }

    public static UserWalletBean getSelectedUWB() {
        return selectedUWB;
    }

    public static void setSelectedUWB(UserWalletBean selectedUWB) {
        SWTracker.selectedUWB = selectedUWB;
    }

    public static void initSettings() {
        HashMap<String, String> innerProd = new HashMap<>();
        HashMap<String, String> innerTest = new HashMap<>();
        innerProd.put("api_url", "https://dev.takamaka.io/api/V2/nodeapi/");
        innerProd.put("send_transaction_url", "https://dev.takamaka.io/api/V2/nodeapi/transaction/");
        innerProd.put("bookmark_create_url", "https://takamaka.io/api/v1/bookmark/create/");
        innerProd.put("bookmark_retrieve_url", "https://takamaka.io/api/v1/bookmark/retrieve/");
        innerProd.put("explorer_url", "https://exp.takamaka.dev/");

        innerTest.put("api_url", "https://dev.takamaka.io/api/V2/testapi/");
        innerTest.put("send_transaction_url", "https://dev.takamaka.io/api/V2/testapi/transaction/");
        innerTest.put("bookmark_create_url", "https://dev.takamaka.io/api/v1/bookmark/create/");
        innerTest.put("bookmark_retrieve_url", "https://dev.takamaka.io/api/v1/bookmark/retrieve/");
        innerTest.put("explorer_url", "https://testexplorer.takamaka.dev/");
        settings.put("prod", innerProd);
        settings.put("test", innerTest);
    }

    public static int getCurrIndex() {
        return currIndex;
    }

    public static void setCurrIndex(int currIndex) {
        SWTracker.currIndex = currIndex;
    }

    public static BalanceBean getBb() {
        return bb;
    }

    public static void setBb(BalanceBean bb) {
        SWTracker.bb = bb;
    }

    public OkHttpClient getClient() {
        return client;
    }

    private final OkHttpClient client = new OkHttpClient();


    public ComboItemSettingsBookmarkUrl getCurrentApiUrl() {
        return currentApiUrl;
    }

    public void setCurrentApiUrl(ComboItemSettingsBookmarkUrl currentApiUrl) {
        SWTracker.currentApiUrl = currentApiUrl;
    }

    public ConcurrentSkipListMap<String, ComboItemSettingsBookmarkUrl> getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(ConcurrentSkipListMap<String, ComboItemSettingsBookmarkUrl> apiUrl) {
        SWTracker.apiUrl = apiUrl;
    }

    public URL getBalanceEndpoint() {
        try {
            return new URL(Objects.requireNonNull(SWTracker.getSettings().get(SWTracker.getCurrentSetting())).get("api_url") + FixedParameters.API_BALANCE_ENDPOINT);
        } catch (MalformedURLException ex) {
        }
        return null;
    }

    public String getBalanceKey() {
        return currentApiUrl.getReqKey();

    }

    public ComboItemSettingsBookmarkUrl getCurrentTransactionsEndpoint() {
        return currentTransactionsEndpoint;
    }

    public void setCurrentTransactionsEndpoint(ComboItemSettingsBookmarkUrl currentTransactionsEndpoint) {
        SWTracker.currentTransactionsEndpoint = currentTransactionsEndpoint;
    }

    public ConcurrentSkipListMap<String, ComboItemSettingsBookmarkUrl> getBookmarksUrlShortener() {
        return bookmarksUrlShortener;
    }

    public void setBookmarksUrlShortener(ConcurrentSkipListMap<String, ComboItemSettingsBookmarkUrl> bookmarksUrlShortener) {
        SWTracker.bookmarksUrlShortener = bookmarksUrlShortener;
    }

    public ConcurrentSkipListMap<String, ComboItemSettingsBookmarkUrl> getSendTransactionUrl() {
        return sendTransactionUrl;
    }

    public void setSendTransactionUrl(ConcurrentSkipListMap<String, ComboItemSettingsBookmarkUrl> sendTransactionUrl) {
        SWTracker.sendTransactionUrl = sendTransactionUrl;
    }

    public ConcurrentSkipListMap<String, ComboItemSettingsBookmarkUrl> getFastTag() {
        return fastTag;
    }

    public void setFastTag(ConcurrentSkipListMap<String, ComboItemSettingsBookmarkUrl> fastTag) {
        SWTracker.fastTag = fastTag;
    }


    public ConcurrentSkipListMap<String, ComboItemSettingsBookmarkUrl> getBookmarks() {
        return bookmarksUrlShortener;
    }

    public void setBookmarks(ConcurrentSkipListMap<String, ComboItemSettingsBookmarkUrl> bookmarksUrlShortener) {
        SWTracker.bookmarksUrlShortener = bookmarksUrlShortener;
    }




    public int getCurrentAddressNumber() {
        return currentAddressNumber;
    }

    public void setCurrentAddressNumber(int cxurrentAddressNumber) {
        SWTracker.currentAddressNumber = cxurrentAddressNumber;
    }


    public InstanceWalletKeystoreInterface getIwk() {
        synchronized (SWTLOCK) {
            return iwk;
        }
    }

    public void setIwk(InstanceWalletKeystoreInterface iwk) {
        synchronized (SWTLOCK) {
            SWTracker.iwk = iwk;
        }
    }


    public NewWalletBean getNwb() {
        return nwb;
    }

    public void setNwb(NewWalletBean nwb) {
        SWTracker.nwb = nwb;
    }


    private SWTracker() {
        //nwb = new NewWalletBean();
        //bookmarksToAddresses = new ConcurrentSkipListMap<>();
        //FileHelper
        resetNewWalletBean();
        //setExplorerBaseurlMap(new ConcurrentSkipListMap<String, BaseurlBean>());

    }



    public static ConcurrentSkipListMap<String, ComboItemSettingsBookmarkUrl> getExplorerBaseurlMap() {
        synchronized (SWTLOCK) {
            return explorerBaseurlMap;
        }
    }

    public static void setExplorerBaseurlMap(ConcurrentSkipListMap<String, ComboItemSettingsBookmarkUrl> explorerBaseurlMap) {
        synchronized (SWTLOCK) {
            SWTracker.explorerBaseurlMap = explorerBaseurlMap;
        }
    }

    private static class SWT {

        public static final SWTracker I = new SWTracker();
    }


    public static SWTracker i() {
        return SWT.I;
    }

    public NewWalletBean getNewWalletBean() {
        synchronized (SWTLOCK) {
            return nwb;
        }
    }

    public void resetNewWalletBean() {
        synchronized (SWTLOCK) {
            SWTracker.nwb = new NewWalletBean();
        }
    }

    public void setNewWalletBean(NewWalletBean nwb) {
        synchronized (SWTLOCK) {
            SWTracker.nwb = nwb;
        }
    }
}
