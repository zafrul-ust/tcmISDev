package com.tcmis.internal.print.beans;

import org.apache.commons.beanutils.DynaBean;

import com.tcmis.common.exceptions.BeanCopyException;
import com.tcmis.common.framework.BaseInputBean;
import com.tcmis.common.util.BeanHandler;

public class StaticLabelInputBean extends BaseInputBean {
	private String labelFileName;
	private int printQuantity;

	public StaticLabelInputBean(DynaBean dynaForm) {
		try {
			BeanHandler.copyAttributes(dynaForm, this);
		}
		catch (BeanCopyException bce) {
		}
	}

	@Override
	public void setHiddenFormFields() {
		// TODO Auto-generated method stub

	}

	public String getLabelFileName() {
		return labelFileName;
	}

	public int getPrintQuantity() {
		return printQuantity;
	}

	public void setLabelFileName(String labelFileName) {
		this.labelFileName = labelFileName;
	}

	public void setPrintQuantity(int printQuantity) {
		this.printQuantity = printQuantity;
	}

	public boolean isPrint() {
		return "Print".equals(getuAction());
	}
}
