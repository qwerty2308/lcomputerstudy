<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
* {
	text-decoration: none;
	margin: auto;
}

h1, h2 {
	text-align: center;
	height: 50px;
}

table {
	border-collapse: collapse;
}

table tr th {
	font-weight: 700;
}

table tr td, table tr th {
	border: 1px solid #818181;
	width: 200px;
	text-align: center;
}

a {
	color: #000;
	font-weight: 700;
	border: none;
	cursor: pointer;
	padding: 10px;
	display: inline-block;
}

p {
	text-align: center;
}

</style>
<table class="comment">
	<tr>
		<th>작성자</th>
		<th>작성날짜</th>
		<th>내용</th>
		<th>댓글관리</th>
	</tr>
	<c:forEach var="co" items = "${comment}" varStatus = "status">
		<tr>
			<td>${co.user.username}</td>
			<td>${co.c_date}</td>
			<td>${co.c_content}</td>
			<td>
				<a href="comment-modify.do?c_idx=${co.c_idx}">수정</a>
				<a href="comment-delete.do?c_idx=${co.c_idx}">삭제</a>
			</td>		
		</tr>
	</c:forEach>
</table>