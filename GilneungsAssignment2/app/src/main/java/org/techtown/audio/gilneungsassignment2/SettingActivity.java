package org.techtown.audio.gilneungsassignment2;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

public class SettingActivity extends AppCompatActivity {
    RadioButton radioButton1;
    RadioButton radioButton2;
    RadioButton radioButton3;
    SharedPreferences sharedPreferences;
    String locale;
    int locale_number;
    ArrayList<String> locales;
    ArrayAdapter adapter;
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

        switch (locale) {
            case "ko_KR": {
                locale_number = 0;
                break;
            }
            case "ja_JP":{
                locale_number = 1;
                break;
            }
            case "en_US":{
                locale_number = 2;
            }
        }

        radioButton1 = findViewById(R.id.radioButton1);
        radioButton2 = findViewById(R.id.radioButton2);
        radioButton3 = findViewById(R.id.radioButton3);
        radioButton1.setText(getStringByLocal(this, R.string.korea,locale));
        radioButton2.setText(getStringByLocal(this, R.string.japen,locale));
        radioButton3.setText(getStringByLocal(this, R.string.usa,locale));

        final RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup);
        Button b = (Button)findViewById(R.id.button2);

        locales = new ArrayList<>();

        locales.add(getStringByLocal(this, R.string.korea, locale));
        locales.add(getStringByLocal(this, R.string.japen, locale));
        locales.add(getStringByLocal(this, R.string.usa, locale));

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = rg.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton) findViewById(id);
                String countries = rb.getText().toString();
                if(countries.equals("한국")){
                    countries = "ko_KR";
                }else if(countries.equals("일본")){
                    countries = "ja_JP";
                }else if(countries.equals("미국")){
                    countries = "en_US";
                }

            if(id != locale_number){
                switch (id){
                    case 0:{
                        locale = "ko_KR";
                        break;
                    }
                    case 1:{
                        locale = "ja_JP";
                        break;
                    }
                    case 2:{
                        locale = "en_US";
                        break;
                    }
                }

                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("locale",locale);

                editor.commit();

                Intent intent = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                finish();
                startActivity(intent);
            }

                Intent myIntent = new Intent(SettingActivity.this, MainActivity.class);
                myIntent.putExtra("countries", countries);
                startActivity(myIntent);
            }
        });
    }

    @NonNull
    public static String getStringByLocal(Activity context, int resId, String locale){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            return getStringByLocalPluse17(context, resId, locale);
        else
            return getStringByLocalBefore17(context, resId, locale);
    }

    @NonNull
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static String getStringByLocalPluse17(Activity context, int resId, String locale){
        Configuration configuration = new Configuration(context.getResources().getConfiguration());
        configuration.setLocale(new Locale(locale));
        return context.createConfigurationContext(configuration).getResources().getString(resId);
    }

    //젤리빈 버전 이하일 경우
    private static String getStringByLocalBefore17(Context context, int resId, String language){
        Resources currentResources = context.getResources();
        AssetManager assets = currentResources.getAssets();
        DisplayMetrics metrics = currentResources.getDisplayMetrics();
        Configuration config = new Configuration(currentResources.getConfiguration());
        Locale locale = new Locale(language);
    }
}
