package com.noplugins.keepfit.android.fragment.statistics

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.base.BaseFragment
import com.noplugins.keepfit.android.global.AppConstants
import com.noplugins.keepfit.android.util.SpUtils
import com.noplugins.keepfit.android.util.ui.ViewPagerFragment
import kotlinx.android.synthetic.main.fragment_statistics.*

class StatisticsFragment: ViewPagerFragment() {
    override fun fetchData() {
        if (tvCGName!=null){
            tvCGName!!.text = SpUtils.getString(activity!!, AppConstants.CG_NAME)
        }

    }

    companion object {
        fun newInstance(title: String): StatisticsFragment {
            val fragment = StatisticsFragment()
            val args = Bundle()
            args.putString("home_fragment_title", title)
            fragment.arguments = args
            return fragment
        }
    }

    var newView: View? = null

    private var fragments: MutableList<Fragment>?=null
    private var currentFragment = Fragment()
    private var currentIndex = 0
    private var tvCGName:TextView ?= null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (newView == null) {
            newView = inflater.inflate(R.layout.fragment_statistics, container, false)
            tvCGName = newView!!.findViewById(R.id.store_type_tv)
        }
        return newView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (fragments == null){
            fragments = ArrayList()
            fragments!!.add(ToUserFragment.newInstance("用户"))
            fragments!!.add(ToProductFragment.newInstance("产品"))
            showFragment()
        }



        rl_user.setOnClickListener {

            changeBtn(1)
            currentIndex = 0
            showFragment()
        }
        rl_product.setOnClickListener {

            changeBtn(2)
            currentIndex = 1
            showFragment()
        }
    }


    private fun showFragment() {

        val transaction = childFragmentManager.beginTransaction()

        //如果之前没有添加过
        if (!fragments!![currentIndex].isAdded) {
            transaction
                    .hide(currentFragment)
                    .add(R.id.main_container_content, fragments!![currentIndex], "" + currentIndex)  //第三个参数为添加当前的fragment时绑定一个tag

        } else {
            transaction
                    .hide(currentFragment)
                    .show(fragments!![currentIndex])
        }

        currentFragment = fragments!![currentIndex]

        transaction.commit()

    }


    private fun changeBtn(select:Int){
        when(select){
            1 -> {
                tv1.setTextColor(Color.parseColor("#F9CE0F"))
                lin1.visibility = View.VISIBLE
                tv2.setTextColor(Color.parseColor("#181818"))
                lin2.visibility = View.INVISIBLE
            }
            2 -> {
                tv2.setTextColor(Color.parseColor("#F9CE0F"))
                lin2.visibility = View.VISIBLE
                tv1.setTextColor(Color.parseColor("#181818"))
                lin1.visibility = View.INVISIBLE
            }
        }
    }
    /**
     * 动态显示Fragment
     *
     * @param showFragment 要增加的fragment
     * @param add          true：增加fragment；false：切换fragment
     */
//    private fun hideOthersFragment(showFragment: Fragment, add: Boolean) {
//        if (add)
//            transition!!.add(R.id.main_container_content, showFragment)
//        fragments.forEach { fragment ->
//            if (showFragment == fragment) {
//                transition!!.show(fragment)
//            } else {
//                transition!!.hide(fragment)
//            }
//        }
//        transition!!.commit()
//    }


//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        Log.d("tongji","onSaveInstanceState")
//    }

    override fun onDestroyView() {
        super.onDestroyView()
//        userFragment = null
//        productFragment = null
    }

}