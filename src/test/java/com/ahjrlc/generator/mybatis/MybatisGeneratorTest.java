package com.ahjrlc.generator.mybatis;

import org.junit.Test;

import static org.junit.Assert.*;

public class MybatisGeneratorTest {
    MybatisGenerator mybatisGenerator = new MybatisGenerator("/Users/aachen0/IdeaProjects/generator/src/test/resources/generator.xml");

    @Test
    public void generate() {
        mybatisGenerator.generate(true,true);
    }
}
