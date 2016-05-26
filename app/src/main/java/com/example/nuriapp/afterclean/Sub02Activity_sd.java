package com.example.nuriapp.afterclean;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

//주기선택 -소독청소
public class Sub02Activity_sd extends AppCompatActivity {
    String type;
    String addr;
    String jugi;
    String size;
    String price;

    TextView t_price;
    TextView t_size;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub02_sd);

        Intent intent = getIntent();
        addr = intent.getStringExtra("addr");
        type = intent.getStringExtra("type");

        //뒤로가기버튼
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //이전 페이지의 addr 값 출력
        TextView t = (TextView)findViewById(R.id.sub02_sd_addr);
        t.setText(addr);

        //arrays.xml의 주기목록 불러오기
        Spinner s = (Spinner)findViewById(R.id.sub02_sd_jugi03);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.jugi_sd, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

        //소독가격
        t_price = (TextView)findViewById(R.id.sub02_sd_price02);

        //소독면적
        t_size = (TextView)findViewById(R.id.sub02_sd_size02);
        size = "20평미만";
        t_size.setText(size);

        //주기, 가격 선택
        RelativeLayout relativeLayout1 = (RelativeLayout) findViewById(R.id.sub02_sd_jugigroup1);
        relativeLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView1 = (TextView) findViewById(R.id.sub02_sd_jugi01);
                jugi = textView1.getText().toString();
                TextView textView2 = (TextView) findViewById(R.id.sub02_sd_jugi01_2);
                price = textView2.getText().toString();
                t_price.setText(price + " / " + jugi);
            }
        });

        RelativeLayout relativeLayout2 = (RelativeLayout) findViewById(R.id.sub02_sd_jugigroup2);
        relativeLayout2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                TextView textView1 = (TextView)findViewById(R.id.sub02_sd_jugi02);
                jugi = textView1.getText().toString();
                TextView textView2 = (TextView)findViewById(R.id.sub02_sd_jugi02_2);
                price = textView2.getText().toString();
                t_price.setText(price+" / "+jugi);
            }
        });

        RelativeLayout relativeLayout3 = (RelativeLayout) findViewById(R.id.sub02_sd_jugigroup3);
        relativeLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner spinner = (Spinner) findViewById(R.id.sub02_sd_jugi03);
                jugi = spinner.getSelectedItem().toString();
                TextView textView = (TextView) findViewById(R.id.sub02_sd_jugi03_2);
                price = textView.getText().toString();
                t_price.setText(price + " / " + jugi);

            }
        });

        RelativeLayout relativeLayout4 = (RelativeLayout) findViewById(R.id.sub02_sd_jugigroup4);
        relativeLayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView1 = (TextView) findViewById(R.id.sub02_sd_jugi04);
                jugi = textView1.getText().toString();
                TextView textView2 = (TextView) findViewById(R.id.sub02_sd_jugi04_2);
                price = textView2.getText().toString();
                t_price.setText(price + " / " + jugi);
            }
        });

        RelativeLayout relativeLayout5 = (RelativeLayout) findViewById(R.id.sub02_sd_jugigroup5);
        relativeLayout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView1 = (TextView) findViewById(R.id.sub02_sd_jugi05);
                jugi = textView1.getText().toString();
                TextView textView2 = (TextView) findViewById(R.id.sub02_sd_jugi05_2);
                price = textView2.getText().toString();
                t_price.setText(price + " / " + jugi);
            }
        });



    }

    //시간선택으로 이동
    public void next_sub03(View v){
        Intent intent = new Intent(getApplicationContext(), Sub03Activity_sd.class);
        intent.putExtra("type", type);
        intent.putExtra("addr", addr);
        intent.putExtra("jugi", jugi);
        intent.putExtra("size", size);
        intent.putExtra("price", price);
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
