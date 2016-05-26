package com.example.nuriapp.afterclean;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.StringTokenizer;

//결제 전
public class Sub06Activity extends AppCompatActivity {
    String type;
    String addr;
    String jugi;
    String price;
    String sub04_time;
    String time;
    String optiontime;
    String size;
    String optionprice;
    String date;
    String addrdetail;
    String totalprice;
    String[] optionlist;

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
    ImageView img1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub06);



        //뒤로가기버튼
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        addr = intent.getStringExtra("addr");
        jugi = intent.getStringExtra("jugi");
        sub04_time = intent.getStringExtra("sub04_time");
        price = intent.getStringExtra("price");
        optionprice = intent.getStringExtra("optionprice");
        date = intent.getStringExtra("date");

        addrdetail = intent.getStringExtra("addrdetail");
        optionlist = intent.getStringArrayExtra("optionlist");
        Log.d("리스트 사이즈6", String.valueOf(optionlist.length));

        String[] options = new String[optionlist.length];
        int[] images = new int[optionlist.length];
        // option종류, option이미지 리스트 저장
        for(int i=0; i<optionlist.length; i++){
            if (type.equalsIgnoreCase("bm")){options[i] = "11000원";}//방문청소 옵션가격
            else {
                if(i == 0){options[i] = "무료";}//첫회 무료
                else {options[i] = "6600원";}
                }//소독청소 옵션가격

            switch (optionlist[i]){
                case "option1":
                    if(type.equalsIgnoreCase("bm")){
                        //방문청소 옵션1 사진
                        images[i] = R.drawable.clean1_on;
                    }else {
                        //소독청소 옵션1 사진
                        images[i] = R.drawable.kill1_on;
                    }break;
                case "option2":
                    if(type.equalsIgnoreCase("bm")){
                        //방문청소 옵션2 사진
                        images[i] = R.drawable.clean2_on;
                    }else {
                        //소독청소 옵션2 사진
                        images[i] = R.drawable.kill2_on;
                    }break;
                case "option3":
                    if(type.equalsIgnoreCase("bm")){
                        //방문청소 옵션3 사진
                        images[i] = R.drawable.clean3_on;
                    }else {
                        //소독청소 옵션3 사진
                        images[i] = R.drawable.kill3_on;
                    }break;
                case "option4":
                    if(type.equalsIgnoreCase("bm")){
                        //방문청소 옵션4 사진 images[i] = R.drawable.bm_option4
                    }else {
                        //소독청소 옵션4 사진
                        images[i] = R.drawable.kill4_on;
                    }break;
            }
        }

        //list_item과 연결
        context = this;
        listView = (ListView)findViewById(R.id.sub06_option_list);
        listView.setAdapter(new CustomAdapter(this, options, images));
        //옵션리스트 크기 조절
        ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = optionlist.length*300;
        listView.setLayoutParams(layoutParams);

        t_1 = (TextView)findViewById(R.id.sub06_text03);
        t_2 = (TextView)findViewById(R.id.sub06_text08);
        t_3 = (TextView)findViewById(R.id.sub06_text09);
        textView1 = (TextView)findViewById(R.id.sub06_text05);
        textView1.setText(date+sub04_time);
        textView2 = (TextView)findViewById(R.id.sub06_text06);

        if(type.equalsIgnoreCase("bm")) {
            time = intent.getStringExtra("time");
            optiontime = intent.getStringExtra("optiontime");
            t_1.setText("청소 소요시간");
            t_2.setText("방문청소");
            t_3.setText("방문청소 주기");
            if(optiontime==null || optiontime=="0"){
                textView2.setText(time + "시간");
            }else{
                textView2.setText(time + "시간" + " + " + optiontime + "시간");
            }
        }
        else{
            size = intent.getStringExtra("size");
            t_1.setText("소독 면적");
            t_2.setText("소독");
            t_3.setText("소독 주기");
            textView2.setText(size);
        }
        checkBox = (CheckBox)findViewById(R.id.sub06_check);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkBox.isChecked()){
                    DialogSimple();
                }
            }
        });



        textView3 = (TextView)findViewById(R.id.sub06_text07);
        textView3.setText(addr+" "+addrdetail);
        textView4 = (TextView)findViewById(R.id.sub06_text10);
        textView4.setText(price);
        textView5 = (TextView)findViewById(R.id.sub06_text11);
        textView5.setText(jugi);
        textView7 = (TextView)findViewById(R.id.sub06_text13);
        StringTokenizer stringTokenizer = new StringTokenizer(price);
        price = stringTokenizer.nextToken("원");
        totalprice = String.valueOf(Integer.parseInt(price)+Integer.parseInt(optionprice));
        textView7.setText("총결제금액: "+totalprice);

    }

    //다이얼로그
    private void DialogSimple(){
        Dialog dialog = new Dialog(Sub06Activity.this);
        dialog.setContentView(R.layout.dialog);
        dialog.setTitle("규정 사항");
        dialog.show();
    }

    //임시 이동 (결제모듈)
    public void next_sub07(View v){
        Intent intent = new Intent();
        intent.putExtra("type", type);
        intent.putExtra("addr", addr);
        intent.putExtra("jugi", jugi);
        if(type.equalsIgnoreCase("bm")){
            intent.putExtra("time", time);
            intent.putExtra("optiontime", optiontime);
        }
        else{
            intent.putExtra("size", size);
        }
        intent.putExtra("price", price);
        intent.putExtra("optionprice", optionprice);
        intent.putExtra("date", date);
        intent.putExtra("addrdetail", addrdetail);
        intent.putExtra("totalprice", totalprice);
        intent.putExtra("optionlist", optionlist);


        if(checkBox.isChecked()){
            intent.setClass(Sub06Activity.this, PayDemoActivity.class);
            startActivityForResult(intent, 1);
        }else{
            Toast.makeText(this, "규정확인을 체크해주세요", Toast.LENGTH_SHORT).show();
        }
    }

    //뒤로가기 이벤트
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //ActionBar 메뉴 클릭에 대한 이벤트 처리
        finish();
        return super.onOptionsItemSelected(item);
    }
}
