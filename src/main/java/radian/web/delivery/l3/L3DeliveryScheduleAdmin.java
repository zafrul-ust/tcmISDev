package radian.web.delivery.l3;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import radian.tcmis.server7.client.l3.dbObjs.L3TcmISDBModel;
import radian.tcmis.server7.client.l3.helpers.L3ServerResourceBundle;
import radian.web.delivery.DeliveryScheduleAdmin;

public class L3DeliveryScheduleAdmin extends HttpServlet
{

	public L3DeliveryScheduleAdmin()
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
		L3TcmISDBModel db = null;
		try
		{
			db = new L3TcmISDBModel("L3");
			if(db != null);
			DeliveryScheduleAdmin obj = new DeliveryScheduleAdmin(new L3ServerResourceBundle(), db);
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
