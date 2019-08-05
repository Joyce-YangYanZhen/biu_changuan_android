package com.noplugins.keepfit.android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.base.BaseActivity;
import com.noplugins.keepfit.android.entity.AddClassEntity;
import com.noplugins.keepfit.android.entity.ClassEntity;
import com.noplugins.keepfit.android.entity.MaxPeopleEntity;
import com.noplugins.keepfit.android.util.data.SharedPreferencesHelper;
import com.noplugins.keepfit.android.util.net.Network;
import com.noplugins.keepfit.android.util.net.entity.Bean;
import com.noplugins.keepfit.android.util.net.progress.GsonSubscriberOnNextListener;
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriberNew;
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener;
import com.othershe.calendarview.utils.CalendarUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.qqtheme.framework.wheelpicker.DatePicker;
import cn.qqtheme.framework.wheelpicker.TimePicker;
import cn.qqtheme.framework.wheelview.annotation.DateMode;
import cn.qqtheme.framework.wheelview.annotation.TimeMode;
import cn.qqtheme.framework.wheelview.contract.OnDateSelectedListener;
import cn.qqtheme.framework.wheelview.contract.OnTimeSelectedListener;
import cn.qqtheme.framework.wheelview.entity.DateEntity;
import cn.qqtheme.framework.wheelview.entity.TimeEntity;
import lib.demo.spinner.MaterialSpinner;
import okhttp3.RequestBody;

import static com.zhy.http.okhttp.log.LoggerInterceptor.TAG;

public class AddClassItemActivity extends BaseActivity {
    @BindView(R.id.back_btn)
    ImageView back_btn;
    @BindView(R.id.add_class_teacher_btn)
    LinearLayout add_class_teacher_btn;
    @BindView(R.id.tuanke_type_spinner)
    MaterialSpinner tuanke_type_spinner;
    @BindView(R.id.xunlian_type_spinner)
    MaterialSpinner xunlian_type_spinner;
    @BindView(R.id.nandu_type_spinner)
    MaterialSpinner nandu_type_spinner;
    @BindView(R.id.room_type_spinner)
    MaterialSpinner room_type_spinner;
    @BindView(R.id.class_xunhuan_type_spinner)
    MaterialSpinner class_xunhuan_type_spinner;
    @BindView(R.id.time1_edit)
    TextView time1_edit;
    @BindView(R.id.time2_edit)
    TextView time2_edit;
    @BindView(R.id.select_date_layout)
    LinearLayout select_date;
    @BindView(R.id.year_tv)
    TextView year_tv;
    @BindView(R.id.month_tv)
    TextView month_tv;
    @BindView(R.id.date_tv)
    TextView date_tv;
    @BindView(R.id.edit_class_name)
    EditText edit_class_name;
    @BindView(R.id.edit_tuanke_renshu_number)
    EditText edit_tuanke_renshu_number;
    @BindView(R.id.edit_class_jieshao)
    EditText edit_class_jieshao;
    @BindView(R.id.edit_shihe_renqun)
    EditText edit_shihe_renqun;
    @BindView(R.id.edit_zhuyi_shixiang)
    EditText edit_zhuyi_shixiang;
    @BindView(R.id.edit_price_number)
    EditText edit_price_number;

    private String select_changguan_type;
    private String select_target_type="燃脂";
    private String select_nandu_type = "容易";
    private String select_room_type ="有氧操房";
    private String select_xunhuan_type = "一周";
    private TimePicker picker;
    private DatePicker datePicker;
    //获取当前的日期
    private int[] cDate = CalendarUtil.getCurrentDate();
    private int page = 1;
    private List<ClassEntity.DataBean> dataBeans = new ArrayList<>();
    private int enable_max_people;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initBundle(Bundle parms) {

    }

    @Override
    public void initView() {
        setContentLayout(R.layout.activity_add_class_item);
        ButterKnife.bind(this);
        isShowTitle(false);
    }

    @Override
    public void doBusiness(Context mContext) {
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        add_class_teacher_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (check_value()) {//如果所有参数不为空，请求网络接口
//                    add_class();
//                }else{
//                    return;
//                }

                Intent intent = new Intent(AddClassItemActivity.this, YaoQingTeacherActivity.class);
                startActivity(intent);
            }
        });

        select_tuanke_type();

        select_target_type();

        select_nandu_type();

        select_room_type();

        select_xunhuan_type();

        select_time();

        search_room_people(true);//获取最大人数
    }

    private void add_class() {
        Map<String, Object> params = new HashMap<>();
        String gymAreaNum;
        if ("".equals(SharedPreferencesHelper.get(this, "changguan_number", "").toString())) {
            gymAreaNum = "";
        } else {
            gymAreaNum = SharedPreferencesHelper.get(this, "changguan_number", "").toString();
        }
        //params.put("gymAreaNum", gymAreaNum);//场馆编号
        params.put("gym_area_num", "GYM19072138381319");//场馆编号
        params.put("course_name", edit_class_name.getText().toString());//团课名称
        if (select_target_type.equals("燃脂")) {//训练目标  1燃脂2塑形3体态改善
            params.put("target", "1");
        } else if (select_target_type.equals("塑形")) {
            params.put("target", "2");
        } else if (select_target_type.equals("减压舒缓")) {
            params.put("target", "3");
        } else {//"体态改善"
            params.put("target", "4");
        }
        if (select_nandu_type.equals("容易")) {//选择难度
            params.put("difficulty", "1");
        } else if (select_nandu_type.equals("中等")) {
            params.put("difficulty", "2");
        } else {//困难
            params.put("difficulty", "3");
        }
        if (select_room_type.equals("有氧操房")) {
            params.put("type", "1");
        } else if (select_room_type.equals("动态单车")) {
            params.put("type", "2");
        } else {
            params.put("type", "3");
        }

        if (select_target_type.equals("单车")) {
            params.put("class_type", "1");
        } else if (select_target_type.equals("瑜伽")) {
            params.put("class_type", "2");
        } else if (select_target_type.equals("普拉提")) {
            params.put("class_type", "3");
        } else if (select_target_type.equals("拳击/搏击")) {
            params.put("class_type", "4");
        } else if (select_target_type.equals("舞蹈")) {
            params.put("class_type", "5");
        } else if (select_target_type.equals("功能性课程")) {
            params.put("class_type", "6");
        } else {//儿童体适能
            params.put("class_type", "7");
        }

        params.put("class_type", "1");//团课类型：1单车2瑜伽3普拉提4拳击5舞蹈6功能性7儿童

        params.put("class_type", "1");//团课类型：1单车2瑜伽3普拉提4拳击5舞蹈6功能性7儿童
        params.put("course_type", "1");//1团课，2私教，3健身
        params.put("max_num", edit_tuanke_renshu_number.getText().toString());//人数限制

        params.put("start_time",
                year_tv.getText().toString()+"-"+month_tv.getText().toString()+"-"+date_tv.getText().toString()+" "
                        +time1_edit.getText().toString());//开始时间
        params.put("end_time", year_tv.getText().toString()+"-"+month_tv.getText().toString()+"-"+date_tv.getText().toString()+" "
                +time2_edit.getText().toString());//结束时间

        if(select_xunhuan_type.equals("一周")){
            params.put("loop_cycle", "1");//循环周数
        }else if(select_xunhuan_type.equals("二周")){
            params.put("loop_cycle", "2");//循环周数
        }else if(select_xunhuan_type.equals("三周")){
            params.put("loop_cycle", "3");//循环周数
        }else{
            params.put("loop_cycle", "4");//循环周数
        }
        params.put("course_des", edit_class_jieshao.getText().toString());//课程介绍
        params.put("tips", edit_zhuyi_shixiang.getText().toString());//注意事项
        params.put("price", edit_price_number.getText().toString());//注意事项
        params.put("suit_person", edit_shihe_renqun.getText().toString());//适合人群
        Gson gson = new Gson();
        String json_params = gson.toJson(params);
        String json = new Gson().toJson(params);//要传递的json
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
        Log.e(TAG, "添加课程：" + json_params);
        subscription = Network.getInstance("添加课程", this).
                add_class(requestBody, new ProgressSubscriberNew<>(AddClassEntity.class, new GsonSubscriberOnNextListener<AddClassEntity>() {
                    @Override
                    public void on_post_entity(AddClassEntity entity, String s) {
                        Log.e("添加课程成功", entity + "添加课程成功" + s);

                    }
                }, new SubscriberOnNextListener<Bean<Object>>() {
                    @Override
                    public void onNext(Bean<Object> result) {

                    }

                    @Override
                    public void onError(String error) {
                        Log.e("添加课程失败", "添加课程失败:" + error);

                    }
                }, this, true));
    }

    private boolean check_value() {
        if (TextUtils.isEmpty(edit_class_name.getText())) {
            Toast.makeText(this, R.string.alert_dialog_tishi16, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(edit_tuanke_renshu_number.getText())) {
            Toast.makeText(this, R.string.alert_dialog_tishi17, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(edit_class_jieshao.getText())) {
            Toast.makeText(this, R.string.alert_dialog_tishi18, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(edit_shihe_renqun.getText())) {
            Toast.makeText(this, R.string.alert_dialog_tishi19, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(edit_zhuyi_shixiang.getText())) {
            Toast.makeText(this, R.string.alert_dialog_tishi20, Toast.LENGTH_SHORT).show();
            return false;
        } else if(Integer.valueOf(edit_tuanke_renshu_number.getText().toString())>enable_max_people){
            Toast.makeText(this, R.string.alert_dialog_tishi21, Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }

    }

    private void select_time() {
        year_tv.setText("" + cDate[0]);
        if (cDate[1] <= 9) {
            month_tv.setText("0" + cDate[1]);//显示当前月份
        } else {
            month_tv.setText(cDate[1]);//显示当前月份
        }
        if (cDate[2] <= 9) {
            month_tv.setText("0" + cDate[2]);//显示当前月份
        } else {
            month_tv.setText(cDate[2]);//显示当前月份
        }

        select_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_date();
            }
        });
        time1_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time_check(time1_edit);
            }
        });

        time2_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time_check(time2_edit);
            }
        });
    }

    private void select_date() {
        DateEntity today = DateEntity.today();
        datePicker = new DatePicker(this, DateMode.YEAR_MONTH_DAY);
        datePicker.setRange(today, new DateEntity(2022, 12, 31));
        datePicker.setDefaultValue(today);
        datePicker.showAtBottom();
        datePicker.setOnDateSelectedListener(new OnDateSelectedListener() {
            @Override
            public void onItemSelected(int year, int month, int day) {
                year_tv.setText(year + "");
                if (month <= 9) {
                    month_tv.setText("0" + month);
                } else {
                    month_tv.setText("" + month);
                }
                if (day <= 9) {
                    date_tv.setText("0" + day);
                } else {
                    date_tv.setText("" + day);

                }
            }
        });
    }

    private void time_check(TextView textView) {
        picker = new TimePicker(this, TimeMode.HOUR_24);
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        picker.setDefaultValue(new TimeEntity(hour, minute));
        picker.showAtBottom();
        picker.setOnTimeSelectedListener(new OnTimeSelectedListener() {
            @Override
            public void onItemSelected(int hour, int minute, int second) {
                if (minute <= 9) {
                    textView.setText(hour + ":0" + minute);
                } else {
                    textView.setText(hour + ":" + minute);
                }
            }
        });
    }

    private void select_xunhuan_type() {
        String[] typeArrays = getResources().getStringArray(R.array.xunhuan_types);
        class_xunhuan_type_spinner.setItems(typeArrays);
        class_xunhuan_type_spinner.setSelectedIndex(0);
        class_xunhuan_type_spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                select_xunhuan_type = item;
            }
        });
        class_xunhuan_type_spinner.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override
            public void onNothingSelected(MaterialSpinner spinner) {
                spinner.getSelectedIndex();
            }
        });
    }

    private void select_room_type() {
        String[] typeArrays = getResources().getStringArray(R.array.gongneng_types);
        room_type_spinner.setItems(typeArrays);
        room_type_spinner.setSelectedIndex(0);
        room_type_spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                select_room_type = item;
                //查询每个房间最大能容纳的人数
                search_room_people(false);
            }
        });
        room_type_spinner.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override
            public void onNothingSelected(MaterialSpinner spinner) {
                spinner.getSelectedIndex();
            }
        });
    }

    private void search_room_people(boolean is_first) {
        Map<String, Object> params = new HashMap<>();
        params.put("gymAreaNum", "GYM19072138381319");//场馆编号
        if (is_first) {
            params.put("PlaceType", 1);
        } else {
            if (select_room_type.equals("有氧操房")) {
                params.put("PlaceType", 1);
            } else if (select_room_type.equals("动态单车")) {
                params.put("PlaceType", 2);
            } else {
                params.put("PlaceType", 3);
            }
        }
        Gson gson = new Gson();
        String json_params = gson.toJson(params);
        Log.e(TAG, "获取最大人数参数：" + json_params);
        String json = new Gson().toJson(params);//要传递的json
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
        subscription = Network.getInstance("获取最大人数", this)
                .get_max_num(requestBody, new ProgressSubscriberNew<>(MaxPeopleEntity.class, new GsonSubscriberOnNextListener<MaxPeopleEntity>() {
                    @Override
                    public void on_post_entity(MaxPeopleEntity entity, String s) {
                        Log.e("获取最大人数成功", entity.getData() + "获取最大人数成功" + s);
                        enable_max_people = entity.getData();
                    }
                }, new SubscriberOnNextListener<Bean<Object>>() {
                    @Override
                    public void onNext(Bean<Object> result) {
                    }
                    @Override
                    public void onError(String error) {
                        Log.e("获取最大人数失败", "获取最大人数失败:" + error);
                    }
                }, this, true));
    }

    private void select_nandu_type() {
        String[] typeArrays = getResources().getStringArray(R.array.nandu_types);
        nandu_type_spinner.setItems(typeArrays);
        nandu_type_spinner.setSelectedIndex(0);
        nandu_type_spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                select_nandu_type = item;
            }
        });
        nandu_type_spinner.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override
            public void onNothingSelected(MaterialSpinner spinner) {
                spinner.getSelectedIndex();
            }
        });
    }

    private void select_target_type() {
        String[] typeArrays = getResources().getStringArray(R.array.tuanke_types);
        xunlian_type_spinner.setItems(typeArrays);
        xunlian_type_spinner.setSelectedIndex(0);
        xunlian_type_spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                select_target_type = item;
            }
        });
        xunlian_type_spinner.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override
            public void onNothingSelected(MaterialSpinner spinner) {
                spinner.getSelectedIndex();
            }
        });
    }

    private void select_tuanke_type() {
        String[] typeArrays = getResources().getStringArray(R.array.target_types);
        tuanke_type_spinner.setItems(typeArrays);
        tuanke_type_spinner.setSelectedIndex(0);
        tuanke_type_spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                select_target_type = item;
            }
        });
        tuanke_type_spinner.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override
            public void onNothingSelected(MaterialSpinner spinner) {
                spinner.getSelectedIndex();
            }
        });
    }
}