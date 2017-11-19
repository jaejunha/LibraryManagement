package dreamline91.naver.com.android.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ToggleButton;

import java.lang.reflect.Array;
import java.util.ArrayList;

import dreamline91.naver.com.android.R;

/**
 * Created by dream on 2017-11-15.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Spinner s1=(Spinner) findViewById(R.id.floor);
        ArrayAdapter adapter1= ArrayAdapter.createFromResource(this,R.array.floor,android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(adapter1);
        Spinner s2=(Spinner) findViewById(R.id.room);
        ArrayAdapter adapter2= ArrayAdapter.createFromResource(this,R.array.room,android.R.layout.simple_spinner_dropdown_item);
        s2.setAdapter(adapter2);

        int[] Seat={1,0,0,1,1,0,0,1};
        int MySeatFloor=1;
        int MySeatRoom=1;
        int MySeatNum=1;
        int hour=16;
        int minute=20;
        boolean isBooked=false;
        ToggleButton b1=(ToggleButton) findViewById(R.id.s1);
        ToggleButton b2=(ToggleButton) findViewById(R.id.s2);
        ToggleButton b3=(ToggleButton) findViewById(R.id.s3);
        ToggleButton b4=(ToggleButton) findViewById(R.id.s4);
        ToggleButton b5=(ToggleButton) findViewById(R.id.s5);
        ToggleButton b6=(ToggleButton) findViewById(R.id.s6);
        ToggleButton b7=(ToggleButton) findViewById(R.id.s7);
        ToggleButton b8=(ToggleButton) findViewById(R.id.s8);
        Button check=(Button) findViewById(R.id.check);
        if(Seat[0]==1)
        {
            b1.setTextOn("예약됨");
            b1.setChecked(true);
            b1.setEnabled(false);
        }
        if(Seat[1]==1)
        {
            b2.setTextOn("예약됨");
            b2.setChecked(true);
            b2.setEnabled(false);
        }
        if(Seat[2]==1)
        {
            b3.setTextOn("예약됨");
            b3.setChecked(true);
            b3.setEnabled(false);
        }
        if(Seat[3]==1)
        {
            b4.setTextOn("예약됨");
            b4.setChecked(true);
            b4.setEnabled(false);
        }
        if(Seat[4]==1)
        {
            b5.setTextOn("예약됨");
            b5.setChecked(true);
            b5.setEnabled(false);
        }
        if(Seat[5]==1)
        {
            b6.setTextOn("예약됨");
            b6.setChecked(true);
            b6.setEnabled(false);
        }
        if(Seat[6]==1)
        {
            b7.setTextOn("예약됨");
            b7.setChecked(true);
            b7.setEnabled(false);
        }
        if(Seat[7]==1)
        {
            b8.setTextOn("예약됨");
            b8.setChecked(true);
            b8.setEnabled(false);
        }
    }
}
