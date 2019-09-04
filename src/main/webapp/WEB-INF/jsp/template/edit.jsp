<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/init.jsp" %>
<%--
  ~ Copyright (c) 2019.Copyright ownership belongs to jrtech(聚戎科技),
  ~ shall not be reproduced , copied, or used in other ways without permission.
  ~ Otherwise jrtech(聚戎科技) will have the right to pursue legal responsibilities.
  --%>

<%
    request.setAttribute("urlPrefix", "/base-info/module");
    request.setAttribute("object", "电站组件");// 操作页面目标名称
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${object}编辑-${proName}-${version}</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <tags:head-link/>
<style>
    .layui-form-label{
        width: 90px;
    }
</style>
</head>
<body>
<div class="weadmin-body">
    <form class="layui-form" action="${ctx}${urlPrefix}/save/submit" method="post">

        <div class="layui-form-item">
            <label for="name" class="layui-form-label">
                <span class="we-red">*</span>品牌
            </label>
            <div class="layui-input-inline">
                <input type="text" class="layui-input" id="name" name="name" value="${investor_personal.name}">
            </div>
        </div>
        <div class="layui-form-item">
            <label for="investorCode" class="layui-form-label">
                <span class="we-red">*</span>型号
            </label>
            <div class="layui-input-inline">
                <input type="text" class="layui-input" id="investorCode" name="investorCode" value="${investor_personal.investorCode}">
            </div>
        </div>
        <div class="layui-form-item">
            <label for="address" class="layui-form-label">参数
            </label>
            <div class="layui-input-inline">
                <input type="text" class="layui-input" id="address" name="address" value="${investor_personal.investorCode}">
            </div>
        </div>
        <div class="layui-form-item">
            <label for="contact" class="layui-form-label">备注
            </label>
            <div class="layui-input-inline">
                <input type="text" class="layui-input" id="contact" name="contact" value="${investor_personal.address}">
            </div>
        </div>
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
        <div class="layui-form-item">
            <label class="layui-form-label"></label>
            <button class="layui-btn" lay-filter="save" lay-submit="">确定</button>
            <input type="hidden" name="id" id="id" value="${investor_personal.investorCode}"/>
        </div>
        </fieldset>
    </form>
</div>
<script src="${ctx}/static/layui/layui.js" charset="utf-8"></script>

<script type="text/javascript">

    layui.extend({
        admin: '${ctx }/static/js/admin'
    });
    layui.use(['form', 'jquery', 'admin', 'layer'], function () {
        var form = layui.form,
            $ = layui.jquery,
            admin = layui.admin,
            layer = layui.layer;


        <c:if test="${!empty result}">
        layer.msg("${result}", {
            icon: 6,
            time: 500,// 延时半秒
        }, function () {
            var index = parent.layer.getFrameIndex(window.name);
//关闭当前frame
            parent.layer.close(index);
        });
        </c:if>

        //自定义验证规则
        form.verify({
            sorder: [/\d+/, '正确填写排序数字']
        });

    });
    layui.use('upload', function() {
        var $ = layui.jquery
            , upload = layui.upload;
        //同时绑定多个元素，并将属性设定在元素上
        upload.render({
            elem: '.demoMore'
            ,before: function(){
                layer.tips('接口地址：'+ this.url, this.item, {tips: 1});
            }
            ,done: function(res, index, upload){
                var item = this.item;
                console.log(item); //获取当前触发上传的元素，layui 2.1.0 新增
            }
        });
    });
</script>
</body>
</html>
