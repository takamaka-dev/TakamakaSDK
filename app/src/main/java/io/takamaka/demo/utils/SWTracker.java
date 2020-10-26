package io.takamaka.demo.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ConcurrentSkipListMap;

import io.takamaka.sdk.globalContext.FixedParameters;
import io.takamaka.sdk.main.defaults.DefaultInitParameters;
import io.takamaka.sdk.utils.ComboItemSettingsBookmarkUrl;
import io.takamaka.sdk.wallet.InstanceWalletKeystoreInterface;
import io.takamaka.sdk.wallet.NewWalletBean;
import io.takamaka.sdk.wallet.beans.BalanceBean;
import okhttp3.OkHttpClient;


public class SWTracker {

    private static final Object SWTLOCK = new Object();
    private static NewWalletBean nwb = null;
    private static BalanceBean bb = null;
    private static InstanceWalletKeystoreInterface iwk;
    private static int currentAddressNumber = 0;
    private static ComboItemSettingsBookmarkUrl currentApiUrl = DefaultInitParameters.DEFAULT_API_URL;
    private static ComboItemSettingsBookmarkUrl currentTransactionsEndpoint = DefaultInitParameters.DEFAULT_SEND_TRANSACTION_URL;
    private static ConcurrentSkipListMap<String, ComboItemSettingsBookmarkUrl> explorerBaseurlMap;
    private static ConcurrentSkipListMap<String, ComboItemSettingsBookmarkUrl> bookmarksUrlShortener;
    private static ConcurrentSkipListMap<String, ComboItemSettingsBookmarkUrl> sendTransactionUrl;
    private static ConcurrentSkipListMap<String, ComboItemSettingsBookmarkUrl> apiUrl;
    private static ConcurrentSkipListMap<String, ComboItemSettingsBookmarkUrl> fastTag;

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
            URL u = new URL(currentApiUrl.getCreateUrl() + FixedParameters.API_BALANCE_ENDPOINT);
            return u;
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
