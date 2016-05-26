package com.example.nuriapp.afterclean;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;

//옵션, 추가시간 선택 -방문청소
public class Sub03Activity_bm extends AppCompatActivity {
    String type;
    String addr;
    String jugi;
    String price;
    String time;
    String optiontime;
    String optionprice = "0";
    String[] optionlist;

    int clickcheck1 = 1;
    int clickcheck2 = 1;
    int clickcheck3 = 1;
    int clickcheck4 = 1;


    TextView t_price;
    TextView t_jugi;
    TextView t_time;
    NumberPicker numberPicker;
    ImageButton ib_option1;
    ImageButton ib_option2;
    ImageButton ib_option3;
    ImageButton btn1;
    ImageButton btn2;
    ImageButton btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub03_bm);

        //뒤로가기버튼
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        addr = intent.getStringExtra("addr");
        jugi = intent.getStringExtra("jugi");
        price = intent.getStringExtra("price");
        time = intent.getStringExtra("time");

        t_price = (TextView)findViewById(R.id.sub03_bm_text03_price);
        t_price.setText(price);
        t_jugi = (TextView)findViewById(R.id.sub03_bm_text03_jugi);
        t_jugi.setText(jugi);
        t_time = (TextView)findViewById(R.id.sub03_bm_text03_time);
        t_time.setText(time + "시간");

        numberPicker = (NumberPicker)findViewById(R.id.sub03_bm_plustime);
        numberPicker.setMaxValue(8);
        numberPicker.setMinValue(2);
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                optiontime = String.valueOf(oldVal - 2);
                t_time.setText(time + "시간" + " + " + optiontime + "시간");
            }
        });
        ib_option1 = (ImageButton)findViewById(R.id.sub03_bm_option01);
        ib_option2 = (ImageButton)findViewById(R.id.sub03_bm_option02);
        ib_option3 = (ImageButton)findViewById(R.id.sub03_bm_option03);
       // ib_option4 = (ImageButton)findViewById(R.id.sub03_bm_option04);



    }

    public void next_sub04(View v){
        Intent intent = new Intent(getApplicationContext(), Sub04Activity.class);
        intent.putExtra("type", type);
        intent.putExtra("addr", addr);
        intent.putExtra("jugi", jugi);
        intent.putExtra("time", time);
        intent.putExtra("price", price);
        intent.putExtra("optiontime", optiontime);
        intent.putExtra("optionprice", optionprice);
        //마지막으로 옵션선택 확인
        int check_sum = clickcheck1 + clickcheck2 + clickcheck3 + clickcheck4;
        int list_size = (4-check_sum)/2;
        Log.d("리스트 사이즈 계산값", String.valueOf(list_size));
        optionlist = new String[list_size];
        int i = 0;
        if(clickcheck1 == (-1)){
            optionlist[i] = "option1";
            i++;
        }
        if(clickcheck2 == (-1)) {
            optionlist[i] = "option2";
            i++;
        }
        if(clickcheck3 == (-1)) {
            optionlist[i] = "option3";
            i++;
        }
        if(clickcheck4 == (-1)){
            optionlist[i] = "option4";
            i++;
        }
        intent.putExtra("optionlist", optionlist);
        Log.d("check1", String.valueOf(clickcheck1));
        Log.d("check2", String.valueOf(clickcheck2));
        Log.d("check3", String.valueOf(clickcheck3));
        Log.d("check4", String.valueOf(clickcheck4));

        Log.d("리스트 사이즈3", String.valueOf(optionlist.length));
        startActivity(intent);
    }


    //option선택시 옵션가격 추가, 해제
    public void selectOption1(View v){
        btn1 = (ImageButton)findViewById(R.id.sub03_bm_option01);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn1.setSelected(!btn1.isSelected());

                int o_price = Integer.parseInt(optionprice) + clickcheck1 * 11000;
                optionprice = String.valueOf(o_price);
                t_price.setText(price + " + " + optionprice + "원");

                clickcheck1 = clickcheck1 * (-1);
            }
        });

    }
    public void selectOption2(View v){
       btn2 = (ImageButton)findViewById(R.id.sub03_bm_option02);
        btn2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                btn2.setSelected(!btn2.isSelected());

                //색상변경으로구분
                int o_price = Integer.parseInt(optionprice) + clickcheck2*11000;
                optionprice = String.valueOf(o_price);
                t_price.setText(price+" + "+optionprice+"원");
               /* if(clickcheck2 == 1){
                    v.setBackgroundColor(Color.rgb(2,171,217));
                }else {v.setBackgroundColor(Color.rgb(204,204,204));}*/
                clickcheck2 = clickcheck2*(-1);
            }

        });

    }
    public void selectOption3(View v){

        btn3 = (ImageButton)findViewById(R.id.sub03_bm_option03);
        btn3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                btn3.setSelected(!btn3.isSelected());

                //색상변경으로구분
                int o_price = Integer.parseInt(optionprice) + clickcheck3 * 11000;
                optionprice = String.valueOf(o_price);
                t_price.setText(price + " + " + optionprice + "원");
               /* if(clickcheck2 == 1){
                    v.setBackgroundColor(Color.rgb(2,171,217));
                }else {v.setBackgroundColor(Color.rgb(204,204,204));}*/
                clickcheck3 = clickcheck3 * (-1);
            }
         });
    }


    //뒤로가기 이벤트
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //ActionBar 메뉴 클릭에 대한 이벤트 처리
        finish();
        return super.onOptionsItemSelected(item);
    }
}
