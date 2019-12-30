package com.tcmis.internal.supply.action;
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

import com.tcmis.client.catalog.beans.FlowDownBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.ReceiptDescriptionViewBean;
import com.tcmis.internal.hub.process.SpecFlowDownProcess;

	public class PoFlowDownsAction extends TcmISBaseAction {
			public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
				
				JSONObject rowsObj = new JSONObject();
				if (!isLoggedIn(request)) {
					rowsObj.put("Message", getResourceBundleValue(request, "label.timeoutmessage1"));
				}
				else
				{

					ReceiptDescriptionViewBean receiptDescriptionViewBean = new ReceiptDescriptionViewBean();
					BeanHandler.copyAttributes(form, receiptDescriptionViewBean);
					BigDecimal workingPoLineRowNumber = new BigDecimal(0);
					workingPoLineRowNumber = new BigDecimal(receiptDescriptionViewBean.getRowNumber());
					SpecFlowDownProcess specFlowDownProcess = new SpecFlowDownProcess(getDbUser(), getTcmISLocale());
					Collection<FlowDownBean> sendFlowdown = specFlowDownProcess.buildSendFlowdowns(receiptDescriptionViewBean);
					JSONArray rowsArr = new JSONArray();
					int resultCount = 0;
					if(sendFlowdown == null)
						rowsObj.put("Message", getResourceBundleValue(request, "error.db.select"));
					else
					{
						String flag = request.getParameter("readOnlyGrids");
						boolean readOnlyGrids = (flag != null && flag.equalsIgnoreCase("Y"))?true:false; 
						log.debug("flow downs flag = '" + flag + "' and readOnlyGrids = '" + readOnlyGrids + "'");
						for (FlowDownBean bean : sendFlowdown) {
							if(bean.getFlowDownType() != null && bean.getFlowDownType().equalsIgnoreCase("receiving"))
					    		  continue;
							
							JSONObject idDataObj = new JSONObject();
							idDataObj.put("id", resultCount+1);
							String color = "";
							switch(bean.getColor().intValue())
							{
								case 1:
									color = "grid_green";
								break;
								case 2:
									color = "grid_yellow";
								break;
								case 3:
									color = "grid_orange";
								break;
								case 4:
									color = "grid_red";
								break;
								case 5:
									color = "grid_purple";
								break;
								default:
									color = "grid_white";
								break;
							}
							idDataObj.put("class", color);
	
							JSONArray dataArr = new JSONArray();
							if (readOnlyGrids)
								dataArr.put("N");
							else 
								dataArr.put("Y");
							dataArr.put((bean.getOk() == null || bean.getOk().equalsIgnoreCase("") || bean.getOk().equalsIgnoreCase("N") ) ? false: true);				
							dataArr.put(((StringHandler.isBlankString(bean.getContent()) || StringHandler.emptyIfNull(bean.getOnLine()).equalsIgnoreCase("N"))?"":"<img src=\"/images/buttons/document.gif\"/>") + bean.getFlowDown());
							dataArr.put(bean.getRevisionDate() == null?"":bean.getRevisionDate());
							dataArr.put(StringHandler.emptyIfNull(bean.getFlowDownDesc()));
							dataArr.put(StringHandler.emptyIfNull(bean.getCatalogId()));
							dataArr.put(StringHandler.emptyIfNull(bean.getCompanyId()));
							dataArr.put(StringHandler.emptyIfNull(bean.getFlowDownType()));
							dataArr.put(StringHandler.emptyIfNull(bean.getFlowDown()));
							dataArr.put(StringHandler.emptyIfNull(bean.getOnLine()));
							dataArr.put(StringHandler.emptyIfNull(bean.getContent()));						
							dataArr.put("N");
							idDataObj.put("data", dataArr);
						    rowsArr.put(idDataObj);
							resultCount++;	
						}
						rowsObj.put("rows",rowsArr);
						rowsObj.put("poLineNumber",workingPoLineRowNumber);
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
