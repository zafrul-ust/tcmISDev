package com.tcmis.internal.hub.action;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONObject;

import com.tcmis.common.admin.beans.AutocompleteInputBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.beans.RightMouseClickMenuBean;
import com.tcmis.common.admin.beans.VvCountryBean;
import com.tcmis.common.admin.process.RightMouseClickMenuProcess;
import com.tcmis.common.admin.process.VvDataProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ProcessFactory;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.GHSLabelReqsBean;
import com.tcmis.internal.hub.beans.ReceiptDescriptionViewBean;
import com.tcmis.internal.hub.beans.ReceiptDocumentInputBean;
import com.tcmis.internal.hub.beans.ReceiptDocumentViewBean;
import com.tcmis.internal.hub.beans.ReceivingInputBean;
import com.tcmis.internal.hub.beans.VvLotStatusBean;
import com.tcmis.internal.hub.process.ReceiptDocumentProcess;
import com.tcmis.internal.hub.process.ReceivingQcCheckListProcess;
import com.tcmis.internal.hub.process.ReceivingQcProcess;
import com.tcmis.ws.tablet.beans.TabletInputBean;
import com.tcmis.ws.tablet.process.ReceiptProcess;

public class ReceivingQcCheckListAction extends TcmISBaseAction {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		String next = "success";
		// QvrPdfThread qvrPdfThread = null;
		if (!isLoggedIn(request)) {
			request.setAttribute("requestedPage", "receivingqcchecklist");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			next = "login";
		}
		else {
			PersonnelBean user = (PersonnelBean) this.getSessionObject(request, "personnelBean");
			Locale locale = getTcmISLocale();

			ReceiptDocumentProcess receiptDocumentProcess = new ReceiptDocumentProcess(getDbUser(request), getTcmISLocaleString(request));
			ReceivingQcCheckListProcess receivingQcCheckListProcess = ProcessFactory.of(ReceivingQcCheckListProcess.class)
					.generate(getDbUser(request), locale)
					.orElse(new ReceivingQcCheckListProcess(getDbUser(request), locale));
			ReceivingQcProcess receivingQcProcess = new ReceivingQcProcess(getDbUser(request), getTcmISLocaleString(request));
			VvDataProcess vvDataProcess = new VvDataProcess(getDbUser(request), locale);

			ReceivingInputBean input = new ReceivingInputBean();
			BeanHandler.copyAttributes(form, input, locale);

			if (input.hasUserAction()) {
				if (input.isConfirmChecklist()) {
					ReceiptDescriptionViewBean viewBean = new ReceiptDescriptionViewBean();
					BeanHandler.copyAttributes(form, viewBean, locale);

					Vector<ReceiptDescriptionViewBean> gridBeans = (Vector<ReceiptDescriptionViewBean>) BeanHandler.getBeans((DynaBean) form, "componentBean",
							new ReceiptDescriptionViewBean(), locale);
					Vector<ReceiptDescriptionViewBean> flatBeans = new Vector<ReceiptDescriptionViewBean>();
					for (ReceiptDescriptionViewBean bean : gridBeans) {
						ReceiptDescriptionViewBean tmpBean = new ReceiptDescriptionViewBean();
						BeanHandler.copyAttributes(viewBean, tmpBean, locale);
						tmpBean.setMaterialDesc(bean.getMaterialDesc());
						tmpBean.setPackaging(bean.getPackaging());
						tmpBean.setMfgLot(bean.getMfgLot());
						tmpBean.setExpireDate(bean.getExpireDate());
						tmpBean.setComponentId(bean.getComponentId());
						tmpBean.setMfgLabelExpireDate(bean.getMfgLabelExpireDate());
						tmpBean.setDateOfRepackaging(bean.getDateOfRepackaging());
						tmpBean.setOk("ok");
						tmpBean.setUpdateStatus("");
						if (tmpBean.getQualityControlItem() != null && "Y".equalsIgnoreCase(tmpBean.getQualityControlItem().trim())) {
							Collection vvLotStatusBeanCollection = new VvDataProcess(getDbUser()).getVvLotStatus();
							Iterator vvLotStatusIterator = vvLotStatusBeanCollection.iterator();
							while (vvLotStatusIterator.hasNext()) {
								VvLotStatusBean vvLotStatusBean = (VvLotStatusBean) vvLotStatusIterator.next();
								String lotStatus = vvLotStatusBean.getLotStatus();
								if (lotStatus.equalsIgnoreCase(tmpBean.getLotStatus())) {
									String certified = vvLotStatusBean.getCertified();
									if ("Y".equalsIgnoreCase(certified)) {
										tmpBean.setCertificationUpdate("Yes");
									}
								}
							}
						}
						flatBeans.add(tmpBean);
					}

					Collection relatedBeans = receivingQcProcess.createRelationalObject(flatBeans);
					input.setSubmitReceive("submitReceive");
					input.setCategory("chemicals");
					HashMap receivedQcReceiptResult = receivingQcProcess.receiveSelected(input, relatedBeans, user);
					String errorMessage = (String) receivedQcReceiptResult.get("ERRORMESSAGE");

					if (StringHandler.isBlankString(errorMessage)) {
						if (input.isBinnedChecked()) {
							TabletInputBean binningBean = new TabletInputBean(form, getLocale());
							new ReceiptProcess(getDbUser()).markReceiptBinned(binningBean, user);
						}

						receivingQcCheckListProcess.printChecklist(input, viewBean, user);

						receivingQcCheckListProcess.setWmsSynchronized(false, input);
					}
					
					request.setAttribute("tcmISErrors", errorMessage);
				}
				else if (input.isRevertStatus()) {
					ReceiptDescriptionViewBean viewBean = new ReceiptDescriptionViewBean();
					BeanHandler.copyAttributes(form, viewBean, locale);
					Integer revertResults = receivingQcCheckListProcess.revertStatus(viewBean);
					if (revertResults == null)
						request.setAttribute("tcmISErrors", getResourceBundleValue(request, "error.db.update"));
				}
				else if (input.isPrintChecklist()) {
					ReceiptDescriptionViewBean viewBean = new ReceiptDescriptionViewBean();
					BeanHandler.copyAttributes(form, viewBean, locale);
					receivingQcCheckListProcess.printChecklist(input, viewBean, user);
				}
				else if (input.isDeleteImg()) {
					ReceiptDocumentInputBean receiptDocumentInputBean = new ReceiptDocumentInputBean();
					BeanHandler.copyAttributes(form, receiptDocumentInputBean);
					String msg = receiptDocumentProcess.deleteImage(receiptDocumentInputBean);
					PrintWriter out = response.getWriter();
					out.write(msg);
					out.close();
					return noForward;
				}
				else if (input.isRecalcMinExpDate()) {
					ReceiptDocumentInputBean receiptDocumentInputBean = new ReceiptDocumentInputBean();
					BeanHandler.copyAttributes(form, receiptDocumentInputBean, locale);
					String minExpDate = receivingQcCheckListProcess.recalculateMinExpDate(receiptDocumentInputBean);
					PrintWriter out = response.getWriter();
					new JSONObject("{minExpDate:'" + minExpDate + "'}").write(out);
					out.close();
					return noForward;
				}
				else if (input.isUpdateExpireDate()) {
					ReceiptDescriptionViewBean viewBean = new ReceiptDescriptionViewBean();
					BeanHandler.copyAttributes(form, viewBean, locale);
					receivingQcCheckListProcess.updateExpireDate(viewBean);
					return noForward;
				}				
				else if (input.isCheckAlreadyAssociated()) {
					ReceiptDocumentInputBean documentInput = new ReceiptDocumentInputBean(form);
					Collection<ReceiptDocumentViewBean> existingAssociations = receivingQcCheckListProcess.getAlreadyAssociatedDocs(documentInput.getDocumentUrl());

					JSONObject results = new JSONObject();
					if (existingAssociations.isEmpty()) {
						results.put("associated", false);
					}
					else {
						try {
							ResourceLibrary reslibrary = new ResourceLibrary("com.tcmis.common.resources.CommonResources", getLocale());
							results.put("associated", true);
							StringBuilder msg = new StringBuilder();
							for (ReceiptDocumentViewBean bean : existingAssociations) {
								if (bean.getReceiptId().equals(documentInput.getReceiptId())) {
									msg.append(reslibrary.format("error.rdocalreadyassociatedtothisreceipt", "" + documentInput.getDocumentId(), "" + documentInput.getReceiptId(),
											bean.getDocumentType())).append(".\n");
								}
								else {
									msg.append(reslibrary.format("error.rdocalreadyassociatedtootherreceipt", "" + documentInput.getDocumentId(), "" + bean.getReceiptId(),
											bean.getDocumentType())).append(".\n");
								}
							}
							results.put("msg", msg.toString());
						}
						catch (Exception e) {
							log.error("Error checking file association", e);
							results.put("error", e.getMessage());
						}
					}
					PrintWriter out = response.getWriter();
					out.write(results.toString(3));
					out.close();
					return noForward;
				}
				else if (input.isGetSimilarCountry()) {
					AutocompleteInputBean autocompleteInputBean = new AutocompleteInputBean(request);
					BeanHandler.copyAttributes(form, autocompleteInputBean, getTcmISLocale(request));
					Collection<VvCountryBean> results = receivingQcCheckListProcess.getSimilarCountries(autocompleteInputBean);
					
					response.setCharacterEncoding("utf-8");
					PrintWriter out = response.getWriter();
					for (VvCountryBean bean : results) {
						out.println(bean.getCountryAbbrev() + autocompleteInputBean.getFieldSeparator() + bean.getCountry());
					}
					out.close();
					
					return noForward;
				} else {
					Collection<ReceiptDocumentInputBean> beans = BeanHandler.getBeans((DynaBean) form, "inboundDocBean", new ReceiptDocumentInputBean(), locale);
					Vector<String> error = new Vector<String>();
					boolean oneDocToReceipt = false;
					boolean oneDocAddedToShipId = false;
					for (ReceiptDocumentInputBean bean : beans) {

						if (bean.getIsAddDocRow().equalsIgnoreCase("n")) {
							// Make sure this doc isn't already associated to
							// this receipt as this type.
							boolean alreadyAssociated = false;
							Collection<ReceiptDocumentViewBean> existingAssociations = receivingQcCheckListProcess.getAlreadyAssociatedDocs(bean.getFileName());
							for (ReceiptDocumentViewBean assocBean : existingAssociations) {
								if (bean.getReceiptId().equals(assocBean.getReceiptId()) && bean.getDocumentType().equals(assocBean.getDocumentType())) {
									alreadyAssociated = true;
									break;
								}
							}
							if (alreadyAssociated) {
								continue;
							}

							try {
								bean.setEnteredBy(user.getPersonnelIdBigDecimal());
								if (!StringHandler.isBlankString(bean.getDocumentName())) {
									String addResult = receiptDocumentProcess.addNewDatabaseDocumentRecord(bean);

									if (bean.getDocumentType().equals("MSDS")) {
										addResult = receiptDocumentProcess.addMSDSDocumentRecord(bean);
									}

									if ("success".equals(addResult)) {
										oneDocToReceipt = true;
									}
									else {
										error.add("Error adding document " + bean.getFileName() + ", document not added to Receipt.\n" + addResult + "\n");
									}
								}
							}
							catch (Exception e) {
								error.add("Error adding document " + bean.getFileName() + ", document not added to Receipt.\n" + e.getMessage() + "\n");
							}
						}
						else {

							try {
								String errColl = receivingQcCheckListProcess.associateShipmentDocument(bean, user.getPersonnelIdBigDecimal());
								if (!StringHandler.isBlankString(errColl))
									error.add(errColl);
								else
									oneDocAddedToShipId = true;
							}
							catch (Exception e) {
								error.add("Error addingInbound Shipment Document Id" + bean.getInboundShipmentDocumentId() + ", document not added to Receipt.\n");
							}
						}
					}

					// Do the update and set any errors into the request
					// request.setAttribute("tcmISErrors", error);
					JSONObject results = new JSONObject();

					if (error.size() > 0)
						results.put("Message", error);
					else {
						if (oneDocToReceipt) {
							ReceiptDocumentInputBean docInputBean = new ReceiptDocumentInputBean();
							docInputBean.setReceiptId(new BigDecimal(input.getSearch()));
							Collection<ReceiptDocumentViewBean> docCollection = receiptDocumentProcess.getTableSearchResult(docInputBean);
							JSONArray resultsArray = new JSONArray();
							for (ReceiptDocumentViewBean bean : docCollection) {
								JSONObject docJSON = new JSONObject();
								docJSON.put("documentType", bean.getDocumentType());
								docJSON.put("companyId", bean.getCompanyName());
								docJSON.put("documentName", bean.getDocumentName());
								docJSON.put("documentDate", bean.getDocumentDate());
								docJSON.put("documentUrl", bean.getDocumentUrl());
								docJSON.put("documentId", bean.getDocumentId());
								docJSON.put("receiptId", bean.getReceiptId());
								resultsArray.put(docJSON);
							}
							results.put("DocToReceiptResults", resultsArray);
						}
						if (oneDocAddedToShipId)
							results.put("AddedToShipIdResults", "Y");
					}

					// Write out the data
					PrintWriter out = response.getWriter();
					out.write(results.toString(3));
					out.close();
					return noForward;
				}
			}

			Vector<ReceiptDescriptionViewBean> results = (Vector<ReceiptDescriptionViewBean>) receivingQcCheckListProcess.getHeaderInfo(input.getSearch());
			Vector<GHSLabelReqsBean> ghsLabelReqs = (Vector<GHSLabelReqsBean>) receivingQcCheckListProcess.getGHSLabelReqs(input.getSearch());
			if (results == null) {
				request.setAttribute("tcmISErrors", getResourceBundleValue(request, "error.db.select"));
				return (mapping.findForward(next));
			}
			// incoming lab test
			ReceiptDescriptionViewBean tmpBean = (ReceiptDescriptionViewBean) results.firstElement();
			if ("Y".equals(tmpBean.getIncomingTesting())) {
				RightMouseClickMenuProcess process = new RightMouseClickMenuProcess(this.getDbUser(request));
				RightMouseClickMenuBean inputBean = new RightMouseClickMenuBean();
				inputBean.setReceiptId(new BigDecimal(input.getSearch()));
				inputBean.setPersonnelId(user.getPersonnelIdBigDecimal());
				RightMouseClickMenuBean bean = process.getIncomingTestRequired(inputBean);
				tmpBean.setIncomingTestRequired(bean.getLabTestRequired());
				tmpBean.setTestRequestId(bean.getTestRequestId());
				if ("Y".equals(bean.getLabTestRequired()))
					tmpBean.setLabTestComplete("N");
				else
					tmpBean.setLabTestComplete(receivingQcCheckListProcess.getLabTestComplete(tmpBean.getReceiptId()));
			}
			else {
				tmpBean.setLabTestComplete("Y");
			}
			request.setAttribute("headerInfo", results);
			request.setAttribute("ghsLabelReqs", ghsLabelReqs);
			request.setAttribute("isWmsInventoryGroup", receivingQcCheckListProcess.isWmsInventoryGroup(results.firstElement().getInventoryGroup()));
			ReceiptDescriptionViewBean requeryData = results.firstElement();

			if (requeryData.isReceivingStatusQcReady()) {
				receivingQcCheckListProcess.updateAssignedIfNull(user, requeryData.getReceiptId());
			}

			// get the defined shelf life item
			String definedShelfLifeItem = receivingQcCheckListProcess.getDefinedShelfLifeItem(requeryData.getReceiptId());
			request.setAttribute("definedShelfLifeItem", definedShelfLifeItem);

			String branchPlant = requeryData.getBranchPlant();

			if (!StringHandler.isBlankString(branchPlant)
					&& (branchPlant.equalsIgnoreCase("2101") || branchPlant.equalsIgnoreCase("2102") || branchPlant.equalsIgnoreCase("2103") || branchPlant.equalsIgnoreCase("2304"))) {
				request.setAttribute("showHubSpecificNotes", "Y");
				Collection hubSpecificNotes = receivingQcCheckListProcess.getHubSpecificNotes(requeryData.getItemId(), branchPlant);
				if (hubSpecificNotes == null)
					request.setAttribute("tcmISErrors", getResourceBundleValue(request, "error.db.select"));
				request.setAttribute("hubSpecificNotesColl", hubSpecificNotes);
			}

			String docType = requeryData.getDocType();
			Collection vvReceiptDocumentType = receivingQcCheckListProcess.getVvReceiptDocumentType();
			if (vvReceiptDocumentType == null)
				request.setAttribute("tcmISErrors", getResourceBundleValue(request, "error.db.select"));
			request.setAttribute("vvReceiptDocumentTypeBeanCollection", vvReceiptDocumentType);

			request.setAttribute("companyIdsCollection", vvDataProcess.getCompanyIdsForInventoryGroup(requeryData.getInventoryGroup()));

			Collection buildDocs = receivingQcCheckListProcess.buildDocs(requeryData);
			if (buildDocs == null)
				request.setAttribute("tcmISErrors", getResourceBundleValue(request, "error.db.select"));
			request.setAttribute("inboundDocDetailColl", buildDocs);

			ResourceLibrary resource = new ResourceLibrary("receiptdocument");
			String documentLocationUrl = resource.getString("receipt.documentfile.hosturl") + resource.getString("receipt.documentfile.urlpath");
			request.setAttribute("documentLocationUrl", documentLocationUrl);
			requeryData.setHub(input.getSourceHub());

			Collection poNotesColl = null;
			Collection customerOrderInternalOrderNotes = null;
			if (requeryData.getRadianPo() != null && (!docType.equalsIgnoreCase("IT") && !docType.equalsIgnoreCase("IA"))) {
				poNotesColl = receivingQcCheckListProcess.getPoNotes(requeryData);
				customerOrderInternalOrderNotes = receivingQcCheckListProcess.getCustomerOrderInternalOrderNotes(requeryData);

				if (poNotesColl == null)
					request.setAttribute("tcmISErrors", getResourceBundleValue(request, "error.db.select"));
				request.setAttribute("poNotesColl", poNotesColl);

				if (customerOrderInternalOrderNotes == null)
					request.setAttribute("tcmISErrors", getResourceBundleValue(request, "error.db.select"));
				request.setAttribute("customerOrderInternalOrderNotes", customerOrderInternalOrderNotes);
			}

			String saicPOClauses = null;
			Collection saicPONotes = null;
			Collection saicVerifiedInfo = null;
			if (!StringHandler.isBlankString(requeryData.getPolchemIg()) && requeryData.getPolchemIg().equalsIgnoreCase("Y") && !StringHandler.isBlankString(docType)
					&& docType.equalsIgnoreCase("IA") && !StringHandler.isBlankString(requeryData.getPoNumber())) {
				saicPOClauses = receivingQcCheckListProcess.getSaicPOClauses(requeryData);
				saicPONotes = receivingQcCheckListProcess.getSaicPONotes(saicPOClauses);
				if (!StringHandler.isBlankString(requeryData.getCatPartNo()))
					saicVerifiedInfo = receivingQcCheckListProcess.getSaicPONotesVerified(requeryData.getCatPartNo());

				if (saicPOClauses == null || saicPONotes == null)
					request.setAttribute("tcmISErrors", getResourceBundleValue(request, "error.db.select"));
				request.setAttribute("saicPONotes", saicPONotes);
				request.setAttribute("saicVerifiedInfo", saicVerifiedInfo);
			}

			Collection engEval = receivingQcCheckListProcess.getEngineeringEval(requeryData);
			if (engEval == null)
				request.setAttribute("tcmISErrors", getResourceBundleValue(request, "error.db.select"));
			request.setAttribute("engEvalColl", engEval);

			ReceiptDocumentInputBean receiptDocumentInputBean = new ReceiptDocumentInputBean();
			receiptDocumentInputBean.setReceiptId(requeryData.getReceiptId());

			request.setAttribute("receiptDocDetailColl", receiptDocumentProcess.getTableSearchResult(receiptDocumentInputBean));
			request.setAttribute("imageDocDetailColl", receiptDocumentProcess.getImageSearchResult(receiptDocumentInputBean));
			Map<String, String> igDetails = receivingQcCheckListProcess.getInventoryGroupDetails(requeryData.getInventoryGroup());
			request.setAttribute("calculateQvrExpDate", igDetails.get("calculateQvrExpDate"));
			request.setAttribute("editShelfLife", igDetails.get("editShelfLife"));

			saveTcmISToken(request);
		}

		return (mapping.findForward(next));
	}
}
