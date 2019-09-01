package com.ahjrlc.generator.service.impl;

import com.ahjrlc.generator.service.TableService;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class TableServiceImplTest {
    TableService tableService = new TableServiceImpl();

    @Test
    public void listColumns() {
        List<Map> video = tableService.listColumns("video");
        System.out.println(video);
    }
}
