package dreamline91.naver.com.android.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;
import android.widget.TextView;

import dreamline91.naver.com.android.R;

/**
 * Created by dream on 2017-12-11.
 */

public class ControlActivity extends AppCompatActivity {
    private int window;
    private int bright;
    public TextView Win;
    public TextView Br;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        Win=(TextView)findViewById(R.id.win);
        Br=(TextView)findViewById(R.id.br);
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
                window=seekBar.getProgress();
                Win.setText("창문 열기 : "+window+"%");
                //아두이노에 창문 값 전송
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
                Br.setText("밝기 조절 : "+bright+"%");
                //아두이노에 밝기 값 전송
            }
        });
    }
}

