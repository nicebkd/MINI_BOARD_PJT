<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="col-xs-3 sidenav">
	<h4><a href="${path }/home">중앙정보 처리 학원</a></h4>
    <c:if test="${user == null }">
    <ul class="nav navbar-nav">
        <li><a href="${path }/member/joinForm.do"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>
        <li><a href="${path }/member/loginForm.do"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
      </ul>
     </c:if>
     
    <c:if test="${user != null }">
   	 <ul class="nav navbar-nav">
        <li><a href="${path }/member/info.do?id=${user.id}">${user.id}(${user.name })</a></li>
        <li><a href="${path }/member/logout.do"><span class="glyphicon glyphicon-log-in"></span> Logout</a></li>
      </ul>
     </c:if>
	<br><br><br><br><br><br>
	
	<ul class="nav nav-pills nav-stacked">
		<li><a href="${path }/board/list.do">JSP</a></li>
		<li><a href="#">HTML/CSS</a></li>
		<li><a href="#">SPRING</a></li>
		<li><a href="#">JAVA</a></li>
	</ul>
	<br>
</div>
