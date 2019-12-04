package com.noplugins.keepfit.android.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.adapter.SelectChangguanAdapter;
import com.noplugins.keepfit.android.base.BaseActivity;
import com.noplugins.keepfit.android.bean.DictionaryeBean;
import com.noplugins.keepfit.android.util.net.Network;
import com.noplugins.keepfit.android.util.net.entity.Bean;
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriber;
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;

public class SelectChangGuanActivity extends BaseActivity {
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.back_btn)
    ImageView back_btn;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    List<DictionaryeBean> select_types = new ArrayList<>();
    SelectChangguanAdapter selectChangguanAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initBundle(Bundle parms) {

    }

    @Override
    public void initView() {
        setContentLayout(R.layout.activity_select_chang_guan);
        ButterKnife.bind(this);

    }

    @Override
    public void doBusiness(Context mContext) {
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //获取场馆列表
        get_changguan_list();
        //禁用加载
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                get_changguan_list();
            }
        });
    }

    private void get_changguan_list() {
        Map<String, Object> params = new HashMap<>();
        params.put("object", "9");
        Subscription subscription = Network.getInstance("获取类型", this)
                .get_types(params,
                        new ProgressSubscriber<>("获取类型", new SubscriberOnNextListener<Bean<List<DictionaryeBean>>>() {
                            @Override
                            public void onNext(Bean<List<DictionaryeBean>> result) {
                                if (select_types.size() > 0) {
                                    select_types.clear();
                                }
                                select_types.addAll(result.getData());
                                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                                recycler_view.setLayoutManager(layoutManager);
                                selectChangguanAdapter = new SelectChangguanAdapter(select_types, getApplicationContext());
                                recycler_view.setAdapter(selectChangguanAdapter);
                                refreshLayout.finishRefresh(true);
                            }

                            @Override
                            public void onError(String error) {
                                refreshLayout.finishRefresh(true);

                            }
                        }, this, true));
    }
}
