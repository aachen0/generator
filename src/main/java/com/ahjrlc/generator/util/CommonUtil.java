package com.ahjrlc.generator.util;

/**
 * 通用工具类
 *
 * @author aachen0
 */
public class CommonUtil {
    
    /**
     * 根据jdbc type 转换称java type 类名
     *
     * @param jdbcType jdbc类型
     * @return java类型
     */
    public static String toJavaType(String jdbcType) {
        switch (jdbcType) {
            case "varchar":
                return "String";
            case "int":
                return "Integer";
            case "bigint":
                return "Long";
            case "datetime":
                return "Date";
            default:
                return jdbcType;
        }
    }

    public static String toHtmlInputType(String jdbcType) {
        switch (jdbcType) {
            case "int":
            case "decimal":
                return "number";
            case "datetime":
                return "date";
            default:
                return "text";
        }
    }
}
