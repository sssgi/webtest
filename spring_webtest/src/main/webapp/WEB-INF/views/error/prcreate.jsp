<%@ page contentType="text/html; charset=UTF-8" %> 
<%@ include file = "/ssi/ssi_member.jsp" %> 

<!DOCTYPE html> 
<html> 
<head>
	<title>오류메세지!</title>
	<meta charset="urf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
	<!-- jquery ajax에 필요한 부분 start -->
	<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.js"></script>
	<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/ajaxerror.js"></script>
	<!--jquery ajax에 필요한 부분 end  -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
    
	<script type="text/javascript">
		$(document).ready(function(){
		 var modal = '${msg}'; //Controller에서 가져온 데이터, 문자열(홑따움표사용)을 지역변수로 받음
		 checkModal(modal); //modal생성
		 
		 //history back일때는 modal 안보여주는 코드 1
		 history.replaceState({},null,null);
		 
		 function checkModal(modal){ //modal 생성함수 선언, 위의 지연변수를 매개변수로 받아서 지역변수로 사용
		     if(history.state) return; //코드2
		     if(modal){//javascript에서는 데이터가 존재하면 조건 true
		         $(".modal-body").html(modal);
		         $("#myModal").modal("show");
		     }
		   }
		});
	</script>
</head>
<body>
<div class="container">
	<!-- Modal -->
	  <div class="modal fade" id="myModal" role="dialog">
	    <div class="modal-dialog modal-sm-2">
	      <div class="modal-content">
	        <div class="modal-header">
	      
	          <h4 class="modal-title">알림 메세지!</h4>
	        </div>
	        <div class="modal-body">
	          <p>This is a small modal.</p>
	        </div>
	        <div class="modal-footer">
	          <button type="button" class="btn btn-default" data-dismiss="modal" onclick="history.back()">Close</button>
	        </div>
	      </div>
	    </div>
		</div>
	</div>
</body>
</html> 

