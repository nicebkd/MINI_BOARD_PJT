<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../include/header.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script>
$(document).ready(function() {
	
	listReply();

	$("#btnReply").click(function() {
		var bno = ${dto.num};
		var replytext = $("#replytext").val();
		var param ="replytext="+replytext+"&bno="+bno
		$.ajax({
			type : "post",
			url :"${path}/reply/insert.do",
			data : param,
			success : function(data) {
				alert("댓글이 등록 되었습니다.");
				listReply();
			}
		});
	});
	
});

function listReply() {
	$.ajax({
		type :"get",
		url :"${path}/reply/list.do?bno=${dto.num}",
		success : function (data) {
			$("#listReply").html(data);
		}
	});
}
function delChk(){
	var frm = document.frm;
	var inputPw = prompt('삭제를 원하면 비밀번호를 입력하세요.','');
	if(inputPw!=null){
		var test = location.href="${path}/board/pwChkDel.do?curPage=${param.curPage}&num=${dto.num}&writer=${user.id}&password="+inputPw;
	}else{
		return;
	}
}
function upChk(){
	var test = location.href="${path}/board/boardUp.do?curPage=${param.curPage}&num=${dto.num}";
}
</script>
</head>
<body>
	<div class="container-fluid">
		<div class="row content">
			<%@include file="../include/menu.jsp"%>
			<div class="col-xs-9 main">
				<header>
					<h2>글쓰기</h2>
				</header>
				<hr>
				<form action="${path}/board/write.do" method="post">
					<div>
						<label>파일 첨부 </label><a href="${path }/board/download.do?num=${dto.num }">${dto.original_file_name }</a>
					</div>
					<div>
						<label>이름 </label> <input type="text" name="writer"
							value="${dto.writer }" readonly="readonly">
					</div>
					<div>
						<label>제목 </label> <input type="text" name="title"
							value="${dto.title }" readonly="readonly">
					</div>
					<div class="boardcontent">
						<textarea rows="20" cols="80" name="content" readonly="readonly">${dto.content }</textarea>
					</div>
					<input type="hidden" name="num" value="${dto.num}">
					<input type="hidden" name="writer" value="${user.id}">
					<input type="hidden" name="curPage" value="${param.curPage}">
					<div class="submit_btn">
						<input type="button" value="수정" onclick="upChk()"> 
						<input type="button" value="삭제" onclick="delChk()"> 
						<input type="button" value="목록"
							onclick="location.href='${path}/board/list.do?curPage=${param.curPage }'">
					</div>
				</form>

				<div style="width: 700px; text-align: center;">
					<br>
					<textarea rows="5" cols="80" placeholder="댓글을 작성하세요."
						id="replytext"></textarea><br>
					<button type="button" id="btnReply">댓글쓰기</button>
				</div>
				
				<div id="listReply"></div>
				
			</div>
		</div>
	</div>
</body>
</html>