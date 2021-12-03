<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>댓글 수정</title>
</head>
<body>
	<h1>댓글 수정</h1>
		<form action="comment-modify-process.do?c_idx${comment.c_idx}" name ="comment" method="post">
			<input type="hidden" name="c_idx" value="${comment.c_idx}">
			<p>작성자 : ${sessionScope.user.u_name}</p>
			<p>작성일 : ${comment.b_date}</p>
			<p>내용 : <textarea name="content" cols="50" rows="10">${comment.b_content}</textarea></p>
			<p><input type="submit" value="수정완료"></p>
		</form>
</body>
</html>