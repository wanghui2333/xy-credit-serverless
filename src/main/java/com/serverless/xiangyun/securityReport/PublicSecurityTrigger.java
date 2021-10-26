package com.serverless.xiangyun.securityReport;


import com.alibaba.fastjson.JSONObject;
import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.HttpRequestHandler;
import com.aliyun.fc.runtime.StreamRequestHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 功能描述 : TODO
 *
 * @Author : wang hui
 * @Email : 793147654@qq.com
 * @Date : 10/15/21 1:42 PM
 */
public class PublicSecurityTrigger implements StreamRequestHandler {


    @Override
    public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
        byte[] read = new byte[1024];

        input.read(read);

        JSONObject resp = new JSONObject();
        resp.put("code", 0);
        resp.put("message", "公安核查通过！");
        output.write(resp.toJSONString().getBytes());
    }
}
