package radian.web.delivery.iai;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import radian.tcmis.server7.client.iai.dbObjs.IAITcmISDBModel;
import radian.tcmis.server7.client.iai.helpers.IAIServerResourceBundle;
import radian.web.delivery.DeliveryScheduleAdmin;

public class IAIDeliveryScheduleAdmin extends HttpServlet
{

	public IAIDeliveryScheduleAdmin()
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
		IAITcmISDBModel db = null;
		try
		{
			db = new IAITcmISDBModel("IAI");
			if(db != null);
			DeliveryScheduleAdmin obj = new DeliveryScheduleAdmin(new IAIServerResourceBundle(), db);
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
