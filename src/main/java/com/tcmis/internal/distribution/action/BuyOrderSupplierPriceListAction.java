package com.tcmis.internal.distribution.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PermissionBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.VvDataProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.distribution.beans.DbuyInputBean;
import com.tcmis.internal.distribution.process.DbuyProcess;
import com.tcmis.internal.supply.beans.BuyOrdersInputBean;
import com.tcmis.internal.supply.beans.DbuyContractPriceFlatBean;
import com.tcmis.internal.supply.beans.DbuyContractPriceOvBean;
import com.tcmis.internal.supply.process.BuyOrdersProcess;

/******************************************************************************
 * Controller for logistics
 * @version 1.0
 ******************************************************************************/
public class BuyOrderSupplierPriceListAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		if (!this.checkLoggedIn("editpricelist"))
			return mapping.findForward("login");
		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		PermissionBean permmissionBean = personnelBean.getPermissionBean();
		// used for both input and result.
		BuyOrdersInputBean buyOrderBean = new BuyOrdersInputBean();
		// used for both input and result.
		DbuyContractPriceFlatBean dbuyInputBean = new DbuyContractPriceFlatBean();

		BuyOrdersProcess buyprocess = new BuyOrdersProcess(this.getDbUser(request),this.getTcmISLocaleString(request));
//		DbuyProcess process = new DbuyProcess(this);

		if (form == null) return (mapping.findForward("success"));

		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
		BeanHandler.copyAttributes(form, buyOrderBean, this.getTcmISLocale(request));
		BeanHandler.copyAttributes(form, dbuyInputBean, this.getTcmISLocale(request));
		
		String action = (String) ((DynaBean) form).get("uAction"); //
		if ("search".equals(action)) {
			request.setAttribute("dbuyViewBeanCollection", buyprocess.getDbuyFlatView(dbuyInputBean));
			// Simplified one doesn't need ig dropdown.
			request.setAttribute("buyPageViewBeanCollection", buyprocess.getSearchDataOpenBuy(personnelBean,buyOrderBean));
// might just remove this later is no refresh itself.
//			this.saveTcmISToken(request);
			return mapping.findForward("success");
		}
		else if (form != null && action != null && action.equalsIgnoreCase("createPo")) {
		// for consistency.
		buyOrderBean.setPoSupplierId(buyOrderBean.getSelectedSupplierForPo());
		buyOrderBean.setStatus("New");
		buyOrderBean.setOldStatus("New");
		Collection c = new Vector();
		c.add(buyOrderBean);
		DynaBean dynaBean = (DynaBean) form;
		List buyOrdersInputBeans = (List) dynaBean.get("buyOrdersInputBean");

		if (buyOrdersInputBeans != null) {
			Iterator iterator = buyOrdersInputBeans.iterator();
			while (iterator.hasNext()) {
				BuyOrdersInputBean _buyBean = new BuyOrdersInputBean();
				LazyDynaBean lazyBean = (LazyDynaBean) iterator.next();
				BeanHandler.copyAttributes(lazyBean, _buyBean, getTcmISLocale(request));
				if (_buyBean.getRowNumber() != null) {
					_buyBean.setSelectedSupplierForPo(buyOrderBean.getSelectedSupplierForPo());
					_buyBean.setPoSupplierId(buyOrderBean.getPoSupplierId());
					_buyBean.setPoSupplierName(buyOrderBean.getPoSupplierName());
					_buyBean.setStatus("New");
					_buyBean.setOldStatus("New");
					c.add(_buyBean);
				}
			}
		}
		String[] createResult = buyprocess.createPo(c, (PersonnelBean) this.getSessionObject(request, "personnelBean"), request.getLocale());
		request.setAttribute("poNumber", createResult[0]);
		request.setAttribute("tcmISError",createResult[1]);
		//Larry Note, is it the same thing, don't need to rerun.
//		request.setAttribute("dbuyViewBeanCollection", buyprocess.getDbuyFlatView(dbuyInputBean));
		// Simplified one doesn't need ig dropdown.
//		request.setAttribute("buyPageViewBeanCollection", buyprocess.getSearchDataOpenBuy(personnelBean,buyOrderBean));
//		request.setAttribute("dbuyViewBeanCollection", buyprocess.getDbuyFlatView(dbuyInputBean));
		return mapping.findForward("success");
		} 
		return mapping.findForward("success");
//		 tcm_ops.preferred_supplier_price_view 
// Use seccess, no result frame		
//		return mapping.findForward("result");
		
		// No Update at this point.
/*		
		if ("update".equals(action)) {
			java.util.Enumeration<String> e = request.getParameterNames();

			String ErrorMsg = "";
			if (personnelBean.getPermissionBean().hasOpsEntityPermission("supplierPriceList", inputBean.getOpsEntityId(), null)) {
				Collection<DbuyContractPriceOvBean> beans = BeanHandler.getBeans((DynaBean) form, "SupperPriceListBean", new DbuyContractPriceOvBean(), this.getTcmISLocale());
				HashMap<String, HashMap<Date, Vector<DbuyContractPriceOvBean>>> partmap = new HashMap();
				Vector<DbuyContractPriceOvBean> deleteStartDatemap = new Vector();

				for (DbuyContractPriceOvBean bean : beans) {
					if ("Y".equals(bean.getIsGrand())) {
						if ("New".equals(bean.getLevel1Changed())) {
							BigDecimal in = bean.getDbuyContractId();
							Vector out = process.ModifyPriceList(bean, personnelId);
							String err = null;
							BigDecimal contractid = null;
							if (out.size() > 0)
								err = (String) out.get(0);
							if (out.size() > 1)
								contractid = (BigDecimal) out.get(1);
							if (contractid != null) {
								for (DbuyContractPriceOvBean bb : beans)
									if (bb.getDbuyContractId().equals(in))
										bb.setDbuyContractId(contractid);
							}
						}
						if ("Y".equals(bean.getLevel1Changed())) {
							//P_MODIFY_DBUY_CONTRACT
							process.ModifyPriceList(bean, personnelId);
						}
					}
				}
				for (DbuyContractPriceOvBean bean : beans) {
					String partname = bean.getDbuyContractId().toString();
					HashMap<Date, Vector<DbuyContractPriceOvBean>> parts = partmap.get(partname);
					if (parts == null) {
						parts = new HashMap();
						partmap.put(partname, parts);
					}
					Date startdate = bean.getStartDate();
					if ("deleteStartDate".equals(bean.getChanged())) {
						Date oldStartDate = bean.getOldStartDate();
						if (oldStartDate != null && "Y".equals(bean.getIsParent()))
							deleteStartDatemap.add(bean);
						continue;
					}
					//        	if( "markasdelete".equals(bean.getChanged())) continue;
					if (startdate == null)
						continue;
					Vector<DbuyContractPriceOvBean> partdates = parts.get(startdate);
					if (partdates == null) {
						partdates = new Vector();
						parts.put(startdate, partdates);
					}
					BigDecimal qty = bean.getBreakQuantity();
					BigDecimal price = bean.getUnitPrice();
					if (qty == null || price == null)
						continue;
					if ("Y".equals(bean.getIsParent()))
						partdates.insertElementAt(bean, 0);
					else {
						partdates.add(bean);
					}
				}
				for (String partname : partmap.keySet()) {
					HashMap<Date, Vector<DbuyContractPriceOvBean>> parts = partmap.get(partname);
					Vector<Date> sort = new Vector();
					for (Date startdate : parts.keySet()) {
						sort.add(startdate);
					}
					Collections.sort(sort);
					for (Date startdate : sort) {
						Vector<DbuyContractPriceOvBean> partdates = parts.get(startdate);
						boolean changed = false;
						for (DbuyContractPriceOvBean bean : partdates) {
							if ("Y".equals(bean.getIsParent())) {
								{
									if ("Y".equals(bean.getLevel2Changed()) || "New".equals(bean.getLevel2Changed())) { //P_MOD_DBUY_CONTRACT_PRICE
										process.ModifyPriceListStartDate(bean, personnelId);
									}
								}
								continue;
							}
						}
						for (DbuyContractPriceOvBean bean : partdates) {
							if (!"Y".equals(bean.getIsParent())) {
								if ("Y".equals(bean.getChanged())) {
									changed = true;
									break;
								}
							}
						}
						if (!changed)
							continue;
						//P_MOD_DBUY_CONTRACT_PRICE_BRK delete
						//            	process.DeletePriceList(db);
						DbuyContractPriceOvBean bean0 = null;
						for (DbuyContractPriceOvBean bean : partdates) {
							if ("Y".equals(bean.getIsParent())) {
								bean0 = bean;
								process.DeletePriceList(bean); //don't delete first
								//                    	process.DeletePriceList(db);
								break;
							}
						}
						//P_MOD_DBUY_CONTRACT_PRICE_BRK insert
						for (DbuyContractPriceOvBean bean : partdates) {
							if (!"Y".equals(bean.getIsParent()))
								process.newPriceBreak(bean0, bean, personnelId);
						}
					}
					//        	if( "Y".equals(bean.getChanged()) ) ErrorMsg += process.ModifyPriceList(bean);
					//     	   if( "Y".equals(bean.getChanged()) ) ErrorMsg += process.updateLine(bean);
					//     	   if( "Remove".equals(bean.getChanged()) ) ErrorMsg += process.RemovePriceList(bean);
				}
			}
			request.setAttribute("tcmISError", ErrorMsg);
			request.setAttribute("beanCollection", process.getDbuy(inputBean, personnelId));
			System.out.println("back from query");
		}
		else if ("loaddata".equals(action)) {
			//    	request.setAttribute("vvLotStatusLegend", process.getLotStatusLegend());
			return (mapping.findForward("loaddata"));
		}
		if ("getPriority".equals(action)) {
			DbuyContractPriceOvBean prioritybean = new DbuyContractPriceOvBean();
			BeanHandler.copyAttributes(form, prioritybean, this.getTcmISLocale(request));
			String priority = process.getPriority(prioritybean);
			request.setAttribute("maxPriority", StringHandler.isBlankString(priority) ? "1" : priority);
			return (mapping.findForward("getPriorityCount"));
		}
		if ("editsupplimentorydata".equals(request.getParameter("uAction"))) {
			
	        request.setAttribute("stateColl", process.getState());
			BigDecimal dBuyContractId = new BigDecimal(request.getParameter("dbuyContractId"));
			Vector editSupDataColl = (Vector) process.getSupData(dBuyContractId);
			request.setAttribute("editSupDataColl", editSupDataColl.get(0));
						
			return (mapping.findForward("showeditsupdata"));
		}
		if ("editData".equals(action))	{
			DbuyContractPriceOvBean editsupdatabean = new DbuyContractPriceOvBean();
			BeanHandler.copyAttributes(form, editsupdatabean, this.getTcmISLocale(request));
			
			String err = process.editSupData(editsupdatabean);
			request.setAttribute("tcmISError", err);
			BigDecimal dBuyContractId = new BigDecimal(request.getParameter("dbuyContractId"));
			Vector editSupDataColl = (Vector) process.getSupData(dBuyContractId);
			request.setAttribute("editSupDataColl", editSupDataColl.get(0));
			request.setAttribute("stateColl", process.getState());
			return (mapping.findForward("showeditsupdata"));
		}

		return (mapping.findForward("success"));
*/		
	}
}
