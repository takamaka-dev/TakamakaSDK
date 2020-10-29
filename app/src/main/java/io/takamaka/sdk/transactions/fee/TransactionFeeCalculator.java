package io.takamaka.sdk.transactions.fee;

import io.takamaka.sdk.db.embedded.balance.BalanceBean;
import io.takamaka.sdk.main.defaults.DefaultInitParameters;
import io.takamaka.sdk.utils.threadSafeUtils.TkmSignUtils;
import io.takamaka.sdk.wallet.TransactionBox;

import java.math.BigInteger;
import java.util.logging.Level;

public class TransactionFeeCalculator {

    public static FeeBean getFeeBean(TransactionBox tbox) {
        FeeBean ret = new FeeBean();
        //System.out.println(tbox.getTransactionJson());
        if (tbox != null) {
            if (tbox.isValid()) {
                ret.setSith(tbox.getSingleInclusionTransactionHash());
                ret.setAddr(tbox.from());
                ret.setHexAddr(TkmSignUtils.fromB64UrlToHEX(tbox.from()));
                switch (tbox.type()) {
                    case ASSIGN_OVERFLOW:
                    case BLOB:
                    case DEREGISTER_OVERFLOW:
                    case PAY:
                    case STAKE:
                    case STAKE_UNDO:
                    case UNASSIGN_OVERFLOW:
                    case REGISTER_MAIN:
                    case REGISTER_OVERFLOW:
                    case S_CONTRACT_DEPLOY:
                    case DEREGISTER_MAIN:
                        ret.setCpu(BigInteger.ZERO);
                        ret.setMemory(BigInteger.ZERO);
                        ret.setDisk(new BigInteger(tbox.getTransactionJson().length() + "")
                                .multiply(DefaultInitParameters.DISK_SCALE)
                                .multiply(DefaultInitParameters.TARGET_CLIENT_NUMBER_MAX_BI)
                                .multiply(DefaultInitParameters.YEARS_MOORE_LAW)
                                .multiply(DefaultInitParameters.FEE_SCALE_MULT)
                                .divide(DefaultInitParameters.FEE_SCALE_DIV)
                        );
                        break;
                    case S_CONTRACT_CALL:
                        //case CONTRACT_DEPLOY:
                    case S_CONTRACT_INSTANCE:
                        break;
                    case BLOCK_HASH:
                    case BLOCK:
                    default:
                        System.out.println("not yet implemented");
                        break;

                }
            } else {
            }
        } else {
        }
        return ret;
    }

    /**
     * This function alters the payer's balance sheet. This function must be
     * called only after verifying that the payer can afford to execute the
     * transaction. The verification is carried out through
     * {@code canPayFee == true}. If the function is called on a payer with an
     * unsustainable balance sheet, it will give rise to a negative balance
     * sheet.
     *
     * @param tbox
     * @param payer
     * @return the reward due to the node for the execution of the current
     * transaction
     */
    public static RewardBean getPayBeanAndApplyToBalanceThePay(TransactionBox tbox, BalanceBean payer) {
        FeeBean feeBean = getFeeBean(tbox);
        BigInteger totalFee = totalFee(feeBean);
        RewardBean payFee = payFee(payer, totalFee);
        return payFee;
    }

    private static RewardBean payFee(BalanceBean payer, BigInteger feeExt) {
        RewardBean rb = new RewardBean();
        rb.setGreenValue(BigInteger.ZERO);
        rb.setRedValue(BigInteger.ZERO);
        BigInteger fee = feeExt;
        //can pay red
        if (payer.getRedBalance().compareTo(fee) >= 0) {
            payer.setRedBalance(payer.getRedBalance().subtract(fee));
            rb.setRedValue(fee);

        } else {
            //partially pay red
            rb.setRedValue(payer.getRedBalance());
            fee = fee.subtract(payer.getRedBalance());
            payer.setRedBalance(BigInteger.ZERO);
            //pay green
            payer.setGreenBalance(payer.getGreenBalance().subtract(fee));
            rb.setGreenValue(fee);
        }
        if (payer.getRedBalance().compareTo(BigInteger.ZERO) < 0) {
        }
        if (payer.getGreenBalance().compareTo(BigInteger.ZERO) < 0) {
        }
        return rb;
    }

    /**
     * For transactions that do not move money but have a cost that is assessed
     * based on the structure alone. These include stake, stake_undo, all
     * register assignments and deregisters.
     *
     * @param tbox
     * @param payer
     * @return
     */
    public static boolean canPayFee(TransactionBox tbox, BalanceBean payer) {
        FeeBean feeBean = getFeeBean(tbox);
        return canPay(payer, feeBean);
    }

    private static boolean canPay(BalanceBean payer, FeeBean cost) {
        if (getRedGreenTotal(payer).compareTo(totalFee(cost)) >= 0) {
            return true;
        } else {
            return false;
        }
    }

    private static BigInteger getRedGreenTotal(BalanceBean payer) {
        return payer.getGreenBalance().add(payer.getRedBalance());
    }

    public static BigInteger totalFee(FeeBean fb) {
        return fb.getCpu().add(fb.getDisk()).add(fb.getMemory());
    }

}
