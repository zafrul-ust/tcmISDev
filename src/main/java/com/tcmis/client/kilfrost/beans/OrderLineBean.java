package com.tcmis.client.kilfrost.beans;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Sep 23, 2008
 * Time: 4:59:27 PM
 * To change this template use File | Settings | File Templates.
 */
import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: OrderBean <br>
 * @version: 1.0, Jan 18, 2008 <br>
 *****************************************************************************/


public class OrderLineBean extends BaseDataBean {

    private String partNumber;
    private String quantity;

    //constructor
	public OrderLineBean() {
	}

	//setters
	public void setPartNumber(String s) {
		this.partNumber = s;
	}

	public void setQuantity(String b) {
		this.quantity = b;
	}

    //getters
	public String getPartNumber() {
		return this.partNumber;
	}

    public String getQuantity() {
        return this.quantity;
    }

}

