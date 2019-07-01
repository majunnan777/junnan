package chapter.android.aweme.ss.com.homework;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class jump extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jump);
        Button a1=findViewById(R.id.button6);
        final String str = getIntent().getStringExtra("name");
        a1.setText(str);

        a1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent m = new Intent(jump.this,Exercises3.class);
                startActivity(m);
                Toast.makeText(jump.this,str,Toast.LENGTH_LONG).show();
            }
        });
    }
}
