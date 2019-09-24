

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class JustTest {
    public static String ReadByGet(String _url) throws Exception {
        // URL url = new
        // URL("L("https://fmarttestoa.gaiaworkforce.com/api/common/accesstoken?appId=FmartYonghui&secret=FmartYonghui2018&companyCode=FmartYongHui");

        URL url = new URL(_url);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                connection.getInputStream(), "UTF-8"));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = reader.readLine())!= null) {
            sb.append(line);
        }

        connection.disconnect();


        System.out.print(sb.toString());
        return sb.toString();
    }
    public static void main(String[] args) throws Exception {
        ReadByGet("https://fmarttestoa.gaiaworkforce.com/api/common/accesstoken?appId=FmartYonghui&secret=FmartYonghui2018&companyCode=FmartYongHui");
    }
}
