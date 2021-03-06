package com.noplugins.keepfit.android.fragment.teacher

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.activity.mine.TeacherDetailActivity
//import com.noplugins.keepfit.android.activity.ChaungguanDetailActivity
import com.noplugins.keepfit.android.adapter.ShoukeCgAdapter
import com.noplugins.keepfit.android.base.BaseFragment
import com.noplugins.keepfit.android.bean.CgListBean
import com.noplugins.keepfit.android.bean.TeacherBean
import com.noplugins.keepfit.android.global.AppConstants
import com.noplugins.keepfit.android.util.SpUtils
import com.noplugins.keepfit.android.util.net.Network
import com.noplugins.keepfit.android.util.net.entity.Bean
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener
import kotlinx.android.synthetic.main.fragment_manager_teacher_1.*
import org.greenrobot.eventbus.EventBus
import java.util.HashMap

class SqAndYaoqinFragment : BaseFragment()  {
    companion object {
        fun newInstance(title: String): SqAndYaoqinFragment {
            val fragment = SqAndYaoqinFragment()
            val args = Bundle()
            args.putString("home_fragment_title", title)
            fragment.arguments = args
            return fragment
        }
    }
    var  datas:MutableList<TeacherBean> = ArrayList()
    lateinit var adapterManager : ShoukeCgAdapter
    var newView: View? = null
    var page = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (newView == null) {
            newView = inflater.inflate(R.layout.fragment_manager_teacher_1, container, false)
        }
        return newView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initAdapter()

    }

    override fun onFragmentVisibleChange(isVisible: Boolean) {
        super.onFragmentVisibleChange(isVisible)
        if (isVisible){
//            requestData()
        }
    }

    override fun onFragmentFirstVisible() {
        super.onFragmentFirstVisible()
        requestData()
    }

    private fun initAdapter(){
        rv_list.layoutManager = LinearLayoutManager(context)
        adapterManager = ShoukeCgAdapter(datas)
        val view = LayoutInflater.from(context).inflate(R.layout.enpty_view, rv_list, false)
        adapterManager.emptyView = view
        rv_list.adapter = adapterManager

        adapterManager.setOnItemChildClickListener { adapter, view, position ->
            when(view.id){
                R.id.rl_jump -> {
                    //跳转到详情页 需要携带状态
                    val toInfo = Intent(activity, TeacherDetailActivity::class.java)
                    val bundle = Bundle()
                    bundle.putInt("type",4)
                    bundle.putString("cgNum",datas[position].teacherNum)
                    toInfo.putExtras(bundle)
                    startActivity(toInfo)
                }
                R.id.tv_jieshou -> {
                    agreeBinding(datas[position].teacherNum,1,position)
                }
                R.id.tv_jujue -> {
                    agreeBinding(datas[position].teacherNum,2,position)
                }
            }
        }
        refresh_layout.setEnableLoadMore(false)
//        refresh_layout.setEnableRefresh(false)

        refresh_layout.setOnRefreshListener {
            //下拉刷新
            page = 1
            requestData()
            refresh_layout.finishRefresh(1000/*,false*/)
        }
        refresh_layout.setOnLoadMoreListener {
            //上拉加载
            page++
            requestData()
            refresh_layout.finishLoadMore(1000/*,false*/)
        }

    }

    private fun requestData(){
        val params = HashMap<String, Any>()
        params["areaNum"] = SpUtils.getString(activity, AppConstants.CHANGGUAN_NUM)
        params["status"] = 4
        params["page"] = page
        val subscription = Network.getInstance("场馆列表", activity)
                .teacherManner(
                        params,
                        ProgressSubscriber("场馆列表", object : SubscriberOnNextListener<Bean<List<TeacherBean>>> {
                            override fun onNext(result: Bean<List<TeacherBean>>) {
//                        setting(result.data.areaList)
                                if (page == 1){
                                    datas.clear()
                                    datas.addAll(result.data)
                                } else{
                                    datas.addAll(result.data)
                                }
                                adapterManager.notifyDataSetChanged()
                            }

                            override fun onError(error: String) {

                            }
                        }, activity, false)
                )
    }

    private fun agreeBinding(teacherNum:String,agree:Int,position:Int){
        val params = HashMap<String, Any>()
        params["teacherNum"] = teacherNum
        params["areaNum"] = SpUtils.getString(activity,AppConstants.CHANGGUAN_NUM)
        params["agree"] = agree
        val subscription = Network.getInstance("同意拒绝", activity)
                .agreeBindingArea(
                        params,
                        ProgressSubscriber("同意拒绝", object : SubscriberOnNextListener<Bean<Any>> {
                            override fun onNext(result: Bean<Any>) {
//                        setting(result.data.areaList)
                                Toast.makeText(activity,"操作成功！", Toast.LENGTH_SHORT).show()

                                if (result.code == 0){
                                    datas.removeAt(position)//删除数据源,移除集合中当前下标的数据
                                    adapterManager.notifyItemRemoved(position)//刷新被删除的地方
                                    adapterManager.notifyItemRangeChanged(position, adapterManager.itemCount) //刷新被删除数据，以及其后面的数据

                                    when (agree) {
                                        1 -> {
                                            EventBus.getDefault().post(AppConstants.TEAM_YQ_AGREE)
                                        }

                                        0 -> {
                                            EventBus.getDefault().post(AppConstants.TEAM_YQ_REFUSE)
                                        }
                                    }
                                }
//                                requestData()
                            }

                            override fun onError(error: String) {
                                Toast.makeText(activity,error, Toast.LENGTH_SHORT).show()
                            }
                        }, activity, false)
                )
    }

}