package com.tcmis.client.common.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Vector;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.catalog.beans.CatalogInputBean;
import com.tcmis.client.catalog.beans.PrCatalogScreenSearchBean;
import com.tcmis.client.catalog.beans.CatalogFacilityBean;
import com.tcmis.client.catalog.process.*;
import com.tcmis.client.common.process.ItemCatalogProcess;
import com.tcmis.client.common.process.MsdsViewerProcess;
import com.tcmis.client.common.process.ShoppingCartProcess;
import com.tcmis.client.common.beans.MsdsViewerInputBean;
import com.tcmis.client.common.beans.MsdsSearchDataTblBean;
import com.tcmis.client.common.beans.ShoppingCartBean;
import com.tcmis.client.pnnl.process.CxmlProcess;
import com.tcmis.client.seagate.beans.MrNeedingApprovalViewBean;
import com.tcmis.client.seagate.process.AribaRepairProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;
import sun.misc.BASE64Encoder;

/**
 * ***************************************************************************
 * Controller for receiving
 *
 * @version 1.0
 *          ****************************************************************************
 */
public class CatalogAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		PersonnelBean user = null;
		String uAction = request.getParameter("uAction");
		if (uAction == null) {
			uAction = "init";
		}
		BigDecimal personnelId = null;
		OrderTrackingProcess orderTrackingProcess = null;
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "catalogmain");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		user = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		if (!user.getPermissionBean().hasUserPagePermission("newcatalog")) {
			return (mapping.findForward("nopermissions"));
		}
		
		
		personnelId = new BigDecimal(user.getPersonnelId());
		//copy date from dyna form to the data form
		CatalogInputBean bean = new CatalogInputBean();
		BeanHandler.copyAttributes(form, bean, this.getTcmISLocale(request));
		CatalogProcess catalogProcess = new CatalogProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
		if (uAction.equals("search")) {
            request.setAttribute("isNewCatalogAddProcessReadyForFacility",user.isFeatureReleased("NewCatalogAddProcess",bean.getFacilityId(),bean.getCompanyId())?"Y":"N");
            if ("partCatalog".equals(bean.getCatalog()) || bean.getCatalog().startsWith("POS:")) {
				bean.setPrintScreen(false);
				Vector<PrCatalogScreenSearchBean> c = (Vector<PrCatalogScreenSearchBean>) catalogProcess.getSearchResult(bean, user);
				Object[] results = catalogProcess.createRelationalObjects(StringHandler.isBlankString(bean.getIncludeObsoleteParts()) == false ? true:false,c);
				request.setAttribute("prCatalogScreenSearchBeanCollection", results[0]);
				request.setAttribute("rowCountPart", results[1]);
				request.setAttribute("rowCountItem", results[2]);
				
				request.setAttribute("qualityIdLabelColumnHeader", results[3]);
				request.setAttribute("catPartAttrColumnHeader", results[4]);
				request.setAttribute("prRulesViewCollection",catalogProcess.getPrRulesForFacility(bean));
				CatalogFacilityBean tmpBean = new CatalogFacilityBean();
				tmpBean.setCompanyId(bean.getCompanyId());
				tmpBean.setFacilityId(bean.getFacilityId());
				request.setAttribute("catalogFacilityCollection",catalogProcess.getCatalogFacility(tmpBean));
				request.setAttribute("isCompanyUsesCustomerMSDS",user.isFeatureReleased("ShowKitMaterialMsds","ALL",bean.getCompanyId())?"Y":"N");
                //hold_as_customer_owned
                String facLocAppHoldAsCustomerOwned = "N";
                if ("partCatalog".equals(bean.getCatalog())) {
                    facLocAppHoldAsCustomerOwned = catalogProcess.getFacLocAppHoldAsCustomerOwned(bean);
                }
                request.setAttribute("facLocAppHoldAsCustomerOwned",facLocAppHoldAsCustomerOwned);
                if (user.isFeatureReleased("ShowLeadTime",bean.getFacilityId(),bean.getCompanyId()))
                	request.setAttribute("defaultLeadTime",catalogProcess.getDefaultLeadTime(bean.getCompanyId()));
                request.setAttribute("showProp65Warning", catalogProcess.getProp65Flag(bean.getFacilityId()));
                return mapping.findForward("partCatalog");
			}else if ("itemCatalog".equals(bean.getCatalog())) {
				ItemCatalogProcess itemCatalogProcess = new ItemCatalogProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
				request.setAttribute("itemCatalogBeanCollection", itemCatalogProcess.searchItemCatalog(bean,personnelId));
				request.setAttribute("prRulesViewCollection",catalogProcess.getPrRulesForFacility(bean));
                CatalogFacilityBean tmpBean = new CatalogFacilityBean();
				tmpBean.setCompanyId(bean.getCompanyId());
				tmpBean.setFacilityId(bean.getFacilityId());
                request.setAttribute("catalogFacilityCollection",catalogProcess.getCatalogFacility(tmpBean));
                //USE fac_loc_app.charge_type default first unless it's overrides by vv_account_sys.fac_item_charge_type_override
                //here's the logic for overriding fac_loc_app.charge_type_default
                //vv_account_sys.fac_item_charge_type_override == fac_item.part_charge_type OR vv_accoount_sys.fac_item_charge_type_override = a
                //then USE fac_item.part_charge_type
                //vv_account_sys.fac_item_charge_type_override:
                // d - and fac_item.part_charge_type = d then USE fac_item.part_charge_type (in this case it's d)
                // i - and fac_item.part_charge_type = i then USE fac_item.part_charge_type (in this case it's i)
                // a - always USE fac_item.part_charge_type
                // n - never override => USE fla.charge_type_default
                String facLocAppChargeTypeDefault = catalogProcess.getFacLocAppChargeTypeDefault(bean);
                request.setAttribute("facLocAppChargeTypeDefault",facLocAppChargeTypeDefault);
                if (!"n".equals(facLocAppChargeTypeDefault)) {
                    request.setAttribute("engEvalPartTypeRequired",user.isFeatureReleased("EngEvalPartTypeRequired",bean.getFacilityId(),bean.getCompanyId())?"Y":"N");
                }else {
                    request.setAttribute("engEvalPartTypeRequired","N");
                }
                return mapping.findForward("itemCatalog");
			}else if ("materialCatalog".equals(bean.getCatalog())) {
				MsdsViewerProcess msdsProcess = new MsdsViewerProcess(this.getDbUser(request),getTcmISLocaleString(request));
				MsdsViewerInputBean inputBean = new MsdsViewerInputBean();
                inputBean.setCompanyId(inputBean.getCompanyId());
                inputBean.setFacilityId(bean.getFacilityId());
                if("Full MSDS Database".equals(bean.getFacilityOrFullMsdsDatabase()))
                	inputBean.setFullDatabase("Y");
				inputBean.setSimpleSearchText(bean.getSearchText());
                Collection dataC = msdsProcess.getSearchMsdsOnlyResults(inputBean);
                if (dataC != null) {
                    msdsProcess.calculatingNumberOfKitsPerMsds(dataC);
                    //use code required
                    request.setAttribute("showFacilityUseCode",msdsProcess.showFacilityUseCode(inputBean.getFacilityId()));
                }
                request.setAttribute("msdsCatalogBeanCollection",dataC);
                CatalogFacilityBean tmpBean = new CatalogFacilityBean();
				tmpBean.setCompanyId(bean.getCompanyId());
				tmpBean.setFacilityId(bean.getFacilityId());
                request.setAttribute("catalogFacilityCollection",catalogProcess.getCatalogFacility(tmpBean));
            	request.setAttribute("isCompanyUsesCustomerMSDS",user.isFeatureReleased("ShowKitMaterialMsds","ALL",bean.getCompanyId())?"Y":"N");
                return mapping.findForward("msdsCatalog");
			}
		} else if (uAction.equals("createXSL")) {
			this.setExcel(response, "CatalogSearch");
			try {
				bean.setPrintScreen(true);
				if ("partCatalog".equalsIgnoreCase(bean.getCatalog())) {
					catalogProcess.getPartExcelReport(bean,user ).write(response.getOutputStream());//
				}else if ("itemCatalog".equalsIgnoreCase(bean.getCatalog())) {
					catalogProcess.getItemExcelReport(bean, personnelId).write(response.getOutputStream());//
				}else if ("materialCatalog".equalsIgnoreCase(bean.getCatalog())) {
                    MsdsViewerProcess msdsProcess = new MsdsViewerProcess(this.getDbUser(request),getTcmISLocaleString(request));
                    MsdsViewerInputBean inputBean = new MsdsViewerInputBean();
                    inputBean.setCompanyId(inputBean.getCompanyId());
                    inputBean.setFacilityId(bean.getFacilityId());
                    if("Full MSDS Database".equals(bean.getFacilityOrFullMsdsDatabase()))
                        inputBean.setFullDatabase("Y");
                    inputBean.setSimpleSearchText(bean.getSearchText());
                    catalogProcess.getMSDSExcelReport(bean, (Vector<MsdsSearchDataTblBean>)msdsProcess.getSearchMsdsOnlyResults(inputBean),user).write(response.getOutputStream());
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				return mapping.findForward("genericerrorpage");
			}
			return noForward;
		} else if (uAction.equals("loaddata")) {
			PrCatalogScreenSearchBean pbean = new PrCatalogScreenSearchBean();
			BeanHandler.copyAttributes(form, pbean, this.getTcmISLocale(request));

			request.setAttribute("specColl", catalogProcess.getSpecMenu(pbean));
			request.setAttribute("partInvColl", catalogProcess.getInventoryMenu(pbean));
			request.setAttribute("stockingReorder", catalogProcess.getStockingReorder(pbean));
			request.setAttribute("ImgLit", catalogProcess.getImgLit(pbean));
			request.setAttribute("kitMsdsNumber",catalogProcess.getKitMsdsNumber(pbean));
			request.setAttribute("requestIdColl",catalogProcess.getRequestIdColl(pbean));
			request.setAttribute("editApprovalCode",user.getPermissionBean().hasFacilityPermission("EditUseCodeExpiration", bean.getFacilityId(), bean.getCompanyId()));
            String showDirectedCharge = "N";
            if (!StringHandler.isBlankString(pbean.getApplicationId()) && !"My Work Areas".equals(pbean.getApplicationId())) {
                showDirectedCharge = catalogProcess.showDirectedCharge(pbean);
            }
            request.setAttribute("showDirectedCharge",showDirectedCharge);
        } else if (uAction.equals("loadRequestforMaterialCatalog")) {
			PrCatalogScreenSearchBean pbean = new PrCatalogScreenSearchBean();
			BeanHandler.copyAttributes(form, pbean, this.getTcmISLocale(request));
			request.setAttribute("requestIdColl",catalogProcess.getRequestIdCollWithMaterialId(pbean));
			
		}  else if (uAction.equals("loaditemdata")) {
			PrCatalogScreenSearchBean pbean = new PrCatalogScreenSearchBean();
			BeanHandler.copyAttributes(form, pbean, this.getTcmISLocale(request));
			request.setAttribute("ImgLit", catalogProcess.getImgLit(pbean));
		} else if (uAction.equals("checkOut")) {
			/*
			java.util.Enumeration e = request.getParameterNames();
			while(e.hasMoreElements()) {
				String name = (String) e.nextElement();
				log.debug("Name:"+name+":value:"+request.getParameter(name));
			}
			 */
			Collection<ShoppingCartBean> beans = BeanHandler.getBeans((DynaBean)form,"cartTableDiv",new ShoppingCartBean(),this.getTcmISLocale(request));
			ShoppingCartProcess shoppingCartProcess = new ShoppingCartProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
			request.setAttribute("prNumber",shoppingCartProcess.buildRequest(bean,beans,personnelId));
			return mapping.findForward("mrScreen");
		} else if (uAction.equals("pnnlCheckOut")) {
			Collection<ShoppingCartBean> beans = BeanHandler.getBeans((DynaBean)form,"cartTableDiv",new ShoppingCartBean(),this.getTcmISLocale(request));
			com.tcmis.client.pnnl.process.CxmlProcess pnnlProcess = new CxmlProcess("DOE");
			pnnlProcess.sendShoppingCart(bean.getPayloadId(),beans);
			//once I am passing the data to CxmlProcess, I am done!
			return mapping.findForward("genericerrorpage");
		} else if (uAction.equals("newEval")) {
			EngEvalProcess engEvalProcess = new EngEvalProcess(this.getDbUser(request), this.getTcmISLocaleString(request),"");
			request.setAttribute("requestId",engEvalProcess.buildNewRequest(bean,user));
			return mapping.findForward("evalScreen");
		} else if (uAction.equals("reorderEval")) {
			EngEvalProcess engEvalProcess = new EngEvalProcess(this.getDbUser(request), this.getTcmISLocaleString(request),"");
			request.setAttribute("requestId",engEvalProcess.buildReorderRequest(bean,user));
			return mapping.findForward("evalScreen");
		}else if (uAction.equals("loadPointOfSaleUserData")) {
			orderTrackingProcess = new OrderTrackingProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
			request.setAttribute("myWorkareaFacilityViewBeanCollection", orderTrackingProcess.getMyPosWorkareaFacilityViewBean(bean.getPosRequestorId(),bean.getPosInventoryGroup()));
			if (StringHandler.isBlankString(user.getMyDefaultFacilityId())) {
				user.setMyDefaultFacilityId(orderTrackingProcess.getMyDefaultFacility(personnelId,user.getCompanyId()));
			}
			return mapping.findForward("success");
		} else if (uAction.equals("modifyQpl")) {
			StringBuffer requestURL = request.getRequestURL();
			CatalogAddRequestProcess catalogAddProcess = new CatalogAddRequestProcess(this.getDbUser(request),getTcmISLocaleString(request),requestURL.substring(0,requestURL.lastIndexOf("/")));
			request.setAttribute("requestId",catalogAddProcess.modifyQpl(bean,user));
			request.setAttribute("modifyPkg","N");
			return mapping.findForward("catalogAddRequestScreen");
		} else if (uAction.equals("newCatalogPart")) {
			StringBuffer requestURL = request.getRequestURL();
			CatalogAddRequestProcess catalogAddProcess = new CatalogAddRequestProcess(this.getDbUser(request),getTcmISLocaleString(request),requestURL.substring(0,requestURL.lastIndexOf("/")));
			request.setAttribute("requestId",catalogAddProcess.newPart(bean,user));
			request.setAttribute("modifyPkg","N");
			return mapping.findForward("catalogAddRequestScreen");
		} else if (uAction.equals("newWorkAreaApproval")) {
			StringBuffer requestURL = request.getRequestURL();
			CatalogAddRequestProcess catalogAddProcess = new CatalogAddRequestProcess(this.getDbUser(request),getTcmISLocaleString(request),requestURL.substring(0,requestURL.lastIndexOf("/")));
			request.setAttribute("requestId",catalogAddProcess.newWorkAreaApproval(bean,user));
            request.setAttribute("modifyPkg","N");
            return mapping.findForward("catalogAddRequestScreen");
		} else if (uAction.equals("newPartFromExistingPart")) {
			StringBuffer requestURL = request.getRequestURL();
			CatalogAddRequestProcess catalogAddProcess = new CatalogAddRequestProcess(this.getDbUser(request),getTcmISLocaleString(request),requestURL.substring(0,requestURL.lastIndexOf("/")));
			request.setAttribute("requestId",catalogAddProcess.newPartFromExistingPart(bean,user));
            request.setAttribute("modifyPkg","N");
            return mapping.findForward("catalogAddRequestScreen");
       } else if (uAction.equals("newPartFromExistingItem")) {
			StringBuffer requestURL = request.getRequestURL();
			CatalogAddRequestProcess catalogAddProcess = new CatalogAddRequestProcess(this.getDbUser(request),getTcmISLocaleString(request),requestURL.substring(0,requestURL.lastIndexOf("/")));
			request.setAttribute("requestId",catalogAddProcess.newPartFromExistingItem(bean,user));
            request.setAttribute("modifyPkg","N");
            return mapping.findForward("catalogAddRequestScreen");
       } else if (uAction.equals("newPartFromExistingItemModifyPkg")) {
			StringBuffer requestURL = request.getRequestURL();
			CatalogAddRequestProcess catalogAddProcess = new CatalogAddRequestProcess(this.getDbUser(request),getTcmISLocaleString(request),requestURL.substring(0,requestURL.lastIndexOf("/")));
			request.setAttribute("requestId",catalogAddProcess.newPartFromExistingItemModifyPkg(bean,user));
            request.setAttribute("modifyPkg","Y");
            return mapping.findForward("catalogAddRequestScreen");
		} else if (uAction.equals("newPartApprovalCode")) {
			StringBuffer requestURL = request.getRequestURL();
			CatalogAddRequestProcess catalogAddProcess = new CatalogAddRequestProcess(this.getDbUser(request),getTcmISLocaleString(request),requestURL.substring(0,requestURL.lastIndexOf("/")));
			request.setAttribute("requestId",catalogAddProcess.newPartApprovalCode(bean,user));
			request.setAttribute("modifyPkg","N");
			return mapping.findForward("catalogAddRequestScreen");
       } else if (uAction.equals("newMsds")) {
			StringBuffer requestURL = request.getRequestURL();
			CatalogAddMsdsRequestProcess catalogAddMsdsProcess = new CatalogAddMsdsRequestProcess(this.getDbUser(request),getTcmISLocaleString(request),requestURL.substring(0,requestURL.lastIndexOf("/")));
			request.setAttribute("requestId",catalogAddMsdsProcess.newMsds(bean,user));
			return mapping.findForward("catalogAddMsdsRequestScreen");
        } else if (uAction.equals("getPosAccountSysData")) {
			request.setAttribute("posAccountSysData",catalogProcess.getPosAccountSysData(bean));
			request.setAttribute("posDefaultNeedDate", Calendar.getInstance().getTime());
			return mapping.findForward("loadPosAccountSysData");
		} else if (uAction.equals("getPosAccountSyschargeNumberPoData")) {
			request.setAttribute("accountSysChargeNumberPoData",catalogProcess.getPosAccountSysChargeNumberPoData(bean));
			return mapping.findForward("loadChargeInfo");
		} else if (uAction.equals("checkChargeNumbers")) {
			request.setAttribute("chargeNumbersValid",catalogProcess.validateChargeNumbers(bean));
			return mapping.findForward("loadChargeNumberValidator");
		} else if (uAction.equals("pointOfSaleCheckOut")) {
			Collection<ShoppingCartBean> beans = BeanHandler.getBeans((DynaBean)form,"cartTableDiv",new ShoppingCartBean(),this.getTcmISLocale(request));
			PointOfSaleProcess pointOfSaleProcess = new PointOfSaleProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
			request.setAttribute("prNumber",pointOfSaleProcess.buildPosRequest(bean,beans,personnelId));
			return mapping.findForward("loadPosPickingScreen");
        } else if (uAction.equals("addGrandfatheredMaterial")) {
            if ("OK".equals(catalogProcess.addGrandfatheredMaterial(bean))) {
                return mapping.findForward("callToServerCallback");
            }else {
                return noForward;
            }
        } else if (uAction.equals("aribarepair")) {
        	orderTrackingProcess = new OrderTrackingProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
        	request.setAttribute("myWorkareaFacilityViewBeanCollection", orderTrackingProcess.getMyWorkareaFacilityViewBean(personnelId));
        	return mapping.findForward("aribarepair");
        }
		else if (uAction.equals("aribarepairSubmit")) {
			//checkToken(request);
			AribaRepairProcess aribaRepairProcess = new AribaRepairProcess(getDbUser(), getTcmISLocale());
			Collection errs = aribaRepairProcess.update((String)request.getParameter("aribaRepairMRsString"), user.getCustomerReturnId());
			if(errs != null)
				request.setAttribute("tcmISErrors", errs);
			else
			{
				errs = new Vector();
				String xmlString = "";
				com.tcmis.client.seagate.process.CxmlProcess cxmlProcess = new com.tcmis.client.seagate.process.CxmlProcess(getDbUser());
				try{
					xmlString = cxmlProcess.getPunchoutOrderMessage(user.getCustomerReturnId(),null);
				}catch(Exception e){
					errs.add(new ResourceLibrary("com.tcmis.common.resources.CommonResources", getLocale()).getString("generic.error"));
					request.setAttribute("tcmISErrors", errs);
				}				
			    if(!StringHandler.isBlankString(xmlString))
				{
					request.setAttribute("postBodyUrlUtf8", xmlString);
					String browserPost = cxmlProcess.getBrowserPostFromPayloadId(user.getCustomerReturnId());
					request.setAttribute("browserPost", browserPost);
				}
			}
	    	return mapping.findForward("aribaPunchout");
		}
        else {
			orderTrackingProcess = new OrderTrackingProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
            request.setAttribute("myWorkareaFacilityViewBeanCollection", orderTrackingProcess.getMyWorkareaFacilityViewBean(personnelId));
			if (StringHandler.isBlankString(user.getMyDefaultFacilityId())) {
				user.setMyDefaultFacilityId(orderTrackingProcess.getMyDefaultFacility(personnelId,user.getCompanyId()));
			} 
			request.setAttribute("myFacilityPointOfSaleIgColl",orderTrackingProcess.getMyFacPosIg(personnelId));
			request.setAttribute("ecommerceLogon", "Y".equalsIgnoreCase(user.getEcommerceLogon())?"Y":"N");
			request.setAttribute("ecommerceSource", !StringHandler.isBlankString(user.getEcommerceSource())?user.getEcommerceSource():"");
			//feature release
            request.setAttribute("activeFeatureReleaseColl",catalogProcess.getActiveFeatureRelease(user));
            //user group member
            request.setAttribute("userGroupMemberForCatalogScreenColl",catalogProcess.getUserGroupMemberForCatalogScreen(user));
            
            return mapping.findForward("success");
		}
		return mapping.findForward("success");
	}
}
