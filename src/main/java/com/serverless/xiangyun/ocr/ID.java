package com.serverless.xiangyun.ocr;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.HttpRequestHandler;
import com.serverless.util.HttpUtils;
import com.serverless.util.RequestUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述 : TODO
 *
 * @Author : wang hui
 * @Email : 793147654@qq.com
 * @Date : 10/15/21 11:30 AM
 */
public class ID implements HttpRequestHandler {
    private static final String host = "http://dm-51.data.aliyun.com";
    private static final String path = "/rest/160601/ocr/ocr_idcard.json";
    private static final String appcode = "3653921cd2e24394946dbcc5b45dc628";
    private static final String method = "POST";
    private static final Logger logger = LoggerFactory.getLogger(ID.class);

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response, Context context) throws IOException, ServletException {

        JSONObject resBody = RequestUtils.getBody(request);

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/json; charset=UTF-8");

        Map<String, String> querys = new HashMap<>();
        // 图像base64编码
        String imgBase64 = resBody.getString("image");
        String side = resBody.getString("side");

        //configure配置
        JSONObject configObj = new JSONObject();
        configObj.put("side", side);

        String config_str = configObj.toString();

        // 拼装请求body的json字符串
        JSONObject requestObj = new JSONObject();
        requestObj.put("image", imgBase64);
        if (configObj.size() > 0) {
            requestObj.put("configure", config_str);
        }
        String bodys = requestObj.toString();
        JSONObject resp = new JSONObject();
        try {
            HttpResponse ocrResponse = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            int stat = ocrResponse.getStatusLine().getStatusCode();

            if (stat != 200) {
                resp.put("code", -1);
                resp.put("message", "身份证识别发生异常！");
                JSONObject ocrInfoJson = new JSONObject();
                ocrInfoJson.put("Http code: ", stat);
                ocrInfoJson.put("http header error msg: ", ocrResponse.getFirstHeader("X-Ca-Error-Message"));
                ocrInfoJson.put("Http body error msg:", EntityUtils.toString(ocrResponse.getEntity()));
                resp.put("ocrInfo", ocrInfoJson);
                logger.warn("Http code: " + stat);
                logger.warn("http header error msg: " + ocrResponse.getFirstHeader("X-Ca-Error-Message"));
                logger.warn("Http body error msg:" + EntityUtils.toString(ocrResponse.getEntity()));
                OutputStream out = response.getOutputStream();
                out.write(resp.toJSONString().getBytes());
                out.flush();
                out.close();
            }
            String res = EntityUtils.toString(ocrResponse.getEntity());
            JSONObject resObj = JSON.parseObject(res);
            response.setStatus(200);
            response.setHeader("Content-Type", "application/json;charset=UTF-8");

            resp.put("code", 0);
            resp.put("message", "身份证识别成功！");
            resp.put("ocrInfo", resObj);
            OutputStream out = response.getOutputStream();
            out.write(resp.toJSONString().getBytes());
            out.flush();
            out.close();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

    }
}
