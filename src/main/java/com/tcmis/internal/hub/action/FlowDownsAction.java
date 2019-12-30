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

import com.tcmis.client.catalog.beans.FlowDownBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.ReceiptDescriptionViewBean;
import com.tcmis.internal.hub.process.SpecFlowDownProcess;

	public class FlowDownsAction extends TcmISBaseAction {
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
					Collection<FlowDownBean> flowDown = specFlowDownProcess.buildSendFlowdowns(receiptDescriptionViewBean);
					if(flowDown == null)
						results.put("Message", getResourceBundleValue(request, "error.db.select"));
					else
					{
							JSONArray resultsArray = new JSONArray();
							for (FlowDownBean bean : flowDown) {								          
								JSONObject aJSON = new JSONObject();
								aJSON.put("flowDown", bean.getFlowDown());
								aJSON.put("revisionDate", bean.getRevisionDate());
								aJSON.put("flowDownDesc", bean.getFlowDownDesc());
								aJSON.put("catalogId", bean.getCatalogId());
								aJSON.put("companyId", bean.getCompanyId());
								aJSON.put("flowDownType", bean.getFlowDownType());
								aJSON.put("onLine", bean.getOnLine());
								aJSON.put("content", bean.getContent());
								aJSON.put("ok", bean.getOk());
								aJSON.put("color", bean.getColor());
								resultsArray.put(aJSON);
							}
							results.put("flowDowns", resultsArray);
					}
				}
				// Write out the data
				PrintWriter out = response.getWriter();
				out.write(results.toString(3));
				out.close();
				return noForward;
				
			}

	}
