package com.tcmis.client.common.beans;

import com.tcmis.common.framework.BaseDataBean;
import java.math.BigDecimal;

/******************************************************************************
 * @version: 1.0, Feb 18, 2011 <br>
 *****************************************************************************/


public class CommentBean extends BaseDataBean {

	private String commentId;
	private String commentTxt;
	private String originalCommentTxt;
	private String status;
    
	//constructor
	public CommentBean() {
	}

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public String getCommentTxt() {
		return commentTxt;
	}

	public void setCommentTxt(String commentTxt) {
		this.commentTxt = commentTxt;
	}

	public String getOriginalCommentTxt() {
		return originalCommentTxt;
	}

	public void setOriginalCommentTxt(String originalCommentTxt) {
		this.originalCommentTxt = originalCommentTxt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}