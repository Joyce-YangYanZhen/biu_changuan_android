package com.noplugins.keepfit.android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.activity.user.Login2Activity;
import com.noplugins.keepfit.android.base.BaseActivity;
import com.noplugins.keepfit.android.callback.DialogCallBack;
import com.noplugins.keepfit.android.global.AppConstants;
import com.noplugins.keepfit.android.util.ActivityCollectorUtil;
import com.noplugins.keepfit.android.util.SpUtils;
import com.noplugins.keepfit.android.util.data.SharedPreferencesHelper;
import com.noplugins.keepfit.android.util.net.Network;
import com.noplugins.keepfit.android.util.ui.PopWindowHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ZhangHaoSafeActivity extends BaseActivity {
    @BindView(R.id.back_btn)
    ImageView back_btn;
    @BindView(R.id.exit_btn)
    LinearLayout exit_btn;
    @BindView(R.id.jujue_btn)
    LinearLayout jujue_btn;
    @BindView(R.id.phone_number_tv)
    TextView phone_number_tv;

    @Override
    public void initBundle(Bundle parms) {

    }

    @Override
    public void initView() {
        setContentLayout(R.layout.activity_zhang_hao_safe);
        ButterKnife.bind(this);
        isShowTitle(false);
        phone_number_tv.setText(SpUtils.getString(getApplicationContext(), AppConstants.PHONE));
    }

    @Override
    public void doBusiness(Context mContext) {

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(SpUtils.getInt(getApplicationContext(), AppConstants.FRAGMENT_SIZE)-1);
                finish();
            }
        });

        //修改密码
        jujue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ZhangHaoSafeActivity.this,UpdatePasswordActivity.class);
                startActivity(intent);
            }
        });

        //退出登录
        exit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopWindowHelper.public_tishi_pop(ZhangHaoSafeActivity.this, "提示", "是否退出登录？", "取消", "确定", new DialogCallBack() {
                    @Override
                    public void save() {
                        toLogin();
                    }

                    @Override
                    public void cancel() {

                    }
                });
            }
        });


    }

    private void toLogin(){
        Intent intent = new Intent(ZhangHaoSafeActivity.this, Login2Activity.class);
        SpUtils.putString(getApplicationContext(), AppConstants.TOKEN, "");
        SpUtils.putString(getApplicationContext(), AppConstants.PHONE, "");
        SpUtils.putString(getApplicationContext(), AppConstants.CHANGGUAN_NUM, "");
        SpUtils.putString(getApplicationContext(),AppConstants.COST,"");
//                                SpUtils.putString(applicationContext, AppConstants.CHANGGUAN_NUM, "GYM19091236750176")
        SpUtils.putString(getApplicationContext(), AppConstants.USER_NAME, "");
        SpUtils.putInt(getApplicationContext(), AppConstants.USER_TYPE, -1);
        SpUtils.putInt(getApplicationContext(), AppConstants.IS_TX, -1);
        SpUtils.putString(getApplicationContext(), AppConstants.USER_DENGJI, "");
        startActivity(intent);
        ActivityCollectorUtil.finishAllActivity();
    }

    @Override
    public void onBackPressed() {
        setResult(SpUtils.getInt(getApplicationContext(), AppConstants.FRAGMENT_SIZE)-1);
        finish();
    }
}
