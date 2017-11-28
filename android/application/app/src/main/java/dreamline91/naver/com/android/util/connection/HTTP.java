package dreamline91.naver.com.android.util.connection;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by dream on 2017-11-29.
 */

public class HTTP {
    public static HttpURLConnection makeConnection(URL url) {
        HttpURLConnection con;
        try {
            con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("Accept-Language", "ko-kr,ko;q=0.8,en-us;q=0.5,en;q=0.3");
            con.setRequestProperty("Accept-Upgrade-Insecure-Requests", "1");
            return con;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
