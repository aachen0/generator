package com.ahjrlc.generator.service.impl;

import com.ahjrlc.generator.service.TableService;
import com.ahjrlc.util.JdbcUtil;
import com.ahjrlc.util.LayUiTable;
import com.ahjrlc.util.LayUiTableColumn;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import static com.ahjrlc.util.CommonUtil.camel;

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
     *
     * @param dbName 数据库名
     * @param tableName 表名
     * @return 列
     */
    @Override
    public LayUiTable getLayUiTable(String dbName, String tableName) {
        ResourceBundle bundle = ResourceBundle.getBundle("config");
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
            while (resultSet.next()){
                LayUiTableColumn column = new LayUiTableColumn(camel(tableName,false),camel(resultSet.getString("COLUMN_NAME"),false), resultSet.getString("DATA_TYPE"),resultSet.getString("COLUMN_COMMENT"));
                if ("PRI".equals(resultSet.getString("COLUMN_KEY"))){
                    table.setKeyName(resultSet.getString("COLUMN_NAME"));
                    table.setKeyType(resultSet.getString("DATA_TYPE"));
                }
                table.addCol(column);
            }
            return table;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public String getTableComment(String dbName, String tableName) {
        ResourceBundle bundle = ResourceBundle.getBundle("config");
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
