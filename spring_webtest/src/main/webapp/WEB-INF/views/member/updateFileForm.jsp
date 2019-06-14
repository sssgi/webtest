<%@ page contentType="text/html; charset=UTF-8" %> 
<%@ include file = "/ssi/ssi_member.jsp" %> 
<!DOCTYPE html> 
<html> 
<head>
<title>사진수정</title>
<meta charset="utf-8">

</head>
<body>
<br><br>
<div class="container">
<h1 class="col-sm-offset-2 col-sm-10" >사진수정</h1>
	<form class="form-horizontal" action="updateFile" method="post" enctype="multipart/form-data">
		<input class="hidden" name="id" value="${param.id}">
		<input class="hidden" name="oldfile" value="${param.oldfile}">
	
		<div class="form-group">
		<label class="control-label col-sm-2" for="oldfile">원본파일</label>
			<div class="col-sm-6">
			<img src="./storage/${param.oldfile}" class="img-rounded" width="200px" height="200px">
			</div>
		</div>

		<div class="form-group">
		<label class="control-label col-sm-2" for="fname">변경파일</label>
			<div class="col-sm-6">
		    <input type="file" name="fnameMF" class="form-control" accept=".png,.jpg,.gif" required="required">
		  	</div>
		</div>
		
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-5">
		    	<button class="btn-default btn" >수정</button>
		    	<button class="btn-default btn" type="button" onclick="history.back()">취소</button>
		    </div>
		</div>
	</form>
</div>
</body> 
</html> 

