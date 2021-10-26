package com.serverless.util;

import com.alicloud.openservices.tablestore.SyncClient;
import com.alicloud.openservices.tablestore.model.*;
import com.serverless.xiangyun.apply.ApplyPeopleInfo;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 功能描述 : TODO
 *
 * @Author : wang hui
 * @Email : 793147654@qq.com
 * @Date : 10/15/21 3:38 PM
 */
public class TableStoreUtils {
    private static final String endPoint = "https://xy-table-store.cn-hangzhou.ots.aliyuncs.com";
    private static final String accessId = "LTAI5tSP1toRuFZ6DPzrkaKT";
    private static final String accessKey = "YsFd9TNM6DtM1cZCIvbGiIbpkEFQ6I";
    private static final String instanceName = "xy-table-store";
    private static final String applyPeopleTableName = "ApplyPeopleInfo";
    public static SyncClient client = new SyncClient(endPoint, accessId, accessKey,
            instanceName);


    public static void putApplyPeople(ApplyPeopleInfo applyPeopleInfo) throws IllegalAccessException {
        // 构造主键
        PrimaryKeyBuilder primaryKeyBuilder = PrimaryKeyBuilder.createPrimaryKeyBuilder();
        primaryKeyBuilder.addPrimaryKeyColumn("id", PrimaryKeyValue.fromString(applyPeopleInfo.getId()));
        PrimaryKey primaryKey = primaryKeyBuilder.build();

        RowPutChange rowPutChange = new RowPutChange(applyPeopleTableName, primaryKey);

        //加入一些属性列
        long ts = System.currentTimeMillis();
        Field[] declaredFields = applyPeopleInfo.getClass().getDeclaredFields();
        for (Field field :
                declaredFields) {
            if ("id".equals(field.getName())) {
                continue;
            }
            field.setAccessible(true);
            if ("limit".equals(field.getName())) {
                rowPutChange.addColumn(new Column(field.getName(), ColumnValue.fromLong(-1), ts));
            } else {
                rowPutChange.addColumn(new Column(field.getName(), ColumnValue.fromString(field.get(applyPeopleInfo).toString()), ts));
            }
        }
        client.putRow(new PutRowRequest(rowPutChange));

    }

    public static Row getApplyPeopleRowById(String pkValue) {
        // 构造主键
        PrimaryKeyBuilder primaryKeyBuilder = PrimaryKeyBuilder.createPrimaryKeyBuilder();
        primaryKeyBuilder.addPrimaryKeyColumn("id", PrimaryKeyValue.fromString(pkValue));
        PrimaryKey primaryKey = primaryKeyBuilder.build();

        // 读一行
        SingleRowQueryCriteria criteria = new SingleRowQueryCriteria(applyPeopleTableName, primaryKey);
        // 设置读取最新版本
        criteria.setMaxVersions(1);
        GetRowResponse getRowResponse = client.getRow(new GetRowRequest(criteria));
        return getRowResponse.getRow();

    }

    public static ColumnValue getApplyPeopleCulumnValue(String pkValue,String columnName) {
        Row row = getApplyPeopleRowById(pkValue);

        List<Column> columns =  row.getColumn(columnName);
        return columns.get(0).getValue();

    }

    public static long getApplyPeopleLimit(String pkValue) {

        return getApplyPeopleCulumnValue(pkValue,"limit").asLong();

    }

    public static String getApplyPeopleNameByRow(Row row) {

        List<Column> columns =  row.getColumn("name");
        return columns.get(0).getValue().asString();

    }

    public static String getApplyPeoplePhoneByRow(Row row) {

        List<Column> columns =  row.getColumn("phone");
        return columns.get(0).getValue().asString();

    }



    /***
     * TODO
     * @Param : String idType;
     *     String id;
     *     String name;
     *     String namePinyin;
     *     String idStartDate;
     *     String idValidity;
     *     String placeDomicile;
     *     String phone;
     *     String verificationCode;
     * @Return : void
     */
    public static void createApplyPeopleTable() {
        TableMeta tableMeta = new TableMeta(applyPeopleTableName);
        tableMeta.addPrimaryKeyColumn(new PrimaryKeySchema("id", PrimaryKeyType.STRING));
        tableMeta.addDefinedColumn("name", DefinedColumnType.STRING);
        tableMeta.addDefinedColumn("idType", DefinedColumnType.STRING);
        tableMeta.addDefinedColumn("namePinyin", DefinedColumnType.STRING);
        tableMeta.addDefinedColumn("idStartDate", DefinedColumnType.STRING);
        tableMeta.addDefinedColumn("idValidity", DefinedColumnType.STRING);
        tableMeta.addDefinedColumn("placeDomicile", DefinedColumnType.STRING);
        tableMeta.addDefinedColumn("phone", DefinedColumnType.STRING);
        tableMeta.addDefinedColumn("verificationCode", DefinedColumnType.STRING);
        tableMeta.addDefinedColumn("limit", DefinedColumnType.INTEGER);

        // 数据的过期时间, 单位秒, -1代表永不过期. 假如设置过期时间为一年, 即为 365 * 24 * 3600.
        int timeToLive = -1;
        // 保存的最大版本数, 设置为1即代表每列上最多保存一个版本(保存最新的版本).
        int maxVersions = 1;

        TableOptions tableOptions = new TableOptions(timeToLive, maxVersions);

        CreateTableRequest request = new CreateTableRequest(tableMeta, tableOptions);

        client.createTable(request);
    }

    public static void main(String[] args) {
//        createApplyPeopleTable();
//        deleteTable(client,applyPeopleTableName);
        ApplyPeopleInfo applyPeopleInfo = new ApplyPeopleInfo();
        applyPeopleInfo.setId("411421199610257615");
        applyPeopleInfo.setIdStartDate("2020-10-01");
        applyPeopleInfo.setIdType("1");
        applyPeopleInfo.setIdValidity("10");
        applyPeopleInfo.setName("王辉");
        applyPeopleInfo.setNamePinyin("wanghui");
        applyPeopleInfo.setPhone("15638896087");
        applyPeopleInfo.setPlaceDomicile("郑州市金水区");
        applyPeopleInfo.setVerificationCode("257615");
//        updateLimit("411421199610257615",999);
//        putApplyPeople(applyPeopleInfo);
        UpdateRowResponse updateRowResponse= updateLimit("411421199610257615",99999);
        System.out.println(updateRowResponse);
    }

    private static void deleteTable(SyncClient client, String tableName) {
        DeleteTableRequest request = new DeleteTableRequest(tableName);
        client.deleteTable(request);
    }

    public static UpdateRowResponse updateLimit(String pkValue,Integer limit) {
        // 构造主键
        PrimaryKeyBuilder primaryKeyBuilder = PrimaryKeyBuilder.createPrimaryKeyBuilder();
        primaryKeyBuilder.addPrimaryKeyColumn("id", PrimaryKeyValue.fromString(pkValue));
        PrimaryKey primaryKey = primaryKeyBuilder.build();

        RowUpdateChange rowUpdateChange = new RowUpdateChange(applyPeopleTableName, primaryKey);

        // 更新一些列
        rowUpdateChange.put(new Column("limit", ColumnValue.fromLong(limit)));

        return client.updateRow(new UpdateRowRequest(rowUpdateChange));
    }
}
