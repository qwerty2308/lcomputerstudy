<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>     
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 작성</title>
</head>
<body>
<h2>글 작성</h2>
<form action="board-insert2-process.do" name= "board" method="post" >
	<p>제목 : <input type="text" name ="title"></p>
	<p>작성자 : ${sessionScope.user.u_name}</p>
	<p>내용 : <textarea name="content" cols="50" rows="10"></textarea></p>
	
	<p> <input type="submit" value="작성하기"></p>
</form>
</body>
</html>