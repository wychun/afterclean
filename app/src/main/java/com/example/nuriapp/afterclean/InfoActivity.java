package com.example.nuriapp.afterclean;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class InfoActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Button button_sub01_bm;
    Button button_sub01_sd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);


        //버튼 저장
        button_sub01_bm = (Button)findViewById(R.id.button_sub01_bm);
        button_sub01_sd = (Button)findViewById(R.id.button_sub01_sd);

        //네비게이션 드로어 토글 설정(회사정보 - 오른쪽)
        DrawerLayout drawer1 = (DrawerLayout) findViewById(R.id.drawer_layout1);
        ActionBarDrawerToggle ad = new ActionBarDrawerToggle(
                this, drawer1, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
        };
        drawer1.setDrawerListener(ad);
        ad.syncState();
        NavigationView navigationView1 = (NavigationView) findViewById(R.id.nav_view1);
        navigationView1.setNavigationItemSelectedListener(this);

    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer1 = (DrawerLayout) findViewById(R.id.drawer_layout1);
        if (drawer1.isDrawerOpen(GravityCompat.END)) {
            drawer1.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
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

        DrawerLayout drawer1 = (DrawerLayout) findViewById(R.id.drawer_layout1);
        drawer1.closeDrawer(GravityCompat.END);
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
