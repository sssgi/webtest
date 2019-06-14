<%@ page contentType="text/html; charset=UTF-8" %> 
<%@ include file="/ssi/ssi_member.jsp" %>
<% 
// 	String root = request.getContextPath(); 
// 	String id = (String)session.getAttribute("id");
// 	String grade = (String)session.getAttribute("grade");
// 	String str ="기본페이지입니다.";
// 	if(id!=null && !grade.equals("A")){
// 		str = "안녕하세요" + id + "님!";
// 	} else if(id!=null && grade.equals("A")){
// 		str = "관리자 페이지입니다.";
// 	}
	
%> 

<c:set var ="str" value="기본페이지입니다"></c:set>
<c:choose>
	<c:when test="${not empty sessionScope.id && not sessionScope.grade=='A'}">
		<c:set var ="str" value="안녕하세요.${sessionScope.id }님"></c:set>
	</c:when>
	<c:when test="${not empty sessionScope.id && sessionScope.grade=='A'}">
		<c:set var ="str" value="관리자 페이지입니다."></c:set>
	</c:when>
</c:choose>

<!DOCTYPE html>
<html lang="en">
<head>
  <title>Bootstrap Example</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
  <!-- jquery ajax에 필요한 부분 -->
  <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.js"></script> 
  <script src="<%=request.getContextPath() %>/js/ajaxsetup.js"></script>
  <!-- jquery ajax에 필요한 부분 -->
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
  <style>
    .navbar-fixed-top{border-width:0 0 0px}
    .dropdown-menu{ min-width: 40px;}
    .container-fluid{padding-right:10px; padding-left:10px;}
  	.navbar-default{background-color:#9b59b6;}
  	.navbar-default .navbar-brand{color:#f9f9f9;}
  	.navbar-default .navbar-nav>li>a {color:#f9f9f9;}
    .navbar-default .navbar-nav>.open>a, .navbar-default .navbar-nav>.open>a:focus, .navbar-default .navbar-nav>.open>a:hover{
	background-color:inherit;
	color:#000000;
    }
	.navbar-nav .open .dropdown-menu{
	background-color: rgba( 255, 255, 255, 0.5 );
    }

    </style>

</head>
<body>
<nav class="navbar navbar-default navbar-fixed-top">
  <div class="container-fluid">
	<div class="navvar-header">
		  <a class="navbar-brand" href="${root}"><strong>VIXX</strong></a>
	</div>
	<ul class="nav navbar-nav navbar-right">
	  <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#"><span class="glyphicon glyphicon-th-list"></span>게시판</a>
	    <ul class="dropdown-menu">
	      <li><a href="${root}/bbs/create">생성</a></li>
	      <li><a href="${root}/bbs/list">목록</a></li>
	    </ul> 
	    </li>
	    
	  <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#"><span class="glyphicon glyphicon-th-list"></span>board</a>
	    <ul class="dropdown-menu">
	      <li><a href="${root}/board/create">생성</a></li>
	      <li><a href="${root}/board/list">목록</a></li>
	    </ul> 
	  </li>  
	    
	    
	  <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#"><span class="glyphicon glyphicon-th-list"></span>메모</a>
	    <ul class="dropdown-menu">
	      <li><a href="${root}/memo/create">생성</a></li>
	      <li><a href="${root}/memo/list">목록</a></li>
	    </ul> 
	  </li>
	    
	  <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#"><span class="glyphicon glyphicon-th-list"></span>팀</a>
	    <ul class="dropdown-menu">
	      <li><a href="${root}/team/create">생성</a></li>
	      <li><a href="${root}/team/list">목록</a></li>
	    </ul> 
	  </li> 
	  
	  <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#"><span class="glyphicon glyphicon-th-list"></span>방명록</a>
	    <ul class="dropdown-menu">
	      <li><a href="${root}/guestbook/create">생성</a></li>
	      <li><a href="${root}/guestbook/list">목록</a></li>
	    </ul> 
	  </li>
	  
	  <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#"><span class="glyphicon glyphicon-th-list"></span>사진첩</a>
	    <ul class="dropdown-menu">
	      <li><a href="${root}/imgup/create">생성</a></li>
	      <li><a href="${root}/imgup/list">목록</a></li>
	    </ul> 
	  </li>

	  <c:choose> 
	  <c:when test = "${empty id }">
	  <li><a href="${root}/member/agreement"><span class="glyphicon glyphicon-user"></span>Signup</a></li>
	  <li><a href="${root}/member/login"><span class="glyphicon glyphicon-log-in"></span>Login</a></li>
	  </c:when>
	  <c:otherwise>
	  <li><a href="${root}/member/read"><span class="glyphicon glyphicon-info-sign"></span>나의정보</a></li>
	  <li><a href="${root}/member/logOut"><span class="glyphicon glyphicon-log-out"></span>Logout</a></li>
	  
	  
	  <c:if test="${not empty sessionScope.id && sessionScope.grade=='A'}">
	  <li><A href="${root}/admin/list"><span class="glyphicon glyphicon-th-list"></span>회원목록</A></li>
	  </c:if>
	  </c:otherwise>
	  </c:choose>
	  <c:if test = "${not empty id }">
	  <li><a href="#"><span class="glyphicon glyphicon-user"></span>안녕하세요. ${sessionScope.id}님!</a></li>
	  </c:if>

	</ul>  
  </div>
</nav>

</body>
</html>
