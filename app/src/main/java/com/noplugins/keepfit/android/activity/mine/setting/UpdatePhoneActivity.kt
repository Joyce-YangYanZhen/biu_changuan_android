package com.noplugins.keepfit.android.activity.mine.setting

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.base.BaseActivity
import com.noplugins.keepfit.android.global.AppConstants
import com.noplugins.keepfit.android.util.BaseUtils
import com.noplugins.keepfit.android.util.SpUtils
import kotlinx.android.synthetic.main.activity_update_phone.*
import kotlinx.android.synthetic.main.title_activity.*

class UpdatePhoneActivity : BaseActivity() {
    override fun initBundle(parms: Bundle?) {
    }

    override fun initView() {
        setContentLayout(R.layout.activity_update_phone)
        isShowTitle(true)
        setTitleView(R.string.tv_update_phone)
        tv_phone.text = SpUtils.getString(applicationContext,AppConstants.PHONE)
    }

    override fun doBusiness(mContext: Context?) {
        title_left_button_onclick_listen {
            finish()
        }

        tv_send_code.setOnClickListener {
            if(BaseUtils.isFastClick()){
                val intent = Intent(this, SMSCodeActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        tv_phone.text = SpUtils.getString(applicationContext,AppConstants.PHONE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}
