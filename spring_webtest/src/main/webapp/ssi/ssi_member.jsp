<%@ page contentType="text/html; charset=UTF-8" %> 
<% 	request.setCharacterEncoding("utf-8"); %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="util" uri="/ELFunctions" %>
<c:set var="root" value="${pageContext.request.contextPath }"/>
<% 
	String upDir = "/member/storage";
	upDir = application.getRealPath(upDir);
%>
