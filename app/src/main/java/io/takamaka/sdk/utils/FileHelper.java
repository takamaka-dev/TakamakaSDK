package io.takamaka.sdk.utils;


import android.content.Context;
import android.content.ContextWrapper;
import android.os.Environment;

import io.takamaka.sdk.exceptions.threadSafeUtils.HashAlgorithmNotFoundException;
import io.takamaka.sdk.exceptions.threadSafeUtils.HashEncodeException;
import io.takamaka.sdk.exceptions.threadSafeUtils.HashProviderNotFoundException;
import io.takamaka.sdk.exceptions.wallet.UnlockWalletException;
import io.takamaka.sdk.globalContext.FixedParameters;
import static io.takamaka.sdk.globalContext.FixedParameters.*;
import static io.takamaka.sdk.main.defaults.DefaultInitParameters.*;
import io.takamaka.sdk.main.defaults.DefaultInitParameters;
import io.takamaka.sdk.utils.threadSafeUtils.TkmSignUtils;
import io.takamaka.sdk.wallet.beans.KeyBean;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchProviderException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author giovanni.antino@h2tcoin.com
 */
public class FileHelper {

    public static final Object KEYLOCK = new Object();



    /**
     * return the default path to application directory
     *
     * @return
     */
    public static Path getDefaultApplicationDirectoryPath() {
        return Paths.get(DefaultInitParameters.APPLICATION_HOME, DefaultInitParameters.APPLICATION_ROOT_FOLDER_NAME);
    }

    public static Path getWalletPath() {
        return Paths.get(DefaultInitParameters.APPLICATION_HOME, DefaultInitParameters.APPLICATION_ROOT_FOLDER_NAME, "/wallets/");
    }

    public static Path getThemeConfigFilePath() {
        String userHome = DefaultInitParameters.APPLICATION_HOME;
        return Paths.get(userHome, DefaultInitParameters.APPLICATION_ROOT_FOLDER_NAME, "themeConfig.json");
    }

    public static Path getHotmokaTestDirectoryPath() {
        String userHome = DefaultInitParameters.APPLICATION_HOME;
        //Log.log(Level.INFO, userHome);
        return Paths.get(getDefaultApplicationDirectoryPath().toString(), DefaultInitParameters.HOTMOKA_TEST_FOLDER_NAME);
    }

    public static Path getHotmokaFilesDirectoryPath() {
        String userHome = DefaultInitParameters.APPLICATION_HOME;
        //Log.log(Level.INFO, userHome);
        return Paths.get(getDefaultApplicationDirectoryPath().toString(), DefaultInitParameters.HOTMOKA_FILES_FOLDER_NAME);
    }

    /**
     * create project directory and folder structure using default parameters
     *
     * @throws IOException
     */
    public static void initProjectFiles() throws IOException {

        //init project home
        if (!homeDirExists()) {
            createDir(getDefaultApplicationDirectoryPath());
            //Log.log(Level.INFO , "Home directory created");
        }
        //init logs directory
        if (!logsDirectoryExists()) {
            createDir(getDefaultLogsDirectoryPath());
            //F.setConsolePrintFine();
            //F.setConsolePrintInfo();
            //F.setConsolePrintSevere();
        }
        //init wallets home
        if (!walletDirExists()) {
            createDir(getDefaultWalletDirectoryPath());
        }

        //init publickeys directory
        if (!publicKeyDirExists()) {
            createDir(getPublicKeyDirectoryPath());
        }

        //init publickeys directory
        if (!transactionsDirExists()) {
            createDir(getTransactionsDirectoryPath());
        }

        //init ephemeral wallets home
        if (!walletEphemeralDirExists()) {
            createDir(getEphemeralWalletDirectoryPath());
        }

        if (!directoryExists(getSettingsPathFolder())) {
            FileHelper.createDir(getSettingsPathFolder());
        }

        Path tboxCacheDir = Paths.get(FileHelper.getDefaultApplicationDirectoryPath().toString(), FixedParameters.TRANSACTION_BOX_CACHING_FOLDER);
        if (!FileHelper.directoryExists(tboxCacheDir)) {
            createDir(tboxCacheDir);
        }

        Path userWalletFolder = Paths.get(FileHelper.getDefaultApplicationDirectoryPath().toString(), FixedParameters.USER_WALLETS_FOLDER);
        if (!FileHelper.directoryExists(userWalletFolder)) {
            createDir(userWalletFolder);
        }

        //Path txsDumpFolder = Paths.get(settingsFolder.toString(), FixedParameters.API_SETTINGS);
        if (!getTransactionsDumpPathFolder().toFile().isDirectory()) {
            FileHelper.createDir(getTransactionsDumpPathFolder());
        }

        if (!getSimulationPathFolder().toFile().isDirectory()) {
            FileHelper.createDir(getSimulationPathFolder());
        }

        if (!getHotmokaFilesDirectoryPath().toFile().isDirectory()) {
            FileHelper.createDir(getHotmokaFilesDirectoryPath());
        }

        if (!getHotmokaTestDirectoryPath().toFile().isDirectory()) {
            FileHelper.createDir(getHotmokaTestDirectoryPath());
        }

        Path hotmokaTestDirectoryPath = getHotmokaTestDirectoryPath();
        //deleteFolderContent(hotmokaTestDirectoryPath.toFile());

        Path settingsFolder = getSettingsPathFolder();
        Path settingsBookmarksFile = Paths.get(settingsFolder.toString(), FixedParameters.SETTINGS_BOOKMARKS);
        if (!FileHelper.directoryExists(settingsFolder)) {
            createDir(settingsFolder);
        }


    }

    /**
     * return the default user wallets directory
     *
     * @return
     */
    public static Path getUserWalletsDirectoryPath() {
        String userHome = DefaultInitParameters.APPLICATION_HOME;
        //Log.log(Level.INFO, userHome);
        return Paths.get(getDefaultApplicationDirectoryPath().toString(), FixedParameters.USER_WALLETS_FOLDER);
    }

    public static Path getWalletDirectoryPath() {
        String userHome = DefaultInitParameters.APPLICATION_HOME;
        return Paths.get(getDefaultApplicationDirectoryPath().toString(), FixedParameters.WALLET_FOLDER);
    }

    /**
     * return the path of the application state file
     *
     * @return
     */
    public static Path getDefaultStatePath() {
        return Paths.get(FileHelper.getDefaultApplicationDirectoryPath().toString(), CURRENT_STATE_FILE + STATE_FILE_EXTENSION);
    }

    /**
     * true if the application state file exists
     *
     * @return
     */
    public static boolean defaultStatePathExists() {
        return Paths.get(FileHelper.getChainDirectory().toString(), CURRENT_STATE_FILE + STATE_FILE_EXTENSION).toFile().exists();
    }

    /**
     * @return true if the default project home dir exist
     */
    public static boolean homeDirExists() {
        Path applicationDirectoryPath = getDefaultApplicationDirectoryPath();
        File applicationDirectoryFilePointer = applicationDirectoryPath.toFile();
        return applicationDirectoryFilePointer.isDirectory();
    }

    /**
     * @return true if the theme configuration file exists
     */
    public static boolean themeConfigFileExists() {
        Path themePath = getThemeConfigFilePath();
        File themeFile = themePath.toFile();
        return themeFile.isFile();
    }



    /**
     *
     * @return default wallet directory path
     */
    public static Path getDefaultWalletDirectoryPath() {
        return Paths.get(getDefaultApplicationDirectoryPath().toString(), WALLET_FOLDER);
    }

    public static Path getSettingsFileJsonPath() {
        return Paths.get(getDefaultApplicationDirectoryPath().toString(), SETTINGS_FILE_JSON);
    }

    public static Path getSettingsDirectoryPath() {
        return Paths.get(getDefaultApplicationDirectoryPath().toString(), "settings");
    }

    /**
     *
     * @return default public keys directory path
     */
    public static Path getPublicKeyDirectoryPath() {
        return Paths.get(getDefaultWalletDirectoryPath().toString(), PUBLICKEY_FOLDER);
    }

    /**
     *
     * @return default transactions directory path
     */
    public static Path getTransactionsDirectoryPath() {
        return Paths.get(getDefaultWalletDirectoryPath().toString(), TRANSACTION_FOLDER);
    }

    /**
     *
     * @return ephemeral wallet directory path
     */
    public static Path getEphemeralWalletDirectoryPath() {
        return Paths.get(getDefaultApplicationDirectoryPath().toString(), WALLET_EPHEMERAL_FOLDER);
    }

    /**
     *
     * @return default address book directory path
     */
    public static Path getDefaultAddressBookDirectoryPath() {
        return Paths.get(getDefaultWalletDirectoryPath().toString(),
                ADDRESS_BOOK_FOLDER);
    }

    /**
     *
     * @return default logs directory path
     */
    public static Path getDefaultLogsDirectoryPath() {
        return Paths.get(getDefaultApplicationDirectoryPath().toString(),
                LOGS_FOLDER);
    }

    /**
     * convert pattern from {@code Log.getLogFilePattern()} to path
     *
     * @param pattern
     * @return
     */
    public static Path logFile(String pattern) {
        return Paths.get(getDefaultLogsDirectoryPath().toString(), pattern);
    }

    /**
     * return the path of default chain directory
     *
     * @return
     */
    public static Path getChainDirectory() {
        return Paths.get(getDefaultApplicationDirectoryPath().toString(),
                CHAIN_FOLDER);
    }

    /**
     * default path for chain lock file
     *
     * @return
     */
    public static Path getChainLockFile() {
        return Paths.get(getChainDirectory().getParent().toString(), CHAIN_LOCK_FILE);
    }

    /**
     * return the path to the epoch folder
     *
     * @param epochNumber
     * @return
     */
    public static Path getEpochDirectory(int epochNumber) {
        return Paths.get(getChainDirectory().toString(),
                EPOCH_FOLDER_PREFIX + epochNumber);
    }

    /**
     * return the path to the slot directory
     *
     * @param epochNumber
     * @param slotNumber
     * @return
     */
    public static Path getSlotDirectory(int epochNumber, int slotNumber) {
        return Paths.get(getEpochDirectory(epochNumber).toString(),
                SLOT_FOLDER_PREFIX + slotNumber);
    }

    /**
     * return the path to the zero block folder
     *
     * @return
     */
    public static Path getDefaultZeroBlockDirectory() {
        return Paths.get(getDefaultApplicationDirectoryPath().toString(), ZERO_BLOCK_FOLDER);
    }

    /**
     * return the path to the block required to initialize the chain
     *
     * @return
     */
    public static Path getDefaultZeroBlockFile() {
        return Paths.get(getDefaultZeroBlockDirectory().toString(), ZERO_BLOCK_FILE_NUMBER);
    }

    /**
     * return the bucket directory
     *
     * @param epoch
     * @param slot
     * @param uid
     * @param hkw
     * @return
     */
    public static Path getKeyWriterBucketDirectory(int epoch, int slot, String uid, HexKeyWriter hkw) {
        return Paths.get(getSlotDirectory(epoch, slot).toString(), uid + "." + hkw.name());
    }

    public static Path initKeyHexWriterDirectoryTree(int epoch, int slot, String uid, HexKeyWriter hkw) {
        Path baseDir = null;
        try {
            baseDir = FileHelper.getKeyWriterBucketDirectory(epoch, slot, uid, hkw);
            FileHelper.createDir(
                    baseDir
            );


        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return baseDir;
    }

    /**
     * First block directory
     *
     * @return true if exists and is a directory
     */
    public static boolean defaultZeroBlockDirectoryExists() {
        return getDefaultZeroBlockDirectory().toFile().isDirectory();
    }

    /**
     * First block
     *
     * @return true if is file and exixsts
     */
    public static boolean defaultZeroBlockFileExists() {
        return getDefaultZeroBlockFile().toFile().isFile();
    }

    /**
     * Slot directory
     *
     * @param epochNumber
     * @param slotNumber
     * @return true if exists and is a directory
     */
    public static boolean slotDirectoryExists(int epochNumber, int slotNumber) {
        return getSlotDirectory(epochNumber, slotNumber).toFile().isDirectory();
    }

    /**
     * Epoch directory
     *
     * @param epochNumber
     * @return true if exists and is a directory
     */
    public static boolean epochDirectoryExists(int epochNumber) {
        return getEpochDirectory(epochNumber).toFile().isDirectory();
    }

    public static Path getNodeNetworkSettings() {
        Path settingsPathFolder = FileHelper.getSettingsPathFolder();
        Path nodeNetworkSettingsPath = Paths.get(settingsPathFolder.toString(), FixedParameters.NODE_NETWORK_SETTINGS_FILE);
        return nodeNetworkSettingsPath;
    }

    /**
     * Chain directory
     *
     * @return true if exists and is a directory
     */
    public static boolean chainDirectoryExists() {
        return getChainDirectory().toFile().isDirectory();
    }

    /**
     * Log directory
     *
     * @return true if exists and is a directory
     */
    public static boolean logsDirectoryExists() {
        return getDefaultLogsDirectoryPath().toFile().isDirectory();
    }

    /**
     * Create slot directory and return path, or return slot directory path if
     * exists
     *
     * @param epochNumber
     * @param slotNumber
     * @return slot directory path
     * @throws IOException
     */
    public static Path createSlotDirectory(int epochNumber, int slotNumber) throws IOException {
        if (!chainDirectoryExists()) {
            createDir(getChainDirectory());
        }
        if (!epochDirectoryExists(epochNumber)) {
            createDir(getEpochDirectory(epochNumber));
        }
        if (!slotDirectoryExists(epochNumber, slotNumber)) {
            createDir(getSlotDirectory(epochNumber, slotNumber));
        }
        return getSlotDirectory(epochNumber, slotNumber);
    }

    /**
     * Creates a directory at the indicated path if none exist already
     *
     * @param path
     * @throws IOException
     */
    public static void createFolderAtPathIfNoneExist(Path path) throws IOException {

        if (!path.toFile().isDirectory()) {
            createDir(path);
        }
    }

    /**
     *
     * @return
     */
    public static Path getTransactionBoxFolder(int epoch, int slot) {
        Path result = null;

        result = Paths.get(getSlotDirectory(epoch, slot).toString(), FixedParameters.TRANSACTION_BOX_FOLDER);

        return result;
    }

    /**
     *
     * @return
     */
    public static Path getTransactionBoxDateFolder(Date input) {
        Path result = null;

        DateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");
        String date = dateformat.format(input);
        result = Paths.get(getDefaultAddressBookDirectoryPath().toString(), date + "_" + FixedParameters.TRANSACTION_BOX_FOLDER);

        return result;
    }

    /**
     * convert uid into database name
     *
     * @param blockUID
     * @return
     */
    public static String getBlockName(String blockUID) {
        return STATE_DB_PREFIX + blockUID;
    }

    /**
     * return the path without extension
     *
     * @param epochNumber
     * @param slotNumber
     * @param blockHashNumber {@code StringUtils.getBlockHashNumber(blockHash)}
     * @return return the path for the requested block
     */
    public static Path getBlockPath(int epochNumber, int slotNumber, String blockHashNumber) {
        return Paths.get(getSlotDirectory(epochNumber, slotNumber).toString(), blockHashNumber);
    }

    /**
     * return the path with extension
     *
     * @param epochNumber
     * @param slotNumber
     * @param blockHashNumber {@code StringUtils.getBlockHashNumber(blockHash)}
     * @return return the path for the requested block
     */
    public static Path getBlockPathWithExension(int epochNumber, int slotNumber, String blockHashNumber) {
        return Paths.get(getSlotDirectory(epochNumber, slotNumber).toString(), blockHashNumber + FixedParameters.BLOCK_FILE_EXTENSION);
    }

    /**
     *
     * @param epochNumber
     * @param slotNumber
     * @param blockHashNumber
     * @return true if file exists and is file
     */
    public static boolean blockExists(int epochNumber, int slotNumber, String blockHashNumber) {
        return chainDirectoryExists()
                && epochDirectoryExists(epochNumber)
                && slotDirectoryExists(epochNumber, slotNumber)
                && getBlockPath(epochNumber, slotNumber, blockHashNumber).toFile().isFile();
    }

    /**
     *
     * @return true if the directory exist
     */
    public static boolean walletDirExists() {
        return getDefaultWalletDirectoryPath().toFile().isDirectory();
    }

    public static boolean settingsJsonFileExists() {
        return getSettingsFileJsonPath().toFile().isFile();
    }

    /**
     *
     * @return true if the directory exist
     */
    public static boolean publicKeyDirExists() {
        return getPublicKeyDirectoryPath().toFile().isDirectory();
    }

    /**
     *
     * @return true if the directory exist
     */
    public static boolean transactionsDirExists() {
        return getTransactionsDirectoryPath().toFile().isDirectory();
    }

    /**
     *
     * @return true if the directory exist
     */
    public static boolean walletEphemeralDirExists() {
        return getEphemeralWalletDirectoryPath().toFile().isDirectory();
    }

    /**
     *
     * @return true if the address book exist
     */
    public static boolean addressBookDirExists() {
        return getDefaultAddressBookDirectoryPath().toFile().isDirectory();
    }

    /**
     * create directory the path is built using {@code Paths.get()}
     *
     * @param directoryPathAndName
     * @throws IOException
     */
    public static void createDir(Path directoryPathAndName) throws IOException {
        Files.createDirectory(directoryPathAndName);
    }

    public static Path getSettingsPathFolder() {
        return Paths.get(FileHelper.getDefaultApplicationDirectoryPath().toString(), FixedParameters.SETTINGS_FOLDER);
    }

    public static Path getTransactionsDumpPathFolder() {
        return Paths.get(FileHelper.getDefaultApplicationDirectoryPath().toString(), FixedParameters.TRANSACTIONS_DUMP_FOLDER);
    }

    public static Path getSimulationPathFolder() {
        return Paths.get(FileHelper.getDefaultApplicationDirectoryPath().toString(), FixedParameters.SIMULATION_DUMP_FOLDER);
    }

    public static Path getTransactionsDumpPathFolder(String pidName) {
        Path baseDumpFolder = Paths.get(FileHelper.getDefaultApplicationDirectoryPath().toString(), FixedParameters.TRANSACTIONS_DUMP_FOLDER);
        if (!baseDumpFolder.toFile().isDirectory()) {
            try {
                FileHelper.createDir(baseDumpFolder);
            } catch (IOException ex) {
            }
        }
        if (baseDumpFolder.toFile().isDirectory()) {
            try {
                Path inner = Paths.get(FileHelper.getDefaultApplicationDirectoryPath().toString(), FixedParameters.TRANSACTIONS_DUMP_FOLDER, pidName);
                FileHelper.createDir(inner);
                if (!inner.toFile().isDirectory()) {
                    return null;
                }
                return inner;
            } catch (IOException ex) {
            }
        }
        return null;
    }


    public static String getReferenceKeyStringNoSave(String qAddr) {
        String refQAddrFraction;
        try {
            refQAddrFraction = TkmSignUtils.Hash256ToHex(qAddr);
        } catch (HashEncodeException | HashAlgorithmNotFoundException | HashProviderNotFoundException ex) {
            return null;
        }
        return FixedParameters.REFERENCE_QTESLA_ADDR_PREFIX + refQAddrFraction;
    }


    /**
     *
     * @param file
     * @return true if exists and is a file
     */
    public static boolean fileExists(Path file) {
        return file.toFile().isFile();
    }

    /**
     *
     * @param filePointer
     * @return true if exists and is a directory
     */
    public static boolean directoryExists(Path filePointer) {
        return filePointer.toFile().isDirectory();
    }

    /**
     * Write a string to file<br>
     * If file does not exist the string will be written to file and the
     * function return true.<br>
     * <pre>
     * <code>FileHelper.writeStringToFile(
     *              FileHelper.getDefaultApplicationDirectoryPath(),
     *              "TestFile.txt", "Only create, no overwrite", false)
     * </code></pre><br>
     * If file exists and overwite is specified the original file is deleted,
     * new one is created, the string is written in it and the function return
     * true.<br>
     * <pre>
     * <code>FileHelper.writeStringToFile(
     *              FileHelper.getDefaultApplicationDirectoryPath(),
     *              "TestFile.txt", "Create or Overwrite", true)
     * </code><br></pre>
     * <br>
     *
     * If file exists, overvrite is denied function terminate and return false
     *
     * @param directory destination directory
     * @param fileName file name
     * @param content string to be writed to file
     * @param overwrite true for overwriting content if file exists, ignored
     * otherwise
     * @return true if succesfully written
     * @throws IOException
     */
    public static boolean writeStringToFile(Path directory, String fileName, String content, boolean overwrite) throws IOException {
        synchronized (KEYLOCK) {
            Path filePath = Paths.get(directory.toString(), fileName);
            if (!fileExists(filePath)) {
                Files.createFile(filePath);
            } else {
                //file exist, checking overwrite flag
                if (overwrite) {
                    //Log.log(Level.INFO, "Deleting obsolete content:" + fileName);
                    //filePath.toFile().delete();
                    filePath.toFile().createNewFile();
                } else {
                    // file exist no overwriting
                    return false;
                }
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()));
            writer.write(content);
            writer.close();
            return true;
        }
    }


    public static ArrayList<String> getWalletList() {
        Path folderPath = FileHelper.getDefaultWalletDirectoryPath();
        ArrayList<String> result = null;
        File folder = new File(folderPath.toString());
        File[] listOfFiles = folder.listFiles();
        String fileName;
        result = new ArrayList<>();
        for (File f : listOfFiles) {
            if (f.isFile()) {
                fileName = f.getName().substring(0, f.getName().length() - WALLET_EXTENSION.length());
                result.add(fileName);
            }
        }
        return result;
    }

    /**
     * list file in the passed folder
     *
     * @param folderPath
     * @return
     */
    public static File[] getFolderContent(Path folderPath) {
        File[] result = null;
        File folder = new File(folderPath.toString());
        result = folder.listFiles();
        return result;
    }

    /**
     * delete passed file or the folder and all its content, recursively. If f is dir f is deleted
     *
     * @param f
     * @throws IOException
     */
    public static void delete(File f) throws IOException {
        if (f.isDirectory()) {
            for (File c : f.listFiles()) {
                delete(c);
            }
        }
        if (!f.delete()) {
            throw new FileNotFoundException("Failed to delete file: " + f);
        }
    }

    /**
     *
     * @param filePath
     */
    public static void deleteSingleFile(Path filePath) {
        if (filePath.toFile().exists()) {
            boolean delete = filePath.toFile().delete();
            if (!delete) {
            }
        } else {
        }
    }

    /**
     * delete RECURSIVELY everithing in the path
     *
     * @param file
     * @throws IOException
     */
    public static void delete(Path file) throws IOException {
        File f = new File(file.toString());
        if (f.isDirectory()) {
            throw new IOException("Failed, i can't deleate a direcotry: " + f);
        }
        if (!f.delete()) {
            throw new FileNotFoundException("Failed to delete file: " + f);
        }
    }

    /**
     * recursively delete folder content
     *
     * TODO delete root folder?
     *
     * @param f
     * @throws IOException
     */
    public static void deleteFolderContent(File f) throws IOException {
        if (f.isFile()) {
        }

        if (f.isDirectory()) {
            for (File listFile : f.listFiles()) {
                try {
                    delete(listFile);

                } catch (Exception e) {
                }
            }
        }

    }

    /**
     * reads a string from the file indicated in the path if it exists
     *
     * @param file {@code Path}
     * @return
     * @throws FileNotFoundException
     */
    public static String readStringFromFile(Path file) throws FileNotFoundException {
//        synchronized (KEYLOCK) {
        if (!file.toFile().isFile()) {
            throw new FileNotFoundException();
        }
        String content = null;
        try {
            content = new String(Files.readAllBytes(file));
        } catch (IOException ex) {
        }
        return content;
        //}
    }

    /**
     * moves the target file/folder to the new destination and can overwrite the
     * existing file/folder if it exists
     *
     * @param sourcePath can be of type String/File/Path
     * @param targetPath can be of type String/File/Path
     * @param overwrite indicated whether to overwrite existing files/folders
     * @throws IOException
     */
    public static void rename(Object sourcePath, Object targetPath, Boolean overwrite) throws IOException {
        File sourceFile = null, targetFile = null;
        Boolean success = false;

        if (sourcePath instanceof String && targetPath instanceof String) {
            sourceFile = new File((String) sourcePath);
            targetFile = new File((String) targetPath);
        } else if (sourcePath instanceof Path && targetPath instanceof Path) {
            sourceFile = ((Path) sourcePath).toFile();
            targetFile = ((Path) targetPath).toFile();
        } else if (sourcePath instanceof File && targetPath instanceof File) {
            sourceFile = (File) sourcePath;
            targetFile = (File) targetPath;
        }
        if (overwrite && targetFile.exists()) {
            delete(targetFile);

        }
        success = sourceFile.renameTo(targetFile);
        //F.b("move attempt result: " + success);
    }

    /**
     * OVERWRITE
     *
     * @param sources
     * @param destination
     * @throws IOException
     */
    public static void copy(Path sources, Path destination) throws IOException {
        Files.copy(sources, destination, StandardCopyOption.REPLACE_EXISTING);
    }


    /**
     *
     * @param path
     * @param input
     */
    public static void writeTransactionBoxToFile(Path path, Object input) {
        FileOutputStream fout = null;
        try {
            fout = new FileOutputStream(path.toString());
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(input);

            oos.flush();
            oos.close();

        } catch (IOException ex) {
        } finally {
            try {
                if (fout != null) {
                    fout.close();
                }
            } catch (IOException ex) {
            }
        }
    }

    /**
     *
     * @param path
     * @param sithNumber
     * @param input
     */
    public static void writeObjectToFile(Path path, String sithNumber, Object input) {
        if (!path.toFile().isDirectory()) {
            try {
                createFolderAtPathIfNoneExist(path);
            } catch (IOException ex) {
            }
        }
        Path filePath = Paths.get(path.toString(), sithNumber);
        writeTransactionBoxToFile(filePath, input);

    }

    /**
     *
     * @param filePath
     * @return
     */
    public static Object readObjectFromFile(Path filePath) {
        Object result = null;
        try {
            if (filePath.toFile().isFile()) {
                FileInputStream fileIn = new FileInputStream(filePath.toFile());
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);

                result = objectIn.readObject();
                objectIn.close();
                fileIn.close();
            }

        } catch (IOException | ClassNotFoundException ex) {
        }
        return result;
    }

    /**
     *
     * @param filepath
     * @return
     */
    public static Object readObjectFromFile(String filepath) {
        Object result = null;
        try {

            FileInputStream fileIn = new FileInputStream(filepath);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            result = objectIn.readObject();
            objectIn.close();
            fileIn.close();

        } catch (IOException | ClassNotFoundException ex) {
            return null;
        }
        return result;
    }

}
