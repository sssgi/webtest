<%@ page contentType="text/html; charset=UTF-8" %> 
<% 	request.setCharacterEncoding("utf-8"); %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="util" uri="/ELFunctions" %>
<c:set var="root" value="${pageContext.request.contextPath }"/>

<%
	request.setCharacterEncoding("utf-8");
			
	String upDir = "/imgup/storage";
	String tempDir = "/imgup/temp";

	upDir = application.getRealPath(upDir);
	tempDir = application.getRealPath(tempDir);
%>



