package com.example.chapter3.homework;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.HashMap;


public class PlaceholderFragment extends Fragment {

    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_placeholder, container, false);
        ListView rv_list = view.findViewById(R.id.rv_list);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_expandable_list_item_1,getData());
        rv_list.setAdapter(adapter);

        // TODO ex3-3: 修改 fragment_placeholder，添加 loading 控件和列表视图控件
        return view;
    }
    private String[] getData(){
        return new String[]{"韩寒","蔡静","卢本伟","五五开","战狼2","朝阳兄","虹桥一姐","犀利哥","王宝庆","系大大","搞踹踹","张三","李四","王五"};
   }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getView().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 这里会在 5s 后执行
                final LottieAnimationView animationView1 = view.findViewById(R.id.animation_view);
                ObjectAnimator alpha = ObjectAnimator.ofFloat(animationView1, "alpha", 1.0f, 0.0f);
                alpha.setInterpolator(new LinearInterpolator());
                AnimatorSet animatorSet1 = new AnimatorSet();


                final ListView listView1 = view.findViewById(R.id.rv_list);
                ObjectAnimator alpha2 = ObjectAnimator.ofFloat(listView1, "alpha", 0.0f, 1.0f);
                alpha2.setInterpolator(new LinearInterpolator());
                animatorSet1.playTogether(alpha,alpha2);
                animatorSet1.start();
                // TODO ex3-4：实现动画，将 lottie 控件淡出，列表数据淡入
            }
        }, 5000);
    }
}
