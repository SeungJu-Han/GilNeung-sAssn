package org.techtown.audio.gilneungsassignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button button;
    TextView textView;
    TextView textView2;

    String countries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2); //처음화면은 무조건 빈값인가요 고정값일이유가 없지않을까요
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , SettingActivity.class);
                startActivity(intent); //StartActivity말고 결과값 받는게 여기서 제일 나을것 같습니다.
            }
        });

        Intent countriesIntent = getIntent();
        countries = countriesIntent.getStringExtra("countries");
        textView2.setText(countries);  //SettingActivity에서 startActivity해서 onCreate 생명주기가 타는건데
        
    }
}
