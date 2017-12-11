package dreamline91.naver.com.android.util.connection;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import dreamline91.naver.com.android.main.MainActivity;

/**
 * Created by dream on 2017-11-29.
 */

public class Alarm extends AsyncTask {

    private final static String SERVER = "http://192.168.1.10:8888";
    private MainActivity mainActivity;

    private boolean cancel;

    public Alarm(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        postSerial("b");
        while(checkSeat("A0") == false){
            try {
                if(cancel == true){
                    postSerial("g");
                    return null;
                }
                Thread.sleep(1000);
                Log.d("wait","wait");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        postSerial("r");
        mainActivity.setIsSeated(true);

        while(checkSeat("A0") == true){
            try {
                if(cancel == true){
                    postSerial("g");
                    mainActivity.setIsSeated(false);
                    mainActivity.setSeat(0,0,0);
                    mainActivity.refreshToggleButton();
                    return null;
                }
                Thread.sleep(1000);
                Log.d("wait","wait");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        return null;
    }

    public void setCancel(boolean cancel){
        this.cancel = cancel;
    }

    public void postSerial(String command) {
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

    public boolean checkSeat(String seat) {
        try {
            String line = null;
            BufferedReader rd = getSimple(new URL(SERVER + "/checkSeat?seat="+seat));
            while ((line = rd.readLine()) != null) {
                if(Integer.parseInt(line) == 1)
                    return true;
                else
                    return false;
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
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
