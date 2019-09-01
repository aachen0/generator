<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.GregorianCalendar" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
<%--
  Created by IntelliJ IDEA.
  User: aachen0
  Date: 2019/5/27
  Time: 16:36
  初始化显示层共同参数.
--%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
    ResourceBundle resource = ResourceBundle.getBundle("config");
    request.setAttribute("ctx", request.getContextPath());
    request.setAttribute("proName", new String(resource.getString("project.name.cn").getBytes(StandardCharsets.ISO_8859_1),StandardCharsets.UTF_8));
    request.setAttribute("version",resource.getString("project.version"));
    request.setAttribute("company",new String(resource.getString("company.name").getBytes(StandardCharsets.ISO_8859_1),StandardCharsets.UTF_8));
    request.setAttribute("year", new GregorianCalendar().getWeekYear());
    request.setAttribute("width",600);
    request.setAttribute("height",600);
%>
<script>
    const layui_limits = [5, 10, 15, 20, 25, 30,100];
</script>