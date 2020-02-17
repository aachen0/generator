package com.ahjrlc.generator.service.impl;

import com.ahjrlc.generator.dao.$Template$Mapper;
import com.ahjrlc.generator.model.$Template$;
import com.ahjrlc.generator.model.$Template$Example;
import com.ahjrlc.generator.service.$Template$Service;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ahjrlc.common.util.CommonUtil.isEmpty;


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
//        如果是单主键，下面的get参数需要取出主键的值,所以要用${saveKey}替换$template$
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
        int i = 0;
        if ($key$s != null && $key$s.size() > 0) {
            for (Object $key$ : $key$s) {
                i += mapper.deleteByPrimaryKey($key$);
            }
        }
        return i;
    }

    @Override
    public List<$Template$> listPaged(Integer page, Integer limit, $Template$ search) {
        $Template$Example example = new $Template$Example();
        $Template$Example.Criteria criteria = example.createCriteria();
//       todo 查询逻辑根据实际从$key$中获取
        if(!isEmpty(search.get$SearchField$())){
            criteria.and$SearchField$Like("%"+search.get$SearchField$()+"%");
        }
        PageHelper.startPage(page, limit);
        return mapper.selectByExample(example);
    }

    @Override
    public List<$Template$> list() {
        return mapper.selectByExample(new $Template$Example());
    }
}
