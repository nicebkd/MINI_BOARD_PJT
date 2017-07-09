<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../include/header.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script>

$(document).ready(function() {
	$("#replySubmit").click(function() {
		var bno = ${param.bno};
 		var rno = $("#rno").val();
 		var ref = $("#ref").val();
 		var re_step = $("#re_step").val();
 		var re_level = $("#re_level").val();
		var replytext =$("#replytext2").val();
		
		var param ="bno="+bno+"&replytext="+replytext+"&rno="+rno+"&ref="+ref+"&re_step="+re_step+"&re_level="+re_level;
		$.ajax({
			type :"post",
			url :"${path}/reply/insert.do",
			data : param,
			success : function(data) {
				alert("댓글이 등록 되었습니다.");
				parent.listReply();
			}
		}); 
		
		
		
	});
});

function replyDel(rno) {
	$.ajax({
		type:"get",
		url : "${path}/reply/delete.do?rno="+rno,
		success : function(data) {
			if(data==1){
				alert("삭제가 완료");
				parent.listReply();
			}else{
				alert("삭제 실패");
			}
		}
	});
}

function show(rno,ref,re_step,re_level) {
	var rno = $("#rno").val(rno);
	var ref = $("#ref").val(ref);
	var re_step = $("#re_step").val(re_step);
	var re_level = $("#re_level").val(re_level);
	
	 $("#myModal").modal('show');
}

</script>
</head>
<body>
<c:forEach var="row" items="${list}">
<div>
					<c:if test="${row.re_level>0 }"><!-- level이 0보다 크면 답변글 -->
						<img src='../resources/images/level.gif'
							width='${row.re_level*10 }' height='5'> <!-- 답변글의 구분을 10칸씩 표시 -->
						<img src='../resources/images/re.gif'><!-- 답변 표시 -->
					</c:if>
작성자 : ${row.replyer }(<fmt:formatDate value="${row.regdate }" pattern="yyyy-MM-dd HH:mm:ss"/>)
<span><button type="button" onclick="show('${row.rno}','${row.ref}','${row.re_step}','${row.re_level }')">R</button><c:if test="${user.id==row.replyer}"><button onclick="replyDel('${row.rno}')">X</button></c:if> </span>
</div>
<div>
내용 : ${row.replytext}<br>
</div>

<!-- Modal -->
  <div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog modal-sm">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Reply</h4>
        </div>
        <br>
        <div class="modal-body">
        <textarea rows="5" cols=35 id="replytext2"></textarea>
        <input type="hidden" id="rno" name="rno">
        <input type="hidden" id="ref" name="ref">
        <input type="hidden" id="re_step" name="re_step">
        <input type="hidden" id="re_level" name="re_level">
        
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal" id="replySubmit">등록</button>
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>
</c:forEach>


</body>
</html>