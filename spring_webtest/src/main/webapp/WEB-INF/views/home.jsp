<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=utf-8" %>
<html>
<head>
	<title>Home</title>
	<style>
	body{
		background-image: url(./images/main1.jpg);
	}
	h1{
		color:white;
	}
	</style>
	<script type="text/javascript">
		$(document).ready(function(){
		 var modal = '${msg}'; //Controller에서 가져온 데이터
		 checkModal(modal); //modal생성
		 
		 //history back일때는 modal 안보여주는 코드 1
		 history.replaceState({},null,null);
		 
		 function checkModal(modal){ //modal 생성함수 선언
		     if(history.state) return; //코드2
		     if('${msg}'){ //javascript에서는 데이터가 존재하면 조건 true
		         $(".modal-body").html(modal);
		         $("#myModal").modal("show");
		     }
		   }
		});
	</script>
</head>
<body>
<br><br>
<h1>
	Hello world!  
</h1>

<P> The time on the server is ${serverTime}. </P>
<body>
<div class="container">
	<!-- Modal -->
	  <div class="modal fade" id="myModal" role="dialog">
	    <div class="modal-dialog modal-sm">
	      <div class="modal-content">
	        <div class="modal-header">
	      
	          <h4 class="modal-title">알림 메세지!</h4>
	        </div>
	        <div class="modal-body">
	          <p>This is a small modal.</p>
	        </div>
	        <div class="modal-footer">
	          <button type="button" class="btn btn-default" data-dismiss="modal" >Close</button>
	        </div>
	      </div>
	    </div>
	  </div>
</div>
</body>	
</html>
