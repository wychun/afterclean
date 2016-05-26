package com.example.nuriapp.afterclean;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//상세주소 입력
public class Sub05Activity extends AppCompatActivity {
    String type;
    String addr;
    String jugi;
    String sub04_time;
    String price;
    String size;
    String time;
    String optiontime;
    String optionprice;
    String date;
    String addrdetail;
    String[] optionlist;
    String name;
    String email;
    String phone1;
    String phone2;
    String phone3;

    TextView textView;
    EditText editText;
    EditText editText1;
    EditText editText2;
    EditText editText3;
    EditText editText4;
    EditText editText5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub05);

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
        optionlist = intent.getStringArrayExtra("optionlist");
        if(type.equalsIgnoreCase("bm")) {
            time = intent.getStringExtra("time");
            optiontime = intent.getStringExtra("optiontime");
        }
        else{
            size = intent.getStringExtra("size");
        }

        textView = (TextView)findViewById(R.id.sub05_addr);
        textView.setText(addr);
        editText = (EditText)findViewById(R.id.sub05_addr_detail);
        editText1 = (EditText)findViewById(R.id.sub05_nm_detail);
        editText2 = (EditText)findViewById(R.id.sub05_email_detail);
        editText3 = (EditText)findViewById(R.id.sub05_phone_detail1);
        editText4 = (EditText)findViewById(R.id.sub05_phone_detail2);
        editText5 = (EditText)findViewById(R.id.sub05_phone_detail3);


    }

    //결제로 이동 (type 전송)
    public void next_sub06(View v) {
        //상세주소 저장
        addrdetail = editText.getText().toString();
        name = editText1.getText().toString();
        email = editText2.getText().toString();
        phone1 = editText3.getText().toString();
        phone2 = editText4.getText().toString();
        phone3 = editText5.getText().toString();

        Intent intent = new Intent(getApplicationContext(), Sub06Activity.class);
        intent.putExtra("type", type);
        intent.putExtra("type", type);
        intent.putExtra("addr", addr);
        intent.putExtra("jugi", jugi);
        intent.putExtra("sub04_time", sub04_time);
        intent.putExtra("optionlist", optionlist);
        if (type.equalsIgnoreCase("bm")) {
            intent.putExtra("time", time);
            intent.putExtra("optiontime", optiontime);
        } else {
            intent.putExtra("size", size);
        }

        if (addrdetail.getBytes().length <= 0) {
            Toast.makeText(this, "상세주소를 입력해 주세요", Toast.LENGTH_SHORT).show();
        } else {
            intent.putExtra("price", price);
            intent.putExtra("optionprice", optionprice);
            intent.putExtra("date", date);
            intent.putExtra("addrdetail", addrdetail);
            intent.putExtra("name", name);
            intent.putExtra("email", email);
            intent.putExtra("phone1", phone1);
            intent.putExtra("phone2", phone2);
            intent.putExtra("phone3", phone3);

            startActivity(intent);
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