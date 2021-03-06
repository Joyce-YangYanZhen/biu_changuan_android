package com.noplugins.keepfit.android.activity.user

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.text.*
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.webkit.WebView
import android.widget.CompoundButton
import android.widget.TextView
import android.widget.Toast
import butterknife.ButterKnife
import com.noplugins.keepfit.android.KeepFitActivity
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.activity.CheckStatusFailActivity
import com.noplugins.keepfit.android.activity.HeTongActivity
import com.noplugins.keepfit.android.activity.InformationCheckActivity
import com.noplugins.keepfit.android.activity.SubmitInformationSelectActivity
import com.noplugins.keepfit.android.activity.mine.CgPriceActivity
import com.noplugins.keepfit.android.base.BaseActivity
import com.noplugins.keepfit.android.bean.LoginBean
import com.noplugins.keepfit.android.entity.CheckEntity
import com.noplugins.keepfit.android.entity.LoginEntity
import com.noplugins.keepfit.android.global.AppConstants
import com.noplugins.keepfit.android.util.MD5
import com.noplugins.keepfit.android.util.SpUtils
import com.noplugins.keepfit.android.util.data.StringsHelper
import com.noplugins.keepfit.android.util.net.Network
import com.noplugins.keepfit.android.util.net.entity.Bean
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener
import com.noplugins.keepfit.android.util.ui.pop.CommonPopupWindow
import kotlinx.android.synthetic.main.activity_login2.*
import java.util.*

class Login2Activity : BaseActivity() {

    private var is_yanzhengma_logon = true
    private var message_id = ""
    private var is_check_fuwu = false

    override fun initBundle(parms: Bundle?) {

    }

    override fun initView() {
        setContentLayout(R.layout.activity_login2)
        ButterKnife.bind(this)
        isShowTitle(false)
    }

    override fun doBusiness(mContext: Context) {

        xieyi_check_btn.setOnClickListener(View.OnClickListener {
            if (xieyi_check_btn.isChecked) {
                xieyi_pop()
            }
        })
        qiehuan_login.setOnClickListener(View.OnClickListener {

            when (qiehuan_login.text.toString()) {
                "验证码登录" -> {
                    Log.e("登录方式", "验证码登录")

                    is_yanzhengma_logon = true
                    qiehuan_login.text = "密码登录"
                    edit_password.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(6)) //最大输入长度

                    val s = SpannableString("请输入验证码")//这里输入自己想要的提示文字
                    edit_password.hint = s
                    edit_password.setText("")
                    img_password.setImageResource(R.drawable.yanzhengma_icon)
                    tv_send.visibility = View.VISIBLE
                    edit_password.inputType = InputType.TYPE_CLASS_NUMBER
                    forget_password_btn.visibility = View.GONE
                }

                "密码登录" -> {
                    Log.e("登录方式", "密码登录")

                    is_yanzhengma_logon = false
                    qiehuan_login.text = "验证码登录"
                    edit_password.inputType = InputType.TYPE_CLASS_TEXT
                    edit_password.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(18)) //最大输入长度

                    val s = SpannableString("请输入密码")//这里输入自己想要的提示文字
                    edit_password.hint = s
                    img_password.setImageResource(R.drawable.password_icon)
                    tv_send.visibility = View.GONE
                    edit_password.setText("")
                    forget_password_btn.visibility = View.VISIBLE
                    edit_password.inputType = 0x00000081
                }
            }
        })
        //发送验证码
        tv_send.setOnClickListener(View.OnClickListener {
            if (TextUtils.isEmpty(edit_phone_number.text)) {
                Toast.makeText(applicationContext, "电话号码不能为空！", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            } else if (!StringsHelper.isMobileOne(edit_phone_number.getText().toString())) {
                Toast.makeText(applicationContext, "电话号码格式不正确！", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            } else {
                tv_send.isEnabled = false//设置不可点击，等待60秒过后可以点击
                timer.start()
                //获取验证码接口
                Get_YanZhengMa()
            }
        })
        edit_phone_number.addTextChangedListener(textWatcher)

        iv_delete_edit.setOnClickListener(View.OnClickListener { edit_phone_number.setText("") })
        tv_user_protocol.setOnClickListener(View.OnClickListener { xieyi_pop() })
        login_btn.setBtnOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                if (!is_check_fuwu) {
                    Toast.makeText(this@Login2Activity, "请先勾选用户协议！", Toast.LENGTH_SHORT).show()
                    return
                } else if (TextUtils.isEmpty(edit_phone_number.getText())) {
                    Toast.makeText(applicationContext, "电话号码不能为空！", Toast.LENGTH_SHORT).show()
                    return
                } else if (!StringsHelper.isMobileOne(edit_phone_number.getText().toString())) {
                    Toast.makeText(applicationContext, "电话号码格式不正确！", Toast.LENGTH_SHORT).show()
                    return
                } else if (TextUtils.isEmpty(edit_password.getText())) {
                    if (is_yanzhengma_logon) {
                        Toast.makeText(applicationContext, "验证码不能为空！", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(applicationContext, "密码不能为空！", Toast.LENGTH_SHORT).show()
                    }
                    return
                }
                run {
                    login_btn.startLoading()
                    if (is_yanzhengma_logon) {//如果是验证码登录，则让它设置密码
                        yanzheng_yanzhengma()
                    } else {
                        password_login()
                    }
                }


            }
        })
        xieyi_check_btn.setOnCheckedChangeListener(onCheckedChangeListener)

        tv_user_protocol.setOnTouchListener(View.OnTouchListener { v, event ->
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            val v = window.peekDecorView()
            val isBoolean = inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0)
            edit_password.clearFocus()
            edit_phone_number.clearFocus()
            isBoolean
        })
        xieyi_check_btn.setOnTouchListener(View.OnTouchListener { v, event ->
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            val v = window.peekDecorView()
            val isBoolean = inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0)
            edit_password.clearFocus()
            edit_phone_number.clearFocus()
            isBoolean
        })
        //忘记密码
        forget_password_btn.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@Login2Activity, SettingPwdActivity::class.java)
            startActivity(intent)
        })
    }

    /**
     * 验证码登陆
     */
    private fun yanzheng_yanzhengma() {
        val params = HashMap<String, Any>()
        params["messageId"] = message_id
        params["code"] = edit_password.text.toString()
        params["phone"] = edit_phone_number.text.toString()
        subscription = Network.getInstance("验证验证码和登录", this)
                .verifyCodeLogin(params,
                        ProgressSubscriber("验证验证码和登录", object : SubscriberOnNextListener<Bean<LoginBean>> {
                            override fun onNext(result: Bean<LoginBean>) {
                                login_btn.loadingComplete()
                                when (result.data.memberService) {
                                    1 -> {
                                        SpUtils.putString(applicationContext, AppConstants.USER_DENGJI, "2999")
                                    }
                                    2 -> {
                                        SpUtils.putString(applicationContext, AppConstants.USER_DENGJI, "3999")
                                    }
                                    3 -> {
                                        SpUtils.putString(applicationContext, AppConstants.USER_DENGJI, "6999")
                                    }
                                }
                                save_resource(result.data)
                                if (result.data.havePassword == 0) {//没有设置过密码
                                    val intent = Intent(this@Login2Activity, SetPasswordActivity::class.java)
                                    startActivity(intent)
                                } else {//设置过密码
                                    if (result.data.type == 0) {
                                        val intent = Intent(this@Login2Activity, SubmitInformationSelectActivity::class.java)
                                        startActivity(intent)
                                    } else if (result.data.type == 1) {
                                        get_check_status()
                                    } else {
                                        val intent = Intent(this@Login2Activity, KeepFitActivity::class.java)
                                        startActivity(intent)
                                    }
                                }
                            }

                            override fun onError(error: String) {
                                login_btn.loadingComplete()
                                Toast.makeText(applicationContext, error, Toast.LENGTH_SHORT).show()


                            }
                        }, this, false))
    }

    private fun password_login() {
        val params = HashMap<String, Any>()
        params["phone"] = edit_phone_number.text.toString()
        params["password"] = edit_password.text.toString()

        val subscription = Network.getInstance("密码登录", this)
                .login(params,
                        ProgressSubscriber("密码登录", object : SubscriberOnNextListener<Bean<LoginEntity>> {
                            override fun onNext(result: Bean<LoginEntity>) {
                                SpUtils.putString(applicationContext, AppConstants.TOKEN, result.data.token)
                                SpUtils.putString(applicationContext, AppConstants.PHONE, edit_phone_number.text.toString())
                                SpUtils.putString(applicationContext, AppConstants.CHANGGUAN_NUM, result.data.gymAreaNum)
//                                SpUtils.putString(applicationContext, AppConstants.CG_NAME, result.data.getMasterName())
//                                SpUtils.putString(applicationContext, AppConstants.CHANGGUAN_NUM, "GYM19091236750176")
                                SpUtils.putString(applicationContext, AppConstants.USER_NAME, result.data.gymUserNum)
                                SpUtils.putInt(applicationContext, AppConstants.USER_TYPE, result.data.type)
                                SpUtils.putInt(applicationContext, AppConstants.IS_TX, result.data.havePayPassWord)

                                when (result.data.memberService) {
                                    1 -> {
                                        SpUtils.putString(applicationContext, AppConstants.USER_DENGJI, "2999")
                                    }
                                    2 -> {
                                        SpUtils.putString(applicationContext, AppConstants.USER_DENGJI, "3999")
                                    }
                                    3 -> {
                                        SpUtils.putString(applicationContext, AppConstants.USER_DENGJI, "6999")
                                    }
                                }
                                if (result.data.type == 0) {
                                    val intent = Intent(this@Login2Activity, SubmitInformationSelectActivity::class.java)
                                    startActivity(intent)
                                } else if (result.data.type == 1) {
                                    get_check_status()
                                } else {
                                    val intent = Intent(this@Login2Activity, KeepFitActivity::class.java)
                                    startActivity(intent)
                                }

//                                val intent = Intent(this@Login2Activity, KeepFitActivity::class.java)
//                                startActivity(intent)
                                login_btn.loadingComplete()
                            }

                            override fun onError(error: String) {
                                Log.e(TAG, "登录失败：$error")
                                Toast.makeText(applicationContext, error, Toast.LENGTH_SHORT).show()
                                login_btn.loadingComplete()
                            }
                        }, this, false))
    }

    private fun Get_YanZhengMa() {
        val params = HashMap<String, Any>()
        params["phone"] = edit_phone_number.text.toString()
        params["sign"] = "${MD5.stringToMD5("MES" + edit_phone_number.text.toString())}"
        params["time"] = System.currentTimeMillis()
        val subscription = Network.getInstance("获取验证码", this)
                .get_yanzhengma(params,
                        ProgressSubscriber("获取验证码", object : SubscriberOnNextListener<Bean<String>> {
                            override fun onNext(result: Bean<String>) {
                                message_id = result.data
                            }

                            override fun onError(error: String) {

                            }
                        }, this, false))
    }

    private fun xieyi_pop() {
        val popupWindow = CommonPopupWindow.Builder(this)
                .setView(R.layout.xieyi_pop_layout)
                .setBackGroundLevel(1f)//0.5f
                .setAnimationStyle(R.style.main_menu_animstyle)
                .setWidthAndHeight(WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.MATCH_PARENT)
                .setOutSideTouchable(true).create()
        popupWindow.showAsDropDown(tv_user_protocol)

        /**设置逻辑 */
        val view = popupWindow.contentView
        val agree_btn = view.findViewById<TextView>(R.id.agree_btn)
        val webView = view.findViewById<WebView>(R.id.content_layout)
        //自适应屏幕
        val webSettings = webView.settings
        webSettings.useWideViewPort = true//设置此属性，可任意比例缩放
        webSettings.loadWithOverviewMode = true
        webView.loadUrl("http://www.noplugins.com/doc/changguan_xieyi.html")
        agree_btn.setOnClickListener(View.OnClickListener {
            popupWindow.dismiss()
            is_check_fuwu = true
            xieyi_check_btn.isChecked = true
        })
        val no_agree_btn = view.findViewById<TextView>(R.id.no_agree_btn)
        no_agree_btn.setOnClickListener(View.OnClickListener {
            is_check_fuwu = false
            xieyi_check_btn.isChecked = false
            popupWindow.dismiss()
        })
    }

    private fun save_resource(login: LoginBean) {
        SpUtils.putString(applicationContext, AppConstants.TOKEN, login.token)
        SpUtils.putString(applicationContext, AppConstants.USER_NAME, login.gymUserNum)
        SpUtils.putInt(applicationContext, AppConstants.USER_TYPE, login.type)
        SpUtils.putString(applicationContext, AppConstants.PHONE, edit_phone_number.text.toString())
        SpUtils.putInt(applicationContext, AppConstants.IS_TX, login.havePayPassWord)
        SpUtils.putString(applicationContext, AppConstants.CHANGGUAN_NUM, login.gymAreaNum)
//        SpUtils.putString(applicationContext, AppConstants.CG_NAME, login.getMasterName())
    }

    private fun get_check_status() {
        val params = HashMap<String, Any>()
        params["token"] = SpUtils.getString(applicationContext, AppConstants.TOKEN)
        val subscription = Network.getInstance("获取审核状态", this)
                .get_check_status(params,
                        ProgressSubscriber("获取审核状态", object : SubscriberOnNextListener<Bean<CheckEntity>> {
                            override fun onNext(result: Bean<CheckEntity>) {
                                //成功1,失败0,没有提交过资料-2,2没有银行卡,3审核中
                                if (result.data.status == 1) {//成功
                                    //0没买过，1是2999 2是3999 3是6999

                                    when (result.data.haveMember) {
                                        "0" -> {
                                            val intent = Intent(this@Login2Activity, HeTongActivity::class.java)
                                            startActivity(intent)
                                            finish()
                                            return
                                        }
                                        "1" -> {
                                            SpUtils.putString(applicationContext, AppConstants.USER_DENGJI, "2999")
                                        }
                                        "2" -> {
                                            SpUtils.putString(applicationContext, AppConstants.USER_DENGJI, "3999")
                                        }
                                        "3" -> {
                                            SpUtils.putString(applicationContext, AppConstants.USER_DENGJI, "6999")
                                        }
                                    }
                                    if (result.data.highTime == 1) {
                                        val intent = Intent(this@Login2Activity, KeepFitActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    } else {
                                        val intent = Intent(this@Login2Activity, CgPriceActivity::class.java)
                                        val bundle = Bundle()
                                        bundle.putString("form", "pay")
                                        intent.putExtras(bundle)
                                        startActivity(intent)
                                        finish()
                                    }
                                } else if (result.data.status == 2) {
                                    val intent = Intent(this@Login2Activity, InformationCheckActivity::class.java)
                                    val bundle = Bundle()
                                    bundle.putInt("is_not_bind", 1)
                                    intent.putExtras(bundle)
                                    startActivity(intent)
                                    finish()
                                } else if (result.data.status == 0) {//失败
                                    val intent = Intent(this@Login2Activity, CheckStatusFailActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                } else if (result.data.status == -2 || result.data.status == 4) {//没有提交过
                                    val intent = Intent(this@Login2Activity, SubmitInformationSelectActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                            }

                            override fun onError(error: String) {

                            }
                        }, this, false))
    }

    internal var textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

        }

        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

        }

        override fun afterTextChanged(editable: Editable) {
            if (editable.isNotEmpty()) {
                iv_delete_edit.visibility = View.VISIBLE
            } else {
                iv_delete_edit.visibility = View.GONE

            }
        }
    }

    internal var timer: CountDownTimer = object : CountDownTimer(60000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            tv_send.setTextColor(Color.parseColor("#7B7B7B"))
            tv_send.text = "重新发送(" + millisUntilFinished / 1000 + "s)"

        }

        override fun onFinish() {
            tv_send.setTextColor(Color.parseColor("#292C31"))
            tv_send.text = "重新发送"
            tv_send.isEnabled = true
        }
    }


    internal var onCheckedChangeListener: CompoundButton.OnCheckedChangeListener = CompoundButton.OnCheckedChangeListener { compoundButton, is_check ->
        if (is_check) {
            Log.e(TAG, "选中了")
            is_check_fuwu = true
        } else {
            Log.e(TAG, "没选中")
            is_check_fuwu = false
        }
    }

    private var exitTime: Long = 0 //必须是long型
    override fun onBackPressed() {
        println(System.currentTimeMillis())
        if (System.currentTimeMillis() - exitTime < 2000) {
            finish()
        } else {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_LONG).show()
            exitTime = System.currentTimeMillis()
        }
    }
}
