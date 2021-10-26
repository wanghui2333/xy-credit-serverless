package com.serverless.xiangyun.approve;


import com.alibaba.fastjson.JSONObject;
import com.alicloud.openservices.tablestore.model.Row;
import com.alicloud.openservices.tablestore.model.UpdateRowResponse;
import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.StreamRequestHandler;
import com.serverless.util.AliSmsUtils;
import com.serverless.util.TableStoreUtils;

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
public class ApproveTrigger implements StreamRequestHandler {

    @Override
    public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
        byte[] buffer = new byte[1024];

        int length = input.read(buffer);

        StringBuilder sb = new StringBuilder();
        while(length != -1){
            sb.append(new String(buffer, 0 , length));
            length = input.read(buffer);
        }
        JSONObject request = JSONObject.parseObject(sb.toString());

        String id = request.getString("id");
        int limit = 100000;
        TableStoreUtils.updateLimit(id,limit);
        // 发送短信
        Row row = TableStoreUtils.getApplyPeopleRowById(id);

        AliSmsUtils.sendApproveResult(TableStoreUtils.getApplyPeopleNameByRow(row),TableStoreUtils.getApplyPeoplePhoneByRow(row));
        JSONObject resp = new JSONObject();
        resp.put("code", 0);
        resp.put("message", "信用卡审批通过！");
        output.write(resp.toJSONString().getBytes());
    }

}
