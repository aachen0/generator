package com.ahjrlc.util;


import com.ahjrlc.generator.service.TableService;
import com.ahjrlc.generator.service.impl.TableServiceImpl;

import java.io.*;
import java.net.URL;
import java.util.*;

import static com.ahjrlc.util.CommonUtil.camel;
import static com.ahjrlc.util.CommonUtil.toJavaType;

/**
 * @author aachen0
 */
public class CodeGenerator {
    private TableService tableService = new TableServiceImpl();
    private String projectDir;
    private String basePackage;
    private String dbName;

    public CodeGenerator(ResourceBundle config) {
        this.projectDir = config.getString("project.base.dir");
        this.basePackage = config.getString("base.package");
        this.dbName = config.getString("db.name");
    }

    public boolean generateTableMvc(String tableName, String urlBase, String searchField, String searchFieldDesc) {
        boolean i = generateControllerAndService(tableName, urlBase, searchField, searchFieldDesc, "all");
        boolean j = generateJsp(tableName, urlBase, searchField, searchFieldDesc, "all");
        return i && j;
    }

    public boolean generateJsp(String tableName, String urlBase, String searchField, String searchFieldDesc, String type) {
        String modelDesc = tableService.getTableComment(dbName, tableName);
//        封装数据列表中的表头js参数
        StringBuilder cols = new StringBuilder();
        LayUiTable layUiTable = tableService.getLayUiTable(dbName, tableName);
        List<LayUiTableColumn> columns = layUiTable.getCols();
//        封装实体编辑页面input标签html
        StringBuilder fieldInputs = new StringBuilder();
        if (columns != null) {
            for (LayUiTableColumn col : columns) {
                cols.append(",").append(col.toString()).append("\n                ");
                fieldInputs.append(col.toInputString()).append("\n");
            }
        }
        Map<String, String> params = new HashMap<>();
        params.put("$entity$", camel(tableName, false));
        params.put("$key$", layUiTable.getKeyName());
        params.put("${entityDesc}", modelDesc);
        params.put("${urlBase}", urlBase);
        params.put("${searchField}", searchField);
        params.put("${searchFieldDesc}", searchFieldDesc);
        params.put("${cols}", cols.toString());
        params.put("${fieldInputs}", fieldInputs.toString());
        InputStream indexTemplete = this.getClass().getResourceAsStream("/template/layui/jsp/index.jsp");
        InputStream editTemplete = this.getClass().getResourceAsStream("/template/layui/jsp/edit.jsp");
        String jspBase = projectDir + "/src/main/webapp/WEB-INF/jsp";
        File indexJsp = new File(jspBase + urlBase + "/index.jsp");
        File editJsp = new File(jspBase + urlBase + "/edit.jsp");
        File detailsJsp = new File(jspBase + urlBase + "/details.jsp");
        try {
            boolean result = false;
            switch (type) {
                case "index":
                    result = instanceFile(indexTemplete, params, indexJsp);
                    break;
                case "edit":
                    result = instanceFile(editTemplete, params, editJsp);
                    break;
                case "all":
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
    public boolean generateControllerAndService(String tableName, String urlBase, String searchField, String searchFieldDesc, String type) {
        String modelDescription = tableService.getTableComment(dbName, tableName);
        LayUiTable table = tableService.getLayUiTable(dbName, tableName);
        char separator = File.separatorChar;
        String baseDir = projectDir +
                separator + "src" +
                separator + "main" +
                separator + "java" +
                separator + basePackage.replace('.', separator);
        String entityClassName = camel(tableName, true);
        String entityName = entityClassName.substring(0, 1).toLowerCase() + entityClassName.substring(1);
        Map<String, String> params = new HashMap<>();
        params.put("com.ahjrlc.generator", basePackage);
        params.put("$Template$", entityClassName);
        params.put("${entityDesc}", modelDescription);
        params.put("${urlBase}", urlBase);
        params.put("$template$", entityName);
        params.put("Object", toJavaType(table.getKeyType()));
        params.put("$key$", camel(table.getKeyName(), false));
        params.put("$Key$", camel(table.getKeyName(), true));
        params.put("$searchField$", searchField);
        params.put("${searchFieldDesc}", searchFieldDesc);

        InputStream controllerTemp = this.getClass().getResourceAsStream("/template/layui/Controller.temp");
        InputStream serviceTemp = this.getClass().getResourceAsStream("/template/layui/Service.temp");
        InputStream serviceTempImpl = this.getClass().getResourceAsStream("/template/layui/ServiceImpl.temp");
        InputStream indexTemplete = this.getClass().getResourceAsStream("/template/layui/jsp/index.jsp");
        File controllerFile = new File(baseDir + separator + "controller" + separator + entityClassName + "Controller.java");
        File serviceFile = new File(baseDir + separator + "service" + separator + entityClassName + "Service.java");
        File serviceFileImpl = new File(baseDir + separator + "service" + separator + "impl" + separator + entityClassName + "ServiceImpl.java");
        try {
            boolean result = false;
            switch (type) {
                case "service":
                    result = instanceFile(serviceTemp, params, serviceFile);
                    break;
                case "serviceImpl":
                    result = instanceFile(serviceTempImpl, params, serviceFileImpl);
                    break;
                case "controller":
                    result = instanceFile(controllerTemp, params, controllerFile);
                    break;
                case "all":
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
