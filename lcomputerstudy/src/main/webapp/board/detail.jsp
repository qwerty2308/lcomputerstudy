<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 보기</title>
</head>
<style>
* {
	text-decoration: none;
}

h1, h2 {
	text-align: center;
	height: 50px;
}

table {
	border-collapse: collapse;
	margin: 20px auto;
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

.insert {
		text-align: right;
		margin-right: 300px;
		
	}
	
</style>
<body>
	<h1>게시글 보기</h1>
		<div class="insert"><a href="/lcomputerstudy/board-list2.do">돌아가기</a></div>
	<table>	
		<tr>
			<td>회원번호 번호</td>
			<th>${dBoard.u_idx}</th>
		</tr>
		<tr>
			<td>작성자</td>
			<th>${dBoard.user.u_name}</th>
		</tr>
		<tr>
			<td>작성일</td>
			<th>${dBoard.b_date}</th>
		</tr>
		<tr>
			<td>조회수</td>
			<th>${dBoard.b_hit}</th>
		</tr>
		<tr>
			<td>제목</td>
			<th>${dBoard.b_title}</th>
		</tr>
		<tr>
			<td>내용</td>
			<th>${dBoard.b_content}</th>
		</tr>
		<tr>
			<td><a href="board-modify.do?b_idx=${dBoard.b_idx}">수정</a></td>
			<td><a href="board-delete.do?b_idx=${dBoard.b_idx}">삭제</a></td>
			<td><a href="board-reply.do?b_idx=${dBoard.b_idx}&order=${dBoard.b_order}&$group=${dBoard.b_group}&depth=${dBoard.b_depth}">답글</a>
			</td>
		</tr>
	</table>
</body>
<h2>댓글</h2>
	<form method="post" action="comment-insert-process.do">
		<input type="hidden" name="bIdx" value="${dBoard.b_idx}">
		<div id="commentList">
			<table class="comment">
				<tr>
					<th>작성자</th>	
					<th>작성날짜</th>	
					<th>내용</th>
					<th>댓글관리</th>
				</tr>
				<c:forEach var="co" items="${comment}" varStatus="status">
					<tr>
						<td style="text-align: center;"> ${co.user.u_name}</td>
						<td>${co.c_date}</td>
						<td>${co.c_content}</td>
						<td>
							<a href="comment-modify.do?c_idx=${co.c_idx}">수정</a>
							<a href="comment-delete.do?c_idx=${co.c_idx}">삭제</a>
						</td>
					</tr>
				</c:forEach>
				<tr style="height: 50px;"></tr>
				<tr>
					<td colspan="2">댓글</td>
					<td><input type="text" name="content"size="50"></td>
					<td><input type="submit" value="댓글작성"></td>
				</tr>
			</table>
		</div>
	</form>
</html>