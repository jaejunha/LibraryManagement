package dreamline91.naver.com.android.util.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by dream on 2017-12-12.
 */

public class Control {

    private final static String SERVER = "http://192.168.1.10:8888";

    public void postMoter(int value) {
        int moter = value;
        if(value>180)
            moter = 180;
        postSerial("m:"+moter);
    }

    public void postLED(int value) {
        int led = value;
        if(value>255)
            led = 255;
        postSerial("l:"+led);
    }

    private void postSerial(String command) {
        try {
            BufferedReader rd = getSimple(new URL(SERVER + "/postSerial?con="+command));
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public ArrayList<String> getLog() {
        ArrayList<String> logs = new ArrayList<>();
        try {
            String line = "";
            String contents="";
            BufferedReader rd = getSimple(new URL(SERVER + "/getLog"));
            while ((line = rd.readLine()) != null){
                contents+=line;
            }
            if(contents!=null) {
                for (String c : contents.split("/")) {
                    logs.add(c);
                }
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return logs;
    }

    public void setLog(int number, int angle, boolean open) {
        try {
            String con ="";
            Calendar calendar = Calendar.getInstance();
            con+=calendar.get(Calendar.YEAR)+".";
            con+=(calendar.get(Calendar.MONTH)+1)+".";
            con+=calendar.get(Calendar.DATE)+" ";
            con+=calendar.get(Calendar.HOUR_OF_DAY)+":";
            con+=calendar.get(Calendar.MINUTE)+" ";
            if(open == true)
                con+="window open ";
            else
                con+="window close ";
            con+=angle+" degree";

            BufferedReader rd = getSimple(new URL(SERVER + "/setLog?number="+number+"&con="+con));
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private BufferedReader getSimple(URL url) {
        try {
            HttpURLConnection con = HTTP.makeConnection(url);
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
