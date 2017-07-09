<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../include/header.jsp"%>
<%@include file="../include/session_check.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">

<style>table {
	border-collapse: collapse;
	width: 100%;
}

th, td {
	padding: 50px;
	text-align: left;
	border-bottom: 1px solid #ddd;
}

.button {
	display: inline-block;
	border-radius: 4px;
	background-color: #f4511e;
	border: none;
	color: #FFFFFF;
	text-align: center;
	font-size: 15px;
	padding: 10px;
	width: 100px;
	transition: all 0.5s;
	cursor: pointer;
	margin: 5px;
	float: right;
}

.button span {
	cursor: pointer;
	display: inline-block;
	position: relative;
	transition: 0.5s;
}

.button span:after {
	content: '\00bb';
	position: absolute;
	opacity: 0;
	top: 0;
	right: -20px;
	transition: 0.5s;
}

.button:hover span {
	padding-right: 25px;
}

.button:hover span:after {
	opacity: 1;
	right: 0;
}

tr:hover {
	background-color: #f5f5f5
}

tr:nth-child(1) {
	background-color: pink
}

.center{
	text-align: center;
}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		$("#writeBtn").click(function() {
			location.href = "${path}/board/writeForm.do";
		});
	});
	
	
	function list(page){
		location.href="${path}/board/list.do?curPage="+page;
	}
		

</script>
</head>

<body>
	<div class="container-fluid">
		<div class="row content">
			<%@include file="../include/menu.jsp"%>
			<div class="col-xs-9 main">
			
				<header>
					<h2>게시판</h2>
				</header>
				<br>
				<form name="form1" method="post" action="${path }/board/list.do" >
<select name="search_option">
	<option value="all"
	<c:out
	value="${map.search_option=='all'?'selected':''}"/>>전체</option>
	<option value="writer"
	<c:out
	value="${map.search_option=='writer'?'selected':''}"/>>이름</option>
	<option value="title" 
	<c:out
	value="${map.search_option=='title'?'selected':''}"/>>제목</option>
	<option value="content"
	<c:out
	value="${map.search_option=='content'?'selected':''}"/>>내용</option>
</select>
<input name="keyword" value="${map.keyword }">
<input type="submit" value="검색">
<c:if test="${sessionScope.userid != null }">
<button type="button" id="btnWrite">글쓰기</button>
</c:if>
</form>
				<div class="col-xs-9 main">
					<span style="float: left;">총 게시물 수 : ${map.count }</span>
					
					<button class="button" id="writeBtn">
						<span>글쓰기</span>
					</button>
					
					<table class="table">
						<tr>
							<th>번호</th>
							<th>제목</th>
							<th>글쓴이</th>
							<th>조회수</th>
							<th>등록일</th>
							<th>첨부 파일</th>
						</tr>
						
						<c:if test="${requestScope.map.list != null }">
							<c:forEach var="row" items="${requestScope.map.list }">
								<tr>
									<td>${row.num }</td>
									<td><a href="${path}/board/view.do?num=${row.num}&curPage=${map.pager.curPage}">${row.title }</a></td>
									<td>${row.writer }</td>
									<td>${row.readCount }</td>
									<td>${row.reg_date }</td>
									<td><a href="${path }/board/download.do?num=${row.num }">${row.original_file_name }</a></td>
								</tr>
							</c:forEach>
						</c:if>
						
	<tr>
		<td colspan="6" class="center">
	<c:if test="${map.pager.curBlock >1 }">
		<a href="javascript:list('1')">[처음]</a>
	</c:if>
	<c:if test="${map.pager.curBlock >1 }">
		<a href="javascript:list('${map.pager.prevPage}')">[이전]</a>
	</c:if>
		<c:forEach var="num" begin="${map.pager.blockBegin }"
		end="${map.pager.blockEnd}">
		
		<c:choose>
			<c:when test="${num == map.pager.curPage }">
			<!-- 현재 페이지면 하이퍼링크 제거 -->
			<span style="color: red">${num}</span>&nbsp;
			</c:when>
			<c:otherwise>
			<a href="javascript:list('${num}')">${num}</a>&nbsp;
			</c:otherwise>
		</c:choose>
		
		</c:forEach>
		<c:if test="${map.pager.curBlock <=map.pager.totBlock }">
			<a href="javascript:list('${map.pager.nextPage}')">[다음]</a>
		</c:if>
		<c:if test="${map.pager.curPage <=map.pager.totPage}">
			<a href="javascript:list('${map.pager.totPage }')">[끝]</a>
		</c:if>
		</td>
	</tr>
						
	
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>