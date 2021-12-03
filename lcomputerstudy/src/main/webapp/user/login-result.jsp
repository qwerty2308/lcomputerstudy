<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인중입니다.</title>
<style>
	body {
		margin: 0;
		padding: 0;
	}
	
	div ul {
		width: 100%;
		text-align: center;
		list-style: none;
		padding: 0;	
	}
	
	div ul li{
		padding: 10px;
		background-color: rgba(75,189,217,0.1);
		border-radius:10px;
		margin: 10px;
		font-weight: 700;
		box-shadow: 2px 3px 3px #bbbbbb; 
	}
	
	div ul li a {
		text-decoration: none;
		color: #333333;
	}
</style>
</head>
<body>
<div>${sessionScope.user.u_name }님 로그인 성공</div>
<div>
	<ul>
		<li><a href="user-list.do">회원 목록</a></li>
		<li><a href="/lcomputerstudy/board-list2.do">글 목록</a></li>
		<li><a href="/lcomputerstudy/board-insert2.do">글 작성</a></li>
		<li><a href="logout.do">로그아웃</a></li>
		
		
	</ul>
</div>
</body>
</html>