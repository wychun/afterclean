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

//주기선택 -방문청소
public class Sub02Activity_bm extends AppCompatActivity {
    String type;
    String addr;
    String jugi;
    String price;
    String time;
    TextView t_price;
    TextView t_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub02_bm);

        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        addr = intent.getStringExtra("addr");

        //뒤로가기버튼
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        //이전 페이지의 addr 값 출력
        TextView t_addr = (TextView) findViewById(R.id.sub02_bm_addr);
        t_addr.setText(addr);

        //arrays.xml의 주기목록 불러오기
        Spinner s = (Spinner) findViewById(R.id.sub02_bm_jugi01);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.jugi_bm, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

        //청소가격
        t_price = (TextView) findViewById(R.id.sub02_bm_price02);

        //청소예상시간
        t_time = (TextView) findViewById(R.id.sub02_bm_time02);
        time = "2";
        t_time.setText(time + "시간");

        RelativeLayout relativeLayout1 = (RelativeLayout) findViewById(R.id.sub02_bm_jugigroup1);
        relativeLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner spinner = (Spinner) findViewById(R.id.sub02_bm_jugi01);
                jugi = spinner.getSelectedItem().toString();
                TextView textView = (TextView) findViewById(R.id.sub02_bm_jugi01_2);
                if(jugi.equals("매주 1회")) textView.setText("100000");
                if(jugi.equals("매주 2회")) textView.setText("200000");
                price = textView.getText().toString();
                t_price.setText(price + " / " + jugi);
            }
        });

        RelativeLayout relativeLayout2 = (RelativeLayout) findViewById(R.id.sub02_bm_jugigroup2);
        relativeLayout2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                TextView textView1 = (TextView)findViewById(R.id.sub02_bm_jugi02);
                jugi = textView1.getText().toString();
                TextView textView2 = (TextView)findViewById(R.id.sub02_bm_jugi02_2);
                //price = textView2.getText().toString();
                price = "100000"; //테스트임
                t_price.setText(price+" / "+jugi);
            }
        });

        RelativeLayout relativeLayout3 = (RelativeLayout) findViewById(R.id.sub02_bm_jugigroup3);
        relativeLayout3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                TextView textView1 = (TextView)findViewById(R.id.sub02_bm_jugi03);
                jugi = textView1.getText().toString();
                TextView textView2 = (TextView)findViewById(R.id.sub02_bm_jugi03_2);
                price = textView2.getText().toString();
                t_price.setText(price+" / "+jugi);
            }
        });

    }


    //시간선택으로 이동
    public void next_sub03(View v) {
        Intent intent = new Intent(getApplicationContext(), Sub03Activity_bm.class);
        intent.putExtra("type", type);
        intent.putExtra("addr", addr);
        intent.putExtra("jugi", jugi);
        intent.putExtra("time", time);
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
