package com.tcmis.ws.scanner.beans;

import java.math.BigDecimal;

/******************************************************************************
 * CLASSNAME: PoLineBean <br>
 * 
 * @version: 1.0, Mar 7, 2013 <br>
 *****************************************************************************/

public class TransferReceipt extends ReceivingReceipt {


	public TransferReceipt() {
		super();
		setDocType("IT");
	}

	public boolean IsValidReceipt() {
		return getTransferRequestId() != null && getOriginalReceiptId() != null && super.IsValidReceipt();
	}

}