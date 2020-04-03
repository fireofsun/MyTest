import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Test {
    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String c_password="key=18E258097972405438CDF1602FE15628&mallid=0001&projectid=test1&timestamp=1552551752&secret=6D569926D6F29891EDCADE91D146709D";



        String query="id=1&key=${key}&limit=1&mallid=${mallid}&projectid=${project}&timestamp=${timestamp}&vip_registration_time_begin=2018-12-05&vip_registration_time_end=2018-12-05&secret=${secret}";
        String urlString = URLEncoder.encode(query, "utf-8");
        /*
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(c_password.getBytes());
        String fuck =  new BigInteger(1, md.digest()).toString(16);*/

        System.out.println(urlString);

    }
}
