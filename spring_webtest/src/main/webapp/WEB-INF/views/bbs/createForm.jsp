<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/ssi/ssi_bbs.jsp"%>

<!DOCTYPE html>
<html>
<head>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/ckeditor/ckeditor.js"></script>
<script type="text/JavaScript">
	window.onload = function() {
		CKEDITOR.replace('content'); // <TEXTAREA>태그 id 값
	};
</script>
</head>
<body>
	<br>
	<br>

	<div class="container">
		<h1 class="col-sm-offset-2 col-sm-10">게시판 생성</h1>
		<form class="form-horizontal" action="create" method="post">
			<div class="form-group">
				<label class="control-label col-sm-2" for="wname">작성자</label>
				<div class="col-sm-8">
					<input type="text" name="wname" id="wname" class="form-control">
				</div>
			</div>

			<div class="form-group">
				<label class="control-label col-sm-2" for="title">제목</label>
				<div class="col-sm-8">
					<input type="text" name="title" id="title" class="form-control">
				</div>
			</div>

			<div class="form-group">
				<label class="control-label col-sm-2" for="content">내용</label>
				<div class="col-sm-8">
					<textarea class="form-control" id="content" name="content"
						rows="10"></textarea>
				</div>
			</div>

			<div class="form-group">
				<label class="control-label col-sm-2" for="passwd">비밀번호</label>
				<div class="col-sm-8">
					<input type="password" name="passwd" id="passwd"
						class="form-control">
				</div>
			</div>


			<div class="form-group">
				<div class="col-sm-offset-5 col-sm-10">
					<button type="submit" class="btn">등록</button>
					<button type="reset" class="btn">취소</button>
				</div>
			</div>

		</form>

	</div>
</body>
</html>

