package dreamline91.naver.com.android.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Calendar;

import dreamline91.naver.com.android.R;
import dreamline91.naver.com.android.util.connection.Control;

/**
 * Created by dream on 2017-12-11.
 */

public class ControlActivity extends AppCompatActivity {
    private int studentNumber;
    private int window;
    private int bright;
    private TextView Win;
    private TextView Br;
    private TextView textLog;

    private String contents="";

    private Control control;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        studentNumber = super.getIntent().getIntExtra("number",0);
        control = new Control();
        Win=(TextView)findViewById(R.id.win);
        Br=(TextView)findViewById(R.id.br);
        textLog = (TextView)findViewById(R.id.textLog);
        for(String log : control.getLog()){
            contents+=log+"\n";
        }
        textLog.setText(contents);
        SeekBar WinSeekBar=(SeekBar)findViewById(R.id.winSeek);
        SeekBar BrightSeekBar=(SeekBar)findViewById(R.id.brSeek);
        //아두이노에서 현재 창문 열린정도, 밝기를 받아와서 window,bright에 initiate
        WinSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int previousWindow = window;
                boolean open;
                window=seekBar.getProgress();
                if(window>previousWindow)
                    open = true;
                else
                    open = false;
                Win.setText("창문 열기 : "+window+"% 개방");
                control.postMoter((int)(window*1.8));
                control.setLog(studentNumber,(int)(window*1.8),open);

                Calendar calendar = Calendar.getInstance();
                contents+=studentNumber+": ";
                contents+=calendar.get(Calendar.YEAR)+".";
                contents+=(calendar.get(Calendar.MONTH)+1)+".";
                contents+=calendar.get(Calendar.DATE)+" ";
                contents+=calendar.get(Calendar.HOUR_OF_DAY)+":";
                contents+=calendar.get(Calendar.MINUTE)+" ";
                if(open == true)
                    contents+="window open ";
                else
                    contents+="window close ";
                contents+=(int)(window*1.8)+" degree\n";
                textLog.setText(contents);
            }
        });
        BrightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                bright=seekBar.getProgress();
                Br.setText("밝기 조절 : "+bright+"% 밝기");
                control.postLED((int)(window*2.55));
            }
        });
    }
}

