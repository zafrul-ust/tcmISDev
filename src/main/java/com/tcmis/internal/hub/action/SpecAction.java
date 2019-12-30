package com.tcmis.internal.hub.action;

import java.io.PrintWriter;
import java.math.BigDecimal;
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
import com.tcmis.internal.hub.beans.ReceiptDocumentInputBean;
import com.tcmis.internal.hub.beans.ReceiptDocumentViewBean;
import com.tcmis.internal.hub.process.SpecFlowDownProcess;

public class SpecAction extends TcmISBaseAction {
		public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

			JSONObject results = new JSONObject();
			if (!isLoggedIn(request)) {
				results.put("Message", getResourceBundleValue(request, "label.timeoutmessage1"));
			}
			else
			{
				SpecFlowDownProcess specFlowDownProcess = new SpecFlowDownProcess(getDbUser(), getTcmISLocale());
				ReceiptDescriptionViewBean receiptDescriptionViewBean = new ReceiptDescriptionViewBean();
				BeanHandler.copyAttributes(form, receiptDescriptionViewBean);
				
				Collection<SpecBean> sendSpecs = specFlowDownProcess.buildSendSpecs(receiptDescriptionViewBean);
				if(sendSpecs == null)
					results.put("Message", getResourceBundleValue(request, "error.db.select"));
				else
				{
						JSONArray resultsArray = new JSONArray();
						for (SpecBean bean : sendSpecs) {
							JSONObject aJSON = new JSONObject();
							aJSON.put("color", bean.getColor());
							aJSON.put("savedCoc", bean.getSavedCoc());
							aJSON.put("savedCoa", bean.getSavedCoa());
							aJSON.put("specIdDisplay", bean.getSpecIdDisplay());
							aJSON.put("specLibraryDesc", bean.getSpecLibraryDesc());
							aJSON.put("currentCocRequirement", bean.getCurrentCocRequirement());
							aJSON.put("currentCoaRequirement", bean.getCurrentCoaRequirement());
							aJSON.put("cocReqAtSave", bean.getCocReqAtSave());
							aJSON.put("coaReqAtSave", bean.getCoaReqAtSave());
							aJSON.put("content", bean.getContent());
							aJSON.put("specId", bean.getSpecId());
                            aJSON.put("specLibrary", bean.getSpecLibrary());
                            resultsArray.put(aJSON);
						}
						results.put("specRes", resultsArray);
				}
			}
		
			// Write out the data
			PrintWriter out = response.getWriter();
			out.write(results.toString(3));
			out.close();
			return noForward;
			
		}
}
