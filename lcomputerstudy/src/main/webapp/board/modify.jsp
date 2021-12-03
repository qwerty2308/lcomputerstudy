<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 수정</title>
</head>
<body>
	<h1>글 수정</h1>
		<form action="board-modify-process.do?b_idx${Board.b_idx}" name ="board" method="post">
			<input type="hidden" name="b_idx" value="${Board.b_idx}">
			<p>작성자 : ${sessionScope.user.u_name}</p>
			<p>작성일 : ${Board.b_date}</p>
			<p>조회수 : ${Board.b_hit}</p>
			<p>제목 : <input type="text" name="title" value="${Board.b_title}"></p>
			<p>내용 : <textarea name="content" cols="50" rows="10">${Board.b_content}</textarea></p>
			<p><input type="submit" value="수정완료"></p>
		</form>
</body>
</html>