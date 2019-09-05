<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/init.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${entityDesc}编辑-${proName}-${version}</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <tags:head-link/>
</head>
<body>
<div class="layui-fluid">
    <div class="layui-row">
        <div class="layui-card">
            <div class="layui-card-body ">
                <form class="layui-form">
                    ${fieldInputs}
                    <div class="layui-form-item">
                        <label class="layui-form-label"></label>
                        <button class="layui-btn" lay-submit="" lay-filter="save">确定</button>
                        <input type="hidden" name="$key$" id="$key$" value="${$entity$.$key$}"/>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script src="${ctx}/static/js/common.js" charset="utf-8"></script>
<script type="text/javascript">
    layui.use(['form', 'jquery', 'layer', 'upload'], function () {
        var form = layui.form,
            $ = layui.jquery,
            layer = layui.layer;
        form.on('submit(save)', function (data) {
            var field = data.field;
            $.ajax({
                url: "${ctx}${urlBase}/edit/submit",
                data: field,
                method: 'post',
                success: function (res) {
                    var message = res.indexOf('add')>0?'添加成功':'修改成功';
                    layer.alert(message, function (index) {
                        parent.layer.close(index);
                        parent.location.reload();
                    })
                }
            });
            return false;
        });
    });
</script>
</body>
</html>
