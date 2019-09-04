package com.ahjrlc.util;

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
            col.append("align: '").append(align).append("'");
        }
        return new String(col.append("}"));
    }

    public String toInputString() {
        StringBuilder fieldInput = new StringBuilder("<div class='layui-form-item'>\n")
                .append("                        <label for='").append(field).append("' class='layui-form-label'>\n")
                .append("                            <span class='we-red'>*</span>").append(title).append("</label>\n")
                .append("                        <div class='layui-input-inline'>\n")
                .append("                            <input type='").append(CommonUtil.toHtmlInputType(fieldType))
                .append("' class='layui-input' id='")
                .append(field).append("' name='").append(field).append("' value='${").append(entity).append(".").append(field).append("}'>\n")
                .append("                        </div>\n")
                .append("                    </div>");
        return fieldInput.toString();
    }
}
