<%@page import="Model.board.Vo.BoardVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../include/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>boardUpdate</title>
<script>
	function chk(){
		var frm = document.frm;
		var inputPw = prompt('수정을 원하면 비밀번호를 입력하세요.','');
		$("#inputPw").val(inputPw);
		frm.submit();
		
	}
</script>
</head>
<body>

<div class="container-fluid">
		<div class="row content">
		<%@include file="../include/menu.jsp"%>
			<div class="col-xs-9 main">
				<form name="frm" method="post" action="${path}/board/pwChkUp.do?num=${param.num}&curPage=${param.curPage}">
		<div>
			<label>이름 </label> <input type="text" name="writer" value="${dto.writer }" readonly="readonly">
		</div>
		<div>
			<label>제목 </label> <input type="text" name="title" value="${dto.title }" >
		</div>
		<div class="boardcontent">
			<textarea rows="20" cols="80" name="content" id="text">${dto.content }</textarea>
		</div>
		<input type="hidden" name="num" value="${param.num }">
		<input type="hidden" name="writer" value="${user.id}">
		<input type="hidden" name="curPage" value="${param.curPage }">
		<input type="hidden" name="inputPw" id="inputPw"> 
		<div class="submit_btn">
			<input type="button" value="수정하기" onclick="chk()">
			<input type="button" value="뒤로" onclick="history.back()">
			
		</div>
	</form>
			</div>
		</div>
	</div>

</body>
</html>