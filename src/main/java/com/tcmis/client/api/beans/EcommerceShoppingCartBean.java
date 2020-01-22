package com.tcmis.client.api.beans;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: EcommerceShoppingCartBean <br>
 * @version: 1.0, Jan 8, 2020 <br>
 *****************************************************************************/

public class EcommerceShoppingCartBean extends BaseDataBean {

	private String unspsc;
    private String mfgDesc;

	//constructor
	public EcommerceShoppingCartBean() {
	}

    public String getUnspsc() {
        return unspsc;
    }

    public void setUnspsc(String unspsc) {
        this.unspsc = unspsc;
    }

    public String getMfgDesc() {
        return mfgDesc;
    }

    public void setMfgDesc(String mfgDesc) {
        this.mfgDesc = mfgDesc;
    }
}