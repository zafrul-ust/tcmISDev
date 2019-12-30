package com.tcmis.ws.tablet.action;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONObject;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.util.StringHandler;
import com.tcmis.ws.tablet.beans.TabletInputBean;
import com.tcmis.ws.tablet.beans.VerifyExistsBean;
import com.tcmis.ws.tablet.process.InboundTransProcess;
import com.tcmis.ws.tablet.process.ReceiptProcess;
import com.tcmis.ws.tablet.process.SimpleTabletProcess;

public class VerifyExistsAction extends BaseTabletAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject results = new JSONObject();
		if (!isLoggedIn(request, true)) {
			results.put("Status", RESPONSE_SESSION_EXPIRED);
			results.put("Message", "Session Expired");
		}
		else {
			TabletInputBean input = new TabletInputBean(form, getTcmISLocale(request));

			if (input.hasPO() || input.hasRma() || input.hasReceiptId() || input.hasTransferRequest() || input.hasCitrPo()) {
				PersonnelBean user = (PersonnelBean)request.getSession().getAttribute("personnelBean");
				SimpleTabletProcess tabletProcess = new SimpleTabletProcess(getDbUser());
				try {
					JSONArray records = tabletProcess.verifyExists(input, user); 
					results.put("Status", RESPONSE_OK);
					if (records.length() == 0) {
						results.put("Exists", Boolean.FALSE);
					}
					
					else {
						results.put("Exists", Boolean.TRUE);
						results.put("Results", records);
					}
				}
				catch (Exception ex) {
					log.error("Error searching DB", ex);
					results.put("Status", RESPONSE_ERROR);
					results.put("Message", ex.toString());
				}
			}
			else {
				results.put("Status", RESPONSE_ERROR);
				results.put("Message", "Must supply one of: po, rma, receiptId, transferRequest, citrPo");
			}
		}

		// Write out the data
		OutputStreamWriter out = new OutputStreamWriter(response.getOutputStream(), "UTF-8");
		out.write(results.toString(3));
		out.close();
		return noForward;
	}

}
