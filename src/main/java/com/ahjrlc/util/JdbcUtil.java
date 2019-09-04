package com.ahjrlc.util;

import com.gitee.aachen0.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
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
     * 将结果集转换成对应的实体类对象
     *
     * @param rs    结果集
     * @param clazz 实体类的Class对象
     * @return 实体类对象
     */
    public static Object rsToObject(ResultSet rs, Class clazz) {
        Object object = null;
        try {
            object = clazz.newInstance();
            ResultSetMetaData data = rs.getMetaData();
            int n = data.getColumnCount();
            for (int i = 1; i <= n; i++) {
                String columnName = data.getColumnName(i);
                String columnType = data.getColumnTypeName(i);
                Object o = rs.getObject(columnName);
                // 处理_风格的字段名为java中的驼峰风格
                String[] ss = columnName.split("_");
                if (ss.length > 1) {
                    StringBuffer colName = new StringBuffer(ss[0]);
                    for (int j = 1; j < ss.length; j++) {
                        colName.append((ss[j].charAt(0) + "").toUpperCase()).append(ss[j].substring(1));
                    }
                    columnName = new String(colName);
                }
                // 拼出set的方法名
                String setter = StringUtils.fieldToSetter(columnName);
                Class paraType = jdbcToJavaType(columnType);
                Method method = clazz.getMethod(setter, paraType);
                Class packType = toPackType(paraType);
                method.invoke(object, packType.cast(o));
            }
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | SQLException e) {
            e.printStackTrace();
        }
        return object;
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

    /**
     * 根据Properties文件生成建表语句&lt;br/&gt;
     * Properties示例&lt;br/&gt;
     * tableName=user&lt;br/&gt;
     * #格式为:字段名=类型，最小值，最大值&lt;br/&gt;
     * user_id=int,,&lt;br/&gt;
     * user_age=int,,
     */
    private static void createGenerator(Properties table) {
        Set<String> fields = table.stringPropertyNames();
        String tableName = table.getProperty("tableName");
        // 移除表名，剩下的为字段名
        fields.remove("tableName");
        // 字段个数
        int length = fields.size();
        StringBuffer sql =
                new StringBuffer("drop table if exists ").append(tableName).append(";\r\ncreate table ").append(tableName).append(
                        "(");
        String[] ss = new String[length];
        fields.toArray(ss);
        for (int i = 0; i < length; i++) {
            String[] args = table.getProperty(ss[i]).split("/");
            String type = args[0].toLowerCase();
            if ("address".equals(type) || "name".equals(type) || "phone".equals(type)) {
                type = "varchar(20)";
            }
            sql.append("\r\n").append(ss[i]).append(" ").append(type).append((i == length - 1) ? ")" : ",");
        }
        sql.append(";");
        // 打印出来
        System.out.println(sql);
    }

}
