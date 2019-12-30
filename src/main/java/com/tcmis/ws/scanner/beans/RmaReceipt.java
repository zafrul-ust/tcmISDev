package com.tcmis.ws.scanner.beans;

import java.math.BigDecimal;

/******************************************************************************
 * CLASSNAME: PoLineBean <br>
 * 
 * @version: 1.0, Mar 7, 2013 <br>
 *****************************************************************************/

public class RmaReceipt extends ReceivingReceipt {

	private BigDecimal	customerRmaId;
	private BigDecimal	originalReceiptId;

	public RmaReceipt() {
		super();
		setDocType("IT");
	}

	public boolean IsValidReceipt() {
		return getCustomerRmaId() != null && getOriginalReceiptId() != null && super.IsValidReceipt();
	}


}