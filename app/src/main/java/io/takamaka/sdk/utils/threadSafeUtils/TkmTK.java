package io.takamaka.sdk.utils.threadSafeUtils;

import io.takamaka.sdk.globalContext.KeyContexts;
import io.takamaka.sdk.main.defaults.DefaultInitParameters;
import io.takamaka.sdk.transactions.fee.FeeBean;
import java.math.BigInteger;
import org.apache.commons.lang3.StringUtils;

/**
 * Tkm Token Utils. <br> all the values entered in the transactions are
 * expressed in nTK
 *
 * <table>
 * <tbody>
 * <tr style="height: 23px;">
 * <td style="height: 23px;">prefix&nbsp;</td>
 * <td style="height: 23px;">key&nbsp;</td>
 * <td style="height: 23px;">Name</td>
 * <td style="height: 23px;">scale&nbsp;</td>
 * <td style="height: 23px;">generic&nbsp;</td>
 * <td style="height: 23px;">green&nbsp;</td>
 * <td style="height: 23px;">red&nbsp;</td>
 * </tr>
 * <tr style="height: 23px;">
 * <td style="height: 23px;">G&nbsp;</td>
 * <td style="height: 23px;">G&nbsp;</td>
 * <td style="height: 23px;">giga</td>
 * <td style="height: 23px;">10^9&nbsp;</td>
 * <td style="height: 23px;">&nbsp;</td>
 * <td style="height: 23px;">TKG&nbsp;&nbsp;</td>
 * <td style="height: 23px;">TKR&nbsp;</td>
 * </tr>
 * <tr style="height: 23px;">
 * <td style="height: 23px;">M&nbsp;</td>
 * <td style="height: 23px;">M&nbsp;</td>
 * <td style="height: 23px;">mega</td>
 * <td style="height: 23px;">10^6&nbsp;</td>
 * <td style="height: 23px;">&nbsp;</td>
 * <td style="height: 23px;">TKG&nbsp;&nbsp;</td>
 * <td style="height: 23px;">TKR&nbsp;</td>
 * </tr>
 * <tr style="height: 23px;">
 * <td style="height: 23px;">h&nbsp;</td>
 * <td style="height: 23px;">k&nbsp;</td>
 * <td style="height: 23px;">kilo</td>
 * <td style="height: 23px;">10^3&nbsp;</td>
 * <td style="height: 23px;">&nbsp;</td>
 * <td style="height: 23px;">TKG&nbsp;&nbsp;</td>
 * <td style="height: 23px;">TKR&nbsp;</td>
 * </tr>
 * <tr style="height: 23px;">
 * <td style="height: 23px;">h&nbsp;</td>
 * <td style="height: 23px;">h&nbsp;</td>
 * <td style="height: 23px;">hecto</td>
 * <td style="height: 23px;">10^2&nbsp;</td>
 * <td style="height: 23px;">&nbsp;</td>
 * <td style="height: 23px;">TKG&nbsp;&nbsp;</td>
 * <td style="height: 23px;">TKR&nbsp;</td>
 * </tr>
 * <tr style="height: 23px;">
 * <td style="height: 23px;">da&nbsp;</td>
 * <td style="height: 23px;">da&nbsp;</td>
 * <td style="height: 23px;">deca</td>
 * <td style="height: 23px;">10^1&nbsp;</td>
 * <td style="height: 23px;">&nbsp;</td>
 * <td style="height: 23px;">TKG&nbsp;&nbsp;</td>
 * <td style="height: 23px;">TKR&nbsp;</td>
 * </tr>
 * <tr style="height: 23px;">
 * <td style="height: 23px;">&nbsp;</td>
 * <td style="height: 23px;">&nbsp;</td>
 * <td style="height: 23px;">&nbsp;</td>
 * <td style="height: 23px;">1&nbsp;</td>
 * <td style="height: 23px;">TK&nbsp;</td>
 * <td style="height: 23px;">TKG&nbsp;</td>
 * <td style="height: 23px;">TKR&nbsp;</td>
 * </tr>
 * <tr style="height: 23px;">
 * <td style="height: 23px;">d&nbsp;</td>
 * <td style="height: 23px;">d&nbsp;</td>
 * <td style="height: 23px;">deci</td>
 * <td style="height: 23px;">10^-1&nbsp;</td>
 * <td style="height: 23px;">&nbsp;</td>
 * <td style="height: 23px;">TKG&nbsp;&nbsp;</td>
 * <td style="height: 23px;">TKR</td>
 * </tr>
 * <tr style="height: 23px;">
 * <td style="height: 23px;">c&nbsp;</td>
 * <td style="height: 23px;">c&nbsp;</td>
 * <td style="height: 23px;">centi</td>
 * <td style="height: 23px;">10^-2&nbsp;</td>
 * <td style="height: 23px;">&nbsp;</td>
 * <td style="height: 23px;">TKG&nbsp;</td>
 * <td style="height: 23px;">TKR</td>
 * </tr>
 * <tr style="height: 23px;">
 * <td style="height: 23px;">m&nbsp;</td>
 * <td style="height: 23px;">m&nbsp;</td>
 * <td style="height: 23px;">milli</td>
 * <td style="height: 23px;">10^-3&nbsp;</td>
 * <td style="height: 23px;">&nbsp;</td>
 * <td style="height: 23px;">TKG&nbsp;</td>
 * <td style="height: 23px;">TKR</td>
 * </tr>
 * <tr style="height: 23px;">
 * <td style="height: 23px;">&micro;</td>
 * <td style="height: 23px;">ALTGR+m&nbsp;</td>
 * <td style="height: 23px;">micro</td>
 * <td style="height: 23px;">10^-6&nbsp;</td>
 * <td style="height: 23px;">&micro;TK&nbsp;</td>
 * <td style="height: 23px;">TKG&nbsp;</td>
 * <td style="height: 23px;">TKR</td>
 * </tr>
 * <tr style="height: 23px;">
 * <td style="height: 23px;">n&nbsp;</td>
 * <td style="height: 23px;">n</td>
 * <td style="height: 23px;">nano</td>
 * <td style="height: 23px;">10^-9&nbsp;</td>
 * <td style="height: 23px;">nTK&nbsp;</td>
 * <td style="height: 23px;">TKG&nbsp;</td>
 * <td style="height: 23px;">TKR</td>
 * </tr>
 * </tbody>
 * </table>
 *
 * @author Giovanni Antino
 */
public class TkmTK {

    public static BigInteger unitTK(int unit) {
        return new BigInteger("" + unit).multiply(KeyContexts.NUMBER_OF_ZEROS_SHIFT_DECIMAL);
    }

    public static BigInteger unitTK(BigInteger unit) {
        return unit.multiply(KeyContexts.NUMBER_OF_ZEROS_SHIFT_DECIMAL);
    }

    public static BigInteger unitTK(String unit) {
        return new BigInteger(unit).multiply(KeyContexts.NUMBER_OF_ZEROS_SHIFT_DECIMAL);
    }

    /**
     *
     * @param unit
     * @return
     */
    public static BigInteger deciTK(int unit) {
        return new BigInteger("" + unit).multiply(KeyContexts.NUMBER_OF_ZEROS_SHIFT_DECI);
    }

    public static BigInteger deciTK(BigInteger unit) {
        return unit.multiply(KeyContexts.NUMBER_OF_ZEROS_SHIFT_DECI);
    }

    public static BigInteger deciTK(String unit) {
        return new BigInteger(unit).multiply(KeyContexts.NUMBER_OF_ZEROS_SHIFT_DECI);
    }

    public static String getFeeViewTK(FeeBean fb) {
        if (fb != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("[ ")
                    .append(tkView(fb.getCpu())).append(" | ")
                    .append(tkView(fb.getMemory())).append(" | ")
                    .append(tkView(fb.getDisk())).append(" ]");
            return sb.toString();
        } else {
            return null;
        }
        //return "";
    }

    private static String tkView(BigInteger val) {
        BigInteger toUnit = BigInteger.TEN.pow(DefaultInitParameters.NUMBER_OF_ZEROS);
        if (val == null) {
            return null;
        } else {
            BigInteger[] integerAndDecimal = val.divideAndRemainder(toUnit);
            //BigInteger decimalPart = val.d(toUnit);
            //String reminder = StringUtils.leftPad(integerAndDecimal[1].toString(), DefaultInitParameters.NUMBER_OF_ZEROS);
            String reminder = String.format("%09d", integerAndDecimal[1]);
            return integerAndDecimal[0].toString() + "," + reminder;
        }
    }

}
