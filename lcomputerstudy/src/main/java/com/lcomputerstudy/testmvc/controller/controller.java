package com.lcomputerstudy.testmvc.controller;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lcomputerstudy.testmvc.service.BoardService;
import com.lcomputerstudy.testmvc.service.UserService;
import com.lcomputerstudy.testmvc.vo.User;
import com.lcomputerstudy.testmvc.vo.Board;
import com.lcomputerstudy.testmvc.vo.Pagination;
import com.lcomputerstudy.testmvc.vo.Comment;

@WebServlet("*.do")
public class controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");

		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = requestURI.substring(contextPath.length());
		
		
		String view = null;
		
		HttpSession session = null;
		command = checkSession(request, response, command);
		
		
		
		int page = 1;
		int usercount = 0;
		String idx = null;
		String pw= null;
		String reqPage = null;
		int bIdx = 0;
		Pagination pagination = new Pagination();
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("utf-8");
		
		switch (command) {
			case "/user-list.do":
				reqPage = request.getParameter("page");
				if(reqPage != null) 
					page = Integer.parseInt(reqPage);
				
				
				UserService userService = UserService.getInstance();
				ArrayList<User> list = userService.getUsers(page);
				usercount = userService.getUsersCount();
				pagination = new Pagination(page, usercount);
				
				request.setAttribute("list", list);
				request.setAttribute("usercount", usercount);
				request.setAttribute("pagination", pagination);
			
				view = "user/list";
				break;
			case "/user-insert.do":
				view = "user/insert";
				break;
			case "/user-insert-process.do":
				User user = new User();
				user.setU_id(request.getParameter("id"));
				user.setU_pw(request.getParameter("password"));
				user.setU_name(request.getParameter("name"));
				user.setU_tel(request.getParameter("tel1") + "-" + request.getParameter("tel2") + "-" + request.getParameter("tel3"));
				user.setU_age(request.getParameter("age"));
				
				userService = UserService.getInstance();
				userService.insertUser(user);
						
				view = "user/insert-result";
				break;
				
			case "/user-login.do":
				view ="user/login";
				break;
			case "/user-login-process.do":
				idx = request.getParameter("login_id");
				pw =  request.getParameter("login_password");
				
				userService = UserService.getInstance();
				user = userService.loginUser(idx, pw);
				
				if(user != null) {
					session = request.getSession();
					session.setAttribute("user", user);
					
					view = "user/login-result";
				} else {
					view = "user/login-fail";
				}
				break;
			case "/logout.do":
				session = request.getSession();
				session.invalidate();
				view="user/login";
				break;
			case "/access-denied.do":
				view="user/access-denied";
				break;
			//게시판 리스트	
			case "/board-list2.do":
				
				reqPage = request.getParameter("page");
				if(reqPage != null) 
					page = Integer.parseInt(reqPage);
				
				BoardService boardService = BoardService.getInstance();
				ArrayList<Board> list2 = boardService.getBoards(page);
				usercount = boardService.getBoardCount();
				pagination = new Pagination(page, usercount);
				
				view = "board/list2";
				request.setAttribute("list2", list2);
				request.setAttribute("boardcount", usercount);
				request.setAttribute("pagination", pagination);
				break;
			//게시판 입력	
			case "/board-insert2.do":
				view = "board/insert2";
				break;
			//게시판 입력 실행	
			case "/board-insert2-process.do":
				session = request.getSession();
				user = (User)session.getAttribute("user");
				
				Board board = new Board();
				board.setB_title(request.getParameter("title"));
				board.setB_content(request.getParameter("content"));
				board.setU_idx(user.getU_idx());
				
				boardService = BoardService.getInstance();
				boardService.insertBoard(board); 
						
				view = "board/insert2-result";
				break;
			//게시판 수정	
			case "/board-modify.do":
				int modIdx = Integer.parseInt(request.getParameter("b_idx"));
				boardService = BoardService.getInstance();
				Board mBoard = boardService.getBoardDetail(modIdx);
				
				request.setAttribute("Board", mBoard);
				view = "board/modify";
				break;
			//게시판 수정 실행	
			case "/board-modify-process.do":
				board = new Board();
				board.setB_idx(Integer.parseInt(request.getParameter("b_idx")));
				board.setB_title(request.getParameter("title"));
				board.setB_content(request.getParameter("content"));
				
				boardService = BoardService.getInstance();
				boardService.modifyDetail(board);
				
				view = "board/modify-result";
				break;	
			
			//상세 페이지
			case "/board-detail.do":
				String b_idx = request.getParameter("b_idx");
				int bidx = 0;
				if(b_idx != null) {
					bidx = Integer.parseInt(b_idx);
				}
				boardService = BoardService.getInstance();
				Board dBoard = boardService.getBoardDetail(bidx);
				int b_hit = boardService.upHit(bidx);
				ArrayList<Comment> comList = boardService.getCommentList(bidx);
				
				request.setAttribute("dBoard", dBoard);
				request.setAttribute("b_hit", b_hit);
				request.setAttribute("comment", comList);
				view = "board/detail";
				break;
			//글 삭제	
			case "/board-delete.do":
				int delIdx = Integer.parseInt(request.getParameter("b_idx"));
				boardService = BoardService.getInstance();
				boardService.deleteDetail(delIdx);

				view = "board/delete-result2";
				break;	
			//답글
			case "/board-reply.do":
				String b_idx2 = request.getParameter("b_idx");
				int bidx2 = 0;
				if(b_idx2 != null) {
					bidx2 = Integer.parseInt(b_idx2);
				}
				boardService = BoardService.getInstance();
				Board board2 = boardService.getBoardDetail(bidx2);
				int b_hit2 = boardService.upHit(bidx2);
				
				request.setAttribute("bBoard", board2);
				request.setAttribute("b_hit", b_hit2);
				view = "board/reply";
				break;
				//답글
			case "/board-reply-process.do":
				session = request.getSession();
				user = (User)session.getAttribute("user");
				
				board = new Board();
				board.setB_title(request.getParameter("title"));
				board.setB_content(request.getParameter("content"));
				board.setU_idx(user.getU_idx());
				board.setB_hit(Integer.parseInt(request.getParameter("hit")));
				board.setB_group(Integer.parseInt(request.getParameter("group")));
				board.setB_depth(Integer.parseInt(request.getParameter("depth")));
				board.setB_order(Integer.parseInt(request.getParameter("order")));
					
				boardService = BoardService.getInstance();
				boardService.insertReply(board);
				
				view = "board/reply-result";
				break;
			//댓글 리스트
			case "/comment-list.do":
				int cidx = Integer.parseInt(request.getParameter("idx"));
				Comment comment = new Comment();
				comment.setB_idx(cidx);
				comment.setC_content(request.getParameter("content"));
				
				boardService = BoardService.getInstance();
				boardService.insertComment(comment);
				ArrayList<Comment> cList2 = boardService.getCommentList(cidx);
				
				request.setAttribute("comment", cList2);
				view = "board/comment-list";
				break;
			//댓글 입력
			case "/comment-insert-process.do":
				session = request.getSession();
				user = (User)session.getAttribute("user");
				
				comment = new Comment();
				
				bIdx = Integer.parseInt(request.getParameter("bIdx"));
				
				comment.setC_content(request.getParameter("content"));
				comment.setU_idx(user.getU_idx());
				comment.setB_idx(bIdx);
				
				boardService = BoardService.getInstance();
				boardService.insertComment(comment);
				
				view = "board/comment-result";
				break;	
				
			//댓글 수정
			case "/comment-modify.do":
				int coidx = Integer.parseInt(request.getParameter("c_idx"));
				boardService = BoardService.getInstance();
				ArrayList<Comment> cList3 = boardService.getCommentList(coidx);
				
				request.setAttribute("comment", cList3);
				view = "board/comment-modify";
				break;
			//댓글 수정 실행	
			case "/comment-modify-process.do":
				
				comment =  new Comment();
				comment.setB_idx(Integer.parseInt(request.getParameter("b_idx")));
				comment.setC_idx(Integer.parseInt(request.getParameter("c_idx")));
				comment.setC_content(request.getParameter("c_content"));
				boardService = BoardService.getInstance();
				boardService.commentModify(comment);
				
				view = "board/modify-result";
				break;	
			//댓글 삭제	
			case "/board-comment-delete.do":
				int comidx = Integer.parseInt(request.getParameter("c_idx"));
				boardService = BoardService.getInstance();
				boardService.commentdelete(comidx);

				view = "board/comment-delete-result";
				break;		
			
			}
		
		RequestDispatcher rd = request.getRequestDispatcher(view+".jsp");
		rd.forward(request, response);
			
	}
	String checkSession(HttpServletRequest request, HttpServletResponse response, String command) {
		HttpSession session = request.getSession();
		
		
		String[] authList = {
				"/user-list.do"
				,"/board-detail.do"
				,"/board-modify.do"
				,"/board-modify-process.do"
				,"/logout.do"
				,"/board-list2.do"
				,"/board-insert2.do"
				,"/board-insert2-process.do"
				,"/board-reply.do"
				,"/board-reply-process.do"
				
			};
		
		for (String item : authList) {
			if (item.equals(command)) {
				if (session.getAttribute("user") == null) {
					command = "/access-denied.do";
		
				}
		
			}

		}
		return command;	
	}
}


