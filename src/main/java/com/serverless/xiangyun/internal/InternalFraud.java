package com.serverless.xiangyun.internal;


import com.alibaba.fastjson.JSONObject;
import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.HttpRequestHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 功能描述 : TODO
 *
 * @Author : wang hui
 * @Email : 793147654@qq.com
 * @Date : 10/15/21 1:42 PM
 */
public class InternalFraud implements HttpRequestHandler {

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response, Context context) throws IOException, ServletException {

        String id = request.getParameter("id");

        JSONObject resp = new JSONObject();
        resp.put("code", 0);
        resp.put("message", "内部征信,反欺诈查询通过！");

        OutputStream out = response.getOutputStream();
        out.write(resp.toJSONString().getBytes());
        out.flush();
        out.close();
    }


}
