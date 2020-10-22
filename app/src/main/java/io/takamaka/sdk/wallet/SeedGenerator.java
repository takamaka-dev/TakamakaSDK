package io.takamaka.sdk.wallet;

import io.takamaka.sdk.exceptions.threadSafeUtils.HashAlgorithmNotFoundException;
import io.takamaka.sdk.exceptions.threadSafeUtils.HashEncodeException;
import io.takamaka.sdk.exceptions.threadSafeUtils.HashProviderNotFoundException;
import io.takamaka.sdk.globalContext.FixedParameters;
import io.takamaka.sdk.utils.threadSafeUtils.TkmSignUtils;
import io.takamaka.sdk.utils.threadSafeUtils.TkmTextUtils;
import org.apache.commons.io.IOUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SeedGenerator {

    public static String[] words = new String[FixedParameters.WALLET_DICTIONARY_LENGTH];

    public static boolean inizialized = false;
    //public static int[] statWords = new int[FixedParameters.WALLET_DICTIONARY_LENGTH];

    public static void init() {
        BufferedReader reader = null;
        try {
            String fileName = FixedParameters.WALLET_DICTIONARY_FILE;
            ClassLoader classLoader = new SeedGenerator().getClass().getClassLoader();
            reader = new BufferedReader(new StringReader(IOUtils.toString(classLoader.getResource(fileName), FixedParameters.CHARSET)));
            String mLine;
            int i = 0;
            while ((!TkmTextUtils.isNullOrBlank((mLine = reader.readLine()))) && (i < FixedParameters.WALLET_DICTIONARY_LENGTH)) {
                words[i] = mLine;
                i++;
            }
        } catch (IOException e) {
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                }
            }
        }

        /*for (int i = 0; i < statWords.length; i++) {
            statWords[i]=0;
        }*/
    }

    /**
     * It generates a random 25 list of words used for wallet creation
     * @return List<String> of words
     * @throws NoSuchAlgorithmException
     */
    public static List<String> generateWords() throws NoSuchAlgorithmException {
        try {
            List<String> rndWords = new ArrayList<String>();

            if (!inizialized) {
                init();
                inizialized = true;
            }

            String concat = "";
            Random rand = new SecureRandom();
            for (int i = 0; i < FixedParameters.WALLET_WORDS_NUMBER - 2; i++) {
                int rnd = rand.nextInt(2048);
                rndWords.add(words[rnd]);
                concat += words[rnd];
            }

            int index;
            index = (new BigInteger(TkmSignUtils.PWHash(concat, "TakamakaWalletWords", 1, 4096)).mod(BigInteger.valueOf(FixedParameters.WALLET_DICTIONARY_LENGTH))).intValue();
            String secondToLastWord = words[index];
            concat += words[index];
            index = (new BigInteger(TkmSignUtils.PWHash(concat, "TakamakaWalletWords", 1, 4096)).mod(BigInteger.valueOf(FixedParameters.WALLET_DICTIONARY_LENGTH))).intValue();
            String lastWord = words[index];
            rndWords.add(secondToLastWord);
            rndWords.add(lastWord);
            return rndWords;
        } catch (HashEncodeException | HashAlgorithmNotFoundException | HashProviderNotFoundException | InvalidKeySpecException ex) {
            Logger.getLogger(SeedGenerator.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static boolean verifySeedWords(List<String> wordsList) {
        try {
            if (wordsList.size() != FixedParameters.WALLET_WORDS_NUMBER) {
                return false;
            }

            if (!inizialized) {
                init();
                inizialized = true;
            }

            String concat = "";
            for (int i = 0; i < wordsList.size() - 2; i++) {
                concat += wordsList.get(i);
            }

            int index;
            index = (new BigInteger(TkmSignUtils.PWHash(concat, "TakamakaWalletWords", 1, 4096)).mod(BigInteger.valueOf(FixedParameters.WALLET_DICTIONARY_LENGTH))).intValue();
            String secondToLastWord = words[index];
            concat += words[index];
            index = (new BigInteger(TkmSignUtils.PWHash(concat, "TakamakaWalletWords", 1, 4096)).mod(BigInteger.valueOf(FixedParameters.WALLET_DICTIONARY_LENGTH))).intValue();
            String lastWord = words[index];

            return (lastWord.equals(wordsList.get(wordsList.size() - 1)) && secondToLastWord.equals(wordsList.get(wordsList.size() - 2)));

        } catch (HashEncodeException | HashAlgorithmNotFoundException | HashProviderNotFoundException | InvalidKeySpecException | NoSuchAlgorithmException ex) {
            Logger.getLogger(SeedGenerator.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     *
     * @param rndWords the generated list of words
     * @return String is the generated seed
     * @throws NoSuchAlgorithmException
     * @throws HashEncodeException
     * @throws InvalidKeySpecException
     * @throws HashAlgorithmNotFoundException
     * @throws HashProviderNotFoundException
     */
    public static String generateSeedPWH(List<String> rndWords) throws NoSuchAlgorithmException, HashEncodeException, InvalidKeySpecException, HashAlgorithmNotFoundException, HashProviderNotFoundException {
        int saltIndex = 0;
        init();

//        System.out.println("Words list:" + Arrays.toString(rndWords.toArray()));
        for (int i = 0; i < words.length; i++) {
            if (rndWords.get(0).equals(words[i])) {
                saltIndex = i;

                //              System.out.println("Index: " + saltIndex);
                break;
            }
        }

        List<String> hashTable;
        String salt = String.valueOf(saltIndex);

//        System.out.println("salt: " + salt);
        String tempWord = "";

//        System.out.println("temp word: " + tempWord);
//        System.out.println("for loop begin...");
//        int extCounter = 0;
        for (String word : rndWords) {
//            int intCounter = 0;

            hashTable = new ArrayList<>();
            tempWord = word;

//            System.out.println("extCounter: " + extCounter + " intCounter: " + intCounter + " tempWord: " + tempWord);
            for (int i = 0; i < FixedParameters.WALLET_DICTIONARY_LENGTH; i++) {

                tempWord = TkmSignUtils.PWHashB64(tempWord, salt, 1, 768);

                hashTable.add(tempWord);
//                intCounter++;
            }

//            System.out.println("extCounter: " + extCounter + " intCounter: " + intCounter + " hashTable: " + Arrays.toString(hashTable.toArray()));
            int modIndex = new BigInteger(salt.getBytes()).abs().mod(new BigInteger("2048")).intValue();

//            System.out.println("mod index: " + modIndex);
            salt += hashTable.get(modIndex);

            //           System.out.println("concat salt: " + salt);
//            extCounter++;
        }

//        System.out.println("for loop end");
//        System.out.println("Seed:" + tempWord);
        return tempWord;
    }

    /**
     * convert the seed to seed string
     *
     * @param
     * @return
     */
    public static synchronized String toStringSeed(List<String> rndWords) {
        return String.join(" ", rndWords);
    }


}
