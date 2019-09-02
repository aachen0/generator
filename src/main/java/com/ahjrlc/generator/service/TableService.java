package com.ahjrlc.generator.service;

import com.ahjrlc.util.LayUiTable;

import java.util.List;
import java.util.Map;

/**
 * 读取数据表信息
 *
 * @author aachen0
 */
public interface TableService {
    /**
     * 列出数据库名为dbName的数据库中所有表
     *
     * @param dbName 数据库名
     * @return 表
     */
    List<Map> listTables(String dbName);

    /**
     * 列出数据库表名为tableName的数据库中所有表
     *
     * @param dbName    数据库名
     * @param tableName 表名
     * @return 列
     */
    LayUiTable getLayUiTable(String dbName, String tableName);

    /**
     * 列出数据库表名为tableName的表的comment
     *
     * @param dbName    数据库名
     * @param tableName 表名
     * @return
     */
    String getTableComment(String dbName, String tableName);
}
