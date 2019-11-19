const layui_limits = [5, 10, 15, 20, 25, 30, 100];
const phone_regex = /^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$/;
$(function () {
    $('[type="reset"]').on('click',function () {
        location.replace(location.href);
    })
});

/**
 * 处理edit/submit接口请求后的交互
 * @param res 请求返回数据，数据结构为：ResponseData
 * @param fresh 保存成功后是否刷新上一级页面
 */
function doResult(res, fresh) {
    console.log(res);
    let index;
    if (res.code === 200) {
        index = parent.layer.getFrameIndex(window.name); //获取当前窗口的name
        let result = res.data;
        if (result === 1) {
            res.msg = "添加成功";
        } else if (result === 2) {
            res.msg = "修改成功";
        } else if (result === -1) {
            res.msg = "添加失败";
        } else if (result === 0) {
            res.msg = "修改失败";
        }
        layer.alert(res.msg, function () {
            parent.layer.close(index);
            if (fresh){
                window.parent.location.reload();
            }
        });
    } else if (data.code === 500) {
        index = parent.layer.getFrameIndex(window.name); //获取当前窗口的name
        layer.alert(res.msg, function () {
            parent.layer.close(index);
        });
    }
}



//封装上传逻辑
function uploadPlus(upload, data) {
    let elem = data.elem;
    let url = data.url;
    let $listView = $(data.elemViewList);
    let bindActiion = data.elemBindAction;
    let fileNames = data.fileNames;
    let recordIdFun = data.recordIdFun;
    if (!fileNames.length) {
        console.log("没有附件分类，无法上传");
        return;
    }
    let fileNameValue = fileNames[0].value;
    let uploadListIns = upload.render({
        elem: elem,
        url: url,
        data: {
            fileName: fileNameValue,
            plantCatalogCode: function () {
                return $('#plantCatalogCode').val();
            },
            plantCountyId: function () {
                return $('#plantCountyId').val();
            },
            recordId: recordIdFun
        },
        accept: 'file',
        exts: 'jpg|jpeg|bmp|pdf|png',
        multiple: true,
        auto: false,
        bindAction: bindActiion,
        choose: function (obj) {
            if (!this.data.plantCatalogCode()) {
                layer.alert("缺少电站目录号，无法上传！");
                return false;
            }
            if (!this.data.plantCountyId()) {
                layer.alert("缺少县区信息，无法上传！");
                return false;
            }
            if (!this.data.recordId()) {
                layer.alert("缺少附件归属信息，无法上传！");
                return false;
            }
            let files = this.files = obj.pushFile();
            obj.preview(function (index, file, result) {
                let select = '<select name="fileName" class="tinySelect">';
                for (let i = 0; i < data.fileNames.length; i++) {
                    let option = '<option value="' + data.fileNames[i].value + '">' + data.fileNames[i].name + '</option>';
                    select = select + option;
                }
                select = select + '</select>';
                var tr = $(['<tr id="upload-' + index + '">'
                    , '<td>' + file.name + '</td>'
                    , '<td>' + (file.size / 1014).toFixed(1) + 'kb</td>'
                    , '<td>等待上传</td>'
                    , '<td><div class="layui-inline">'
                    , select
                    , '</div>'
                    , '</td>'
                    , '<td>'
                    , '<button class="layui-btn layui-btn-xs demo-reload layui-hide">重传</button>'
                    , '<button class="layui-btn layui-btn-xs layui-btn-danger demo-delete">删除</button>'
                    , '</td>'
                    , '</tr>'].join(''));
                //单个重传
                obj.resetFile(index, file, fileNameValue); //重命名文件名，layui 2.3.0 开始新增
                tr.find('.tinySelect').on('change', function () {
                    fileNameValue = $(this).val();
                    obj.resetFile(index, file, fileNameValue); //重命名文件名，layui 2.3.0 开始新增
                });
                tr.find('.demo-reload').on('click', function () {
                    formNameValue = $(this).parents().children().find(".tinySelect").val();
                    obj.resetFile(index, file, formNameValue); //重命名文件名，layui 2.3.0 开始新增
                    obj.upload(index, file);
                    return false;
                });
                tr.find('.editTypeInput').on('click', function () {
                    $(this).next().show();
                });
                tr.find('.allselect span').on('click', function () {
                    $(this).parent().prev().val($(this).html());
                    $(this).parent().prev().attr("fileName", $(this).html());
                    $(this).parent().hide();
                });

                //删除
                tr.find('.demo-delete').on('click', function () {
                    delete files[index]; //删除对应的文件
                    tr.remove();
                    uploadListIns.config.elem.next()[0].value = ''; //清空 input file 值，以免删除后出现同名文件不可选
                });
                $listView.append(tr);
                fileNameValue = $(".tinySelect").val();
            });
        }, before: function (obj) {
        }
        , done: function (res, index, upload) {
            console.log("plantCatalogCode:" + this.data.plantCatalogCode() + ",plantCountyId:" + this.data.plantCountyId() + ",recordId:" + this.data.recordId());
            if (res.code === 0) { //上传成功
                var tr = $listView.find('tr#upload-' + index)
                    , tds = tr.children();
                tds.eq(2).html('<span style="color: #5FB878;">上传成功</span>');
                return delete this.files[index]; //删除文件队列已经上传成功的文件
            }
            this.error(index, upload);
        }
        , error: function (index, upload) {
            var tr = $listView.find('tr#upload-' + index)
                , tds = tr.children();
            tds.eq(2).html('<span style="color: #FF5722;">上传失败</span>');
            tds.eq(4).find('.demo-reload').removeClass('layui-hide'); //显示重传
        }
    });
}

//处理删除后逻辑，需引入layer
function delAfter(res) {
    console.log(res);
    if (res.code === 200) {
        if (res.msg > 0) {
            layer.alert("删除成功，共删除" + res.msg + "条记录", function (index) {
                layer.close(index);
                location.replace(location.href);
            });
        } else {
            layer.alert("未删除任何记录", function (index) {
                layer.close(index);
            });
        }
    }
    if (res.code === 500) {
        layer.alert("删除失败:" + res.msg, function (index) {
            layer.close(index);
        });
    }
}

//处理附件查看url路径
function joint(data) {
    return data.filePath + '/' + data.fileName + '.' + data.fileType;
}

//管理已上传附件
function attachmentPreview(table, data) {
    // console.log(data)
    var tableIns = table.render({
        elem: data.elem,
        url: data.url,
        where: data.where,
        cols: [[
            {field: 'fileTitle', width: '200', title: '附件类型'}
            , {field: 'fileSize', width: '123', title: '附件大小'}
            , {field: 'fileType', width: '80', title: '附件后缀'}
            , {field: 'uploadPerson', width: '110', title: '上传人'}
            , {field: 'uploadTime', width: '150', title: '上传时间'}
            , {
                fixed: 'right', width: 140, align: 'center', title: '操作', templet: function (d) {
                    var op = '<a class="layui-btn layui-btn-xs" lay-event="details">查看</a>\n';
                    //非只读状态时可进行的操作
                    if (!data.readOnly) {
                        op += '<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>';
                    }
                    return op;
                }
            }
        ]],
        even: true,
        size: 'sm'
    });
    //监听行工具事件
    table.on('tool(tEvent)', function (obj) {
        var attachment = obj.data;
        if (obj.event === 'del') {
            layer.confirm('真的删除这行记录么？', function (index) {
                $.ajax({
                    url: data.delUrl + attachment.id,
                    method: 'post',
                    success: function (res) {
                        delAfter(res);
                    }
                });
            });
        } else if (obj.event === 'details') {
            xadmin.openAttachment('查看附件', joint(attachment));
        }
    });
    return tableIns;
}