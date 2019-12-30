package com.tcmis.internal.supply.action;

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
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.supply.beans.PurchaseOrderBean;
import com.tcmis.internal.supply.beans.VvItemComponentViewBean;
import com.tcmis.internal.supply.process.PoPageProcess;

public class PoMsdsAction extends TcmISBaseAction {
		public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
			
			JSONObject rowsObj = new JSONObject();
			if (!isLoggedIn(request)) {
				rowsObj.put("Message", getResourceBundleValue(request, "label.timeoutmessage1"));
			}
			else
			{	
				PoPageProcess poPageProcess = new PoPageProcess(getDbUser());	
				PurchaseOrderBean input = new PurchaseOrderBean();
				BeanHandler.copyAttributes(form, input);
				Collection<VvItemComponentViewBean> beansCol = poPageProcess.getMsdsRev(input);
				boolean msdsCheck = poPageProcess.getMsdsRevCheck(input);
				if(beansCol == null)
					rowsObj.put("Message", getResourceBundleValue(request, "error.db.select"));
				else
				{	
					JSONArray rowsArr = new JSONArray();
					int resultCount = 0;

					for (VvItemComponentViewBean bean : beansCol) {
						JSONObject idDataObj = new JSONObject(); 
						idDataObj.put("id", resultCount+1);

						JSONArray dataArr = new JSONArray();
						dataArr.put("N");
						dataArr.put(StringHandler.emptyIfNull(bean.getDescription()));
						dataArr.put(StringHandler.emptyIfNull(bean.getPackaging()));
						dataArr.put(StringHandler.emptyIfNull(bean.getManufacturer()));
						dataArr.put(((StringHandler.isBlankString(bean.getContent()) || StringHandler.emptyIfNull(bean.getOnLine()).equalsIgnoreCase("N"))? "":"<img src=\"/images/buttons/document.gif\"/>") +  StringHandler.emptyIfNull(bean.getRevisionDate()));
						dataArr.put(StringHandler.emptyIfNull(bean.getRevisionDate()));
						dataArr.put(StringHandler.emptyIfNull(bean.getItemId()));
						dataArr.put(StringHandler.emptyIfNull(bean.getOnLine()));
						dataArr.put(StringHandler.emptyIfNull(bean.getContent()));
						dataArr.put(StringHandler.emptyIfNull(input.getRowNumber()));
						idDataObj.put("data", dataArr);
					    rowsArr.put(idDataObj);
						resultCount++;
					}
					rowsObj.put("MSDSCHECK", msdsCheck);
					rowsObj.put("rows",rowsArr);
					rowsObj.put("poLineNumber",input.getRowNumber());
					rowsObj.put("NoOfRows",resultCount);
				}
			}
			// Write out the data
			PrintWriter out = response.getWriter();
			out.write(rowsObj.toString(3));
			out.close();
			return noForward;
			
		}

}
