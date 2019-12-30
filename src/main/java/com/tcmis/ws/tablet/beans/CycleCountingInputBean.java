package com.tcmis.ws.tablet.beans;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.util.StringHandler;

public class CycleCountingInputBean extends BaseTabletBean {
	public static Log	log	= LogFactory.getLog(CycleCountingInputBean.class);
	
	private BigDecimal	actualQuantity;
	private BigDecimal	countId;
	private BigDecimal	receiptId;

	public CycleCountingInputBean(ActionForm form, Locale tcmISLocale) throws BaseException {
		super(form, tcmISLocale);
	}
	public CycleCountingInputBean(ActionForm form, Locale tcmISLocale, String dateFormat) throws BaseException {
		super(form, tcmISLocale, dateFormat);
	}
	public BigDecimal getActualQuantity() {
		return actualQuantity;
	}
	public BigDecimal getCountId() {
		return countId;
	}
	public BigDecimal getReceiptId() {
		return receiptId;
	}
	public void setActualQuantity(BigDecimal actualQuantity) {
		this.actualQuantity = actualQuantity;
	}
	public void setCountId(BigDecimal countId) {
		this.countId = countId;
	}
	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}

}
