package com.tcmis.client.cal.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: Cal.TransactionTypes <br>
 * @version: 1.0, Jul 16, 2012 <br>
 *****************************************************************************/


public class TransactionTypes extends BaseDataBean {

	private String txType;
	private BigDecimal txQty;
	private String txTmstmp;

	//constructor
	public TransactionTypes() {
	}

	public BigDecimal getTxQty() {
		return txQty;
	}

	public void setTxQty(BigDecimal txQty) {
		this.txQty = txQty;
	}

	public String getTxTmstmp() {
		return txTmstmp;
	}

	public void setTxTmstmp(String txTmstmp) {
		this.txTmstmp = txTmstmp;
	}

	public String getTxType() {
		return txType;
	}

	public void setTxType(String txType) {
		this.txType = txType;
	}


}