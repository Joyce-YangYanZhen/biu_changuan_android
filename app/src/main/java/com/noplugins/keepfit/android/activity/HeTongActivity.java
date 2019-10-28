package com.noplugins.keepfit.android.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.base.BaseActivity;
import com.noplugins.keepfit.android.callback.DialogCallBack;
import com.noplugins.keepfit.android.util.ui.PopWindowHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HeTongActivity extends BaseActivity {
    @BindView(R.id.kaiqi_huiyuan_btn)
    LinearLayout kaiqi_huiyuan_btn;
    @BindView(R.id.xieyi_check_btn)
    CheckBox xieyi_check_btn;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.back_btn)
    ImageView back_btn;

    private boolean is_check;

    @Override
    public void initBundle(Bundle parms) {

    }

    @Override
    public void initView() {
        setContentLayout(R.layout.activity_he_tong);
        ButterKnife.bind(this);
        isShowTitle(false);
    }

    @Override
    public void doBusiness(Context mContext) {

        webView = (WebView) findViewById(R.id.webView);
        //自适应屏幕
        WebSettings webSettings = webView.getSettings();
        webSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放
        webSettings.setLoadWithOverviewMode(true);
        webView.loadUrl("file:///android_asset/hetong.html");

        xieyi_check_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    is_check = true;
                } else {
                    is_check = false;
                }
            }
        });
        kaiqi_huiyuan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (is_check) {
                    Intent intent = new Intent(HeTongActivity.this, BuyHuiYuanActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "请先勾选协议", Toast.LENGTH_SHORT).show();
                }
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_advice_pop();
            }
        });


    }

    @Override
    public void onBackPressed() {
        show_advice_pop();
    }

    private void show_advice_pop() {
        PopWindowHelper.public_tishi_pop(HeTongActivity.this, "温馨提示", "是否退出app？", "取消", "确定", new DialogCallBack() {
            @Override
            public void save() {
                Intent home=new Intent(Intent.ACTION_MAIN);
                home.addCategory(Intent.CATEGORY_HOME);
                startActivity(home);
            }

            @Override
            public void cancel() {

            }
        });
    }

}
