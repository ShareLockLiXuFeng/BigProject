package com.sanbafule.bigproject;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sanbafule.bigproject.annotation.OnClick;

import butterknife.BindView;


public class MainActivity extends Activity {


    @BindView(R.id.tv_tv)
    TextView tvTv;
    @BindView(R.id.tv_tv1)
    TextView tvTv1;
    @BindView(R.id.tv_tv2)
    TextView tvTv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    @OnClick({R.id.tv_tv, R.id.tv_tv1, R.id.tv_tv2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_tv:
                break;
            case R.id.tv_tv1:
                break;
            case R.id.tv_tv2:
                break;

        }
    }
}
