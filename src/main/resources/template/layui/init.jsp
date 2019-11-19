<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="java.util.GregorianCalendar" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    ResourceBundle resource = ResourceBundle.getBundle("config");
    request.setAttribute("ctx", request.getContextPath());
    request.setAttribute("proName", new String(resource.getString("project.name.cn").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
    request.setAttribute("version", resource.getString("project.version"));
    request.setAttribute("companyName", resource.getString("company.name"));
    request.setAttribute("companyUrl", resource.getString("company.homepage.url"));
    request.setAttribute("year", new GregorianCalendar().getWeekYear());
    request.setAttribute("width", 600);
    request.setAttribute("height", 600);
%>