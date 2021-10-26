package com.serverless.xiangyun.apply;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.HttpRequestHandler;
import com.serverless.util.OSSUtils;
import com.serverless.util.TableStoreUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

/**
 * 功能描述 : 信用卡申请进度
 *
 * @Author : wang hui
 * @Email : 793147654@qq.com
 * @Date : 10/25/21 10:01 AM
 */
public class ApplyProgress implements HttpRequestHandler {

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response, Context context) throws IOException, ServletException {
        String id = request.getParameter("id");
        String phone = request.getParameter("phone");
        String verificationCode = request.getParameter("verificationCode");

        JSONObject resp = new JSONObject();
        resp.put("code", 0);
        resp.put("message", "查询成功！");
        JSONObject info = new JSONObject();
        long limit = TableStoreUtils.getApplyPeopleLimit(id);
        if (limit >= 0){
            info.put("code", ApplyProgressEnum.SUCCESS.getCode());
            info.put("message", ApplyProgressEnum.SUCCESS.getMessage());
            info.put("limit", limit);
        }else if (limit == -1){
            info.put("code", ApplyProgressEnum.ONGOING.getCode());
            info.put("message", ApplyProgressEnum.ONGOING.getMessage());
        }else {
            info.put("code", ApplyProgressEnum.REFUSE.getCode());
            info.put("message", ApplyProgressEnum.REFUSE.getMessage());
        }
        resp.put("info", info);

        OutputStream out = response.getOutputStream();
        out.write(resp.toJSONString().getBytes());
        out.flush();
        out.close();

    }

}
