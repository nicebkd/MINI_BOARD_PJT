<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<%@include file="../views/include/header.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">

    .row.content {height: 900px}
    
    .sidenav {
      text-align : center;
      display : inline-block;
      background-color: #f1f1f1;
      height: 100%;
      padding: 35px;
    }
</style>
<%@ include file="views/include/header.jsp" %>
</head>
<body>
	<div class="container-fluid">
		<div class="row content">
		<%@include file="views/include/menu.jsp"%>
			<div class="col-xs-9 main">
			메인
			</div>
		</div>
	</div>
</body>
</html>