package dreamline91.naver.com.android.main;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
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

import dreamline91.naver.com.android.R;
import dreamline91.naver.com.android.util.connection.Ajou;
import dreamline91.naver.com.android.util.connection.Alarm;
import dreamline91.naver.com.android.util.object.User;

/**
 * Created by dream on 2017-11-15.
 */

public class MainActivity extends AppCompatActivity {

    private int[][] seat={{0,1,1,1,1,0,1,1,0,1,1,1},{1,0,0,1,1,1,1,1,1,1,1,0},{0,1,1,1,1,1,1,1,1,1,0,1},
            {0,1,0,1,0,1,1,0,0,1,1,0},{0,1,1,1,1,0,1,1,0,1,1,1},{1,0,0,1,1,1,1,1,1,1,1,0},
            {0,1,1,1,1,1,1,1,1,1,0,1},{0,1,1,1,1,0,1,1,0,1,1,1},{1,0,0,1,1,1,1,1,1,1,1,0}};
    private int[][] queue={{0,1,2,1,3,0,1,1,0,1,1,2},{1,0,0,2,1,1,2,1,1,2,1,0},{0,1,2,1,1,1,2,1,3,1,0,2},
            {0,1,0,1,0,2,1,0,0,1,2,0},{0,1,1,2,1,0,1,1,0,1,2,1},{1,0,0,2,1,2,1,3,1,4,1,0},
            {0,1,1,2,1,2,1,1,1,2,0,1},{0,1,1,2,1,0,1,1,0,1,2,1},{1,0,0,1,1,1,2,1,2,1,1,0}};
    private int MySeatFloor=0;
    private int MySeatRoom=0;
    private int MySeatNum=-1;
    private int select = -1;
    private boolean isSeated=false;
    private boolean isBooked=false;
    private Button buttonBook;
    private ToggleButton toggleButtons[];
    private Spinner spinnerFloor, spinnerRoom;

    private int studentNumber;

    private int timer;

    private Alarm alarm;

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
        final Ajou ajou = new Ajou();
        final User user =  ajou.printUser(cookie);

        final ImageView imagePicture = (ImageView) findViewById(R.id.StuImg);
        final TextView textName = (TextView) findViewById(R.id.StuName);
        final TextView textNumber = (TextView) findViewById(R.id.StuNum);
        final TextView textMajor = (TextView) findViewById(R.id.StuMajor);

        imagePicture.post(new Runnable() {
            @Override
            public void run() {
                int image_height = imagePicture.getHeight();
                imagePicture.getLayoutParams().height = image_height;
                if(user.getNumber()!=0) {
                    imagePicture.setImageBitmap(ajou.printPicture(user.getNumber(),image_height));
                    textName.setText(user.getName());
                    textMajor.setText(user.getMajor());
                    textNumber.setText(user.getNumber()+"");
                    studentNumber = user.getNumber();
                }
            }
        });

    }

    private void initSpinner() {
        spinnerFloor=(Spinner) findViewById(R.id.floor);
        ArrayAdapter adapterFloor= ArrayAdapter.createFromResource(this,R.array.floor,android.R.layout.simple_spinner_dropdown_item);
        spinnerFloor.setAdapter(adapterFloor);
        spinnerFloor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                MySeatFloor = i;
                MySeatRoom = 0;
                spinnerRoom.setSelection(0);
                refreshToggleButton();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerRoom=(Spinner) findViewById(R.id.room);
        ArrayAdapter adapterRoom= ArrayAdapter.createFromResource(this,R.array.room,android.R.layout.simple_spinner_dropdown_item);
        spinnerRoom.setAdapter(adapterRoom);
        spinnerRoom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                MySeatRoom = i;
                refreshToggleButton();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initButton() {
        toggleButtons=new ToggleButton[12];
        setToggleButtonID();
        setToggleButtonListener();

        Button buttonCheck=(Button) findViewById(R.id.check);
        buttonBook=(Button) findViewById(R.id.book);
        refreshToggleButton();
    }

    public void refreshToggleButton() {
        int index  = MySeatFloor*3+MySeatRoom;
        select = -1;
        for(int i=0;i<12;i++){
            toggleButtons[i].setTextOff((i+1)+"번");
            toggleButtons[i].setChecked(false);
            if(seat[index][i] == 1)
                toggleButtons[i].setBackgroundResource(R.drawable.toggle_use);
            else
                toggleButtons[i].setBackgroundResource(R.drawable.toggle_normal);
        }
    }

    private void setToggleButtonListener() {
        for(int i=0;i<12;i++){
            final int finalI = i;
            toggleButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (toggleButtons[finalI].getText().toString().equals("선택")) {
                        if (select != -1)
                            toggleButtons[select].setChecked(false);
                        select = Integer.parseInt(toggleButtons[finalI].getTextOff().toString().split("번")[0]) - 1;
                    }else
                        select = -1;
                }
            });
        }
    }

    private void setToggleButtonID() {
        toggleButtons[0]=(ToggleButton) findViewById(R.id.s1);
        toggleButtons[1]=(ToggleButton) findViewById(R.id.s2);
        toggleButtons[2]=(ToggleButton) findViewById(R.id.s3);
        toggleButtons[3]=(ToggleButton) findViewById(R.id.s4);
        toggleButtons[4]=(ToggleButton) findViewById(R.id.s5);
        toggleButtons[5]=(ToggleButton) findViewById(R.id.s6);
        toggleButtons[6]=(ToggleButton) findViewById(R.id.s7);
        toggleButtons[7]=(ToggleButton) findViewById(R.id.s8);
        toggleButtons[8]=(ToggleButton) findViewById(R.id.s9);
        toggleButtons[9]=(ToggleButton) findViewById(R.id.s10);
        toggleButtons[10]=(ToggleButton) findViewById(R.id.s11);
        toggleButtons[11]=(ToggleButton) findViewById(R.id.s12);
    }

    public void ToggleListener(View target)
    {
        /*
        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("확인")
        .setMessage("정말 선택하시겠습니까?")
            .setCancelable(false)
            .setPositiveButton("네", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    MySeatNum=6;
                    toggleButtons[6].setTextColor(Color.parseColor("#ffFF6000"));
                    isSeated=true;
                    toggleButtons[6].setEnabled(false);
                    Toast.makeText(getApplication(),"배정되었습니다",Toast.LENGTH_SHORT).show();
                    //라즈베리파이에 신호 보내기
                }
            })
            .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    toggleButtons[6].setChecked(false);
                    dialog.cancel();
                }
            }).show();*/
    }
    public void bookListener(View target)
    {
        if(isSeated==true)
        {
            Toast.makeText(getApplication(),"이미 배정된 자리가 있습니다",Toast.LENGTH_SHORT).show();
        }
        else if(isBooked==false) {
            if(select !=-1) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("확인")
                        .setMessage("정말 예약하시겠습니까?")
                        .setCancelable(false)
                        .setPositiveButton("네", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                isBooked = true;
                                if(seat[MySeatFloor*3+MySeatRoom][select] == 1){
                                    Toast.makeText(getApplication(),"대기 순번: "+queue[MySeatFloor*3+MySeatRoom][select],Toast.LENGTH_SHORT).show();
                                    MySeatNum = select;
                                    toggleButtons[select].setChecked(false);
                                    select = -1;
                                }else{
                                    seat[MySeatFloor*3+MySeatRoom][select] = 1;
                                    MySeatNum = select;
                                    toggleButtons[select].setChecked(false);
                                    select = -1;
                                    refreshToggleButton();
                                }
                                Toast.makeText(getApplication(), "예약되었습니다", Toast.LENGTH_SHORT).show();
                                buttonBook.setText("예약 취소");

                                if(MySeatNum == 0 & MySeatRoom == 0 & MySeatFloor ==0)
                                    alarm = (Alarm) new Alarm(MainActivity.this).execute("");
                            }
                        })
                        .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }).show();
            }else
                Toast.makeText(getApplication(),"좌석을 선택해주세요",Toast.LENGTH_SHORT).show();
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
                            if(queue[MySeatFloor*3+MySeatRoom][MySeatNum] == 0)
                                seat[MySeatFloor*3+MySeatRoom][MySeatNum] = 0;
                            MySeatNum = -1;
                            if(select!=-1) {
                                toggleButtons[select].setChecked(false);
                                select = -1;
                            }
                            refreshToggleButton();
                            Toast.makeText(getApplication(), "예약 취소되었습니다", Toast.LENGTH_SHORT).show();
                            buttonBook.setText("예약 신청");

                            if(alarm!=null){
                                alarm.setCancel(true);
                                alarm = null;
                            }
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
            String text="잔여시간 "+timer/60+"분 "+timer%60+"초 남았습니다";
            Toast.makeText(getApplication(),text,Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplication(),"예약을 먼저 해주세요.",Toast.LENGTH_SHORT).show();
        }
    }
    public void controlListener(View target) {
        startActivity(new Intent(this, ControlActivity.class).putExtra("number",studentNumber));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
    public void retListener(View target)
    {
        if(isSeated==false)
        {
            Toast.makeText(getApplication(),"배정된 자리가 없습니다.",Toast.LENGTH_SHORT).show();
        }
        else if(isSeated==true)
        {

            NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(this)
                    .setContentText("자리가 반납되었습니다")
                    .setTicker("자리 반납")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    .setContentTitle("알림").setAutoCancel(true);
            NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            nManager.notify(1,nBuilder.build());
            Toast.makeText(getApplication(),"좌석 반납되었습니다.",Toast.LENGTH_SHORT).show();
            if(queue[MySeatFloor*3+MySeatRoom][MySeatNum] == 0)
                seat[MySeatFloor*3+MySeatRoom][MySeatNum] = 0;
            MySeatNum = -1;
            if(select!=-1) {
                toggleButtons[select].setChecked(false);
                select = -1;
            }
            alarm.setCancel(true);
            //노티피케이션
        }
    }
    public void setSeat(int i,int j,int value){
        seat[i][j]=value;
    }
    public void setIsSeated(boolean isSeated){
        this.isSeated = isSeated;
    }
    public void setIsBooked(boolean isBooked){this.isBooked = isBooked;}
    public void setButtonBook(String text){this.buttonBook.setText(text);}
    public void setTimer(int timer){this.timer = timer;}

}
