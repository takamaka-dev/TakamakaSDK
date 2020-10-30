package io.takamaka.sdk.main.defaults;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import io.takamaka.sdk.globalContext.KeyContexts;
import io.takamaka.sdk.transactions.BuilderITB;
import io.takamaka.sdk.transactions.InternalTransactionBean;

public class TransactionGenerator {

    public static InternalTransactionBean getTransactionBean(KeyContexts.TransactionType type, String from, String to, BigInteger greenValue, BigInteger redValue, String message) {
        InternalTransactionBean itb = BuilderITB.pay(from, to, greenValue, redValue, message);
        return itb;
    }

    public static InternalTransactionBean getTransactionBean(KeyContexts.TransactionType type, String from, String to, BigInteger greenValue, BigInteger redValue, String message, Date notBefore, int epoch, int slot) {
        InternalTransactionBean itb = BuilderITB.pay(from, to, greenValue, redValue, message, notBefore);
        System.out.println("Internal transaction bean terzo: " + itb);
        return itb;
    }
}
