package com.noplugins.keepfit.android.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.google.gson.Gson;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.adapter.SystemMessageAdapter;
import com.noplugins.keepfit.android.entity.MessageEntity;
import com.noplugins.keepfit.android.global.AppConstants;
import com.noplugins.keepfit.android.util.SpUtils;
import com.noplugins.keepfit.android.util.eventbus.MessageEvent;
import com.noplugins.keepfit.android.util.net.Network;
import com.noplugins.keepfit.android.util.net.entity.Bean;
import com.noplugins.keepfit.android.util.net.progress.GsonSubscriberOnNextListener;
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriberNew;
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.RequestBody;
import rx.Subscription;

import static com.zhy.http.okhttp.log.LoggerInterceptor.TAG;


public class SystemMessageFragment extends Fragment {
    @BindView(R.id.xrefreshview)
    XRefreshView xrefreshview;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    private View view;
    private LinearLayoutManager layoutManager;
    private SystemMessageAdapter systemMessageAdapter;
    private List<MessageEntity.MessageBean> messageBeans = new ArrayList<>();
    private int page = 1;
    private int maxPage;
    private boolean is_not_more;

    public static SystemMessageFragment newInstance(String title) {
        SystemMessageFragment fragment = new SystemMessageFragment();
        Bundle args = new Bundle();
        args.putString("home_fragment_title", title);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_system_message, container, false);
            ButterKnife.bind(this, view);//绑定黄牛刀

            initView();
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();


    }

    private void initView() {
        initMessageDate();//获取消息列表

    }

    private void initMessageDate() {
        Map<String, Object> params = new HashMap<>();
        params.put("gymAreaNum", SpUtils.getString(getActivity(), AppConstants.CHANGGUAN_NUM));//场馆编号
        params.put("page", page);//场馆编号
        params.put("type", "2");//场馆编号
        Gson gson = new Gson();
        String json_params = gson.toJson(params);
        String json = new Gson().toJson(params);//要传递的json
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
        Log.e(TAG, "系统消息参数：" + json_params);
        Subscription subscription = Network.getInstance("系统消息列表", getActivity())
                .zhanghu_message_list(requestBody, new ProgressSubscriberNew<>(MessageEntity.class, new GsonSubscriberOnNextListener<MessageEntity>() {
                    @Override
                    public void on_post_entity(MessageEntity entity, String s) {
                        maxPage = entity.getMaxPage();
                        if (page == 1) {//表示刷新
                            messageBeans.addAll(entity.getMessage());
                            set_list_resource(entity.getMessage(), entity.getNoRead());
                        } else {
                            if (page <= maxPage) {//表示加载还有数据
                                is_not_more = false;
                                messageBeans.addAll(entity.getMessage());
                                systemMessageAdapter.notifyDataSetChanged();

                            } else {//表示没有更多数据了
                                is_not_more = true;
                                messageBeans.addAll(entity.getMessage());
                                systemMessageAdapter.notifyDataSetChanged();
                            }
                        }

                        boolean is_read = entity.isRead();//有已读消息，false是未读消息，通知MessageFragment更新红点信息
                        Intent intent = new Intent("update_message_read_status");
                        intent.putExtra("is_read", "" + is_read);
                        intent.putExtra("type_number", "2");
                        LocalBroadcastManager.getInstance(Objects.requireNonNull(getActivity())).sendBroadcast(intent);
                    }
                }, new SubscriberOnNextListener<Bean<Object>>() {
                    @Override
                    public void onNext(Bean<Object> result) {

                    }

                    @Override
                    public void onError(String error) {
                        Log.e("系统消息列表失败", "消息列表失败:" + error);
                    }
                }, getActivity(), true));
    }


    private void set_list_resource(final List<MessageEntity.MessageBean> dates, final List<MessageEntity.NoReadBean> reads) {
        //设置上拉刷新下拉加载
        recycler_view.setHasFixedSize(false);
        recycler_view.setItemAnimator(null);
        layoutManager = new LinearLayoutManager(getActivity());
        recycler_view.setLayoutManager(layoutManager);
        systemMessageAdapter = new SystemMessageAdapter(dates, reads, getActivity());
        systemMessageAdapter.setOnItemClickListener(new SystemMessageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.e(TAG, "进来了吗：");
                //通知主Activity更新总消息数量
                // TODO: 2019-08-06
                change_message_status(dates.get(position));
            }
        });
        recycler_view.setAdapter(systemMessageAdapter);
        // 静默加载模式不能设置footerview
        // 设置静默加载模式
        //xrefreshview.setSilenceLoadMore(true);
        //设置刷新完成以后，headerview固定的时间
        xrefreshview.setPinnedTime(1000);
        xrefreshview.setMoveForHorizontal(true);
        //xrefreshview.setPullRefreshEnable(true);
        xrefreshview.setPullLoadEnable(true);//关闭加载更多
        xrefreshview.setAutoLoadMore(false);
        xrefreshview.enableRecyclerViewPullUp(true);
        xrefreshview.enablePullUpWhenLoadCompleted(true);
        //给recycler_view设置底部加载布局
        if (dates.size() > 9) {
            xrefreshview.enableReleaseToLoadMore(true);
            systemMessageAdapter.setCustomLoadMoreView(new XRefreshViewFooter(getActivity()));//加载更多
            xrefreshview.setLoadComplete(false);//显示底部
        } else {
            xrefreshview.enableReleaseToLoadMore(false);
            xrefreshview.setLoadComplete(true);//隐藏底部
        }
        //设置静默加载时提前加载的item个数
//        xefreshView1.setPreLoadCount(4);

        xrefreshview.setOnRecyclerViewScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        xrefreshview.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = 1;
                        //填写刷新数据的网络请求，一般page=1，List集合清空操作
                        dates.clear();
                        initMessageDate();
                        xrefreshview.stopRefresh();//刷新停止


                    }
                }, 1000);//2000是刷新的延时，使得有个动画效果
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        page = page + 1;
                        initMessageDate();
                        //填写加载更多的网络请求，一般page++
//                        //没有更多数据时候
                        if (is_not_more) {
                            xrefreshview.setLoadComplete(true);
                        } else {
                            //刷新完成必须调用此方法停止加载
                            xrefreshview.stopLoadMore(true);
                        }
                    }
                }, 1000);//1000是加载的延时，使得有个动画效果


            }
        });
    }

    /**
     * 改变消息类型
     *
     * @param messageBean
     */
    private void change_message_status(MessageEntity.MessageBean messageBean) {
        Map<String, Object> params = new HashMap<>();
        params.put("messageNum", messageBean.getMessageNum());//场馆编号
        Gson gson = new Gson();
        String json_params = gson.toJson(params);
        String json = new Gson().toJson(params);//要传递的json
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
        Log.e(TAG, "改变消息已读：" + json_params);
        Subscription subscription = Network.getInstance("改变消息已读", getActivity())
                .change_status(requestBody, new ProgressSubscriberNew<>(String.class, new GsonSubscriberOnNextListener<String>() {
                    @Override
                    public void on_post_entity(String entity, String s) {
                        Log.e(TAG, "改变消息成功：" + json_params);
                        //通知KeepFitActivity
                        MessageEvent messageEvent = new MessageEvent("update_message_num");
                        EventBus.getDefault().post(messageEvent);


                    }
                }, new SubscriberOnNextListener<Bean<Object>>() {
                    @Override
                    public void onNext(Bean<Object> result) {

                    }

                    @Override
                    public void onError(String error) {
                        Log.e(TAG, "改变消息失败：" + error);
                    }
                }, getActivity(), true));
    }


}
