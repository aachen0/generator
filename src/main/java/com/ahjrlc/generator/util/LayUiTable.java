package com.ahjrlc.generator.util;

import com.ahjrlc.generator.model.KeyColumnUsage;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 描述某数据表的layui数据表格属性
 *
 * @author aachen0
 */
@Data
public class LayUiTable {
    private String elem;
    private String url;
    private Map<String, Object> where;
    private String toolbar;
    private List<LayUiTableColumn> cols = new ArrayList<>();
    private Boolean page;
    private Boolean even;
    private String size;
    private String keyName;
    private String keyType;
    private int priCount;

    public LayUiTable addCol(LayUiTableColumn layUiTableColumn) {
        cols.add(layUiTableColumn);
        return this;
    }

    public void setForeignKey(KeyColumnUsage foreign) {
        String columnName = foreign.getColumnName();
        LayUiTableColumn column = getColumnByName(columnName);
        if (column != null) {
            column.setIsForeign(true);
            column.setReferencedTableName(foreign.getReferencedTableName());
            column.setReferencedColumnName(foreign.getReferencedColumnName());
        }
    }

    private LayUiTableColumn getColumnByName(String columnName) {
        if (cols != null && cols.size() > 0) {
            for (LayUiTableColumn column : cols) {
                if (columnName != null && columnName.equals(column.getField())) {
                    return column;
                }
            }
        }
        return null;
    }
}
