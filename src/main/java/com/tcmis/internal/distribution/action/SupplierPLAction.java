package com.tcmis.internal.distribution.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
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
import com.tcmis.internal.supply.beans.DbuyContractPriceOvBean;
import com.tcmis.internal.supply.beans.VvSupplyPathBean;

/******************************************************************************
 * Controller for logistics
 * 
 * @version 1.0
 ******************************************************************************/
public class SupplierPLAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		if (!this.checkLoggedIn("supplierpricelistmain")) {
			return mapping.findForward("login");
		}

		PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");
		PermissionBean permmissionBean = user.getPermissionBean();
		if (!permmissionBean.hasUserPagePermission("supplierPriceList")) {
			return (mapping.findForward("nopermissions"));
		}

		DbuyProcess process = new DbuyProcess(this);

		if (form == null) {
			Collection<VvSupplyPathBean> supplyPaths = new VvDataProcess(getDbUser(request),getTcmISLocaleString(request)).getSupplyPathType();
			 VvSupplyPathBean customerPath = new VvSupplyPathBean();
			 customerPath.setJspLabel("label.customerbuy");
			 customerPath.setSupplyPath("Customer");
			 supplyPaths.add(customerPath);
            request.setAttribute("vvSupplyPathBeanCollection",supplyPaths);
			return (mapping.findForward("success"));
		}

		DbuyInputBean input = new DbuyInputBean();
		BeanHandler.copyAttributes(form, input, getTcmISLocale(request));

		if (input.isSearch()) {
			request.setAttribute("beanCollection", process.getDbuy(input, user.getPersonnelIdBigDecimal()));
			this.saveTcmISToken(request);
		}
		if (input.isUpdate()) {
			Enumeration<String> e = request.getParameterNames();

			StringBuilder errorMsg = new StringBuilder();
			String error = "";

			if (user.getPermissionBean().hasOpsEntityPermission("supplierPriceList", input.getOpsEntityId(), null)) {
				@SuppressWarnings("unchecked")
				Collection<DbuyContractPriceOvBean> beans = BeanHandler.getBeans((DynaBean) form, "SupperPriceListBean", new DbuyContractPriceOvBean(), this.getTcmISLocale());
				HashMap<String, HashMap<Date, Vector<DbuyContractPriceOvBean>>> partmap = new HashMap<String, HashMap<Date, Vector<DbuyContractPriceOvBean>>>();
				Vector<DbuyContractPriceOvBean> deleteStartDatemap = new Vector<DbuyContractPriceOvBean>();
				String err = null;
				for (DbuyContractPriceOvBean bean : beans) {
					if ("Y".equals(bean.getIsGrand())) {
						Vector out;
						if ("New".equals(bean.getLevel1Changed())) {
							BigDecimal in = bean.getDbuyContractId();
							out = process.ModifyPriceList(bean, user.getPersonnelIdBigDecimal());

							BigDecimal contractid = null;
							if (out.size() > 0)
								err = (String) out.get(0);
							if (!"OK".equalsIgnoreCase(err)) {
								errorMsg.append((String) out.get(0) + "<br>\n");
							}
							if (out.size() > 1)
								contractid = (BigDecimal) out.get(1);
							if (contractid != null) {
								for (DbuyContractPriceOvBean bb : beans)
									if (bb.getDbuyContractId().equals(in))
										bb.setDbuyContractId(contractid);
							}
						}
						if ("Y".equals(bean.getLevel1Changed())) {
							// P_MODIFY_DBUY_CONTRACT
							out = process.ModifyPriceList(bean, user.getPersonnelIdBigDecimal());
							if (out.size() > 0) {
								err = (String) out.get(0);
								if (!"OK".equalsIgnoreCase(err)) {
									errorMsg.append((String) out.get(0) + "<br>\n");
								}
							}
						}
					}
				}
				for (DbuyContractPriceOvBean bean : beans) {
					String partname = bean.getDbuyContractId().toString();
					HashMap<Date, Vector<DbuyContractPriceOvBean>> parts = partmap.get(partname);
					if (parts == null) {
						parts = new HashMap<Date, Vector<DbuyContractPriceOvBean>>();
						partmap.put(partname, parts);
					}
					Date startdate = bean.getStartDate();
					if ("deleteStartDate".equals(bean.getChanged())) {
						Date oldStartDate = bean.getOldStartDate();
						if (oldStartDate != null && "Y".equals(bean.getIsParent()))
							deleteStartDatemap.add(bean);
						continue;
					}
					// if( "markasdelete".equals(bean.getChanged())) continue;
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
									if ("Y".equals(bean.getLevel2Changed()) || "New".equals(bean.getLevel2Changed())) { // P_MOD_DBUY_CONTRACT_PRICE
										error = process.ModifyPriceListStartDate(bean, user.getPersonnelIdBigDecimal());
										if (error != null && error.length() > 0)
											errorMsg.append(error + "<br>\n");
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
						// P_MOD_DBUY_CONTRACT_PRICE_BRK delete
						// process.DeletePriceList(db);
						DbuyContractPriceOvBean bean0 = null;
						for (DbuyContractPriceOvBean bean : partdates) {
							if ("Y".equals(bean.getIsParent())) {
								bean0 = bean;
								process.DeletePriceList(bean); // don't delete
																// first
								// process.DeletePriceList(db);
								break;
							}
						}
						// P_MOD_DBUY_CONTRACT_PRICE_BRK insert
						for (DbuyContractPriceOvBean bean : partdates) {
							if (!"Y".equals(bean.getIsParent())) {
								error = process.newPriceBreak(bean0, bean, user.getPersonnelIdBigDecimal());
								if (error != null && error.length() > 0)
									errorMsg.append(error + "<br>\n");
							}
						}
					}
				}
			}
			request.setAttribute("tcmISError", errorMsg.toString());
			request.setAttribute("beanCollection", process.getDbuy(input, user.getPersonnelIdBigDecimal()));
			System.out.println("back from query");
		}
		else if (input.isCreateExcel()) {
			setExcel(response, "SupplierPriceList");
			try {
				process.getExcelReport(input, user).write(response.getOutputStream());
			}
			catch (Exception ex) {
				ex.printStackTrace();
				return mapping.findForward("genericerrorpage");
			}
			return noForward;
		}
		else if (input.isLoadData()) {
			// request.setAttribute("vvLotStatusLegend",
			// process.getLotStatusLegend());
			return (mapping.findForward("loaddata"));
		}
		if (input.isGetPriority()) {
			DbuyContractPriceOvBean prioritybean = new DbuyContractPriceOvBean();
			BeanHandler.copyAttributes(form, prioritybean, this.getTcmISLocale(request));
			String priority = process.getPriority(prioritybean);
			request.setAttribute("maxPriority", StringHandler.isBlankString(priority) ? "1" : priority);
			return (mapping.findForward("getPriorityCount"));
		}
		if (input.isEditSupplementaryData()) {
			request.setAttribute("stateColl", process.getState());
			BigDecimal dBuyContractId = new BigDecimal(request.getParameter("dbuyContractId"));
			Vector editSupDataColl = (Vector) process.getSupData(dBuyContractId);
			if (editSupDataColl.size() > 0)
				request.setAttribute("editSupDataColl", editSupDataColl.get(0));

			return (mapping.findForward("showeditsupdata"));
		}
		if (input.isEditData()) {
			DbuyContractPriceOvBean editsupdatabean = new DbuyContractPriceOvBean();
			BeanHandler.copyAttributes(form, editsupdatabean, this.getTcmISLocale(request));

			String err = process.editSupData(editsupdatabean);
			request.setAttribute("tcmISError", err);
			BigDecimal dBuyContractId = new BigDecimal(request.getParameter("dbuyContractId"));
			Vector editSupDataColl = (Vector) process.getSupData(dBuyContractId);
			if (editSupDataColl.size() > 0)
				request.setAttribute("editSupDataColl", editSupDataColl.get(0));
			request.setAttribute("stateColl", process.getState());
			return (mapping.findForward("showeditsupdata"));
		}
		else if (input.isCreateUpdateTemplate()) {
			try {
				this.setExcel(response, "pricelisttemplate");
				process.getPriceListUpdateTemplateExcel(input, user).write(response.getOutputStream());
			}
			catch (Exception ex) {
				ex.printStackTrace();
				return mapping.findForward("genericerrorpage");
			}
			return noForward;
		}
		else if (input.isCreateInsertTemplate()) {
			try {
				this.setExcel(response, "pricelisttemplate");
				process.getPriceListInsertTemplateExcel(user.getLocale()).write(response.getOutputStream());
			}
			catch (Exception ex) {
				ex.printStackTrace();
				return mapping.findForward("genericerrorpage");
			}
			return noForward;
		}

		return (mapping.findForward("success"));
	}
}
