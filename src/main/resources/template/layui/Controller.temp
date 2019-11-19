package com.ahjrlc.generator.controller;

import com.ahjrlc.generator.model.$Template$;
import com.ahjrlc.generator.service.$Template$Service;
import com.ahjrlc.util.ResponseData;
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
    @ApiImplicitParam(value = "${searchFieldDesc}关键字", name = "part$searchField$")
    public String $template$Index(HttpServletRequest request, String part$searchField$) {
        request.setAttribute("part$searchField$", part$searchField$);
        return "${urlBase}/index";
    }

    @GetMapping("/edit")
    @ApiOperation("进入${entityDesc}编辑视图")
    public String $template$Edit(HttpServletRequest request, Object $key$) {
        $Template$ ori = $template$Service.getByKey($key$);
        request.setAttribute("$template$", ori);
        return "${urlBase}/edit";
    }


    @PostMapping("/edit/submit")
    @ResponseBody
    @ApiOperation(value = "增加或修改一个${entityDesc}信息", notes = "依据主键判断添加还是更新")
    public ResponseData $template$Save(@Validated $Template$ $template$) {
        int code = $template$Service.saveByKey($template$);
        return new ResponseData().setCode(200).setData(code);
    }


    @GetMapping("/list/paged")
    @ResponseBody
    @ApiOperation("分页列出符合筛选条件的${entityDesc}列表，返回符合layui数据表格规范的数据格式")
    @ApiImplicitParam(value = "${searchFieldDesc}关键字", name = "part$searchField$")
    public ResponseData $template$ListPaged(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit, String part$searchField$) {
        List<$Template$> list = $template$Service.listPaged(page, limit, part$searchField$);
        if (list instanceof Page) {
            Page p = (Page) list;
            return new ResponseData().setCount(p.getTotal()).setData(list).setLimit(limit).setMsg("${entityDesc}列表");
        }
        return null;
    }

    @GetMapping("/list")
    @ResponseBody
    @ApiOperation("列出所有${entityDesc}信息")
    public List<$Template$> $template$List() {
        return $template$Service.list();
    }

    @PostMapping("/del")
    @ResponseBody
    @ApiOperation("根据主键删除一个${entityDesc}")
    public ResponseData $template$Del(@RequestParam("ids") Object[] ids) {
        int count = 0;
        if (ids != null && ids.length > 0) {
            count = $template$Service.delByKeys(Arrays.asList(ids));
        }
        return new ResponseData().setCode(200).setMsg(count + "");
    }

    @GetMapping("/details")
    @ResponseBody
    @ApiOperation("根据主键获取${entityDesc}详情")
    public $Template$ $template$Details(@RequestParam("$key$") Object $key$) {
        return $template$Service.getByKey($key$);
    }
}