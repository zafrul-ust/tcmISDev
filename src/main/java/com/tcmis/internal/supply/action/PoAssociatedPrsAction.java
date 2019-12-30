package com.tcmis.internal.supply.action;
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

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.supply.beans.InternalAssociatedPrsBean;
import com.tcmis.internal.supply.beans.PurchaseOrderBean;
import com.tcmis.internal.supply.process.PoPageProcess;

	public class PoAssociatedPrsAction extends TcmISBaseAction {
			public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
				
				JSONObject rowsObj = new JSONObject();
				if (!isLoggedIn(request)) {
					rowsObj.put("Message", getResourceBundleValue(request, "label.timeoutmessage1"));
				}
				else
				{
					PoPageProcess poPageProcess = new PoPageProcess(getDbUser());
					PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
					PurchaseOrderBean input = new PurchaseOrderBean();
					BeanHandler.copyAttributes(form, input);
					Collection<InternalAssociatedPrsBean> associatedPrsCol = poPageProcess.getAssociatedPrCol(input, personnelBean);

					if(associatedPrsCol == null)
						rowsObj.put("Message", getResourceBundleValue(request, "error.db.select"));
					else
					{
						JSONArray rowsArr = new JSONArray();
						int resultCount = 0;
						ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", getTcmISLocale());
						SimpleDateFormat dateFormat = new SimpleDateFormat(library.getString("java.dateformat"));
						BigDecimal totalItemQty = new BigDecimal(0);
						for (InternalAssociatedPrsBean bean : associatedPrsCol) {
							if (input.getItemId() != null && (input.getItemId().compareTo(bean.getItemId()) == 0)) {								
								JSONObject idDataObj = new JSONObject();
								idDataObj.put("id", resultCount+1);
								
								JSONArray dataArr = new JSONArray();
								dataArr.put("N");
								dataArr.put(StringHandler.emptyIfNull(bean.getPrNumber()));
								if (bean.getMrLineItem() != null && !bean.getMrLineItem().equals(""))
									dataArr.put(StringHandler.emptyIfNull(bean.getMrNumber()) + " - " + StringHandler.emptyIfNull(bean.getMrLineItem()));
								else 
									dataArr.put(StringHandler.emptyIfNull(bean.getMrNumber()));
								dataArr.put(StringHandler.emptyIfNull(bean.getPartId()));
								if (bean.getStocked() != null && "Y".equalsIgnoreCase( bean.getStocked()))
									dataArr.put(StringHandler.emptyIfNull(bean.getSpecList()) + "</br>(" + library.getLabel("label.stocked") +")");
								else   
									dataArr.put(StringHandler.emptyIfNull(bean.getSpecList()));							
								dataArr.put(StringHandler.emptyIfNull(bean.getItemType()));
								dataArr.put(StringHandler.emptyIfNull(bean.getLpp()));
								dataArr.put(StringHandler.emptyIfNull(bean.getNotes()));
								dataArr.put(StringHandler.emptyIfNull(bean.getLinePurchasingNote()));
								dataArr.put(StringHandler.emptyIfNull(bean.getComments()));
								dataArr.put(StringHandler.emptyIfNull(bean.getRequestorFirstName()) + " " +
											StringHandler.emptyIfNull(bean.getRequestorLastName()) + " " +
											StringHandler.emptyIfNull(bean.getEmail()) + " " +
											StringHandler.emptyIfNull(bean.getPhone()));
								dataArr.put(StringHandler.emptyIfNull(bean.getCsrName()));
								dataArr.put(StringHandler.emptyIfNull(bean.getMrQuantity()));
								dataArr.put(StringHandler.emptyIfNull(bean.getOrderQuantity()));
								if (bean.getOrderQuantity() != null)
									totalItemQty = totalItemQty.add(bean.getOrderQuantity());
								dataArr.put(StringHandler.emptyIfNull(bean.getSizeUnit()));							
								dataArr.put(StringHandler.emptyIfNull(bean.getNeedDate() == null ? "" : dateFormat.format(bean.getNeedDate())));
								dataArr.put(StringHandler.emptyIfNull(bean.getShipToLocationId()));
								dataArr.put(StringHandler.emptyIfNull(bean.getFacility()));
								dataArr.put(StringHandler.emptyIfNull(bean.getShipToDeliveryPoint()));
								dataArr.put(StringHandler.emptyIfNull(bean.getBranchPlant()) + " " + 
										    StringHandler.emptyIfNull(bean.getInventoryGroup()));
								dataArr.put(StringHandler.emptyIfNull(bean.getRaytheonPo()));
								dataArr.put(StringHandler.emptyIfNull(bean.getStockingLevel()));
								dataArr.put(StringHandler.emptyIfNull(bean.getReorderPoint()));
								dataArr.put(StringHandler.emptyIfNull(bean.getAvailableQuantity()));
								dataArr.put(StringHandler.emptyIfNull(bean.getOpenPoQuantity()));
								dataArr.put(StringHandler.emptyIfNull(bean.getRemainingShelfLifePercent()));
								dataArr.put(StringHandler.emptyIfNull(bean.getRequestId()));
								dataArr.put(StringHandler.emptyIfNull(bean.getCatalogId()));
								dataArr.put(StringHandler.emptyIfNull(bean.getDeliveryType()));
								dataArr.put(StringHandler.emptyIfNull(bean.getMrLineItem()));
								dataArr.put(StringHandler.emptyIfNull(bean.getCompanyId()));
								dataArr.put(StringHandler.emptyIfNull(bean.getMrNumber()));
								idDataObj.put("data", dataArr);
							    rowsArr.put(idDataObj);
								resultCount++;
							}
						}
						rowsObj.put("rows",rowsArr);
						rowsObj.put("poLineNumber",input.getRowNumber());
						rowsObj.put("NoOfRows",resultCount);
						rowsObj.put("totalItemQty",totalItemQty);
						PrintWriter out = response.getWriter();
						out.write(rowsObj.toString(3));
						out.close();
						return noForward;	
					}
				}
				//write out the data
				PrintWriter out = response.getWriter();
				out.write(rowsObj.toString(3));
				out.close();
				return noForward;
			}
}