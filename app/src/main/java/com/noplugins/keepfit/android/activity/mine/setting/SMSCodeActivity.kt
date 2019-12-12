package com.noplugins.keepfit.android.activity.mine.setting

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.activity.mine.UpdatePasswordActivity
import com.noplugins.keepfit.android.base.BaseActivity
import com.noplugins.keepfit.android.global.AppConstants
import com.noplugins.keepfit.android.util.BaseUtils
import com.noplugins.keepfit.android.util.MD5
import com.noplugins.keepfit.android.util.SpUtils
import com.noplugins.keepfit.android.util.net.Network
import com.noplugins.keepfit.android.util.net.entity.Bean
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener
import kotlinx.android.synthetic.main.activity_smscode.*
import kotlinx.android.synthetic.main.title_activity.*
import java.util.HashMap

class SMSCodeActivity : BaseActivity() {
    private var bool = ""
    private var messageId = ""
    private var phone = ""
    private var txPwd = false
    override fun initBundle(parms: Bundle?) {
        if (parms!=null){
            bool = parms.getString("newPhone","")
            txPwd = parms.getBoolean("txPwd",false)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        setContentView(R.layout.activity_smscode)
        tv_phone.text = "短信已发送至${SpUtils.getString(applicationContext,AppConstants.PHONE)}"
        title_tv.text = "短信验证码"
        phone = SpUtils.getString(applicationContext,AppConstants.PHONE)
        tv_send_code.isEnabled = false//设置不可点击，等待60秒过后可以点击
        timer.start()

        if (bool.isNotEmpty()){
            tv_phone.text = "短信已发送至$bool"
            phone = bool
            tv_next.text = "完成"
        }
    }

    override fun doBusiness(mContext: Context?) {
        back_btn.setOnClickListener {
            finish()
        }

        tv_next.setOnClickListener {
            if (BaseUtils.isFastClick()){

                if (txPwd){
                    val intent = Intent(this, UpdatePasswordActivity::class.java)
                    startActivity(intent)
                    finish()
                    return@setOnClickListener
                }

                if(bool.isNotEmpty()){
                    //todo  验证新手机号
                    InputNewPhoneActivity.inputNewPhoneActivity.finish()
                    finish()
                    return@setOnClickListener
                }

                val intent = Intent(this, InputNewPhoneActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }


    /**
     * 获取验证码
     */
    private fun send() {
        val params = HashMap<String, Any>()
        params["phone"] = phone
        params["sign"] = "${MD5.stringToMD5("MES$phone")}"
        params["time"] = System.currentTimeMillis()
        subscription = Network.getInstance("获取验证码", this)
                .get_yanzhengma(
                        params,
                        ProgressSubscriber("获取验证码", object : SubscriberOnNextListener<Bean<String>> {
                            override fun onNext(result: Bean<String>) {
                                messageId = result.data
                            }

                            override fun onError(error: String) {
                                Toast.makeText(applicationContext,error,Toast.LENGTH_SHORT)
                                        .show()
                            }
                        }, this, false)
                )
    }

    internal var timer: CountDownTimer = object : CountDownTimer(60000, 1000) {
        @SuppressLint("SetTextI18n")
        override fun onTick(millisUntilFinished: Long) {
            tv_send_code.setTextColor(Color.parseColor("#181818"))
            tv_send_code.text = "${millisUntilFinished / 1000}秒后可重新发送"

        }

        override fun onFinish() {
            tv_send_code.setTextColor(Color.parseColor("#929292"))
            tv_send_code.text = "重新发送验证码"
            tv_send_code.isEnabled = true
        }
    }


}
