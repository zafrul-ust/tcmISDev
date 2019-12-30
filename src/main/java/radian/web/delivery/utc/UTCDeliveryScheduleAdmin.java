package radian.web.delivery.utc;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import radian.tcmis.server7.client.utc.dbObjs.UTCTcmISDBModel;
import radian.tcmis.server7.client.utc.helpers.UTCServerResourceBundle;
import radian.web.delivery.DeliveryScheduleAdmin;

public class UTCDeliveryScheduleAdmin extends HttpServlet
{

	public UTCDeliveryScheduleAdmin()
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
		UTCTcmISDBModel db = null;
		try
		{
			db = new UTCTcmISDBModel("UTC");
			if(db != null);
			DeliveryScheduleAdmin obj = new DeliveryScheduleAdmin(new UTCServerResourceBundle(), db);
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
