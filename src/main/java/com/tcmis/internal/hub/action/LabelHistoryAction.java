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
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.ContainerLabelViewBean;
import com.tcmis.internal.hub.beans.ReceivingInputBean;
import com.tcmis.internal.hub.process.ReceivingQcCheckListProcess;

	public class LabelHistoryAction extends TcmISBaseAction {
			public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
				
				JSONObject results = new JSONObject();
				if (!isLoggedIn(request)) {
					results.put("Message", getResourceBundleValue(request, "label.timeoutmessage1"));
				}
				else
				{	
					ReceivingQcCheckListProcess receivingQcCheckListProcess = new ReceivingQcCheckListProcess(getDbUser(), getTcmISLocale());
					ReceivingInputBean receivingInputBean = new ReceivingInputBean();
					BeanHandler.copyAttributes(form, receivingInputBean);
					Collection<ContainerLabelViewBean> labelHistory = receivingQcCheckListProcess.getContainerLabelResult(receivingInputBean.getSearch());
					if(labelHistory == null)
						results.put("Message", getResourceBundleValue(request, "error.db.select"));
					else
					{
							SimpleDateFormat outformat = new SimpleDateFormat("dd-MMM-yyyy");
							JSONArray resultsArray = new JSONArray();
							for (ContainerLabelViewBean bean : labelHistory) {								          
								JSONObject aJSON = new JSONObject();
								aJSON.put("receiptId", bean.getReceiptId());
								aJSON.put("containerLabelId", bean.getContainerLabelId());
								aJSON.put("materialDesc", bean.getMaterialDesc());
								aJSON.put("mfgLot", bean.getMfgLot());
								aJSON.put("expireDate", (bean.getExpireDate() != null?outformat.format(bean.getExpireDate()):""));
								aJSON.put("lotStatus", bean.getLotStatus());
								aJSON.put("partId", bean.getPartId());
								aJSON.put("partNumbers", bean.getPartNumbers());
								aJSON.put("compMfgLot", bean.getCompMfgLot());
								aJSON.put("compDateInserted", bean.getCompDateInserted());
								aJSON.put("compExpireDate", (bean.getExpireDate() != null ? outformat.format(bean.getExpireDate()):""));
								aJSON.put("specDetail", bean.getSpecDetail());
								aJSON.put("specList", bean.getSpecList());
								aJSON.put("vendorShipDate", (bean.getVendorShipDate() != null ? outformat.format(bean.getVendorShipDate()):""));
								aJSON.put("dateOfReceipt", (bean.getDateOfReceipt()!= null ? outformat.format(bean.getDateOfReceipt()):""));
								aJSON.put("dateOfManufacture", (bean.getDateOfManufacture()!= null ? outformat.format(bean.getDateOfManufacture()):""));
								aJSON.put("dateOfShipment", (bean.getDateOfShipment() != null ? outformat.format(bean.getDateOfShipment()):""));
								aJSON.put("mfgLabelExpireDate", (bean.getMfgLabelExpireDate()!= null ? outformat.format(bean.getMfgLabelExpireDate()):""));
								aJSON.put("storageTemp", bean.getStorageTemp());
								aJSON.put("recertNumber", bean.getRecertNumber());
								aJSON.put("certifiedByName", bean.getCertifiedByName());
								aJSON.put("certificationDate", (bean.getCertificationDate()!= null ? outformat.format(bean.getCertificationDate()):""));
								aJSON.put("qaStatement", bean.getQaStatement());
								aJSON.put("qualityTrackingNumber", bean.getQualityTrackingNumber());
								aJSON.put("quantityPrinted", bean.getQuantityPrinted());
								aJSON.put("lastIdPrinted", bean.getLastIdPrinted());
								aJSON.put("printUserName", bean.getPrintUserName());
								aJSON.put("dateInserted", (bean.getDateInserted()!= null ? outformat.format(bean.getDateInserted()):""));
								aJSON.put("labelColor", bean.getLabelColor());
								aJSON.put("manageKitsAsSingleUnit", bean.getManageKitsAsSingleUnit());

								resultsArray.put(aJSON);
							}
							results.put("labelHistory", resultsArray);
					}
				}
				// Write out the data
				PrintWriter out = response.getWriter();
				out.write(results.toString(3));
				out.close();
				return noForward;
				
			}

	}
