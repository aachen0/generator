package com.ahjrlc.generator.service.impl;

import com.ahjrlc.generator.dao.$Template$Mapper;
import com.ahjrlc.generator.model.$Template$;
import com.ahjrlc.generator.model.$Template$Example;
import com.ahjrlc.generator.service.$Template$Service;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import static com.gitee.aachen0.util.CommonUtils.isEmpty;


@Service
public class $Template$ServiceImpl implements $Template$Service {
    @Autowired
    $Template$Mapper mapper;

    @Override
    public $Template$ getBy$Key$(Object $key$) {
        return mapper.selectByPrimaryKey($key$);
    }

    @Override
    public String saveBy$Key$($Template$ $template$) {
        $Template$ ori = getBy$Key$($template$.get$Key$());
        if (ori == null) {
            return "add:" + mapper.insert($template$);
        } else {
            return "edit:" + mapper.updateByPrimaryKeySelective($template$);
        }
    }

    @Override
    public Integer delBy$Key$(Object $key$) {
        return mapper.deleteByPrimaryKey($key$);
    }

    @Override
    public int delBy$Key$s(List<Object> $key$s) {
        if (!isEmpty($key$s)) {
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
        if (!isEmpty(part$searchField$)) {
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
