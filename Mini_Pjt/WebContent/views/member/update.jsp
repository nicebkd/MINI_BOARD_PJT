<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
function chk(){
	if(!Join.pw.value){
		alert("패스워드를 입력하세요");
		Join.pw.focus();
		return false;
	}
	if(!Join.pw2.value){
		alert("패스워드 확인을 입력하세요");
		Join.pw2.focus();
		return false;
	}
	if(Join.pw.value!=Join.pw2.value){
		Join.pw.value="";
		Join.pw2.value="";
		Join.pw.focus();
		alert("패스워드가 서로 다릅니다.")
		return false;
	}

	return true;
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회원정보수정</title>
<%@include file="../include/header.jsp"%>
</head>
<body>
	<div class="container-fluid">
		<div class="row content">
		<%@include file="../include/menu.jsp"%>
			<div class="col-xs-9 main">
			<form action="${path}/member/updateForm.do" method="post" name="Join" onsubmit="return chk()">
	<div class="myDiv">
		<div class="input-group">
	      <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
	   아이디 :<input id="id" type="text" class="form-control" name="id" placeholder="아이디" value="${info.id }" readonly> 
	    </div><br>
	   
	    <div class="input-group">
	      <span class="input-group-addon"><i class="glyphicon glyphicon-pencil"></i></span>
	  이름 :<input id="name" type="text" class="form-control" name="name" placeholder="닉네임" value="${info.name }" required>
	    </div><br>
	
	    <div class="input-group">
	      <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
	  비밀번호 :<input id="pw" type="password" class="form-control" name="pw" placeholder="패스워드" value="${info.password }" required>
	    </div><br>
	    
	    <div class="input-group">
	      <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
	  비밀번호 확인:<input id="pw2" type="password" class="form-control" name="pw2" placeholder="패스워드 확인" value="${info.password}" required>
	    </div><br>
	    
	    <div class="input-group">
	      <span class="input-group-addon"><i class="glyphicon glyphicon-envelope"></i></span>
	  이메일 : <input id="email" type="email" class="form-control" name="email" value="${info.email }" placeholder="이메일">
	    </div>
	</div>
    <br>

    <div>
    	<button class="btn btn-danger" type="submit" id="updateForm_Btn">수정 완료</button>
    </div> 
  </form>
  
	
			</div>
		</div>
	</div> `
</body>
</html>