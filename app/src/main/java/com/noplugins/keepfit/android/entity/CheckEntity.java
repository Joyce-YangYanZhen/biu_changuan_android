package com.noplugins.keepfit.android.entity;

public class CheckEntity {
    public String getGymAreaNum() {
        return gymAreaNum;
    }

    public void setGymAreaNum(String gymAreaNum) {
        this.gymAreaNum = gymAreaNum;
    }

    private String gymAreaNum;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private int status;

    public String getHaveMember() {
        return haveMember;
    }

    public void setHaveMember(String haveMember) {
        this.haveMember = haveMember;
    }

    private String haveMember;
}
