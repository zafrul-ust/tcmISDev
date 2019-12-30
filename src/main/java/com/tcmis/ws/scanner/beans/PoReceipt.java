package com.tcmis.ws.scanner.beans;

import java.math.BigDecimal;

/******************************************************************************
 * CLASSNAME: PoLineBean <br>
 * 
 * @version: 1.0, Mar 7, 2013 <br>
 *****************************************************************************/

public class PoReceipt extends ReceivingReceipt {

	public PoReceipt() {
		super();
		setDocType("OV");
	}
	
	public boolean IsValidReceipt() {
		return getPo() != null && getPoLine() != null && super.IsValidReceipt();
	}

}