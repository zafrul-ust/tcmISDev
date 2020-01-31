package com.tcmis.client.api.beans;

import com.tcmis.common.framework.BaseDataBean;

import java.math.BigDecimal;

/******************************************************************************
 * CLASSNAME: EcommerceShoppingCartBean <br>
 * @version: 1.0, Jan 8, 2020 <br>
 *****************************************************************************/

public class EcommerceShoppingCartBean extends BaseDataBean {

	private BigDecimal unspsc;
    private String mfgDesc;

	//constructor
	public EcommerceShoppingCartBean() {
	}

    public BigDecimal getUnspsc() {
        return unspsc;
    }

    public void setUnspsc(BigDecimal unspsc) {
        this.unspsc = unspsc;
    }

    public String getMfgDesc() {
        return mfgDesc;
    }

    public void setMfgDesc(String mfgDesc) {
        this.mfgDesc = mfgDesc;
    }
}