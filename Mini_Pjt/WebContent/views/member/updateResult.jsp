<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../include/header.jsp"%>

<!-- mvc 패턴에서는 원래 이렇게 처리하면 안되는데 귀찮아서 처리함. 원래 컨트롤러 거쳐서 가야함 -->
<c:choose>
	<c:when test="${requestScope.result >0}">
		<script>
			alert("개인정보가 수정되어 로그아웃 되었습니다."+"\n"+"다시 로그인 해 주세요.");
			
		 location.href="${path }/member/logout.do";
			 
		</script>
	</c:when>
	<c:otherwise>
		<script>
			alert("수정 실패.");
			history.back();
		</script>
	</c:otherwise>
</c:choose>