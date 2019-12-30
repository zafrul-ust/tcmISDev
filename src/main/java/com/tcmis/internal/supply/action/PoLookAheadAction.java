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

import com.tcmis.client.catalog.beans.CatalogPartInventoryBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.supply.beans.PurchaseOrderBean;
import com.tcmis.internal.supply.process.PurchaseOrderProcess;

	public class PoLookAheadAction extends TcmISBaseAction {
			public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
				
				JSONObject rowsObj = new JSONObject();
				if (!isLoggedIn(request)) {
					rowsObj.put("Message", getResourceBundleValue(request, "label.timeoutmessage1"));
				}
				else
				{
					PurchaseOrderBean input = new PurchaseOrderBean();
					BeanHandler.copyAttributes(form, input);
					PurchaseOrderProcess process = new PurchaseOrderProcess(getDbUser());
					Collection<CatalogPartInventoryBean> lookAheadResults = process.buildsendLookahead(input);
					if(lookAheadResults == null)
						rowsObj.put("Message", getResourceBundleValue(request, "error.db.select"));
					else
					{
						JSONArray rowsArr = new JSONArray();
						int resultCount = 0;

						for (CatalogPartInventoryBean bean : lookAheadResults) {
							JSONObject idDataObj = new JSONObject();
							idDataObj.put("id", resultCount+1);

							JSONArray dataArr = new JSONArray();
							dataArr.put("Y");
							dataArr.put(StringHandler.emptyIfNull(bean.getCompanyId()));
							dataArr.put(StringHandler.emptyIfNull(bean.getCatalogId()));
							dataArr.put(StringHandler.emptyIfNull(bean.getInventoryGroup()));
							dataArr.put(StringHandler.emptyIfNull(bean.getCatPartNo()));
							dataArr.put(bean.getLookAheadDays()!= null?bean.getLookAheadDays().toPlainString():"");
							dataArr.put(bean.getPartGroupNo()!= null?bean.getPartGroupNo().toPlainString():"");
							dataArr.put("N");
							idDataObj.put("data", dataArr);
						    rowsArr.put(idDataObj);
							resultCount++;
						}
						rowsObj.put("rows",rowsArr);
						rowsObj.put("poLineNumber",input.getRowNumber());
						rowsObj.put("NoOfRows",resultCount);						
					}
				}
				//write out the data
				PrintWriter out = response.getWriter();
				out.write(rowsObj.toString(3));
				out.close();
				return noForward;
			}
}