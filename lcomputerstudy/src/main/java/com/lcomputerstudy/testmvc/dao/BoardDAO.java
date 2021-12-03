package com.lcomputerstudy.testmvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


import com.lcomputerstudy.testmvc.database.DBConnection;
import com.lcomputerstudy.testmvc.vo.Board;
import com.lcomputerstudy.testmvc.vo.Comment;
import com.lcomputerstudy.testmvc.vo.User;


public class BoardDAO {
	
	private static BoardDAO bdao = null;
	
	private BoardDAO() {
		
	}
	
	public static BoardDAO getInstance() {
		if(bdao == null) {
			bdao = new BoardDAO();
		}
		return bdao;
	}
	
	public ArrayList<Board> getBoards(int page){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Board> list2 = null;
		String sql = null;
		int pageNum = (page-1)*5;
		
		try {
			conn = DBConnection.getConnection();
			sql = new StringBuilder()
					.append("select		@ROWNUM := @ROWNUM -1 as ROWNUM, concat(repeat('ㄴ', b_depth), b_title) b_title,\n")
					.append("			ta.*\n")
					.append("from(		select ta.*,\n")
					.append("              tb.u_name\n")
					.append("from           board ta\n")
					.append("LEFT JOIN	user tb ON ta.u_idx = tb.u_idx) ta,")
					.append("			(select @rownum := (select COUNT(*)-?+1 from board ta)) tb\n")
					.append("ORDER BY 	b_group DESC, b_order asc\n")
					.append("LIMIT			?, 5\n")
					.toString();
			
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, pageNum);
				pstmt.setInt(2, pageNum);
        		rs = pstmt.executeQuery();
        		list2 = new ArrayList<Board>();
			
			while(rs.next()) {
				Board board = new Board();
				board.setB_rownum(rs.getInt("ROWNUM"));
				board.setB_idx(rs.getInt("b_idx"));
				board.setB_title(rs.getString("b_title"));
				board.setB_content(rs.getString("b_content"));
				board.setB_date(rs.getString("b_date"));
				board.setU_idx(rs.getInt("u_idx"));
				board.setB_hit(rs.getInt("b_hit"));
				board.setB_group(rs.getInt("b_group"));
				board.setB_depth(rs.getInt("b_depth"));
				board.setB_order(rs.getInt("b_order"));
				User user = new User(); 
				user.setU_name(rs.getString("u_name"));
				board.setUser(user);
				list2.add(board);
			}
			
		}catch (Exception ex) {
			System.out.println("SQLException: "+ex.getMessage());
		}finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		 return list2;
	}
	//글 작성
	public void insertBoard(Board board) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			
			conn = DBConnection.getConnection();
			sql = "insert into board(b_title, b_content, b_date, b_hit, u_idx, b_group, b_depth, b_order) values(?, ?, NOW(), 0, ?, ?, 0, 1)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, board.getB_title());
			pstmt.setString(2, board.getB_content());
			pstmt.setInt(3, board.getU_idx());
			pstmt.setInt(4, board.getB_group());
			pstmt.executeUpdate();
			pstmt.close();
			
			sql = "update board set b_group = last_insert_id() where b_idx = last_insert_id()";
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
			
			
			
		} catch (Exception ex) {
			System.out.println("SQLException : "+ex.getMessage());
		}finally {
			try {
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} 
	}
	//전체 수
	public int getBoardCount() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;

		try {
			conn = DBConnection.getConnection();
			String sql = "SELECT COUNT(*) count FROM board ";
	       	pstmt = conn.prepareStatement(sql);
	        rs = pstmt.executeQuery();
	        
	        while(rs.next()){     
	        	count = rs.getInt("count");
	        }
		} catch (Exception ex) {
			System.out.println("SQLException: "+ex.getMessage());
		} finally {
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}
	//상세
	public Board getBoardDetail(int idx) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Board board = null;
		String sql = null;
		try {
			conn = DBConnection.getConnection();
			sql = "select * from board ta join user tb on ta.u_idx = tb.u_idx where ta.b_idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			rs = pstmt.executeQuery();	
			
			while(rs.next()){
				board = new Board();
				board.setB_idx(rs.getInt("b_idx"));
				board.setB_title(rs.getString("b_title"));
				board.setB_content(rs.getString("b_content"));
				board.setB_date(rs.getString("b_date"));
				board.setU_idx(Integer.parseInt(rs.getString("u_idx")));
				board.setB_hit(Integer.parseInt(rs.getString("b_hit")));
				board.setB_group(rs.getInt("b_group"));
				board.setB_order(rs.getInt("b_order"));
				board.setB_depth(rs.getInt("b_depth"));
				User user = new User(); 
				user.setU_name(rs.getString("u_name"));
				board.setUser(user);
			}
		}catch(Exception ex) {
			System.out.println("SQLException: "+ex.getMessage());
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return board;
	}
	//수정
	public void modifyDetail(Board board) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBConnection.getConnection();
			
			String sql = "update board set b_title=?, b_content=? where b_idx=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, board.getB_title());
			pstmt.setString(2, board.getB_content());
			pstmt.setInt(3, board.getB_idx());
			pstmt.executeUpdate();
			
		}catch(Exception ex) {
			System.out.println("SQLException: "+ex.getMessage());
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	//삭제
	public void deleteDetail(int idx) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBConnection.getConnection();
			String sql = "delete from board where b_idx=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			pstmt.executeUpdate();
		}catch(Exception ex) {
			System.out.println("SQLException: "+ex.getMessage());
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	//조회수
	public int upHit(int idx){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;	

		try{
			conn = DBConnection.getConnection();
			
			String sql=" update board set b_hit = b_hit + 1 where b_idx =?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			count = pstmt.executeUpdate();
		}catch(Exception ex) {
			System.out.println("SQLException: "+ex.getMessage());
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}
	//답글
	public void insertReply(Board board) {
		Connection conn = null;
		PreparedStatement pstmt = null;	
		ResultSet rs = null;
		String sql = null;
			
		try {
			conn = DBConnection.getConnection();
			sql = "select b_group, b_order, b_depth from board where b_idx = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board.getB_idx());
			rs = pstmt.executeQuery();	
			while(rs.next()){
				board = new Board();
				board.setB_idx(rs.getInt("b_idx"));
				board.setB_group(rs.getInt("b_group"));
				board.setB_depth(rs.getInt("b_depth"));
				board.setB_order(rs.getInt("b_order"));
			}
			pstmt.close();
			
			sql="update board set b_order = b_order+1 where b_group >= ? and b_order > ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board.getB_group());
			pstmt.setInt(2, board.getB_order());
			pstmt.executeUpdate();
			pstmt.close();
			
			sql = "insert into board (b_title, b_content, b_date, u_idx, b_hit, b_group, b_order, b_depth)"
					+ "values(?, ?, NOW(), ?, 0, ?, ?, ?)" ;  
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, board.getB_title());
			pstmt.setString(2, board.getB_content());
			pstmt.setInt(3, board.getU_idx());
			pstmt.setInt(4, board.getB_group());
			pstmt.setInt(5, board.getB_order()+1);
			pstmt.setInt(6, board.getB_depth()+1);
			pstmt.executeUpdate();
	

		} catch(Exception ex) {
			System.out.println("SQLException: "+ex.getMessage());
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	//댓글 리스트
	public ArrayList<Comment> getCommentList(int idx) {
		Connection conn =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Comment> cList = null;
		String sql = null;
		try {
			conn = DBConnection.getConnection();
			sql = "SELECT ta.*, tb.u_name FROM boardcomment ta left JOIN user tb ON ta.u_idx = tb.u_idx WHERE ta.b_idx = ? ORDER BY c_group DESC, c_order asc";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			rs = pstmt.executeQuery();
			cList = new ArrayList<Comment>(); 
			
			while (rs.next()) {
				Comment comment = new Comment();
				comment = new Comment();
				comment.setC_idx(rs.getInt("c_idx"));
				comment.setU_idx(rs.getInt("u_idx"));
				comment.setC_content(rs.getString("c_content"));
				
				SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm");
				/*Date date = rs.getTimestamp("c_date");
				String c_date = f.format(date);*/
				comment.setC_date(f.format(rs.getTimestamp("c_date")));
				
				comment.setB_idx(rs.getInt("b_idx"));
				comment.setC_group(rs.getInt("c_group"));
				comment.setC_depth(rs.getInt("c_depth"));
				comment.setC_order(rs.getInt("c_order"));
				User user = new User(); 
				user.setU_name(rs.getString("u_name"));
				comment.setUser(user);
				
				cList.add(comment);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return cList;
		
	}
	//댓글 입력
	public void insertComment(Comment comment) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBConnection.getConnection();
			sql="insert into boardcomment(c_content, c_date, u_idx, b_idx, c_group, c_depth, c_order) values(?, SYSDATE(), ?, ?, ?, 0, 1)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, comment.getC_content());
			pstmt.setInt(2, comment.getU_idx());
			pstmt.setInt(3, comment.getB_idx());
			pstmt.setInt(4, comment.getC_group());
			pstmt.executeUpdate();
			pstmt.close();
			
			sql = "update boardcomment set c_group = last_insert_id() where c_idx = last_insert_id()";
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
			
		}catch (Exception ex) {
			System.out.println("SQLException : "+ex.getMessage());
		}finally {
			try {
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} 
	}
		
	
	//댓글 수정
	public void commentModify(Comment comment) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBConnection.getConnection();
				
			String sql = "update boardcomment set c_content=? where c_idx=? and b_idx=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, comment.getC_content());
			pstmt.setInt(2, comment.getC_idx());				
			pstmt.setInt(3, comment.getB_idx());
			pstmt.executeUpdate();
				
		}catch(Exception ex) {
			System.out.println("SQLException: "+ex.getMessage());
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	//댓글 삭제
	public void commentdelete(int idx) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBConnection.getConnection();
			String sql = "delete from boardcomment where c_idx=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			pstmt.executeUpdate();
		}catch(Exception ex) {
			System.out.println("SQLException: "+ex.getMessage());
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
		
}

	
