package com.ahjrlc.generator.service;

import com.ahjrlc.generator.model.$Template$;

import java.util.List;

/**
 * ${entityDesc}业务层
 *
 * @author generator by frank
 */
public interface $Template$Service {
    /**
     * 根据主键获取一个${entityDesc}信息
     *
     * @param $key$ 主键
     * @return ${entityDesc}信息
     */
    $Template$ getByKey(Object $key$);

    /**
     * 保存一个${entityDesc}信息,根据主键判断添加还是修改
     * 添加成功1/失败-1，编辑成功2/失败0
     *
     * @param $template$ ${entityDesc}对象
     * @return 添加或修改结果：add：1/-1,/edit：2/0
     */
    Integer saveByKey($Template$ $template$);

    /**
     * 根据主键删除指定${entityDesc}
     *
     * @param $key$ 主键
     * @return 删除记录数
     */
    Integer delByKey(Object $key$);

    /**
     * 批量删除${entityDesc}
     *
     * @param $key$s ${entityDesc}id
     * @return 成功删除记录数
     */
    int delByKeys(List<Object> $key$s);

    /**
     * 分页列出符合条件的${entityDesc}
     *
     * @param page              分页第几页
     * @param limit             分页每页大小
     * @param part$searchField$ $searchField$模糊查询
     * @return 符合条件的${entityDesc}列表
     */
    List<$Template$> listPaged(Integer page, Integer limit, String part$searchField$);

    /**
     * 列出所有${entityDesc}
     *
     * @return 所有的${entityDesc}
     */
    List<$Template$> list();

}
