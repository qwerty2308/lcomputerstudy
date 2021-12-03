package com.lcomputerstudy.testmvc.vo;



public class Board {
	 
	private int b_idx;
	private String b_title;
	private String b_content;
	private String b_date;
	private int u_idx;
	private int b_hit;
	private int b_rownum;
	private int b_group;
	private int b_order;
	private int b_depth;
	private User user;
	

	public int getB_idx() {
		return b_idx;
	}
	
	public void setB_idx(int b_idx) {
		this.b_idx = b_idx;
	}
	
	public String getB_title() {
		 return b_title;
	}
	
	public void setB_title(String b_title) {
		this.b_title = b_title;
	}
	
	public String getB_content() {
		return b_content;
	}
	
	public void setB_content(String b_content) {
		this.b_content = b_content;
	}
	
	public String getB_date() {
		return b_date;
	}
	
	public void setB_date(String b_date) {
		this.b_date = b_date;
	}
	
	
	public int getU_idx() {
		return u_idx;
	}
	
	public void setU_idx(int u_idx) {
		this.u_idx = u_idx;
	}
	
	public int getB_hit() {
		return b_hit;
	}

	public void setB_hit(int b_hit) {
		this.b_hit = b_hit;
	}

	public int getB_rownum() {
		return b_rownum;
	}
	public void setB_rownum(int b_rownum) {
		this.b_rownum = b_rownum;
	}
	
	public int getB_group() {
		return b_group;
	}

	public void setB_group(int b_group) {
		this.b_group = b_group;
	}

	public int getB_order() {
		return b_order;
	}

	public void setB_order(int b_order) {
		this.b_order = b_order;
	}

	public int getB_depth() {
		return b_depth;
	}

	public void setB_depth(int b_depth) {
		this.b_depth = b_depth;
	}
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
