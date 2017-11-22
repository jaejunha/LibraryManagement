package dreamline91.naver.com.android.main;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.lang.reflect.Array;
import java.util.ArrayList;

import dreamline91.naver.com.android.R;

/**
 * Created by dream on 2017-11-15.
 */

public class MainActivity extends AppCompatActivity {

    public int[] Seat={1,1,1,1,1,0,1,1};
    public int MySeatFloor=1;
    public int MySeatRoom=1;
    public int MySeatNum=1;
    public int hour=16;
    public int minute=20;
    public boolean isSeated=false;
    public boolean isBooked=false;
    public Button book=(Button) findViewById(R.id.book);
    private ToggleButton b[];
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

        b=new ToggleButton[9];
        b[1]=(ToggleButton) findViewById(R.id.s1);
        b[2]=(ToggleButton) findViewById(R.id.s2);
        b[3]=(ToggleButton) findViewById(R.id.s3);
        b[4]=(ToggleButton) findViewById(R.id.s4);
        b[5]=(ToggleButton) findViewById(R.id.s5);
        b[6]=(ToggleButton) findViewById(R.id.s6);
        b[7]=(ToggleButton) findViewById(R.id.s7);
        b[8]=(ToggleButton) findViewById(R.id.s8);
        Button check=(Button) findViewById(R.id.check);
        if(Seat[0]==1)
        {
            b[1].setTextOn("예약됨");
            b[1].setChecked(true);
            b[1].setEnabled(false);
        }
        if(Seat[1]==1)
        {
            b[2].setTextOn("예약됨");
            b[2].setChecked(true);
            b[2].setEnabled(false);
        }
        if(Seat[2]==1)
        {
            b[3].setTextOn("예약됨");
            b[3].setChecked(true);
            b[3].setEnabled(false);
        }
        if(Seat[3]==1)
        {
            b[4].setTextOn("예약됨");
            b[4].setChecked(true);
            b[4].setEnabled(false);
        }
        if(Seat[4]==1)
        {
            b[5].setTextOn("예약됨");
            b[5].setChecked(true);
            b[5].setEnabled(false);
        }
        if(Seat[5]==1)
        {
            b[6].setTextOn("예약됨");
            b[6].setChecked(true);
            b[6].setEnabled(false);
        }
        if(Seat[6]==1)
        {
            b[7].setTextOn("예약됨");
            b[7].setChecked(true);
            b[7].setEnabled(false);
        }
        if(Seat[7]==1)
        {
            b[8].setTextOn("예약됨");
            b[8].setChecked(true);
            b[8].setEnabled(false);
        }
    }
    public void ToggleListener(View target)
    {
        //Alert 알람 넣기
        MySeatNum=6;
        b[6].setTextColor(Color.parseColor("#ffFF6000"));
        b[6].setEnabled(false);
        isSeated=true;
        Toast.makeText(getApplication(),"배정되었습니다",Toast.LENGTH_LONG).show();
        //라즈베리파이에 신호 보내기
    }
    public void bookListener(View target)
    {
        if(isBooked==false) {
            //Alert 알람 넣기
            isBooked = true;
            Toast.makeText(getApplication(), "예약되었습니다", Toast.LENGTH_LONG).show();
            book.setText("예약 취소");
            //라즈베리파이에 신호 보내기
        }
        else if(isBooked==true)
        {
            //Alert 알람 넣기
            isBooked=false;
            Toast.makeText(getApplication(), "예약 취소되었습니다", Toast.LENGTH_LONG).show();
            book.setText("예약 신청");
            //라즈베리파이에 신호 보내기
        }
    }
    public void checkListener(View target)
    {
        if(isBooked==true)
        {
            Toast.makeText(getApplication(),"예약대기 중입니다.",Toast.LENGTH_LONG).show();
        }
        else if(isSeated==true)
        {
            String text=MySeatFloor+"층 "+MySeatRoom+"호실 "+MySeatNum+"번 자리입니다.";
            Toast.makeText(getApplication(),text,Toast.LENGTH_LONG).show();
        }
    }
    public void retListener(View target)
    {
        if(isSeated==false)
        {
            Toast.makeText(getApplication(),"배정된 자리가 없습니다.",Toast.LENGTH_LONG).show();
        }
        else if(isSeated==true)
        {
            Toast.makeText(getApplication(),"좌석 반납되었습니다.",Toast.LENGTH_LONG).show();
            isSeated=false;
        }

    }
}
