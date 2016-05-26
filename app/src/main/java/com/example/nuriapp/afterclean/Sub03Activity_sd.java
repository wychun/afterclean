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

import java.util.StringTokenizer;

//옵션, 면적 선택 -소독청소
public class Sub03Activity_sd extends AppCompatActivity {
    String type;
    String addr;
    String jugi;
    String price;
    String size;
    String optionprice = "0";
    String[] optionlist;


    int clickcheck1 = 1;
    int clickcheck2 = 1;
    int clickcheck3 = 1;
    int clickcheck4 = 1;

    int basic_price; //초기 소독가격
    int op_price = 0;// 면적에 따른 추가가격

    TextView t_price;
    TextView t_jugi;
    TextView t_size;
    NumberPicker numberPicker;
    ImageButton ib_option1;
    ImageButton ib_option2;
    ImageButton ib_option3;
    ImageButton ib_option4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub03_sd);

        //뒤로가기버튼
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        addr = intent.getStringExtra("addr");
        jugi = intent.getStringExtra("jugi");
        price = intent.getStringExtra("price");
        size = intent.getStringExtra("size");

        StringTokenizer stringTokenizer = new StringTokenizer(price);
        basic_price = Integer.valueOf(stringTokenizer.nextToken("원"));//초기 소독가격 저장

        t_price = (TextView)findViewById(R.id.sub03_sd_text03_price);
        t_price.setText(price);
        t_jugi = (TextView)findViewById(R.id.sub03_sd_text03_jugi);
        t_jugi.setText(jugi);
        t_size = (TextView)findViewById(R.id.sub03_sd_text03_size);
        t_size.setText(size);

        //면적 선택 (최소 1, 최대 100 선택가능, 표시는 10평단위)
        numberPicker = (NumberPicker)findViewById(R.id.sub03_sd_plustime);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(100);
        numberPicker.setValue(19);//default
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                price = String.valueOf(basic_price);
                int size_num;
                //20평 미만은 모두 '20평 미만'으로 표시, 20평 이상은 10평 단위로 표시
                if(oldVal<20){oldVal = 19;}
                size_num = (int)(oldVal/10+1)*10;


                size = String.valueOf(size_num) + "평 미만";
                op_price = (size_num-20)*220;
                price = String.valueOf(basic_price + op_price) + "원";

                t_size.setText(size);

                if(optionprice != null && !optionprice.equalsIgnoreCase("0")){
                    //첫 옵션은 무료
                     t_price.setText(price + " + " + (Integer.parseInt(optionprice)-6600) + "원");
                }else{
                    t_price.setText(price);
                }

            }
        });
        ib_option1 = (ImageButton)findViewById(R.id.sub03_sd_option01);
        ib_option2 = (ImageButton)findViewById(R.id.sub03_sd_option02);
        ib_option3 = (ImageButton)findViewById(R.id.sub03_sd_option03);
        ib_option4 = (ImageButton)findViewById(R.id.sub03_sd_option04);



    }

    public void next_sub04(View v){
        Intent intent = new Intent(getApplicationContext(), Sub04Activity.class);
        intent.putExtra("type", type);
        intent.putExtra("addr", addr);
        intent.putExtra("jugi", jugi);
        intent.putExtra("size", size);
        intent.putExtra("price", price);
        if(!optionprice.equalsIgnoreCase("0")){
            //첫 옵션은 무료
            optionprice = String.valueOf(Integer.parseInt(optionprice) - 6600);
        }
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
        intent.putExtra("optionlist", optionlist);
        startActivity(intent);
    }


    //option선택시 옵션가격 추가, 해제
    public void selectOption1(View v){

        ib_option1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //색상변경으로구분
                ib_option1.setSelected(!ib_option1.isSelected());

                int o_price = Integer.parseInt(optionprice) + clickcheck1 * 6600;
                optionprice = String.valueOf(o_price);
                t_price.setText(price + " + " + optionprice + "원");
                /*      if(clickcheck1 == 1){
                          v.setBackgroundColor(Color.rgb(2,171,217));
                      }else {v.setBackgroundColor(Color.rgb(204,204,204));}*/
                clickcheck1 = clickcheck1 * (-1);
                //옵션가격 두번째부터 계산
                if (!optionprice.equalsIgnoreCase("0")) {
                    t_price.setText(price + " + " + (Integer.parseInt(optionprice) - 6600) + "원");
                }
            }
        });
    }
    public void selectOption2(View v) {
        ib_option2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ib_option2.setSelected(!ib_option2.isSelected());

                int o_price = Integer.parseInt(optionprice) + clickcheck2 * 6600;
                optionprice = String.valueOf(o_price);
                t_price.setText(price + " + " + optionprice + "원");
            /*    if(clickcheck2 == 1){
                    v.setBackgroundColor(Color.rgb(2,171,217));
                }else {v.setBackgroundColor(Color.rgb(204,204,204));}*/
                clickcheck2 = clickcheck2 * (-1);
                //옵션가격 두번째부터 계산
                if (!optionprice.equalsIgnoreCase("0")) {
                    t_price.setText(price + " + " + (Integer.parseInt(optionprice) - 6600) + "원");
                }
            }
        });
    }
    public void selectOption3(View v) {
        ib_option3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //색상변경으로구분
                ib_option3.setSelected(!ib_option3.isSelected());

                int o_price = Integer.parseInt(optionprice) + clickcheck3 * 6600;
                optionprice = String.valueOf(o_price);
                t_price.setText(price + " + " + optionprice + "원");
                /* if(clickcheck3 == 1){
                     v.setBackgroundColor(Color.rgb(2,171,217));
                 }else {v.setBackgroundColor(Color.rgb(204,204,204));}*/
                clickcheck3 = clickcheck3 * (-1);
                //옵션가격 두번째부터 계산
                if (!optionprice.equalsIgnoreCase("0")) {
                    t_price.setText(price + " + " + (Integer.parseInt(optionprice) - 6600) + "원");
                }
            }
        });
    }

    public void selectOption4(View v){
        ib_option4.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                //색상변경으로구분
                ib_option4.setSelected(!ib_option4.isSelected());

                int o_price = Integer.parseInt(optionprice) + clickcheck4*6600;
                optionprice = String.valueOf(o_price);
                t_price.setText(price+" + "+optionprice+"원");

                clickcheck4 = clickcheck4*(-1);
                //옵션가격 두번째부터 계산
                if(!optionprice.equalsIgnoreCase("0")){
                    t_price.setText(price + " + " + (Integer.parseInt(optionprice)-6600) + "원");
                }
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
