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
import com.tcmis.internal.supply.beans.PoViewBean;
import com.tcmis.internal.supply.beans.PurchaseOrderBean;
import com.tcmis.internal.supply.process.PoPageProcess;

public class PoTcmBuysAction extends TcmISBaseAction {
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
				Collection<PoViewBean> beansCol = poPageProcess.getTcmBuysCol(input);
				
				if(beansCol == null)
					rowsObj.put("Message", getResourceBundleValue(request, "error.db.select"));
				else
				{
					ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", getTcmISLocale());
					SimpleDateFormat dateFormat = new SimpleDateFormat(library.getString("java.dateformat"));
					SimpleDateFormat dateTimeFormatPattern = new SimpleDateFormat(library.getString("java.datetimeformat"));
					JSONArray rowsArr = new JSONArray();
					int resultCount = 0;

					for (PoViewBean bean : beansCol) {
						JSONObject idDataObj = new JSONObject();
						idDataObj.put("id", resultCount+1);
						if (!StringHandler.isBlankString(input.getShipToLocationId()) && input.getShipToLocationId().equals(bean.getShipToLocationId()))
							idDataObj.put("class", "grid_green");

						JSONArray dataArr = new JSONArray();
						dataArr.put("N");
						dataArr.put(StringHandler.emptyIfNull(bean.getBuyerName()));
						dataArr.put(StringHandler.emptyIfNull(bean.getRadianPo()));
						dataArr.put(StringHandler.emptyIfNull(bean.getCustomerPo()));
						dataArr.put(StringHandler.emptyIfNull(bean.getShipToLocationId()));
						dataArr.put(StringHandler.emptyIfNull(bean.getCarrier()));
						dataArr.put(StringHandler.emptyIfNull(bean.getSupplierName()));
						dataArr.put(StringHandler.emptyIfNull(bean.getSupplierContactName()));
						dataArr.put(StringHandler.emptyIfNull(bean.getPhone()));
						dataArr.put(StringHandler.emptyIfNull(bean.getQuantity()));
						dataArr.put(StringHandler.emptyIfNull(bean.getQuantityReceived()));
						dataArr.put((bean.getUnitPrice() != null ? bean.getUnitPrice().setScale(4,4):"") + " " + StringHandler.emptyIfNull(bean.getCurrencyId()));				
						dataArr.put(bean.getDateConfirmed()== null || bean.getDateConfirmed().equals("")? "": dateTimeFormatPattern.format(bean.getDateConfirmed()));
						dataArr.put(bean.getOriginalDateConfirmed()== null || bean.getOriginalDateConfirmed().equals("")? "": dateTimeFormatPattern.format(bean.getOriginalDateConfirmed()));
						dataArr.put(bean.getFirstTimeReceived()== null || bean.getFirstTimeReceived().equals("")? "": dateFormat.format(bean.getFirstTimeReceived()));
						
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
