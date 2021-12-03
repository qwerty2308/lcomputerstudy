package com.lcomputerstudy.testmvc.service;

import java.util.ArrayList;
import com.lcomputerstudy.testmvc.dao.BoardDAO;
import com.lcomputerstudy.testmvc.vo.Board;
import com.lcomputerstudy.testmvc.vo.Comment;



public class BoardService {
	private static BoardService service = null;
	private static BoardDAO bdao = null;
	
	private BoardService() {	
		
	}
	
	public static BoardService getInstance() {
		if(service == null) {
			service = new BoardService();
			bdao = BoardDAO.getInstance();
		}
		
		return service;
	}
	public ArrayList<Board> getBoards(int page){
		return bdao.getBoards(page);
	}
	
	public void insertBoard(Board board) {
		bdao.insertBoard(board);
	}
	public void modifyDetail(Board board) {
		bdao.modifyDetail(board);
	}
	public Board getBoardDetail(int idx) {
		bdao.upHit(idx);
		bdao.getCommentList(idx);
		return bdao.getBoardDetail(idx);
	}
	public void deleteDetail(int idx) {
		bdao.deleteDetail(idx);
	} 
	
	public int getBoardCount() {
		return bdao.getBoardCount();
	}
	
	public int upHit(int idx) {
		return bdao.upHit(idx);
	}

	public void insertReply(Board board) {
		bdao.insertReply(board);
		
	}
	
	public ArrayList<Comment> getCommentList(int idx) {
		return  bdao.getCommentList(idx);
	}
	
	public void insertComment(Comment comment) {
		bdao.insertComment(comment);
		
	}
	public void commentModify(Comment comment) {
		bdao.commentModify(comment);
	}

	public void commentdelete(int idx) {
		bdao.commentdelete(idx);
	}

}
