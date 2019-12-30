package com.tcmis.client.common.beans;

import java.util.Locale;
import java.math.BigDecimal;

import org.apache.struts.action.ActionForm;

import com.tcmis.common.framework.BaseInputBean;
import com.tcmis.common.util.StringHandler;

public class DocViewerInputBean extends BaseInputBean {

	private static final long	serialVersionUID	= 1L;

	private String				catalogId;
	private String				catpartno;
	private String				companyId;
	private String				facility;
	private String				fileName;
	private String				inventoryGroup;
	private String				opsEntityId;
	private String				seq;
	private String				spec;
    //the reason for this is because in general condition the specific facility is specified
    //in Swing Gui if user picks All Catalog then it will pass in N for hasSpecificFacility
    private String              hasSpecificFacility;
    //this is to handle docViewer called from catalog add request pages
    private BigDecimal          catalogAddRequestId;
    //this is to user confirmed to view ITAR
    private String              viewItarConfirmed;
    private String              specLibrary;


    public DocViewerInputBean(ActionForm form, Locale tcmISLocale) {
		super(form, tcmISLocale);
	}

	public String getCatalogId() {
		return catalogId;
	}

	public String getCatpartno() {
		return catpartno;
	}

	public String getCompanyId() {
		return companyId;
	}

	public String getFacility() {
		return facility;
	}

	public String getFileName() {
		return fileName;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public String getOpsEntityId() {
		return opsEntityId;
	}

	public String getSeq() {
		return seq;
	}

	public String getSpec() {
		return spec;
	}

	public boolean hasOpsEntityId() {
		return !StringHandler.isBlankString(opsEntityId);
	}

	public boolean hasFacility() {
		return !StringHandler.isBlankString(facility);
	}
	
	public boolean hasSeq() {
		return !StringHandler.isBlankString(seq);
	}

	public boolean isViewSpec() {
		return "viewSpec".equals(getuAction());
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public void setCatpartno(String catpartno) {
		this.catpartno = catpartno;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setFacility(String facility) {
		this.facility = facility;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setHiddenFormFields() {
		// TODO Auto-generated method stub

	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}


    public void setSpec(String spec) {
        //the reason for this is because we added "(Purchase to)" to spec_id in the
        //catalog right mouse click
        if (spec.indexOf("(Purchase") > 0)
            this.spec = spec.substring(0,spec.indexOf("(Purchase")).trim();
        else
            this.spec = spec;
	}

    public String getHasSpecificFacility() {
        return hasSpecificFacility;
    }

    public void setHasSpecificFacility(String hasSpecificFacility) {
        this.hasSpecificFacility = hasSpecificFacility;
    }
    
    public boolean isViewCatalogAddSpec() {
		return "viewCatalogAddSpec".equals(getuAction());
	}

    public boolean isViewCatalogAddSpecQc() {
		return "viewCatalogAddSpecQc".equals(getuAction());
	}

    public BigDecimal getCatalogAddRequestId() {
        return catalogAddRequestId;
    }

    public void setCatalogAddRequestId(BigDecimal catalogAddRequestId) {
        this.catalogAddRequestId = catalogAddRequestId;
    }

    public String getViewItarConfirmed() {
        return viewItarConfirmed;
    }

    public void setViewItarConfirmed(String viewItarConfirmed) {
        this.viewItarConfirmed = viewItarConfirmed;
    }

    public String getSpecLibrary() {
        return specLibrary;
    }

    public void setSpecLibrary(String specLibrary) {
        this.specLibrary = specLibrary;
    }
}
