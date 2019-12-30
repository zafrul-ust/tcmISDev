package com.tcmis.client.common.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.DropDownListBean;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.client.catalog.process.OrderTrackingProcess;
import com.tcmis.client.catalog.process.CatalogProcess;
import com.tcmis.client.catalog.beans.CatalogInputBean;
import com.tcmis.client.catalog.beans.PointOfSaleAccountSysInfoBean;
import com.tcmis.client.common.process.CabinetDefinitionManagementProcess;
import com.tcmis.client.common.process.CountTypeProcess;
import com.tcmis.client.common.beans.CabinetManagementBean;
import com.tcmis.client.common.beans.WorkAreaSearchTemplateInputBean;


/**
 * ***************************************************************************
 * Controller for receiving
 *
 * @version 1.0
 *          ****************************************************************************
 */
public class ClientCabinetManagementAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "clientcabinetmanagementmain");
			// This is to save any parameters passed in the URL, so that they
			// can be acccesed after the login
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

        String uAction = (String) ((DynaBean) form).get("uAction");
        PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");

        if (!StringHandler.isBlankString(uAction)) {
            if (uAction.indexOf("DirectedCharge") < 0) {
                if (!personnelBean.getPermissionBean().hasUserPagePermission("clientCabinetManagement"))
                {
                   return (mapping.findForward("nopermissions"));
                }
            }
        }else {
            if (!personnelBean.getPermissionBean().hasUserPagePermission("clientCabinetManagement")) {
	            return (mapping.findForward("nopermissions"));
	        }
        }

        if (form != null) {
			WorkAreaSearchTemplateInputBean bean = new WorkAreaSearchTemplateInputBean();
			BeanHandler.copyAttributes(form, bean, getTcmISLocale(request));

			CabinetDefinitionManagementProcess process = new CabinetDefinitionManagementProcess(this.getDbUser(request), getTcmISLocaleString(request));
			if ("addPart".equalsIgnoreCase(uAction)) {
				request.setAttribute("vvTierIITemperatureCodeBeanColl",process.getVvTierIITemperatureCodeBeanColl());	
				return (mapping.findForward("newbin"));
			}
			else if ("addNonmanagedMatlWorkAreaSelect".equalsIgnoreCase(uAction)) {
        		request.setAttribute("authorizedUserWorkAreasColl",process.getAuthorizedUserWorkAreasForNonManangedMatl(personnelBean,request.getParameter("facilityId")));
				return (mapping.findForward("success"));
			}
			else if("nonManagedMatCountCheck".equalsIgnoreCase(uAction))
			{
				request.setAttribute("count",process.getNonManagedMatCount(request.getParameter("companyId"),request.getParameter("facilityId"),request.getParameter("application"),request.getParameter("materialId")));
				return (mapping.findForward("countCheck"));	
			}
			else if("nonManMatlHist".equalsIgnoreCase(uAction))
			{
				request.setAttribute("nonManMatlHistResults",process.getNonManMatlHist(request.getParameter("companyId"),request.getParameter("facilityId"),request.getParameter("application"),request.getParameter("materialId"),request.getParameter("catalogCompanyId"),request.getParameter("catalogId")));
				return (mapping.findForward("clientcabinetmanagementhistory"));  
			}
            else if("viewPartHistory".equalsIgnoreCase(uAction))
			{
                CabinetManagementBean inputBean = new CabinetManagementBean();
                BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
                request.setAttribute("partHistoryResults",process.getPartHistory(inputBean));
				return (mapping.findForward("viewparthistory"));
			}
            else if ("search".equalsIgnoreCase(uAction)) {
				this.saveTcmISToken(request);
				Collection dataColl = process.getClientSearchData(bean);
				Object[] results = process.getCabinetManagementRowSpan(dataColl);
				request.setAttribute("rowCountFirstLevel", results[0]);
				request.setAttribute("rowCountSecondLevel", results[1]);
				CatalogProcess catalogProcess = new CatalogProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
				CatalogInputBean tmpBean = new CatalogInputBean();
				tmpBean.setFacilityId(bean.getFacilityId());
				request.setAttribute("prRulesViewCollection",catalogProcess.getPrRulesForFacility(tmpBean));

				String showDefault = process.getShowDefault(personnelBean,bean);
				request.setAttribute("showDefault",showDefault);
				if("Y".equals(showDefault)) {
					process.setDropdownsforGrid(getDbUser(), getTcmISLocale(), dataColl);
				}
				
				setCommonAttributes(dataColl, process, bean, personnelBean);
			
                return mapping.findForward("results");
			} else if ("update".equalsIgnoreCase(uAction)) {
				checkToken(request);
		
				Collection<CabinetManagementBean> c = BeanHandler.getBeans((DynaBean) form, "cabinetPartLevelViewBean", new CabinetManagementBean(), getTcmISLocale(request));
				String errMsg = process.updateCabinetBinPartInventory(c, personnelBean);
				request.setAttribute("tcmISError", errMsg);
				Collection dataColl = process.getClientSearchData(bean);
				Object[] results = process.getCabinetManagementRowSpan(dataColl);
				request.setAttribute("rowCountFirstLevel", results[0]);
				request.setAttribute("rowCountSecondLevel", results[1]);
				CatalogProcess catalogProcess = new CatalogProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
				CatalogInputBean tmpBean = new CatalogInputBean();
				tmpBean.setFacilityId(bean.getFacilityId());
				request.setAttribute("prRulesViewCollection",catalogProcess.getPrRulesForFacility(tmpBean));
				
				String showDefault = process.getShowDefault(personnelBean,bean);
				request.setAttribute("showDefault",showDefault);
				if("Y".equals(showDefault)) {
					process.setDropdownsforGrid(getDbUser(), getTcmISLocale(), dataColl);
				}
				
				setCommonAttributes(dataColl, process, bean, personnelBean);
				
                return mapping.findForward("results");
			} else if ("insertNewRow".equalsIgnoreCase(uAction)) {
				checkToken(request);
				CabinetManagementBean nonManagedBean = new CabinetManagementBean();
				BeanHandler.copyAttributes(form, nonManagedBean, getTcmISLocale(request));
				//PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");
				String errMsg = process.insertNonManagedMaterial(nonManagedBean, personnelBean);
				request.setAttribute("tcmISError", errMsg);
				Collection dataColl = process.getClientSearchData(bean);
				Object[] results = process.getCabinetManagementRowSpan(dataColl);
				request.setAttribute("rowCountFirstLevel", results[0]);
				request.setAttribute("rowCountSecondLevel", results[1]);
				CatalogProcess catalogProcess = new CatalogProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
				CatalogInputBean tmpBean = new CatalogInputBean();
				tmpBean.setFacilityId(bean.getFacilityId());
				request.setAttribute("prRulesViewCollection",catalogProcess.getPrRulesForFacility(tmpBean));
				
				String showDefault = process.getShowDefault(personnelBean,bean);
				request.setAttribute("showDefault",showDefault);
				if("Y".equals(showDefault)) {
					process.setDropdownsforGrid(getDbUser(), getTcmISLocale(), dataColl);
				}
				
				setCommonAttributes(dataColl, process, bean, personnelBean);
				
                return mapping.findForward("results");
			} else if ("deletRow".equalsIgnoreCase(uAction)) {
				checkToken(request);
				CabinetManagementBean nonManagedBean = new CabinetManagementBean();
				BeanHandler.copyAttributes(form, nonManagedBean, getTcmISLocale(request));
				String errMsg = process.deleteNonManagedMaterial(nonManagedBean);
				request.setAttribute("tcmISError", errMsg);
				Collection dataColl = process.getClientSearchData(bean);
				Object[] results = process.getCabinetManagementRowSpan(dataColl);
				request.setAttribute("rowCountFirstLevel", results[0]);
				request.setAttribute("rowCountSecondLevel", results[1]);
				CatalogProcess catalogProcess = new CatalogProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
				CatalogInputBean tmpBean = new CatalogInputBean();
				tmpBean.setFacilityId(bean.getFacilityId());
				request.setAttribute("prRulesViewCollection",catalogProcess.getPrRulesForFacility(tmpBean));
				
				String showDefault = process.getShowDefault(personnelBean,bean);
				request.setAttribute("showDefault",showDefault);
				if("Y".equals(showDefault)) {
					process.setDropdownsforGrid(getDbUser(), getTcmISLocale(), dataColl);
				}
				
				setCommonAttributes(dataColl, process, bean, personnelBean);
				
                return mapping.findForward("results");
			} else if ("createExcel".equalsIgnoreCase(uAction)) {
				try {
					bean.setShowDefault(process.getShowDefault(personnelBean,bean));
					this.setExcel(response, "CabinetManagement");
                    this.overrideMaxData(request);
                    process.getClientCabinetManagementExcelReport(bean, personnelBean, (java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
				} catch (Exception ex) {
					ex.printStackTrace();
					return mapping.findForward("genericerrorpage");
				}
				return noForward;
            } else if ("createTemplateData".equalsIgnoreCase(uAction)) {
				try {
					bean.setShowDefault(process.getShowDefault(personnelBean,bean));
					this.setExcel(response, "CabinetManagement");
                    this.overrideMaxData(request);
                    process.createTemplateData(personnelBean,bean,(java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
				} catch (Exception ex) {
					ex.printStackTrace();
					return mapping.findForward("genericerrorpage");
				}
				return noForward;
            }else if ("editCabinetDirectedCharge".equalsIgnoreCase(uAction)) {
				CatalogProcess catalogProcess = new CatalogProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
				CatalogInputBean tmpBean = new CatalogInputBean();
				BeanHandler.copyAttributes(form, tmpBean, this.getTcmISLocale(request));
				PointOfSaleAccountSysInfoBean posBean = catalogProcess.getPosAccountSysChargeNumberPoData(tmpBean);
				process.getDirectedChargeInfo(posBean,tmpBean);
				request.setAttribute("accountSysChargeNumberPoData",posBean);
				return mapping.findForward("loadChargeInfo");
			} else if ("setWorkAreaCabinetPartDirectedCharge".equalsIgnoreCase(uAction)) {
				CatalogProcess catalogProcess = new CatalogProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
				CatalogInputBean tmpBean = new CatalogInputBean();
				BeanHandler.copyAttributes(form, tmpBean, this.getTcmISLocale(request));
                //handling data with both charge type
                String[] tmpChargeType = tmpBean.getChargeType().split(",");
                String[] tmpChargeNumber = tmpBean.getChargeNumbers().split("!txtx!");
                String failedChargeType = "";
                String failedChargeNumber = "";
                for (int i = 0; i < tmpChargeType.length; i++) {
                    String errorVal = "OK";
                    tmpBean.setChargeType(tmpChargeType[i]);
                    tmpBean.setChargeNumbers(tmpChargeNumber[i]);
                    if ("true".equalsIgnoreCase(tmpBean.getUserEnteredChargeNumber()) && !StringHandler.isBlankString(tmpBean.getChargeNumbers())) {
                        errorVal = catalogProcess.validateChargeNumbers(tmpBean);
                    }

                    if ("OK".equalsIgnoreCase(errorVal)) {
                        process.setWorkAreaCabinetPartDirectedCharge(tmpBean);
                    }else {
                        if (failedChargeType.length() == 0) {
                            failedChargeType = tmpChargeType[i];
                            failedChargeNumber = tmpChargeNumber[i];
                        }else {
                            failedChargeType += ","+tmpChargeType[i];
                            failedChargeNumber += "!txtx!"+tmpChargeNumber[i];    
                        }
                    }
                }  //end of each charge type
                //check to see if everything ok
                if (failedChargeType.length() == 0) {
                    request.setAttribute("chargeNumbersValid","OK");
                }else {
                    request.setAttribute("chargeType",failedChargeType);
                    request.setAttribute("chargeNumber",failedChargeNumber);
                    request.setAttribute("chargeNumbersValid","Failed");
                }
                return mapping.findForward("loadChargeNumberValidator");
			} else if ("addWorkAreaCabinetPartDirectedCharge".equalsIgnoreCase(uAction)) {
				CatalogInputBean tmpBean = new CatalogInputBean();
				BeanHandler.copyAttributes(form, tmpBean, this.getTcmISLocale(request));
				//PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");
                //handling data with both charge type
                String[] tmpChargeType = tmpBean.getChargeType().split(",");
                String[] tmpChargeNumber = tmpBean.getChargeNumbers().split("!txtx!");
                boolean failed = false;
                for (int i = 0; i < tmpChargeType.length; i++) {
                    tmpBean.setChargeType(tmpChargeType[i]);
                    tmpBean.setChargeNumbers(tmpChargeNumber[i]);
                    String errorVal = process.addNewChargeNumber(tmpBean,personnelBean);
                    if ("OK".equalsIgnoreCase(errorVal)) {
                        errorVal = process.setWorkAreaCabinetPartDirectedCharge(tmpBean);
                        if (!"OK".equalsIgnoreCase(errorVal)) {
                            failed = true;
                        }
                    }else {
                        failed = true;
                    }
                } //end of each charge type
				if (failed) {
					request.setAttribute("chargeNumbersValid","Failed");
				}else {
					request.setAttribute("chargeNumbersValid","OK");
				}

				return mapping.findForward("loadChargeNumberValidator");
			} else if ("deleteDirectedCharge".equalsIgnoreCase(uAction)) {
				CatalogInputBean tmpBean = new CatalogInputBean();
				BeanHandler.copyAttributes(form, tmpBean, this.getTcmISLocale(request));
				request.setAttribute("chargeNumbersValid",process.deleteDirectedCharge(tmpBean));
				return mapping.findForward("loadChargeNumberValidator");
            } else if ("checkDirectedChargeForWorkArea".equalsIgnoreCase(uAction)) {
				CatalogInputBean tmpBean = new CatalogInputBean();
				BeanHandler.copyAttributes(form, tmpBean, this.getTcmISLocale(request));
                request.setAttribute("chargeNumbersValid",process.workAreaHasDirectedCharge(tmpBean));
				return mapping.findForward("loadChargeNumberValidator");
            } else if ("getSizeUnitString".equalsIgnoreCase(uAction)) {
				String materialId = request.getParameter("materialId");
				request.setAttribute("sizeUnitString",process.getSizeUnitString(materialId));
				request.setAttribute("vvUnidocsStorageContainerBeanColl",process.getVvUnidocsStorageContainerBeanColl());
				request.setAttribute("vvTierIITemperatureCodeBeanColl",process.getVvTierIITemperatureCodeBeanColl());
				return mapping.findForward("success"); 
		  	} else if ("loadChangeOwnership".equalsIgnoreCase(uAction)) {
		  		CabinetManagementBean cabinetPartLevelViewBean = new CabinetManagementBean();
				BeanHandler.copyAttributes(form, cabinetPartLevelViewBean, getTcmISLocale(request));
				
		  		// check permission
		  		if(personnelBean.isFeatureReleased("HomeCompanyOwned", cabinetPartLevelViewBean.getFacilityId(),cabinetPartLevelViewBean.getCompanyId())
		  			&& personnelBean.isOperatingCompanyEmployee() 
		  			&& personnelBean.getPermissionBean().hasFacilityPermission("HomeCompanyOwned", cabinetPartLevelViewBean.getFacilityId(), cabinetPartLevelViewBean.getCompanyId())){
		  		
		  			request.setAttribute("cabinetPartLevelViewBean",cabinetPartLevelViewBean);
					return mapping.findForward("loadChangeOwnership");
		  		}
		  		else{
		  			return (mapping.findForward("nopermissions"));
		  		}
			} else if ("setChangeOwnership".equalsIgnoreCase(uAction)) {
		  		CabinetManagementBean cabinetPartLevelViewBean = new CabinetManagementBean();
				BeanHandler.copyAttributes(form, cabinetPartLevelViewBean, getTcmISLocale(request));
				
				String error = process.changeOwnership(cabinetPartLevelViewBean, personnelBean);
				
				request.setAttribute("cabinetPartLevelViewBean",cabinetPartLevelViewBean);
				request.setAttribute("tcmisError",error);
				return mapping.findForward("loadChangeOwnership");
			} else if ("loadChangeBinName".equalsIgnoreCase(uAction)) {
				return (mapping.findForward("loadChangeBinName"));
			} else if ("setChangeBinName".equalsIgnoreCase(uAction)) {
		  		CabinetManagementBean cabinetPartLevelViewBean = new CabinetManagementBean();
				BeanHandler.copyAttributes(form, cabinetPartLevelViewBean, getTcmISLocale(request));
				
				String error = process.updateCabinetPartInventory(cabinetPartLevelViewBean, personnelBean);
				
				request.setAttribute("cabinetPartLevelViewBean",cabinetPartLevelViewBean);
				request.setAttribute("tcmisError",error);
				return mapping.findForward("loadChangeBinName");
			}    
            else {
		        if (personnelBean.getFacilityAreaBuildingFloorRoomColl() == null || personnelBean.getFacilityAreaBuildingFloorRoomColl().size() == 0) {
		            Collection facilityCollection = process.getFacilityAreaBldgRm(personnelBean);
		            personnelBean.setFacilityAreaBuildingFloorRoomColl(facilityCollection);
		            request.setAttribute("facAppReportViewBeanCollection", facilityCollection);
		        }else {
		            request.setAttribute("facAppReportViewBeanCollection",personnelBean.getFacilityAreaBuildingFloorRoomColl());
		        }
		        
				//get default facilityId
				if(personnelBean.getMyCompanyDefaultFacilityIdCollection() == null || personnelBean.getMyCompanyDefaultFacilityIdCollection().size() == 0) {
					OrderTrackingProcess orderTrackingProcess = new OrderTrackingProcess(getDbUser(request), getTcmISLocaleString(request));
					personnelBean.setMyCompanyDefaultFacilityIdCollection(orderTrackingProcess.getMyCompanpyDefaultFacility(personnelBean.getPersonnelId()));
				}
				//get user group
			    request.setAttribute("authorizedUsersForUsergroup", process.getAuthorizedUsersForUsergroup(personnelBean, "uploadPartLevel"));
                // count type dropdown
				getCountType();
				return mapping.findForward("success");
			}
		}
		return mapping.findForward("success");
	}

    private void getCountType() {
        try {
            CountTypeProcess countTypeProcess = new CountTypeProcess(this.getDbUser(request), getTcmISLocaleString(request));
            request.setAttribute("countTypeDropDownList", countTypeProcess.getCountType("ClientCabinetManagementAction"));
        }catch(Exception e) {
            e.printStackTrace();
            request.setAttribute("countTypeDropDownList",  new DropDownListBean[0]);
        }
    }

    private void setCommonAttributes(Collection dataColl,CabinetDefinitionManagementProcess process, WorkAreaSearchTemplateInputBean bean, PersonnelBean personnelBean) throws BaseException, Exception {
		// count type dropdown
	    getCountType();
        request.setAttribute("cabinetPartLevelViewBeanCollection",dataColl);
        request.setAttribute("vvUnidocsStorageContainerBeanColl",process.getVvUnidocsStorageContainerBeanColl());
		request.setAttribute("vvTierIITemperatureCodeBeanColl",process.getVvTierIITemperatureCodeBeanColl());
        request.setAttribute("hasStockPartMgmtPermission",process.checkUserPermissionByWorkArea(bean,personnelBean,"StockedPartMgmt"));
        request.setAttribute("hasNonStockPartMgmtPermission",process.checkUserPermissionByWorkArea(bean,personnelBean,"NonStockedPartMgmt"));
	}
}
