package com.ahjrlc.generator.util;

/**
 * 通用工具类
 *
 * @author aachen0
 */
public class CommonUtil {
    /**
     * 将下划线分割的命名转换为java的驼峰命名
     *
     * @param _name_ 下划线名称
     * @param isCap  首字母是否大写
     * @return 驼峰命名的实体类名
     */
    public static String camel(String _name_, boolean isCap) {
        if (_name_ != null && !"".equals(_name_.trim())) {
            int size = _name_.length();
            char[] chars = _name_.toCharArray();
            String firstChar = chars[0] + "";
            if (isCap) {
                firstChar = firstChar.toUpperCase();
            }
            StringBuffer entityName = new StringBuffer(firstChar);
            for (int i = 1; i < size; i++) {
                if (chars[i] != '_') {
                    entityName.append(chars[i]);
                } else {
                    i++;
                    if (i < size) {
                        entityName.append((chars[i] + "").toUpperCase());
                    }
                }
            }
            return new String(entityName);
        }
        return null;
    }

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
