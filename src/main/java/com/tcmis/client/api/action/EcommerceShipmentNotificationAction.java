package com.tcmis.client.api.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tcmis.client.api.process.EcommerceShipmentNotificationProcess;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONObject;

import com.tcmis.common.exceptions.BaseException;


public class EcommerceShipmentNotificationAction extends BaseApiAction {

	public ActionForward executeAction(ActionMapping mapping, HttpServletRequest request, HttpServletResponse response, String jsonString) throws BaseException {
		try {
			JSONObject input = new JSONObject(jsonString);
			EcommerceShipmentNotificationProcess process = new EcommerceShipmentNotificationProcess(this.getDbUser(request));
			// Write out the data
			PrintWriter out = response.getWriter();
			out.write(process.getShipmentNotificationData(input).toString(3));
			out.close();
		}
		catch (Exception e) {
			log.error("Base Error in EcommerceShipmentNotificationAction", e);
			throw new BaseException(e);
		}
		return noForward;
	}
}
