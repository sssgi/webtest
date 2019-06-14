<%@ page contentType="text/html; charset=UTF-8" %> 
<%@ include file = "/ssi/ssi_bbs.jsp" %>

<% 
// 	List<BbsDTO> list = (List<BbsDTO>)request.getAttribute("list");
// 	String paging = (String)request.getAttribute("paging");
// 	String col = (String)request.getAttribute("col");
// 	String word = (String)request.getAttribute("word");
// 	int nowPage = (Integer)request.getAttribute("nowPage");
%>

<!DOCTYPE html> 
<html> 
<head>
  <title>Bootstrap Example</title>
  <meta charset="utf-8">

  <script type="text/javascript">
  <!--JSP인자를 통해 Javascript의 함수 호출-->
	 function read(bbsno){	
	 	var url = "read"; //content의 내용을 보여줄 페이지의 주소
	 	url += "?bbsno="+bbsno; //bbsno는 jsp메소드에 의해 호출되어서 사용되는 javascript변수
	 	
	 	//javascript변수 :bbsno,url  <-- 선언된 변수 혹은 메소드에 의해 parameter로 사용되는 변수 
	 	//jsp변수:col,word,nowPage  <--자바스크립트 안에서 사용하려면 스크립트릿으로 감싸줘야 함.
	 	url += "&col=${col}";
	 	url += "&word=${word}";
	 	url += "&nowPage=${nowPage}";
	 	
	 	location.href=url; //내장객체 location를 통해 href호출. -->url의 페이지를 찾아가라는 의미.
	 	
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
  
</head>
<body>


<br><br>

<div class="container">
<h2>게시판 목록</h2>
<br>
<form class="form-inline" action="./list">
  	<div class="form-group">
    	<select class="form-control" name="col">
      		<option value="wname" <c:if test="${col=='wname'}">selected</c:if>>성명</option>
      <option value="title"
      <c:if test="${col=='title'}">selected</c:if>
      >제목</option>
      <option value="content"
      <c:if test="${col=='content'}">selected</c:if>
      >내용</option>
      <option value="title_content"
  	  <c:if test="${col=='title_content'}">selected</c:if>
      >제목+내용</option>
      <option value="total"
      <c:if test="${col=='total'}">selected</c:if>
      >전체출력</option>   
    </select> 
  </div>
  
  <div class="form-group">
    <input type="text" class="form-control" placeholder="Enter검색어" name="word" value="${word }">
  </div>
  
  <button type="submit" class="btn btn-default">검색</button>
  <button type="button" class="btn btn-default" onclick="location.href='./create'">등록</button>
</form>

  <table class= "table table-striped">
    <thead>
      <tr>
        <th>번호</th>
        <th>제목</th>
        <th>작성자</th>
        <th>grpno</th>
        <th>indent</th>
        <th>ansnum</th>
      </tr>
    </thead>
  	<tbody>
  	 <c:choose>
  	 	<c:when test="${empty list}">
  	 		<tr><td colspan="4">등록된 게시글이 없습니다.</td></tr>
  	 	</c:when>	
		<c:otherwise>
		<c:forEach var="dto" items="${list }">
			<tr>
        		<td>${dto.bbsno}</td>
        		<td>
        		<c:forEach begin="1" end="${dto.indent}">
        			&nbsp;&nbsp;
        		</c:forEach>
        		<c:if test="${dto.indent>0}">
        			<img src='${root}/images/re.jpg'>
        		</c:if>
        		<c:set var="rcount" value="${util:rcount(dto.bbsno,rinter)}"/>        
        		<a href="javascript:read('${dto.bbsno}')">${dto.title }</a>
        		<c:if test="${rcount>0 }">
            		<span class="badge">${rcount}</span>
      			</c:if>
        		<c:if test="${util:newImg(dto.wdate)}">
               		<img src = "${root }/images/new.gif">
             	</c:if>
        		</td>
       			<td>${dto.wname }</td>
        		<td>${dto.grpno }</td>
        		<td>${dto.indent }</td>
        		<td>${dto.ansnum }</td>
      		</tr>
		 </c:forEach>	
		</c:otherwise>  	 	 
  	  </c:choose>
        
  	</tbody>
  </table>

  <div>
    ${paging}
  </div>

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

