package com.example.nuriapp.afterclean;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;

import com.kakao.auth.ErrorCode;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;

//첫 화면 (카카오 로그인)
public class LoginActivity extends AppCompatActivity {
    private SessionCallback callback;

    /**
     * 로그인 버튼을 클릭 했을시 access token을 요청하도록 설정한다.
     *
     * @param savedInstanceState 기존 session 정보가 저장된 객체
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(Window.FEATURE_NO_TITLE, Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        UserManagement.requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {
                //로그아웃 성공 후 처리

                //profile 그룹 데이터 초기화
                SharedPreferences preferences = getSharedPreferences("profile", 0);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();
            }
        });

        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {

            UserManagement.requestMe(new MeResponseCallback() {

                @Override
                public void onFailure(ErrorResult errorResult) {
                    String message = "failed to get user info. msg=" + errorResult;
                    Logger.d(message);

                    ErrorCode result = ErrorCode.valueOf(errorResult.getErrorCode());
                    if (result == ErrorCode.CLIENT_ERROR_CODE) {
                        finish();
                    } else {
                        //redirectMainActivity();
                    }
                }

                @Override
                public void onSessionClosed(ErrorResult errorResult) {
                }

                @Override
                public void onNotSignedUp() {
                }

                @Override
                public void onSuccess(UserProfile userProfile) {
                    //로그인에 성공하면 로그인한 사용자의 일련번호, 닉네임, 이미지url등을 리턴합니다.
                    //사용자 ID는 보안상의 문제로 제공하지 않고 일련번호는 제공합니다.
                    Log.e("UserProfile", userProfile.toString());
                    //로그인 성공시 메인화면(프로필, 청소타입선택)으로 이동
                    Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);

                    //profile 그룹에 nickname, imgpath 저장(메인화면을 벗어나도 로그인동안 유저데이터가 유지되도록)
                    SharedPreferences preferences = getSharedPreferences("profile", 0);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("nickname", userProfile.getNickname().toString());
                    editor.putString("imgpath", userProfile.getProfileImagePath().toString());
                    editor.commit();

                    startActivity(intent);
                }
            });

        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            //세션 연결이 실패했을 때
        }
    }

}