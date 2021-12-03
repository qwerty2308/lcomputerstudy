<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 목록</title>
</head>
<style>
	h1{text-align: center;}
	table{ 
		border-collapse:collapse;
		margin: 20px auto;
	}
	table tr th{
		font-weight:700;
		background-color: lightgray;
	}
	table tr td, table tr th{
		border: 1px solid #818181;
		padding: 5px 5px;
		width: 200px;
		text-align: center;
	}
	a{
		text-decoration: none;
		color: blue;
		font-weight: 700;
	}
	ul {
		width:400px;
		height:50px;
		margin:10px auto;
	}
	li {
		list-style:none;
		width:50px;
		line-height:50px;
		border:1px solid #ededed;
		float:left;
		text-align:center;
		margin:0 5px;
		border-radius:5px;
	}
	
	.insert {
		text-align: right;
		margin-right: 300px;
		
	}
	
	.td-align {
		text-align:left; 
		padding-left:10px;
	}
		
</style>
<body>
<h1>게시판 목록</h1>
<div class="insert"><a href="board-insert2.do">글쓰기</a></div>
<table>
	<tr>
		<td colspan="6">전체 글 수 : ${boardcount }</td>
	</tr>
	<tr>
		<th>글번호</th>
		<th>제목</th>
		<th>내용</th>
		<th>작성자</th>
		<th>작성일시</th>
		<th>조회수</th>
	</tr>
	<c:forEach items="${list2}" var="item" varStatus="status">
		<tr>
			<td>${item.b_rownum}</td>
			<td class="td-align"><a href="board-detail.do?b_idx=${item.b_idx}">${item.b_title}</a></td>
			
			<td>${item.b_content}</td>
			<td>${item.user.u_name}</td>
			<td>${item.b_date}</td>
			<td>${item.b_hit}</td>
		</tr>
	</c:forEach>
</table>
<div>
		<ul>
			 <c:choose>
				<c:when test="${ pagination.prevPage > 0}">
					<li>
						<a href="board-list2.do?page=${ pagination.prevPage }">◀</a>
					</li>
				</c:when>
			</c:choose> 
			<c:forEach var="i" begin="${ pagination.startPage}" end="${ pagination.endPage}" step="1">
				
					<c:choose>
						<c:when test="${ pagination.page == i }">
							
							<li style="background-color:#ededed;">
								<span>${i}</span>
							</li>
						</c:when>
						<c:when test="${ pagination.page != i }">
							<li>
								<a href="board-list2.do?page=${i}">${i}</a>
							</li>
						</c:when>
					</c:choose>
			</c:forEach>
			 <c:choose>
				<c:when test="${ pagination.nextPage <= pagination.lastPage }">
					<li style="">
						<a href="board-list2.do?page=${ pagination.nextPage}">▶</a>
					</li>
				</c:when>
			</c:choose> 
		</ul>
	</div>
</body>
</html>