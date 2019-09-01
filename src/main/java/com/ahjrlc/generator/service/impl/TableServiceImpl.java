package com.ahjrlc.generator.service.impl;

import com.ahjrlc.generator.dao.TableMapper;
import com.ahjrlc.generator.service.TableService;

import java.util.List;
import java.util.Map;

/**
 * @author aachen0
 */
public class TableServiceImpl implements TableService {
    TableMapper mapper;
    /**
     * 列出数据库名为dbName的数据库中所有表
     *
     * @param dbName 数据库名
     * @return 表
     */
    @Override
    public List<Map> listTables(String dbName) {
        return mapper.listTables(dbName);
    }

    /**
     * 列出数据库表名为tableName的数据库中所有表
     *
     * @param tableName 表名
     * @return 列
     */
    @Override
    public List<Map> listColumns(String tableName) {
        return mapper.listTableColumn(tableName);
    }
}
