package com.example.nuriapp.afterclean;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

//최근 결제내역
public class Sub08Activity extends AppCompatActivity {
    ListView listView;
    String[] date, timesize, help, price;
    int[] images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub08);

        //뒤로가기버튼
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    //뒤로가기 이벤트
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //ActionBar 메뉴 클릭에 대한 이벤트 처리
        finish();
        return super.onOptionsItemSelected(item);
    }
}
