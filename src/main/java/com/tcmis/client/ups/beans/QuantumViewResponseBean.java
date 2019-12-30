package com.tcmis.client.ups.beans;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Jun 6, 2008
 * Time: 3:23:13 PM
 * To change this template use File | Settings | File Templates.
 */

import com.tcmis.common.framework.BaseDataBean;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Vector;


/******************************************************************************
 * CLASSNAME: QuantumViewResponseBean <br>
 * @version: 1.0, Jan 18, 2008 <br>
 *****************************************************************************/


public class QuantumViewResponseBean extends BaseDataBean {

    //private String bookmark;

    private Collection<ManifestBean> manifestBeanCollection = new Vector();

    private Collection<PackageResultsBean>packageResultsBeanCollection = new Vector();
    
    //constructor
	public QuantumViewResponseBean() {
	}

	//setters


    public void setManifestBeanCollection(Collection<ManifestBean> c){
        this.manifestBeanCollection = c;
    }

    public void setPackageResultsBeanCollection(Collection<PackageResultsBean> c){
        this.packageResultsBeanCollection = c;
    }
    //getters


    public Collection<ManifestBean> getManifestBeanCollection(){
        return this.manifestBeanCollection;
    }

    public void addManifestBean(ManifestBean bean){
        this.manifestBeanCollection.add(bean);
    }

    public Collection<PackageResultsBean> getPackageResultsBeanCollection(){
        return this.packageResultsBeanCollection;
    }

    public void addPackageResultsBean(PackageResultsBean bean){
        this.packageResultsBeanCollection.add(bean);
    }
}
