package org.techtown.audio.gilneungsassignment2;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class BaseContextWrapper extends AppCompatActivity {
    //AppCompatActivity-FragmentActivity-ComponentActivity-Activity-ContextThemeWrapper-ContextWrapper-Context
    private static Locale sLocale;

    public static Context wrap(Context base) {
        if (sLocale == null) {
            return base;
        }

        final Resources res = base.getResources();
        final Configuration config = res.getConfiguration();
        config.setLocale(sLocale);
        return base.createConfigurationContext(config);
    }

    public static void onBaseContextWrapper(String lang) {
        sLocale = new Locale(lang);
    }

    @NonNull
    public static String getStringByLocal(Activity context, int resId, String locale) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            return getStringByLocalPluse24(context, resId, locale);
        else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            return getStringByLocalPluse17(context, resId, locale);
        else
            return getStringByLocalBefore17(context, resId, locale);
    }

    //누가 버전 이상일경우
    @NonNull
    @TargetApi(Build.VERSION_CODES.N)
    public static String getStringByLocalPluse24(Activity context, int resId, String locale) {
        Configuration configuration = new Configuration(context.getResources().getConfiguration());
        configuration.setLocale(new Locale(locale));

        LocaleList localeList = new LocaleList(new Locale(locale));
        LocaleList.setDefault(localeList);
        configuration.setLocales(localeList);

        return context.createConfigurationContext(configuration).getResources().getString(resId);
    }

    //젤리빈 버전 이상일경우
   @NonNull
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
   public static String getStringByLocalPluse17(Activity context, int resId, String locale) {
        Configuration configuration = new Configuration(context.getResources().getConfiguration());
        configuration.setLocale(new Locale(locale));
        return context.createConfigurationContext(configuration).getResources().getString(resId);
    }

    //젤리빈 버전 이하일 경우
    public static String getStringByLocalBefore17(Context context, int resId, String language) {
        Resources currentResources = context.getResources();
        AssetManager assets = currentResources.getAssets();
        DisplayMetrics metrics = currentResources.getDisplayMetrics();
        Configuration config = new Configuration(currentResources.getConfiguration());
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        config.locale = locale;

        Resources defaultLocaleResources = new Resources(assets, metrics, config);
        String string = defaultLocaleResources.getString(resId);
        // Restore device-specific locale
        new Resources(assets, metrics, currentResources.getConfiguration());
        return string;

    }
}