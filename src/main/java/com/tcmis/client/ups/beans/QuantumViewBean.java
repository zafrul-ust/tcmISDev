package com.tcmis.client.ups.beans;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Jun 6, 2008
 * Time: 3:32:42 PM
 * To change this template use File | Settings | File Templates.
 */

import com.tcmis.common.framework.BaseDataBean;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Vector;


/******************************************************************************
 * CLASSNAME: ShipmentAcceptResponseBean <br>
 * @version: 1.0, Jan 18, 2008 <br>
 *****************************************************************************/


public class QuantumViewBean extends BaseDataBean {

	private String subscriberId;
    private String error;
    private String errorDescription;
    private String responseStatusDescription;
    private String bookmark;

    private Collection<QuantumViewResponseBean> quantumViewResponseBeanCollection = new Vector();

    //constructor
	public QuantumViewBean() {
	}

	//setters
	public void setSubscriberId(String s) {
		this.subscriberId = s;
	}

	public void setError(String s) {
		this.error = s;
	}

	public void setErrorDescription(String s) {
		this.errorDescription = s;
	}

	public void setResponseStatusDescription(String s) {
		this.responseStatusDescription = s;
	}

	public void setBookmark(String s) {
		this.bookmark = s;
	}

    public void setQuantumViewResponseBeanCollection(Collection<QuantumViewResponseBean> c){
        this.quantumViewResponseBeanCollection = c;
    }

    //getters
	public String getSubscriberId() {
		return this.subscriberId;
	}

	public String getError() {
		return this.error;
	}

	public String getErrorDescription() {
		return this.errorDescription;
	}

	public String getResponseStatusDescription() {
		return this.responseStatusDescription;
	}

    public String getBookmark() {
		return this.bookmark;
	}

    public Collection<QuantumViewResponseBean> getQuantumViewResponseBeanCollection(){
        return this.quantumViewResponseBeanCollection;
    }

    public void addQuantumViewResponseBean(QuantumViewResponseBean bean) {
		this.quantumViewResponseBeanCollection.add(bean);
	}
}

