package com.noplugins.keepfit.android.entity;

import java.util.List;

public class ClassDetailEntity {

    /**
     * course : {"id":62,"courseNum":"GYM19080804881206","gymAreaNum":"GYM19072138381319","genTeacherNum":"","gymPlaceNum":"","courseName":"个风格","type":"1","price":500,"courseType":1,"classType":1,"courseTime":null,"loop":false,"target":1,"difficulty":1,"loopCycle":1,"maxNum":1,"suitPerson":"呵呵","applyNum":null,"courseDes":"凤飞飞","tips":"经济","remark":"","lable":null,"checkStatus":1,"reason":"","startTime":1565956800000,"endTime":1565960400000,"createDate":"2019-08-08 19:16:47","updateDate":"2019-08-08 19:16:47","comeNum":0,"status":1,"deleted":false,"extendBigint01":null,"extendBigint02":null,"extendBigint03":null,"extendBigint04":null,"extendBigint05":null,"extendDec01":null,"extendDec02":null,"extendDec03":null,"extendDec04":null,"extendDec05":null,"extendVar02":null,"extendVar03":null,"extendVar04":null,"extendVar05":null,"extendVar01":null,"placeType":null}
     * teacherList : [{"teacherNum":"Gen123","teacherName":"测试","phone":"123","password":"","card":"","logoUrl":null,"teacherType":null,"tips":null,"skill":"1,2,3,4","serviceDur":null,"createDate":1563862366000,"updateDate":"2019-07-23 14:12:51","deleted":0,"inviteStatus":0}]
     */

    private CourseBean course;
    private List<TeacherListBean> teacherList;

    public CourseBean getCourse() {
        return course;
    }

    public void setCourse(CourseBean course) {
        this.course = course;
    }

    public List<TeacherListBean> getTeacherList() {
        return teacherList;
    }

    public void setTeacherList(List<TeacherListBean> teacherList) {
        this.teacherList = teacherList;
    }

    public static class CourseBean {
        /**
         * id : 62
         * courseNum : GYM19080804881206
         * gymAreaNum : GYM19072138381319
         * genTeacherNum :
         * gymPlaceNum :
         * courseName : 个风格
         * type : 1
         * price : 500
         * courseType : 1
         * classType : 1
         * courseTime : null
         * loop : false
         * target : 1
         * difficulty : 1
         * loopCycle : 1
         * maxNum : 1
         * suitPerson : 呵呵
         * applyNum : null
         * courseDes : 凤飞飞
         * tips : 经济
         * remark :
         * lable : null
         * checkStatus : 1
         * reason :
         * startTime : 1565956800000
         * endTime : 1565960400000
         * createDate : 2019-08-08 19:16:47
         * updateDate : 2019-08-08 19:16:47
         * comeNum : 0
         * status : 1
         * deleted : false
         * extendBigint01 : null
         * extendBigint02 : null
         * extendBigint03 : null
         * extendBigint04 : null
         * extendBigint05 : null
         * extendDec01 : null
         * extendDec02 : null
         * extendDec03 : null
         * extendDec04 : null
         * extendDec05 : null
         * extendVar02 : null
         * extendVar03 : null
         * extendVar04 : null
         * extendVar05 : null
         * extendVar01 : null
         * placeType : null
         */

        private int id;
        private String courseNum;
        private String gymAreaNum;
        private String genTeacherNum;
        private String gymPlaceNum;
        private String courseName;
        private String type;
        private int price;
        private int courseType;
        private int classType;
        private Object courseTime;
        private boolean loop;
        private int target;
        private int difficulty;
        private int loopCycle;
        private int maxNum;
        private String suitPerson;
        private Object applyNum;
        private String courseDes;
        private String tips;
        private String remark;
        private Object lable;
        private int checkStatus;
        private String reason;
        private long startTime;
        private long endTime;
        private String createDate;
        private String updateDate;
        private int comeNum;
        private int status;
        private boolean deleted;
        private Object extendBigint01;
        private Object extendBigint02;
        private Object extendBigint03;
        private Object extendBigint04;
        private Object extendBigint05;
        private Object extendDec01;
        private Object extendDec02;
        private Object extendDec03;
        private Object extendDec04;
        private Object extendDec05;
        private Object extendVar02;
        private Object extendVar03;
        private Object extendVar04;
        private Object extendVar05;
        private Object extendVar01;
        private int placeType;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCourseNum() {
            return courseNum;
        }

        public void setCourseNum(String courseNum) {
            this.courseNum = courseNum;
        }

        public String getGymAreaNum() {
            return gymAreaNum;
        }

        public void setGymAreaNum(String gymAreaNum) {
            this.gymAreaNum = gymAreaNum;
        }

        public String getGenTeacherNum() {
            return genTeacherNum;
        }

        public void setGenTeacherNum(String genTeacherNum) {
            this.genTeacherNum = genTeacherNum;
        }

        public String getGymPlaceNum() {
            return gymPlaceNum;
        }

        public void setGymPlaceNum(String gymPlaceNum) {
            this.gymPlaceNum = gymPlaceNum;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getCourseType() {
            return courseType;
        }

        public void setCourseType(int courseType) {
            this.courseType = courseType;
        }

        public int getClassType() {
            return classType;
        }

        public void setClassType(int classType) {
            this.classType = classType;
        }

        public Object getCourseTime() {
            return courseTime;
        }

        public void setCourseTime(Object courseTime) {
            this.courseTime = courseTime;
        }

        public boolean isLoop() {
            return loop;
        }

        public void setLoop(boolean loop) {
            this.loop = loop;
        }

        public int getTarget() {
            return target;
        }

        public void setTarget(int target) {
            this.target = target;
        }

        public int getDifficulty() {
            return difficulty;
        }

        public void setDifficulty(int difficulty) {
            this.difficulty = difficulty;
        }

        public int getLoopCycle() {
            return loopCycle;
        }

        public void setLoopCycle(int loopCycle) {
            this.loopCycle = loopCycle;
        }

        public int getMaxNum() {
            return maxNum;
        }

        public void setMaxNum(int maxNum) {
            this.maxNum = maxNum;
        }

        public String getSuitPerson() {
            return suitPerson;
        }

        public void setSuitPerson(String suitPerson) {
            this.suitPerson = suitPerson;
        }

        public Object getApplyNum() {
            return applyNum;
        }

        public void setApplyNum(Object applyNum) {
            this.applyNum = applyNum;
        }

        public String getCourseDes() {
            return courseDes;
        }

        public void setCourseDes(String courseDes) {
            this.courseDes = courseDes;
        }

        public String getTips() {
            return tips;
        }

        public void setTips(String tips) {
            this.tips = tips;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public Object getLable() {
            return lable;
        }

        public void setLable(Object lable) {
            this.lable = lable;
        }

        public int getCheckStatus() {
            return checkStatus;
        }

        public void setCheckStatus(int checkStatus) {
            this.checkStatus = checkStatus;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public long getStartTime() {
            return startTime;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }

        public int getComeNum() {
            return comeNum;
        }

        public void setComeNum(int comeNum) {
            this.comeNum = comeNum;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public boolean isDeleted() {
            return deleted;
        }

        public void setDeleted(boolean deleted) {
            this.deleted = deleted;
        }

        public Object getExtendBigint01() {
            return extendBigint01;
        }

        public void setExtendBigint01(Object extendBigint01) {
            this.extendBigint01 = extendBigint01;
        }

        public Object getExtendBigint02() {
            return extendBigint02;
        }

        public void setExtendBigint02(Object extendBigint02) {
            this.extendBigint02 = extendBigint02;
        }

        public Object getExtendBigint03() {
            return extendBigint03;
        }

        public void setExtendBigint03(Object extendBigint03) {
            this.extendBigint03 = extendBigint03;
        }

        public Object getExtendBigint04() {
            return extendBigint04;
        }

        public void setExtendBigint04(Object extendBigint04) {
            this.extendBigint04 = extendBigint04;
        }

        public Object getExtendBigint05() {
            return extendBigint05;
        }

        public void setExtendBigint05(Object extendBigint05) {
            this.extendBigint05 = extendBigint05;
        }

        public Object getExtendDec01() {
            return extendDec01;
        }

        public void setExtendDec01(Object extendDec01) {
            this.extendDec01 = extendDec01;
        }

        public Object getExtendDec02() {
            return extendDec02;
        }

        public void setExtendDec02(Object extendDec02) {
            this.extendDec02 = extendDec02;
        }

        public Object getExtendDec03() {
            return extendDec03;
        }

        public void setExtendDec03(Object extendDec03) {
            this.extendDec03 = extendDec03;
        }

        public Object getExtendDec04() {
            return extendDec04;
        }

        public void setExtendDec04(Object extendDec04) {
            this.extendDec04 = extendDec04;
        }

        public Object getExtendDec05() {
            return extendDec05;
        }

        public void setExtendDec05(Object extendDec05) {
            this.extendDec05 = extendDec05;
        }

        public Object getExtendVar02() {
            return extendVar02;
        }

        public void setExtendVar02(Object extendVar02) {
            this.extendVar02 = extendVar02;
        }

        public Object getExtendVar03() {
            return extendVar03;
        }

        public void setExtendVar03(Object extendVar03) {
            this.extendVar03 = extendVar03;
        }

        public Object getExtendVar04() {
            return extendVar04;
        }

        public void setExtendVar04(Object extendVar04) {
            this.extendVar04 = extendVar04;
        }

        public Object getExtendVar05() {
            return extendVar05;
        }

        public void setExtendVar05(Object extendVar05) {
            this.extendVar05 = extendVar05;
        }

        public Object getExtendVar01() {
            return extendVar01;
        }

        public void setExtendVar01(Object extendVar01) {
            this.extendVar01 = extendVar01;
        }

        public int getPlaceType() {
            return placeType;
        }

        public void setPlaceType(int placeType) {
            this.placeType = placeType;
        }
    }

    public static class TeacherListBean {
        /**
         * teacherNum : Gen123
         * teacherName : 测试
         * phone : 123
         * password :
         * card :
         * logoUrl : null
         * teacherType : null
         * tips : null
         * skill : 1,2,3,4
         * serviceDur : null
         * createDate : 1563862366000
         * updateDate : 2019-07-23 14:12:51
         * deleted : 0
         * inviteStatus : 0
         */

        private String teacherNum;
        private String teacherName;
        private String phone;
        private String password;
        private String card;
        private Object logoUrl;
        private Object teacherType;
        private Object tips;
        private String skill;
        private Object serviceDur;
        private long createDate;
        private String updateDate;
        private int deleted;
        private int inviteStatus;

        public String getTeacherNum() {
            return teacherNum;
        }

        public void setTeacherNum(String teacherNum) {
            this.teacherNum = teacherNum;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getCard() {
            return card;
        }

        public void setCard(String card) {
            this.card = card;
        }

        public Object getLogoUrl() {
            return logoUrl;
        }

        public void setLogoUrl(Object logoUrl) {
            this.logoUrl = logoUrl;
        }

        public Object getTeacherType() {
            return teacherType;
        }

        public void setTeacherType(Object teacherType) {
            this.teacherType = teacherType;
        }

        public Object getTips() {
            return tips;
        }

        public void setTips(Object tips) {
            this.tips = tips;
        }

        public String getSkill() {
            return skill;
        }

        public void setSkill(String skill) {
            this.skill = skill;
        }

        public Object getServiceDur() {
            return serviceDur;
        }

        public void setServiceDur(Object serviceDur) {
            this.serviceDur = serviceDur;
        }

        public long getCreateDate() {
            return createDate;
        }

        public void setCreateDate(long createDate) {
            this.createDate = createDate;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }

        public int getDeleted() {
            return deleted;
        }

        public void setDeleted(int deleted) {
            this.deleted = deleted;
        }

        public int getInviteStatus() {
            return inviteStatus;
        }

        public void setInviteStatus(int inviteStatus) {
            this.inviteStatus = inviteStatus;
        }
    }

}
