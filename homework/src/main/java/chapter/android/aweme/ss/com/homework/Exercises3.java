package chapter.android.aweme.ss.com.homework;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 大作业:实现一个抖音消息页面,所需资源已放在res下面
 */
public class Exercises3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);
        final Button bu1=findViewById(R.id.button2);
        final Button bu2=findViewById(R.id.button3);
        final Button bu3=findViewById(R.id.button4);
        final Button bu4=findViewById(R.id.button5);

        bu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = bu1.getText().toString().trim();
                Intent i = new Intent(Exercises3.this,jump.class);
                i.putExtra("name",data);
                startActivity(i);
            }
        });

        bu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = bu2.getText().toString().trim();
                Intent i = new Intent(Exercises3.this,jump.class);
                i.putExtra("name",data);
                startActivity(i);
            }
        });

        bu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = bu3.getText().toString().trim();
                Intent i = new Intent(Exercises3.this,jump.class);
                i.putExtra("name",data);
                startActivity(i);
            }
        });

        bu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = bu4.getText().toString().trim();
                Intent i = new Intent(Exercises3.this,jump.class);
                i.putExtra("name",data);
                startActivity(i);
            }
        });


        ListView rv_list = findViewById(R.id.rv_list);
        ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < 30; i++) {
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("图片2",R.drawable.icon_girl);
            hashMap.put("文字1", "AABBC" + (i+1));
            hashMap.put("文字2", "5201314");
            hashMap.put("time", "昨天");
            arrayList.add(hashMap);
        }
        SimpleAdapter adapter = new SimpleAdapter(this,
                arrayList,
                R.layout.im_list_item,
                new String[]{ "图片2","文字1", "文字2", "time"},
                new int[]{R.id.iv_avatar,R.id.tv_title, R.id.tv_description, R.id.tv_time});
        rv_list.setAdapter(adapter);

        rv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Exercises3.this, "当前位置" + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Exercises3.this, chat.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
    }
}
