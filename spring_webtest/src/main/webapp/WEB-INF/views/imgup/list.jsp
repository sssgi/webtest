<%@ page contentType="text/html; charset=UTF-8" %> 
<%@ include file = "/ssi/ssi_img.jsp" %>

<!DOCTYPE html> 
<html> 
<head>
<title>이미지 목록</title>
<meta charset="utf-8">
<script type="text/javascript">
  	
  	function read(imgno){
  		var url = "read";
  		url += "?imgno="+imgno;
        url += "&col=${col}";
        url += "&word=${word}";
        url += "&nowPage=${nowPage}"
  		location.href=url; 		
  	}
  	
  	function filedown(fname){
		var url = "${root}/download";
		url += "?dir=/imgup/storage";
		url += "&filename="+fname;
		location.href=url;
	}

</script>


<script type="text/javascript">
	//등록된 후 목록으로 갈때, 성공했다는 모달창(팝업)띄움
	$(document).ready(function(){
		var modal = '${msg}';//Controller에서 가져온 데이터
	 	checkModal(modal); //modal생성
	 
	 	//history back일때는 modal 안보여주는 코드 1
		 history.replaceState({},null,null);
	 
	 	function checkModal(modal){ //modal 생성함수 선언
	    	if(history.state) return; 
	     	if(modal){
	        	 $(".modal-body").html(modal);
	         	$("#myModal").modal("show");
	     	}	
	   	}
	});
</script>
<style>

	th{text-align: center !important;}
	td{
		vertical-align: middle !important;
		text-align: center;
	}
	
</style>
</head>

<body>
<br><br>
<div class="container">
<h1 class="col-sm-offset-1 col-sm-10" style="text-align: center">이미지 목록</h1><br>
	<table class="table table-bordered">	 
    	<tr>
	    	<th>이미지</th>
	      	<th>순차</th>	
	      	<th>제목</th>
	      	<th>작성자</th>
	      	<th>조회수</th>
	      	<th>등록일</th>
	      	<th>다운로드</th>
		</tr>
  		<c:forEach var="dto" items="${list}">
  		<tr>   
			<td><img src="./storage/${dto.fname}" class="img-rounded" style="width:80px; height:80px;" /></td>
		    <td>${dto.imgno }</td>
		    <c:set var="rcount" value="${util:rcount2(dto.imgno,irinter)}"/>    
		    <td>
		    <a href="javascript:read('${dto.imgno}')">${dto.title }</a>
		    <c:if test="${rcount>0 }"><span class="badge">${rcount}</span></c:if>
		    </td>
		    <td>${dto.name }</td>
		    <td>${dto.viewcnt }</td>
		    <td>${dto.regdate}</td>
		    <td><a href="javascript:filedown('${dto.fname}')"><span class="glyphicon glyphicon-download-alt"></span></a></td>
		</tr>
  		</c:forEach>
	</table>
 
 	<form style="text-align: center" class="form-inline" action="./list" method="post">
    	<div class= "form-group">
      		<select class="form-control" name = "col">
        	<option value="name" <c:if test="${col=='name'}">selected</c:if>>작성자</option>
        	<option value="title" <c:if test="${col=='title'}">selected</c:if>>제목</option>
	        <option value="content" <c:if test="${col=='content'}">selected</c:if>>내용</option>	
	        <option value="title_content" <c:if test="${col=='title_content'}">selected</c:if>>제목+내용</option>
	        <option value="total" <c:if test="${col=='total'}">selected</c:if>>전체</option>
	      	</select>  
    	</div>
	    
	    <div class="form-group">
      		<input type="text" class="form-control" name="word" value="${word }">
    	</div>
   		<button class= 'btn btn-default'>검색</button>
    	<button class="btn-default btn" type="button" onclick="location.href='${root}/imgup/create'">등록</button>
	</form>
${paging } 
</div>

<div class="container">
	<!-- Modal -->
	<div class="modal fade" id="myModal" role="dialog">
		<div class="modal-dialog modal-sm">
	        
			<!-- Modal Content -->		    
	    	<div class="modal-content">
	      		<div class="modal-header">
	          		<button type="button" class="close" data-dismiss="modal">&times;</button>
	          		<h4 class="modal-title">알림메세지!</h4>
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

