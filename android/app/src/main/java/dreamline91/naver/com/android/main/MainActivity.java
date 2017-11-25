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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.lang.reflect.Array;
import java.util.ArrayList;

import dreamline91.naver.com.android.R;
import dreamline91.naver.com.android.util.connection.Ajou;
import dreamline91.naver.com.android.util.object.User;

/**
 * Created by dream on 2017-11-15.
 */

public class MainActivity extends AppCompatActivity {

    public int[] seat={1,1,1,1,1,0,1,1};
    public int MySeatFloor=1;
    public int MySeatRoom=1;
    public int MySeatNum=1;
    public int hour=16;
    public int minute=20;
    public boolean isSeated=false;
    public boolean isBooked=false;
    public Button book;
    private ToggleButton b[];
    public AlertDialog.Builder builder;
    public boolean sw=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initProfile();
        initSpinner();
        initButton();
    }

    private void initProfile() {
        String cookie = getIntent().getStringExtra("Cookie");
        Ajou ajou = new Ajou();
        User user =  ajou.printUser(cookie);

        ImageView imagePicture = (ImageView) findViewById(R.id.StuImg);
        TextView textName = (TextView) findViewById(R.id.StuName);
        TextView textNumber = (TextView) findViewById(R.id.StuNum);
        TextView textMajor = (TextView) findViewById(R.id.StuMajor);

        if(user.getNumber()!=0) {
            imagePicture.setImageBitmap(ajou.printPicture(user.getNumber()));
            textName.setText(user.getName());
            textMajor.setText(user.getMajor());
            textNumber.setText(user.getNumber()+"");
        }
    }

    private void initSpinner() {
        Spinner s1=(Spinner) findViewById(R.id.floor);
        ArrayAdapter adapter1= ArrayAdapter.createFromResource(this,R.array.floor,android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(adapter1);
        Spinner s2=(Spinner) findViewById(R.id.room);
        ArrayAdapter adapter2= ArrayAdapter.createFromResource(this,R.array.room,android.R.layout.simple_spinner_dropdown_item);
        s2.setAdapter(adapter2);
    }

    private void initButton() {
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
        book=(Button) findViewById(R.id.book);
        if(seat[0]==1)
        {
            b[1].setTextOn("예약됨");
            b[1].setChecked(true);
            b[1].setEnabled(false);
        }
        if(seat[1]==1)
        {
            b[2].setTextOn("예약됨");
            b[2].setChecked(true);
            b[2].setEnabled(false);
        }
        if(seat[2]==1)
        {
            b[3].setTextOn("예약됨");
            b[3].setChecked(true);
            b[3].setEnabled(false);
        }
        if(seat[3]==1)
        {
            b[4].setTextOn("예약됨");
            b[4].setChecked(true);
            b[4].setEnabled(false);
        }
        if(seat[4]==1)
        {
            b[5].setTextOn("예약됨");
            b[5].setChecked(true);
            b[5].setEnabled(false);
        }
        if(seat[5]==1)
        {
            b[6].setTextOn("예약됨");
            b[6].setChecked(true);
            b[6].setEnabled(false);
        }
        if(seat[6]==1)
        {
            b[7].setTextOn("예약됨");
            b[7].setChecked(true);
            b[7].setEnabled(false);
        }
        if(seat[7]==1)
        {
            b[8].setTextOn("예약됨");
            b[8].setChecked(true);
            b[8].setEnabled(false);
        }
    }

    public void ToggleListener(View target)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("확인")
        .setMessage("정말 선택하시겠습니까?")
            .setCancelable(false)
            .setPositiveButton("네", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    MySeatNum=6;
                    b[6].setTextColor(Color.parseColor("#ffFF6000"));
                    isSeated=true;
                    b[6].setEnabled(false);
                    Toast.makeText(getApplication(),"배정되었습니다",Toast.LENGTH_SHORT).show();
                    //라즈베리파이에 신호 보내기
                }
            })
            .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    b[6].setChecked(false);
                    dialog.cancel();
                }
            }).show();

    }
    public void bookListener(View target)
    {
        if(isSeated==true)
        {
            Toast.makeText(getApplication(),"이미 배정된 자리가 있습니다",Toast.LENGTH_SHORT).show();
        }
        else if(isBooked==false) {
            AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("확인")
                    .setMessage("정말 예약하시겠습니까?")
                    .setCancelable(false)
                    .setPositiveButton("네", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            isBooked = true;
                            Toast.makeText(getApplication(),"예약되었습니다",Toast.LENGTH_SHORT).show();
                            book.setText("예약 취소");
                            //라즈베리파이에 신호 보내기
                        }
                    })
                    .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    }).show();

        }
        else if(isBooked==true)
        {
            AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("확인")
                    .setMessage("정말 취소하시겠습니까?")
                    .setCancelable(false)
                    .setPositiveButton("네", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            isBooked=false;
                            Toast.makeText(getApplication(), "예약 취소되었습니다", Toast.LENGTH_SHORT).show();
                            book.setText("예약 신청");
                            //라즈베리파이에 신호 보내기
                        }
                    })
                    .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    }).show();
        }
    }
    public void checkListener(View target)
    {
        if(isBooked==true)
        {
            Toast.makeText(getApplication(),"예약대기 중입니다.",Toast.LENGTH_SHORT).show();
        }
        else if(isSeated==true)
        {
            String text=MySeatFloor+"층 "+MySeatRoom+"호실 "+MySeatNum+"번 자리입니다.";
            Toast.makeText(getApplication(),text,Toast.LENGTH_SHORT).show();
        }
        else if(isSeated==false)
        {
            Toast.makeText(getApplication(),"배정된 자리가 없습니다.",Toast.LENGTH_SHORT).show();
        }
    }
    public void retListener(View target)
    {
        if(isSeated==false)
        {
            Toast.makeText(getApplication(),"배정된 자리가 없습니다.",Toast.LENGTH_SHORT).show();
        }
        else if(isSeated==true)
        {
            Toast.makeText(getApplication(),"좌석 반납되었습니다.",Toast.LENGTH_SHORT).show();
            b[6].setChecked(false);
            b[6].setTextColor(Color.parseColor("#ffffff"));
            b[6].setEnabled(true);
            isSeated=false;
        }

    }
}
