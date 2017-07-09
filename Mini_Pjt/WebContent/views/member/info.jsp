<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<%@ include file="../include/header.jsp" %>
</head>
<body>
<center><h2>회원 정보</h2></center>
	<div class="container-fluid">
		<div class="row content">
		<%@include file="../include/menu.jsp"%>
			<div class="col-xs-9 main">
	 <form action="${path}/member/update.do" method="post" name="Join" onsubmit="return chk()">
	<div class="myDiv">
		<div class="input-group">
	      <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
	 아이디:  <input id="id" type="text" class="form-control" name="id" placeholder="아이디" value="${info.id}" readonly> 
	    </div><br>
	   
	    <div class="input-group">
	      <span class="input-group-addon"><i class="glyphicon glyphicon-pencil"></i></span>
	 이름:     <input id="name" type="text" class="form-control" name="name" placeholder="이름" value="${info.name}" readonly>
	    </div><br>
	    
	    <div class="input-group">
	      <span class="input-group-addon"><i class="glyphicon glyphicon-envelope"></i></span>
	 이메일:     <input id="email" type="email" class="form-control" name="email" placeholder="이메일" value="${info.email}" readonly>
	    </div>
	</div>
    <br>

    <div>
    <center>
    	<button class="btn btn-danger" type="submit" id="update_Btn">회원정보 수정</button>
    </center>
    </div>
     
  </form> 

			</div>
		</div>
	</div> `
</body>
</html>