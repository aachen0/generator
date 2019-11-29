<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/init.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${entityDesc}列表-${proName}-${version}</title>
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
        <a href="${urlBase}/index">${entityDesc}管理</a>
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
                    <form class="layui-form layui-col-space5">
                        <div class="layui-inline layui-show-xs-block">
                            <input type="text" name="${searchField}"
                                   placeholder="${searchFieldDesc}搜索"
                                   autocomplete="off"
                                   class="layui-input"></div>
                        <div class="layui-inline layui-show-xs-block">
                            <button class="layui-btn" lay-submit="" lay-filter="search">
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
    layui.use(['table', 'upload', 'layer', 'form'], function () {
        var table = layui.table,
            layer = layui.layer,
            form = layui.form,
            open_width = 600,// 弹窗宽度度
            open_height = 500;// 弹窗高度
        // 从cookie中取分页大小，如果没有值，则使用默认10
        var pageSizeLocal = get_cookie("pageSizeLocal");
        var pageSize = (pageSizeLocal > 0) ? pageSizeLocal : 10;
        // 渲染列表
        var $entity$TableIns = table.render({
            elem: '#list'
            , url: '${ctx}${urlBase}/list/paged'
            , toolbar: '#toolbar'
            , cols: [[
                {type: 'checkbox'},
                {type: 'numbers', title: '序号', width: 60}
                ${cols}, {fixed: 'right', width: 190, align: 'center', toolbar: '#option'}
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
                //todo 下面的data[i].$key$根据实际情况更改
                selIDs.push(data[i].$key$);
            }
            switch (obj.event) {
                case 'delete':
                    if (data.length) {
                        layer.confirm('真的删除选中的' + data.length + "条记录么？", function (index) {
                            $.ajax({
                                url: "${ctx}${urlBase}/del?ids=" + selIDs,
                                method: 'post',
                                success: function (res) {
                                    delAfter(res);
                                }
                            });
                        });
                    } else {
                        layer.msg("请选择需要删除的记录");
                    }
                    break;
                case 'add':
                    xadmin.open('添加${entityDesc}', '${ctx}${urlBase}/edit', open_width, open_height);
                    break;
            }
        });
        //监听行工具事件
        table.on('tool(tEvent)', function (obj) {
            var data = obj.data;
            if (obj.event === 'del') {
                layer.confirm('真的删除这行记录么？', function (index) {
                    $.ajax({
                        //todo 下面的data.$key$根据实际情况更改
                        url: "${ctx}${urlBase}/del?ids=" + data.$key$,
                        method: 'post',
                        success: function (res) {
                            delAfter(res);
                        }
                    });
                });
            } else if (obj.event === 'edit') {
                //todo 下面的data.$key$根据实际情况更改
                xadmin.open('编辑${entityDesc}', '${ctx}${urlBase}/edit?$key$=' + data.$key$, open_width, open_height);
            }
        });
        form.on('submit(search)', function (d) {
            $entity$TableIns.reload({
                where: d.field
            });
            return false;
        });
    });
</script>
<script id="option" type="text/html">
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>
</body>
</html>