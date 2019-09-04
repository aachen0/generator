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
                <form class="layui-form" action="${ctx}${urlBase}/edit/submit" method="post">
                    ${fieldInputs}
                    <div class="layui-form-item">
                        <label class="layui-form-label"></label>
                        <button class="layui-btn" lay-submit="">确定</button>
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
            layer = layui.layer,
            upload = layui.upload;
        //普通图片上传
        transImgToBase64({
            upload: upload,
            elem: '#businessLicenseStrShow',
            url: '${ctx}/declare/upload',
            paramElem: $('#businessLicenseStr'),
        });
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

        <c:if test="${!empty result}">
        layer.msg("${result}", {
            icon: 6,
            time: 500// 延时半秒
        }, function () {
            var index = parent.layer.getFrameIndex(window.name);
//关闭当前frame
            parent.layer.close(index);
        });
        </c:if>
    });
</script>
</body>
</html>
