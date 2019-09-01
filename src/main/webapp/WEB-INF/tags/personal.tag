<%@ tag pageEncoding="utf-8" %>
<div class="layui-form-item">
    <label for="identityNo" class="layui-form-label">
        <span class="we-red">*</span>身份证号
    </label>
    <div class="layui-input-inline">
        <input type="text" class="layui-input ${empty personal?'':'layui-disabled'}" id="identityNo" name="identityNo"
               value="${personal.investorCode}">
    </div>
</div>
<div class="layui-form-item">
    <label for="personalName" class="layui-form-label">
        <span class="we-red">*</span>姓名
    </label>
    <div class="layui-input-inline">
        <input type="text" class="layui-input ${empty personal?'':'layui-disabled'}" id="personalName" name="personalName" value="${personal.name}"
                ${empty personal?'':'disabled'}>
    </div>
</div>
<div class="layui-form-item">
    <label for="personalAddress" class="layui-form-label"><span class="we-red">*</span>地址
    </label>
    <div class="layui-input-block">
        <input type="text" class="layui-input ${empty personal?'':'layui-disabled'}" id="personalAddress" name="personalAddress" value="${personal.address}"  ${empty personal?'':'disabled'}>
    </div>
</div>
<div class="layui-form-item">
    <label for="personalContact" class="layui-form-label"><span class="we-red">*</span>绑定手机号
    </label>
    <div class="layui-input-inline">
        <input type="text" class="layui-input ${empty personal?'':'layui-disabled'}" id="personalContact" name="personalContact" value="${personal.contact}"  ${empty personal?'':'disabled'}>
    </div>
    <label class="layui-form-label-col we-green">用于用户接收短信</label>
</div>
<div class="layui-form-item">
    <label for="identityCardFront" class="layui-form-label"><span class="we-red">*</span>身份证正面
    </label>
    <div class="layui-input-inline">
        <input type="hidden" class="layui-input" id="identityCardFront" name="identityCardFront"
               value="${personal.identityCardFront}">
        <img id="identityCodeFrontShow" src="${empty personal.identityCardFront?'/static/imgs/noimg.jpg':personal.identityCardFront}" height="128">
    </div>
    <label for="identityCardBack" class="layui-form-label"><span class="we-red">*</span>身份证背面
    </label>
    <div class="layui-input-inline">
        <input type="hidden" class="layui-input" id="identityCardBack" name="identityCardBack"
               value="${personal.identityCardBack}">
        <img id="identityCodeBackShow" src="${empty personal.identityCardBack?'/static/imgs/noimg.jpg':personal.identityCardBack}" height="128">
    </div>
</div>
<div class="layui-form-item">
    <label for="propertyCertificate" class="layui-form-label"><span class="we-red">*</span>房产证
    </label>
    <div class="layui-input-inline">
        <input type="hidden" class="layui-input" id="propertyCertificate" name="propertyCertificate"
               value="${personal.propertyCertificate}">
        <img id="propertyCertificateShow" src="${empty personal.propertyCertificate?'/static/imgs/noimg.jpg':personal.propertyCertificate}" height="128">
    </div>
</div>

<div class="layui-form-item">
    <label for="householdRegister" class="layui-form-label"><span class="we-red">*</span>户口本
    </label>
    <div class="layui-input-inline">
        <input type="hidden" class="layui-input" id="householdRegister" name="householdRegister"
               value="${personal.householdRegister}">
        <img id="householdRegisterShow" src="${empty personal.householdRegister?'/static/imgs/noimg.jpg':personal.householdRegister}" height="128">
    </div>
</div>
<div class="layui-form-item">
    <label for="homesteadCertificate" class="layui-form-label"><span class="we-red">*</span>宅基地证明
    </label>
    <div class="layui-input-inline">
        <input type="hidden" class="layui-input" id="homesteadCertificate" name="homesteadCertificate"
               value="${personal.homesteadCertificate}">
        <img id="homesteadCertificateShow" src="${empty personal.homesteadCertificate?'/static/imgs/noimg.jpg':personal.homesteadCertificate}" height="128">
    </div>
</div>