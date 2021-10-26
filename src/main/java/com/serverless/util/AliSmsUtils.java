package com.serverless.util;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.*;
import com.aliyun.teaopenapi.models.*;
/**
 * 功能描述 : TODO
 *
 * @Author : wang hui
 * @Email : 793147654@qq.com
 * @Date : 10/26/21 1:01 PM
 */
public class AliSmsUtils {
    public static final String ACCESS_KEY_ID = "";
    public static final String ACCESS_KEY_SECRET = "";
    public static final Client client = createClient();

    public static Client createClient() {
        Config config = new Config()
                .setAccessKeyId(ACCESS_KEY_ID)
                .setAccessKeySecret(ACCESS_KEY_SECRET);
        // 访问的域名
        config.endpoint = "dysmsapi.aliyuncs.com";
        Client client = null;
        try {
            client = new Client(config);
        } catch (Exception e) {
            System.out.println(e);
        }
        return client;
    }

    public static SendSmsResponse sendApproveResult(String name,String phone){
        JSONObject templateParam = new JSONObject();
        templateParam.put("name",name);
        templateParam.put("path","credit-serverless/#/view/status");
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setPhoneNumbers(phone)
                .setSignName("[测试专用]阿里云通信")
                .setTemplateCode("[测试专用]阿里云通信测试模版")
                .setTemplateParam(templateParam.toJSONString());
        SendSmsResponse sendSmsResponse = null;
        try {
            sendSmsResponse = client.sendSms(sendSmsRequest);
        } catch (Exception e) {
            System.out.println(e);
        }
        return sendSmsResponse;
    }

}
