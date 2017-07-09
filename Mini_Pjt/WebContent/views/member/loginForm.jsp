<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../include/header.jsp" %>
<style type="text/css">
.input-group{
		width: 550px;
	}

</style>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>


<div class="container-fluid">
		<div class="row content">
		<%@include file="/views/include/menu.jsp"%>
			<div class="col-xs-9 main">
			<form action="${path }/member/loginPro.do" method="post" onsubmit="return chk()">
		<div class="myDiv">
			<div class="input-group">
		      <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
		      <input id="id" type="text" class="form-control" name="id" placeholder="아이디" > 
		    </div><br>
		
		    <div class="input-group">
		      <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
		      <input id="pw" type="password" class="form-control" name="pw" placeholder="패스워드">
		    </div>
		</div>
		<input type="submit" value="로그인">
	</form>
			</div>
		</div>
	</div> 
	
</body>
</html>