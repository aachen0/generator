package com.ahjrlc.generator.dao;

import com.ahjrlc.generator.model.$Template$;
import com.ahjrlc.generator.model.$Template$Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface $Template$Mapper {
    long countByExample($Template$Example example);

    int deleteByExample($Template$Example example);

    int deleteByPrimaryKey(Object $Key$);

    int insert($Template$ record);

    int insertSelective($Template$ record);

    List<$Template$> selectByExample($Template$Example example);

    $Template$ selectByPrimaryKey(Object $Key$);

    int updateByExampleSelective(@Param("record") $Template$ record, @Param("example") $Template$Example example);

    int updateByExample(@Param("record") $Template$ record, @Param("example") $Template$Example example);

    int updateByPrimaryKeySelective($Template$ record);

    int updateByPrimaryKey($Template$ record);
}