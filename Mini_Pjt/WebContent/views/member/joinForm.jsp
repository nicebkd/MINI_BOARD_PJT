<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../include/header.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">

	.input-group{
		width: 550px;
	}
		
	/* div{
		margin: 0 auto;
	} */
	
	.myDiv div{
		margin: 0 auto;
	}
	
	.text-center{
		text-align: center;
	}
	
	article{
		margin: 0 auto;
	}
</style>
<script>
	$(document).ready(function() {
		
		var id = $("#id");
		var pw = $("#pw");
		var pw2 = $("#pw2");
		var name = $("#name");
		var email = $("#email");
		
		var re_id = /^[A-Za-z0-9+]{4,16}$/; // 아이디 검사식
		var re_pw = /^(?=.*[a-zA-Z])((?=.*\d)|(?=.*\W)).{4,16}$/; // 비밀번호 검사식
		
		var re_email = /^([\w\.-]+)@([a-z\d\.-]+)\.([a-z\.]{2,6})$/; // 이메일 검사식
		var re_name = /^[0-9a-zA-Z가-힣]{2,12}$/; //이름 유효성
		
		var id_check;
		

		
		
		$("#join_Btn").click(function() {
			if(!re_id.test(id.val())){
				alert("아이디 형식이 틀립니다.");
				id.focus();
				return false;
			}else if(id_check ==false){
				alert("이미 존재하는 아이디 입니다.");
				id.focus();
				return false;
			}else if(!re_name.test(name.val())){
				alert("닉네임 형식에 맞지 않습니다.");
				name.focus();
				return false;
			}else if(!re_pw.test(pw.val())){
				alert("비밀번호 형식이 다릅니다.");
				pw.focus();
				return false;
			}else if(pw.val()!=pw2.val()){
				alert("두 비밀번호가 일치 하지 않습니다.");
				pw.val("");
				pw2.val("");
				pw.focus();
				return false;
			}
		
			$("#Join").submit();
		});
		
		$("#id").blur(function() {
			 var id=$("#id").val();
			 var param="id="+id;
			 
			 if(!re_id.test(id)){
				 return false;
			 }
			
			 $.ajax({
				 	type :"post",
					url : "${path}/member/idcheck.do",
					data : param,
					success : function(data) {
						if(data=="success"){
							alert("사용 가능한 아이디 입니다.");
							id_check=true;
						}else if(data=="fail"){
							alert("이미 존재 하는 아이디 입니다.");
							id_check=false;
						}
					}
				 });
		});
						
	});
</script>
</head>

<body>

<section class="container-fluid">
<div class="row content">
<%@include file="../include/menu.jsp" %>
	<article class="col-xs-9 text-center">
	<header><h2>회원 가입</h2></header>
	<form action="${path}/member/join.do" method="post" name="Join" onsubmit="return chk()">
	<div class="myDiv">
		<div class="input-group">
	      <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
	      <input id="id" type="text" class="form-control" name="id" placeholder="아이디" > 
	    </div>
	    	<span>아이디 형식은 영문 숫자 4~16글자</span>
	    <br>
	   
	    <div class="input-group">
	      <span class="input-group-addon"><i class="glyphicon glyphicon-pencil"></i></span>
	      <input id="name" type="text" class="form-control" name="name" placeholder="닉네임" >
	    </div>
	    <span>닉네임 한글,영어,숫자 2~12 형식은 입니다.</span>
	    <br>
		
	    <div class="input-group">
	      <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
	      <input id="pw" type="password" class="form-control" name="pw" placeholder="패스워드">
	    </div>
	    <span>비밀번호 형식은4~16,숫자 혹은 특수 문자 반드시 포함</span>
	    <br>
	    
	    <div class="input-group">
	      <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
	      <input id="pw2" type="password" class="form-control" name="pw2" placeholder="패스워드 확인">
	    </div><br>
	    
	    <div class="input-group">
	      <span class="input-group-addon"><i class="glyphicon glyphicon-envelope"></i></span>
	      <input id="email" type="email" class="form-control" name="email" placeholder="이메일">
	    </div>
	</div>
    <br>

    <div>
    	<button class="btn btn-danger" type="submit" id="join_Btn">회원가입</button>
    </div> 
  </form>
  
	
	</article>

	</div>
</section>

</body>
</html>