package chapter.android.aweme.ss.com.homework;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * 作业1：
 * 打印出Activity屏幕切换 Activity会执行什么生命周期？
 */
public class Exercises1 extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("------", "onCreate: ");
    }
    @Override
    protected void onStart(){
        super.onStart();
        Log.e("------","onStart: ");
    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.e("------", "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("------","onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("------", "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("------", "onDestroy: ");
    }
}
