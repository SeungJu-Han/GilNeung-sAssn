package org.techtown.audio.gilneungsassignment2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends BaseContextWrapper {
    Button button;
    TextView textView;
    TextView textView2;

    @Override
    protected void onStart() {
        super.onStart();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onResume() {
        super.onResume();
        onBaseContextWrapperString(Locale.getDefault().toString());
        SharedPreferences sharedPreferences = this.getSharedPreferences("shared", MODE_PRIVATE);
        String languageToLoad = sharedPreferences.getString("locale", getResources().getConfiguration().getLocales().get(0).toString());

        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        this.getResources().updateConfiguration(config, this.getResources().getDisplayMetrics());

        // 현재 기기 언어 설정값 가져오기
        textView2.setText(Locale.getDefault().toString());

        button.setText(getStringByLocal(this, R.string.setting,languageToLoad));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , SettingActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onBaseContextWrapperString(String s){
        BaseContextWrapper.onBaseContextWrapper(s);

    }

}