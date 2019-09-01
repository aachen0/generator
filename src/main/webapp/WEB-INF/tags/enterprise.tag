<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag pageEncoding="utf-8" %>
<div class="layui-form-item">
    <label for="organizationCode" class="layui-form-label long-label">
        <span class="we-red">*</span>机构代码
    </label>
    <div class="layui-input-inline">
        <input type="text" class="layui-input ${empty enterprise?'':'layui-disabled'}" id="organizationCode"
               name="organizationCode"
               value="${enterprise.investorCode}">
    </div>
</div>
<div class="layui-form-item">
    <label for="enterpriseName" class="layui-form-label">
        <span class="we-red">*</span>企业名称
    </label>
    <div class="layui-input-block">
        <input type="text" class="layui-input" id="enterpriseName"
               name="enterpriseName" value="${enterprise.name}">
    </div>
</div>
<div class="layui-form-item">
    <label for="enterpriseAddress" class="layui-form-label"><span class="we-red">*</span>地址
    </label>
    <div class="layui-input-block">
        <input type="text" class="layui-input" id="enterpriseAddress"
               name="enterpriseAddress" value="${enterprise.address}">
    </div>
</div>
<div class="layui-form-item">
    <label for="enterpriseContact" class="layui-form-label"><span class="we-red">*</span>绑定手机号
    </label>
    <div class="layui-input-inline">
        <input type="text" class="layui-input" id="enterpriseContact"
               name="enterpriseContact" value="${enterprise.contact}">
    </div>
    <label class="layui-form-label-col we-green">用于用户接收短信</label>
</div>
<div class="layui-form-item">
    <label for="legalPersonName" class="layui-form-label"><span class="we-red">*</span>法人姓名
    </label>
    <div class="layui-input-inline">
        <input type="text" class="layui-input" id="legalPersonName"
               name="legalPersonName"
               value="${enterprise.legalPersonName}">
    </div>
</div>
<div class="layui-form-item">
    <label for="legalPersonIdNo" class="layui-form-label"><span class="we-red">*</span>法人身份证
    </label>
    <div class="layui-input-inline">
        <input type="text" class="layui-input" id="legalPersonIdNo"
               name="legalPersonIdNo"
               value="${enterprise.legalPersonIdNo}">
    </div>
</div>
<div class="layui-form-item">
    <label class="layui-form-label"><span class="we-red">*</span>机构代码证
    </label>
    <div class="layui-input-block">
        <img id="businessLicenseStrShow"
             src="${empty enterprise.businessLicenseStr?'/static/imgs/noimg.jpg':enterprise.businessLicenseStr}"
             height="128">
        <input type="hidden" name="businessLicenseStr" id="businessLicenseStr" value="${enterprise.businessLicenseStr}">
    </div>
</div>