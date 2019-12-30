package com.tcmis.client.catalog.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: BoothBean <br>
 * @version: 1.0, Jul 11, 2011 <br>
 *****************************************************************************/

public class BoothBean extends BaseDataBean {

    private BigDecimal boothId;
    private String boothName;
	private String companyId;
    private String facilityId;
    private String boothVocetId;
    private String production;
    private String nonProduction;
    private String isFugitive;
    private String boothType;

    //constructor
	public BoothBean() {
	}

    public BigDecimal getBoothId() {
        return boothId;
    }

    public void setBoothId(BigDecimal boothId) {
        this.boothId = boothId;
    }

    public String getBoothName() {
        return boothName;
    }

    public void setBoothName(String boothName) {
        this.boothName = boothName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }

    public String getBoothVocetId() {
        return boothVocetId;
    }

    public void setBoothVocetId(String boothVocetId) {
        this.boothVocetId = boothVocetId;
    }

    public String getProduction() {
        return production;
    }

    public void setProduction(String production) {
        this.production = production;
    }

    public String getNonProduction() {
        return nonProduction;
    }

    public void setNonProduction(String nonProduction) {
        this.nonProduction = nonProduction;
    }

    public String getFugitive() {
        return isFugitive;
    }

    public void setFugitive(String fugitive) {
        isFugitive = fugitive;
    }

    public String getBoothType() {
        return boothType;
    }

    public void setBoothType(String boothType) {
        this.boothType = boothType;
    }
}