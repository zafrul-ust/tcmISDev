package com.tcmis.ws.scanner.beans;

import org.apache.commons.lang.StringUtils;


/******************************************************************************
 * CLASSNAME: PoLineBean <br>
 * 
 * @version: 1.0, Mar 7, 2013 <br>
 *****************************************************************************/

public class CitrReceipt extends ReceivingReceipt {

	public CitrReceipt() {
		super();
		setDocType("IA");
	}

	public boolean IsValidReceipt() {
		return hasDocNum() && StringUtils.isNotBlank(getCustomerPo()) && super.IsValidReceipt();
	}


}