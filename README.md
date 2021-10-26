### 一、前言

本项目仅用于参加阿里Serverless比赛使用（赛道三），涉及阿里系产品有：

1、FC（函数计算）

2、Serverless工作流

3、OSS（对象存储，用于存储身份证等照片信息）

4、TableStore（表格存储，用于存储常规个人信息等）

5、短信服务

<b>注：具体项目详情及方案请查看ppt材料</b>

### 二、项目整体流程

1. 用户点击信用卡申请页面，拍照或相册选取身份证照片，进行OCR识别并自动填充申请表单； 

2. 申请信息无误，获取验证码等，点击提交； 

3. 后端接收请求： 

    3.1 处理身份证照片，上传阿里OSS； 

    3.2 保存用户申请资料，上传阿里TableStore； 

    3.3 触发serverless 工作流： 

    ​	3.3.1 根据用户id，调用外部核查接口，并行处理用户征信等相关信息： 

    ​	3.3.2 根据三家核查接口返回结果，确定审核结果及审批额度，存库； 

    ​	3.3.3 短信通知用户结果，用户可点击短信链接查看审核详情。

### 三、项目相关图

#### 3.1 业务流程图

![](assets/%E4%B8%9A%E5%8A%A1%E6%B5%81%E7%A8%8B%E5%9B%BE.png)

#### 3.2 业务架构图

![](assets/%E4%B8%9A%E5%8A%A1%E6%9E%B6%E6%9E%84%E5%9B%BE.png)

#### 3.3 Serverless工作流图

![](assets/Serverless%E5%B7%A5%E4%BD%9C%E6%B5%81%E5%9B%BE.png)

### 四、项目演示

<b>注：请使用手机操作</b>

#### 4.1 申请

![](assets/%E7%94%B3%E8%AF%B71.png)

![](assets/%E7%94%B3%E8%AF%B72.png)

![](assets/%E7%94%B3%E8%AF%B73.png)

![](assets/%E7%94%B3%E8%AF%B74.png)

#### 4.2 后台处理

##### 存储类相关：![身份证存储](assets/%E8%BA%AB%E4%BB%BD%E8%AF%81%E5%AD%98%E5%82%A8.png)

![](assets/%E4%B8%9A%E5%8A%A1%E6%95%B0%E6%8D%AE%E5%AD%98%E5%82%A8.png)

##### 工作流相关：

![](assets/%E5%B7%A5%E4%BD%9C%E6%B5%81%E6%89%A7%E8%A1%8C%E5%9B%BE.png)

![image-20211026145627328](assets//image-20211026145627328.png)

![](assets/%E5%B7%A5%E4%BD%9C%E6%B5%81%E4%BA%8B%E4%BB%B6%E8%AE%B0%E5%BD%95%E8%A1%A8.png)

#### 4.3 进度查询

![](assets/%E8%BF%9B%E5%BA%A6%E6%9F%A5%E8%AF%A2.png)

![](assets/%E6%9F%A5%E8%AF%A2%E7%BB%93%E6%9E%9C.png)

