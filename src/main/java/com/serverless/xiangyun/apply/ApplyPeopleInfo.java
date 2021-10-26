package com.serverless.xiangyun.apply;

/**
 * 功能描述 : TODO
 *
 * @Author : wang hui
 * @Email : 793147654@qq.com
 * @Date : 10/14/21 1:37 PM
 */
public class ApplyPeopleInfo {
    /***
     * TODO
     * @Param : idType:证件类型（默认0，身份证，只支持身份证,演示使用）,
     * 	id:身份证号,
     * 	name:姓名,
     * 	namePinyin:姓名拼音,
     * 	idStartDate:证件起始日期,
     * 	idValidity:证件有效期,
     * 	placeDomicile:户籍地址,
     * 	phone:姓名拼音,
     * 	verificationCode:手机验证码
     * @Return : 
     */
    String idType;
    String id;
    String name;
    String namePinyin;
    String idStartDate;
    String idValidity;
    String placeDomicile;
    String phone;
    String verificationCode;
    Integer limit = 0;

    public ApplyPeopleInfo() {
    }

    public ApplyPeopleInfo(String idType, String id, String name, String namePinyin, String idStartDate, String idValidity, String placeDomicile, String phone, String verificationCode, Integer limit) {
        this.idType = idType;
        this.id = id;
        this.name = name;
        this.namePinyin = namePinyin;
        this.idStartDate = idStartDate;
        this.idValidity = idValidity;
        this.placeDomicile = placeDomicile;
        this.phone = phone;
        this.verificationCode = verificationCode;
        this.limit = limit;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamePinyin() {
        return namePinyin;
    }

    public void setNamePinyin(String namePinyin) {
        this.namePinyin = namePinyin;
    }

    public String getIdStartDate() {
        return idStartDate;
    }

    public void setIdStartDate(String idStartDate) {
        this.idStartDate = idStartDate;
    }

    public String getIdValidity() {
        return idValidity;
    }

    public void setIdValidity(String idValidity) {
        this.idValidity = idValidity;
    }

    public String getPlaceDomicile() {
        return placeDomicile;
    }

    public void setPlaceDomicile(String placeDomicile) {
        this.placeDomicile = placeDomicile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "ApplyPeopleInfo{" +
                "idType='" + idType + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", namePinyin='" + namePinyin + '\'' +
                ", idStartDate='" + idStartDate + '\'' +
                ", idValidity='" + idValidity + '\'' +
                ", placeDomicile='" + placeDomicile + '\'' +
                ", phone='" + phone + '\'' +
                ", verificationCode='" + verificationCode + '\'' +
                ", limit=" + limit +
                '}';
    }
}
