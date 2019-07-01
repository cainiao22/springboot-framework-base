package com.qding.bigdata.config.columntype;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lzs on 2018/5/18.
 */

@Slf4j
public class ColumnTypeConfigure {

    protected static JdbcConfig commonJdbcConf;

    private static Map<String, JdbcConfig> map = new HashMap<>();

    private ColumnTypeConfigure(){

    }

    static {
        try {
            InputStream inputStream = ColumnTypeConfigure.class.getClassLoader().getResourceAsStream("jdbc/jdbc-templete.json");
            String commenText = IOUtils.toString(inputStream, "utf8");
            commonJdbcConf= JSON.parseObject(commenText,JdbcConfig.class);
        } catch (IOException e) {
            log.error("导入列转换模板失败", e);
        }

        try {
            URL resource = ColumnTypeConfigure.class.getClassLoader().getResource("jdbc/jdbc-templete.json");
            final File jdbcDir = ResourceUtils.getFile(resource).getParentFile();
            File[] files = jdbcDir.listFiles(pathname ->
                pathname.getName().startsWith("jdbc-types-") && pathname.getName().endsWith(".json"));

            for (File file : files) {
                JdbcConfig commonJdConf = commonJdbcConf.clone();
                InputStream inputStream = new FileInputStream(file);
                String jsonStr = IOUtils.toString(inputStream, "utf8");
                JdbcConfig temp = JSON.parseObject(jsonStr, JdbcConfig.class);

                //公共type map
                HashMap<String, String> commonWriter = commonJdConf.getWriterTypes();
                HashMap<String, String> commonReader = commonJdConf.getReaderTypes();
                //本地type map
                HashMap<String, String> localWriter = temp.getWriterTypes();
                HashMap<String, String> localReader = temp.getReaderTypes();

                commonWriter.putAll(localWriter);
                commonReader.putAll(localReader);
                commonJdConf.setJdbcType(temp.getJdbcType());

                map.put(commonJdConf.getJdbcType(), commonJdConf);
            }


        } catch (IOException | CloneNotSupportedException e) {
            log.error("导入列转换模板失败", e);
        }
    }




    public static JdbcConfig getJdbcConfig(String jdbcType){
        JdbcConfig jdbcConfig = map.get(jdbcType);
        return jdbcConfig == null ? commonJdbcConf : jdbcConfig;
    }

}
