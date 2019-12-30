package com.tcmis.client.common.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.ModuleUtils;

import com.tcmis.client.catalog.beans.SpecDisplayViewBean;
import com.tcmis.client.common.beans.DocViewerInputBean;
import com.tcmis.client.common.process.DocViewerProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * Controller for login
 * 
 * @version 1.0
 ******************************************************************************/
public class DocViewerAction extends TcmISBaseAction {
	private String	docBasePath	= "";

	public DocViewerAction() {
		super();
		ResourceLibrary customerResource = new ResourceLibrary("tcmISWebResource");
		docBasePath = customerResource.getString("docViewerPath");
		if (!docBasePath.endsWith(File.separator)) {
			docBasePath += File.separator;
		}
	}

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		DocViewerInputBean input = new DocViewerInputBean(form, getTcmISLocale());
		if (isLoggedIn(request, true) || input.hasSeq()) {
			DocViewerProcess process = new DocViewerProcess(getDbUser());
			StringBuilder docToView = new StringBuilder(docBasePath);
			SpecDisplayViewBean spec = null;

			PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");

			if (input.isViewSpec()) {
				docToView.append("spec").append(File.separator);
				if (user == null) {
					spec = process.getSpec(input);
				}
				else {
					String module = ModuleUtils.getInstance().getModuleConfig(request).getPrefix();
					if ("/haas".equals(module)) {
						if (!input.hasOpsEntityId()) {
							input.setOpsEntityId(process.getOpsEntityId(input.getInventoryGroup()));
						}

						if (user.getPermissionBean().hasOpsEntityPermission("viewSpec", input.getOpsEntityId(), null)) {
							spec = process.getSpecForHaas(input);
						}
						else {
							log.debug("User denied access to spec due to lack of viewSpec permission for opsEntity : '" + input.getOpsEntityId() + "'");
							return mapping.findForward("noitarpermissions");
						}
					}
					else if ("/catalog".equals(module)) {
						spec = process.getSpec(input.getSpec());
					}
					else {
						input.setSeq("" + user.getPersonnelId());
						spec = process.getSpec(input);
					}
				}
			}
			else if (input.isViewCatalogAddSpec()) {
				spec = process.getCatalogAddSpec(input, "catalog_add_spec");
				if ("spec".equals(spec.getSpecSource())) docToView.append("spec").append(File.separator);
				else
					docToView.append("catalogAddSpec").append(File.separator);
			}
			else if (input.isViewCatalogAddSpecQc()) {
				spec = process.getCatalogAddSpec(input, "catalog_add_spec_qc");
				if ("spec".equals(spec.getSpecSource())) docToView.append("spec").append(File.separator);
				else
					docToView.append("catalogAddSpec").append(File.separator);
			}

			if (spec != null) {
				if (spec.isItar() && (user == null || !user.hasItarAccess())) {
					log.debug("User denied access to ITAR restricted spec: '" + spec.getSpecId() + "'");
					return mapping.findForward("noitarpermissions");
				}
				else {
					docToView.append(spec.getContent());
					// if spec is ITAR then confirm with users
					if (spec.isItar() && !"Y".equals(input.getViewItarConfirmed())) {
						request.setAttribute("specId", spec.getSpecId());
						request.setAttribute("docToView", docToView.toString());
						return mapping.findForward("itarviewer");
					}
					else {
						try {
							response.setHeader("Content-Disposition", "filename=Haas_" + input.getSpec() + ".pdf");
							response.setContentType("application/pdf");
							if (log.isDebugEnabled()) {
								log.debug("Retrieveing Doc: " + docToView.toString());
							}
							InputStream incomingPDF = new FileInputStream(docToView.toString());
							ServletOutputStream out = response.getOutputStream();
							IOUtils.copy(incomingPDF, out);
							out.flush();
							out.close();
						}catch (Exception e) {
							log.error("Error accessing Doc ->  " + docToView.toString(), e);
							return mapping.findForward("nopermissions");
						}
						return noForward;
					}
				}
			}
		}
		else {
			request.setAttribute("requestedPage", "docViewer");
			request.setAttribute("requestedURLWithParameters", getRequestedURLWithParameters(request));
			return (mapping.findForward("dologin"));
		}
		return mapping.findForward("nopermissions");
	}
}
