package com.ahjrlc.util;


import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * @author aachen0
 */
public class CodeGenerator {
    private String projectDir;
    private String basePackage;

    public CodeGenerator() {
        ResourceBundle config = ResourceBundle.getBundle("config");
        this.projectDir = config.getString("project.base.dir");
        this.basePackage = config.getString("base.package");
    }

    public CodeGenerator(String projectDir, String basePackage) {
        this.projectDir = projectDir;
        this.basePackage = basePackage;
    }

    public static void main(String[] args) {
        CodeGenerator generator = new CodeGenerator();
        String modelDesc = "视频资源";
        String urlBase = "/admin/video";
        String searchField = "Title";
        String searchFieldDesc = "视频名称";

        generator.generateControllerAndService(modelDesc, urlBase, "video", "id", "Integer", searchField, searchFieldDesc);
        generator.generateJsp(modelDesc, urlBase, searchField, searchFieldDesc, new LayUiTable().addCol(new LayUiTableColumn("id", "ID"))
                .addCol(new LayUiTableColumn("title", "视频名称"))
                .addCol(new LayUiTableColumn("typeId", "视频分类"))
                .addCol(new LayUiTableColumn("picUrl", "首页链接"))
                .addCol(new LayUiTableColumn("video", "视频链接"))
                .addCol(new LayUiTableColumn("playCount", "播放次数"))
                .addCol(new LayUiTableColumn("downloadCount", "下载次数"))
                .addCol(new LayUiTableColumn("uploadTime", "上传时间"))
                .addCol(new LayUiTableColumn("uploader", "上传者"))
                .addCol(new LayUiTableColumn("memo", "备注")));
    }

    private boolean generateJsp(String modelDesc, String urlBase, String searchField, String searchFieldDesc, LayUiTable layUiTable) {
        StringBuffer cols = new StringBuffer();
        List<LayUiTableColumn> cols1 = layUiTable.getCols();
        if (cols1 != null) {
            for (LayUiTableColumn col : cols1) {
                cols.append(",").append(col.toString()).append("\n                ");
            }
        }
        Map<String, String> params = new HashMap<>();
        params.put("${entityDesc}", modelDesc);
        params.put("${urlBase}", urlBase);
        params.put("${searchField}", searchField);
        params.put("${searchFieldDesc}", searchFieldDesc);
        params.put("${cols}", cols.toString());
        InputStream indexTemplete = this.getClass().getResourceAsStream("/template/layui/jsp/index.jsp");
        String jspBase = projectDir + "/src/main/webapp/WEB-INF/jsp";
        File indexJsp = new File(jspBase + urlBase + "/index.jsp");
        File editJsp = new File(jspBase + urlBase + "/edit.jsp");
        File detailsJsp = new File(jspBase + urlBase + "/details.jsp");
        try {
            boolean index = instanceFile(indexTemplete, params, indexJsp);
//            return c && s && si;
            return false;
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
     * @param modelDescription 数据表对应等实体名称
     * @param urlBase          该模块web访问api前缀
     * @param tableName        数据表名
     * @param keyName          主键字段名
     * @param keyType          主键java数据类型
     * @param searchField      模糊搜索字段名驼峰形式（首字母大写）
     * @param fieldNames       表指定字段列表
     * @return 所有代码成功生产返回true，否则返回false
     */
    public boolean generateControllerAndService(String modelDescription, String urlBase, String tableName, String keyName, String keyType, String searchField, String searchFieldDesc, String... fieldNames) {
        char separator = File.separatorChar;
        String baseDir = projectDir +
                separator + "src" +
                separator + "main" +
                separator + "java" +
                separator + basePackage.replace('.', separator);
        String entityClassName = camel(tableName);
        String entityName = entityClassName.substring(0, 1).toLowerCase() + entityClassName.substring(1);
        Map<String, String> params = new HashMap<>();
        params.put("com.ahjrlc.generator", basePackage);
        params.put("$Template$", entityClassName);
        params.put("${entityDesc}", modelDescription);
        params.put("${urlBase}", urlBase);
        params.put("$template$", entityName);
        params.put("Object", keyType);
        params.put("$key$", keyName);
        params.put("$Key$", camel(keyName));
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
            boolean s = instanceFile(serviceTemp, params, serviceFile);
            boolean si = instanceFile(serviceTempImpl, params, serviceFileImpl);
            boolean c = instanceFile(controllerTemp, params, controllerFile);
//            return c && s && si;
            return false;
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
                String line = null;
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

    /**
     * 将下划线分割的表名转换为java的驼峰命名的实体类名
     *
     * @param tableName 表名
     * @return 驼峰命名的实体类名
     */
    private String camel(String tableName) {
        if (tableName != null && !"".equals(tableName.trim())) {
            int size = tableName.length();
            char[] chars = tableName.toCharArray();
            StringBuffer entityName = new StringBuffer((chars[0] + "").toUpperCase());
            for (int i = 1; i < size; i++) {
                if (chars[i] != '_') {
                    entityName.append(chars[i]);
                } else {
                    i++;
                    if (i < size) {
                        entityName.append((chars[i] + "").toUpperCase());
                    }
                }
            }
            return new String(entityName);
        }
        return null;
    }
}
