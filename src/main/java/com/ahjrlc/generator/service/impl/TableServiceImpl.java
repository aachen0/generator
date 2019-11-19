package com.ahjrlc.generator.service.impl;

import com.ahjrlc.generator.service.TableService;
import com.ahjrlc.generator.util.JdbcUtil;
import com.ahjrlc.generator.util.LayUiTable;
import com.ahjrlc.generator.util.LayUiTableColumn;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import static com.ahjrlc.generator.util.CommonUtil.camel;

/**
 * @author aachen0
 */
public class TableServiceImpl implements TableService {

    /**
     * 列出数据库名为dbName的数据库中所有表
     *
     * @param dbName 数据库名
     * @return 表
     */
    @Override
    public List<Map> listTables(String dbName) {
        return null;
    }


    /**
     * 列出数据库表名为tableName的数据库中所有表
     * @param bundle 数据库链接配置
     * @param dbName 数据库名
     * @param tableName 表名
     * @return 列
     */
    @Override
    public LayUiTable getLayUiTable(ResourceBundle bundle,String dbName, String tableName) {
        Connection connection = JdbcUtil.getConnection(bundle);
        try {
            String sql = "select COLUMN_NAME, DATA_TYPE, COLUMN_KEY, COLUMN_COMMENT\n" +
                    "from information_schema.COLUMNS\n" +
                    "where TABLE_SCHEMA = ?" +
                    "  and TABLE_NAME = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,dbName);
            preparedStatement.setString(2,tableName);
            ResultSet resultSet = preparedStatement.executeQuery();
            LayUiTable table = new LayUiTable();
            int priCount = 0;
            while (resultSet.next()){
                LayUiTableColumn column = new LayUiTableColumn(camel(tableName,false),camel(resultSet.getString("COLUMN_NAME"),false), resultSet.getString("DATA_TYPE"),resultSet.getString("COLUMN_COMMENT"));
                if ("PRI".equals(resultSet.getString("COLUMN_KEY"))){
                    table.setKeyName(column.getField());
                    table.setKeyType(column.getFieldType());
                    column.setIsPri(true);
                    priCount++;
                }
                table.addCol(column);
            }
            table.setPriCount(priCount);
            if (priCount>1){
                table.setKeyType(camel(tableName,true));
                table.setKeyName(tableName);
            }
            return table;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * @param dbName    数据库名
     * @param tableName 表名
     * @return
     */
    @Override
    public String getTableComment(ResourceBundle bundle,String dbName, String tableName) {
        Connection connection = JdbcUtil.getConnection(bundle);
        try {
            String sql = "select TABLE_COMMENT\n" +
                    "from information_schema.TABLES\n" +
                    "where TABLE_SCHEMA = ?" +
                    "  and TABLE_NAME = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,dbName);
            preparedStatement.setString(2,tableName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                return resultSet.getString("TABLE_COMMENT");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
