package com.serverless.util;


import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.fnf.model.v20190315.*;
import com.aliyuncs.profile.DefaultProfile;

import java.util.List;

/**
 * 功能描述 : TODO
 *
 * @Author : wang hui
 * @Email : 793147654@qq.com
 * @Date : 10/25/21 8:08 PM
 */
public class WorkStreamUtils {
    static String flowName = "wh-check-workstream";
    static String execName = "";
    static DefaultProfile profile = DefaultProfile.getProfile(
            "cn-hangzhou",
            "",
            "");
    static IAcsClient client = new DefaultAcsClient(profile);

    public static StartExecutionResponse startExecution(String id) {
        StartExecutionRequest request = new StartExecutionRequest();
        request.setFlowName(flowName);
        JSONObject input = new JSONObject();
        input.put("id", id);
        request.setInput(input.toJSONString());
        StartExecutionResponse startExecutionResponse = null;
        try {
            startExecutionResponse = client.getAcsResponse(request);
        } catch (ClientException e) {
            System.out.println(e);
        }
        return  startExecutionResponse;
    }

    static GetExecutionHistoryResponse getExecutionHistory() throws ClientException {
        GetExecutionHistoryRequest request = new GetExecutionHistoryRequest();

        request.setFlowName(flowName);
        request.setExecutionName(execName);
        return client.getAcsResponse(request);
    }

    static DescribeExecutionResponse describeExecution() throws ClientException {
        DescribeExecutionRequest request = new DescribeExecutionRequest();
        request.setFlowName(flowName);
        request.setExecutionName(execName);
        return client.getAcsResponse(request);
    }

    public static void main(String[] args) {
        // Create DefaultAcsClient


        try {
//            // Start Execution
            StartExecutionResponse startExeResp = startExecution("xx");
            execName = startExeResp.getName();
            System.out.println(startExeResp);

            int waitTimes = 0;
            DescribeExecutionResponse describeExecutionResponse = describeExecution();
            while (true) {
                 describeExecutionResponse = describeExecution();
                if ("Succeeded".equals(describeExecutionResponse.getStatus())) {
                    break;
                }
                try {
                    Thread.sleep(100);
                    waitTimes += 100;
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
                if (waitTimes >= 100 * 10 * 50) {
                    // 流程执行失败
                    return;
                }
            }

            GetExecutionHistoryResponse getExecutionHistory = getExecutionHistory();
            List<GetExecutionHistoryResponse.EventsItem> eventsItems = getExecutionHistory.getEvents();
            GetExecutionHistoryResponse.EventsItem eventsItem = eventsItems.get(eventsItems.size() - 1);

            System.out.println(getExecutionHistory);
            System.out.println(eventsItem.getType());
        } catch (ClientException e) {
            System.out.println(e);
        }
    }
}
