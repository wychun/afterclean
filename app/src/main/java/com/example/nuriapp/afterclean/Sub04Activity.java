package com.example.nuriapp.afterclean;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;

//날짜 선택
public class Sub04Activity extends AppCompatActivity {
    String type;
    String addr;
    String jugi;
    String price;
    String size;
    String time;
    String optiontime;
    String optionprice;
    String date;
    String[] optionlist;
    String c_time;

    TextView t1;
    TextView t2;
    TextView t_price;
    TextView t_jugi;
    TextView t_time;
    TextView t_size;
    Spinner spinner;
    CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub04);

        //뒤로가기버튼
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        addr = intent.getStringExtra("addr");
        jugi = intent.getStringExtra("jugi");
        price = intent.getStringExtra("price");
        optionprice = intent.getStringExtra("optionprice");
        optionlist = intent.getStringArrayExtra("optionlist");

        if(type.equalsIgnoreCase("bm")){
            time = intent.getStringExtra("time");
            optiontime = intent.getStringExtra("optiontime");

            t1 = (TextView)findViewById(R.id.sub04_text01);
            t1.setText("방문청소 가격");
            t2 = (TextView)findViewById(R.id.sub04_text02);
            t2.setText("방문청소 예상시간");
            t_jugi = (TextView)findViewById(R.id.sub04_text03_jugi);
            t_jugi.setText(jugi);
            t_time = (TextView)findViewById(R.id.sub04_text03_time);
            t_price = (TextView)findViewById(R.id.sub04_text03_price);

            if(optionprice == null){t_price.setText(price);}
            else{t_price.setText(price+" + "+optionprice+"원");}

            if(optiontime == null){t_time.setText(time + "시간");}
            else{t_time.setText(time + "시간" + " + " + optiontime + "시간");}
        }

        else{
            size = intent.getStringExtra("size");

            t1 = (TextView)findViewById(R.id.sub04_text01);
            t1.setText("소독 가격");
            t2 = (TextView)findViewById(R.id.sub04_text02);
            t2.setText("소독 면적");
            t_jugi = (TextView)findViewById(R.id.sub04_text03_jugi);
            t_jugi.setText(jugi);
            t_size = (TextView)findViewById(R.id.sub04_text03_time);
            t_price = (TextView)findViewById(R.id.sub04_text03_price);

            if(optionprice == null){t_price.setText(price);}
            else{t_price.setText(price+" + "+optionprice+"원");}

            t_size.setText(size);

        }


        //arrays.xml의 시간목록 불러오기(type별 구분)
        spinner = (Spinner)findViewById(R.id.sub04_time);
        ArrayAdapter adapter = null;
        if(type.equalsIgnoreCase("bm")){
            adapter = ArrayAdapter.createFromResource(this, R.array.time_bm, android.R.layout.simple_spinner_item);
        }
        else if (type.equalsIgnoreCase("sd")){
            adapter = ArrayAdapter.createFromResource(this, R.array.time_sd, android.R.layout.simple_spinner_item);
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        c_time = (String)spinner.getSelectedItem().toString();

        //킷캣부터 달력표시됨
        calendarView = (CalendarView)findViewById(R.id.sub04_layout03);
        //선택 안했을시 (초기 날짜값)
        date = new SimpleDateFormat("yyyy년 MM월 dd일").format(calendarView.getDate());
        //선택 했을시 (변경 날짜값)
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                date = year+"년"+month+"월"+dayOfMonth+"일";
            }
        });


    }

    //이동
    public void next_sub05(View v){

        String sub04_time = spinner.getSelectedItem().toString();
        Intent intent = new Intent(getApplicationContext(), Sub05Activity.class);
        intent.putExtra("type", type);
        intent.putExtra("addr", addr);
        intent.putExtra("jugi", jugi);
        intent.putExtra("sub04_time", sub04_time);
        intent.putExtra("price", price);
        intent.putExtra("optionprice", optionprice);
        intent.putExtra("date", date);
        intent.putExtra("c_time", c_time);
        intent.putExtra("optionlist", optionlist);
        if(type.equalsIgnoreCase("bm")){
            intent.putExtra("time", time);
            intent.putExtra("optiontime", optiontime);
        }
        else{
            intent.putExtra("size", size);
        }
        startActivity(intent);
    }

    //뒤로가기 이벤트
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //ActionBar 메뉴 클릭에 대한 이벤트 처리
        finish();
        return super.onOptionsItemSelected(item);
    }
}
