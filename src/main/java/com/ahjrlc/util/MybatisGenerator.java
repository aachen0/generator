package com.ahjrlc.util;


import com.gitee.aachen0.util.mybatis.AachenGenerator;

import java.io.File;

/**
 * 持久层crud代码生产
 * @author aachen0
 */
public class MybatisGenerator {
    public static void main(String[] args) {
        File config = new File("src/main/resources/generator.xml");
        AachenGenerator generator = new AachenGenerator();
        generator.generate(config, true, true);
    }
}
