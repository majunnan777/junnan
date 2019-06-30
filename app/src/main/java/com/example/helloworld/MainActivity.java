package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button a = findViewById(R.id.button);
        final TextView z = findViewById(R.id.textView);
        final TextView n = findViewById(R.id.textView2);
        Switch d = findViewById(R.id.switch1);
        final SeekBar e = findViewById(R.id.seekBar3);

        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                z.setText("登录成功！");
            }
        });

        d.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                z.setText("隐身成功！");
            }
        });

        e.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int seekBar3, boolean fromUser) {
                n.setText("透明度" + Integer.toString(seekBar3));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.e("开始调节", "onStartTrackingTouch: ");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.e("停止调节", "onStopTrackingTouch: ");
            }
        });

    }
}
