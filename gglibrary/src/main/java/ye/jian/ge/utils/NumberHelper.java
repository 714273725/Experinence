package ye.jian.ge.utils;

/**
 * Created by Administrator on 2016/6/6.
 */
public class NumberHelper {
    public static String LeftPad_Tow_Zero(int str) {
        java.text.DecimalFormat format = new java.text.DecimalFormat("00");
        return format.format(str);
    }
}
