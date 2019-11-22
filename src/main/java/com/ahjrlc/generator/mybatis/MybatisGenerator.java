package com.ahjrlc.generator.mybatis;

import org.mybatis.generator.api.*;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.PluginConfiguration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 逆向工程工具类，进行了适度封装,根据指定的generator配置文件一键生成实体类，接口类，sql映射文件，同时在控制台输出生成过程信息
 *
 * @author admin
 * @since 2018/11/19 21:41
 * IDE:IntelliJ IDEA
 */
public class MybatisGenerator {
    /**
     * 逆向工程的配置文件
     */
    private String generatorConfig;

    public MybatisGenerator(String generatorConfig) {
        this.generatorConfig = generatorConfig;
    }

    /**
     * Mybatis根据配置文件configFile逆向工程生成实体类，mapper.xml和mapper.java接口文件，
     *
     * @param javaOverwrite 是否覆盖旧的.java文件（接口和pojo类文件），true为覆盖，false为生成带后缀名单新文件
     * @param xmlOverwrite  是否覆盖旧的.xml sql语句，true为完全替换，false为合并
     */
    public void generate(boolean javaOverwrite, boolean xmlOverwrite) {
        File configFile = new File(generatorConfig);
        if (!configFile.exists()) {
            throw new RuntimeException("jdbc配置文件：" + configFile.getAbsolutePath() + " 不存在");
        }
        List<String> warnings = new ArrayList<>();
        ConfigurationParser cp = new ConfigurationParser(warnings);
        try {
            Configuration config = cp.parseConfiguration(configFile);
            if (xmlOverwrite) {
                setXmlOverwrite(config);
            }
            DefaultShellCallback callback = new DefaultShellCallback(javaOverwrite);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            ProgressCallback cb = new ProgressCallbackAachenImpl();
            myBatisGenerator.generate(cb);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (String warning : warnings) {
            System.out.println("[WARN] generator:-->" + warning);
        }
    }

    private void setXmlOverwrite(Configuration config) {
        List<Context> contexts = config.getContexts();
        if (contexts != null) {
            for (Context context : contexts) {
                PluginConfiguration plugin = new PluginConfiguration();
                plugin.setConfigurationType("com.ahjrlc.generator.mybatis.OverIsMergeablePlugin");
                context.addPluginConfiguration(plugin);
            }
        }
    }

    private class ProgressCallbackAachenImpl implements ProgressCallback {
        @Override
        public void introspectionStarted(int i) {

        }

        @Override
        public void generationStarted(int i) {

        }

        @Override
        public void saveStarted(int i) {

        }

        @Override
        public void startTask(String s) {
            System.out.println("generator:-->" + s);
        }

        @Override
        public void done() {
            System.out.println("逆向生成完毕");
        }

        @Override
        public void checkCancel() throws InterruptedException {

        }
    }

}
