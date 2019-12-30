package com.tcmis.client.ups.beans;


import com.tcmis.common.framework.BaseDataBean;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Vector;


/******************************************************************************
 * CLASSNAME: QuantumViewResponseBean <br>
 * @version: 1.0, Jan 18, 2008 <br>
 *****************************************************************************/


public class ManifestBean extends BaseDataBean {

    private String pickupDate;

    private Collection<PackageResultsBean> packageResultsBeanCollection = new Vector();
    //constructor
	public ManifestBean() {
	}

	//setters
	public void setPickupDate(String s) {
		this.pickupDate = s;
	}

    public void setPackageResultsBeanCollection(Collection<PackageResultsBean> c){
        this.packageResultsBeanCollection = c;
    }
    //getters
	public String getPickupDate() {
		return this.pickupDate;
	}

    public Collection<PackageResultsBean> getPackageResultsBeanCollection(){
        return this.packageResultsBeanCollection;
    }

    public void addPackageResultsBean(PackageResultsBean packageResultsBean){
        this.packageResultsBeanCollection.add(packageResultsBean);
    }
}
