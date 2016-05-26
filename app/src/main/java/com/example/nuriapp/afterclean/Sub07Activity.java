package com.example.nuriapp.afterclean;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.StringTokenizer;

public class Sub07Activity extends AppCompatActivity {
    String type;
    String addr;
    String jugi;
    String price;
    String time;
    String optiontime;
    String size;
    String optionprice;
    String date;
    String addrdetail;
    String totalprice;
    String[] optionlist;

    TextView textView1, textView2, textView3, textView4 , textView5, textView6, textView7 ,textView8, textView9;
    ListView listView;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub07);

        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        addr = intent.getStringExtra("addr");
        jugi = intent.getStringExtra("jugi");
        price = intent.getStringExtra("price");
        optionprice = intent.getStringExtra("optionprice");
        date = intent.getStringExtra("date");
        addrdetail = intent.getStringExtra("addrdetail");
        totalprice = intent.getStringExtra("totalprice");
        optionlist = (String[])intent.getStringArrayExtra("optionlist");
        Log.d("옵션 뭐잇냐", optionlist.toString());

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
                        //방문청소 옵션1 사진 images[i] = R.drawable.bm_option1
                        images[i] = R.drawable.clean1_on;
                    }else {
                        //소독청소 옵션1 사진 images[i] = R.drawable.sd_option1
                        images[i] = R.drawable.kill1_on;
                    }break;
                case "option2":
                    if(type.equalsIgnoreCase("bm")){
                        //방문청소 옵션2 사진 images[i] = R.drawable.bm_option2
                        images[i] = R.drawable.clean2_on;
                    }else {
                        //소독청소 옵션2 사진 images[i] = R.drawable.sd_option2
                        images[i] = R.drawable.kill2_on;
                    }break;
                case "option3":
                    if(type.equalsIgnoreCase("bm")){
                        //방문청소 옵션3 사진 images[i] = R.drawable.bm_option3
                        images[i] = R.drawable.clean3_on;
                    }else {
                        //소독청소 옵션3 사진 images[i] = R.drawable.sd_option3
                        images[i] = R.drawable.kill3_on;
                    }break;
                case "option4":
                    if(type.equalsIgnoreCase("bm")){
                        //방문청소 옵션4 사진 images[i] = R.drawable.bm_option4
                    }else {
                        //소독청소 옵션4 사진 images[i] = R.drawable.sd_option4
                        images[i] = R.drawable.kill4_on;
                    }break;
            }
        }

        //list_item과 연결
        context = this;
        listView = (ListView)findViewById(R.id.sub07_option_list);
        listView.setAdapter(new CustomAdapter(this, options, images));

        //옵션 개수에 따른 listview 높이 동적으로 조절
        ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = optionlist.length*300;
        listView.setLayoutParams(layoutParams);

        //텍스트 set
        textView1 = (TextView)findViewById(R.id.sub07_text03);
        textView2 = (TextView)findViewById(R.id.sub07_text04);
        textView3 = (TextView)findViewById(R.id.sub07_text05);
        textView4 = (TextView)findViewById(R.id.sub07_text06);
        textView5 = (TextView)findViewById(R.id.sub07_text08);
        textView6 = (TextView)findViewById(R.id.sub07_text09);
        textView7 = (TextView)findViewById(R.id.sub07_text10);
        textView8 = (TextView)findViewById(R.id.sub07_text11);
        textView9 = (TextView)findViewById(R.id.sub07_text13);
        if(type.equalsIgnoreCase("bm")) {
            time = intent.getStringExtra("time");
            optiontime = intent.getStringExtra("optiontime");
            textView2.setText("청소 소요시간");
            textView4.setText(time + "시간" + " + " + optiontime + "시간");
            textView5.setText("방문청소");
            textView6.setText("방문청소 주기");
        }
        else{
            size = intent.getStringExtra("size");
            textView2.setText("소독 면적");
            textView4.setText(size);
            textView5.setText("소독");
            textView6.setText("소독 주기");
        }
        textView3.setText(date);
        textView7.setText(price);
        textView8.setText(jugi);
        StringTokenizer stringTokenizer = new StringTokenizer(price);
        price = stringTokenizer.nextToken("원");
        totalprice = String.valueOf(Integer.parseInt(price)+Integer.parseInt(optionprice));
        textView9.setText("총결제금액: "+totalprice);

        //뒤로가기버튼
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    //home으로 이동
    public void next_home(View v){
        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);

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
