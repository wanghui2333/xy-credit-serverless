package com.serverless.xiangyun.apply;

/**
 * 功能描述 : 信用卡申请进度枚举
 *
 * @Author : wang hui
 * @Email : 793147654@qq.com
 * @Date : 10/25/21 10:01 AM
 */
public enum ApplyProgressEnum {
    /***
     * 审核中
     */
    ONGOING(0, "审核中，请耐心等待，预计三个工作日审核完成。"),
    /***
     * 申请通过
     */
    SUCCESS(1, "申请通过!"),
    /***
     * 申请拒绝
     */
    REFUSE(2, "申请拒绝:征信或个人信息暂不符合我行信用卡申请标准，请至营业网点办理，谢谢。");

    private Integer code;
    private String message;

    ApplyProgressEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
