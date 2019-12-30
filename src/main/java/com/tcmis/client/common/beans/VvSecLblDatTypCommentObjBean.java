package com.tcmis.client.common.beans;

import java.util.*;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.SQLException;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: OpenUserFacilityObjBean <br>
 * @version: 1.0, Feb 7, 2011 <br>
 *****************************************************************************/


public class VvSecLblDatTypCommentObjBean extends BaseDataBean implements SQLData {
	
	private String commentId;
	private String commentTxt;
	private String sqlType = "VV_SEC_LBL_DAT_TYP_COMMENT_OBJ";

	//constructor
	public VvSecLblDatTypCommentObjBean() {
	}

	//setters
	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}
	public void setCommentTxt(String commentTxt) {
		this.commentTxt = commentTxt;
	}


	//getters
	public String getCommentId() {
		return commentId;
	}
	public String getCommentTxt() {
		return commentTxt;
	}

	public String getSQLTypeName() {
		return this.sqlType;
	}
	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try {
			this.setCommentId(stream.readString());
			this.setCommentTxt(stream.readString());
		}
		catch (SQLException e) {
			throw (SQLException) e;
		}
		catch (Exception e) {
			new IllegalStateException(getClass().getName() + ".readSQL method failed").
			initCause(e);
		}
	}
	public void writeSQL(SQLOutput stream) throws SQLException {
		try {
			stream.writeString(this.getCommentId());
			stream.writeString(this.getCommentTxt());
		}
		catch (SQLException e) {
			throw (SQLException) e;
		}
		catch (Exception e) {
			new IllegalStateException(getClass().getName() + ".writeSQL method failed").
			initCause(e);
		}
	}
}