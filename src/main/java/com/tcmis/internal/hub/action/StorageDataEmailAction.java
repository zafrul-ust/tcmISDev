package com.tcmis.internal.hub.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.internal.catalog.factory.ItemStorageDataMapper;
import com.tcmis.internal.hub.process.StorageDataEmailProcess;

public class StorageDataEmailAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		StorageDataEmailProcess process = new StorageDataEmailProcess(
				new ItemStorageDataMapper(
						new DbManager(this.getDbUser(request), this.getTcmISLocaleString(request))),
				getTcmISLocale(request));
		
		response.getWriter().print(process.process());
		
		return noForward;
	}

}
