package com.tcmis.client.common.beans;

import com.tcmis.common.framework.BaseDataBean;
import java.math.BigDecimal;

/******************************************************************************
 * @version: 1.0, Feb 16, 2011 <br>
 *****************************************************************************/


public class SecondaryLabelDataBean extends BaseDataBean {

	private String companyId;
	private String faciltiyId;
	private BigDecimal typeId;
	private String commentId;
	private BigDecimal materialId;
	private String commentAltTxt;
    private String typeName;

    //constructor
	public SecondaryLabelDataBean() {
	}

	public String getCommentAltTxt() {
		return commentAltTxt;
	}

	public void setCommentAltTxt(String commentAltTxt) {
		this.commentAltTxt = commentAltTxt;
	}


	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getFaciltiyId() {
		return faciltiyId;
	}

	public void setFaciltiyId(String faciltiyId) {
		this.faciltiyId = faciltiyId;
	}

	public BigDecimal getMaterialId() {
		return materialId;
	}

	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public BigDecimal getTypeId() {
		return typeId;
	}

	public void setTypeId(BigDecimal typeId) {
		this.typeId = typeId;
	}

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}