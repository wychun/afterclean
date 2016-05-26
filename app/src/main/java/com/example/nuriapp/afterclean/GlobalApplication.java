package com.example.nuriapp.afterclean;

import android.app.Activity;
import android.app.Application;
import android.net.Uri;

import com.kakao.auth.KakaoSDK;
//카카오로그인 연동 필수 class
public class GlobalApplication extends Application {
    private static volatile GlobalApplication obj = null;
    private static volatile Activity currentActivity = null;

    public Uri m_uriResult;
    public  boolean b_type      = false;

    public  static final  String  m_strLogTag = "PaySample";
    public  static final  String  s_strLogTag = "PayACNTSample";

    @Override
    public void onCreate() {
        super.onCreate();
        obj = this;
        KakaoSDK.init(new KakaoSDKAdapter());
    }

    public static GlobalApplication getGlobalApplicationContext() {
        return obj;
    }

    public static Activity getCurrentActivity() {
        return currentActivity;
    }

    // Activity가 올라올때마다 Activity의 onCreate에서 호출해줘야한다.
    public static void setCurrentActivity(Activity currentActivity) {
        GlobalApplication.currentActivity = currentActivity;
    }
}
