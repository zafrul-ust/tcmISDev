package com.tcmis.client.common.action;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONObject;

import com.tcmis.client.common.beans.CatalogQueueBean;
import com.tcmis.client.common.beans.MsdsIndexingBean;
import com.tcmis.client.common.beans.MsdsIndexingKitBean;
import com.tcmis.client.common.beans.MsdsIndexingRequestBean;
import com.tcmis.client.common.beans.MsdsLocaleViewBean;
import com.tcmis.client.common.process.MsdsIndexingProcess;
import com.tcmis.common.admin.beans.CompanyBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.Profile;
import com.tcmis.internal.catalog.beans.CatalogAddReqMsdsInputBean;
import com.tcmis.internal.catalog.beans.CatalogAddRequestOvBean;
import com.tcmis.internal.catalog.beans.CompositionBean;
import com.tcmis.internal.catalog.beans.GHSFieldsInputBean;
import com.tcmis.internal.catalog.beans.GHSStatementsViewBean;
import com.tcmis.internal.catalog.beans.ManufacturerBean;
import com.tcmis.internal.catalog.beans.MaterialBean;
import com.tcmis.internal.catalog.beans.PictogramBean;
import com.tcmis.internal.catalog.process.GHSFieldsProcess;
import com.tcmis.internal.catalog.process.MsdsAuthorSearchProcess;

import radian.tcmis.common.util.StringHandler;

public class MsdsIndexingAction extends TcmISBaseAction{
	
	private static final int DO_NOTHING_ACTION = 1000; 
	private final String[] uActionList = {
			"",
			"getMaterialQc",
			"getComponent",
			"getMsds",
			"getRevision",
			"getCoData",
			"viewKitDocs",
			CatalogAddReqMsdsInputBean.NEW_REVISION_ACTION,
			"update" // this must always come last
		};
	
	@SuppressWarnings("unchecked")
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws BaseException, Exception {
		
		ActionForward next = mapping.findForward("success");
		
		Locale locale = getTcmISLocale();
		PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");

		CatalogAddReqMsdsInputBean input = null;
		if (CatalogAddReqMsdsInputBean.NEW_REVISION_ACTION.equals(request.getParameter("uAction"))) {
			input = new CatalogAddReqMsdsInputBean(form, locale, "dateformat");
		}
		else {
			input = new CatalogAddReqMsdsInputBean(form, locale, "datetimeformat");
		}
		
		int uActionId = 0;
		for (int i = 1; i < uActionList.length; i++) {
			if (uActionList[i].equalsIgnoreCase(input.getuAction())) {
				uActionId = i;
			}
		}
		
		//msds pages have logics that are dependent on what parameters being passed.
		//For work queue view, qId must exists and requestId-lineItem must not. For request view, it's vice versa.
		if ((user.getPermissionBean().hasUserPagePermission("msdsMaintenance") && uActionId > 2) ||
				user.getPermissionBean().hasUserPagePermission("catalogAddProcess") ||
				user.getPermissionBean().hasUserPagePermission("catAddVendorQueue")) {
			try {
				GHSFieldsInputBean ghsInput = null;
				if ( ! input.isNewRevision()){
					// Get the user/page input in a usable form as an input bean
					ghsInput = new GHSFieldsInputBean(form, locale, "datetimeformat");
				}
				MsdsIndexingProcess process = new MsdsIndexingProcess(user.getPersonnelIdBigDecimal(), getDbUser());
				process.setProfile(this.getProfile().map(profile -> Profile.valueOf(profile)).orElse(Profile.PROD));
				GHSFieldsProcess ghsProcess = new GHSFieldsProcess(getDbUser(),
						locale);
				
				Collection<MsdsIndexingBean> msdsColl = null;
				CatalogAddRequestOvBean catAddReq = null;
				MsdsIndexingBean msds = null;
				if (input.isUpdate()) {
					uActionId = 3; // this will enable us to reload the SDS/MSDS Maintenance page
					MsdsIndexingRequestBean requestBean = new MsdsIndexingRequestBean();
					BeanHandler.copyAttributes(form, requestBean);
					if (input.getqId() == null) {
						if (requestBean.getRequestId() != null) {
							uActionId = 1; // reload the Material QC page instead of MSDS Maintenance
							process.updatePartDescription(requestBean);
						}
						MsdsIndexingKitBean kitBean = new MsdsIndexingKitBean();
						BeanHandler.copyAttributes(form, kitBean);
						if (kitBean.isAllowMixture()) {
							process.updateKitData(kitBean, requestBean);
						}
					}
					else {
						uActionId = 1;
					}
					Collection<MsdsIndexingBean> components = BeanHandler.getBeans((DynaBean) form, "msds", "datetimeformat", new MsdsIndexingBean(), locale);
					Collection<MsdsIndexingBean> coData = BeanHandler.getBeans((DynaBean) form, "co", "datetimeformat", new MsdsIndexingBean(), locale);
					Iterator<MsdsIndexingBean> coIterator = coData.iterator();

					boolean validMaterial = true;
                    String catAddReqGlobalDataEntryCompleteRequired = "";
                    String catAddReqCompanyDataEntryCompleteRequired = "";
                    int count = 0;
					for (Iterator<MsdsIndexingBean> i = components.iterator();i.hasNext();) {
						MsdsIndexingBean bean = i.next();
						// comments should be empty; if null, then this is a blank component, and we do not want it saved
						if (bean.getComments() == null) {
							count++;
							continue;
						}
						if ( ! (validMaterial = validateMaterialId(input, bean, process))) {
							// TODO, how does this message look?
							request.setAttribute("tcmisError", "Submit failed due to Material ID mismatch. This Task has been reloaded with the correct Material ID. Please use this Material ID to complete this Task.");
							continue;
						}
						if (bean.getMaterialId() != null) {
							if (count == 0) {
								input.setMaterialId(bean.getMaterialId());
								input.setRevisionDate(bean.getRevisionDate());
                                if (!StringHandler.isBlankString(bean.getCompanyId()))
                                    input.setCompanyId(bean.getCompanyId());
							}
							Collection<CompositionBean> compositionData = BeanHandler.getBeans((DynaBean) form, "compositionData"+count, "datetimeformat", new CompositionBean(), locale);
							Collection<PictogramBean> pictogramData = BeanHandler.getBeans((DynaBean) form, "msdspictogram"+count, "datetimeformat", new PictogramBean(), locale);
							Collection<GHSStatementsViewBean> hazardStmts = BeanHandler.getBeans((DynaBean) form, "msdshazards"+count, "datetimeformat", new GHSStatementsViewBean(), locale);
							Collection<GHSStatementsViewBean> precautionaryStmts = BeanHandler.getBeans((DynaBean) form, "msdsprecautions"+count, "datetimeformat", new GHSStatementsViewBean(), locale);
							bean.setCompositionData(compositionData);
							StringBuilder casErrors = new StringBuilder();
							for (CompositionBean composition : bean.getCompositionData()) {
								String casNumber = composition.getCasNumber();
								if (!StringHandler.isBlankString(casNumber)) {
			                        if (!casNumber.startsWith("TS") && !casNumber.startsWith("NI") && !process.isValidCasNumber(casNumber))
			                            casErrors.append(getResourceBundleValue(request, "label.invalidcasnumber") + " " + casNumber + "<BR/>\n");
			                    }
			                }
							if (casErrors.length() > 0) {
								request.setAttribute("tcmisError", casErrors.toString());
							}
							bean.setPictograms(pictogramData);
							bean.setHazardStmts(hazardStmts);
							bean.setPrecautionaryStmts(precautionaryStmts);
							if (coIterator.hasNext()) {
								bean.setCoData(coIterator.next());
							}
						}
						Optional<CatalogQueueBean> queueRow = Optional.empty();
						if (requestBean.getLineItem() != null) { 
							process.updateLineItemMSDS(bean, requestBean, new BigDecimal(count+1));
							process.insertCompanyMsdsForCatalogAdd(bean,requestBean);
						}
						else {
							queueRow = process.getCatalogQueueData(input);
							queueRow.ifPresent(qRow -> {
								MsdsIndexingRequestBean queueRequestBean = new MsdsIndexingRequestBean();
								queueRequestBean.setRequestId(qRow.getCatalogAddRequestId());
								queueRequestBean.setLineItem(qRow.getLineItem());
								try {
									process.updateLineItemMSDS(bean, queueRequestBean, qRow);
									process.insertCompanyMsdsForCatalogAdd(bean,queueRequestBean);
								} catch (BaseException be) {
									processExceptions(request, mapping, be);
								} catch (Exception e) {
									request.setAttribute("tcmisError", e.getMessage());
						            log.error(e);
								}
							});
						}

                        //this is to handle approving Material QC for catalog adds
                        if (input.isApprove() && requestBean.getRequestId() != null &&
                            ("Material QC".equals(input.getApprovalRole()) || "Custom Indexing".equals(input.getApprovalRole())
							  || "MSDS Indexing".equals(input.getApprovalRole()))) {
                            input.setSubmitForQc(true);
                        }

                        //now update msds data
                        process.updateMSDS(input, bean, user);
                        queueRow.ifPresent(qRow -> {
                        	try {
                        		process.updateWorkQueueItem(bean, qRow);
                        	} catch (BaseException be) {
								processExceptions(request, mapping, be);
							} catch (Exception e) {
								request.setAttribute("tcmisError", e.getMessage());
					            log.error(e);
							}
                        });
						//set it from the first component
		                //saving these parameters to use later in the isApprove section
		                if (input.isApprove() && (input.getMaterialId() == null || input.getRevisionDate() == null)) {
		                    input.setMaterialId(bean.getMaterialId());
		                    input.setRevisionDate(bean.getRevisionDate());
		                    input.setIdOnly(bean.getIdOnly());
		                }
						process.upsertReportSnap(bean.getMaterialId(), bean.getRevisionDate());
						String globalDataEntryCompleteRequired = process.globalDataEntryCompleteRequired();
						String companyDataEntryCompleteRequired = process.companyDataEntryCompleteRequired();
						String dataEntryCompleteRequired = "";
						if ("Custom Indexing".equals(input.getApprovalRole())) {
							if (!StringHandler.isBlankString(companyDataEntryCompleteRequired)) {
								dataEntryCompleteRequired += companyDataEntryCompleteRequired;
	                            catAddReqCompanyDataEntryCompleteRequired += companyDataEntryCompleteRequired;
	                        }
						}
						if ("Custom Indexing".equals(input.getApprovalRole()) || "MSDS Indexing".equals(input.getApprovalRole())) {
							if (!StringHandler.isBlankString(globalDataEntryCompleteRequired)) {
								dataEntryCompleteRequired = globalDataEntryCompleteRequired;
	                            catAddReqGlobalDataEntryCompleteRequired += globalDataEntryCompleteRequired;
	                        }
							if (!StringHandler.isBlankString(dataEntryCompleteRequired))
								request.setAttribute("tcmisError", dataEntryCompleteRequired);
						}

						count++;
					}
					
					boolean canApproveRequest = canApproveRequest(input, user, process, catAddReqGlobalDataEntryCompleteRequired, catAddReqCompanyDataEntryCompleteRequired);
					// Approve the MSDS if necessary
		            if (input.isApprove() && requestBean.getRequestId() != null && input.getqId() == null) {
		        		if (canApproveRequest) {
		        			process.approveRequest(input, user);
		                    request.setAttribute("msdsApproved", "true");
		                    uActionId = DO_NOTHING_ACTION; // do not get anything else; we do not reload an approved record
		        		}
		            }
		            else if ((input.isApprove() || input.isSubmitForQc()) && validMaterial && input.hasqId()) {
		            	process.advanceCatalogQueue(input, user);
		            	request.setAttribute("queueAdvanced", "true");
		            	if (input.isPendingApproval() && canApproveRequest && !process.isRequestHasUnapprovedQueue(input)) {
		            		process.approveRequest(input, user);
		                    request.setAttribute("msdsApproved", "true");
		            	}
		            	uActionId = DO_NOTHING_ACTION;
		            }
				}else if (input.isRejectOutOfScope()) {
					// Reject Out of Scope
					if (input.getRequestId() != null) {
						process.rejectOutOfScope(input,user);
						request.setAttribute("msdsApproved", "true");
						uActionId = DO_NOTHING_ACTION; // do not get anything else; we do not reload an rejected record
					}
				}else if (input.isRejectCannotFulfill()) {
					// Reject Cannot Fulfill Request
					if (input.getRequestId() != null) {
						process.rejectCannotFulfill(input,user);
						request.setAttribute("msdsApproved", "true");
						uActionId = DO_NOTHING_ACTION; // do not get anything else; we do not reload an rejected record
					}
				}
				else if (input.isNewRevision()){
					JSONObject json = new JSONObject();
					json.put("revisionDate", DateHandler.formatDate(process.getNewRevisionDate(input.getMaterialId().toString(), input.getRevisionDate(), ""),"dd-MMM-yyyy HH:mm a"));
					String localeCode = StringHandler.isBlankString(input.getqLocaleCode())?getTcmISLocale().toString():input.getqLocaleCode();
					json.put("content", process.getNotRequiredContent(localeCode, input.getSdsRequired()));
					json.put("imageLocaleCode", localeCode);
					PrintWriter out = response.getWriter();
					out.write(json.toString(3));
					out.flush();
					out.close();
					return noForward;
				}
				else if (input.isViewRequestAction()) {
					request.setAttribute("requestHeader", process.getQcOriginalHeader(input));
					request.setAttribute("resultsCollection", process.getQcOriginalInfo(input));
					uActionId = DO_NOTHING_ACTION;
					next = mapping.findForward("qcoriginal");
				}
				else if (input.isChangeLocaleAction()) {
					process.overrideWorkQueueItemLocale(input);
					uActionId = 1;
				}

				Optional<CatalogQueueBean> queueRow = process.getCatalogQueueData(input);
				// define uActions in the uActionList array
				switch (uActionId) { 
				case 1: // getMaterialQc
					if (queueRow.isPresent()) {
						input.setRequestId(queueRow.get().getCatalogAddRequestId().toString());
						input.setCatalogAddItemId(queueRow.get().getCatalogAddItemId());
						input.setqLocaleCode(queueRow.get().getLocaleCode());
						//since vendor view now also has reject buttons, need to add this flag
						request.setAttribute("hasOutOfScopeFeature", process.getRejectOutOfScopeFeature(queueRow.get().getCatalogAddRequestId()));
					}
					
					if ( ! queueRow.filter(q -> ! q.isClosedStatus()).isPresent()) {
						catAddReq = process.getRequest(input);
						request.setAttribute("chemRequest", catAddReq);
						BigDecimal chemRequestStatus = catAddReq == null ? null : catAddReq.getRequestStatus();
						//added new attribute as chemRequest attribute also used as string type in case 3
						request.setAttribute("chemRequestStatus", chemRequestStatus);
						request.setAttribute("kitData", process.getKitData(input));
						request.setAttribute("tabs", process.getNumComponents(input));
						//since catAddReq can be null, needs to check
						BigDecimal catAddReqId = catAddReq == null ? null : catAddReq.getRequestId();
						request.setAttribute("beforeOrAfterSdsQc", process.getApprovalRoleBefore(catAddReqId, "SDS QC"));
						request.setAttribute("hasOutOfScopeFeature", process.getRejectOutOfScopeFeature(catAddReqId));
						//since catAddReq can be null, needs to check
						String engEvalFacilityId = catAddReq == null ? null : catAddReq.getEngEvalFacilityId();
						String companyId = catAddReq == null ? null : catAddReq.getCompanyId();
						input.setGenerateSdsFromSequence(user.isFeatureReleased("GenerateSdsFromSequence",engEvalFacilityId,companyId)?"true":"false");
					}
					input.setuAction("getComponent");
				case 2: // getComponent
					if (queueRow.isPresent() && queueRow.get().isAuthoringTask()) {
						msdsColl = process.getMsdsByAuthoringRequest(input, queueRow.get());
					}
					else {
						msdsColl = process.getMsdsByRequest(input, new BigDecimal(StringHandler.zeroIfNull(request.getParameter("component"))));
						request.setAttribute("generateSdsFromSequence",input.getGenerateSdsFromSequence());
					}
					if (msdsColl != null) {
						Iterator<MsdsIndexingBean> it = msdsColl.iterator();
						if (it.hasNext()) {
							input.setMaterialId(it.next().getMaterialId());
						}
					}
					input.setuAction("getMsds");
				case 3: // getMsds
					if (queueRow.filter(q -> ! q.isClosedStatus()).isPresent()) {
						request.setAttribute("facilityLocales", process.getFacilityLocales(queueRow.get()));
					}
					else if (catAddReq == null && input.getRequestId() != null) { 
						request.setAttribute("chemRequest", input.getRequestId());
					}
					Collection<MsdsLocaleViewBean> revisions = process.getAvailableRevDates(input, input.hasqId());
					if (revisions != null) {
						request.setAttribute("revisions", revisions);
						Iterator<MsdsLocaleViewBean> revIterator = revisions.iterator();
						if (input.getRevisionDate() == null && revIterator.hasNext()) {
							input.setRevisionDate(revIterator.next().getRevisionDate());
						}
					}
					input.setuAction("getRevision");
				case 4: // getRevision
					Collection<CompanyBean> customerCompanies = new ArrayList<CompanyBean>();
					if ((catAddReq == null) && ( ! StringHandler.isBlankString(input.getRequestId()))) {
						catAddReq = process.getRequest(input);
					}
					
					// if no request id, we aren't looking at the cat add QC page
					if ( ! queueRow.isPresent()) {
						if (catAddReq == null || catAddReq.getRequestId() == null) {
							customerCompanies = process.getCustomerOverrideCompanies(input.getMaterialId());
						}
						else {
							CompanyBean comp = new CompanyBean();
							if (process.isRequestForCompanyWithMSDS(input.getRequestId())) {
								if (catAddReq != null) {
									comp.setCompanyId(catAddReq.getCompanyId());
									comp.setCompanyName(catAddReq.getCompanyName());
								}
								customerCompanies.add(comp);
							}
							else {
								comp.setCompanyId(user.getCompanyId());
								comp.setCompanyName(user.getCompanyName());
							}
						}
					}
					
					Iterator<CompanyBean> ccIterator = customerCompanies.iterator();
					if (input.getCompanyId() == null && ccIterator.hasNext()) {
						input.setCompanyId(ccIterator.next().getCompanyId());
					}
					
					if (msdsColl == null) {
						msdsColl = process.getMsdsByMaterialRevision(input, new BigDecimal(StringHandler.zeroIfNull(request.getParameter("component"))));
					}
					Iterator<MsdsIndexingBean> it = msdsColl.iterator();
					// if there is an MSDS, then grab it
					if (it.hasNext()) {
						msds = it.next();
						loadMsds(msds, input, ghsInput, process, ghsProcess, queueRow);
					}
					// if there is no MSDS, it may mean a new material
					else if (input.getMaterialId() != null) {
						msds = new MsdsIndexingBean();
						msds.setMaterialId(input.getMaterialId());
						loadMsds(msds, input, ghsInput, process, ghsProcess, queueRow);
					}
					
					if ( ! StringHandler.isBlankString(input.getRequestId()) && msds != null){
						process.fillRequestData(input, msds, new BigDecimal(StringHandler.zeroIfNull(request.getParameter("component"))));
					}
					request.setAttribute("customerCompanies", customerCompanies);
					request.setAttribute("msds",msds);
					request.setAttribute("msdsIndexingPriorityColl",process.getMsdsIndexingPriority("catalogAddOrder"));
					request.setAttribute("signalWordColl", ghsProcess.getSignalWords());
					request.setAttribute("sdsSectionColl", ghsProcess.getSdsSectionColl());
					if ("TCM_OPS".equalsIgnoreCase(this.getDbUser(request))) {
						request.setAttribute("idOnlyColl", ghsProcess.getIdOnlyColl());
						request.setAttribute("userApprover", process.userIsApprover(user, null));
					}
					if (msds != null && msds.getCompanyId() != null) {
						request.setAttribute("userCoApprover", process.userIsApprover(user, msds.getCompanyId()));
						request.setAttribute("coDataStandard", process.getDataEntryStandard(msds.getCompanyId()));
					}
					request.setAttribute("globalDataStandard", process.getDataEntryStandard(null));
					request.setAttribute("vvFlashPointMethodCollection",process.setFlashPointMethod());
					request.setAttribute("vvQcErrorTypeCollection", process.getQcErrorTypes());
					if ( !StringUtils.isBlank(input.getRequestId())) {
						request.setAttribute("enableMaxcomIndexing", process.getIdOnlyDisplayValue(new BigDecimal(input.getRequestId())));
						request.setAttribute("catalogFacilityMsdsIndexingPriorityId",process.getCatalogFacilityMsdsIndexingPriority(input.getRequestId()));
					}
					if (msds != null && msds.getMfg() != null) {
						MsdsAuthorSearchProcess authorProcess = new MsdsAuthorSearchProcess(this.getDbUser(request));
						request.setAttribute("mfrAuthor", authorProcess.getMsdsAuthor(msds.getMfg().getMfgId()));
					}
					request.setAttribute("catalogQueueRow", queueRow.orElse(null));
					break;
				case 5: // getCoData
					if ( ! StringHandler.isBlankString(input.getCompanyId())) {
						Collection<MsdsIndexingBean> coDataColl = process.getCustomerOverrideMsds(input);
						if ( coDataColl != null && ! coDataColl.isEmpty()) {
							msds = new MsdsIndexingBean();
							msds.setMaterialId(input.getMaterialId());
							msds.setRevisionDate(input.getRevisionDate());
							msds.setCompanyId(input.getCompanyId());
							msds.setCoData(coDataColl.iterator().next());
							process.fillInQcData(msds, true);
							process.fillInQcData(msds.getCoData(), true);
							request.setAttribute("msds",msds);
							request.setAttribute("coDataStandard", process.getDataEntryStandard(msds.getCompanyId()));
							request.setAttribute("userCoApprover", process.userIsApprover(user, msds.getCompanyId()));
							request.setAttribute("vvQcErrorTypeCollection", process.getQcErrorTypes());
						}
					}
					request.setAttribute("vvFlashPointMethodCollection",process.setFlashPointMethod());
					break;
				case 6:
		        	String results =  process.getKitDocs(input);
					if(results.length() > 0) {
						String[] splits = results.split("\n");
						request.setAttribute("fileList",splits);
					}
		            next = mapping.findForward("kitDocs");
		            break;
				default:
					msdsColl = new ArrayList<MsdsIndexingBean>();
					// make a list with a dummy bean so that the search panel appears
					msdsColl.add(new MsdsIndexingBean());
					request.setAttribute("msdsCollection", msdsColl);
				}
			}
			catch (BaseException be) {
				processExceptions(request, mapping, be);
			}
			catch (Exception e) {
				request.setAttribute("tcmisError", e.getMessage());
	            log.error(e);
			}
		}
		
		return next;
	}
	
	private void loadMsds(MsdsIndexingBean msds, CatalogAddReqMsdsInputBean input, GHSFieldsInputBean ghsInput, MsdsIndexingProcess process, GHSFieldsProcess ghsProcess, Optional<CatalogQueueBean> workQueueItem) throws BaseException {
		if (msds.getMaterialId() != null) {
			//if (msds.getMsdsId() != null) {
				ghsInput.setMsdsId(msds.getMsdsId());
				
				ghsInput.setCodeAbbrev("H");
				Collection<GHSStatementsViewBean> hazardStmts = ghsProcess.getStatements(ghsInput);
				msds.setHazardStmts(hazardStmts);
				
				ghsInput.setCodeAbbrev("P");
				Collection<GHSStatementsViewBean> precautionStmts = ghsProcess.getStatements(ghsInput);
				msds.setPrecautionaryStmts(precautionStmts);
			//}
			
			msds.setPictograms(process.getGhsPictograms(msds.getMaterialId(), msds.getRevisionDate()));
			
			input.setMaterialId(msds.getMaterialId());
			Collection<ManufacturerBean> mfgColl = process.getManufacturerByMaterialId(input);
			if (mfgColl != null && ! mfgColl.isEmpty()) {
				msds.setMfg(mfgColl.iterator().next());
			}
			Collection<MaterialBean> materialColl = process.getMaterialById(input);
			if (materialColl != null && ! materialColl.isEmpty()) {
				msds.setMaterial(materialColl.iterator().next());
			}
			if (workQueueItem.isPresent()) {
				Collection<MaterialBean> localizedMaterialColl = process.getLocalizedMaterialById(input, workQueueItem.get().getLocaleCode());
				if (localizedMaterialColl != null && ! localizedMaterialColl.isEmpty()) {
					msds.setLocalizedMaterial(localizedMaterialColl.iterator().next());
				}
			}
			else if ( ! StringHandler.isBlankString(msds.getContent())) {
				Collection<MaterialBean> localizedMaterialColl = process.getLocalizedMaterialById(input, msds.getLocaleCode()==null?msds.getImageLocaleCode():msds.getLocaleCode());
				if (localizedMaterialColl != null && ! localizedMaterialColl.isEmpty()) {
					msds.setLocalizedMaterial(localizedMaterialColl.iterator().next());
				}
			}
	
			Collection<MsdsIndexingBean> coDataColl = process.getCustomerOverrideMsds(input);
			if (coDataColl != null && ! coDataColl.isEmpty()) {
				msds.setCompanyId(input.getCompanyId());
				msds.setCoData(coDataColl.iterator().next());
				process.fillInQcData(msds.getCoData(), true);
				msds.setCompanyId(input.getCompanyId());
			}
			
			if (msds.getRevisionDate() != null) {
				msds.setCompositionData(process.getCompositionBeans(msds.getMaterialId(), msds.getRevisionDate()));
				process.fillInQcData(msds, false);
			}
		}
	}
	
	private boolean validateMaterialId(CatalogAddReqMsdsInputBean input, MsdsIndexingBean msds, MsdsIndexingProcess process) throws BaseException {
		boolean valid = true;
		
		Optional<BigDecimal> lockedInMaterialId = process.lockedInMaterialId(input);
		if (input.isSourcing() && lockedInMaterialId.isPresent()) {
			valid = (0 == msds.getMaterialId().compareTo(lockedInMaterialId.get()));
		}
		return valid;
	}
	
	private boolean canApproveRequest(CatalogAddReqMsdsInputBean input, PersonnelBean user, MsdsIndexingProcess process, String catAddReqGlobalDataEntryCompleteRequired, String catAddReqCompanyDataEntryCompleteRequired) throws BaseException {
		boolean success = false;
		if (StringHandler.isBlankString(catAddReqGlobalDataEntryCompleteRequired)) {
            if ("Custom Indexing".equals(input.getApprovalRole()) || "MSDS Indexing".equals(input.getApprovalRole())) {
                //then user is required to do complete company data
                if (StringHandler.isBlankString(catAddReqCompanyDataEntryCompleteRequired)) {
                    //company data entry complete not required then go and approve request
                    success = true;
                } else
                    request.setAttribute("tcmisError", catAddReqCompanyDataEntryCompleteRequired);
            } else {
                //global data entry complete not required then go and approve request
                success = true;
            }
        } else
            request.setAttribute("tcmisError", catAddReqGlobalDataEntryCompleteRequired);
		
		return success;
	}
}
