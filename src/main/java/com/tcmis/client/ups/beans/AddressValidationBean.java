package com.tcmis.client.ups.beans;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Jun 20, 2008
 * Time: 2:37:49 PM
 * To change this template use File | Settings | File Templates.
 */

import com.tcmis.common.framework.BaseDataBean;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Vector;


/******************************************************************************
 * CLASSNAME: AddressValidationBean <br>
 * @version: 1.0, Jan 18, 2008 <br>
 *****************************************************************************/


public class AddressValidationBean extends BaseDataBean {

	private String transactionReference;
    private String responseStatusCode;
    private String responseStatusDescription;

    private Collection<AddressValidationResultBean> addressValidationResultBeanCollection = new Vector();

    //constructor
	public AddressValidationBean() {
	}

	//setters
	public void setTransactionReference(String s) {
		this.transactionReference = s;
	}

	public void setResponseStatusCode(String s) {
		this.responseStatusCode = s;
	}

	public void setResponseStatusDescription(String s) {
		this.responseStatusDescription = s;
	}

    public void setAddressValidationResultBeanCollection(Collection<AddressValidationResultBean> c){
        this.addressValidationResultBeanCollection = c;
    }

    //getters
	public String getTransactionReference() {
		return this.transactionReference;
	}

	public String getResponseStatusCode() {
		return this.responseStatusCode;
	}

	public String getResponseStatusDescription() {
		return this.responseStatusDescription;
	}

    public Collection<AddressValidationResultBean> getAddressValidationResultBeanCollection(){
        return this.addressValidationResultBeanCollection;
    }

    public void addAddressValidationResultBean(AddressValidationResultBean bean) {
		this.addressValidationResultBeanCollection.add(bean);
	}
}
