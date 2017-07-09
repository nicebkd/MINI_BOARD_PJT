<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../include/header.jsp"%>

<!-- mvc 패턴에서는 원래 이렇게 처리하면 안되는데 귀찮아서 처리함. 원래 컨트롤러 거쳐서 가야함 -->
<c:choose>
	<c:when test="${requestScope.result >0}">
		<script>
			alert("가입 성공 축하드립니다.");
			location.href="${path}/home.jsp"
		</script>
	</c:when>
	<c:otherwise>
		<script>
			alert("가입에 실패 하였습니다.");
			history.back();
		</script>
	</c:otherwise>
</c:choose>