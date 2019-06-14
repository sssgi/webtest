<%@ page contentType="text/html; charset=UTF-8" %> 
<%@ include file = "/ssi/ssi_member.jsp" %> 

<!DOCTYPE html> 
<html> 
<head>
  <title>ID중복확인</title>
  <meta charset="urf-8">
  <script type="text/javascript">
  	function read(id){
 	  var url = "${root}/member/read"; <%--admin경로와 다르기때문에 root사용 --%>
  	  url += "?id="+id;
  	  location.href=url; 	
  	}
  
  
  </script>

</head>
<body>
<br><br><br>
<div class="container">
<h1 class="col-sm-offset-2 col-sm-10" >회원목록</h1>
	<form class="form-inline" method="post" action="./list">
		<div class="form-group">
			<select class="form-control" name="col">
	        <option value="mname" <c:if test="${col=='mname'}">selected</c:if>>이름</option>
	        <option value="id" <c:if test="${col=='id'}">selected</c:if>>아이디</option>
	        <option value="email" <c:if test="${col=='email'}">selected</c:if>>이메일</option>
	        <option value="total"<c:if test="${col=='total'}">selected</c:if>>전체출력</option>
	      	</select>
    	</div>
  
    	<div class="form-group">
      		<input type="text" class="form-control" name="word" value="${word }">
    	</div>
    
    	<button class="btn btn-default">검색</button>
    	<button type="button" class="btn btn-default" onclick="location.href='${root}/member/create'">등록</button>
	</form>

<br>

	<c:forEach var="dto" items="${list }">
  	<table class="table table-bordered">
	    <tr>
	    	<td rowspan="5" class="col-sm-2">
	        	<img src = "${root}/member/storage/${dto.fname}" class="img-roundedm" width="200px" height="200px" >        
	    	</td>
	      	<th class="col-sm-2">아이디</th>
	      	<td class="col-sm-7"><a href="javascript:read('${dto.id}')">${dto.id}</a></td>   
	    	</tr>
	    
	    <tr>   
	      <th>성명</th>
	      <td>${dto.mname}</td>
	    </tr>
	    <tr>
	      <th>전화번호</th>
	      <td>${dto.tel}</td>
	    </tr>
	    <tr>
	      <th>이메일</th>
	      <td>${dto.email}</td>
	    </tr>
	    <tr>
	      <th>주소</th>
	      
	      <td>(${dto.zipcode }) ${dto.address1}${dto.address2}</td>
	
	    </tr>        
	  </table>
</c:forEach>
 ${paging }
</div>
</body> 
</html> 

