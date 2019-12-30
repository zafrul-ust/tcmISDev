package com.tcmis.internal.supply.action;

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
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.supply.beans.ClientBuyHistoryViewBean;
import com.tcmis.internal.supply.beans.PurchaseOrderBean;
import com.tcmis.internal.supply.process.PoPageProcess;

public class PoClientBuysAction extends TcmISBaseAction {
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
				Collection<ClientBuyHistoryViewBean> beansCol = poPageProcess.getClientBuysCol(input);

				if(beansCol == null)
					rowsObj.put("Message", getResourceBundleValue(request, "error.db.select"));
				else
				{			
					ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", getTcmISLocale());
					SimpleDateFormat dateFormat = new SimpleDateFormat(library.getString("java.dateformat"));
					JSONArray rowsArr = new JSONArray();
					int resultCount = 0;

					for (ClientBuyHistoryViewBean bean : beansCol) {
						JSONObject idDataObj = new JSONObject();
						idDataObj.put("id", resultCount+1);

						JSONArray dataArr = new JSONArray();
						dataArr.put("N");
						dataArr.put(StringHandler.emptyIfNull(bean.getCompanyId()));
						dataArr.put(StringHandler.emptyIfNull(bean.getCatalogId()));
						dataArr.put(StringHandler.emptyIfNull(bean.getBuyerName()));
						dataArr.put(StringHandler.emptyIfNull(bean.getPo()));
						dataArr.put(StringHandler.emptyIfNull(bean.getFacPartNo()));
						dataArr.put(StringHandler.emptyIfNull(bean.getSupplier()));
						dataArr.put(StringHandler.emptyIfNull(bean.getManufacturer()));
						dataArr.put(StringHandler.emptyIfNull(bean.getMfgPartNo()));
						dataArr.put(StringHandler.emptyIfNull(bean.getQuantity()));
						dataArr.put(StringHandler.emptyIfNull(bean.getUom()));
						dataArr.put(bean.getPoDate() == null || bean.getPoDate().equals("") ? "": dateFormat.format(bean.getPoDate()));
						dataArr.put((bean.getUnitPrice() != null ? bean.getUnitPrice().setScale(4,4):"") + " " + StringHandler.emptyIfNull(bean.getCurrencyId()));						
						dataArr.put(StringHandler.emptyIfNull(bean.getItemId()));
						idDataObj.put("data", dataArr);
					    rowsArr.put(idDataObj);
						resultCount++;
					}					
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
