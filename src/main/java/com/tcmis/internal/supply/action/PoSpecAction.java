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
import com.tcmis.internal.catalog.beans.SpecBean;
import com.tcmis.internal.hub.beans.ReceiptDescriptionViewBean;
import com.tcmis.internal.hub.process.SpecFlowDownProcess;

	public class PoSpecAction extends TcmISBaseAction {
			public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
				
				JSONObject rowsObj = new JSONObject();
				if (!isLoggedIn(request)) {
					rowsObj.put("Message", getResourceBundleValue(request, "label.timeoutmessage1"));
				}
				else
				{
					ReceiptDescriptionViewBean receiptDescriptionViewBean = new ReceiptDescriptionViewBean();
					BeanHandler.copyAttributes(form, receiptDescriptionViewBean);
					SpecFlowDownProcess specFlowDownProcess = new SpecFlowDownProcess(getDbUser(), getTcmISLocale());
					Collection<SpecBean> sendSpecs = specFlowDownProcess.buildSendSpecs(receiptDescriptionViewBean);
					if(sendSpecs == null)
						rowsObj.put("Message", getResourceBundleValue(request, "error.db.select"));
					else
					{
						String flag = request.getParameter("readOnlyGrids");
						boolean readOnlyGrids = (flag != null && flag.equalsIgnoreCase("Y"))?true:false; 
						//log.debug("PoSpecAction  flag = '" + flag + "' and readOnlyGrids = '" + readOnlyGrids + "'");	
						JSONArray rowsArr = new JSONArray();
						int resultCount = 0;
	
						for (SpecBean bean : sendSpecs) {
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
							//dataArr.put((bean.getSavedCocStr() == null || bean.getSavedCocStr().equalsIgnoreCase("") || bean.getSavedCocStr().equalsIgnoreCase("N") ) ? false: true);
							//dataArr.put((bean.getSavedCoaStr() == null || bean.getSavedCoaStr().equalsIgnoreCase("") || bean.getSavedCoaStr().equalsIgnoreCase("N") ) ? false: true);
							dataArr.put( bean.getSavedCoc());
							dataArr.put( bean.getSavedCoa());
							dataArr.put(((StringHandler.isBlankString(bean.getContent()) || StringHandler.emptyIfNull(bean.getOnLine()).equalsIgnoreCase("N"))?"":"<img src=\"/images/buttons/document.gif\"/>") + bean.getSpecIdDisplay());
							dataArr.put(StringHandler.emptyIfNull(bean.getSpecLibraryDesc()));
							dataArr.put(false);
							dataArr.put(false);
							//dataArr.put((bean.getCurrentCocRequirementStr() == null || bean.getCurrentCocRequirementStr().equalsIgnoreCase("") || bean.getCurrentCocRequirementStr().equalsIgnoreCase("N") ) ? false: true);					
							//dataArr.put((bean.getCurrentCoaRequirementStr() == null || bean.getCurrentCoaRequirementStr().equalsIgnoreCase("") || bean.getCurrentCoaRequirementStr().equalsIgnoreCase("N") ) ? false: true);
							dataArr.put(bean.getCurrentCocRequirement());					
							dataArr.put(bean.getCurrentCoaRequirement());
							dataArr.put(false);
							dataArr.put(false);
							//dataArr.put((bean.getCocReqAtSaveStr() == null || bean.getCocReqAtSaveStr().equalsIgnoreCase("") || bean.getCocReqAtSaveStr().equalsIgnoreCase("N") ) ? false: true);					
							//dataArr.put((bean.getCoaReqAtSaveStr() == null || bean.getCoaReqAtSaveStr().equalsIgnoreCase("") || bean.getCoaReqAtSaveStr().equalsIgnoreCase("N") ) ? false: true);
							dataArr.put(bean.getCocReqAtSave());					
							dataArr.put(bean.getCoaReqAtSave());
							dataArr.put(StringHandler.emptyIfNull(bean.getContent()));
							dataArr.put(StringHandler.emptyIfNull(bean.getSpecId()));
							dataArr.put(StringHandler.emptyIfNull(bean.getDetail()));
							dataArr.put(bean.getColor().intValue());
							dataArr.put(StringHandler.emptyIfNull(bean.getSpecLibrary()));
							dataArr.put("N");
							dataArr.put(bean.getOnLine());
							dataArr.put(receiptDescriptionViewBean.getRowNumber());
							idDataObj.put("data", dataArr);
						    rowsArr.put(idDataObj);
							resultCount++;
						}
						rowsObj.put("rows",rowsArr);
						rowsObj.put("poLineNumber",receiptDescriptionViewBean.getRowNumber());
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