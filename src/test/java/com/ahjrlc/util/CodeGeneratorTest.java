package com.ahjrlc.util;

import org.junit.Test;

import java.util.ResourceBundle;

import static org.junit.Assert.*;

public class CodeGeneratorTest {
    CodeGenerator generator = new CodeGenerator(ResourceBundle.getBundle("config"));
//    @Test
    public void generateTableMvc() {
        generator.generateTableMvc("config","/admin/config","ConfigKey","字典键");

    }

//    @Test
    public void generateJsp() {
        generator.generateJsp("config","/admin/config","ConfigKey","字典键","all");
    }

//    @Test
    public void generateControllerAndService() {
    }
}