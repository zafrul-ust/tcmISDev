package com.tcmis.client.common.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.catalog.process.OrderTrackingProcess;
import com.tcmis.client.common.process.CabinetDefinitionManagementProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.CabinetInventoryCountStageBean;
import com.tcmis.internal.hub.beans.CabinetItemInventoryCountBean;
import com.tcmis.internal.hub.beans.ManualCabinetCountInputBean;
import com.tcmis.internal.hub.process.ManualCabinetCountProcess;

public class ClientCabinetCountAction extends TcmISBaseAction {


	@SuppressWarnings("unchecked")
	private void doSearch(HttpServletRequest request, ManualCabinetCountProcess process, ManualCabinetCountInputBean input) throws BaseException {
		Collection results =process.getSearchData(input);
		request.setAttribute("cabinetsCollection", results);
        try {
            Object[] rowSpan = process.getDataRowSpan(results);
		    request.setAttribute("rowCountFirstLevel", rowSpan[0]);
		    request.setAttribute("rowCountSecondLevel", rowSpan[1]);
            request.setAttribute("containAutomaticRefillData", rowSpan[2]);
			request.setAttribute("containDisbursementData",rowSpan[3]);
        }catch (Exception e) {};
        input.setTotalLines(results.size());
	}

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		ActionForward next = mapping.findForward("success");

		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "clientcabinetcountmain");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			next = mapping.findForward("login");
		}
		else {

			PersonnelBean user = (PersonnelBean) this.getSessionObject(request, "personnelBean");
			if (false && !user.getPermissionBean().hasUserPagePermission("cabinetCount")) {
				next = mapping.findForward("nopermissions");
			}
			else {

				ManualCabinetCountInputBean input = new ManualCabinetCountInputBean(form, getTcmISLocale());
				request.setAttribute("input", input);

				ManualCabinetCountProcess process = new ManualCabinetCountProcess(this.getDbUser(request));
				if (input.isUpdate()) {
					checkToken(request);
					Collection beans = BeanHandler.getBeans((DynaBean) form, "cabinetBinItemBean", new CabinetItemInventoryCountBean(),this.getTcmISLocale(request));
					StringBuffer requestURL = request.getRequestURL();
					String emailUrl = requestURL.substring(0,requestURL.lastIndexOf("/")) + "/customermrtracking.do";
					String msg = process.insertCabinetItemInventoryCounts(beans, user.getPersonnelIdBigDecimal(), emailUrl);

					if (!StringHandler.isBlankString(msg)) {
						request.setAttribute("tcmISError", msg);
					}
					else {
						request.setAttribute("updateSuccess", "Y");
					}
					doSearch(request, process, input);
				}
				else if (input.isCreateExcel()) {
					try {
						this.setExcel(response, "ClientCabinetCount");
						process.setCalledFromNewPage(true); 
						process.getExcelReport(input, user.getLocale()).write(response.getOutputStream());
						next = noForward;
					}
					catch (Exception ex) {
						ex.printStackTrace();
						next = mapping.findForward("genericerrorpage");
					}
				}
				else if (input.isSearch()) {
					doSearch(request, process, input);
					this.saveTcmISToken(request);
				}
				else if("loadReceiptCount".equals(input.getAction())){
					try{
                        String binId = request.getParameter("binId");
						String receiptId = request.getParameter("receiptId");
						String receiptQty =  request.getParameter("receiptQty");
						String receiptDamaged =  request.getParameter("receiptDamagedQty");
						String receiptExpired =  request.getParameter("receiptExpiredQty");
						
						if(receiptId != null) {
							request.setAttribute("receiptCollection",process.getReceipts(binId, receiptId, receiptQty, receiptDamaged, receiptExpired));
						}
						else {
							request.setAttribute("receiptCollection",process.getReceipts(binId));
						}
					}
					catch(Exception ex){
						ex.printStackTrace();
						next = mapping.findForward("genericerrorpage");
					}
				}
				else if("setReceiptCount".equals(input.getAction())){
					Collection beans = BeanHandler.getBeans((DynaBean) form, "cabinetInventoryCountBean", new CabinetInventoryCountStageBean());
					String itemId = request.getParameter("itemId");
					
					String error = process.validateNewReceipts(beans, itemId);
					
					request.setAttribute("receiptCollection", beans);
					request.setAttribute("tcmisError",error);
				}
				else {
					//populate drop downs
			        if (user.getFacilityAreaBuildingFloorRoomColl() == null || user.getFacilityAreaBuildingFloorRoomColl().size() == 0) {
                        CabinetDefinitionManagementProcess cabinetDefinitionManagementProcess = new CabinetDefinitionManagementProcess(this.getDbUser(request), getTcmISLocaleString(request));
                        Collection facilityCollection = cabinetDefinitionManagementProcess.getFacilityAreaBldgRm(user);
			            user.setFacilityAreaBuildingFloorRoomColl(facilityCollection);
			            request.setAttribute("facAppReportViewBeanCollection", facilityCollection);
			        }else {
			            request.setAttribute("facAppReportViewBeanCollection",user.getFacilityAreaBuildingFloorRoomColl());
			        }
			        
					//get default facilityId
					if(user.getMyCompanyDefaultFacilityIdCollection() == null || user.getMyCompanyDefaultFacilityIdCollection().size() == 0) {
						OrderTrackingProcess orderTrackingProcess = new OrderTrackingProcess(getDbUser(request), getTcmISLocaleString(request));
						user.setMyCompanyDefaultFacilityIdCollection(orderTrackingProcess.getMyCompanpyDefaultFacility(user.getPersonnelId()));
					}
					
				}
			}
		}
		return next;
	}

}