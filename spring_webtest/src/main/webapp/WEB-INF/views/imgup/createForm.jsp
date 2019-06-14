<%@ page contentType="text/html; charset=UTF-8" %> 
<%@ include file="/ssi/ssi_img.jsp" %>
<!DOCTYPE html>
<html>
<head>
  <title>사진 등록</title>
  <meta charset="utf-8">

</head>
<body>
<br><br><br>

<div class="container">
<h2 class="col-sm-offset-1 col-sm-10" style="text-align: center">사진 등록</h2>
<br><br><br><br>
  <form class="form-horizontal" method="post" action="create" enctype="multipart/form-data">
			  
	<div class="form-group">
	  <label class="control-label col-sm-4" for="name">이름</label>
		<div class="col-sm-4">
		  <input type="text" name = "name" id="name" class="form-control">
		</div>
    </div>
		  
	<div class="form-group">
      <label class="control-label col-sm-4" for="title">제목</label>
		<div class="col-sm-4">
		  <input type="text" name = "title" id="title" class="form-control">
 		</div>
	</div>
		  
	<div class="form-group">
	  <label class="control-label col-sm-4" for="content">내용</label>
		<div class="col-sm-4">
		  <textarea rows="5" cols="5" id="content" name="content" class="form-control"></textarea>
		</div>
	</div>
		  
	<div class="form-group">
	  <label class="control-label col-sm-4" for="fname">사진</label>
		<div class="col-sm-4">
		  <input type="file" name = "fnameMF" id="fname" class="form-control" accept=".jpg,.png,.gif">
		</div>
	</div>
		  
	<div class="form-group">
	  <label class="control-label col-sm-4" for="passwd">비밀번호</label>
		<div class="col-sm-4">
		  <input type="password" name = "passwd" id="passwd" class="form-control">
	    </div>
    </div>
		 		  
		  
    <div class = "form-group">
      <div class="text-center">
        <button class="btn btn-default">등록</button>
        <button type="reset" class="btn btn-default">취소</button>
      </div>
    </div>
        
  </form>
</div>

</body>
</html>