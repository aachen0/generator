package com.ahjrlc.generator.service.impl;

import com.ahjrlc.generator.service.TableService;
import com.ahjrlc.util.LayUiTable;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class TableServiceImplTest {
    TableService tableService = new TableServiceImpl();

    @Test
    public void listColumns() {
        LayUiTable video = tableService.getLayUiTable("video","video");
        System.out.println(video);
    }
}
