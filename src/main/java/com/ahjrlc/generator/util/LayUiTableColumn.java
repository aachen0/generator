package com.ahjrlc.generator.util;

import lombok.Data;

/**
 * layui数据表格列属性
 *
 * @author aachen0
 */
@Data
public class LayUiTableColumn {
    public LayUiTableColumn(String entity, String field, String fieldType, String title) {
        this.entity = entity;
        this.field = field;
        this.fieldType = fieldType;
        this.title = title;
    }

    /**
     * 实体对象名称，取表名小驼峰形式
     */
    private String entity;

    /**
     * 实体对象属性名，字段名小驼峰形式
     */
    private String field;

    /**
     * 本列字段jdbc类型
     */
    private String fieldType;

    /**
     * 是否主键
     */
    private Boolean isPri;
    /**
     * 是否是外键
     */
    private Boolean isForeign;

    /**
     * 外键指向的表名
     */
    private String referencedTableName;

    /**
     * 外键对应的参考表字段名
     */
    private String referencedColumnName;

    /**
     * 实体对象属性说明，取数据表字段说明
     */
    private String title;
    private Integer width;
    private Boolean sort;
    private String align;

    @Override
    public String toString() {
        StringBuffer col = new StringBuffer("{field: '").append(field)
                .append("', title: '").append(title).append("'");
        if (width != null) {
            col.append(", width: ").append(width);
        }
        if (sort != null && sort) {
            col.append(", sort: true");
        }
        if (align != null) {
            col.append(", align: '").append(align).append("'");
        }
//        逻辑值
        if ("bit".equals(fieldType)) {
            col.append(", templet: function (d) {\n" +
                    "                        return d." + field + " ? '是' : '否';\n" +
                    "                    }");
        }
        return new String(col.append("}"));
    }

    public String toInputString() {
//        主键不提供输入框
        if (isPri != null && isPri) {
            return "";
        }
        StringBuilder fieldInput = new StringBuilder("                    <div class='layui-form-item'>\n")
                .append("                        <label for='").append(field).append("' class='layui-form-label'>\n")
                .append("                            <span class='we-red'>*</span>").append(title).append("</label>\n")
                .append("                        <div class='layui-input-inline'>\n")
                .append(inputHtml())
                .append("                        </div>\n")
                .append("                    </div>");
        return fieldInput.toString();
    }

    private String inputHtml() {
//        外键用select
        if (isForeign != null && isForeign) {
            return "                            <select name='" + field + "' id='" + field + "'>\n" +
                    "                                <option value=''>" + title + "</option>\n" +
                    "                                <c:forEach var='item' items='${" + referencedTableName + "s}'>\n" +
                    "                                    <option value='${item." + referencedColumnName + "}' ${item" +
                    "." + referencedColumnName + "==" + entity + "." + field + "?'selected':''}>${item}</option>\n" +
                    "                                </c:forEach>\n" +
                    "                            </select>\n";
        } else {
            String type;
            String value = "value='${" + entity + "." + field + "}'";
            switch (fieldType) {
                case "int":
                case "decimal":
                    type = "number";
                    break;
                case "datetime":
                    type = "date";
                    break;
                case "bit":
                    type = "checkbox";
                    value = "lay-skin='switch' lay-text='是|否' ${" + entity + "." + field + "?'checked':''}";
                    break;
                default:
                    type = "text";
            }

            return "                            <input type='" + type +
                    "' class='layui-input' id='" +
                    field + "' name='" + field + "' " + value + ">\n";
        }
    }
}
