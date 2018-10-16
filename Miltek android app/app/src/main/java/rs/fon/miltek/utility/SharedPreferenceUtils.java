package rs.fon.miltek.utility;

import android.content.Context;
import android.content.SharedPreferences;

import rs.fon.miltek.MyApp;

public class SharedPreferenceUtils {
    private static String PREFERENCE_NAME = "miltek";
    private static SharedPreferenceUtils sharedPreferenceUtils;
    private SharedPreferences sharedPreferences;

    private SharedPreferenceUtils(Context context){
        PREFERENCE_NAME = PREFERENCE_NAME + context.getPackageName();
        this.sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPreferenceUtils getInstance(){
        if(sharedPreferenceUtils==null){
            sharedPreferenceUtils = new SharedPreferenceUtils(MyApp.getContext());
        }
        return sharedPreferenceUtils;
    }

    public void saveString(String key, String val){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, val);
        editor.commit();
    }

    public String getString(String key, String defVal){
        return sharedPreferences.getString(key, defVal);
    }

    public String getString(String key){
        return sharedPreferences.getString(key, "");
    }

    public void saveInt(String key, int val){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, val);
        editor.commit();
    }

    public int getInt(String key, int defVal){
        return sharedPreferences.getInt(key, defVal);
    }

    public int getInt(String key){
        return sharedPreferences.getInt(key, 0);
    }

    public void clearAllValues(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    public void clearValue(String key){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }
}
