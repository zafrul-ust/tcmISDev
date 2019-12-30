package com.tcmis.internal.hub.action;
import java.io.PrintWriter;
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
import com.tcmis.internal.hub.beans.ReceiptDescriptionViewBean;
import com.tcmis.internal.hub.beans.ReceivingQcCheckListBean;
import com.tcmis.internal.hub.process.ReceivingQcCheckListProcess;

	public class CatalogRQCAction extends TcmISBaseAction {
			public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
				
				JSONObject results = new JSONObject();
				if (!isLoggedIn(request)) {
					results.put("Message", getResourceBundleValue(request, "label.timeoutmessage1"));
				}
				else
				{	
					ReceivingQcCheckListProcess receivingQcCheckListProcess = new ReceivingQcCheckListProcess(getDbUser(), getTcmISLocale());
					ReceiptDescriptionViewBean receiptDescriptionViewBean = new ReceiptDescriptionViewBean();
					BeanHandler.copyAttributes(form, receiptDescriptionViewBean);
					Collection<ReceivingQcCheckListBean> catalogResults = receivingQcCheckListProcess.getCatalogResults(receiptDescriptionViewBean);
					if(catalogResults == null)
						results.put("Message", getResourceBundleValue(request, "error.db.select"));
					else
					{
							JSONArray resultsArray = new JSONArray();
							for (ReceivingQcCheckListBean bean : catalogResults) {			
								resultsArray.put(BeanHandler.getJsonObject(bean));				             
					        }
							results.put("catalog", resultsArray);
					}
				}
				// Write out the data
				PrintWriter out = response.getWriter();
				out.write(results.toString(3));
				out.close();
				return noForward;
				
			}

	}
