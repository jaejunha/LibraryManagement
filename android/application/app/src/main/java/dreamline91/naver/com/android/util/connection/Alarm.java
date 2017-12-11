package dreamline91.naver.com.android.util.connection;

import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import dreamline91.naver.com.android.R;
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
        init("A0");
        postSerial("b");
        while(checkSeat("A0") == false){
            try {
                if(cancel == true){
                    postSerial("g");
                    init("A0");
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
        mainActivity.setIsBooked(false);
        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(mainActivity)
                .setContentText("자리가 배정되었습니다")
                .setTicker("자리 배정")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(mainActivity.getResources(), R.mipmap.ic_launcher))
                .setContentTitle("알림").setAutoCancel(true);
        NotificationManager nManager = (NotificationManager) mainActivity.getSystemService(Context.NOTIFICATION_SERVICE);
        nManager.notify(1,nBuilder.build());

        new Thread(new Runnable() {
            @Override
            public void run() {
                mainActivity.runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        Toast.makeText(mainActivity,"좌석 배정되었습니다.",Toast.LENGTH_SHORT).show();
                        mainActivity.setButtonBook("1층 1호실 1번 사용 중");
                    }
                });
            }
        }).start();

        while(checkSeat("A0") == true){
            try {
                if(cancel == true){
                    postSerial("g");
                    mainActivity.setIsSeated(false);
                    mainActivity.setSeat(0,0,0);
                    init("A0");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            mainActivity.runOnUiThread(new Runnable(){
                                @Override
                                public void run() {
                                    mainActivity.refreshToggleButton();
                                    mainActivity.setButtonBook("예약 신청");
                                }
                            });
                        }
                    }).start();
                    return null;
                }
                mainActivity.setTimer(getRemain("A0"));
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

    private void init(String seat) {
        try {
            BufferedReader rd = getSimple(new URL(SERVER + "/init?seat="+seat));
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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

    public int getRemain(String seat) {
        int timer = 0;
        try {
            String line = null;
            BufferedReader rd = getSimple(new URL(SERVER + "/getTimer?seat="+seat));
            while ((line = rd.readLine()) != null) {
                timer = Integer.parseInt(line);
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return timer;
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
