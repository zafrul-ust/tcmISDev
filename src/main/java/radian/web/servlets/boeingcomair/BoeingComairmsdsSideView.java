package radian.web.servlets.boeingcomair;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;

import radian.tcmis.server7.client.boeingcomair.dbObjs.BoeingComairTcmISDBModel;
import radian.web.servlets.share.msdsSideView;

public class BoeingComairmsdsSideView extends HttpServlet {

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoeingComairTcmISDBModel db = null;
		try {
			//System.out.println("\n\n============ BoeingComairmsdssideview check");
			String client = request.getParameter("cl");
			db = new BoeingComairTcmISDBModel(StringUtils.isBlank(client) ? "BOEING_CO" : client);
			if (db == null){
				PrintWriter out2 = new PrintWriter (response.getOutputStream());
				out2.println("Starting DB");
				out2.println("DB is null");
				out2.close();
				return;
			}
			msdsSideView obj = new msdsSideView(new BoeingComairServerResourceBundle(),db);
			obj.doPost(request,response);
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			db.close();
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
}
