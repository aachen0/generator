<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/init.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>系统字典列表-${proName}-${version}</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <tags:head-link/>
</head>
<body>
<div class="x-nav">
	<span class="layui-breadcrumb">
        <a href="">首页</a>
        <a href="">系统字典管理</a>
     </span>
    <a class="layui-btn layui-btn-sm refresh" href="javascript:location.replace(location.href);" title="刷新">
        <i class="layui-icon layui-icon-refresh"></i>
    </a>
</div>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-body ">
                    <form class="layui-form layui-col-space5" action="${ctx}/admin/config/index">
                        <div class="layui-inline layui-show-xs-block">
                            <input type="text" name="username" id="username" placeholder="请输入系统字典名"
                                   autocomplete="off"
                                   class="layui-input" value="${username}"></div>
                        <div class="layui-inline layui-show-xs-block">
                            <button class="layui-btn" lay-submit="" lay-filter="sreach">
                                <i class="layui-icon">&#xe615;</i></button>
                        </div>
                    </form>
                </div>
                <div class="layui-card-body ">
                    <table class="layui-table" id="list" lay-filter="tEvent"></table>
                </div>
            </div>
        </div>
    </div>
    <script id="toolbar" type="text/html">
        <div class="layui-btn-container">
            <button class="layui-btn layui-btn-danger" lay-event="delete">
                <i class="layui-icon layui-icon-delete"></i>批量删除
            </button>
            <button class="layui-btn" lay-event="add">
                <i class="layui-icon layui-icon-add-circle"></i>添加
            </button>
        </div>
    </script>
</div>
<script src="${ctx}/static/js/cookies.js"></script>
<script>
    layui.use(['table', 'util', 'upload', 'layer'], function () {
        var table = layui.table,
            util = layui.util,
            layer = layui.layer;
        // 显示跳转到本页前的操作结果
        if (${empty result?0:1}) {
            layer.msg('${result}');
        }
        // 从cookie中取分页大小，如果没有值，则使用默认10
        var pageSizeLocal = get_cookie("pageSizeLocal");
        var pageSize = (pageSizeLocal > 0) ? pageSizeLocal : 10;
        // 渲染列表
        table.render({
            elem: '#list'
            , url: '${ctx}/admin/config/list/paged'
            , where: {
                name: $("#name").val(),
                unitId: $("#unitId").val()
            }
            , toolbar: '#toolbar'
            , cols: [[
                {type: 'checkbox'}
                , {field: 'id', width: 60, title: 'id', sort: true}
                , {field: 'username', width: 100, title: '用户名', sort: true}
                , {field: 'realname', width: 150, title: '真实姓名', sort: true}
                , {field: 'idNo', width: 170, title: '身份证号码', sort: true}
                , {field: 'telPhone', width: 110, title: '固定电话'}
                , {field: 'mobilePhone', width: 110, title: '手机号码'}
                , {field: 'dept', width: 160, title: '所在单位'}
                <%-- 上面开始对应的是实体类的属性名 --%>
                , {fixed: 'right', width: 190, align: 'center', toolbar: '#option'}
            ]]
            , page: true
            , limit: pageSize
            , limits: layui_limits
            , even: true
            , size: 'sm'
            , done: function (res, curr, count) {
                // 如果分页大小发生变化，此回掉函数更新cookie中的分页大小
                var newLimit = (res.limit);
                if (pageSize !== newLimit) {
                    document.cookie = "pageSizeLocal=" + newLimit;
                }
            }
        });
        //头工具栏事件
        table.on('toolbar(tEvent)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            var data = checkStatus.data;
            var selIDs = [];
            for (var i = 0; i < data.length; i++) {
                selIDs.push(data[i].id);
            }
            switch (obj.event) {
                case 'delete':
                    layer.confirm('真的删除选中的' + data.length + "条记录么？", function (index) {
                        $.ajax({
                            url: "${ctx}/admin/config/del?id=" + selIDs,
                            success: function (res) {
                                layer.alert(res, function (index) {
                                    layer.close(index);
                                    location.replace(location.href);
                                });
                            }
                        });
                    });
                    break;
                case 'add':
                    xadmin.open('添加用户', '${ctx}/admin/config/edit', ${width}, ${heigth});
                    break;
            }
        });
        //监听行工具事件
        table.on('tool(tEvent)', function (obj) {
            var data = obj.data;
            if (obj.event === 'del') {
                layer.confirm('真的删除这行记录么？', function (index) {
                    $.ajax({
                        url: "${ctx}/admin/config/del?id=" + data.id,
                        method: 'post',
                        success: function (res) {
                            layer.alert(res, function (index) {
                                layer.close(index);
                                location.replace(location.href);
                            });
                        }
                    });
                });
            } else if (obj.event === 'edit') {
                xadmin.open('编辑系统字典', '${ctx}/admin/config/edit?id=' + data.id, ${width}, ${heigth})
            }
        });
    });
</script>
<script id="option" type="text/html">
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>
</body>
</html>
