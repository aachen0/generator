package com.ahjrlc.generator.model;

import lombok.Data;

/**
 * 外键关系描述
 *
 * @author aachen0
 * @date 2019/12/18 14:49
 */
@Data
public class KeyColumnUsage {
    private String constraintCatalog;
    private String constraintSchema;
    private String constraintName;
    private String tableCatalog;
    private String tableSchema;
    private String columnName;
    private String ordinalPosition;
    private String positionInUniqueConstraint;
    private String referencedTableSchema;
    private String referencedTableName;
    private String referencedColumnName;
}

