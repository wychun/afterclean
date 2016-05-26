package com.example.nuriapp.afterclean;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

//최근 결제내역
public class Sub09Activity extends AppCompatActivity {
    String type;
    String jugi;
    String price;
    String time;
    String optiontime;
    String optionprice;
    String date;
    String totalprice;
    String[] optionlist;
    String c_time;
    String point;

    TextView t_1;//청소 소요시간 or 면적
    TextView t_2;//청소가격
    TextView t_3;//청소주기
    TextView textView1;//일시 date
    TextView textView2;//소요시간 time + optiontime or size
    TextView textView3;//위치 addr + addrdetail
    TextView textView4;//기본금 price
    TextView textView5;//주기 jugi
    TextView textView7;//총 금액 totalprice
    ListView listView;
    Context context;
    CheckBox checkBox;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub09);

        //뒤로가기버튼
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        jugi = intent.getStringExtra("jugi");
        price = intent.getStringExtra("price");
        optionprice = intent.getStringExtra("optionprice");
        date = intent.getStringExtra("date");
        c_time = intent.getStringExtra("c_time");

      /*  optionlist = intent.getStringArrayExtra("optionlist");

        String[] date = new String[optionlist.length];
        String[] time = new String[optionlist.length];
        String[] price = new String[optionlist.length];
        String[] point = new String[optionlist.length];
        int[] images = new int[optionlist.length];

        //list_item과 연결
        context = this;
        listView = (ListView)findViewById(R.id.sub09_list);
        listView.setAdapter(new CustomAdapter2(this, date, time, price, point, images));

        //옵션리스트 크기 조절
        ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = optionlist.length*300;
        listView.setLayoutParams(layoutParams);

        // option종류 리스트 저장
        for(int i=0; i<optionlist.length; i++){
            if (type.equalsIgnoreCase("bm")){
                date[i] = "2016년 5월 19일 ";
                time[i] = "2시간 + 1시간";
                price[i] = "36000원";
                point[i] = "1800 Point 적립";
                images[i] = R.drawable.helper;
            }//방문청소 옵션가격
            else {
                date[i] = "2016년 5월 19일 04:30";
                time[i] = "2시간 + 3시간";
                price[i] = "36000원";
                point[i] = "1800 Point 적립";
                images[i] = R.drawable.helper;
            }//소독청소 옵션가격
        }*/
    }

    //뒤로가기 이벤트
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //ActionBar 메뉴 클릭에 대한 이벤트 처리
        finish();
        return super.onOptionsItemSelected(item);
    }
}
