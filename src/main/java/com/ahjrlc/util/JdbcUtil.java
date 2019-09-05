package com.ahjrlc.util;

import com.gitee.aachen0.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * JDBC常用工具
 *
 * @author aachen0
 */
public class JdbcUtil {
    /**
     * 根据属性文件的配置建立一个jdbc连接
     *
     * @param bundle jdbc配置文件,示例如下：
     *               jdbc.url=jdbc:mysql://127.0.0.1/video?useSSL=false&characterEncoding=utf8
     *               jdbc.driverClass=com.mysql.jdbc.Driver
     *               jdbc.userId=root
     *               jdbc.password=root
     * @return jdbc连接Connection
     */

    public static Connection getConnection(ResourceBundle bundle) {
        Connection connection = null;
        try {
            Class.forName(bundle.getString("jdbc.driverClass"));
            connection = DriverManager.getConnection(bundle.getString("jdbc.url"), bundle.getString("jdbc.userId"),
                    bundle.getString("jdbc.password"));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 将基本类型的类转换为对应的包装类型，和mybatis的generator生成的实体类成员属性保持一致
     *
     * @param paraType 基本数据类型
     * @return 包装数据类型
     */
    private static Class toPackType(Class paraType) {
        switch (paraType.getName()) {
            case "int":
                return Integer.class;
            case "long":
                return Long.class;
            case "byte":
                return Byte.class;
            case "short":
                return Short.class;
            case "float":
                return Float.class;
            case "double":
                return Double.class;
            default:
                return paraType;
        }
    }

    /**
     * 将jdbc的数据类型映射到java基本数据类型
     *
     * @param columnType 结果集中取出的字段数据类型
     * @return 对应的java数据类
     */
    private static Class jdbcToJavaType(String columnType) {

        switch (columnType) {
            case "TINYINT":
            case "TINYINT UNSIGNED":
            case "SMALLINT":
            case "SMALLINT UNSIGNED":
            case "MEDIUMINT":
            case "MEDIUMINT UNSIGNED":
            case "INT":
                return Integer.class;
            case "BIGINT":
            case "INT UNSIGNED":
                return Long.class;
            case "BIGINT UNSIGNED":
                return BigInteger.class;
            case "FLOAT":
            case "FLOAT UNSIGNED":
                return Float.class;
            case "DOUBLE":
            case "DOUBLE UNSIGNED":
                return Double.class;
            case "CHAR":
            case "VARCHAR":
                return String.class;
            case "DECIMAL":
            case "DECIMAL UNSIGNED":
                return BigDecimal.class;

            case "TIME":
                return java.sql.Time.class;
            case "YEAR":
            case "DATE":
                return java.sql.Date.class;
            case "DATETIME":
            case "TIMESTAMP":
                return java.sql.Timestamp.class;
            default:
                return String.class;
        }
    }

}
