package com.example.nuriapp.afterclean;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    String nickname;
    String imgpath;

    Button button_sub01_bm;
    Button button_sub01_sd;
    TextView profile_name;
    ImageView profile_img;
    private BackPressCloseSystem backPressCloseSystem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //버튼 저장
        button_sub01_bm = (Button)findViewById(R.id.button_sub01_bm);
        button_sub01_sd = (Button)findViewById(R.id.button_sub01_sd);

        //네비게이션 드로어 토글 설정(프로필-왼쪽)
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        backPressCloseSystem = new BackPressCloseSystem(this);
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
//            super.onBackPressed();
            backPressCloseSystem.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);

        //카톡프로필

        //profile 그룹의 데이터 로드
        SharedPreferences preferences = getSharedPreferences("profile", 0);
        nickname = preferences.getString("nickname", "default");
        imgpath = preferences.getString("imgpath", "default");

        final Bitmap[] bitmap = new Bitmap[1];
        profile_name = (TextView)findViewById(R.id.profile_name);
        profile_img = (ImageView)findViewById(R.id.profile_img);

        profile_name.setText(nickname);
        //getBitmap실행(그냥 실행하면 NetworkOnMainThreadException 발생)
        new Thread(new Runnable() {
            public void run() {
                try {
                    bitmap[0] = getBitmap(imgpath);
                }catch(Exception e) {

                }finally {
                    if(bitmap[0] !=null) {
                        runOnUiThread(new Runnable() {
                            @SuppressLint("NewApi")
                            public void run() {
                                profile_img.setImageBitmap(bitmap[0]);
                            }
                        });
                    }
                }
            }
        }).start();

        return true;
    }

    //url로 비트맵 생성 (카톡 프로필사진)
    private Bitmap getBitmap(String url) {
        URL imgUrl = null;
        HttpURLConnection connection = null;
        InputStream is = null;

        Bitmap retBitmap = null;

        try{
            imgUrl = new URL(url);
            connection = (HttpURLConnection) imgUrl.openConnection();
            connection.setDoInput(true); //url로 input받는 flag 허용
            connection.connect(); //연결
            is = connection.getInputStream(); // get inputstream
            retBitmap = BitmapFactory.decodeStream(is);
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            if(connection!=null) {
                connection.disconnect();
            }
            return retBitmap;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_my1) {
            //최근결제내역
            Intent intent = new Intent(getApplicationContext(), Sub08Activity.class);
            startActivity(intent);
        } else if (id == R.id.nav_my2) {
            //나의청소내역
            Intent intent = new Intent(getApplicationContext(), Sub09Activity.class);
            startActivity(intent);
        } else if (id == R.id.nav_menu1) {

        } else if (id == R.id.nav_menu2) {

        } else if (id == R.id.nav_menu3) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //지역 선택 이동 ,해당버튼의 아이디를 type값으로 저장
    //방문청소 선택
    public void next_sub01_bm(View v){
        Intent intent = new Intent(getApplicationContext(), Sub01Activity.class);
        intent.putExtra("type", "bm");
        startActivity(intent);
    }
    //소독청소 선택
    public void next_sub01_sd(View v){
        Intent intent = new Intent(getApplicationContext(), Sub01Activity.class);
        intent.putExtra("type", "sd");
        startActivity(intent);
    }

}
