<%@ page contentType="text/html; charset=UTF-8" %> 

<!DOCTYPE html> 
<html> 
<head>
  <title>회원탈퇴</title>
  <meta charset="utf-8">

</head>
<body>

<br><br>

<div class="container">
<h1 class="col-sm-offset-2 col-sm-10" >회원탈퇴</h1>
<br><br>
 <form class="form-horizontal" action="delete" method="post">
 
  <input type="hidden" name="id" value="${param.id}">
  <input type="hidden" name="oldfile" value="${param.oldfile}">
 
  <div class="form-group">
    <label class="col-sm-offset-2 col-sm-6">
        탈퇴를 하시면 더이상 컨텐트를 제공받을 수 없습니다.<br>
        그래도 탈퇴를 원하시면 아래 탈퇴버튼을 클릭하세요.
    </label>
  </div>	

  <div class="form-group">
    <div class="col-sm-offset-2 col-sm-10">
      <button class="btn-default btn" >탈퇴</button>
      <button class="btn-default btn" type="reset" onclick="history.back()">취소</button>
    </div>
  </div>

</form>

</div>
</body> 
</html> 

