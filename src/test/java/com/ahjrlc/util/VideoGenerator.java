package com.ahjrlc.util;

import com.gitee.aachen0.util.mybatis.AachenGenerator;
import org.junit.Test;

import java.io.File;

public class VideoGenerator {
    @Test
    public void mybatisGenerator() {
        File config = new File("src/main/resources/generator.xml");
        AachenGenerator generator = new AachenGenerator();
        generator.generate(config, true, true);
    }

    @Test
    public void codeGenerator() {
//        CodeGenerator generator = new CodeGenerator();
//        generator.generateTableMvc("comment", "/admin/comment", "Content", "评论内容");
//        generator.generateTableMvc("config", "/admin/config", "ConfigKey", "字典键");
//        generator.generateTableMvc("notice", "/admin/notice", "Title", "通知标题");
//        generator.generateTableMvc("type", "/admin/type", "Name", "分类名");
//        generator.generateTableMvc("user", "/admin/user", "Username", "用户名");
//        generator.generateTableMvc("video", "/admin/video", "Title", "视频标题");
//        generator.generateTableMvc("menu", "/admin/menu", "Name", "菜单名称");

    }
}
