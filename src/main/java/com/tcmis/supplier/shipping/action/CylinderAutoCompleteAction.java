package com.tcmis.supplier.shipping.action;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tcmis.common.util.StringHandler;
import com.tcmis.supplier.shipping.beans.CylinderManagementBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.admin.beans.AutocompleteInputBean;
import com.tcmis.supplier.shipping.process.CylinderAutoCompleteProcess;


public class CylinderAutoCompleteAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CylinderAutoCompleteProcess process = new CylinderAutoCompleteProcess(this.getDbUser(request),getTcmISLocaleString(request));
		//copy date from dyna form to the data bean
		AutocompleteInputBean inputBean = new AutocompleteInputBean(request);
		BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
		// Write out the data
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		if(inputBean.isSerialNumberSearch()) {
			Collection<CylinderManagementBean> results = process.getSerialNumberColl(inputBean);
			// status needs to be in the third position or just id:name with no statue
			// If we need to add different values, please leave the third one empty like id:name::something else
			for (CylinderManagementBean bean : results)
				out.println(bean.getSerialNumber() + ":" + bean.getSerialNumber() + ":'A'");
		}else if (inputBean.isManufacturerSearch()) {
			Collection<CylinderManagementBean> results = process.getManufacturerColl(inputBean);
			// status needs to be in the third position or just id:name with no statue
			// If we need to add different values, please leave the third one empty like id:name::something else
			for (CylinderManagementBean bean : results)
				out.println(bean.getManufacturerIdNo() + ":" + bean.getManufacturerName() + ":'A'");
		}else if (inputBean.isVendorPartNoSearch()) {
			Collection<CylinderManagementBean> results = process.getVendorPartNoColl(inputBean);
			// status needs to be in the third position or just id:name with no statue
			// If we need to add different values, please leave the third one empty like id:name::something else
			for (CylinderManagementBean bean : results) {
				String tmpPartData = bean.getVendorPartNo();
				if (StringHandler.isBlankString(bean.getRefurbCategory()))
					tmpPartData += ";X";
				else
					tmpPartData += ";"+bean.getRefurbCategory();
				if (StringHandler.isBlankString(bean.getConversionGroup()))
					tmpPartData += ";X";
				else
					tmpPartData += ";"+bean.getConversionGroup();
				out.println(tmpPartData + ":" + bean.getVendorPartNo() + ":'A'");
			}
		}else if (inputBean.isCorrespondingNsnSearch()) {
			Collection<CylinderManagementBean> results = process.getCorrespondingNsnColl(inputBean);
			// status needs to be in the third position or just id:name with no statue
			// If we need to add different values, please leave the third one empty like id:name::something else
			for (CylinderManagementBean bean : results) {
				String tmpPartData = bean.getCorrespondingNsn();
				if (StringHandler.isBlankString(bean.getRefurbCategory()))
					tmpPartData += ";X";
				else
					tmpPartData += ";"+bean.getRefurbCategory();
				if (StringHandler.isBlankString(bean.getConversionGroup()))
					tmpPartData += ";X";
				else
					tmpPartData += ";"+bean.getConversionGroup();
				out.println(tmpPartData + ":" + bean.getCorrespondingNsn() + ":'A'");
			}
		}

		out.close();

		return noForward;	
	}

}
