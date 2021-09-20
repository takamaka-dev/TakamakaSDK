package io.takamaka.demo.utils;

import io.takamaka.sdk.exceptions.wallet.UnlockWalletException;
import io.takamaka.sdk.main.defaults.DefaultInitParameters;
import io.takamaka.sdk.utils.FileHelper;
import io.takamaka.sdk.utils.threadSafeUtils.TkmTextUtils;
import io.takamaka.sdk.wallet.InstanceWalletKeyStoreBCED25519;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WalletFXHelper {

    public static synchronized WalletFXHelperErrorBean loadWallet(String password) throws FileNotFoundException {
        WalletFXHelperErrorBean wUnlocked = new WalletFXHelperErrorBean();
        String uwbJson = null;

        Path userWalletOrigPath = Paths.get(FileHelper.getWalletDirectoryPath().toString(), SWTracker.i().getSelectedUWB().getInternalName());

        String wName = SWTracker.i().getSelectedUWB().getInternalName();

        Path userWalletDestPath = Paths.get(FileHelper.getDefaultWalletDirectoryPath().toString(), wName + DefaultInitParameters.WALLET_EXTENSION);

        userWalletOrigPath = userWalletDestPath;
        System.out.println("wallet name and path " + wName + " " + userWalletOrigPath);

        try {
            uwbJson = FileHelper.readStringFromFile(userWalletOrigPath);

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            System.out.println("file not found");
            wUnlocked.error = "file not found";
            return wUnlocked;
        }

        UserWalletBean userWalletBeanFromJson = TkmTextUtils.getUserWalletBeanFromJson(uwbJson);
        userWalletBeanFromJson.setEncryptedWallet(FileHelper.readStringFromFile(userWalletOrigPath));
        userWalletBeanFromJson.setValid(true);

        try {
            FileHelper.writeStringToFile(FileHelper.getDefaultWalletDirectoryPath(), wName + DefaultInitParameters.WALLET_EXTENSION, userWalletBeanFromJson.getEncryptedWallet(), true);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("temporary file write error");
            wUnlocked.error = "temporary file write error";
            return wUnlocked;
        }
        if (userWalletBeanFromJson == null || !userWalletBeanFromJson.isValid()) {

            System.out.println(userWalletBeanFromJson);
            System.out.println(!userWalletBeanFromJson.isValid());


            String err = "unreadable wallet";
            System.out.println(err);
            wUnlocked.error = err;
            return wUnlocked;
        }
        if (TkmTextUtils.isNullOrBlank(password)) {
            String err = "null password";
            System.out.println(err);
            wUnlocked.error = err;
            return wUnlocked;
        }

        try {
            System.out.println("IWK PRIMA: " + SWTracker.i().getIwk());
            SWTracker.i().setIwk(new InstanceWalletKeyStoreBCED25519(wName, password));
        } catch (UnlockWalletException ex) {
            ex.printStackTrace();
            String err = "wrong password";
            System.out.println(err);
            wUnlocked.error = err;
            return wUnlocked;
        }

        wUnlocked.loaded = true;
        return wUnlocked;

    }

    public static int generateRandomNumber() {
        int min = 50;
        int max = 100;
        return (int)(Math.random() * (max - min + 1) + min);

    }

    public static Path getCurrentWalletpath() {
        String currentWalletName = SWTracker.i().getSelectedUWB().getInternalName().substring(0, 40) + DefaultInitParameters.WALLET_EXTENSION;
        Path currentWalletPath = Paths.get(FileHelper.getDefaultWalletDirectoryPath().toString(), currentWalletName);
        return currentWalletPath;
    }

    public static Path getWalletPath() {
        return Paths.get(FileHelper.getDefaultWalletDirectoryPath().toString(), SWTracker.getSelectedUWB().getInternalName() + DefaultInitParameters.WALLET_EXTENSION);
    }

//    public static void submitTransaction(TransactionBox tbox, AsyncActionInterface aai) {
//        try {
//            String hexBody = TkmSignUtils.fromStringToHexString(tbox.getTransactionJson());
//            WalletFXPostSender.endpointRequestTX(SWTracker.i().getCurrentTransactionsEndpoint().getCreateUrl(), hexBody, aai);
//            //WalletFXPostSender.endpointRequestAddress(address, aai);
//        } catch (Exception e) {
//            Log.logStacktrace(Level.SEVERE, e);
//        }
//    }

}
