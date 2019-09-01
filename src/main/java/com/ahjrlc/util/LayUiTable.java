package com.ahjrlc.util;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 描述某数据表的layui数据表格属性
 * @author aachen0
 */
@Data
public class LayUiTable {
    private String elem;
    private String url;
    private Map<String,Object> where;
    private String toolbar;
    private List<LayUiTableColumn> cols = new ArrayList<>();
    private  Boolean page;
    private Boolean even;
    private String size;

    public LayUiTable addCol(LayUiTableColumn layUiTableColumn) {
        cols.add(layUiTableColumn);
        return this;
    }
}
