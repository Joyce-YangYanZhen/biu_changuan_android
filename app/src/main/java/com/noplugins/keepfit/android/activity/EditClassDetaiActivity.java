package com.noplugins.keepfit.android.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.activity.use.ClassItemEditActivity;
import com.noplugins.keepfit.android.base.BaseActivity;
import com.noplugins.keepfit.android.util.screen.KeyboardUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditClassDetaiActivity extends BaseActivity {
    @BindView(R.id.back_btn)
    LinearLayout back_btn;
    @BindView(R.id.done_btn)
    LinearLayout done_btn;
    @BindView(R.id.edit_tv)
    EditText edit_tv;
    @BindView(R.id.number_tv)
    TextView number_tv;
    @BindView(R.id.title_tv)
    TextView title_tv;
    @BindView(R.id.title_tv_top)
    TextView title_tv_top;

    String type = "";
    String infoType = "";
    int input_max_umber = 0;
    String class_content = "";
    String class_shihe_renqun = "";
    String class_zhuyi_shixiang = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initBundle(Bundle parms) {
        type = getIntent().getStringExtra("type");
        if (getIntent().getStringExtra("infoType") != null) {
            infoType = getIntent().getStringExtra("infoType");
        }
        if (getIntent().getStringExtra("class_content") != null) {
            class_content = getIntent().getStringExtra("class_content");
        }
        if (getIntent().getStringExtra("class_shihe_renqun") != null) {
            class_shihe_renqun = getIntent().getStringExtra("class_shihe_renqun");
        }
        if (getIntent().getStringExtra("class_zhuyi_shixiang") != null) {
            class_zhuyi_shixiang = getIntent().getStringExtra("class_zhuyi_shixiang");
        }

    }

    @Override
    public void initView() {
        setContentLayout(R.layout.activity_edit_class_detai);
        ButterKnife.bind(this);
        isShowTitle(false);
    }

    @Override
    public void doBusiness(Context mContext) {
        if (type.equals("class_content")) {//课程内容
            title_tv.setText(getResources().getText(R.string.tv74));
            title_tv_top.setText(getResources().getText(R.string.edit_hint23));
            input_max_umber = 300;
            if (!TextUtils.isEmpty(class_content)) {
                edit_tv.setText(class_content);
                number_tv.setText(class_content.length() + "/" + input_max_umber);
            } else {
                number_tv.setText("0/" + input_max_umber);
            }

        } else if (type.equals("shihe_renqun")) {//适合人群
            title_tv.setText(getResources().getText(R.string.tv75));
            title_tv_top.setText(getResources().getText(R.string.edit_hint24));
            input_max_umber = 150;
            if (!TextUtils.isEmpty(class_shihe_renqun)) {
                edit_tv.setText(class_shihe_renqun);
                number_tv.setText(class_shihe_renqun.length() + "/" + input_max_umber);
            } else {
                number_tv.setText("0/" + input_max_umber);
            }

        } else if (type.equals("zhuyi_shixiang")) {//注意事项
            title_tv_top.setText(getResources().getText(R.string.edit_hint25));
            title_tv.setText(getResources().getText(R.string.tv76));
            input_max_umber = 300;
            if (!TextUtils.isEmpty(class_zhuyi_shixiang)) {
                edit_tv.setText(class_zhuyi_shixiang);
                number_tv.setText(class_zhuyi_shixiang.length() + "/" + input_max_umber);
            } else {
                number_tv.setText("0/" + input_max_umber);
            }
        }
        edit_tv.setMaxEms(input_max_umber);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(edit_tv.getText())) {
                    if (infoType.equals("edit")) {
                        //
                        if (type.equals("class_content")) {//课程内容
                            ClassItemEditActivity.Companion.setClass_jianjie_tv_edit(edit_tv.getText().toString());

                        } else if (type.equals("shihe_renqun")) {//适合人群

                            ClassItemEditActivity.Companion.setShihe_renqun_tv_edit(edit_tv.getText().toString());

                        } else if (type.equals("zhuyi_shixiang")) {//注意事项
                            ClassItemEditActivity.Companion.setZhuyi_shixiang_tv_edit(edit_tv.getText().toString());

                        }
                    } else {
                        if (type.equals("class_content")) {//课程内容
                            AddClassItemActivity.class_jianjie_tv = edit_tv.getText().toString();

                        } else if (type.equals("shihe_renqun")) {//适合人群
                            AddClassItemActivity.shihe_renqun_tv = edit_tv.getText().toString();

                        } else if (type.equals("zhuyi_shixiang")) {//注意事项
                            AddClassItemActivity.zhuyi_shixiang_tv = edit_tv.getText().toString();

                        }
                    }

                }
                //影藏键盘
                KeyboardUtils.hideSoftKeyboard(EditClassDetaiActivity.this);
                finish();
            }
        });

        edit_tv.addTextChangedListener(textWatcher);
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (!TextUtils.isEmpty(edit_tv.getText())) {
                number_tv.setText(edit_tv.getText().length() + "/" + input_max_umber);
            }
        }
    };
}
