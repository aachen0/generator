package com.ahjrlc.generator;


import com.ahjrlc.generator.service.TableService;
import com.ahjrlc.generator.service.impl.TableServiceImpl;
import com.ahjrlc.generator.util.LayUiTable;
import com.ahjrlc.generator.util.LayUiTableColumn;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.ahjrlc.common.CommonUtil.camel;
import static com.ahjrlc.common.JdbcUtil.parseDbName;
import static com.ahjrlc.generator.util.CommonUtil.toJavaType;

/**
 * @author aachen0
 */
@Slf4j
public class CodeGenerator {
    public static final String TYPE_ALL = "all";
    public static final String TYPE_CONTROLLER = "controller";
    public static final String TYPE_SERVICE = "service";
    public static final String TYPE_SERVICE_IMPL = "serviceImpl";
    public static final String TYPE_JSP_INDEX = "index";
    public static final String TYPE_JSP_EDIT = "edit";
    public static final String TYPE_JSP_DETAILS = "details";
    public static final String TYPE_JSP_ALL = "all";

    private TableService tableService = new TableServiceImpl();
    private ResourceBundle bundle;
    private String projectDir;
    private String basePackage;
    private String dbName;

    public CodeGenerator(String config) {
        this.bundle = ResourceBundle.getBundle(config);
        this.projectDir = bundle.getString("project.base.dir");
        this.basePackage = bundle.getString("base.package");
        String url = bundle.getString("jdbc.url");
        this.dbName = parseDbName(url);
    }


    public boolean generateTableMvc(String tableName, String urlBase, String searchField, String searchFieldDesc,
                                    String... referencedTable) {
        boolean i = generateControllerAndService(tableName, urlBase, searchField, searchFieldDesc, "all",referencedTable);
        boolean j = generateJsp(tableName, urlBase, searchField, searchFieldDesc, "all",referencedTable);
        return i && j;
    }

    public boolean generateJsp(String tableName, String urlBase, String searchField, String searchFieldDesc,
                               String type,String... referencedTable) {
        String modelDesc = tableService.getTableComment(bundle, dbName, tableName);
        Assert.notNull(modelDesc, "未找到指定的数据表信息：" + dbName + "." + tableName);
//        封装数据列表中的表头js参数
        StringBuilder cols = new StringBuilder();
        LayUiTable table = tableService.getLayUiTable(bundle, dbName, tableName,referencedTable);
        Assert.notNull(table, "未找到指定的数据表信息：" + dbName + "." + tableName);
        String keyType = table.getKeyType();
        Assert.notNull(keyType, "无主键数据表，暂不支持生成代码");
        List<LayUiTableColumn> columns = table.getCols();
//        封装实体编辑页面input标签html
        StringBuilder fieldInputs = new StringBuilder();
        if (columns != null) {
            for (LayUiTableColumn col : columns) {
                //                主键和外键不提供输入
                Boolean isPri = col.getIsPri();
                Boolean isFor = col.getIsForeign();
                if (isPri != null && isPri) {
                    continue;
                }
                fieldInputs.append(col.toInputString()).append("\n");
//                外键不在列表中显示
                if ((isFor != null && isFor)) {
                    continue;
                }
                cols.append(",").append(col.toString()).append("\n                ");
            }
        }
        Map<String, String> params = new HashMap<>();
        params.put("$entity$", camel(tableName, false));
        params.put("$key$", camel(table.getKeyName(), false));
        params.put("${entityDesc}", modelDesc);
        params.put("${urlBase}", urlBase);
        params.put("${searchField}", searchField);
        params.put("${SearchField}", camel(searchField, true));
        params.put("${searchFieldDesc}", searchFieldDesc);
        params.put("${cols}", cols.toString());
        params.put("${fieldInputs}", fieldInputs.toString());
        if (table.getPriCount() > 1) {
            params.put("ids", "uuids");
        }
        InputStream indexTemplete = this.getClass().getResourceAsStream("/template/layui/jsp/index.jsp");
        InputStream editTemplete = this.getClass().getResourceAsStream("/template/layui/jsp/edit.jsp");
        String jspBase = projectDir + "/src/main/webapp/WEB-INF/jsp";
        File indexJsp = new File(jspBase + urlBase + "/index.jsp");
        File editJsp = new File(jspBase + urlBase + "/edit.jsp");
        File detailsJsp = new File(jspBase + urlBase + "/details.jsp");
        try {
            boolean result = false;
            switch (type) {
                case TYPE_JSP_INDEX:
                    result = instanceFile(indexTemplete, params, indexJsp);
                    break;
                case TYPE_JSP_EDIT:
                    result = instanceFile(editTemplete, params, editJsp);
                    break;
                case TYPE_JSP_ALL:
                    result = instanceFile(indexTemplete, params, indexJsp) && instanceFile(editTemplete, params, editJsp);
                default:
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("写入文件失败");
        }
    }

    /**
     * 根据表名，主键名，主键数据类型，字段列表自动生产Controller层和Service层接口及Service实现类代码
     * 默认文件结构：
     * controller层
     * basePackage.controller
     * service层
     * basePackage.service
     * service实现类
     * basePackage.service.impl
     *
     * @param urlBase     该模块web访问api前缀
     * @param tableName   数据表名
     * @param searchField 模糊搜索字段名驼峰形式（首字母大写）
     * @return 所有代码成功生产返回true，否则返回false
     */
    public boolean generateControllerAndService(String tableName, String urlBase, String searchField,
                                                String searchFieldDesc, String type,String... referencedTable) {
        String modelDescription = tableService.getTableComment(bundle, dbName, tableName);
        Assert.notNull(modelDescription, "未找到指定的数据表信息：" + dbName + "." + tableName);
        LayUiTable table = tableService.getLayUiTable(bundle, dbName, tableName,referencedTable);
        Assert.notNull(table, "未找到指定的数据表信息：" + dbName + "." + tableName);
        String keyType = table.getKeyType();
        Assert.notNull(keyType, "无主键数据表，暂不支持生成代码");
        char separator = File.separatorChar;
        String baseDir = projectDir +
                separator + "src" +
                separator + "main" +
                separator + "java" +
                separator + basePackage.replace('.', separator);
        String entityClassName = camel(tableName, true);
        String entityName = camel(tableName, false);
        Map<String, String> params = new HashMap<>();
        params.put("com.ahjrlc.generator", basePackage);
        params.put("$Template$", entityClassName);
        params.put("${entityDesc}", modelDescription);
        params.put("${urlBase}", urlBase);
        params.put("$template$", entityName);
        params.put("Object", toJavaType(keyType));
        String keyName = table.getKeyName();
        params.put("$key$", camel(keyName, false));
        params.put("$Key$", camel(keyName, true));
        params.put("$searchField$", searchField);
        params.put("$SearchField$", camel(searchField, true));
        params.put("${searchFieldDesc}", searchFieldDesc);
        params.put("${dataTime}", new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(System.currentTimeMillis()));
        if (table.getPriCount() == 1) {
            String saveKey = entityName + ".get" + camel(keyName, true) + "()";
            params.put("${saveKey}", saveKey);
        } else {
            params.put("${saveKey}", entityName);
            params.put("ids", "uuids");
        }

        InputStream controllerTemp = this.getClass().getResourceAsStream("/template/layui/Controller.temp");
        InputStream serviceTemp = this.getClass().getResourceAsStream("/template/layui/Service.temp");
        InputStream serviceTempImpl = this.getClass().getResourceAsStream("/template/layui/ServiceImpl.temp");
        File controllerFile = new File(baseDir + separator + "controller" + separator + entityClassName + "Controller.java");
        File serviceFile = new File(baseDir + separator + "service" + separator + entityClassName + "Service.java");
        File serviceFileImpl = new File(baseDir + separator + "service" + separator + "impl" + separator + entityClassName + "ServiceImpl.java");
        try {
            boolean result = false;
            switch (type) {
                case TYPE_SERVICE:
                    result = instanceFile(serviceTemp, params, serviceFile);
                    break;
                case TYPE_SERVICE_IMPL:
                    result = instanceFile(serviceTempImpl, params, serviceFileImpl);
                    break;
                case TYPE_CONTROLLER:
                    result = instanceFile(controllerTemp, params, controllerFile);
                    break;
                case TYPE_ALL:
                    result = instanceFile(serviceTemp, params, serviceFile) && instanceFile(serviceTempImpl, params, serviceFileImpl) && instanceFile(controllerTemp, params, controllerFile);
                default:

            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("写入文件失败");
        }
    }

    /**
     * 用map中的参数替换文件中的模版文件中的占位符，并写入到目标文件
     *
     * @param template
     * @param params
     * @return
     */
    private boolean instanceFile(InputStream template, Map<String, String> params, File destFile) throws IOException {
        if (template != null && params.size() > 0) {
            if (!destFile.exists()) {
                File absoluteFile = destFile.getParentFile();
                if (!absoluteFile.exists()) {
                    absoluteFile.mkdirs();
                }
                destFile.createNewFile();
            }
            try (BufferedReader br = new BufferedReader(new InputStreamReader(template));
                 BufferedWriter bw = new BufferedWriter(new FileWriter(destFile));
            ) {
                String line;
                while ((line = br.readLine()) != null) {
                    for (Map.Entry<String, String> entry : params.entrySet()) {
                        line = line.replace(entry.getKey(), entry.getValue());
                    }
                    bw.write(line + "\n");
                }
                bw.flush();
            }
            if (destFile.exists() && destFile.length() > 0) {
                System.out.println("已生成" + destFile.getPath());
                return true;
            }
        } else {
            throw new RuntimeException("模版文件未找到");
        }
        return false;
    }
}
