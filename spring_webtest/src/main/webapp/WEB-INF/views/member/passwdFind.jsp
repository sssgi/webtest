<%@ page contentType="text/html; charset=UTF-8" %> 
<%@ include file = "/ssi/ssi_member.jsp" %> 

<!DOCTYPE html> 
<html> 
<head>
<title>비밀번호 찾기</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
<!-- jquery ajax에 필요한 부분 -->
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.js"></script> 
<script src="<%=request.getContextPath() %>/js/ajaxsetup.js"></script>
<!-- jquery ajax에 필요한 부분 -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
<script type="text/javascript">
  	function passwdFind(){
  		if($("#mname").val()==""){
  			alert('이름을 입력해주세요');
  			document.frm.mname.focus();  			
  		}else if($("#id").val()==""){
  			alert('ID를 입력해주세요');
  			document.frm.id.focus(); 
  		}else{
  			var url="passwdFindCheck";
  			var param = $("#frm").serialize();
  			$.get(
  				url,
  				param,
  				function(data,textStatus){
  					data = data.trim();
  					
  					$("#passwdFind").text(data);
  					
  				} 			
  			);			  			
  		}
  	}

</script>
</head>
<body>
<br><br><br>
<div class="container">
<h1 class="col-sm-offset-2 col-sm-10" >비밀번호찾기</h1>
	<form class="form-horizontal" id="frm" name="frm"  >
    	<div class="form-group">
      	<label class="control-label col-sm-2" for="id">ID</label>
        	<div class="col-sm-6">
          		<input type="text" name="id" id="id" class="form-control" placeholder="user1">
        	</div>
    	</div>
  
	  	<div class="form-group">
	   	<label class="control-label col-sm-2" for="mname">이름</label>
	    	<div class="col-sm-6">
	      		<input type="text" id="mname" name="mname" class="form-control"  placeholder="홍길동"> 
	    	</div>
	  	</div>
	
	  	<div class="form-group">
	    	<div class="col-sm-offset-2 col-sm-10">
			    <button class="btn-default btn" type="button" onclick="passwdFind()">찾기</button>
			    <button class="btn-default btn" type="reset">취소</button>
			    <br><br>
			    <div id="passwdFind"></div>	
	    	</div>
	  	</div>
	</form>
</div>
</body> 
</html> 

