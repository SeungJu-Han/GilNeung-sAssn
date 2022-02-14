package org.techtown.audio.gilneungsassignment2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Locale;

public class SettingActivity extends BaseContextWrapper {
    RadioButton radioButton1;
    RadioButton radioButton2;
    RadioButton radioButton3;
    SharedPreferences sharedPreferences;
    String locale;
    ArrayList<String> locales;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);

        //버전 확인 후 sharedpreferences에 locale키 값의 value 값을 가져옵니다.
        //값이 없을 경우 기본언어 설정
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            locale = sharedPreferences.getString("locale", getResources().getConfiguration().getLocales().get(0).getLanguage());
        }else{
            locale = sharedPreferences.getString("locale", Resources.getSystem().getConfiguration().locale.getLanguage());
        }

        radioButton1 = findViewById(R.id.radioButton1);
        radioButton2 = findViewById(R.id.radioButton2);
        radioButton3 = findViewById(R.id.radioButton3);
        radioButton1.setText(getStringByLocal(this, R.string.korea,locale));
        radioButton2.setText(getStringByLocal(this, R.string.japen,locale));
        radioButton3.setText(getStringByLocal(this, R.string.usa,locale));

        final RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup);
        rg.check(R.id.radioButton1); //라디오버튼 초기값 설정
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.radioButton1){
                    locale = "ko_KR";
                }else if(i == R.id.radioButton2){
                    locale = "ja_JP";
                }else if(i == R.id.radioButton3){
                    locale = "en_US";
                }
            }
        });
        Button okButton = (Button)findViewById(R.id.button2);

        okButton.setText(getStringByLocal(this, R.string.ok,locale));

        locales = new ArrayList<>();

        locales.add(getStringByLocal(this, R.string.korea, locale));
        locales.add(getStringByLocal(this, R.string.japen, locale));
        locales.add(getStringByLocal(this, R.string.usa, locale));

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLanguage(locale);

                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("locale",locale);
                //저장
                editor.commit();



                //어플 재시작
                Intent intent = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                finish();
                startActivity(intent);

            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(BaseContextWrapper.wrap(newBase));
    }
    public void changeLanguage(String locale) {
        BaseContextWrapper.onBaseContextWrapper(locale);
        recreate();

    }

}
