package com.ahjrlc.generator.mybatis;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.MergeConstants;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.util.StringUtility;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import static org.mybatis.generator.internal.util.StringUtility.isTrue;

public class SimpleCommentGenerator implements CommentGenerator {

    private boolean suppressDate;

    private boolean suppressAllComments;

    /**
     * If suppressAllComments is true, this option is ignored.
     */
    private boolean addRemarkComments;

    private SimpleDateFormat dateFormat;

    public SimpleCommentGenerator() {
        super();
    }

    /**
     * Adds properties for this instance from any properties configured in the
     * CommentGenerator configuration.
     *
     * <p>This method will be called before any of the other methods.
     *
     * @param properties All properties from the configuration
     */
    @Override
    public void addConfigurationProperties(Properties properties) {
        suppressDate = isTrue(properties
                .getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_DATE));

        suppressAllComments = isTrue(properties
                .getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS));

        addRemarkComments = isTrue(properties
                .getProperty(PropertyRegistry.COMMENT_GENERATOR_ADD_REMARK_COMMENTS));

        String dateFormatString = properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_DATE_FORMAT);
        if (StringUtility.stringHasValue(dateFormatString)) {
            dateFormat = new SimpleDateFormat(dateFormatString);
        }
    }

    /**
     * This method should add a Javadoc comment to the specified field. The field is related to the
     * specified table and is used to hold the value of the specified column.
     * 实体类属性注释
     * <p><b>Important:</b> This method should add a the nonstandard JavaDoc tag "@mbg.generated" to
     * the comment. Without this tag, the Eclipse based Java merge feature will fail.
     *
     * @param field              the field
     * @param introspectedTable  the introspected table
     * @param introspectedColumn
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }
        field.addAnnotation("@ApiModelProperty(\"" + introspectedColumn.getRemarks() + "\")");
        if (introspectedColumn.getJdbcType() == 91) {
            field.addAnnotation("@DateTimeFormat(pattern = CommonConst.DATE_PATTERN)");
        } else if (introspectedColumn.getJdbcType() == 93) {
            field.addAnnotation("@DateTimeFormat(pattern = CommonConst.DATE_TIME_PATTERN)");
        }
    }

    /**
     * Adds the field comment.
     * example注释
     *
     * @param field             the field
     * @param introspectedTable
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
    }

    /**
     * Adds a comment for a model class.  The Java code merger should
     * be notified not to delete the entire class in case any manual
     * changes have been made.  So this method will always use the
     * "do not delete" annotation.
     * 实体类注释
     * <p>Because of difficulties with the Java file merger, the default implementation
     * of this method should NOT add comments.  Comments should only be added if
     * specifically requested by the user (for example, by enabling table remark comments).
     *
     * @param topLevelClass     the top level class
     * @param introspectedTable
     */
    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if (suppressAllComments  || !addRemarkComments) {
            return;
        }

        topLevelClass.addJavaDocLine("/**"); //$NON-NLS-1$

        String remarks = introspectedTable.getRemarks();
        if (addRemarkComments && StringUtility.stringHasValue(remarks)) {
            topLevelClass.addJavaDocLine(" * Database Table Remarks:"); //$NON-NLS-1$
            String[] remarkLines = remarks.split(System.getProperty("line.separator"));  //$NON-NLS-1$
            for (String remarkLine : remarkLines) {
                topLevelClass.addJavaDocLine(" *   " + remarkLine);  //$NON-NLS-1$
            }
        }
        String sb = " * 根据数据库表("+introspectedTable.getFullyQualifiedTable()+")逆向生成实体类，任何修改将会在下次生成后被覆盖";
        topLevelClass.addJavaDocLine(sb);
        topLevelClass.addJavaDocLine(" * @author frank"); //$NON-NLS-1$
        topLevelClass.addJavaDocLine(" */"); //$NON-NLS-1$

        topLevelClass.addImportedType("io.swagger.annotations.*");
        if (hasDateField(introspectedTable)) {
            topLevelClass.addImportedType("org.springframework.format.annotation.DateTimeFormat");
            topLevelClass.addImportedType("com.ahjrlc.common.CommonConst");
        }
        topLevelClass.addAnnotation("@ApiModel(\"" + introspectedTable.getRemarks() + "\")");
    }

    /**
     * 判断数据表中是否存在时间类字段，如果存在返回true
     *
     * @param introspectedTable 逆向的数据表
     * @return 是否存在时间字段
     */
    private boolean hasDateField(IntrospectedTable introspectedTable) {
        List<IntrospectedColumn> allColumns = introspectedTable.getAllColumns();
        for (IntrospectedColumn column : allColumns) {
            int jdbcType = column.getJdbcType();
            if (jdbcType == 91 || jdbcType == 93) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds the inner class comment.
     *
     * @param innerClass        the inner class
     * @param introspectedTable
     */
    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }

        StringBuilder sb = new StringBuilder();

        innerClass.addJavaDocLine("/**"); //$NON-NLS-1$
        innerClass
                .addJavaDocLine(" * This class was generated by frank Generator."); //$NON-NLS-1$

        sb.append(" * This class corresponds to the database table "); //$NON-NLS-1$
        sb.append(introspectedTable.getFullyQualifiedTable());
        innerClass.addJavaDocLine(sb.toString());

        addJavadocTag(innerClass, false);

        innerClass.addJavaDocLine(" */"); //$NON-NLS-1$
    }

    /**
     * This method adds the custom javadoc tag for. You may do nothing if you do not wish to include the Javadoc tag -
     * however, if you do not include the Javadoc tag then the Java merge capability of the eclipse plugin will break.
     *
     * @param javaElement       the java element
     * @param markAsDoNotDelete the mark as do not delete
     */
    protected void addJavadocTag(JavaElement javaElement,
                                 boolean markAsDoNotDelete) {
        javaElement.addJavaDocLine(" *"); //$NON-NLS-1$
        StringBuilder sb = new StringBuilder();
        sb.append(" * "); //$NON-NLS-1$
        sb.append(MergeConstants.NEW_ELEMENT_TAG);
        if (markAsDoNotDelete) {
            sb.append(" do_not_delete_during_merge"); //$NON-NLS-1$
        }
        String s = getDateString();
        if (s != null) {
            sb.append(' ');
            sb.append(s);
        }
        javaElement.addJavaDocLine(sb.toString());
    }

    /**
     * Adds the inner class comment.
     *
     * @param innerClass        the inner class
     * @param introspectedTable the introspected table
     * @param markAsDoNotDelete
     */
    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
        if (suppressAllComments) {
            return;
        }

        StringBuilder sb = new StringBuilder();

        innerClass.addJavaDocLine("/**"); //$NON-NLS-1$
        innerClass
                .addJavaDocLine(" * This class was generated by frank Generator."); //$NON-NLS-1$

        sb.append(" * This class corresponds to the database table "); //$NON-NLS-1$
        sb.append(introspectedTable.getFullyQualifiedTable());
        innerClass.addJavaDocLine(sb.toString());

        addJavadocTag(innerClass, markAsDoNotDelete);

        innerClass.addJavaDocLine(" */"); //$NON-NLS-1$
    }

    /**
     * Adds the enum comment.
     *
     * @param innerEnum         the inner enum
     * @param introspectedTable
     */
    @Override
    public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }

        StringBuilder sb = new StringBuilder();

        innerEnum.addJavaDocLine("/**"); //$NON-NLS-1$
        innerEnum
                .addJavaDocLine(" * This enum was generated by MyBatis Generator."); //$NON-NLS-1$

        sb.append(" * This enum corresponds to the database table "); //$NON-NLS-1$
        sb.append(introspectedTable.getFullyQualifiedTable());
        innerEnum.addJavaDocLine(sb.toString());

        addJavadocTag(innerEnum, false);

        innerEnum.addJavaDocLine(" */"); //$NON-NLS-1$

    }

    /**
     * Adds the getter comment.
     *
     * @param method             the method
     * @param introspectedTable  the introspected table
     * @param introspectedColumn
     */
    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {

    }

    /**
     * Adds the setter comment.
     *
     * @param method             the method
     * @param introspectedTable  the introspected table
     * @param introspectedColumn
     */
    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {

    }

    /**
     * Adds the general method comment.
     *
     * @param method            the method
     * @param introspectedTable
     */
    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {

    }

    /**
     * This method is called to add a file level comment to a generated java file. This method
     * could be used to add a general file comment (such as a copyright notice). However, note
     * that the Java file merge function in Eclipse does not deal with this comment. If you run
     * the generator repeatedly, you will only retain the comment from the initial run.
     *
     * <p>The default implementation does nothing.
     *
     * @param compilationUnit the compilation unit
     */
    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {
        compilationUnit.addFileCommentLine("/* 逆向生成文件，下次生成后可能会覆盖当前文件*/");
    }

    /**
     * This method should add a suitable comment as a child element of the specified xmlElement to warn users that the
     * element was generated and is subject to regeneration.
     *
     * @param xmlElement the xml element
     */
    @Override
    public void addComment(XmlElement xmlElement) {
        if (suppressAllComments) {
            return;
        }

        xmlElement.addElement(new TextElement("<!--")); //$NON-NLS-1$

        StringBuilder sb = new StringBuilder();
        sb.append("  WARNING - "); //$NON-NLS-1$
        sb.append(MergeConstants.NEW_ELEMENT_TAG);
        xmlElement.addElement(new TextElement(sb.toString()));
        xmlElement
                .addElement(new TextElement(
                        "  This element is automatically generated by frank Generator, do not modify.")); //$NON-NLS-1$

        String s = getDateString();
        if (s != null) {
            sb.setLength(0);
            sb.append("  This element was generated on "); //$NON-NLS-1$
            sb.append(s);
            sb.append('.');
            xmlElement.addElement(new TextElement(sb.toString()));
        }

        xmlElement.addElement(new TextElement("-->")); //$NON-NLS-1$
    }

    /**
     * Returns a formated date string to include in the Javadoc tag
     * and XML comments. You may return null if you do not want the date in
     * these documentation elements.
     *
     * @return a string representing the current timestamp, or null
     */
    protected String getDateString() {
        if (suppressDate) {
            return null;
        } else if (dateFormat != null) {
            return dateFormat.format(new Date());
        } else {
            return new Date().toString();
        }
    }

    /**
     * This method is called to add a comment as the first child of the root element. This method
     * could be used to add a general file comment (such as a copyright notice). However, note
     * that the XML file merge function does not deal with this comment. If you run the generator
     * repeatedly, you will only retain the comment from the initial run.
     *
     * <p>The default implementation does nothing.
     *
     * @param rootElement the root element
     */
    @Override
    public void addRootComment(XmlElement rootElement) {

    }

    /**
     * Adds a @Generated annotation to a method.
     *
     * @param method            the method
     * @param introspectedTable the introspected table
     * @param imports           the comment generator may add a required imported type to this list
     * @since 1.3.6
     */
    @Override
    public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> imports) {
        imports.add(new FullyQualifiedJavaType("javax.annotation.Generated")); //$NON-NLS-1$
        String comment = "Source Table: " + introspectedTable.getFullyQualifiedTable().toString(); //$NON-NLS-1$
        method.addAnnotation(getGeneratedAnnotation(comment));
    }
    private String getGeneratedAnnotation(String comment) {
        StringBuilder buffer = new StringBuilder();
        buffer.append("@Generated("); //$NON-NLS-1$
        if (suppressAllComments) {
            buffer.append('\"');
        } else {
            buffer.append("value=\""); //$NON-NLS-1$
        }

        buffer.append(MyBatisGenerator.class.getName());
        buffer.append('\"');

        if (!suppressDate && !suppressAllComments) {
            buffer.append(", date=\""); //$NON-NLS-1$
            buffer.append(DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(ZonedDateTime.now()));
            buffer.append('\"');
        }

        if (!suppressAllComments) {
            buffer.append(", comments=\""); //$NON-NLS-1$
            buffer.append(comment);
            buffer.append('\"');
        }

        buffer.append(')');
        return buffer.toString();
    }
    /**
     * Adds a @Generated annotation to a method.
     *
     * @param method             the method
     * @param introspectedTable  the introspected table
     * @param introspectedColumn thr introspected column
     * @param imports            the comment generator may add a required imported type to this list
     * @since 1.3.6
     */
    @Override
    public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> imports) {

    }

    /**
     * Adds a @Generated annotation to a field.
     *
     * @param field             the field
     * @param introspectedTable the introspected table
     * @param imports           the comment generator may add a required imported type to this list
     * @since 1.3.6
     */
    @Override
    public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> imports) {

    }

    /**
     * Adds a @Generated annotation to a field.
     *
     * @param field              the field
     * @param introspectedTable  the introspected table
     * @param introspectedColumn the introspected column
     * @param imports            the comment generator may add a required imported type to this list
     * @since 1.3.6
     */
    @Override
    public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> imports) {

    }

    /**
     * Adds a @Generated annotation to a class.
     *
     * @param innerClass        the class
     * @param introspectedTable the introspected table
     * @param imports           the comment generator may add a required imported type to this list
     * @since 1.3.6
     */
    @Override
    public void addClassAnnotation(InnerClass innerClass, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> imports) {
        innerClass.addAnnotation("haha");
    }
}
