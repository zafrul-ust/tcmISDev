
package com.tcmis.internal.supply.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: PoCannedCommentsBean <br>
 * @version: 1.0, Dec 13, 2007 <br>
 *****************************************************************************/


public class PoCannedCommentsBean extends BaseDataBean {

	private BigDecimal 	commentId;
	private String 		comments;
	private String 		commentName;


	//constructor
	public PoCannedCommentsBean() {
	}

	//setters
	public void setCommentId(BigDecimal commentId) {
		this.commentId = commentId;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public void setCommentName(String commentName) {
		this.commentName = commentName;
	}


	//getters
	public BigDecimal getCommentId() {
		return commentId;
	}
	public String getComments() {
		return comments;
	}
	public String getCommentName() {
		return commentName;
	}
}
