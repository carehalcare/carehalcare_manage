package carehalcare.carehalcare_manage;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenUtils {
    private static final String PREFS = "Token_prefs";
    private static final String Access_Token = "Access_Token";
    private static final String Refresh_Token = "Refresh_Token";
    private static final String User_Id = "User_Id";

    private static final String CUser_Id = "CUser_Id";
    private Context mContext;
    private static SharedPreferences prefs;
    private static SharedPreferences.Editor prefsEditor;
    private static TokenUtils instance;

    public static synchronized TokenUtils init(Context context) {
        if(instance == null)
            instance = new TokenUtils(context);
        return instance;
    }

    private TokenUtils(Context context) {
        mContext = context;
        prefs = mContext.getSharedPreferences(PREFS,Context.MODE_PRIVATE);
        prefsEditor = prefs.edit();
    }

    public static void setAccessToken(String value) {
        prefsEditor.putString(Access_Token, value).commit();
    }

    public static String getAccessToken(String defValue) {
        return prefs.getString(Access_Token,defValue);
    }
    public static void setCUser_Id(String value) {
        prefsEditor.putString(CUser_Id, value).commit();
    }

    public static String getCUser_Id(String defValue) {
        return prefs.getString(CUser_Id,defValue);
    }

    public static void setUser_Id(String value) {
        prefsEditor.putString(User_Id, value).commit();
    }

    public static String getUser_Id(String defValue) {
        return prefs.getString(User_Id,defValue);
    }

    public static void setRefreshToken(String value) {
        prefsEditor.putString(Refresh_Token, value).commit();
    }

    public static String getRefreshToken(String defValue) {
        return prefs.getString(Refresh_Token,defValue);
    }

    public static void clearToken() {
        prefsEditor.clear().apply();
    }

}
