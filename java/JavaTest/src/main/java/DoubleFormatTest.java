import com.sun.tools.javac.util.Convert;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;

public class DoubleFormatTest {
    public static void main(String[] args){
        NumberFormat nf = NumberFormat.getInstance();
        ParsePosition position = new ParsePosition(0);
        //String number1 = "49541274121797925";
        String number1 = "49541274121797925";

        String number2 = "71903267885434990";
        String number3 = "58978081259520293";
        String number4 = "1,234";
        Number num = nf.parse(number1, position);
        System.out.println(num.doubleValue());
        System.out.println(num.intValue());
        System.out.println(num.longValue());



        /*DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
        df.setParseBigDecimal(true);
        BigDecimal bd = null;
        try {
            bd = (BigDecimal) df.parseObject(number1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(bd.toString());

        System.out.println(bd.doubleValue());
        System.out.println(bd.longValue());

        Number aaaa = df.parse(number1,position);
        System.out.println(aaaa.doubleValue());*/
    }

}
