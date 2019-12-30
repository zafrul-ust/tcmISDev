package com.tcmis.client.common.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: MaterialShipDeclarationViewBean <br>
 * @version: 1.0, March 8, 2012 <br>
 *****************************************************************************/


public class MaterialShipDeclarationViewBean extends BaseDataBean {

	private BigDecimal materialId;
	private String shippingRegulation;
	private String shippingDeclaration;


	public BigDecimal getMaterialId() {
		return materialId;
	}


	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}


	public String getShippingDeclaration() {
		return shippingDeclaration;
	}


	public void setShippingDeclaration(String shippingDeclaration) {
		this.shippingDeclaration = shippingDeclaration;
	}


	public String getShippingRegulation() {
		return shippingRegulation;
	}


	public void setShippingRegulation(String shippingRegulation) {
		this.shippingRegulation = shippingRegulation;
	}


	//constructor
	public MaterialShipDeclarationViewBean() {
	}

}