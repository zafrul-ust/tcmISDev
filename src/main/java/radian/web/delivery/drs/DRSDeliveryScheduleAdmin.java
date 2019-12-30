package radian.web.delivery.drs;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import radian.tcmis.server7.client.drs.dbObjs.DRSTcmISDBModel;
import radian.tcmis.server7.client.drs.helpers.DRSServerResourceBundle;
import radian.web.delivery.DeliveryScheduleAdmin;

public class DRSDeliveryScheduleAdmin extends HttpServlet
{

	public DRSDeliveryScheduleAdmin()
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
		DRSTcmISDBModel db = null;
		try
		{
			db = new DRSTcmISDBModel("DRS");
			if(db != null);
			DeliveryScheduleAdmin obj = new DeliveryScheduleAdmin(new DRSServerResourceBundle(), db);
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
