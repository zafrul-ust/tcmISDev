package com.tcmis.internal.hub.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONObject;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.catalog.beans.SpecBean;
import com.tcmis.internal.hub.beans.ReceiptDescriptionViewBean;
import com.tcmis.internal.hub.beans.ReceivingQcCheckListBean;
import com.tcmis.internal.hub.process.ReceivingQcCheckListProcess;

public class AssocPrsAction extends TcmISBaseAction {
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		
		JSONObject results = new JSONObject();
		if (!isLoggedIn(request)) {
			results.put("Message", getResourceBundleValue(request, "label.timeoutmessage1"));
		}
		else
		{
			ReceiptDescriptionViewBean receiptDescriptionViewBean = new ReceiptDescriptionViewBean();
			BeanHandler.copyAttributes(form, receiptDescriptionViewBean);
			ReceivingQcCheckListProcess receivingQcCheckListProcess = new ReceivingQcCheckListProcess(getDbUser(), getTcmISLocale());
	
			
			Collection<ReceivingQcCheckListBean> associatePrs = receivingQcCheckListProcess.getAssociatePrs(receiptDescriptionViewBean);
	
			if(associatePrs == null)
				results.put("Message", getResourceBundleValue(request, "error.db.select"));
			else
			{
					SimpleDateFormat outformat = new SimpleDateFormat("dd-MMM-yyyy");
					JSONArray resultsArray = new JSONArray();
					for (ReceivingQcCheckListBean bean : associatePrs) {
						JSONObject aJSON = new JSONObject();
						aJSON.put("prNumber", bean.getPrNumber());
						aJSON.put("mrNumber", bean.getMrNumber());
						aJSON.put("mrLineItem", bean.getMrLineItem());
						aJSON.put("partId", bean.getPartId());
						aJSON.put("itemType", bean.getItemType());
						aJSON.put("notes", bean.getNotes());
						aJSON.put("requestorLastName", bean.getRequestorLastName());
						aJSON.put("requestorFirstName}", bean.getRequestorFirstName());
						aJSON.put("phone", bean.getPhone());
						aJSON.put("email", bean.getEmail());
						aJSON.put("csrName", bean.getCsrName());
						aJSON.put("mrQuantity", bean.getMrQuantity());
						aJSON.put("orderQuantity", bean.getOrderQuantity());
						aJSON.put("sizeUnit", bean.getSizeUnit());
						aJSON.put("needDate", (bean.getNeedDate() != null?outformat.format(bean.getNeedDate()):""));
						aJSON.put("facilityId", bean.getFacilityId());
						aJSON.put("shipToDeliveryPoint", bean.getShipToDeliveryPoint());
						resultsArray.put(aJSON);
					}
					results.put("assocPrs", resultsArray);
			}
		}
	
				
		// Write out the data
		PrintWriter out = response.getWriter();
		out.write(results.toString(3));
		out.close();
		return noForward;
	}
}