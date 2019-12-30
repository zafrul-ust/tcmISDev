package radian.web.delivery.ray;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;
import radian.tcmis.server7.client.ray.helpers.RayServerResourceBundle;
import radian.web.delivery.DeliveryScheduleAdmin;

public class RayDeliveryScheduleAdmin extends HttpServlet
{

	public RayDeliveryScheduleAdmin()
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
		RayTcmISDBModel db = null;
		try
		{
			db = new RayTcmISDBModel("Ray");
			if(db != null);
			DeliveryScheduleAdmin obj = new DeliveryScheduleAdmin(new RayServerResourceBundle(), db);
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
