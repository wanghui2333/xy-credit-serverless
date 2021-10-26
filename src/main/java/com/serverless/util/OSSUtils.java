package com.serverless.util;

/**
 * 功能描述 : TODO
 *
 * @Author : wang hui
 * @Email : 793147654@qq.com
 * @Date : 10/14/21 2:26 PM
 */


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;


public class OSSUtils {

    private static String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";

    private static String accessKeyId = "";
    private static String accessKeySecret = "";


    private static OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

    public static OSS getOssClient() {
        return ossClient;
    }

}
