package com.ahjrlc.generator;

import com.ahjrlc.generator.mybatis.MybatisGenerator;
import org.junit.Test;

import static org.junit.Assert.*;

public class CodeGeneratorTest {
    CodeGenerator codeGenerator = new CodeGenerator("config");
    MybatisGenerator mybatisGenerator = new MybatisGenerator("C:\\Users\\禾木\\IdeaProjects\\gen\\src\\test\\resources\\generator.xml");

    @Test
    public void generateTableMvc() {
    }

    @Test
    public void generateJsp() {
    }

    @Test
    public void generateControllerAndService() {
    }
}
