package com.tcmis.internal.hub.action;

import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.json.JSONArray;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.JsonRequestResponseAction;
import com.tcmis.common.util.CSV;

public class CSVtoJSONAction extends JsonRequestResponseAction {

	public ActionForward executeAction(ActionMapping mapping, HttpServletRequest request, HttpServletResponse response, String jsonString) throws BaseException {
		try {
			JSONArray rows = new JSONArray();
			ActionForm form = (ActionForm) request.getAttribute("genericForm");
			FormFile uploadedFile = null;

			try {
				if (form.getMultipartRequestHandler() != null) {
					uploadedFile = (FormFile) form.getMultipartRequestHandler().getFileElements().get("uploadedCSV");
					try (InputStream in = uploadedFile.getInputStream();) {
						CSV csv = new CSV(true, ',', in);
						while (csv.hasNext()) {
							List<String> fields = csv.next();
							JSONArray row = new JSONArray();
							for (String field : fields) {
								row.put(field);
							}
							rows.put(row);
						}
					}
				}
			}
			catch (Exception e) {
			}

			// Write out the data
			PrintWriter out = response.getWriter();
			out.write("<html><body><script>\n");
			out.write("var data= " + rows.toString(3) + ";\n");
			out.write("parent.loadCSV(data);\n");
			out.write("</script></body></html>\n");
			out.close();
		}
		catch (Exception e) {
			log.error("Base Error in CSVtoJSONAction", e);
			throw new BaseException(e);
		}
		return noForward;
	}
}
