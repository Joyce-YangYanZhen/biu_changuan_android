package com.noplugins.keepfit.android.entity;

/**
 * Created on 2018/12/20 10:28
 * <p>
 * author lhm
 * <p>
 * Description:
 * <p>
 * Remarks:
 */
public class ItemBean {

    private int place;//限制人数
    private boolean focus;
    private String type_name;//类型
    private String place_num="";
    private int type;

    public String getPlace_num() {
        return place_num;
    }

    public void setPlace_num(String place_num) {
        this.place_num = place_num;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }



    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public boolean isFocus() {
        return focus;
    }

    public void setFocus(boolean focus) {
        this.focus = focus;
    }
}
