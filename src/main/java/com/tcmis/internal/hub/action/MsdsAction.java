package com.tcmis.internal.hub.action;

import java.io.PrintWriter;
import java.math.BigDecimal;
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
import com.tcmis.internal.hub.beans.ReceivingQcCheckListBean;
import com.tcmis.internal.hub.process.ReceivingQcCheckListProcess;

public class MsdsAction extends TcmISBaseAction {
		public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
			
			JSONObject results = new JSONObject();
			if (!isLoggedIn(request)) {
				results.put("Message", getResourceBundleValue(request, "label.timeoutmessage1"));
			}
			else
			{	
				ReceivingQcCheckListProcess receivingQcCheckListProcess = new ReceivingQcCheckListProcess(getDbUser(), getTcmISLocale());
				Collection<ReceivingQcCheckListBean> msdsInfo = receivingQcCheckListProcess.getMsdsInfo(new BigDecimal(request.getParameter("itemId")));
				if(msdsInfo == null)
					results.put("Message", getResourceBundleValue(request, "error.db.select"));
				else
				{
						SimpleDateFormat outformat = new SimpleDateFormat("dd-MMM-yyyy");
						JSONArray resultsArray = new JSONArray();
						for (ReceivingQcCheckListBean bean : msdsInfo) {								          
							JSONObject aJSON = new JSONObject();
							aJSON.put("description", bean.getDescription());
							aJSON.put("manufacturer", bean.getManufacturer());
							aJSON.put("revisionDate", (bean.getRevisionDate() != null?outformat.format(bean.getRevisionDate()):""));
							aJSON.put("content", bean.getContent());
							resultsArray.put(aJSON);
						}
						results.put("msds", resultsArray);
				}
			}
			// Write out the data
			PrintWriter out = response.getWriter();
			out.write(results.toString(3));
			out.close();
			return noForward;
			
		}

}
