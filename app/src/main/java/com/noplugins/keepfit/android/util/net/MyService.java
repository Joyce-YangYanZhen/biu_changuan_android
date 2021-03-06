package com.noplugins.keepfit.android.util.net;


import com.alibaba.fastjson.annotation.JSONField;
import com.noplugins.keepfit.android.bean.BindCardBean;
import com.noplugins.keepfit.android.bean.BankCradBean;
import com.noplugins.keepfit.android.bean.BuyInformationBean;
import com.noplugins.keepfit.android.bean.CalenderEntity;
import com.noplugins.keepfit.android.bean.ChangguanBean;
import com.noplugins.keepfit.android.bean.CheckBean;
import com.noplugins.keepfit.android.bean.ChooseBean;
import com.noplugins.keepfit.android.bean.CityCode;
import com.noplugins.keepfit.android.bean.CompnyBean;
import com.noplugins.keepfit.android.bean.DictionaryeBean;
import com.noplugins.keepfit.android.bean.HightList11Bean;
import com.noplugins.keepfit.android.bean.HightListBean;
import com.noplugins.keepfit.android.bean.LoginBean;
import com.noplugins.keepfit.android.bean.OrderResultBean;
import com.noplugins.keepfit.android.bean.PrivateDetailBean;
import com.noplugins.keepfit.android.bean.SelectChangGuanBean;
import com.noplugins.keepfit.android.bean.SelectRoomBean;
import com.noplugins.keepfit.android.bean.TeacherBean;
import com.noplugins.keepfit.android.bean.TeacherDetailBean;
import com.noplugins.keepfit.android.bean.RiChengBean;
import com.noplugins.keepfit.android.bean.UserStatisticsBean;
import com.noplugins.keepfit.android.bean.WxPayBean;
import com.noplugins.keepfit.android.bean.mine.BalanceListBean;
import com.noplugins.keepfit.android.bean.mine.WalletBean;
import com.noplugins.keepfit.android.bean.use.ManagerBean;
import com.noplugins.keepfit.android.bean.use.RoomBean;
import com.noplugins.keepfit.android.bean.use.RoomDelBean;
import com.noplugins.keepfit.android.bean.use.UseBean;
import com.noplugins.keepfit.android.entity.AddClassEntity;
import com.noplugins.keepfit.android.entity.CheckEntity;
import com.noplugins.keepfit.android.entity.ClassDetailEntity;
import com.noplugins.keepfit.android.entity.ClassEntity;
import com.noplugins.keepfit.android.entity.ClassTypeEntity;
import com.noplugins.keepfit.android.entity.InformationEntity;
import com.noplugins.keepfit.android.entity.LoginEntity;
import com.noplugins.keepfit.android.entity.RoleBean;
import com.noplugins.keepfit.android.entity.TeacherEntity;
import com.noplugins.keepfit.android.entity.VersionEntity;
import com.noplugins.keepfit.android.util.net.entity.Bean;
import com.noplugins.keepfit.android.util.net.entity.Token;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.internal.Version;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by limengtao on 2017/3/17.
 */

public interface MyService {

    /**
     * 登录
     *
     * @return
     */
    @FormUrlEncoded
    @POST("answerApi/login/login")
    Observable<Bean<Object>> fast_login(@FieldMap Map<String, String> map);

    /**
     * 获取验证码
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("getVerifyCodeNew")
//
    Observable<Bean<String>> get_yanzhengma(@Body RequestBody json);

    /**
     * 验证验证码
     *
     * @return
     */
    @FormUrlEncoded
    @POST("verifyCode")
    Observable<Bean<String>> check_yanzhengma(@FieldMap Map<String, Object> map);

    /**
     * 注册
     *
     * @return
     */
    @FormUrlEncoded
    @POST("setPassword")
    Observable<Bean<Object>> register(@FieldMap Map<String, String> map);

    /**
     * 登录
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("userlogin")
    Observable<Bean<LoginEntity>> login(@Body RequestBody json);


    /**
     * 验证码登陆登录
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("verifyCodeLogin")
    Observable<Bean<LoginBean>> verifyCodeLogin(@Body RequestBody json);

    /**
     * 修改密码
     *
     * @return
     */
    @FormUrlEncoded
    @POST("forgetPassword")
    Observable<Bean<Object>> update_password(@FieldMap Map<String, String> map);

    /**
     * 修改密码
     *
     * @return
     */
    @FormUrlEncoded
    @POST("choiceRole")
    Observable<Bean<Object>> select_role(@FieldMap Map<String, String> map);

    /**
     * 获取七牛token
     *
     * @return
     */
    @FormUrlEncoded
    @POST("getPicToken")
    Observable<Bean<Object>> get_qiniu_token(@FieldMap Map<String, String> map);

    /**
     * 获取七牛token
     *
     * @return
     */
    @FormUrlEncoded
    @POST("getPicUrl")
    Observable<Bean<Object>> get_qiniu_url(@FieldMap Map<String, String> map);

    /**
     * 提交审核资料
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("submitAudit")
    Observable<Bean<CheckBean>> submit_information(@Body RequestBody json);


    /**
     * 获取审核状态
     *
     * @return
     */
    @FormUrlEncoded
    @POST("getAuditResult")
    Observable<Bean<CheckEntity>> get_check_status(@FieldMap Map<String, Object> json);

    /**
     * 获取公司信息
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("getAreaAccount")
    Observable<Bean<CompnyBean>> get_compny_information(@Body RequestBody map);

    /**
     * 绑定银行卡
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("bindingBank")
    Observable<Bean<Object>> bind_card(@Body RequestBody map);

    /**
     * 生成订单
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("memberOrder")
    Observable<Bean<String>> get_order(@Body RequestBody map);


    /**
     * 获取购买信息
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("getBuyAreaData")
    Observable<Bean<BuyInformationBean>> get_buy_information(@Body RequestBody map);

    /**
     * 获取审核状态
     *
     * @return
     */
    @FormUrlEncoded
    @POST("dayView")
    Observable<Bean<Object>> get_class_resource(@FieldMap Map<String, String> map);

    /**
     * 添加团课
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("addClass")
    Observable<Bean<AddClassEntity>> add_class(@Body RequestBody map);

    /**
     * 获取审核状态
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("resourceList")
    Observable<Bean<ClassEntity>> class_list(@Body RequestBody map);

    /**
     * 获取审核状态
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("getMaxPerson")
    Observable<Bean<Object>> get_max_num(@Body RequestBody json);

    /**
     * 获取审核状态
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("dayViewList")
    Observable<Bean<Object>> get_month_view(@Body RequestBody json);

    /**
     * 获取审核状态
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("searchMessage")
    Observable<Bean<Object>> zhanghu_message_list(@Body RequestBody json);

    /**
     * 获取审核状态
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("agreeApply")
    Observable<Bean<Object>> agreeApply(@Body RequestBody json);

    /**
     * 获取申请详情
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("classDetail")
    Observable<Bean<Object>> get_shenqing_detail(@Body RequestBody json);

    /**
     * 获取申请详情
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("alreadyRead")
    Observable<Bean<Object>> change_status(@Body RequestBody json);

    /**
     * 获取申请详情
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("initializeMessage")
    Observable<Bean<Object>> get_message_all(@Body RequestBody json);

    /**
     * 获取申请详情
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("getFreeTeacher")
    Observable<Bean<TeacherEntity>> get_teacher_list(@Body RequestBody json);

    /**
     * 邀请
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("inviteTeacher")
    Observable<Bean<Object>> invite(@Body RequestBody json);

    /**
     * 取消邀请
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("cancelInvite")
    Observable<Bean<Object>> cancel_invite(@Body RequestBody json);

    /**
     * 获取用户/产品 统计
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("statistics")
    Observable<Bean<Object>> get_statistics(@Body RequestBody json);

    /**
     * 团课详情
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("leagueClass")
    Observable<Bean<ClassDetailEntity>> class_detail(@Body RequestBody json);

    /**
     * 修改密码
     *
     * @return 是否修改成功
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("modificationPassword")
    Observable<Bean<Object>> update_my_password(@Body RequestBody json);

    /**
     * 设置高低峰时段
     *
     * @return 是否设置成功
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("setHighAndLowTime")
    Observable<Bean<Object>> setHighAndLowTime(@Body RequestBody json);

//    /**
//     * 设置高低峰时段
//     *
//     * @return 是否设置成功
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
//    @POST("teacherDetail")
//    Observable<Bean<Object>> teacherDetail(@Body RequestBody json);


    /**
     * 批量绑定用户
     *
     * @return 是否设置成功
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("bindingRole")
    Observable<Bean<Object>> bindingRole(@Body RequestBody json);

    /**
     * 获取已绑定列表
     *
     * @return 是否设置成功
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("findBindingRoles")
    Observable<Bean<RoleBean>> findBindingRoles(@Body RequestBody json);

    /**
     * 批量绑定教练
     *
     * @return 是否设置成功
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("gymBinding")
    Observable<Bean<Object>> bindingTeacher(@Body RequestBody json);

    /**
     * 获取已绑定列表
     *
     * @return 是否设置成功
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("findBindingTeachers")
    Observable<Bean<Object>> findBindingTeachers(@Body RequestBody json);

    /**
     * 产品反馈
     *
     * @return 是否设置成功
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("feedBackData")
    Observable<Bean<Object>> feedback(@Body RequestBody json);

    /**
     * 获取我的账户
     *
     * @return 账户信息
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("searchWallet")
    Observable<Bean<Object>> searchWallet(@Body RequestBody json);

    /**
     * 获取我的账单列表
     *
     * @return 账户信息
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("searchWalletDetail")
    Observable<Bean<Object>> searchWalletDetail(@Body RequestBody json);

    /**
     * 发送账号
     *
     * @return 账户信息
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("userCheckIn")
    Observable<Bean<Object>> sen_order(@Body RequestBody json);

    /**
     * 获取房间类型
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("findAreaPlace")
    Observable<Bean<List<ClassTypeEntity>>> get_class_type(@Body RequestBody json);


    /**
     * 获取房间名字
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("getAreaPlace")
    Observable<Bean<List<SelectRoomBean>>> get_class_name(@Body RequestBody json);
    /**
     * 获取支付信息 -支付宝
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("memberOrderPay")
    Observable<Bean<String>> memberOrderPay(@Body RequestBody json);

    /**
     * 获取支付信息 -微信
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("memberOrderPay")
    Observable<Bean<WxPayBean>> memberOrderPayWx(@Body RequestBody json);

    /**
     * 教练详情
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("memberOrderPay")
    Observable<Bean<PrivateDetailBean>> teacherDetails(@Body RequestBody json);

    /**
     * 账户
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("searchWallet")
    Observable<Bean<WalletBean>> myBalance(@Body RequestBody json);

    /**
     * 明细列表
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("searchWalletDetail")
    Observable<Bean<BalanceListBean>> myBalanceList(@Body RequestBody json);

    /**
     * 明细详情
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("myWalletDetail")
    Observable<Bean<BalanceListBean.ListBean>> myBalanceListDetail(@Body RequestBody json);

    /**
     * 修改手机号
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("updatePhone")
    Observable<Bean<Object>> updatePhone(@Body RequestBody json);

    /**
     * 设置密码
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("setPassword")
    Observable<Bean<String>> setPassword(@Body RequestBody params);

    /**
     * 修改密码
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("forgetPassword")
    Observable<Bean<Object>> forgetPassword(@Body RequestBody json);

    /**
     * 设置转出密码
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("setPayPassWord")
    Observable<Bean<Object>> settingPayPassword(@Body RequestBody json);

    /**
     * 转出
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("withdrawDeposit")
    Observable<Bean<Object>> withdrawDeposit(@Body RequestBody json);


    /**
     * 教练管理列表
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("teacherManner")
    Observable<Bean<List<TeacherBean>>> teacherManner(@Body RequestBody json);

    /**
     * 教练类型管理列表
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("teacherMannerList")
    Observable<Bean<List<TeacherBean>>> teacherMannerList(@Body RequestBody json);


    /**
     * 教练详情
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("teacherDetail")
    Observable<Bean<TeacherDetailBean>> teacherDetail(@Body RequestBody json);


    /**
     * 同意你拒绝
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("agreeTeacherApply")
    Observable<Bean<Object>> agreeBindingArea(@Body RequestBody json);

    /**
     * 精细化时间段
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("setHighTime")
    Observable<Bean<Object>> setHighTime(@Body RequestBody json);

    /**
     * 成本核算
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("updateCost")
    Observable<Bean<Object>> updateCost(@Body RequestBody json);

    /**
     * 获取日历
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("yearView")
    Observable<Bean<CalenderEntity>> get_rili(@Body RequestBody json);

    /**
     * 获取首页数据
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("areaSchedule")
    Observable<Bean<RiChengBean>> get_shouye_date(@Body RequestBody json);

    /**
     * 获取首页类型
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("searchDict")
    Observable<Bean<List<DictionaryeBean>>> get_types(@Body RequestBody json);

    /**
     * 获取场馆列表
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("findAllArea")
    Observable<Bean<List<SelectChangGuanBean>>> get_changguans(@Body RequestBody json);

    /**
     * 切换场馆
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("chooseArea")
    Observable<Bean<ChooseBean>> qiehuan_changguans(@Body RequestBody json);


    /**
     * 获取首页数据
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("myArea")
    Observable<Bean<ChangguanBean>> myArea(@Body RequestBody json);

    /**
     * 修改场馆数据
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("updateMyArea")
    Observable<Bean<Object>> submitAudit(@Body RequestBody json);

    /**
     * 场馆绑定教练
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("areaInviteTeacher")
    Observable<Bean<Object>> areaInviteTeacher(@Body RequestBody json);

    /**
     * 转出
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("areaWithdraw")
    Observable<Bean<Object>> areaWithdraw(@Body RequestBody json);

    /**
     * 银行卡
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("bankCard")
    Observable<Bean<BankCradBean>> bankCard(@Body RequestBody json);

    /**
     * 获取高峰时间价格
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("findAreaPrice")
    Observable<Bean<HightList11Bean>> findAreaPrice(@Body RequestBody json);

    /**
     * 统计
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("statistics")
    Observable<Bean<UserStatisticsBean>> statistics(@Body RequestBody json);

    /**
     * pay 二次验证
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("getPayResult")
    Observable<Bean<OrderResultBean>> getPayResult(@Body RequestBody json);

    /**
     * 解除绑定
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("deleteTeacherBinding")
    Observable<Bean<Object>> deleteTeacherBinding(@Body RequestBody json);

    /**
     * 统计首页
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("operationData")
    Observable<Bean<UseBean>> operationData(@Body RequestBody json);

    /**
     * 添加房间
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("addAreaPlace")
    Observable<Bean<Object>> addAreaPlace(@Body RequestBody json);

    /**
     * 删除房间
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("deleteAreaPlace")
    Observable<Bean<RoomDelBean>> deleteAreaPlace(@Body RequestBody json);

    /**
     * 房间信息
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("getAreaPlace")
    Observable<Bean<List<RoomBean>>> getAreaPlace(@Body RequestBody json);

    /**
     * 修改手机号
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("updateUserPhone")
    Observable<Bean<Object>> updateUserPhone(@Body RequestBody json);

    /**
     * 修改密码
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("setLoginPassword")
    Observable<Bean<Object>> setLoginPassword(@Body RequestBody json);

    /**
     * 团课管理
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("courseManagerByArea")
    Observable<Bean<ManagerBean>> courseManagerByArea(@Body RequestBody json);

    /**
     * 取消邀请
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("cancelCourseByArea")
    Observable<Bean<Object>> cancelCourseByArea(@Body RequestBody json);

    /**
     * 团课申请 同意/拒绝
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("agreeCourseByArea")
    Observable<Bean<Object>> agreeCourseByArea(@Body RequestBody json);

    /**
     * 已创建的团课 邀请教练
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("inviteTeachers")
    Observable<Bean<Object>> inviteTeachers(@Body RequestBody json);
}
