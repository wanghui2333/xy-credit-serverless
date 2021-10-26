package com.serverless.xiangyun.apply;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.HttpRequestHandler;
import com.serverless.util.OSSUtils;
import com.serverless.util.TableStoreUtils;
import com.serverless.util.WorkStreamUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;

/**
 * 功能描述 : TODO
 *
 * @Author : wang hui
 * @Email : 793147654@qq.com
 * @Date : 10/14/21 10:01 AM
 */
public class Apply implements HttpRequestHandler {
    private static String bucketName = "zy-id-card";

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response, Context context) throws IOException, ServletException {
        uploadFile(request, "front");
        uploadFile(request, "back");
        String reqInfo = request.getParameter("reqInfo");
        ApplyPeopleInfo applyPeopleInfo = JSON.parseObject(reqInfo, ApplyPeopleInfo.class);

        response.setStatus(200);
        response.setHeader("Content-Type", "application/json;charset=UTF-8");


        try {
            TableStoreUtils.putApplyPeople(applyPeopleInfo);
        } catch (IllegalAccessException e) {
            System.out.println(e);
        }

        // 触发工作流
        WorkStreamUtils.startExecution(applyPeopleInfo.getId());
        JSONObject resp = new JSONObject();
        resp.put("code", 0);
        resp.put("message", "申请成功！");
        resp.put("applyPeopleInfo", applyPeopleInfo);
        OutputStream out = response.getOutputStream();
        out.write(resp.toJSONString().getBytes());
        out.flush();
        out.close();
    }

    void uploadFile(HttpServletRequest request, String formDataFileKey) throws IOException, ServletException {
        //获取文件,参数为前台的name
        Part part = request.getPart(formDataFileKey);
        //判断是否选择了文件
        if (part.getSize() == 0) {
            return;    //不再执行后续操作
        }
        //获取文件名，获取到文件名的格式如：a.jpg
        String fileName = part.getSubmittedFileName();

        OSSUtils.getOssClient().putObject(bucketName, fileName, part.getInputStream());
    }
}
