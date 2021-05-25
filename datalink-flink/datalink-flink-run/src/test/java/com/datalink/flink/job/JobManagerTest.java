package com.datalink.flink.job;

import com.datalink.flink.executor.Executor;
import com.datalink.flink.executor.ExecutorSetting;
import com.datalink.flink.result.RunResult;
import com.datalink.flink.result.SubmitResult;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * JobManagerTest
 *
 * @author qiwenkai
 * @since 2021/5/25 19:47
 **/
public class JobManagerTest {

    @Test
    public void runJobTest(){
        JobManager jobManager = new JobManager("10.1.51.25",8081,"test",100);
        String sql ="tableName:=DWD_FIRSTPAGE_ICU_PUBLIC;\n" +
                "CREATE TABLE ${tableName} ( rowkey STRING ,f ROW<HOSPITAL_NUM STRING,ICU_TYPE STRING,IN_ICU_DATET STRING,OUT_ICU_DATET STRING,TOTAL_HOURS STRING,SERIAL_NO STRING> ,\n" +
                "   PRIMARY KEY (rowkey) NOT ENFORCED )WITH( 'connector'  = 'hbase-1.4', 'table-name'  = '${tableName}', 'null-string-literal'  = '', 'zookeeper.quorum'  = 'dwrj5124:2181,dwrj5125:2181,dwrj5126:2181' );\n" +
                "show tables;\n" +
                "\n" +
                "select HOSPITAL_NUM ,ICU_TYPE ,IN_ICU_DATET  from ${tableName} limit 2;";
        RunResult result = jobManager.execute(sql);
        System.out.println(result.isSuccess());
    }

    @Test
    public void runJobSessionTest(){
        JobManager jobManager = new JobManager("10.1.51.25",8081,"test",100);
//        String sql = "select HOSPITAL_NUM ,ICU_TYPE ,IN_ICU_DATET  from ${tableName} limit 2;";
        String sql ="tableName:=DWD_FIRSTPAGE_ICU_PUBLIC;\n" +
                "CREATE TABLE ${tableName} ( rowkey STRING ,f ROW<HOSPITAL_NUM STRING,ICU_TYPE STRING,IN_ICU_DATET STRING,OUT_ICU_DATET STRING,TOTAL_HOURS STRING,SERIAL_NO STRING> ,\n" +
                "   PRIMARY KEY (rowkey) NOT ENFORCED )WITH( 'connector'  = 'hbase-1.4', 'table-name'  = '${tableName}', 'null-string-literal'  = '', 'zookeeper.quorum'  = 'dwrj5124:2181,dwrj5125:2181,dwrj5126:2181' );\n" +
                "show tables;\n" ;
        RunResult result = jobManager.execute(sql);
        System.out.println(result.isSuccess());
        JobManager jobManager2 = new JobManager("10.1.51.25",8081,"test2",100);
        String sql2 = "show tables";
        RunResult result2 = jobManager2.execute(sql2);
        System.out.println(result2.isSuccess());
        JobManager jobManager3 = new JobManager("10.1.51.25",8081,"test",100);
        String sql3 = "show tables";
        RunResult result3 = jobManager3.execute(sql3);
        System.out.println(result3.isSuccess());
    }


    @Test
    public void submitJobTest(){
        JobManager jobManager = new JobManager("10.1.51.25",8081,"test2",100);
        String sql1 ="CREATE TABLE DWD_FIRSTPAGE_ICU_PUBLIC ( rowkey STRING ,f ROW<HOSPITAL_NUM STRING,ICU_TYPE STRING,IN_ICU_DATET STRING,OUT_ICU_DATET STRING,TOTAL_HOURS STRING,SERIAL_NO STRING> ,PRIMARY KEY (rowkey) NOT ENFORCED )WITH( 'connector'  = 'hbase-1.4', 'table-name'  = 'DWD_FIRSTPAGE_ICU_PUBLIC', 'null-string-literal'  = '', 'zookeeper.quorum'  = 'dwrj5124:2181,dwrj5125:2181,dwrj5126:2181' )";
        String sql2 = "CREATE TABLE ODS_EMR_PATI_FIRSTPAGE_ICU ( rowkey STRING ,f ROW<ZYLSH STRING,ZYCS STRING,ZZJHBFLX STRING,JZZJHSSJ STRING,CZZJHSSJ STRING,HJ STRING,XSSX STRING> ,PRIMARY KEY (rowkey) NOT ENFORCED )WITH( 'connector'  = 'hbase-1.4', 'table-name'  = 'ODS_EMR_PATI_FIRSTPAGE_ICU', 'null-string-literal'  = '', 'zookeeper.quorum'  = 'dwrj5124:2181,dwrj5125:2181,dwrj5126:2181' )";
        String sql3 = "tableName:=ODS_EMR_PATI_FIRSTPAGE_ICU;\n" +
                "INSERT INTO DWD_FIRSTPAGE_ICU_PUBLIC SELECT HOSPITAL_NUM || IN_ICU_DATET, ROW( HOSPITAL_NUM, ICU_TYPE, IN_ICU_DATET, OUT_ICU_DATET, TOTAL_HOURS, SERIAL_NO) AS f FROM ( SELECT ZYLSH || ZYCS AS HOSPITAL_NUM, ZZJHBFLX AS ICU_TYPE, JZZJHSSJ AS IN_ICU_DATET, CZZJHSSJ AS OUT_ICU_DATET, HJ AS TOTAL_HOURS, XSSX AS SERIAL_NO FROM ${tableName} where ZYLSH || ZYCS IS NOT NULL AND JZZJHSSJ IS NOT NULL)";
        List<String> sqls = new ArrayList<>();
        sqls.add(sql1);
        sqls.add(sql2);
        sqls.add(sql3);
        ExecutorSetting setting = new ExecutorSetting(Executor.REMOTE);
        SubmitResult result = jobManager.submit(sqls, setting);
        System.out.println(result.isSuccess());
    }

    @Test
    public void submitJobTest2(){
        JobManager jobManager = new JobManager("192.168.123.157",8081,"test2",100);
        String sql1 ="CREATE TABLE student (\n" +
                "  sid INT,\n" +
                "  name STRING,\n" +
                "  PRIMARY KEY (sid) NOT ENFORCED\n" +
                ") WITH (\n" +
                "   'connector' = 'jdbc',\n" +
                "   'url' = 'jdbc:mysql://192.168.24.1:3306/data?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useSSL=false&zeroDateTimeBehavior=convertToNull&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true',\n" +
                "   'username'='datalink',\n" +
                "   'password'='datalink',\n" +
                "   'table-name' = 'student'\n" +
                ");";
        String sql2 ="CREATE TABLE man (\n" +
                "  pid INT,\n" +
                "  name STRING,\n" +
                "  PRIMARY KEY (pid) NOT ENFORCED\n" +
                ") WITH (\n" +
                "   'connector' = 'jdbc',\n" +
                "   'url' = 'jdbc:mysql://192.168.24.1:3306/data?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useSSL=false&zeroDateTimeBehavior=convertToNull&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true',\n" +
                "   'username'='datalink',\n" +
                "   'password'='datalink',\n" +
                "   'table-name' = 'man'\n" +
                ");";
        String sql3 = "INSERT INTO man SELECT sid as pid,name from student";
        List<String> sqls = new ArrayList<>();
        sqls.add(sql1);
        sqls.add(sql2);
        sqls.add(sql3);
        ExecutorSetting setting = new ExecutorSetting(Executor.REMOTE);
        SubmitResult result = jobManager.submit(sqls, setting);
        System.out.println(result.isSuccess());
    }
}
