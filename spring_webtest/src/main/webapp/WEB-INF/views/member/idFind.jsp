<%@ page contentType="text/html; charset=UTF-8" %> 
<%@ include file = "/ssi/ssi_member.jsp" %> 

<!DOCTYPE html> 
<html> 
<head>
<title>ID찾기</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
<!-- jquery ajax에 필요한 부분 -->
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.js"></script> 
<script src="<%=request.getContextPath() %>/js/ajaxsetup.js"></script>
<!-- jquery ajax에 필요한 부분 -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
<script type="text/javascript">
  	function idFind(){
  		if($("#mname").val()==""){
  			alert('이름을 입력해주세요');
  			document.frm.mname.focus();  			
  		}else if($("#email").val()==""){
  			alert('이메일을 입력해주세요');
  			document.frm.email.focus(); 
  		}else{
  			var url="idFindCheck";
  			var param = $("#frm").serialize();
  			$.get(
  				url,
  				param,
  				function(data,textStatus){
  					data = data.trim();
  					
  					$("#idFind").text(data);
  					
  				}
  			
  			);
  			  			
  		}
  		
  	}

</script>
</head>
<body>
<br><br><br>
<div class="container">
<h1 class="col-sm-offset-2 col-sm-10" >아이디찾기</h1>
	<form class="form-horizontal" id="frm" name="frm" >
    	<div class="form-group">
      	<label class="control-label col-sm-2" for="mname">이름</label>
        	<div class="col-sm-6">
          		<input type="text" name="mname" id="mname" class="form-control" placeholder="홍길동">
        	</div>
   		</div>
  
		<div class="form-group">
		<label class="control-label col-sm-2" for="email" >email</label>
			<div class="col-sm-6">
		    	<input type="text" id="email" name="email" class="form-control"  placeholder="email1@mail.com"> 
		    </div>
		</div>
		
		<div class="form-group">
		    <div class="col-sm-offset-2 col-sm-10">
		    	<button class="btn-default btn" type="button" onclick="idFind()" >찾기</button>
		      	<button class="btn-default btn" type="reset">취소</button><br>
		      	<div id="idFind"></div>
		    </div>
		</div>
	</form>
</div>
</body> 
</html> 
