package com.tcmis.internal.catalog.action;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONException;
import org.json.JSONObject;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.catalog.beans.ItemAffectedNotification;
import com.tcmis.internal.catalog.beans.MaterialAffectedNotification;
import com.tcmis.internal.catalog.beans.MfrAffectedNotification;
import com.tcmis.internal.catalog.beans.MfrNotificationCategoryBean;
import com.tcmis.internal.catalog.beans.MfrNotificationRequest;
import com.tcmis.internal.catalog.beans.MfrNotificationRequestWrapper;
import com.tcmis.internal.catalog.process.MfrNotificationMgmtProcess;
import com.tcmis.internal.catalog.process.MfrNotificationProcess;

public class MfrNotificationAction extends TcmISBaseAction {

	private Collection<MfrNotificationCategoryBean> reload(HttpServletRequest request, MfrNotificationRequest input, 
			MfrNotificationMgmtProcess mgmtProcess, MfrNotificationProcess process) throws BaseException {
		Collection<MfrNotificationRequest> notification = process.getNotificationRequest(input.getNotificationId());
		Collection<MfrNotificationCategoryBean> categories = null;
		Collection<BigDecimal> selectedCategories = Collections.emptyList();
		if (notification != null) {
			selectedCategories = notification.stream().map(n -> n.getMfrReqCategoryId()).collect(Collectors.toList());
			if ( ! notification.isEmpty()) {
				request.setAttribute("notification", notification.stream().findFirst().get());
			}
		}
		categories = mgmtProcess.getNotificationCategories(selectedCategories);
		request.setAttribute("categories", categories);
		//request.setAttribute("requestComponents", process.getRequestComponents(notification, categories));
		MfrNotificationRequestWrapper[] wrapper = process.getRequestComponents(notification, categories);
		request.setAttribute("notificationRequest", wrapper);
		return categories;
	}

	private Collection<MfrNotificationCategoryBean> recycleData
			(MfrNotificationRequest input, MfrNotificationMgmtProcess mgmtProcess, MfrNotificationProcess process) throws Exception {
		JSONObject pageUploadData = new JSONObject(input.getPageUploadCode());
		Collection<MfrNotificationRequest> notificationCategories = Arrays.stream(input.getSelectedCategories().split(",")).map(c -> {
			MfrNotificationRequest notification = new MfrNotificationRequest();
			notification.setNotificationId(input.getNotificationId());
			BigDecimal categoryId = new BigDecimal(c.substring("category".length()));
			notification.setMfrReqCategoryId(categoryId);
			try {
				notification.setPageUploadCode(pageUploadData.getJSONObject(c).toString());
			} catch (JSONException e) {
				log.error(e);
			}
			return notification;
		}).collect(Collectors.toList());
		
		Collection<BigDecimal> selectedCategories = Arrays.stream(input.getSelectedCategories().split(",")).map(c -> new BigDecimal(c.substring("category".length()))).collect(Collectors.toList());
		Collection<MfrNotificationCategoryBean> categories = mgmtProcess.getNotificationCategories(selectedCategories);
		request.setAttribute("categories", categories);
		MfrNotificationRequestWrapper[] wrapper = process.getRequestComponents(notificationCategories, categories);
		request.setAttribute("notificationRequest", wrapper);
		request.setAttribute("notification", input);
		return categories;
	}
	
	@SuppressWarnings("unchecked")
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, 
			   HttpServletRequest request, HttpServletResponse response) throws Exception {
		PersonnelBean user = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		MfrNotificationMgmtProcess mgmtProcess = new MfrNotificationMgmtProcess(getDbUser());
		MfrNotificationProcess process = new MfrNotificationProcess(getDbUser());
		
		ActionForward forward = mapping.findForward("success");
		if (!isLoggedIn(request)) {
			request.setAttribute("requestedPage", "mfrnotificationmain");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return mapping.findForward("login");
		}
		
		MfrNotificationRequest input = new MfrNotificationRequest();
		BeanHandler.copyAttributes((DynaBean) form, input, this.getResourceBundleValue(request, "java.datetimeformat"));
		Collection<MfrNotificationCategoryBean> categories = null;
		if (input.isInit()) {
			//Collection<MfrNotificationCategoryBean> components = BeanHandler.getBeans((DynaBean) form, "category", "datetimeformat", new MfrNotificationCategoryBean(), getLocale());
			categories = mgmtProcess.getNotificationCategories();
			request.setAttribute("categories", categories);
			request.setAttribute("notificationId", process.insertNotificationRequest(input, user));
		}
		else if (input.isSave()) {
			forward = noForward;
			try {
				process.removeDeletedCategories(input);
				process.saveRequest(input, user);
			}
			catch(Exception e) {
	            log.error(e);
	            
				JSONObject error = new JSONObject();
				error.put("tcmisError", e.getMessage());
	            PrintWriter out = response.getWriter();
				out.write(error.toString());
				out.close();
			}
		}
		else if (input.isSubmit()) {
			forward = noForward;
			try {
				process.removeDeletedCategories(input);
				categories = mgmtProcess.getNotificationCategories(Arrays.stream(input.getSelectedCategories().split(",")).map(c -> new BigDecimal(c.substring("category".length()))).collect(Collectors.toList()));
				process.submitRequest(input, categories, user);
			}
			catch(Exception e) {
	            log.error(e);
	            
				JSONObject error = new JSONObject();
				error.put("tcmisError", e.getMessage());
	            PrintWriter out = response.getWriter();
				out.write(error.toString());
				out.close();
			}
		}
		else if (input.isApprove()) {
			try {
				if ( ! StringHandler.isBlankString(input.getSelectedCategories())) {
					process.removeDeletedCategories(input);
					categories = mgmtProcess.getNotificationCategories(Arrays.stream(input.getSelectedCategories().split(",")).map(c -> new BigDecimal(c.substring("category".length()))).collect(Collectors.toList()));

					Function<String, MfrAffectedNotification> mfrSupplier = categoryName -> {
						MfrAffectedNotification mfr = null;
						try {
							Collection<MfrAffectedNotification> mfrs = BeanHandler.getBeans((DynaBean)form, categoryName, new MfrAffectedNotification());
							if (mfrs != null) {
								Iterator<MfrAffectedNotification> mfrIterator = mfrs.iterator();
								if (mfrIterator.hasNext()) {
									mfr = mfrIterator.next();
								}
							}
						} catch(Exception e) {
							request.setAttribute("tcmisError", e.getMessage());
						}
						return mfr;
					};
					
					Function<String, Collection<MaterialAffectedNotification>> materialSupplier = categoryName -> {
						Collection<MaterialAffectedNotification> materials = null;
						try {
							if (((DynaBean)form).get(categoryName+"MaterialGrid") != null) {
								materials = BeanHandler.getBeans((DynaBean)form, categoryName+"MaterialGrid", new MaterialAffectedNotification());
							}
						} catch(Exception e) {
							request.setAttribute("tcmisError", e.getMessage());
						}
						return materials;
					};
					
					Function<String, Collection<ItemAffectedNotification>> itemSupplier = categoryName -> {
						Collection<ItemAffectedNotification> items = null;
						try {
							if (((DynaBean)form).get(categoryName+"ItemGrid") != null) {
								items = BeanHandler.getBeans((DynaBean)form, categoryName+"ItemGrid", new ItemAffectedNotification());
							}
						} catch(Exception e) {
							request.setAttribute("tcmisError", e.getMessage());
						}
						return items;
					};
					
					
					process.approveRequest(input, user, categories, mfrSupplier, materialSupplier, itemSupplier);
					Executors.newSingleThreadExecutor().execute(() -> {
						try {
							process.sendNotification(input);
						} catch(BaseException e) {
							log.error(e);
						}
					});
				}
			} catch(Exception e) {
				request.setAttribute("tcmisError", e.getMessage());
			}
			categories = reload(request, input, mgmtProcess, process);
		}
		else if (input.isReload()) {
			categories = reload(request, input, mgmtProcess, process);
		}
		else if (input.isLoadMfr() || input.isLoadMaterials()) {
			forward = noForward;
			try {
				if (input.isLoadMfr()) {
					process.loadManufacturer(input);
				}
				MfrNotificationRequestWrapper wrapper = process.getRequestComponents(input);
				request.setAttribute("request", wrapper);
				request.setAttribute("categoryId", "category"+input.getMfrReqCategoryId());
				forward = mapping.findForward(input.getuAction());
			}
			catch(Exception e) {
	            log.error(e);
	            
				JSONObject error = new JSONObject();
				error.put("tcmisError", e.getMessage());
	            PrintWriter out = response.getWriter();
				out.write(error.toString());
				out.close();
			}
		}
		else if (input.isSelectMaterial()) {
			forward = noForward;
			try {
				MfrNotificationRequest notification = input;
				if (StringHandler.isBlankString(input.getPageUploadCode())) { 
					notification = process.getNotificationRequest(input.getNotificationId(), input.getMfrReqCategoryId());
					notification.setMaterialId(input.getMaterialId());
				}
				MfrNotificationCategoryBean category = process.getCategory(input.getMfrReqCategoryId());
				MfrNotificationRequestWrapper wrapper = process.getRequestComponents(notification, category);
				request.setAttribute("categoryId", "category"+category.getMfrReqCategoryId());
				request.setAttribute("request", wrapper);
				forward = mapping.findForward("selectMaterial");
			}
			catch(Exception e) {
	            log.error(e);
	            
				JSONObject error = new JSONObject();
				error.put("tcmisError", e.getMessage());
	            PrintWriter out = response.getWriter();
				out.write(error.toString());
				out.close();
			}
		}
		else if (input.isAddItems()) {
			/*try {
				process.removeDeletedCategories(input);
				process.saveRequest(input, user);
			}
			catch(Exception e) {
				log.error(e);
				request.setAttribute("tcmisError", "Unable to Save: " + e.getMessage());
			}*/
			String category = "category"+input.getMfrReqCategoryId();
			Collection<MfrNotificationRequest> requests = BeanHandler.getBeans((DynaBean)form, category, new MfrNotificationRequest());
			if (requests!= null) {
				Optional<MfrNotificationRequest> requestCategory = requests.stream().findFirst();
				if (requestCategory.isPresent()) {
					String itemsToAdd = requestCategory.get().getItemsToAdd();
					String materialsToAdd = requestCategory.get().getMaterialsToAdd();
					if ( ! StringHandler.isBlankString(itemsToAdd)) {
						process.addItems(input, Arrays.stream(itemsToAdd.split(",")).map(i -> new BigDecimal(i)).collect(Collectors.toList()), false);
					}
					else if ( ! StringHandler.isBlankString(materialsToAdd)) {
						process.addItems(input, Arrays.stream(materialsToAdd.split(",")).map(m -> new BigDecimal(m)).collect(Collectors.toList()), true);
					}
				}
			}
			recycleData(input, mgmtProcess, process);
			//categories = reload(request, input, mgmtProcess, process);
		}
		else if (input.isAddMaterials()) {
			/*try {
				process.removeDeletedCategories(input);
				process.saveRequest(input, user);
			}
			catch(Exception e) {
				log.error(e);
				request.setAttribute("tcmisError", "Unable to Save: " + e.getMessage());
			}*/
			String category = "category"+input.getMfrReqCategoryId();
			Collection<MfrNotificationRequest> requests = BeanHandler.getBeans((DynaBean)form, category, new MfrNotificationRequest());
			if (requests!= null) {
				Optional<MfrNotificationRequest> requestCategory = requests.stream().findFirst();
				if (requestCategory.isPresent()) {
					String materialsToAdd = requestCategory.get().getMaterialsToAdd();
					if ( ! StringHandler.isBlankString(materialsToAdd)) {
						process.addMaterials(input, Arrays.stream(materialsToAdd.split(",")).map(m -> new BigDecimal(m)).collect(Collectors.toList()));
					}
				}
			}
			recycleData(input, mgmtProcess, process);
			//categories = reload(request, input, mgmtProcess, process);
		}
		else if (input.isDeleteItems()) {
			/*try {
				process.removeDeletedCategories(input);
				process.saveRequest(input, user);
			}
			catch(Exception e) {
				log.error(e);
				request.setAttribute("tcmisError", "Unable to Save: " + e.getMessage());
			}*/
			String category = "category"+input.getMfrReqCategoryId();
			Collection<ItemAffectedNotification> items = BeanHandler.getBeans((DynaBean)form, category+"ItemGrid", new ItemAffectedNotification());
			process.deleteItems(input, items);
			recycleData(input, mgmtProcess, process);
			//categories = reload(request, input, mgmtProcess, process);
		}
		else if (input.isDeleteMaterials()) {
			/*try {
				process.removeDeletedCategories(input);
				process.saveRequest(input, user);
			}
			catch(Exception e) {
				log.error(e);
				request.setAttribute("tcmisError", "Unable to Save: " + e.getMessage());
			}*/
			String category = "category"+input.getMfrReqCategoryId();
			Collection<MaterialAffectedNotification> materials = BeanHandler.getBeans((DynaBean)form, category+"MaterialGrid", new MaterialAffectedNotification());
			process.deleteMaterials(input, materials);
			recycleData(input, mgmtProcess, process);
			//categories = reload(request, input, mgmtProcess, process);
		}
		else if (input.isReject()) {
			process.rejectRequest(input, user);
			categories = reload(request, input, mgmtProcess, process);
		}
		else if (input.isDeleteRequest()) {
			process.deleteRequest(input);
			forward = noForward;
		}
		
		if (categories == null) {
			categories = mgmtProcess.getNotificationCategories();
		}
		for (MfrNotificationCategoryBean category : categories) {
			if (category.isSLSTUpdate()) {
				request.setAttribute("shelfLifeBasisColl", process.getShelfLifeBasisColl());
			}
			request.setAttribute(category.getMfrReqCategoryDesc().replaceAll("\\W", "").toLowerCase(), 
					"category"+category.getMfrReqCategoryId());
			if (input.getMfrReqCategoryId() != null && 
					category.getMfrReqCategoryId().compareTo(input.getMfrReqCategoryId()) == 0) {
				request.setAttribute("category", category);
			}
		}
		
		request.setAttribute("now", new Date());
		return forward;
	}
}
