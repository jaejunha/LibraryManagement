package dreamline91.naver.com.android.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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
    }
}
