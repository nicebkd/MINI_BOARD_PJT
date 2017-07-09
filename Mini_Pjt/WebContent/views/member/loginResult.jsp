<%@page import="Model.member.Vo.MemberVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<!-- mvc 패턴에서는 원래 이렇게 처리하면 안되는데 귀찮아서 처리함. 원래 컨트롤러 거쳐서 가야함 -->
<%
	MemberVo dto=(MemberVo) request.getAttribute("user");
%>
<c:choose>
	<c:when test="${user !=null}">
		<script>
		<%
			session.setAttribute("user",dto);
		%>
			location.href="${path}/home.jsp";
		</script>
	</c:when>
	<c:otherwise>
		<script>
			alert("아이디 및 패스워드가 일치 하지 않습니다.");
			history.back();
		</script>
	</c:otherwise>
</c:choose>