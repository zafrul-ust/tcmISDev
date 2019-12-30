package radian.web.delivery.bae;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import radian.tcmis.server7.client.bae.dbObjs.BAETcmISDBModel;
import radian.tcmis.server7.client.bae.helpers.BAEServerResourceBundle;
import radian.web.delivery.DeliveryScheduleAdmin;

public class BAEDeliveryScheduleAdmin extends HttpServlet
{

	public BAEDeliveryScheduleAdmin()
	{
	}

	public void init(ServletConfig config)
		throws ServletException
	{
		super.init(config);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException
	{
		BAETcmISDBModel db = null;
		try
		{
			db = new BAETcmISDBModel("BAE");
			if(db != null);
			DeliveryScheduleAdmin obj = new DeliveryScheduleAdmin(new BAEServerResourceBundle(), db);
			obj.doPost(request, response);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return;
		}
		finally
		{
			db.close();
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException
	{
		doPost(request, response);
	}
}
