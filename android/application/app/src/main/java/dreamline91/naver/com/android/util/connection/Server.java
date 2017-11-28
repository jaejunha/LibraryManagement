package dreamline91.naver.com.android.util.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by dream on 2017-11-29.
 */

public class Server {

    public String httpServer(String mode, String url, String contents) {
        BufferedReader response;
        try {
            if(mode.equals("GET") || mode.equals("get"))
                response = getServer(url,contents);
            else if(mode.equals("POST")||mode.equals("post"))
                postServer(new URL(url),contents);

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        return null;
    }

    private void postServer(URL url, String contents) {
        try {
            HttpURLConnection con = HTTP.makeConnection(url);
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(contents);
            wr.flush();

            con.disconnect();
        } catch (IOException | IndexOutOfBoundsException e) {
            // TODO Auto-generated catch block
        }
    }

    private BufferedReader getServer(String url,String contents) {
        try {
            HttpURLConnection con = HTTP.makeConnection(new URL(url+contents));
            con.setDoOutput(false);
            con.setRequestMethod("GET");

            return new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
