package com.tcmis.ws.scanner.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

public class DocumentId extends BaseDataBean {
	private BigDecimal	docId;

	public BigDecimal getDocId() {
		return docId;
	}

	public void setDocId(BigDecimal docId) {
		this.docId = docId;
	}
}
