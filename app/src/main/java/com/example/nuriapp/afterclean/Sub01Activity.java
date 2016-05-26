package com.example.nuriapp.afterclean;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//지역선택
public class Sub01Activity extends AppCompatActivity {
    String type;
    String addr = "서울특별시";//default 서울특별시
    String addr2 = "";
    String[] juso;
    String[] code;

    ListView listView1, listView2;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub01);

        //뒤로가기버튼
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //이전 페이지에서 type값 받아오기
        Intent intent = getIntent();
        type = intent.getExtras().getString("type");
        Log.d("type", type);

 /*       final RadioGroup rg = (RadioGroup)findViewById(R.id.sub01_juso2);
        Button b = (Button)findViewById(R.id.button_sub02);

        b.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int id = rg.getCheckedRadioButtonId();
                //getCheckedRadioButtonId() 의 리턴값은 선택된 RadioButton 의 id 값.
                RadioButton rb = (RadioButton) findViewById(id);

                addr2 = rb.getText().toString();
                Toast.makeText(getApplicationContext(), addr2+"가 선택되었습니다.",Toast.LENGTH_LONG).show();
            } // end onClick()
        });  // end Listener*/
/*
        시도/구군 모두 받아올 때
        listView1 = (ListView)findViewById(R.id.sub01_juso1);
        listView2 = (ListView)findViewById(R.id.sub01_juso2);
        textView = (TextView)findViewById(R.id.sub01_text);
        //arrays.xml에 있는 배열 값 저장
        juso = getResources().getStringArray(R.array.juso);
        code = getResources().getStringArray(R.array.juso_code);
        //listview1 생성
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, juso);
        listView1.setAdapter(adapter);

        //listview2 생성
        //기본 값
        new Http().execute("11");

        //listview1 클릭 addr변경
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                addr = juso[position];
                new Http().execute(code[position]);

            }
        });

        //listview2 클릭 addr추가
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                addr2 = ((TextView) view).getText().toString();
                textView.setText(addr + " "+addr2);
            }
        });*/


    };



    //기상청 행정코드 조회 gubun = "top"이면 시도, "mdl.시도코드"이면 시군구, "leaf.시군구코드"이면 동
    //시군구 목록
  /*  class Http extends AsyncTask<String, Void, String[]> {
        HttpURLConnection conn;
        URL obj;
        @Override
        protected String[] doInBackground(String... params) {
            String[] result = null;
            try{
                String gubun = "mdl."+params[0];

                String url = "http://www.kma.go.kr/DFSROOT/POINT/DATA/"+gubun+".json.txt";
                obj = new URL(url);
                conn = (HttpURLConnection)obj.openConnection();

                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                InputStream inputStream = conn.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                StringBuffer response = new StringBuffer();
                while ((line = bufferedReader.readLine()) != null){
                    response.append(line);
                    response.append('\r');
                }
                bufferedReader.close();
                JSONArray jsonArray = new JSONArray(response.toString());

                result = new String[jsonArray.length()];
                for(int i=0; i<jsonArray.length(); i++){
                   result[i] = jsonArray.getJSONObject(i).getString("value");
                }

            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if(conn != null){
                    conn.disconnect();
                }
            }
            return result;
        }

        @Override
        protected void onPostExecute(String[] result){
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Sub01Activity.this, android.R.layout.simple_list_item_1, result);
            listView2.setAdapter(arrayAdapter);
            super.onPostExecute(result);
        }
    }*/

    //청소주기, 소독주기로 이동(type값으로 구분), addr값 전송
    public void next_sub02(View v){
        //주소 받아오기
        final RadioGroup rg = (RadioGroup)findViewById(R.id.sub01_juso2);
        Button b = (Button)findViewById(R.id.button_sub02);
        int id = rg.getCheckedRadioButtonId();
        //getCheckedRadioButtonId() 의 리턴값은 선택된 RadioButton 의 id 값.
        RadioButton rb = (RadioButton) findViewById(id);

        addr2 = rb.getText().toString();
        Toast.makeText(getApplicationContext(), addr2+"가 선택되었습니다.",Toast.LENGTH_LONG).show();
        if (type.equalsIgnoreCase("bm")) {
            Intent intent = new Intent(getApplicationContext(), Sub02Activity_bm.class);
            intent.putExtra("addr","서울시 "+addr2);
            intent.putExtra("type", type);
            startActivity(intent);
        }
        else if (type.equalsIgnoreCase("sd")){
            Intent intent = new Intent(getApplicationContext(), Sub02Activity_sd.class);
            intent.putExtra("addr","서울시 "+addr2);
            intent.putExtra("type", type);
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

