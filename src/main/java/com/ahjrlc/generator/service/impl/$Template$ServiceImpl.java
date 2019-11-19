package com.ahjrlc.generator.service.impl;

import com.ahjrlc.generator.dao.$Template$Mapper;
import com.ahjrlc.generator.model.$Template$;
import com.ahjrlc.generator.model.$Template$Example;
import com.ahjrlc.generator.service.$Template$Service;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class $Template$ServiceImpl implements $Template$Service {
    @Autowired
    $Template$Mapper mapper;

    @Override
    public $Template$ getByKey(Object $key$) {
        return mapper.selectByPrimaryKey($key$);
    }

    @Override
    public Integer saveByKey($Template$ $template$) {
        $Template$ ori = getByKey($template$);
        if (ori == null) {
            return mapper.insert($template$) > 0 ? 1 : -1;
        } else {
            return mapper.updateByPrimaryKeySelective($template$) > 0 ? 2 : 0;
        }
    }

    @Override
    public Integer delByKey(Object $key$) {
        return mapper.deleteByPrimaryKey($key$);
    }

    @Override
    public int delByKeys(List<Object> $key$s) {
        if ($key$s != null && $key$s.size() > 0) {
            $Template$Example example = new $Template$Example();
            $Template$Example.Criteria criteria = example.createCriteria();
            criteria.and$Key$In($key$s);
            return mapper.deleteByExample(example);
        }
        return -1;
    }

    @Override
    public List<$Template$> listPaged(Integer page, Integer limit, String part$searchField$) {
        $Template$Example example = new $Template$Example();
        $Template$Example.Criteria criteria = example.createCriteria();
        if (part$searchField$ != null && part$searchField$.trim().length() > 0) {
            criteria.and$searchField$Like("%" + part$searchField$ + "%");
        }
        PageHelper.startPage(page, limit);
        return mapper.selectByExample(example);
    }

    @Override
    public List<$Template$> list() {
        return mapper.selectByExample(new $Template$Example());
    }
}
