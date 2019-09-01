package com.ahjrlc.util;

import lombok.Data;

/**
 * layui数据表格列属性
 *
 * @author aachen0
 */
@Data
public class LayUiTableColumn {

    public LayUiTableColumn(String field, String title) {
        this.field = field;
        this.title = title;
    }

    private String field;
    private Integer width;
    private String title;
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
}
