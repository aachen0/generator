package com.ahjrlc.generator.controller;

import com.ahjrlc.generator.model.$Template$;
import com.ahjrlc.generator.service.$Template$Service;
import com.ahjrlc.common.ResponseData;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * ${entityDesc}
 *
 * @author generator by frank
 * @date ${dataTime}
 */
@Controller
@RequestMapping("${urlBase}")
@Api(tags = "${entityDesc}接口")
@CrossOrigin
public class $Template$Controller {
    @Autowired
    $Template$Service $template$Service;


    @GetMapping("/index")
    @ApiOperation("进入${entityDesc}列表视图")
    @ApiImplicitParam(value = "查询条件", name = "search")
    public String index$Template$() {
        return "${urlBase}/index";
    }

    @GetMapping("/list/paged")
    @ResponseBody
    @ApiOperation("分页列出符合筛选条件的${entityDesc}列表，返回符合layui数据表格规范的数据格式")
    @ApiImplicitParam(value = "查询条件", name = "search")
    public ResponseData list$Template$Paged(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit
            , $Template$ search) {
        List<$Template$> list = $template$Service.listPaged(page, limit, search);
        if (list instanceof Page) {
            Page p = (Page) list;
            return new ResponseData().setCount(p.getTotal()).setData(list).setLimit(limit).setMsg("${entityDesc}列表");
        }
        return null;
    }


    @GetMapping("/edit")
    @ApiOperation("进入${entityDesc}编辑视图")
    @ApiImplicitParam(value = "唯一键", name = "$key$")
    public String edit$Template$(HttpServletRequest request, Object $key$) {
        $Template$ ori = $template$Service.getByKey($key$);
        request.setAttribute("$template$", ori);
        return "${urlBase}/edit";
    }

    @GetMapping("/view")
    @ApiOperation("根据主键进入${entityDesc}详情视图")
    @ApiImplicitParam(value = "唯一键", name = "$key$")
    public String view$Template$(HttpServletRequest request, @RequestParam("$key$") Object $key$) {
        $Template$ ori = $template$Service.getByKey($key$);
        request.setAttribute("$template$", ori);
        return "${urlBase}/view";
    }


    @PostMapping("/edit/submit")
    @ResponseBody
    @ApiImplicitParam(value = "需要保存的对象信息", name = "$template$")
    @ApiOperation(value = "增加或修改一个${entityDesc}信息，已标准响应格式返回保存结果")
    public ResponseData save$Template$(@Validated $Template$ $template$) {
        int code = $template$Service.saveByKey($template$);
        return new ResponseData().setCode(200).setData(code).setMsg("保存成功");
    }


    @PostMapping("/del")
    @ResponseBody
    @ApiOperation("根据主键或主键列表删除指定记录${entityDesc}")
    @ApiImplicitParam(value = "需要删除记录的主键列表或单个主键", name = "ids")
    public ResponseData del$Template$(@RequestParam("ids") Object[] ids) {
        int count = 0;
        if (ids != null && ids.length > 0) {
            count = $template$Service.delByKeys(Arrays.asList(ids));
        }
        return new ResponseData().setCode(200).setMsg(count + "");
    }

    @GetMapping("/list")
    @ResponseBody
    @ApiOperation("列出所有${entityDesc}信息")
    public List<$Template$> list$Template$() {
        return $template$Service.list();
    }

    @GetMapping("/get")
    @ResponseBody
    @ApiOperation("根据主键获取${entityDesc}详情")
    @ApiImplicitParam(value = "记录的主键", name = "$key$")
    public $Template$ get$Template$Details(@RequestParam("$key$") Object $key$) {
        return $template$Service.getByKey($key$);
    }
}