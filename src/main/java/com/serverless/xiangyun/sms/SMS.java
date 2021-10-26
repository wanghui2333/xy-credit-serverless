package com.serverless.xiangyun.sms;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.HttpRequestHandler;
import com.serverless.util.OSSUtils;
import com.serverless.xiangyun.apply.ApplyPeopleInfo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

/**
 * 功能描述 : TODO
 *
 * @Author : wang hui
 * @Email : 793147654@qq.com
 * @Date : 10/15/21 1:42 PM
 */
public class SMS implements HttpRequestHandler {

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response, Context context) throws IOException, ServletException {

        String id = request.getParameter("id");
        String phone = request.getParameter("phone");

        JSONObject resp = new JSONObject();
        resp.put("code", 0);
        resp.put("message", "短信发送成功！");
        resp.put("verificationCode", id.substring(id.length() - 6));

        OutputStream out = response.getOutputStream();
        out.write(resp.toJSONString().getBytes());
        out.flush();
        out.close();
    }


}
