<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>답글 작성</title>
<style>
	h2 {
		text-align: center;
		
		}
	table {
		
		margin: auto;
	}	
	
	input {
		align: center;
		
		}
		
	div {
		text-align: center;
	}
	
</style>
</head>
<body>
	<div class="writeForm">
		<h2>답글 작성</h2>
		<form action="board-reply-process.do" method="post">
			<table>
				<tr>
					<td><input type="hidden" name="idx" value="${bBoard.b_idx}"></td>
					<td><input type="hidden" name="writer" value="${sessionScope.user.u_name}"></td>
					<td><input type="hidden" name="hit" value="${bBoard.b_hit}"></td>
					<td><input type="hidden" name="group" value="${bBoard.b_group}"></td>
					<td><input type="hidden" name="depth" value="${bBoard.b_depth}"></td>
					<td><input type="hidden" name="order" value="${bBoard.b_order}"></td>
				</tr>
				<tr>
					<td>작성자 : </td>
					<td>${sessionScope.user.u_name}</td>
				</tr>
				<tr>
					<td>제목 : </td>
					<td><input type="text" name="title" size="60"></td>
				</tr>
				<tr>
					<td>내용 : </td>
					<td><textarea rows="20" cols="60" name="content"></textarea></td>
				</tr>
			</table>
			<br/>
			<input type="submit" value="답글작성">
			<input type="button" value="취소하기" onclick="/board-detail.do?${bBoard.b_idx}">
		</form>
	</div>
</body>
</html>