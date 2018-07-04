package com.alibaba.datax.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by YZP on 2018/5/10.
 */
public class StringUtil {

    private static Pattern mysqlPattern = Pattern.compile("jdbc:mysql://(.+):\\d+/(.+)");
    /**
     * 注意：实现了从 mysql中识别出数据库信息.未识别到则返回 null.
     * @anthor yangzepeng
     */
    public static String parseDataNameFromJdbcUrl(String jdbcUrl) {
        Matcher mysql = mysqlPattern.matcher(jdbcUrl);
        if (mysql.matches()) {
            return mysql.group(2);
        }
        return null;
    }

}
